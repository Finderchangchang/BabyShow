package liuliu.babyshow.control.login;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.cache.ACache;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import liuliu.babyshow.activity.LoginActivity;
import liuliu.babyshow.type.SignTypes;
import liuliu.babyshow.model.User;
import liuliu.custom.desc.Constants;
import liuliu.custom.method.cache.ImageGetFromHttp;
import liuliu.custom.model.Cache;

/**
 * Created by liuliu on 2015/10/26   9:27
 * 登录的处理
 *
 * @author 柳伟杰
 * @Email 1031066280@qq.com
 */
public class LoginListener {
    ILoginView mLoginView;
    UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.login");
    User mUser;
    Context mContext;
    FinalDb mDB;
    //封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
    Oauth2AccessToken mAccessToken;
    UsersAPI mUsersAPI;
    ACache mCache;

    public LoginListener(ILoginView loginView, Context context, FinalDb db) {
        this.mLoginView = loginView;
        mUser = new User();
        mContext = context;
        mCache = ACache.get(context);
        mDB = db;
    }

    /*qq快捷登录*/
    public void qqLogin() {
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(LoginActivity.sInstance,
                Constants.QQ_APPID, Constants.QQ_KEY);
        qqSsoHandler.setTargetUrl("http://www.umeng.com");
        qqSsoHandler.addToSocialSDK();
        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(LoginActivity.sInstance, Constants.QQ_APPID, Constants.QQ_KEY);
        qZoneSsoHandler.addToSocialSDK();
        login(SHARE_MEDIA.QQ);
    }

    /*微博快捷登录*/
    public void sinaLogin(SsoHandler ssoHandler) {
        ssoHandler.authorize(new AuthListener());
    }

    /*微博获得登录信息Listener*/
    private class AuthListener implements WeiboAuthListener {
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
                    mUser.setUid(user.id);//唯一ID
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
                        if (loca.length > 1) {
                            mUser.setQq_province(user.location.split(" ")[0]);//qq显示的所在省
                            if (loca.length >= 2) {
                                mUser.setQq_city(user.location.split(" ")[1]);//qq上显示的所在市
                            }
                        }
                    }
                    mUser.setHeadimg_urlbig(user.avatar_large);//大头像
                    mUser.setHeadimg_urlsmall(user.profile_image_url);//小头像(50*50)
                    mUser.setSignTypes(SignTypes.SINA);
                    updateUser(mUser);
                } else {
                    Toast.makeText(LoginActivity.sInstance, response, Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            mLoginView.OnLoginResult(false, "登录失败！");
        }
    };

    /*授权。如果授权成功，则获取用户信息*/
    private void login(final SHARE_MEDIA platform) {
        mController.doOauthVerify(LoginActivity.sInstance, platform, new SocializeListeners.UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {

            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
                mLoginView.OnLoginResult(false, "登录失败！");
            }

            @Override
            public void onComplete(Bundle value, SHARE_MEDIA platform) {
                String uid = value.getString("uid");
                if (!TextUtils.isEmpty(uid)) {
                    mUser.setUid(uid);
                    getUserInfo(platform);
                } else {
                    mLoginView.OnLoginResult(false, "登录失败！");
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                mLoginView.OnLoginResult(false, "登录失败！");
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
//                    mUser.setSigntype(SignType.QQ);
                    mUser.setSignTypes(SignTypes.QQ);
                    updateUser(mUser);
                }
            }
        });
    }

    /*更新用户信息 User*/
    private void updateUser(final User user) {
        BmobQuery<User> bmobQuery = new BmobQuery<User>();
        bmobQuery.addWhereEndsWith("uid", user.getUid());
        bmobQuery.findObjects(mContext, new FindListener<User>() {
                    @Override
                    public void onSuccess(List<User> list) {
                        if (list.size() == 1) {//数据库中存在一条记录(不可能存在多条记录，uid为唯一主键)
                            list.get(0).update(mContext, new UpdateListener() {
                                @Override
                                public void onSuccess() {//添加成功以后保存在数据库中。
                                    saveUser(user);
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    mLoginView.OnLoginResult(false, "登录失败！");
                                }
                            });
                        } else {
                            user.save(mContext, new SaveListener() {
                                @Override
                                public void onSuccess() {//添加成功以后保存在数据库中。
                                    saveUser(user);
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    mLoginView.OnLoginResult(false, "登录失败！");
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        if (i == 101) {//没有user表
                            user.save(mContext, new SaveListener() {
                                @Override
                                public void onSuccess() {//添加成功以后保存在数据库中。
                                    saveUser(user);
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    mLoginView.OnLoginResult(false, "登录失败！");
                                }
                            });
                        } else {
                            mLoginView.OnLoginResult(false, "登录失败！");

                        }
                    }
                }

        );
    }

    /*保存用户信息*/

    public void saveUser(User user) {
        if (user.getHeadimg_urlbig() != null) {
            Bitmap bitmap = ImageGetFromHttp.downloadBitmap(user.getHeadimg_urlbig());
            if (bitmap != null) {
                user.setHeadimg_big(bitmap);
            }
        }
        if (user.getHeadimg_urlsmall() != null) {
            Bitmap bitmap = ImageGetFromHttp.downloadBitmap(user.getHeadimg_urlsmall());
            if (bitmap != null) {
                user.setHeadimg_small(bitmap);
            }
        }
        mDB.save(user);
        mCache.put(Cache.CACHE_USER_BIG_IMG, user.getHeadimg_big());//大头像缓存
        mCache.put(Cache.CACHE_USER_SMALL_IMG, user.getHeadimg_small());//小头像缓存
        mLoginView.OnLoginResult(true, "登录成功！");
    }
}
