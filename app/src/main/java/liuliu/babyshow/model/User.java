package liuliu.babyshow.model;

import android.graphics.Bitmap;

import cn.bmob.v3.BmobObject;

/**
 * Created by liuliu on 2015/10/24   9:29
 *
 * @author 柳伟杰
 * @Email 1031066280@qq.com
 */
public class User extends BmobObject {
    private String id;//登录的账号
    private String uid;//唯一的编码（后台返回的）
    private int signtype;//登录方式（NORMAL = 0,默认情况;QQ = 1,qq登录;SINA = 2,新浪登录;WEIXIN = 3,微信登录）
    private String nickname;//用户名（昵称）
    private String gender;//性别
    private String qq_city;//qq账号显示：所在城市
    private String qq_province;//qq账号显示：所在省
    private String headimg_urlbig;//大头像url
    private String headimg_urlsmall;//小头像url
    private Bitmap headimg_big;//大头像
    private Bitmap headimg_small;//小头像
    private String password;//密码
    private String telnum;//手机号码
    private String token_info;//token详细信息
    private String content;//简介
    private String token;//token
    private String message;//当出现错误时返回错误提示

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getSigntype() {
        return signtype;
    }

    public void setSigntype(int signtype) {
        this.signtype = signtype;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimg_urlbig() {
        return headimg_urlbig;
    }

    public void setHeadimg_urlbig(String headimg_urlbig) {
        this.headimg_urlbig = headimg_urlbig;
    }

    public String getHeadimg_urlsmall() {
        return headimg_urlsmall;
    }

    public void setHeadimg_urlsmall(String headimg_urlsmall) {
        this.headimg_urlsmall = headimg_urlsmall;
    }

    public Bitmap getHeadimg_big() {
        return headimg_big;
    }

    public void setHeadimg_big(Bitmap headimg_big) {
        this.headimg_big = headimg_big;
    }

    public Bitmap getHeadimg_small() {
        return headimg_small;
    }

    public void setHeadimg_small(Bitmap headimg_small) {
        this.headimg_small = headimg_small;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelnum() {
        return telnum;
    }

    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }

    public String getToken_info() {
        return token_info;
    }

    public void setToken_info(String token_info) {
        this.token_info = token_info;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getQq_city() {
        return qq_city;
    }

    public void setQq_city(String qq_city) {
        this.qq_city = qq_city;
    }

    public String getQq_province() {
        return qq_province;
    }

    public void setQq_province(String qq_province) {
        this.qq_province = qq_province;
    }
}
