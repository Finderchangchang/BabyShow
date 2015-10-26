package liuliu.babyshow.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by liuliu on 2015/10/24   9:29
 *
 * @author 柳伟杰
 * @Email 1031066280@qq.com
 */
public class UserModel extends BmobObject {
    private String id;//登录的账号
    private String nickname;//用户名（昵称）
    private String password;//密码
    private String telnum;//手机号码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
}
