package com.example.magicindicatordemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class MyListViewAdapter extends BaseAdapter {

//    private static final int ITEM_HEAD = 0;
    private static final int ITEM_ONE = 0;
    private static final int ITEM_TWO = 1;
    private final List<String> itemList;
    private final Context mContext;

    public MyListViewAdapter(List<String> itemList, Context context) {
        this.itemList = itemList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 返回布局类型的总数
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    // 返回项目的布局类型标识符
    @Override
    public int getItemViewType(int position) {
//        if (position == 0) {
//            return ITEM_HEAD;
//        }
        if (position % 2 == 0){
            return ITEM_TWO;
        }else {
            return ITEM_ONE;
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OneViewHolder holder1 = null;
        TwoViewHolder holder2 = null;

        int viewType = getItemViewType(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            switch (viewType) {
//                case 0:
//                    convertView = inflater.inflate(R.layout., parent, false);
//                    holder1 = new OneViewHolder();
//                    holder1.iv = convertView.findViewById(R.id.header_image);
//                    convertView.setTag(holder1);
//                    break;
                case 0:
                    convertView = inflater.inflate(R.layout.item_single_image, parent, false);
                    holder1 = new OneViewHolder();
                    holder1.iv = convertView.findViewById(R.id.single);
                    convertView.setTag(holder1);
                    break;

                case 1:
                    convertView = inflater.inflate(R.layout.item_double_image, parent, false);
                    holder2 = new TwoViewHolder();
                    holder2.iv1 = convertView.findViewById(R.id.double_1);
                    holder2.iv2 = convertView.findViewById(R.id.double_2);
                    convertView.setTag(holder2);
                    break;
            }
        } else {
            switch (viewType) {
                case 0:
                    holder1 = (OneViewHolder) convertView.getTag();
                    break;

                case 1:
                    holder2 = (TwoViewHolder) convertView.getTag();
                    break;
            }
        }

        // 绑定数据到相应的布局中
//        String item = itemList.get(position);
        String url;
        switch (viewType) {
            case 0:
                url="https://th.bing.com/th/id/R.dbX`zz3836610f631c4d06bdde4fd923e98f?rik=qaf5de1wCMNaRw&riu=http%3a%2f%2fseopic.699pic.com%2fphoto%2f50093%2f5337.jpg_wh1200.jpg&ehk=Oe3ZIbrBYnhQ0zqIu%2ftsRSE8srtaRlewEtSIg3sp6Zw%3d&risl=&pid=ImgRaw&r=0";
                loadImage(url, holder1.iv);
                break;

            case 1:
                url="https://tse3-mm.cn.bing.net/th/id/OIP-C.emVs9f8_SJl2Pp0Tsw3zmQHaE7?w=271&h=181&c=7&r=0&o=5&dpr=1.5&pid=1.7";
                loadImage(url, holder2.iv1);
                url="https://tse2-mm.cn.bing.net/th/id/OIP-C.ow-bcHAXHQoayakbq0NLOQHaFB?w=268&h=181&c=7&r=0&o=5&dpr=1.5&pid=1.7";
                loadImage(url, holder2.iv2);
                break;
        }
        return convertView;
    }

    static class OneViewHolder {
        ImageView iv;
    }

    static class TwoViewHolder {
        ImageView iv1;
        ImageView iv2;
    }

    private void loadImage(String url, ImageView iv) {
        Glide.with(mContext)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(80)))
//                .transform(new RoundedCorners(80))    //两句都能实现圆角图片
                .into(iv);
    }
}
