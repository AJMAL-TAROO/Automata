package com.freedomedition.automata.ui.notifications;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationsFragment extends Fragment {
    private NotificationsViewModel notificationsViewModel;
    ImageView imageView; ImageView dht11image; ImageView tdsimage;
    TextView dht11txt; TextView tdstxt;
    DatabaseReference dref;
    String string_dht11;
    String string_tds;

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
//        Glide.with(this).load(R.drawable.gif_sensor).into(dht11image);
//        Glide.with(this).load(R.drawable.gif_sensor).into(tdsimage);

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

//        if(dht11.equals("ON")){
//            Glide.with(this).load(R.drawable.gif_sensor).into(dht11image);
//        }else{
//            Glide.with(this).load(R.drawable.ic_sensor_stop).into(dht11image);
//        }
//
//        if(tds.equals("ON")){
//            Glide.with(this).load(R.drawable.gif_sensor).into(tdsimage);
//        }else{
//            Glide.with(this).load(R.drawable.ic_sensor_stop).into(tdsimage);
//        }

        return root;
    }
}
