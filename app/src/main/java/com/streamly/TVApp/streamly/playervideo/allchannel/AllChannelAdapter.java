
package com.streamly.TVApp.streamly.playervideo.allchannel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.streamly.TVApp.streamly.MainActivity;
import com.streamly.TVApp.streamly.QRCode.QRCodeActivity;
import com.streamly.TVApp.streamly.R;
import com.streamly.TVApp.streamly.channelschedule.Datum;
import com.streamly.TVApp.streamly.playervideo.PlayerActivity;

import java.util.List;

public class AllChannelAdapter extends RecyclerView.Adapter<AllChannelAdapter.MyViewHolder> {

    private List<com.streamly.TVApp.streamly.epg.Datum> channelList;
    private Context ctx;

    public AllChannelAdapter(Context ctx, List<com.streamly.TVApp.streamly.epg.Datum> channelList){
        this.ctx = ctx;
        this.channelList = channelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.player_all_channel_row , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.textView.setText(position+1 + "");
        Glide.with(ctx).load(channelList.get(position).getLogo()).into(holder.imageView);

        holder.itemView.setOnClickListener(view -> {

            Log.e("playerac" , "done");
            if (ctx instanceof PlayerActivity) {
                ((PlayerActivity)ctx).playNext(channelList.get(position).getId().toString() , channelList.get(position).getUrl());
            }
            else{
                Log.e("playerac" , "no");

            }

        });

    }

    @Override
    public int getItemCount() {
        if(channelList!=null)
        return channelList.size();

        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;
        LinearLayout card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.player_channel_text);
            imageView = itemView.findViewById(R.id.player_channel_image);
            card = itemView.findViewById(R.id.card);

        }
    }

}
