package com.example.hp.zermome.Fragement;




import android.icu.text.TimeZoneFormat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.zermome.R;

import java.util.ArrayList;

public class profileAdapter extends RecyclerView.Adapter<profileAdapter.EmployeeViewHolder> {

    private ArrayList<Profile> dataList;

    public profileAdapter(ArrayList<Profile> dataList) {
        this.dataList = dataList;
    }

    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.profile_info, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {
        holder.txtkey.setText(dataList.get(position).getKey());
        holder.txtvalue.setText(dataList.get(position).getValue());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {

        TextView txtkey,txtvalue;

        EmployeeViewHolder(View itemView) {
            super(itemView);
            txtkey = (TextView) itemView.findViewById(R.id.key);
            txtvalue= (TextView) itemView.findViewById(R.id.value);

        }
    }
}