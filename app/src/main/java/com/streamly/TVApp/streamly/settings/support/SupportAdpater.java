package com.streamly.TVApp.streamly.settings.support;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.streamly.TVApp.streamly.R;

import java.util.List;


public class SupportAdpater extends RecyclerView.Adapter<SupportAdpater.MyViewHolder> {

    private Context context;
    private List<SupportTextType> list;
    private LayoutInflater inflater;
    private View view;

    public SupportAdpater() {
    }

    public SupportAdpater(Context context, List<SupportTextType> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view=inflater.inflate(R.layout.view_support,parent,false);
        SupportAdpater.MyViewHolder myViewHolder=new SupportAdpater.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvSupport.setText(list.get(position).getStringNumber());

    }

    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();

        }        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvSupport;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSupport=itemView.findViewById(R.id.tv_support);
        }
    }
}
