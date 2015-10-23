package liuliu.babyshow.base;

import android.app.Application;
import android.content.Context;

/**
 * 作者:柳伟杰 邮件:1031066280@qq.com
 * 创建时间:15/6/21 下午10:13
 * 描述:
 */
public class BaseApplication extends Application {
    private static BaseApplication sInstance;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public static BaseApplication getInstance() {
        return sInstance;
    }

    //系统处于资源匮乏的状态
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}