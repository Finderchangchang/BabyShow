package liuliu.babyshow.control.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.cache.ACache;

import liuliu.babyshow.R;
import liuliu.babyshow.activity.MainActivity;
import liuliu.babyshow.model.User;
import liuliu.custom.model.Cache;

/**
 * Created by liuliu on 2015/10/29   16:01
 * 主页面Listener处理
 *
 * @author 柳伟杰
 * @Email 1031066280@qq.com
 */
public class MainListener {
    ACache mCache;//缓存
    IMainView mImainView;
    Context mContext;
    User user;
    FinalDb mFinalDb;

    public MainListener(IMainView iMainView, FinalDb finalDb) {
        mContext = MainActivity.mIntaile;
        mCache = ACache.get(mContext);
        mImainView = iMainView;
        mFinalDb = finalDb;
    }

    /*获得用户信息*/
    public void GetUserCache() {
        user = mFinalDb.findAll(new User().getClass()).get(0);
        Bitmap big_img = mCache.getAsBitmap(Cache.CACHE_USER_BIG_IMG);
        Bitmap small_img = mCache.getAsBitmap(Cache.CACHE_USER_SMALL_IMG);
        if (big_img != null) {
            user.setHeadimg_big(big_img);
        } else if (small_img != null) {
            user.setHeadimg_small(small_img);
        } else {
            user.setHeadimg_small(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher));
        }
        mImainView.OnLoadUser(user);
    }
}
