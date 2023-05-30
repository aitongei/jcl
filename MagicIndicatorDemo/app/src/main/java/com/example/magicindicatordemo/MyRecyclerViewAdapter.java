package com.example.magicindicatordemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public  class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int HEADER_ITEM = 0;
    public static final int ONE_ITEM = 1;
    public static final int TWO_ITEM = 2;

    private Context mContext;
    private Fragment curFragment; //不同Fragment布局方式不同，用以区分

    List<String> mdatas;

    public MyRecyclerViewAdapter(List<String > mdatas, Fragment curFragment) {
        this.mdatas = mdatas;
        this.curFragment = curFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        if (HEADER_ITEM == viewType) {
            View v = mInflater.inflate(R.layout.header_image, parent, false);
            return new HeaderViewHolder(v);
        }
        else if (ONE_ITEM == viewType || curFragment instanceof FragmentThree) {
            View v = mInflater.inflate(R.layout.item_single_image, parent, false);
            return new OneViewHolder(v);
        } else {
            View v = mInflater.inflate(R.layout.item_double_image, parent, false);
            return new TwoViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String url;
        if (holder instanceof OneViewHolder) {
            OneViewHolder mHolder = ((OneViewHolder) holder);
            if (curFragment instanceof FragmentOne) {  //FragmentOne
                url = "https://th.bing.com/th/id/R.db3836610f631c4d06bdde4fd923e98f?rik=qaf5de1wCMNaRw&riu=http%3a%2f%2fseopic.699pic.com%2fphoto%2f50093%2f5337.jpg_wh1200.jpg&ehk=Oe3ZIbrBYnhQ0zqIu%2ftsRSE8srtaRlewEtSIg3sp6Zw%3d&risl=&pid=ImgRaw&r=0";
            }else {  //FragmentThree
                if (position % 2 == 0) {  //FragmentThree中的图片交替显示
                    url = "https://tse1-mm.cn.bing.net/th/id/OIP-C.f6R4h0pVEZf4g3XWj-0vwgHaE7?w=279&h=186&c=7&r=0&o=5&dpr=1.5&pid=1.7";
                }else {
                    url = "https://tse1-mm.cn.bing.net/th/id/OIP-C.f87GPYLmIk30DpqztAbMVQHaFj?w=248&h=186&c=7&r=0&o=5&dpr=1.5&pid=1.7";
                }
            }
            loadImage(url,mHolder.iv);
        } else if(holder instanceof TwoViewHolder){
            TwoViewHolder mHolder = ((TwoViewHolder) holder);
            url = "https://th.bing.com/th/id/OIP.Om1s0N91Jbx3NpJgPyePygHaEK?w=188&h=106&c=7&r=0&o=5&dpr=1.5&pid=1.7";
            loadImage(url,mHolder.iv1);
            url = "https://th.bing.com/th/id/OIP.jIlzeYCjr5yP2aczQALnoAHaEo?w=188&h=117&c=7&r=0&o=5&dpr=1.5&pid=1.7";
            loadImage(url,mHolder.iv2);
//            mHolder.iv1.setImageResource(R.drawable.img1);        //  本地图片
//            mHolder.iv2.setImageResource(R.drawable.img2);
//            ((TwoViewHolder) holder).tv2.setText(mDatas.get(position));

        }


    }

    private void loadImage(String url, ImageView iv) {
        Glide.with(mContext)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(80)))
//                .transform(new RoundedCorners(80))    //两句都能实现圆角图片
                .into(iv);
    }

//    @Override
//    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
//        super.onViewRecycled(holder);
//    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return HEADER_ITEM;
        }
        if (position % 2 == 0) {
            return TWO_ITEM;
        } else {
            return ONE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mdatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class HeaderViewHolder extends ViewHolder {
        ImageView iv;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.header_image);
        }
    }

    public static class OneViewHolder extends ViewHolder {
        ImageView iv;

        public OneViewHolder(View itemView) {
            super(itemView);
            iv =  itemView.findViewById(R.id.single);
        }
    }

    public static class TwoViewHolder extends ViewHolder {
        ImageView iv1, iv2;

        public TwoViewHolder(View itemView) {
            super(itemView);
            iv1 = itemView.findViewById(R.id.double_1);
            iv2 = itemView.findViewById(R.id.double_2);
        }
    }
}
