package liuliu.custom.control.toolbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import liuliu.custom.R;

/**
 * Created by liuliu on 2015/09/30   16:55
 *
 * @author 柳伟杰
 * @Email 1031066280@qq.com
 */
public class TToolbar extends LinearLayout {
    int left_img_int;
    int right_img_int;
    float toolbar_elevation;
    String center_title_str;
    String left_title_str;
    boolean left_clickable;
    boolean center_clickable;
    boolean right_clickable;
    ImageView left_img;
    ImageView right_img;
    TextView center_title;
    TextView left_user_type_title;
    ImageView right_message_bg;
    TextView right_message;
    LinearLayout left_ll;
    RelativeLayout center_rl;
    RelativeLayout right_ll;
    RelativeLayout total_rl;
    RelativeLayout right_rl_message;
    LeftOnClickListener leftOnClickListener;
    RightOnClickListener rightOnClickListener;
    CenterOnClickListener centerOnClickListener;
    public TToolbar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TToolbar, defStyle, 0);
        left_img_int = a.getResourceId(R.styleable.TToolbar_left_img, 0);
        right_img_int = a.getResourceId(R.styleable.TToolbar_right_img, 0);
        center_title_str = a.getString(R.styleable.TToolbar_center_title);
        left_title_str = a.getString(R.styleable.TToolbar_left_title);
        left_clickable = a.getBoolean(R.styleable.TToolbar_left_ll_clickable, true);
        center_clickable = a.getBoolean(R.styleable.TToolbar_center_ll_clickable, false);
        right_clickable = a.getBoolean(R.styleable.TToolbar_right_ll_clickable, true);
        toolbar_elevation = a.getDimension(R.styleable.TToolbar_toolbar_elevation, 0);
        a.recycle();
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.t_toolbar, this, true);//自定义控件绑定
        left_img = (ImageView) this.findViewById(R.id.toolbar_left_img);
        right_img = (ImageView) this.findViewById(R.id.toolbar_right_img);
        center_title = (TextView) this.findViewById(R.id.toolbar_center_title);
        left_ll = (LinearLayout) this.findViewById(R.id.toolbar_left_ll);
        right_ll = (RelativeLayout) this.findViewById(R.id.toolbar_right_rl_img);
        left_user_type_title = (TextView) this.findViewById(R.id.toolbar_left_ll_user_type_title);
        right_message = (TextView) this.findViewById(R.id.toolbar_right_message);
        right_message_bg = (ImageView) this.findViewById(R.id.toolbar_right_message_bg);
        total_rl = (RelativeLayout) this.findViewById(R.id.toolbar_top_rl);
        right_rl_message = (RelativeLayout) this.findViewById(R.id.toolbar_right_rl_message);
        center_rl= (RelativeLayout) this.findViewById(R.id.toolbar_center_ll);
        if (toolbar_elevation != 0) {
            setToolbarElevation(toolbar_elevation);
        }
        if (center_title_str != null) {
            center_title.setText(center_title_str);
        }
        //设置左侧右侧图片显示以及点击事件
        if (left_img_int != 0 || left_title_str != null) {
            left_ll.setVisibility(VISIBLE);
            setLeftTitle(left_title_str);
            setLeftImg(left_img_int);
            if (left_clickable) {
                left_ll.setClickable(true);
                left_ll.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        leftOnClickListener.leftclick();
                    }
                });
            }
        } else {
            left_ll.setVisibility(GONE);
        }
        if (right_img_int != 0) {
            setRightImg(right_img_int);
        } else {
            right_ll.setVisibility(View.GONE);
        }
        if(center_clickable){
            center_rl.setClickable(true);
            center_rl.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    centerOnClickListener.centerclick();
                }
            });
        }
    }

    /*设置左侧文字*/
    public void setLeftTitle(String str) {
        if (str != null) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5, 0, 10, 0);
            left_user_type_title.setVisibility(VISIBLE);
            left_user_type_title.setLayoutParams(lp);
            left_user_type_title.setText(str);
        } else {
            left_user_type_title.setVisibility(GONE);
        }
    }


    //用代码为TToolbar设置图片以及文字
    public void setCenterTitle(String title) {
        center_title.setText(title);
    }

    /**
     * Drawable drawable = MainActivity.mIntaile.getResources().getDrawable(R.mipmap.face);
     * tToolbar.setLeftImg(drawable);
     * 设置左侧图片
     */
    public void setLeftImg(Drawable drawable) {
        left_img.setImageDrawable(drawable);
    }

    //设置右侧图片
    public void setRightImg(Drawable drawable) {
        right_img.setImageDrawable(drawable);
    }

    //设置toolbar阴影大小
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setToolbarElevation(float num) {
        total_rl.setElevation(num);
    }

    //设置右侧显示的文字
    public void setRightMessage(int message) {
        right_rl_message.setVisibility(VISIBLE);
        if (message >= 10) {
            right_message.setText("+" + message + " ");
        } else {
            right_message.setText(" +" + message + " ");
        }
    }

    //根据int类型设置左侧图片
    private void setLeftImg(int img) {
        left_img.setVisibility(VISIBLE);
        left_img.setImageResource(img);
        if (left_img == null) {
            left_img.setVisibility(GONE);
        }
    }

    //根据int类型设置右侧图片
    private void setRightImg(int img) {
        right_img.setImageResource(right_img_int);
        if (right_ll != null) {
            if (right_clickable) {
                right_ll.setClickable(true);
                right_ll.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rightOnClickListener.rightclick();
                    }
                });
            } else {
                right_ll.setClickable(false);
            }
        }
    }

    //获得中间的文本内容
    public String getCenterText(){
        return center_title.getText().toString().trim();
    }

    //---------接口设置----------
    public interface RightOnClickListener {//右侧点击事件

        void rightclick();
    }

    public interface LeftOnClickListener {//左侧点击事件
        void leftclick();
    }
    public interface CenterOnClickListener{//中间点击事件
        void centerclick();
    }

    public void setRightOnClick(RightOnClickListener rightOnClick) {
        rightOnClickListener = rightOnClick;
    }

    public void setCenterOnClick(CenterOnClickListener centerOnClick){
        centerOnClickListener=centerOnClick;
    }

    public void setLeftOnClick(LeftOnClickListener leftOnClick) {
        leftOnClickListener = leftOnClick;
    }

    public TToolbar(Context context) {
        this(context, null);
        init(context);
    }

    public TToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }
}
