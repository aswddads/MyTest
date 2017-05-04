package tj.com.mysdk.okhttp.listener;

/**
 * Created by Jun on 17/5/4.
 */

public class DisposeDataHandle {
    public DisposeDataListener listener = null;
    public Class<?> mClass = null;

    public DisposeDataHandle(DisposeDataListener listener) {
        this.listener = listener;
    }

    public DisposeDataHandle(DisposeDataListener listener, Class<?> clazz) {
        this.listener = listener;
        this.mClass = clazz;
    }
}
