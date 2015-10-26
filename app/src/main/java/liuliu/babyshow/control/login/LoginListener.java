package liuliu.babyshow.control.login;

import android.content.Context;

/**
 * Created by liuliu on 2015/10/26   9:27
 *
 * @author 柳伟杰
 * @Email 1031066280@qq.com
 */
public class LoginListener {
    ILoginView mLoginView;
    Context mContext;

    public LoginListener(ILoginView loginView) {
        this.mLoginView = loginView;
    }

    /**
     * 登录的时候传递过来的参数。
     * 接受返回值用iLoginView.OnLoginResult(bool);
     *
     * @param name     登录的账号
     * @param password 密码
     */
    public void doLogin(String name, String password) {
        mLoginView.OnLoginResult(true, "登录成功");
    }
}
