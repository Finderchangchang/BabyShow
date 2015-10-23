package liuliu.custom.control.toast;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import liuliu.custom.R;
import liuliu.custom.model.PersonnelModel;


/**
 * Created by Administrator on 2015/9/30.
 */
public class ViewDialog extends Dialog {
    TextView pv_title;

    public ViewDialog(Context context, int theme) {
        super(context, theme);
        setOwnerActivity((Activity) context);

    }


    public static class Builder {
        private String positiveButtonText="";//确定
        private String negativeButtonText="";//取消
        private OnClickListener positiveButtonClickListener;//确定按钮
        private OnClickListener negativeButtonClickListener;//取消按钮
        private Context context;
        private String title;
        private View contentView;
        private PersonnelModel person = new PersonnelModel();

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setPerson(PersonnelModel p) {
            this.person = p;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;

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
        public ViewDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ViewDialog dialog = new ViewDialog(context, R.style.personal_dialog);
            dialog.setCanceledOnTouchOutside(false);
            View layout = inflater.inflate(R.layout.dialog_person_view, null);
            dialog.addContentView(layout, new ActionBar.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT));
            //((TextView) layout.findViewById(R.id.pv_title)).setText(title);
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
