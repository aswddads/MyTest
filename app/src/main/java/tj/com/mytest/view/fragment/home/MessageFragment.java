package tj.com.mytest.view.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tj.com.mytest.R;
import tj.com.mytest.view.fragment.BaseFragment;

/**
 * Created by Jun on 17/5/4.
 */

public class MessageFragment extends BaseFragment implements View.OnClickListener{
    /**
     * UI
     */
    private View mContentView;
    private RelativeLayout mMessageLayout;
    private RelativeLayout mZanLayout;
    private RelativeLayout mImoocLayout;
    private TextView mTipView;
    private TextView mTipZanView;
    private TextView mTipMsgView;


    public MessageFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_message_layout, null, false);
        initView();
        return mContentView;
    }

    private void initView() {
        mMessageLayout = (RelativeLayout) mContentView.findViewById(R.id.message_layout);
        mImoocLayout = (RelativeLayout) mContentView.findViewById(R.id.imooc_layout);
        mZanLayout = (RelativeLayout) mContentView.findViewById(R.id.zan_layout);
        mTipView = (TextView) mContentView.findViewById(R.id.tip_view);
        mTipZanView = (TextView) mContentView.findViewById(R.id.zan_tip_view);
        mTipMsgView = (TextView) mContentView.findViewById(R.id.unread_tip_view);

        mImoocLayout.setOnClickListener(this);
        mZanLayout.setOnClickListener(this);
        mMessageLayout.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }
}
