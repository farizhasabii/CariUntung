package com.example.fariz.cariuntung;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Fariz on 10/29/2018.
 */


public class LokasiAdapter extends BaseAdapter {

    private ArrayList<LokasiItem> mItemsList;

    // The LayoutInflater to holds layout inflater to inflate list item.
    private LayoutInflater mLayoutInflater;

    // The OnMessageClickListener of listener.
    private OnFruitItemClickListener listener;


    public LokasiAdapter(Context context, ArrayList<LokasiItem> list) {
        mItemsList = list;
        mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public void setItemlist(ArrayList<LokasiItem> list) {
        mItemsList = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        if (mItemsList != null) {
            return mItemsList.size();
        } else {
            return 0;
        }
    }
    @Override
    public LokasiItem getItem(int position) {
        LokasiItem item = null;
        if (mItemsList != null && mItemsList.size() > 0) {
            item = mItemsList.get(position);
        }
        return item;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnFruitClickListener(OnFruitItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnFruitItemClickListener {
        public void onCheckboxClicked(int position, LokasiItem item);
    }

    public ArrayList<LokasiItem> getUpdatedList() {
        return mItemsList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            ViewHolder viewHolder = new ViewHolder();
            view = mLayoutInflater.inflate(R.layout.lokasi_item_layout, parent,
                    false);
            viewHolder.header_textview = (TextView) view
                    .findViewById(R.id.header_textview);
            viewHolder.sub_textview = (TextView) view
                    .findViewById(R.id.sub_textview);
            viewHolder.profile_imageview = (ImageView) view
                    .findViewById(R.id.profile_imageview);
            viewHolder.checkbox = (CheckBox) view
                    .findViewById(R.id.checkbox_imageview);
            view.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        final LokasiItem item = getItem(position);
        if (item != null && viewHolder != null) {
            viewHolder.header_textview.setText(item.getFruitname());
            viewHolder.sub_textview.setText(item.getMessage());
            viewHolder.profile_imageview.setBackgroundResource(item
                    .getPictureResId());

            viewHolder.checkbox.setTag(position);
            viewHolder.checkbox.setChecked(item.isCheckboxChecked());
            viewHolder.checkbox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    listener.onCheckboxClicked(position, item);
                }
            });

        }
        return view;
    }


    public static class ViewHolder {
        TextView header_textview;
        TextView sub_textview;
        ImageView profile_imageview;
        CheckBox checkbox;

    }

}

