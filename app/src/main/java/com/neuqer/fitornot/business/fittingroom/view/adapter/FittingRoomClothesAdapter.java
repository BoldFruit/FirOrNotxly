package com.neuqer.fitornot.business.fittingroom.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.neuqer.fitornot.R;
import com.neuqer.fitornot.base.adapter.BaseRecyclerViewAdapter;
import com.neuqer.fitornot.base.adapter.BaseViewHolder;
import com.neuqer.fitornot.business.clothes.modle.response.GetClothesRspModle;
import com.neuqer.fitornot.business.fittingroom.model.response.GetClothesRspModel;
import com.neuqer.fitornot.util.Loader.ImageLoader;
import com.neuqer.fitornot.util.LogUtil;

import java.util.List;

/**
 * @author DuLong
 * @since 2019/9/28
 * email 798382030@qq.com
 */

public class FittingRoomClothesAdapter extends BaseRecyclerViewAdapter<GetClothesRspModle.DataEntity> {
    private ImageLoader mLoader;
    private int width;
    private int height;
    public FittingRoomClothesAdapter(Context context, List<GetClothesRspModle.DataEntity> dataList, int itemLayoutId) {
        super(context, dataList, itemLayoutId);
        mLoader = ImageLoader.build(context);

    }

    @Override
    protected void bind(BaseViewHolder holder, GetClothesRspModle.DataEntity mDataEntity) {
        String imgUrl = mDataEntity.getPic_url();
        LogUtil.e("url", imgUrl.toString());
        mLoader.bindBitmap(imgUrl, holder.getmItemView().findViewById(R.id.img_cloth_fitting_room), width, height);
        List<GetClothesRspModle.DataEntity.TagsEntity> tags = mDataEntity.getTags();
        if (!tags.isEmpty()) {
            int[] number = new int[] {0, 0, 0};
            for (GetClothesRspModle.DataEntity.TagsEntity tag : tags) {
                if (tag.getTag_type().equals("场景") && number[0] == 0) {
                    number[0]++;
                    holder.setText(R.id.txt_lable_fit_location, tag.getTag_name());
                    holder.setVisible(R.id.txt_lable_fit_location, true);
                } else if (tag.getTag_type().equals("风格") && number[1] == 0) {
                    number[1]++;
                    holder.setText(R.id.txt_lable_style, tag.getTag_name());
                    holder.setVisible(R.id.txt_lable_style, true);
                } else if (tag.getTag_type().equals("季节") && number[2] == 0) {
                    number[2]++;
                    holder.setText(R.id.txt_lable_season, tag.getTag_name());
                    holder.setVisible(R.id.txt_lable_season, true);
                }
            }
        }
    }

    @NonNull
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //显示3*3个装饰
        View view = mInflater.inflate(mItemLayoutId, parent, false);
        int parentHeight = parent.getHeight();
        width = parent.getWidth() / 3;
        height =(int) (parentHeight / 3.7);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = (int) (parentHeight / 3.7);
        return new BaseViewHolder(mContext, view);
    }
}
