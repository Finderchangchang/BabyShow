package liuliu.babyshow.control.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;

import java.util.Map;

import liuliu.babyshow.activity.LoginActivity;
import liuliu.babyshow.model.SignType;
import liuliu.babyshow.model.User;
import liuliu.custom.desc.Constants;

/**
 * Created by liuliu on 2015/10/26   9:27
 *
 * @author 柳伟杰
 * @Email 1031066280@qq.com
 */
public class LoginListener {
    ILoginView mLoginView;
    UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.login");
    User mUser;


    public LoginListener(ILoginView loginView) {
        this.mLoginView = loginView;
        mUser = new User();
    }

    /**
     * 登录的时候传递过来的参数。
     * 接受返回值用iLoginView.OnLoginResult(bool);
     *
     * @param name     登录的账号
     * @param password 密码
     */
    public void doLogin(String name, String password) {
        mLoginView.OnLoginResult(true, null);
    }

    /*qq快捷登录*/
    public void qqLogin() {
        initQQ();
        login(SHARE_MEDIA.QQ);
    }

    /*微博快捷登录*/
    public void sinaLogin(SsoHandler ssoHandler) {
        ssoHandler.authorize(new AuthListener());
    }

    private AuthInfo mAuthInfo;
    private Oauth2AccessToken mAccessToken;//封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
    private UsersAPI mUsersAPI;

    class AuthListener implements WeiboAuthListener {
        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken != null) {
                long uid = Long.parseLong(mAccessToken.getUid());
                mUsersAPI = new UsersAPI(LoginActivity.sInstance, Constants.SINA_KEY, mAccessToken);
                mUsersAPI.show(uid, mListener);
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

        }

        @Override
        public void onWeiboException(WeiboException e) {

        }
    }

    /*微博 OpenAPI 回调接口。*/
    private RequestListener mListener = new RequestListener() {
        @Override
        public void onComplete(String response) {
            if (!TextUtils.isEmpty(response)) {
                // 调用 User#parse 将JSON串解析成User对象
                com.sina.weibo.sdk.openapi.models.User user = com.sina.weibo.sdk.openapi.models.User.parse(response);
                if (user != null) {//根据id获得登录微博用户的所有信息
                    mUser.setMessage("登录成功！");
                    mUser.setId(user.id);//唯一ID
                    mUser.setNickname(user.screen_name);//昵称
                    if (!user.gender.isEmpty()) {//性别
                        switch (user.gender) {
                            case "m":
                                mUser.setGender("男");
                                break;
                            case "f":
                                mUser.setGender("女");
                                break;
                            case "未知":
                                mUser.setGender("未知");
                                break;
                        }
                    }
                    if (!user.location.isEmpty()) {
                        String loca[] = user.location.split(" ");
                        if (loca.length >= 2) {
                            mUser.setQq_city(user.location.split(" ")[1]);//qq上显示的所在市
                        } else if (loca.length == 1) {
                            mUser.setQq_province(user.location.split(" ")[0]);//qq显示的所在省
                        }
                    }
                    mUser.setHeadimg_urlbig(user.avatar_large);//大头像
                    mUser.setHeadimg_urlsmall(user.profile_image_url);//小头像(50*50)
                    mUser.setSigntype(SignType.SINA);
                    mLoginView.OnLoginResult(true, mUser);
                } else {
                    Toast.makeText(LoginActivity.sInstance, response, Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            ErrorInfo info = ErrorInfo.parse(e.getMessage());
            Toast.makeText(LoginActivity.sInstance, info.toString(), Toast.LENGTH_LONG).show();
        }
    };

    /*加载qq平台*/
    private void initQQ() {
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(LoginActivity.sInstance,
                Constants.QQ_APPID, Constants.QQ_KEY);
        qqSsoHandler.setTargetUrl("http://www.umeng.com");
        qqSsoHandler.addToSocialSDK();
        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(LoginActivity.sInstance, Constants.QQ_APPID, Constants.QQ_KEY);
        qZoneSsoHandler.addToSocialSDK();
    }

    /*授权。如果授权成功，则获取用户信息*/
    private void login(final SHARE_MEDIA platform) {
        mController.doOauthVerify(LoginActivity.sInstance, platform, new SocializeListeners.UMAuthListener() {

            @Override
            public void onStart(SHARE_MEDIA platform) {

            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
                mUser.setMessage("授权失败！");
                mLoginView.OnLoginResult(false, mUser);
            }

            @Override
            public void onComplete(Bundle value, SHARE_MEDIA platform) {
                String uid = value.getString("uid");
                if (!TextUtils.isEmpty(uid)) {
                    mUser.setUid(uid);
                    getUserInfo(platform);
                } else {
                    mUser.setMessage("授权失败！");
                    mLoginView.OnLoginResult(false, mUser);
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                mUser.setMessage("授权失败！");
                mLoginView.OnLoginResult(false, mUser);
            }
        });
    }

    /*获取qq的用户信息*/
    private void getUserInfo(SHARE_MEDIA platform) {
        mController.getPlatformInfo(LoginActivity.sInstance, platform, new SocializeListeners.UMDataListener() {

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(int status, Map<String, Object> info) {
                if (info != null) {
                    mUser.setMessage("登录成功！");
                    mUser.setNickname(info.get("screen_name").toString());//昵称
                    mUser.setGender(info.get("gender").toString());//性别
                    mUser.setQq_city(info.get("city").toString());//qq上显示的所在市
                    mUser.setQq_province(info.get("province").toString());//qq显示的所在省
                    String img_url = info.get("profile_image_url").toString();
                    if (img_url != "") {
                        mUser.setHeadimg_urlbig(img_url);//大头像
                        if (img_url.contains("/100")) {//小头像
                            mUser.setHeadimg_urlsmall(img_url.replace("100", "40").toString());
                        }
                    }
                    mUser.setSigntype(SignType.QQ);
                    mLoginView.OnLoginResult(true, mUser);
                }
            }
        });
    }
}
