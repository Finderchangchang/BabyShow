package liuliu.babyshow.base;

import android.os.Bundle;
import android.os.StrictMode;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;

import cn.bmob.v3.Bmob;
import liuliu.custom.method.Utils;
import liuliu.custom.model.AuthorizeModel;
import liuliu.custom.model.Cryptography;
import liuliu.custom.model.GlobalModel;
import liuliu.custom.model.HttpModel;

/**
 * BaseActivity声明相关通用方法
 * <p/>
 * Created by LiuWeiJie on 2015/7/22 0022.
 * Email:1031066280@qq.com
 */
public abstract class BaseActivity extends FinalActivity {
    public HttpModel httpModel;
    public FinalDb finalDb;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finalDb = FinalDb.create(BaseActivity.this);
        httpModel = new HttpModel();
        initViews();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        initEvents();
        // 初始化 Bmob SDK
        // 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
        Bmob.initialize(this, "58ad362bfd8c7253ebc156096abeb0f0");
    }

    public abstract void initViews();

    public abstract void initEvents();

    /*activity销毁处理*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    /** 获取用户信息 */
    protected AuthorizeModel getLoginAuthorizeModel() {
        AuthorizeModel model = new AuthorizeModel();
        {
            model.SessionId = Utils.ReadString(this, Utils.KEY_SESSIONID);
            model.UserId = Utils.ReadString(this, Utils.KEY_USERNAME);
            model.Password = Utils.ReadString(this, Utils.KEY_PASSWORD);
            if (!Utils.isEmpty(model.Password)) {
                model.Password = Cryptography.HashPassword(model.Password,
                        "");
            }
        }
        return model;
    }

    /** 获取服务器信息 */
    protected GlobalModel getGlobalModel() {
        GlobalModel model = new GlobalModel();
        {
            model.ServerName = Utils.ReadString(this, Utils.KEY_SERVERNAME);
            model.ServerPort = Utils.ReadString(this, Utils.KEY_PORT);
        }
        return model;
    }

}
