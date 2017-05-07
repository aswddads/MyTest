package tj.com.mysdk.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import tj.com.mysdk.R;

/**
 * Created by Jun on 17/5/5.
 * 初始化ImageLoader
 */

public class ImageLoaderManager {
    /**
     * 默认的参数值
     */
    private static final int THREAD_COUNT = 4;//表明uil最多可以有多少条线程
    private static final int PROPRITY = 2;//图片加载的优先级
    private static final int DISK_CACHE_SIZE = 50 * 1024;//可以缓存图片的大小
    private static final int CONNECTION_TIME_OUT = 5;//连接超时时间
    private static final int READ_TIME_OUT = 30;//读取超时时间

    private static ImageLoader mImageLoader = null;
    private static ImageLoaderManager mInstance = null;

    public static ImageLoaderManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ImageLoaderManager.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoaderManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 单例模式的私有构造方法
     *
     * @param context
     */
    private ImageLoaderManager(Context context) {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration
                .Builder(context)
                .threadPoolSize(THREAD_COUNT)//图片下载的线程数量
                .threadPriority(Thread.NORM_PRIORITY - PROPRITY)//图片下载优先级
                .denyCacheImageMultipleSizesInMemory()//防止缓存多套图片尺寸
                .memoryCache(new WeakMemoryCache())//弱引用
                .diskCacheSize(DISK_CACHE_SIZE)//硬盘缓存大小
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//使用md5命名缓存文件
                .tasksProcessingOrder(QueueProcessingType.LIFO)//图片下载顺序
                .defaultDisplayImageOptions(getDefaultOptions())//默认图片加载options
                .imageDownloader(new BaseImageDownloader(context, CONNECTION_TIME_OUT, READ_TIME_OUT))//设置图片下载器
                .writeDebugLogs()//debug环境下输出日志
                .build();

        ImageLoader.getInstance().init(configuration);
        mImageLoader = ImageLoader.getInstance();
    }

    /**
     * 实现默认的Options
     *
     * @return
     */
    private DisplayImageOptions getDefaultOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.xadsdk_img_error)//图片地址为空加载
                .showImageOnFail(R.drawable.xadsdk_img_error)//图片下载失败加载
                .cacheInMemory(true)//允许图片缓存
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)//解码类型
                .decodingOptions(new BitmapFactory.Options())//图片解码配置
                .build();
        return options;
    }

    /**
     * 加载图片的api
     * @param imageview
     * @param url
     * @param options
     * @param listener
     */

    public void displayImage(ImageView imageview, String url, DisplayImageOptions options,
                             ImageLoadingListener listener) {
        if (mImageLoader != null) {
            mImageLoader.displayImage(url, imageview, options, listener);
        }
    }

    public void displayImage(ImageView imageview, String url) {
        displayImage(imageview, url, null);
    }

    public void displayImage(ImageView imageview, String url, ImageLoadingListener listener) {
        displayImage(imageview, url, null, listener);
    }
}
