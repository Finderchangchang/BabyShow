package liuliu.babyshow.control.login;

import liuliu.babyshow.model.User;

/**
 * Created by liuliu on 2015/10/26   9:26
 * 登录View
 *
 * @author 柳伟杰
 * @Email 1031066280@qq.com
 */
public interface ILoginView {
    void OnLoginResult(boolean result, User user);//登录操作
}
