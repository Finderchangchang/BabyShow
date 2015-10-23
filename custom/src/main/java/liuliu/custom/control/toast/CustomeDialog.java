package liuliu.custom.control.toast;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import liuliu.custom.R;

/**
 * 自定义Dialog
 * <p/>
 * Created by LiuWeiJie on 2015/7/28 0028.
 * Email:1031066280@qq.com
 */
public class CustomeDialog extends Dialog {
    public CustomeDialog(Context context) {
        super(context);
    }

    public CustomeDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context mContext;//系统上下文
        private String mTitle;//title
        private String mMessage;//消息内容
        private String positiveButtonText;//确定
        private String negativeButtonText;//取消
        private OnClickListener positiveButtonClickListener;//确定按钮
        private OnClickListener negativeButtonClickListener;//取消按钮
        private EditText pwd;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.mMessage = message;
            return this;
        }

        public String getPwdText() {
            return pwd.getText().toString();
        }

        public Builder setPositiveButtonText(String positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButtonText(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public CustomeDialog create() {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomeDialog dialog = new CustomeDialog(mContext, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_custome_layout, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            pwd = ((EditText) layout.findViewById(R.id.dialog_pwd));
            if (mTitle != null) {//设置标题
                ((TextView) layout.findViewById(R.id.dialog_title)).setText(mTitle);
            }
            if (mMessage != null) {//设置中间的消息内容
                TextView tv = ((TextView) layout.findViewById(R.id.dialog_content));
                tv.setVisibility(View.VISIBLE);
                pwd.setVisibility(View.GONE);

                if (mMessage.length() <= 32) {//字数超过一行字体变小
                    tv.setTextSize(16);
                } else {
                    tv.setTextSize(14);
                }
                tv.setText(mMessage);
            }
            Button button = (Button) layout.findViewById(R.id.dialog_positiveButton);
            if (("").equals(positiveButtonText) || positiveButtonText == null) {
                button.setVisibility(View.GONE);
            } else {
                button.setText(positiveButtonText);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        positiveButtonClickListener.onClick(dialog,
                                DialogInterface.BUTTON_POSITIVE);
                    }
                });
            }
            Button btn = (Button) layout.findViewById(R.id.dialog_negativeButton);
            if (("").equals(negativeButtonText) || negativeButtonText == null) {
                btn.setVisibility(View.GONE);
            } else {
                btn.setText(negativeButtonText);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        negativeButtonClickListener.onClick(dialog,
                                DialogInterface.BUTTON_NEGATIVE);
                    }
                });
            }
            dialog.setContentView(layout);
            return dialog;
        }

    }
}
