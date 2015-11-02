package liuliu.custom.method.cache;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by liuliu on 2015/11/02   16:08
 *
 * @author 柳伟杰
 * @Email 1031066280@qq.com
 */
public class demo {
    private ImageMemoryCache memoryCache;
    private ImageFileCache fileCache;
    Context context;
    public Bitmap getBitmap(String url) {
        memoryCache = new ImageMemoryCache(context);
        fileCache = new ImageFileCache();
        // 从内存缓存中获取图片
        Bitmap result = memoryCache.getBitmapFromCache(url);
        if (result == null) {
            // 文件缓存中获取
            result = fileCache.getImage(url);
            if (result == null) {
                // 从网络获取
                result = ImageGetFromHttp.downloadBitmap(url);
                if (result != null) {
                    fileCache.saveBitmap(result, url);
                    memoryCache.addBitmapToCache(url, result);
                }
            } else {
                // 添加到内存缓存
                memoryCache.addBitmapToCache(url, result);
            }
        }
        return result;
    }
}
