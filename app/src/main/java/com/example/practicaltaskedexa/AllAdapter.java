package com.example.practicaltaskedexa;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AllAdapter extends RecyclerView.Adapter<AllAdapter.MyViewHolder> implements Filterable {

    private LayoutInflater inflater;
    private ArrayList<DataModel> dataModelArrayList,dataModelArrayListFilterd;
    public Context context;

    public AllAdapter(Context ctx, ArrayList<DataModel> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
        this.dataModelArrayListFilterd=dataModelArrayList;
        this.context=ctx;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_rv, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        DataModel dataModel = dataModelArrayListFilterd.get(position);
        holder.first_name.setText(dataModel.getFirst_name());
        holder.last_name.setText(dataModel.getLast_name());
        holder.city.setText(dataModel.getCity());
        holder.itemView.setBackgroundColor(dataModel.isSelected? Color.CYAN:Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return dataModelArrayListFilterd.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    dataModelArrayListFilterd = dataModelArrayList;
                } else {
                    ArrayList<DataModel> filteredList = new ArrayList<>();
                    for (DataModel row : dataModelArrayList) {
                        if (row.getFirst_name().toLowerCase().contains(charString.toLowerCase()) || row.getLast_name().toLowerCase().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    dataModelArrayListFilterd = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataModelArrayListFilterd;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataModelArrayListFilterd = (ArrayList<DataModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView first_name,last_name,city;

        public MyViewHolder(View itemView) {
            super(itemView);

            first_name = itemView.findViewById(R.id.tv_first_name);
            last_name = itemView.findViewById(R.id.tv_last_name);
            city = itemView.findViewById(R.id.tv_city);

        }

    }
}