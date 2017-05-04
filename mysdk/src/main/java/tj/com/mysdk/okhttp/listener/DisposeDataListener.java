package tj.com.mysdk.okhttp.listener;

/**
 * Created by Jun on 17/5/4.
 * 自定义事件监听
 */

public interface DisposeDataListener {
    /**
     * 请求成功回调处理事件
     * @param responseObj
     */
    public void onSuccess(Object responseObj);

    /**
     * 请求失败回调事件
     * @param reasonObj
     */
    public void onFailure(Object reasonObj);
}
