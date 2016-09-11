package co.ohlora.fireapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MoreInfoActivity extends Activity {

    ImageView btn_close;
    TextView txt_member,txt_fire,txt_temperature,txt_co,txt_smoke;
    ImageView img_img1;
    ImageView img_person1,img_person2,img_person3,img_person4,img_person5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams= new WindowManager.LayoutParams();
        layoutParams.flags= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount= 0.5f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.activity_more_info);

        img_person1 = (ImageView) findViewById(R.id.img_person1);
        img_person2 = (ImageView) findViewById(R.id.img_person2);
        img_person3 = (ImageView) findViewById(R.id.img_person3);
        img_person4 = (ImageView) findViewById(R.id.img_person4);
        img_person5 = (ImageView) findViewById(R.id.img_person5);

        btn_close = (ImageView) findViewById(R.id.btn_close);
        //txt_member = (TextView) findViewById(R.id.txt_member);
        //txt_fire = (TextView) findViewById(R.id.txt_fire);
        img_img1 = (ImageView) findViewById(R.id.img1);
        //txt_co = (TextView) findViewById(R.id.txt_co);
        //txt_temperature = (TextView) findViewById(R.id.txt_temperature);
        //txt_smoke = (TextView) findViewById(R.id.txt_smoke);

        Intent intent_adapter = this.getIntent();
        String member = intent_adapter.getStringExtra("member");
        String fire = intent_adapter.getStringExtra("fire");
        String img1 = intent_adapter.getStringExtra("img1");
        String co = intent_adapter.getStringExtra("co");
        String smoke = intent_adapter.getStringExtra("smoke");
        String temperature = intent_adapter.getStringExtra("temperature");

        //txt_member.setText(member);
        //txt_fire.setText(fire);
        img_img1.setImageResource(R.drawable.fire_o);

        int int_member = Integer.parseInt(member);

        if(int_member == 0){
            img_person1.setImageResource(R.drawable.ic_person_no);
            img_person2.setImageResource(R.drawable.ic_person_no);
            img_person3.setImageResource(R.drawable.ic_person_no);
            img_person4.setImageResource(R.drawable.ic_person_no);
            img_person5.setImageResource(R.drawable.ic_person_no);
        } else if(int_member == 1){
            img_person1.setImageResource(R.drawable.ic_person_yes);
            img_person2.setImageResource(R.drawable.ic_person_no);
            img_person3.setImageResource(R.drawable.ic_person_no);
            img_person4.setImageResource(R.drawable.ic_person_no);
            img_person5.setImageResource(R.drawable.ic_person_no);
        } else if(int_member == 2){
            img_person1.setImageResource(R.drawable.ic_person_yes);
            img_person2.setImageResource(R.drawable.ic_person_yes);
            img_person3.setImageResource(R.drawable.ic_person_no);
            img_person4.setImageResource(R.drawable.ic_person_no);
            img_person5.setImageResource(R.drawable.ic_person_no);
        } else if(int_member == 3){
            img_person1.setImageResource(R.drawable.ic_person_yes);
            img_person2.setImageResource(R.drawable.ic_person_yes);
            img_person3.setImageResource(R.drawable.ic_person_yes);
            img_person4.setImageResource(R.drawable.ic_person_no);
            img_person5.setImageResource(R.drawable.ic_person_no);
        } else if(int_member == 4){
            img_person1.setImageResource(R.drawable.ic_person_yes);
            img_person2.setImageResource(R.drawable.ic_person_yes);
            img_person3.setImageResource(R.drawable.ic_person_yes);
            img_person4.setImageResource(R.drawable.ic_person_yes);
            img_person5.setImageResource(R.drawable.ic_person_no);
        } else if(int_member == 5){
            img_person1.setImageResource(R.drawable.ic_person_yes);
            img_person2.setImageResource(R.drawable.ic_person_yes);
            img_person3.setImageResource(R.drawable.ic_person_yes);
            img_person4.setImageResource(R.drawable.ic_person_yes);
            img_person5.setImageResource(R.drawable.ic_person_yes);
        }

        btn_close.bringToFront();
        //txt_co.setText(co);
        //txt_temperature.setText(temperature);
        //txt_smoke.setText(smoke);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
