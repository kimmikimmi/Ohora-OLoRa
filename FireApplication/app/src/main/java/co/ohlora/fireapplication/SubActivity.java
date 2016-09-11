package co.ohlora.fireapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.ohlora.fireapplication.adapters.SensorsAdapter;
import co.ohlora.fireapplication.models.Sensors;

public class SubActivity extends AppCompatActivity {

    private RelativeLayout content_sub_section;

    private RecyclerView recyclerView;
    private SensorsAdapter adapter;
    private List<Sensors> sensorsList, allSensorList;
    private List<String> homesList, allHomeList, allAddressList;

    private DatabaseReference mDatabase, whole_DB, user_DB;

    private Sensors[] arr_sensors;
    private String[] arr_homes;

    public TextView txt_title;
    public ImageButton btn_back;
    public FloatingActionButton btn_floating;

    public String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        setCustomActionbar();

        btn_back = (ImageButton) findViewById(R.id.btn_back);
        txt_title = (TextView) findViewById(R.id.txt_title);

        btn_floating = (FloatingActionButton) findViewById(R.id.btn_floating);

        content_sub_section = (RelativeLayout) findViewById(R.id.content_sub_section);

        recyclerView = (RecyclerView) findViewById(R.id.sub_recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        address = getIntent().getStringExtra("address");

        txt_title.setText(address);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DomyeunActivity.class);
                startActivity(intent);
            }
        });

        search(address);
    }

    public void setCustomActionbar(){
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        View mCustomView = LayoutInflater.from(this).inflate(R.layout.layout_actionbar,null);
        actionBar.setCustomView(mCustomView);

        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.top));
    }

    public void search(final String address){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("address");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getValue());

                System.out.println(dataSnapshot.getChildrenCount());

                sensorsList = new ArrayList<>();
                homesList = new ArrayList<>();
                adapter = new SensorsAdapter(getApplicationContext(), sensorsList, homesList);

                recyclerView.setAdapter(adapter);

                arr_sensors = new Sensors[(int) dataSnapshot.child(address).getChildrenCount()];
                arr_homes = new String[(int) dataSnapshot.child(address).getChildrenCount()];

                int i = 0;

                for(DataSnapshot sensor : dataSnapshot.child(address).getChildren()){
                    arr_homes[i] = sensor.getKey();
                    arr_sensors[i] = sensor.getValue(Sensors.class);
                    i++;
                    homesList.add(sensor.getKey());
                    sensorsList.add(sensor.getValue(Sensors.class));
                }
                i = 0;
                Collections.reverse(homesList);
                Collections.reverse(sensorsList);

                for(int s = 0; s < homesList.size(); s = s+2){
                    String temp1 = homesList.get(s);
                    String temp2 = homesList.get(s+1);
                    homesList.set(s+1,temp1);
                    homesList.set(s,temp2);
                }

                for(int s = 0; s < sensorsList.size(); s = s+2){
                    Sensors temp1 = sensorsList.get(s);
                    Sensors temp2 = sensorsList.get(s+1);
                    sensorsList.set(s+1,temp1);
                    sensorsList.set(s,temp2);
                }

                adapter.notifyDataSetChanged();

                int fire_status = 0;

                for(int j = 0; j < dataSnapshot.child(address).getChildrenCount(); j++){

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
                    content_sub_section.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    content_sub_section.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    for(int free_tmp = 0; free_tmp < arr_homes.length; free_tmp++){
                        arr_homes[free_tmp] = null;
                        arr_sensors[free_tmp] = null;
                    }
                    homesList.clear();
                    sensorsList.clear();
                    //Toast.makeText(getApplicationContext(),"개인정보 보호를 위해 불이 난 장소의 정보만 제공합니다.",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
