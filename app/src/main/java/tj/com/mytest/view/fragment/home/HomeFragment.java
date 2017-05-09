package tj.com.mytest.view.fragment.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.utils.L;

import tj.com.mysdk.okhttp.listener.DisposeDataListener;
import tj.com.mytest.R;
import tj.com.mytest.adapter.CourseAdapter;
import tj.com.mytest.beam.BaseRecommandModel;
import tj.com.mytest.network.RequestCenter;
import tj.com.mytest.view.fragment.BaseFragment;
import tj.com.mytest.view.home.HomeHeaderLayout;
import tj.com.mytest.zxing.app.CaptureActivity;

/**
 * Created by Jun on 17/5/4.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final int REQUEST_QRCODE = 0x01;
    /**
     * UI
     */
    private View mContentView;
    private ListView mListView;
    private TextView mQRCodeView;
    private TextView mCategoryView;
    private TextView mSearchView;
    private ImageView mLoadingView;

    /**
     * data
     */
    private CourseAdapter mAdapter;
    private BaseRecommandModel mRecommandData;

    public HomeFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestRecommanndData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mContentView = inflater.inflate(R.layout.fragment_home_layout, container, false);
        initView();
        return mContentView;
    }

    private void initView() {
        mQRCodeView = (TextView) mContentView.findViewById(R.id.qrcode_view);
        mQRCodeView.setOnClickListener(this);
        mCategoryView = (TextView) mContentView.findViewById(R.id.category_view);
        mCategoryView.setOnClickListener(this);
        mSearchView = (TextView) mContentView.findViewById(R.id.search_view);
        mSearchView.setOnClickListener(this);
        mListView = (ListView) mContentView.findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);
        mLoadingView = (ImageView) mContentView.findViewById(R.id.loading_view);
        AnimationDrawable anim = (AnimationDrawable) mLoadingView.getDrawable();
        anim.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.qrcode_view:
                Intent intent=new Intent(mContext, CaptureActivity.class);
                startActivityForResult(intent,REQUEST_QRCODE);
                break;
            case R.id.category_view:
                break;
            case R.id.search_view:
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            //扫码返回
            case REQUEST_QRCODE:
                if (resultCode == Activity.RESULT_OK) {
                    String code = data.getStringExtra("SCAN_RESULT");
                    if (code.contains("http") || code.contains("https")) {
//                        Intent intent = new Intent(mContext, AdBrowserActivity.class);
//                        intent.putExtra(AdBrowserActivity.KEY_URL, code);
//                        startActivity(intent);
                        Toast.makeText(mContext, code, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, code, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * 发送首页列表数据请求
     */
    private void requestRecommanndData() {
        RequestCenter.requestRecommandData(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {

                //获取到数据后更新ui
                mRecommandData = (BaseRecommandModel) responseObj;
                L.i("message",responseObj.toString());
                showSuccessView();
            }

            @Override
            public void onFailure(Object reasonObj) {//提示用户

            }
        });
    }

    /**
     * 请求成功执行的方法
     */
    private void showSuccessView() {
        //为了程序的健壮性  判空处理
        if (mRecommandData.data.list != null && mRecommandData.data.list.size() > 0){
            mLoadingView.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);

            mListView.addHeaderView(new HomeHeaderLayout(mContext,mRecommandData.data.head));
            //创建adapter
            mAdapter = new CourseAdapter(mContext,mRecommandData.data.list);
            mListView.setAdapter(mAdapter);
        }else {
            showErrorView();
        }
    }

    private void showErrorView() {
    }
}
