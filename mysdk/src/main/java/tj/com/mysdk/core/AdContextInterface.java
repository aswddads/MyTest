package tj.com.mysdk.core;

/**
 * @function: 最终通知应用层广告是否成功
 */
public interface AdContextInterface {

    void onAdSuccess();

    void onAdFailed();

    void onClickVideo(String url);
}
