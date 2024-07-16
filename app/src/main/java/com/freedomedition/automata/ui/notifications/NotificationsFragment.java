package com.freedomedition.automata.ui.notifications;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.freedomedition.automata.R;
import com.freedomedition.automata.ui.CustomDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class NotificationsFragment extends Fragment implements OnMapReadyCallback{
    private NotificationsViewModel notificationsViewModel;
    ImageView imageView; ImageView dht11image; ImageView tdsimage;
    TextView dht11txt; TextView tdstxt;
    DatabaseReference dref;
    String string_dht11;
    String string_tds;

    GoogleMap mMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        imageView = root.findViewById(R.id.hydroponicImageView);
        dht11image = root.findViewById(R.id.dht11);
        tdsimage = root.findViewById(R.id.tds);
        dht11txt = root.findViewById(R.id.dht11txt);
        tdstxt = root.findViewById(R.id.tdstxt);

        Glide.with(this).load(R.drawable.hydroponic_animation).into(imageView);

        dref= FirebaseDatabase.getInstance().getReference().child("SENSORS");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                string_dht11 =dataSnapshot.child("DHT11").getValue().toString();
                dht11txt.setText(string_dht11);
                if(string_dht11.equals("ON")){
                    Glide.with(root.getContext()).load(R.drawable.gif_sensor).into(dht11image);
                }else{
                    Glide.with(root.getContext()).load(R.drawable.ic_sensor_stop).into(dht11image);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        dref= FirebaseDatabase.getInstance().getReference().child("SENSORS");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                string_tds =dataSnapshot.child("TDS").getValue().toString();
                tdstxt.setText(string_tds);
                if(string_tds.equals("ON")){
                    Glide.with(root.getContext()).load(R.drawable.gif_sensor).into(tdsimage);
                }else{
                    Glide.with(root.getContext()).load(R.drawable.ic_sensor_stop).into(tdsimage);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        dht11image.setOnClickListener(view -> {
            String details_dht11;
            if(string_dht11.equals("ON")){
                details_dht11 = "Sensor is up and running.";
            }else{
                details_dht11 = "Sensor might not be connected. \nPlease check the wiring connection.";
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(true);
            builder.setTitle("DHT11");
            builder.setMessage(details_dht11);
            if(string_dht11.equals("ON")) {
                builder.setPositiveButton("OK", (dialog, which) -> {
                });
            }
            if(string_dht11.equals("OFF")){
                builder.setNegativeButton("Take Action",(dialog, which) -> {
                    getDHTAction();
                });
            }
            builder.create().show();
        });

        tdsimage.setOnClickListener(view -> {
            String details_tds;
            if(string_tds.equals("ON")){
                details_tds = "Sensor is up and running.";
            }else{
                details_tds = "Sensor might not be connected. \n1. Check the wiring connection. \n2. If sensor is in water.";
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(true);
            builder.setTitle("TDS");
            builder.setMessage(details_tds);
            if(string_tds.equals("ON")) {
                builder.setPositiveButton("OK", (dialog, which) -> {
                });
            }
            if(string_tds.equals("OFF")){
                builder.setNegativeButton("Take Action",(dialog, which) -> {
                });
            }
            builder.create().show();
        });

        return root;
    }

    public void getDHTAction(){
        String actions_tds = "You can either: \n1. Crosscheck the wiring connection provided\n2. Buy a new component at the shops provided";
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle("Actions that can be taken");
        builder.setMessage(actions_tds);
        builder.setPositiveButton("Check wiring", (dialog, which) -> {
            checkDHTConnection();
        });
        builder.setNegativeButton("Buy locally",(dialog, which) -> {
            final CustomDialog dialog1 = new CustomDialog(getContext());
            View view = getLayoutInflater().inflate(R.layout.popup_donation, null);
            dialog1.setView(view);
            dialog1.setCancelable(true);


            // Get the SupportMapFragment from the inflated layout
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            dialog1.show();

//            listView = view.findViewById(R.id.listView);
//            labelDonation = view.findViewById(R.id.labelDonation);
        });
        builder.create().show();
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker at the specified location
        LatLng location = new LatLng(-20.25578199000835, 57.481395786625434);
        mMap.addMarker(new MarkerOptions().position(location).title("Marker at specified coordinates"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
    public void checkDHTConnection(){
        String connectionDetails;
        connectionDetails = "1. Connect GND from DHT11 to GND in NodeMCU\n2. Connect VCC from DHT11 to 3V in NodeMCU\n 3. Connect Data from DHT11 to D4 in NodeMCU";
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle("DHT wiring connection");
        builder.setMessage(connectionDetails);
        builder.setPositiveButton("OK", (dialog, which) -> {
        });
//        builder.setNegativeButton("Buy locally",(dialog, which) -> {
//        });
        builder.create().show();
    }

}
