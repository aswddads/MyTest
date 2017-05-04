package tj.com.mysdk.okhttp;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import tj.com.mysdk.okhttp.https.HttpsUtils;
import tj.com.mysdk.okhttp.response.CommonJsonCallback;

/**
 * Created by Jun on 17/5/4.
 * 请求的发送，请求参数配置，https支持
 */

public class CommonOkhttpClient {
    private static final int TIME_OUT = 30;//超时参数
    private static OkHttpClient mOkhttpClient;

    //    配置参数
    static {
        //创建client的构建者
        OkHttpClient.Builder okHttpBuilder=new OkHttpClient.Builder();
//        为构建着填充超时时间
        okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(TIME_OUT,TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(TIME_OUT,TimeUnit.SECONDS);

        okHttpBuilder.followRedirects(true);//允许请求重定向

//        添加https支持  客户端默认支持所有的证书
        okHttpBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;           }
        });
        okHttpBuilder.sslSocketFactory(HttpsUtils.getSslSocketFactory());

//        生成client对象
        mOkhttpClient=okHttpBuilder.build();
    }

    /**
     * 发送具体http、https请求
     * @param request
     * @param commCallback
     * @return
     */
    public static Call sendRequest(Request request, CommonJsonCallback commCallback){
        Call call=mOkhttpClient.newCall(request);
        call.enqueue(commCallback);

        return call;
    }
}
