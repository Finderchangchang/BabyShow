package liuliu.babyshow.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import net.tsz.afinal.annotation.view.CodeNote;

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
    LinearLayout qq_login;
    @CodeNote(id = R.id.sina_iv_login, click = "onClick")
    LinearLayout sina_login;
    @CodeNote(id = R.id.wenxin_iv_login, click = "onClick")
    LinearLayout wenxin_login;

    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;//注意：SsoHandler 仅当 SDK 支持 SSO 时有效

    @Override
    public void initViews() {
        setContentView(R.layout.activity_login);
        mlistener = new LoginListener(this);
        sInstance = this;
    }

    @Override
    public void initEvents() {

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
                mAuthInfo = new AuthInfo(LoginActivity.sInstance, Constants.SINA_APPID, Constants.REDIRECT_URL, Constants.SCOPE);
                mSsoHandler = new SsoHandler(LoginActivity.sInstance, mAuthInfo);

//                mlistener.sinaLogin(mSsoHandler);
                mSsoHandler.authorize(new AuthListener());
                break;
        }
    }

    private Oauth2AccessToken mAccessToken;//封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能

    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     * 该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken != null) {
                // 保存 Token 到 SharedPreferences
                String uid = values.getString("uid").toString();
                String username = values.getString("userName").toString();
//                AccessTokenKeeper.writeAccessToken(sInstance, mAccessToken);
            } else {
                String code = values.getString("code");
                String message = "取消授权";
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
            }
        }

        @Override
        public void onCancel() {
//                    "授权取消"
        }

        @Override
        public void onWeiboException(WeiboException e) {
//                    "Auth exception : " + e.getMessage()
        }
    }

    /**
     * 当 SSO 授权 Activity 退出时，该函数被调用。
     *
     * @see {@link Activity#onActivityResult}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    @Override
    public void OnLoginResult(boolean result, User user) {
        if (result) {//登录成功

        } else {//登录失败

        }
    }
}
