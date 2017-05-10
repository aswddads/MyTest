package tj.com.mysdk.okhttp;


import tj.com.mysdk.module.AdInstance;
import tj.com.mysdk.okhttp.listener.DisposeDataHandle;
import tj.com.mysdk.okhttp.listener.DisposeDataListener;
import tj.com.mysdk.okhttp.request.CommonRequest;

/**
 * Created by renzhiqiang on 16/10/27.
 *
 * @function sdk请求发送中心
 */
public class RequestCenter {

    /**
     * 发送广告请求
     */
    public static void sendImageAdRequest(String url, DisposeDataListener listener) {

        CommonOkHttpClient.post(CommonRequest.createPostRequest(url, null),
                new DisposeDataHandle(listener, AdInstance.class));
    }
}
