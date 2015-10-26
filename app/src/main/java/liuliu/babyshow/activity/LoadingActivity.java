package liuliu.babyshow.activity;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import net.tsz.afinal.annotation.view.CodeNote;

import liuliu.babyshow.R;
import liuliu.babyshow.base.BaseActivity;
import liuliu.custom.method.Utils;

public class LoadingActivity extends BaseActivity {
    public static LoadingActivity mInstance;
    @CodeNote(id = R.id.bg_loading_iv)
    ImageView bg_img;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_loading);
        mInstance = this;
    }

    @Override
    public void initEvents() {
        Animation mAnimation = AnimationUtils.loadAnimation(mInstance, R.anim.enlarge);
        mAnimation.setFillAfter(true);
        bg_img.startAnimation(mAnimation);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Utils.IntentPost(mInstance, LoginActivity.class);
                mInstance.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
