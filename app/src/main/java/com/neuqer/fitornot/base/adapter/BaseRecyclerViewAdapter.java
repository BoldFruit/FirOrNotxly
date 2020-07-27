package com.neuqer.fitornot.base.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.List;

/**
 * @author YangCihang
 * @since 17/10/9.
 * email yangcihang@hrsoft.net
 */

public abstract class BaseRecyclerViewAdapter<Data> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<Data> mDataList;
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected int mItemLayoutId;
    private OnItemClicked<Data> mOnItemClickedListener;

    public BaseRecyclerViewAdapter(Context context, List<Data> dataList, @LayoutRes int itemLayoutId) {
        mContext = context;
        mDataList = dataList;
        mInflater = LayoutInflater.from(context);
        mItemLayoutId = itemLayoutId;
    }

    /**
     * 设置数据
     *
     * @param data 数据
     */
    public void setData(Collection<Data> data) {
        mDataList.clear();
        mDataList.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 添加一条数据
     *
     * @param data 数据
     */
    public void add(Data data) {
        mDataList.add(data);
        notifyDataSetChanged();
    }

    /**
     * 添加多条数据
     *
     * @param collection 数据
     */
    public void addAll(Collection<Data> collection) {
        mDataList.addAll(collection);
        notifyDataSetChanged();
    }

    /**
     * 移除数据
     *
     * @param data 移除的数据
     */
    public void remove(Data data) {
        mDataList.remove(data);
        notifyDataSetChanged();
    }

    /**
     * 移除数据（带动画）
     *
     * @param position pos
     */
    public void remove(int position) {
        mDataList.remove(position);
        //改方法不会使position及其之后的itemView重新onBindViewHodler
        notifyItemRemoved(position);
        //此方法使从position到列表末尾进行数据刷新
        notifyItemRangeChanged(position, mDataList.size());
    }

    /**
     * 清空列表
     */
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 刷新页面
     */
    public void refresh() {
        notifyDataSetChanged();
    }

    public void refresh(int position) {
        notifyItemChanged(position);
    }

    /**
     * 获取当前列表的数据
     *
     */
    public List<Data> getListData() {
        return mDataList;
    }


    /**
     * 获取position 处数据
     */
    public Data getItem(int position) {
        return mDataList.get(position);
    }

    public BaseViewHolder onCreateViewHodler(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(mItemLayoutId, parent, false);
        return new BaseViewHolder(mContext, view);
    }


    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        //ViewHolder绑定数据
        Data data = mDataList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickedListener != null) {
                    mOnItemClickedListener.onItemClicked(view, mDataList.get(position), position);
                }
            }

        });
        bind(holder, data);
    }


    @Override
    public int getItemCount() {
        return mDataList == null?  0 : mDataList.size();
    }

    /**
     * 设置点击事件监听
     */
    public void setOnItemClickedListener(OnItemClicked<Data> onItemClickedListener) {
        mOnItemClickedListener = onItemClickedListener;
    }


    /**
     * 点击事件接口
     */
    public interface OnItemClicked<Data> {
        void onItemClicked(View view, Data data, int position);
    }


    //绑定数据
    protected abstract void bind(BaseViewHolder holder, Data data);

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(mItemLayoutId, parent, false);
        return new BaseViewHolder(mContext, view);
    }

}
