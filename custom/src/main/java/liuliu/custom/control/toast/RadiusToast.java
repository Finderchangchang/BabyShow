package liuliu.custom.control.toast;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import liuliu.custom.R;

/**
 * Created by LiuWeiJie on 2015/8/11 0011.
 * Email:1031066280@qq.com
 */
public class RadiusToast extends Toast {
    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public RadiusToast(Context context) {
        super(context);
    }

    public static Toast makeText(Context context, CharSequence text, int duration) {
        Toast result = new Toast(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.newtoast, null);
        layout.getBackground().setAlpha(100);
        layout.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        TextView textView = (TextView) layout.findViewById(R.id.toast_text);
        textView.setText(text);
        result.setView(layout);
        result.setDuration(duration);
        return result;
    }
}
