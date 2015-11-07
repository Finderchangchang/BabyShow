package liuliu.babyshow.adapter;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import liuliu.babyshow.R;

/**
 * Created by liuliu on 2015/11/03   11:13
 *
 * @author 柳伟杰
 * @Email 1031066280@qq.com
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener {
    View header;
    int headerHeight;//顶部布局文件高度
    int firstVisibleItem;//第一个可见的item

    boolean isRemark;//标记，当前是在listview最顶端摁下的
    int startY;//摁下时的y坐标

    int scrollState;//当前滚动状态
    int state;//当前的状态
    final int NONE = 0;//正常状态
    final int PULL = 1;//提示下拉状态
    final int RELESE = 2;//提示释放状态
    final int REFLASHING = 3;//刷新状态
    IReflashListener iReflashListener;


    public RefreshListView(Context context) {
        super(context);
        initView(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    /**
     * 初始化界面
     */
    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        header = inflater.inflate(R.layout.header_layout, null);
        measureView(header);
        headerHeight = header.getMeasuredHeight();
        topPadding(-headerHeight);
        this.addHeaderView(header);
        this.setOnScrollListener(this);
    }

    /*通知父布局高度宽度*/
    private void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int height;
        int tempHeight = p.height;
        if (tempHeight > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(width, height);
    }

    private void topPadding(int topPadding) {
        header.setPadding(header.getPaddingLeft(), topPadding, header.getPaddingRight(), header.getPaddingBottom());
        header.invalidate();
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        scrollState = i;
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {//touch事件
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN://按下
                if (firstVisibleItem == 0) {
                    isRemark = true;
                    startY = (int) ev.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE://移动时候
                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                if (state == RELESE) {
                    state = REFLASHING;
                    //加载更多数据
                    iReflashListener.onReflash();
                } else if (state == PULL) {
                    state = NONE;
                    isRemark = false;
                }
                reflashViewByState();
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void onMove(MotionEvent ev) {
        if (!isRemark) {
            return;
        }
        int tempY = (int) ev.getY();
        int space = (tempY - startY) / 2;
        int topPadding = space - headerHeight;

        switch (state) {
            case NONE:
                if (space > 0) {
                    state = PULL;
                }
                break;//正常状态
            case PULL:
                topPadding(topPadding);
                if (space > headerHeight + 30 && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    state = RELESE;
                }
                break;//提示下拉状态
            case RELESE:
                topPadding(topPadding);
                if (space < headerHeight + 30) {
                    state = PULL;
                } else if (space <= 0) {
                    state = NONE;
                    isRemark = false;
                }
                break;//提示释放状态
            case REFLASHING:
                break;//刷新状态
        }
    }

    private void reflashViewByState() {
        switch (state) {
            case NONE:
                topPadding(-headerHeight);
                break;
            case PULL:
                topPadding(-headerHeight);
                break;
            case RELESE:
                break;
            case REFLASHING:
                topPadding(0);
                Handler handler = new Handler();

                Runnable updateThread = new Runnable() {
                    public void run() {
                        topPadding(-headerHeight);
                        state = NONE;
                    }
                };
                handler.postDelayed(updateThread, 3000);
                break;
        }
    }

    public void setiReflashListener(IReflashListener listener) {
        this.iReflashListener = listener;
    }

    public interface IReflashListener {
        void onReflash();
    }
    public void reflashComplete(){
        state=NONE;
        isRemark=false;
        reflashViewByState();
    }
}
