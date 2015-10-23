package liuliu.custom.model;

/**
 * Created by LiuWeiJie on 2015/8/6 0006.
 * Email:1031066280@qq.com
 */
public class HttpModel {
    private boolean IsPost;//是否为post请求
    private String URL;//路径
    private String SessionId;//
    private String UserId;//用户ID
    private String Password;//密码
    private String Objective;//方法名
    private String Information;

    public boolean getIsPost() {
        return IsPost;
    }

    public void setIsPost(boolean isPost) {
        IsPost = isPost;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getObjective() {
        return Objective;
    }

    public void setObjective(String objective) {
        Objective = objective;
    }

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public String getInformation() {
        return Information;
    }

    public void setInformation(String information) {
        Information = information;
    }
}
