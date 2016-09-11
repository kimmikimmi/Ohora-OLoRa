package co.ohlora.fireapplication;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import co.ohlora.fireapplication.adapters.AddressAdapter;
import co.ohlora.fireapplication.models.Sensors;
import co.ohlora.fireapplication.models.Users;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout content_main_section;

    private RecyclerView recyclerView;
    private CoordinatorLayout whole_main_section;

    private AddressAdapter addressAdapter;

    private List<Sensors> sensorsList, allSensorList;
    private List<String> homesList, allHomeList, allAddressList;
    private List<String> fireAddressList, result_fire_list;

    private DatabaseReference mDatabase, whole_DB, user_DB;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
*/
        whole_main_section = (CoordinatorLayout) findViewById(R.id.whole_main_section);

        content_main_section = (RelativeLayout) findViewById(R.id.content_main_section);
        content_main_section.setVisibility(View.GONE);
        recyclerView = (RecyclerView) findViewById(R.id.address_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        whole_DB = FirebaseDatabase.getInstance().getReference().child("address");

        allSensorList = new ArrayList<>();
        allHomeList = new ArrayList<>();
        allAddressList = new ArrayList<>();
        fireAddressList = new ArrayList<>();

        allAddressList.clear();
        allHomeList.clear();
        allSensorList.clear();
        fireAddressList.clear();

        result_fire_list = new ArrayList<>();

        whole_DB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                allAddressList.clear();
                allHomeList.clear();
                allSensorList.clear();
                fireAddressList.clear();
                result_fire_list.clear();


                addressAdapter = new AddressAdapter(getApplicationContext(),result_fire_list);

                recyclerView.setAdapter(addressAdapter);

                for (DataSnapshot address : dataSnapshot.getChildren()) {
                    allAddressList.add(address.getKey());
                }
                //System.out.println(allAddressList.size() + "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                for (int temp = 0; temp < allAddressList.size(); temp++) {
                    for (DataSnapshot sensors : dataSnapshot.child(allAddressList.get(temp)).getChildren()) {
                        allHomeList.add(sensors.getKey());
                        allSensorList.add(sensors.getValue(Sensors.class));

                        for (int temp2 = 0; temp2 < allSensorList.size(); temp2++) {
                            Sensors sensors2 = allSensorList.get(temp2);
                            //System.out.println("!!!!!!" + allSensorList.get(temp2));
                            if (sensors2.getFire().equals("yes")) {

                                fireAddressList.add(allAddressList.get(temp));
                            }
                        }
                    }
                }

                HashSet hs = new HashSet(fireAddressList);
                Iterator<String> it = hs.iterator();
                while(it.hasNext()){
                    result_fire_list.add(it.next());
                }

                if(result_fire_list.size() != 0){
                    content_main_section.setVisibility(View.VISIBLE);
                    whole_main_section.setBackgroundColor(Color.WHITE);
                } else {
                    content_main_section.setVisibility(View.GONE);
                    whole_main_section.setBackgroundResource(R.drawable.img_no_list);
                }


                addressAdapter.notifyDataSetChanged();

                for(int k=0; k < result_fire_list.size(); k++){
                    System.out.println("!@!@!@@!@@" + "  " + result_fire_list.get(k));
                }


                //   System.out.println("before notify  " + addressAdapter.getItemCount());


                //  System.out.println("after notify  " + addressAdapter.getItemCount());

                //System.out.println(allSensorList.size() + "@@@@@@@@@@@@@@@@@@@@@");
                for (int temp = 0; temp < allSensorList.size(); temp++) {
                    Sensors sensors = allSensorList.get(temp);
                    //System.out.println("!!!!!!" + allSensorList.get(temp));
                    if (sensors.getFire().equals("yes")) {

                        //sendSMS(sensors.getUserId());
                    }
                }

                allAddressList.clear();
                allHomeList.clear();
                allSensorList.clear();
                fireAddressList.clear();
                //result_fire_list.clear();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void sendSMS(String uid){
        final PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT_ACTION"), 0);
        final PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED_ACTION"), 0);

        String user_id = uid;
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch(getResultCode()){
                    case Activity.RESULT_OK:
                        // 전송 성공
                        Toast.makeText(getApplicationContext(), "신고 접수!!", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        // 전송 실패
                        Toast.makeText(getApplicationContext(), "전송 실패", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        // 서비스 지역 아님
                        Toast.makeText(getApplicationContext(), "서비스 지역이 아닙니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        // 무선 꺼짐
                        Toast.makeText(getApplicationContext(), "무선(Radio)가 꺼져있습니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        // PDU 실패
                        Toast.makeText(getApplicationContext(), "PDU Null", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_SENT_ACTION"));

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        // 도착 완료
                        //Toast.makeText(mContext, "SMS 도착 완료", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        // 도착 안됨
                        //Toast.makeText(mContext, "SMS 도착 실패", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_DELIVERED_ACTION"));

        user_DB = FirebaseDatabase.getInstance().getReference().child("users").child(user_id);

        user_DB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.getValue(Users.class);
                String user_address;
                user_address = users.useraddress;

                System.out.println("!@@@@@@@!@!@!@" + user_address);

                String juso = user_address.split("/")[0];
                String ho = user_address.split("/")[1];

                String message = "화재 발생\n" + "주소 : " + juso + "\n" + "호 수 : " + ho + "호";

                SmsManager mSmsManager = SmsManager.getDefault();
                mSmsManager.sendTextMessage("01072623869", null, message, sentIntent, deliveredIntent);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //user 정보에서 주소정보 가져와야함..


    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
*/
}
