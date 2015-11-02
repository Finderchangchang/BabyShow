package liuliu.babyshow.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

import net.tsz.afinal.annotation.view.CodeNote;
import net.tsz.afinal.cache.ACache;

import liuliu.babyshow.R;
import liuliu.babyshow.base.BaseActivity;
import liuliu.babyshow.control.login.ILoginView;
import liuliu.babyshow.control.login.LoginListener;
import liuliu.babyshow.model.User;
import liuliu.custom.control.edittext.ImageEditText;
import liuliu.custom.desc.Constants;
import liuliu.custom.method.Utils;

public class LoginActivity extends BaseActivity implements ILoginView {
    LoginListener mlistener;
    public static LoginActivity sInstance;
    @CodeNote(id = R.id.user_btn_login, click = "onClick")
    Button login_btn;
    @CodeNote(id = R.id.reg_user_btn_login, click = "onClick")
    Button reg_btn;
    @CodeNote(id = R.id.user_et_login)
    ImageEditText user_id;//用户id
    @CodeNote(id = R.id.password_et_login)
    ImageEditText user_pwd;//密码
    @CodeNote(id = R.id.qq_iv_login, click = "onClick")
    RelativeLayout qq_login;
    @CodeNote(id = R.id.sina_iv_login, click = "onClick")
    LinearLayout sina_login;
    @CodeNote(id = R.id.wenxin_iv_login, click = "onClick")
    LinearLayout wenxin_login;

    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;//注意：SsoHandler 仅当 SDK 支持 SSO 时有效
    ACache mCache;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_login);
        sInstance = this;
        mlistener = new LoginListener(this, sInstance, finalDb);
        mCache = ACache.get(sInstance);
    }

    @Override
    public void initEvents() {
        if (finalDb.findAll(new User().getClass()).size() == 1) {
            Utils.IntentPost(sInstance, MainActivity.class);
            sInstance.finish();
        }
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_btn_login:
                break;
            case R.id.reg_user_btn_login:
                Utils.ToastShort(sInstance, "当前未开放注册，请选择快捷登录");
                break;
            case R.id.qq_iv_login:
                mlistener.qqLogin();
                break;
            case R.id.sina_iv_login:
                mAuthInfo = new AuthInfo(sInstance, Constants.SINA_APPID, Constants.REDIRECT_URL, Constants.SCOPE);
                mSsoHandler = new SsoHandler(sInstance, mAuthInfo);
                mlistener.sinaLogin(mSsoHandler);
                break;
        }
    }

    /*当 SSO 授权 Activity 退出时，该函数被调用。*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    /*处理登录成功和失败*/
    @Override
    public void OnLoginResult(boolean result, String message) {
        if (result) {//登录成功
            Utils.IntentPost(sInstance, MainActivity.class);
            sInstance.finish();
        }
        Utils.ToastShort(sInstance, message);
    }
}
