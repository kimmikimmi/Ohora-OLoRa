package co.ohlora.fireapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import co.ohlora.fireapplication.models.Sensors;

/**
 * Created by Administrator on 2016-08-01.
 */
public class SensorsAdapter extends RecyclerView.Adapter<SensorsAdapter.MyViewHolder>{

    private Context mContext;
    private List<Sensors> sensorsList;
    private List<String> homesList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView home, fire, image1, image2, temperature, member, smoke;
        public LinearLayout member_section;
        public RelativeLayout card_section;
        public ImageView img_status;

        public MyViewHolder(View view){
            super(view);

            img_status = (ImageView) view.findViewById(R.id.img_status);

            home = (TextView) view.findViewById(R.id.text_home);
            //fire = (TextView) view.findViewById(R.id.txt_fire);
            //image1 = (TextView) view.findViewById(R.id.txt_image1);
            //image2 = (TextView) view.findViewById(R.id.txt_image2);
            //temperature = (TextView) view.findViewById(R.id.txt_temperature);
            member = (TextView) view.findViewById(R.id.txt_member);
            //smoke = (TextView) view.findViewById(R.id.txt_smoke);

            card_section = (RelativeLayout) view.findViewById(R.id.card_section);
        }
    }

    public SensorsAdapter(Context context, List<Sensors> sensorsList, List<String> homesList){
        this.mContext = context;
        this.sensorsList = sensorsList;
        this.homesList = homesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sensor_card,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position){
        final Sensors sensors = sensorsList.get(position);
        String home = homesList.get(position);
        holder.home.setText(home + "호");
        //holder.fire.setText(sensors.getFire());
        //holder.image1.setText(sensors.getImage1());
        //holder.image2.setText(sensors.getImage2());
        //holder.temperature.setText(sensors.getTemperature());
        holder.member.setText(sensors.getMember());
        //holder.smoke.setText(sensors.getSmoke());
        if(sensors.getFire().equals("yes")){
            holder.img_status.setImageResource(R.drawable.fire);
        } else {
            if(Integer.parseInt(sensors.getSmoke()) > 20){
                holder.img_status.setImageResource(R.drawable.smoke);
            } else {
                holder.img_status.setBackgroundColor(Color.WHITE);
            }

        }

        holder.card_section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(sensors.getFire().equals("no")){
                    Toast.makeText(mContext,"개인정보 보호를 위해 불이 난 장소의 정보만 제공합니다.",Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(mContext,sensors.getMember(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, MoreInfoActivity.class);
                    intent.putExtra("member",sensors.getMember());
                    intent.putExtra("smoke",sensors.getSmoke());
                    intent.putExtra("img1",sensors.getImage1());
                    intent.putExtra("co",sensors.getCo());
                    intent.putExtra("temperature",sensors.getTemperature());
                    intent.putExtra("fire",sensors.getFire());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount(){
        return sensorsList.size();
    }

}