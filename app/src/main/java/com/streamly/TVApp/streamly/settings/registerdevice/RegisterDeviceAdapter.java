package com.streamly.TVApp.streamly.settings.registerdevice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.streamly.TVApp.streamly.R;
import java.util.List;

public class RegisterDeviceAdapter extends RecyclerView.Adapter<RegisterDeviceAdapter.MyViewHolder> {
    private Context context;
    private List<RegisterDeviceType> list;
    private LayoutInflater inflater;
    private View view;


    public RegisterDeviceAdapter(Context context, List<RegisterDeviceType> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view=inflater.inflate(R.layout.view_register_device,parent,false);
        RegisterDeviceAdapter.MyViewHolder myViewHolder=new RegisterDeviceAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.deviceModelNo.setText(list.get(position).getDeviceModel());
        holder.deviceId.setText(list.get(position).getDeviceId());

    }

    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView deviceModelNo,deviceId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceModelNo=itemView.findViewById(R.id.device_model);
            deviceId=itemView.findViewById(R.id.device_id);

        }
    }
}
