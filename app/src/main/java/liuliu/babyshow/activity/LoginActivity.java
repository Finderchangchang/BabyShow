package liuliu.babyshow.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;

import net.tsz.afinal.annotation.view.CodeNote;

import java.util.Map;

import liuliu.babyshow.R;
import liuliu.babyshow.base.BaseActivity;
import liuliu.babyshow.control.login.ILoginView;
import liuliu.babyshow.control.login.LoginListener;
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
    ImageView qq_login;
    @CodeNote(id = R.id.sina_iv_login, click = "onClick")
    ImageView sina_login;
    @CodeNote(id = R.id.wenxin_iv_login, click = "onClick")
    ImageView wenxin_login;

    private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.login");

    @Override
    public void initViews() {
        setContentView(R.layout.activity_login);
        mlistener = new LoginListener(this);
        sInstance = this;
    }

    @Override
    public void initEvents() {
        addQZoneQQPlatform();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_btn_login:
                break;
            case R.id.reg_user_btn_login:
                break;
            case R.id.qq_iv_login:
                login(SHARE_MEDIA.QQ);
                break;
            case R.id.sina_iv_login:
                login(SHARE_MEDIA.SINA);
                break;
        }
    }

    @Override
    public void OnLoginResult(boolean result, String message) {
        if (result) {//登录成功

        } else {//登录失败

        }
    }

    /*初始化QQ登陆*/
    private void addQZoneQQPlatform() {
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(sInstance,
                Constants.QQ_APPID, Constants.QQ_KEY);
        qqSsoHandler.setTargetUrl("http://www.umeng.com");
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(sInstance, Constants.QQ_APPID, Constants.QQ_KEY);
        qZoneSsoHandler.addToSocialSDK();
    }
    private void addSina(){
        UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.login");
    }

    /**
     * 授权。如果授权成功，则获取用户信息</br>
     */
    private void login(final SHARE_MEDIA platform) {
        mController.doOauthVerify(sInstance, platform, new SocializeListeners.UMAuthListener() {

            @Override
            public void onStart(SHARE_MEDIA platform) {
                Utils.ToastShort(sInstance, "start");
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
            }

            @Override
            public void onComplete(Bundle value, SHARE_MEDIA platform) {
                Utils.ToastShort(sInstance, "onComplete");
                String uid = value.getString("uid");
                if (!TextUtils.isEmpty(uid)) {
                    getUserInfo(platform);
                } else {
                    Utils.ToastShort(sInstance, "授权失败...");
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
            }
        });
    }

    /**
     * 获取授权平台的用户信息</br>
     */
    private void getUserInfo(SHARE_MEDIA platform) {
        mController.getPlatformInfo(sInstance, platform, new SocializeListeners.UMDataListener() {

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(int status, Map<String, Object> info) {
                // String showText = "";
                // if (status == StatusCode.ST_CODE_SUCCESSED) {
                // showText = "用户名：" + info.get("screen_name").toString();
                // Log.d("#########", "##########" + info.toString());
                // } else {
                // showText = "获取用户信息失败";
                // }
                if (info != null) {
                    Toast.makeText(sInstance, info.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
