package liuliu.custom.control.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import liuliu.custom.R;

/**
 * Created by liuliu on 2015/08/29   14:14
 *
 * @author 柳伟杰
 * @Email 1031066280@qq.com
 */
public class LoadMoreListView extends ListView implements OnScrollListener {
    View bottomView;
    TextView textView;//需要显示的文字内容
    ProgressBar bar;//进度条
    LinearLayout load_more_ll;//底部显示的内容
    int startY;
    int stopY;
    int totalItem;//总的item
    int lastVisibleItem;//最后一个可见的item
    int footer_minHeight = 200;//底部最小需要滑动距离（小于此距离不加载）
    onLoadListener mLoadListener;
    onToastListener mToastListener;
    public final int Footer_LoadMore = 1;//向上拉（松开加载更多）
    public final int Footer_Loading = 2;//加载中
    public final int Footer_LoadEnd = 3;//加载结束
    boolean Footer_isLoading = false;//是否正在加载
    public boolean Footer_noMore = true;
//    public boolean isFullScanner=true;//是否能布满全屏
//    public boolean isToasting;

    public LoadMoreListView(Context context) {
        super(context);
        initViews(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    /*加载需要显示的内容*/
    private void initViews(Context context) {
        this.setOnScrollListener(this);
        bottomView = LayoutInflater.from(context).inflate(R.layout.loadmore_footer, null);
        textView = (TextView) bottomView.findViewById(R.id.footer_tv);//底部文字提示信息
        bar = (ProgressBar) bottomView.findViewById(R.id.footer_pb);
        load_more_ll = (LinearLayout) bottomView.findViewById(R.id.load_more_layout);
        this.addFooterView(bottomView);//添加View到主布局
        setFooterVisible(Footer_LoadEnd, 0);//隐藏底部布局
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.lastVisibleItem = firstVisibleItem + visibleItemCount;
        this.totalItem = totalItemCount;
    }

    /*滑动距离大于footer_minHeight执行刷新操作*/
    private boolean isPullToLoadMore() {
        if ((startY - stopY) > footer_minHeight) {
            return true;
        } else {//小于footer_minHeight隐藏底部布局
            setFooterVisible(Footer_LoadEnd, 0);
            return false;
        }
    }

    /*加载更多监听事件*/
    public interface onLoadListener {
        void onLoad();
    }

    public interface onToastListener {
        void onToast();
    }

    /*设置onLoadListener监听事件*/
    public void setLoadMore(onLoadListener listener) {
        mLoadListener = listener;
    }

    public void setOnToastListener(onToastListener listener) {
        mToastListener = listener;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP://抬起
                stopY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_DOWN://按压（向上滑动显示：松开加载更多...）
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE://移动
                if (!Footer_isLoading) {
                    onMove(ev);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /*判断移动过程中操作*/
    private void onMove(MotionEvent event) {
        int totalHeight = startY - (int) event.getY();
        if (totalHeight > 0) {
            if (totalHeight < 400) {//设置向上滑动的最高距离
//                if(isFullScanner) {
                if (Footer_noMore) {
                    setFooterVisible(Footer_LoadMore, totalHeight);
                }
//                }else{
//                    setFooterVisible(Footer_LoadEnd, 0);
//                    if(!isToasting){
//                        mToastListener.onToast();
//                    }
//                }
            }
        }
    }

    /*位置发生改变*/
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (lastVisibleItem == totalItem && scrollState == SCROLL_STATE_IDLE) {//状态为滚动停止
            if (!Footer_isLoading && isPullToLoadMore()) {//&&isFullScanner
                Footer_isLoading = true;
                setFooterVisible(Footer_Loading, 0);
                mLoadListener.onLoad();
            }
        }
    }

    /**
     * 控制底部显示的内容。
     *
     * @param state       1.松开加载更多 2.加载中 3.隐藏
     * @param totalHeight 向上拉时滑动的距离
     */
    public void setFooterVisible(int state, int totalHeight) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        load_more_ll.setLayoutParams(params); //使设置好的布局参数应用到控件
        load_more_ll.setVisibility(View.VISIBLE);
        String txt_val = "";
        switch (state) {
            case Footer_LoadMore:
                params.height = totalHeight;
                load_more_ll.setLayoutParams(params); //使设置好的布局参数应用到控件
                bar.setVisibility(View.GONE);
                txt_val = "松开加载更多...";
                break;
            case Footer_Loading:
                if (Footer_noMore) {
                    bar.setVisibility(View.VISIBLE);
                    txt_val = "加载中...";
                }
                break;
            case Footer_LoadEnd:
                /*隐藏这三个属性即可因为主布局是WRAP_Content*/
                Footer_isLoading = false;
                bar.setVisibility(View.GONE);
                textView.setTextSize(0);
                textView.setText("");
                break;
        }
        if (txt_val != "") {
            textView.setTextSize(14);
            textView.setText(txt_val);
        }
    }
}
