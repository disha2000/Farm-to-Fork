package com.example.hp.zermome;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    private ArrayList<product> dataList;

    public ProductAdapter(ArrayList<product> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_info, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.format_info.setText(dataList.get(position).getFormat());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {


        TextView format_info;

       ProductViewHolder(View itemView) {
            super(itemView);
            format_info=(TextView) itemView.findViewById(R.id.format);


        }
    }
}