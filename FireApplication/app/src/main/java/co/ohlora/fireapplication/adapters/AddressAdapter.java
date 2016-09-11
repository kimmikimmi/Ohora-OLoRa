package co.ohlora.fireapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import co.ohlora.fireapplication.MoreInfoActivity;
import co.ohlora.fireapplication.R;
import co.ohlora.fireapplication.SubActivity;
import co.ohlora.fireapplication.models.Sensors;

/**
 * Created by Administrator on 2016-08-01.
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder>{

    private Context mContext;
    private List<String> addressList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txt_address, txt_name_house, txt_distance, txt_remain_time;
        public ImageView img_house;
        public LinearLayout card_section;

        public MyViewHolder(View view){
            super(view);

            System.out.println("@@@@@@@@@myviewholder!!");

            txt_address = (TextView) view.findViewById(R.id.text_address);
            img_house = (ImageView) view.findViewById(R.id.img_house);
            txt_name_house = (TextView) view.findViewById(R.id.txt_name_house);
            txt_distance = (TextView) view.findViewById(R.id.txt_distance);
            txt_remain_time = (TextView) view.findViewById(R.id.txt_remain_time);
            //smoke = (TextView) view.findViewById(R.id.txt_smoke);

            card_section = (LinearLayout) view.findViewById(R.id.address_card_section);
        }
    }

    public AddressAdapter(Context context, List<String> addressList){
        this.mContext = context;
        this.addressList = addressList;
        System.out.println("@@@@@@@@@constructed!!");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_card,parent,false);
        System.out.println("@@@@@@@@@created holder!!");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position){
        System.out.println("@@@@@@@@@bind view!!!!!!!");
        Toast.makeText(mContext,"화재발생!!",Toast.LENGTH_SHORT).show();
        final String address = addressList.get(position);
        holder.txt_address.setText(address);
        holder.txt_name_house.setText(address.split("동")[1]);

        if(address.split("동")[1].equals("송도더샵퍼스트월드")){
            holder.img_house.setImageResource(R.drawable.img_home1);
            holder.txt_remain_time.setText("2m");
            holder.txt_distance.setText("0.5km");
        } else {
            holder.img_house.setImageResource(R.drawable.img_home2);
            holder.txt_remain_time.setText("1m");
            holder.txt_distance.setText("0.3km");
        }

        holder.card_section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext,address,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, SubActivity.class);
                intent.putExtra("address",address);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount(){
        System.out.println("@@@@@@@@@return count!!" + addressList.size());
        return addressList.size();

    }

}
