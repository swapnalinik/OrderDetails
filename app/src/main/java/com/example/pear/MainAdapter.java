package com.example.pear;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> implements Filterable {

    private ArrayList<MainData> dataArrayList;
    private Activity activity;
    private List<MainData> filteroutput;

    public MainAdapter(Activity activity, ArrayList<MainData> dataArrayList){
        this.activity = activity;
        this.dataArrayList=dataArrayList;
        this.filteroutput=dataArrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainData data = dataArrayList.get(position);
        Glide.with(activity).load(data.getRestaurant_image()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
        holder.txt_time.setText(data.getTimestamp());
        holder.txt_loc.setText(data.getRestaurant_location());
        holder.txt_amt.setText(String.valueOf(data.getGrand_total()));
        holder.txt_name.setText(data.getRestaurant_name());
        holder.txt_qua.setText(data.getTotal_info());
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter=new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults=new FilterResults();
                if(constraint == null | constraint.length() == 0){
                    filterResults.count=filteroutput.size();
                    filterResults.values=filteroutput;
                }else {
                    String searchchar=constraint.toString().toLowerCase();
                    List<MainData> resultData=new ArrayList<>();
                    for(MainData mainData: filteroutput){
                        if(mainData.getRestaurant_image().toLowerCase().contains(searchchar)){
                            resultData.add(mainData);
                        }
                    }
                    filterResults.count=resultData.size();
                    filterResults.values=resultData;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataArrayList= (ArrayList<MainData>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txt_name,txt_loc,txt_time,txt_amt,txt_qua;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            txt_name =itemView.findViewById(R.id.rest_name);
            txt_amt =itemView.findViewById(R.id.s3_value);
            txt_loc =itemView.findViewById(R.id.rest_loc);
            txt_time =itemView.findViewById(R.id.s2_value);
            txt_qua = itemView.findViewById(R.id.s1_value);
        }
    }
}
