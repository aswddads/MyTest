package tj.com.mytest.network;

import tj.com.mysdk.okhttp.CommonOkHttpClient;
import tj.com.mysdk.okhttp.listener.DisposeDataHandle;
import tj.com.mysdk.okhttp.listener.DisposeDataListener;
import tj.com.mysdk.okhttp.request.CommonRequest;
import tj.com.mysdk.okhttp.request.RequestParams;
import tj.com.mytest.beam.BaseRecommandModel;

/**
 * Created by Jun on 17/5/7.
 * 存放所有请求
 */

public class RequestCenter {
    //根据参数发送所有的post请求
    private static void postRequest(String url, RequestParams params, DisposeDataListener listener,Class<?> clazz){
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url,params),new DisposeDataHandle(listener,clazz));
    }

    /**
     * 发送首页请求
     */
    public static void requestRecommandData(DisposeDataListener listener){
        RequestCenter.postRequest(HttpConstants.HOME_RECOMMAND,null,listener,BaseRecommandModel.class);
    }
}
