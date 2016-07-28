package co.ohlora.userapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Map;

import co.ohlora.userapp.models.Sensors;
import co.ohlora.userapp.models.User;

public class MainActivity extends AppCompatActivity {

    private TextView txt_fire, txt_member, txt_image1, txt_image2, txt_smoke, txt_temperature;
    private DatabaseReference mSensorsReference;
    private ValueEventListener mSensorsListener;
    private DatabaseReference mUsersReference;

    public String userAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_fire = (TextView) findViewById(R.id.txt_fire);
        txt_member = (TextView) findViewById(R.id.txt_member);
        txt_image1 = (TextView) findViewById(R.id.txt_image1);
        txt_image2 = (TextView) findViewById(R.id.txt_image2);
        txt_smoke = (TextView) findViewById(R.id.txt_smoke);
        txt_temperature = (TextView) findViewById(R.id.txt_temperature);

        mUsersReference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(getUid());


        mUsersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getValue());
                User user = dataSnapshot.getValue(User.class);
                userAddress = user.useraddress;

                mSensorsReference = FirebaseDatabase.getInstance().getReference().child("address")
                        .child(userAddress.split("/")[0]).child(userAddress.split("/")[1]);

                ValueEventListener sensorListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println(dataSnapshot.getValue());
                        Sensors sensors = dataSnapshot.getValue(Sensors.class);

                        txt_image1.setText(sensors.image1);
                        txt_image2.setText(sensors.image2);
                        txt_member.setText(sensors.member);
                        txt_smoke.setText(sensors.smoke);
                        txt_temperature.setText(sensors.temperature);
                        txt_fire.setText(sensors.fire);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        // [START_EXCLUDE]
                        Toast.makeText(MainActivity.this, "Failed to load info.",
                                Toast.LENGTH_SHORT).show();
                        // [END_EXCLUDE]
                    }
                };
                mSensorsReference.addValueEventListener(sensorListener);


                mSensorsListener = sensorListener;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public String getUid(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (mSensorsListener != null) {
            mSensorsReference.removeEventListener(mSensorsListener);
        }

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

}

