package liuliu.custom.desc;

/**
 * 分享与login
 */
public class Constants {
    /*QQ的第三方登录需要的东西*/
    public static final String QQ_APPID = "1104852415";
    public static final String QQ_KEY = "iBVxwYXKS6F5HJwg";
    /*微博的第三方登录需要的东西*/
    public static final String SINA_APPID = "4147686949";
    public static final String SINA_KEY = "02e10b9e0c2be29df8286368c531e797";
    public static final String REDIRECT_URL = "http://www.sina.com";
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";
    /*微信的第三方登录需要的东西*/
    public static final String WEIXIN_APPID = "1104852415";
    public static final String WEIXIN_KEY = "iBVxwYXKS6F5HJwg";

    public static final String DESCRIPTOR = "com.umeng.share";

    private static final String TIPS = "请移步官方网站 ";
    private static final String END_TIPS = ", 查看相关说明.";
    public static final String TENCENT_OPEN_URL = TIPS + "http://wiki.connect.qq.com/android_sdk使用说明"
            + END_TIPS;
    public static final String PERMISSION_URL = TIPS + "http://wiki.connect.qq.com/openapi权限申请"
            + END_TIPS;

    public static final String SOCIAL_LINK = "http://www.umeng.com/social";
    public static final String SOCIAL_TITLE = "友盟社会化组件帮助应用快速整合分享功能";
    public static final String SOCIAL_IMAGE = "http://www.umeng.com/images/pic/banner_module_social.png";

    public static final String SOCIAL_CONTENT = "友盟社会化组件（SDK）让移动应用快速整合社交分享功能，我们简化了社交平台的接入，为开发者提供坚实的基础服务：（一）支持各大主流社交平台，" +
            "（二）支持图片、文字、gif动图、音频、视频；@好友，关注官方微博等功能" +
            "（三）提供详尽的后台用户社交行为分析。http://www.umeng.com/social";

}
