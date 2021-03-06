package com.streamly.TVApp.streamly.epg;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.streamly.TVApp.streamly.MainActivity;
import com.streamly.TVApp.streamly.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class GenereAdapter extends RecyclerView.Adapter<GenereAdapter.MyViewHolder> {

    private Context ctx;
    private Map<String , List<Datum>> dataList;
    private List<String> keyList = new ArrayList<String>();


    public GenereAdapter(Context ctx, Map<String, List<Datum>> dataList){
        this.ctx = ctx;
        this.dataList = dataList;


        for ( String key : dataList.keySet() ) {
            keyList.add(key);
        }

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.genere_item, null);
        MyViewHolder mh = new MyViewHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.textView.setText(keyList.get(position));

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ctx instanceof MainActivity) {
                    ((MainActivity)ctx).fragment(keyList.get(position) , dataList.get(keyList.get(position)));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return keyList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.textView = itemView.findViewById(R.id.text_genere);
            textView.setFocusable(true);
            textView.setFocusableInTouchMode(true);
        }
    }

}
