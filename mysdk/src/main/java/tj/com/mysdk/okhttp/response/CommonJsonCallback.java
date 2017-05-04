package tj.com.mysdk.okhttp.response;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import tj.com.mysdk.okhttp.exception.OkHttpException;
import tj.com.mysdk.okhttp.listener.DisposeDataHandle;
import tj.com.mysdk.okhttp.listener.DisposeDataListener;

/**
 * Created by Jun on 17/5/4.
 */

public class CommonJsonCallback implements Callback {
    //与服务器返回字段对应
    protected final String RESULT_CODE = "ecode";//请求成功
    protected final int RESULT_CODE_VALUE = 0;
    protected final String ERROR_MSG = "emsg";
    protected final String EMPTY_MSG = "";

    /**
     * 定义的异常
     */
    protected final int NETWORK_ERROR = -1;
    protected final int JSON_ERROR = -2;//error
    protected final int OTHER_ERROR = -3;

    private Handler mDeliveryHandler;
    private DisposeDataListener mListener;
    private Class<?> mClass;


    public CommonJsonCallback(DisposeDataHandle handle) {
        this.mListener = handle.listener;
        this.mClass = handle.mClass;
        this.mDeliveryHandler = new Handler(Looper.myLooper());
    }

    /**
     * 请求失败处理
     *
     * @param call
     * @param e
     */
    @Override
    public void onFailure(Call call, final IOException e) {
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(new OkHttpException(NETWORK_ERROR, e));
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                handleResponse(result);
            }
        });
    }

    /**
     * 处理服务器返回的响应数据
     *
     * @param result
     */
    private void handleResponse(Object result) {
        //保证代码的健壮性
        if (result == null && result.toString().trim().equals("")) {
            mListener.onFailure(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
            return;
        }
        try {
            JSONObject res=new JSONObject(result.toString());
            if (res.has(RESULT_CODE)) {
                //从json对象取出响应码
                if (res.getInt(RESULT_CODE) == RESULT_CODE_VALUE) {
                    if (mClass == null){
                        mListener.onSuccess(result);
                    } else {
                        //需要将json对象转换为实体对象
                        Gson gson=new Gson();
                        Object obj=gson.fromJson(String.valueOf(res),mClass);
//                        正确的转化为了实体类
                        if (obj != null) {
                            mListener.onSuccess(obj);
                        } else {
                            //不是合法的json
                            mListener.onFailure(new OkHttpException(JSON_ERROR,EMPTY_MSG));
                        }
                    }
                } else {
                    //将服务器返回的异常回调到应用层
                    mListener.onFailure(new OkHttpException(OTHER_ERROR,res.get(RESULT_CODE)));
                }
            }
        } catch (Exception e) {
            mListener.onFailure(new OkHttpException(OTHER_ERROR,e.getMessage()));
        }
    }
}
