package tj.com.mytest.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tj.com.mysdk.imageloader.ImageLoaderManager;
import tj.com.mytest.R;
import tj.com.mytest.beam.RecommandBodyValue;

/**
 * Created by Jun on 17/5/8.
 */

public class HotSalePagerAdapter extends PagerAdapter {
    private Context Context;
    private LayoutInflater mInflate;

    private ArrayList<RecommandBodyValue> mDate;
    private ImageLoaderManager mImageLoader;

    public HotSalePagerAdapter(Context mContext, ArrayList<RecommandBodyValue> recommandList) {
        mContext=mContext;
        mDate=recommandList;
        mInflate=LayoutInflater.from(mContext);
        mImageLoader=ImageLoaderManager.getInstance(mContext);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final RecommandBodyValue value=mDate.get(position%mDate.size());
        View rootView=mInflate.inflate(R.layout.item_hot_product_pager_layout,null);
        TextView titleView = (TextView) rootView.findViewById(R.id.title_view);
        TextView infoView = (TextView) rootView.findViewById(R.id.info_view);
        TextView gonggaoView = (TextView) rootView.findViewById(R.id.gonggao_view);
        TextView saleView = (TextView) rootView.findViewById(R.id.sale_num_view);
        ImageView[] imageViews = new ImageView[3];
        imageViews[0] = (ImageView) rootView.findViewById(R.id.image_one);
        imageViews[1] = (ImageView) rootView.findViewById(R.id.image_two);
        imageViews[2] = (ImageView) rootView.findViewById(R.id.image_three);

        titleView.setText(value.title);
        infoView.setText(value.price);
        gonggaoView.setText(value.info);
        saleView.setText(value.text);
        for (int i = 0; i < imageViews.length; i++) {
            mImageLoader.displayImage(imageViews[i], value.url.get(i));
        }
        container.addView(rootView, 0);
        return rootView;

    }
}
