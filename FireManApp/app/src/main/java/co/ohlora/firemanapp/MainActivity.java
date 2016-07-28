package co.ohlora.firemanapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.ohlora.firemanapp.models.Sensors;

public class MainActivity extends AppCompatActivity {

    private EditText mEditApart;
    private Button mBtnSearch;
    private TextView mTxtSensor;
    private LinearLayout value_section;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private Sensors[] arr_sensors;
    private String[] arr_homes;

    public String apart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditApart = (EditText) findViewById(R.id.edit_apart);
        mBtnSearch = (Button) findViewById(R.id.btn_search);
        mTxtSensor = (TextView) findViewById(R.id.txt_sensor);
        value_section = (LinearLayout) findViewById(R.id.value_section) ;
        value_section.setVisibility(View.GONE);

        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
    }

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

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mEditApart.getText().toString())) {
            mEditApart.setError("Required");
            result = false;
        } else {
            mEditApart.setError(null);
        }

        return result;
    }

    public void search(){

        if (!validateForm()) {
            return;
        }

        apart = mEditApart.getText().toString();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("address");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getValue());

                System.out.println(dataSnapshot.getChildrenCount());

                arr_sensors = new Sensors[(int) dataSnapshot.child(apart).getChildrenCount()];
                arr_homes = new String[(int) dataSnapshot.child(apart).getChildrenCount()];

                int i = 0;

                for(DataSnapshot sensor : dataSnapshot.child(apart).getChildren()){
                    arr_homes[i] = sensor.getKey();
                    arr_sensors[i] = sensor.getValue(Sensors.class);
                    i++;
                }
                i = 0;

                int fire_status = 0;

                for(int j = 0; j < dataSnapshot.child(apart).getChildrenCount(); j++){

                    if(arr_sensors[j].fire.equals("yes")){
                        fire_status = 1;
                    } else {
                        if(fire_status == 1){
                            fire_status = 1;
                        } else {
                            fire_status = 0;
                        }
                    }
                }

                if(fire_status == 1){
                    value_section.setVisibility(View.VISIBLE);
                    for(int k = 0; k < arr_homes.length; k++){
                        if(k == 0){
                            mTxtSensor.setText(arr_homes[k] + " : " + arr_sensors[k].member + "\n");
                        } else {
                            mTxtSensor.append(arr_homes[k] + " : " + arr_sensors[k].member + "\n");
                        }
                    }
                } else {
                    value_section.setVisibility(View.GONE);
                    mTxtSensor.setText("");
                    for(int free_tmp = 0; free_tmp < arr_homes.length; free_tmp++){
                        arr_homes[free_tmp] = null;
                        arr_sensors[free_tmp] = null;
                    }
                    Toast.makeText(getApplicationContext(),"개인정보 보호를 위해 불이 난 장소의 정보만 제공합니다.",Toast.LENGTH_SHORT).show();
                }

                /*
                Sensors sensors = dataSnapshot.getValue(Sensors.class);

                if(sensors.fire.equals("yes")){
                    mTxtSensor.setVisibility(View.VISIBLE);
                    mTxtSensor.setText(sensors.fire + "\n");
                    mTxtSensor.append(sensors.image1 + "\n");
                    mTxtSensor.append(sensors.image2 + "\n");
                    mTxtSensor.append(sensors.member + "\n");
                    mTxtSensor.append(sensors.smoke + "\n");
                    mTxtSensor.append(sensors.temperature + "\n");
                } else {

                }
                */
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
