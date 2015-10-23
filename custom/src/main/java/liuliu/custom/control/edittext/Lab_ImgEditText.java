package liuliu.custom.control.edittext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;

import java.util.List;

import liuliu.custom.method.Utils;
import liuliu.custom.control.spinner.popsipnner.SpinerPopWindow;

/*
 * Created by liuliu on 2015/08/20   9:39
 *
 * @author 柳伟杰
 * @Remark 左部文字，右侧图片（带点击事件）
 * @Email 1031066280@qq.com
 */
public class Lab_ImgEditText extends EditText {
    Drawable mImg;//右侧显示的图片
    String mLab;//左侧显示的文字
    String mRightLab;//右侧显示的文字
    int lab_size = 28;//左侧显示的文字大小
    ImgClickListener mListener;//图片点击监听事件
    int mIdRight = 1;//默认图片id
    int mIdBottom = 1;//点击图片id
    List<String> mList;
    SpinerPopWindow popWindow;

    //EditText右侧图片点击设置，监听。
    public interface ImgClickListener {
        void IntentPost(boolean result);//执行相关操作
    }


    //设置左侧图片与右侧图片（点击事件）
    public void setEditText(int layoutIdRight, int layoutIdBottom, String lab, ImgClickListener listener) {
        mIdRight = layoutIdRight;
        mIdBottom = layoutIdBottom;
        mLab = lab;
        mListener = listener;
        initPop();//加载popWindow隐藏触发事件
    }

    /*设置左右都有文字，但是无点击事件，无图片*/
    public void setEditText(String left_lab, String right_lab) {
        mLab = left_lab;
        mRightLab = right_lab;
    }

    public void setEditText(String left_lab) {
        mLab = left_lab;
    }

    //设置左侧图片与右侧图片（无点击事件）
    public void setEditText(int layoutIdRight, String lab, ImgClickListener listener) {
        mIdRight = layoutIdRight;
        mLab = lab;
        mListener = listener;
        initPop();//加载popWindow隐藏触发事件
    }

    //设置左侧图片与右侧图片（带下拉菜单的）
    public void setEditText(int layoutIdRight, int layoutIdBottom, String lab, ImgClickListener listener,
                            List<String> list, SpinerPopWindow mPop) {
        mIdRight = layoutIdRight;
        mIdBottom = layoutIdBottom;
        mLab = lab;
        mListener = listener;
        mList = list;
        popWindow = mPop;
        initPop();//加载popWindow隐藏触发事件
    }

    public void setTxtRight(String val) {
        final int left = mIdRight;
        final int bottom = mIdBottom;
        if (val.equals("left")) {
            mImg = getResources().getDrawable(left);
            resc = true;
        } else if (val.equals("bottom")) {
            mImg = getResources().getDrawable(bottom);
        }
        mImg.setBounds(0, 0, mImg.getIntrinsicWidth(), mImg.getIntrinsicHeight());
        setClearIconVisible(true);
    }

    //监听EditText右侧图片点击进行切换的方法
    private void initPop() {
        final int mleft = mIdRight;
        if (popWindow != null) {//监听dialog隐藏
            popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mImg = getResources().getDrawable(mleft);
                    mImg.setBounds(0, 0, mImg.getIntrinsicWidth(), mImg.getIntrinsicHeight());
                    setClearIconVisible(true);
                }
            });
        }
        if (mIdRight != 1) {
            int id = mIdRight;
            mImg = getResources().getDrawable(id);
            mImg.setBounds(0, 0, mImg.getIntrinsicWidth(), mImg.getIntrinsicHeight());
            setClearIconVisible(true);
        }
    }

    Matrix matrix = new Matrix();
    /**
     * @说明：isInnerWidth, isInnerHeight为ture，触摸点在删除图标之内，则视为点击了删除图标
     * event.getX() 获取相对应自身左上角的X坐标
     * event.getY() 获取相对应自身左上角的Y坐标
     * getWidth() 获取控件的宽度
     * getHeight() 获取控件的高度
     * getTotalPaddingRight() 获取删除图标左边缘到控件右边缘的距离
     * getPaddingRight() 获取删除图标右边缘到控件右边缘的距离
     * isInnerWidth:
     * getWidth() - getTotalPaddingRight() 计算删除图标左边缘到控件左边缘的距离
     * getWidth() - getPaddingRight() 计算删除图标右边缘到控件左边缘的距离
     * isInnerHeight:
     * distance 删除图标顶部边缘到控件顶部边缘的距离
     * distance + height 删除图标底部边缘到控件顶部边缘的距离
     */
    //EditText图片点击触发事件
    public boolean resc = true;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.requestFocus();
            final int mBottom = mIdBottom;
            final int mLeft = mIdRight;
            if (mIdBottom == 1) {
                if (getCompoundDrawables()[2] != null) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    Rect rect = getCompoundDrawables()[2].getBounds();
                    int height = rect.height();
                    int distance = (getHeight() - height) / 2;
                    boolean isInnerWidth = x > (getWidth() - getTotalPaddingRight()) && x < (getWidth() - getPaddingRight());
                    boolean isInnerHeight = y > distance && y < (distance + height);
                    if (isInnerWidth && isInnerHeight) {
                        resc = false;
                    }
                }
                if (resc) {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    this.setFocusable(true);
                }
            } else if (mIdBottom != 1) {
                if (popWindow != null) {
                    resc = false;
                } else {
                    if (resc) {//打开隐藏的内容
                        mImg = getResources().getDrawable(mBottom);
                        resc = false;
                    } else {//隐藏内容
                        mImg = getResources().getDrawable(mLeft);
                        resc = true;
                    }
                }
                mImg.setBounds(0, 0, mImg.getIntrinsicWidth(), mImg.getIntrinsicHeight());
                setClearIconVisible(true);
            }
            if (mRightLab == null) {
                if (mIdRight != 1 || mIdBottom != 1) {
                    mListener.IntentPost(!resc);
                }
            }
        }

        return true;
    }

    //EditText头部文字
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        if (Utils.getScannerWidth(getContext()) > 480) {
            paint.setTextSize(32);
            lab_size = 32;
        } else {
            paint.setTextSize(22);
            lab_size = 22;
        }
        int len = mLab.length();
        if (len > 4) {
            len = 4;
        }
        setPadding(lab_size * (len) + 25, 0, 30, 0);//30:图片距离右侧的距离
        paint.setColor(Color.BLACK);
        if (mLab != null) {
            canvas.drawText(mLab, 15, getHeight() / 2 + 8, paint);
        }
        if (mRightLab != null) {
            canvas.drawText(mRightLab, getWidth() - 60, getHeight() / 2 + 8, paint);
        }
    }

    //设置右侧图片显示隐藏方法

    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mImg : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    public Lab_ImgEditText(Context context) {
        super(context);
    }

    public Lab_ImgEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Lab_ImgEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
