package com.smart.school.dialog.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.smart.school.R;
import com.smart.school.adapter.item.CommItem;

import java.util.List;

/**
 * Created by user01 on 2015-07-22.
 */
public class CDialogAdapter extends ArrayAdapter<CommItem>{

    private Context mContext;

    private int RES_ID_LAYOUT;

    private List<CommItem> mList;

    public CDialogAdapter(Context context, int resource, List<CommItem> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.RES_ID_LAYOUT = resource;
        this.mList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        CommItem item = mList.get(position);

        View v = convertView;
        ViewHolder holder;
        if(v == null){
            v = View.inflate(mContext, RES_ID_LAYOUT, null);
            holder = new ViewHolder();
            holder.mTv_Name = v.findViewById(R.id.tv_list_name);
            v.setTag(holder);
        }else{
            holder = (ViewHolder)v.getTag();
        }
        if(item != null){
            holder.mTv_Name.setText(item.getName());
        }
        return v;
    }

    public class ViewHolder{
        public TextView mTv_Name;
    }

}
