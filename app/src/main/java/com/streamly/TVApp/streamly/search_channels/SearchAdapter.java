package com.streamly.TVApp.streamly.search_channels;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.streamly.TVApp.streamly.R;
import com.streamly.TVApp.streamly.playervideo.PlayerActivity;
import com.streamly.TVApp.streamly.utilities.CommonUtility;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<SearchModel> list;
    private List<SearchModel> listFull;
    private View view;
    private SearchActivity searchActivity;


    public SearchAdapter() {
    }

    public SearchAdapter(Context context, List<SearchModel> list, SearchActivity searchActivity) {
        this.context = context;
        this.list = list;
        listFull = new ArrayList<>(list);
        this.searchActivity = searchActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.view_search, parent, false);
        SearchAdapter.MyViewHolder viewHolder = new SearchAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getLogoUrl()).into(holder.channelLogo);
        holder.channelName.setText(list.get(position).channelTitle);
        holder.channelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("channel_count", list.get(position).getVideoUrl() + "");
                context.startActivity(new Intent(context, PlayerActivity.class)
                        .putExtra("from", list.get(position).getVideoUrl()));
            }
        });


    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;

    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    //#neovifyissueresolved28sep
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<SearchModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                try {
                    filteredList.addAll(listFull);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (SearchModel item : listFull) {
                        if (item.getChannelTitle().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
//                            searchActivity.hideEmptyView();
                        } else {
                            Log.e("SearchAdapter", "::>>>no data found...");
                            CommonUtility.showToast(context, context.getString(R.string.no_data_found));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView channelLogo;
        TextView channelName;
        LinearLayout channelLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            channelLayout = itemView.findViewById(R.id.channel_layout);
            channelLogo = itemView.findViewById(R.id.channel_logo);
            channelName = itemView.findViewById(R.id.channel_name);
        }
    }
}
