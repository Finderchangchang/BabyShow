package liuliu.babyshow.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by liuliu on 2015/10/24   9:29
 *
 * @author 柳伟杰
 * @Email 1031066280@qq.com
 */
public class UserModel extends BmobObject {
    private String name;//用户名
    private String password;//密码

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
