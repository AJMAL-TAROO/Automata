package com.freedomedition.automata.ui.NewFragmentExample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.freedomedition.automata.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class NewFragment extends Fragment {
    private NewViewModel newViewModel;
    DatabaseReference dref;
    TextView temperatureLoadingtxt;
    TextView humidityLoadingtxt;
    TextView tdsLoadingtxt;
    String str_temperatureLoading;
    String str_humidityLoading;
    String str_tdsLoading;
    ProgressBar progressbarTemperature;
    ProgressBar progressbarHumidity;
    ImageView imageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newViewModel =
                ViewModelProviders.of(this).get(NewViewModel.class);
        View root = inflater.inflate(R.layout.new_layout, container, false);

        humidityLoadingtxt = root.findViewById(R.id.humidityLoadingtxt);
        temperatureLoadingtxt = root.findViewById(R.id.temperatureLoadingtxt);
        tdsLoadingtxt = root.findViewById(R.id.tdsLoadingtxt);

        progressbarHumidity = root.findViewById(R.id.progressHumidity);
        progressbarTemperature = root.findViewById(R.id.progressTemperature);
        imageView = root.findViewById(R.id.plantImageView);

        Glide.with(this).load(R.drawable.plant_animation).into(imageView);
        progressbarTemperature.setMax(100);
        progressbarHumidity.setMax(100);

        dref= FirebaseDatabase.getInstance().getReference().child("TEMPERATURE");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                str_temperatureLoading=dataSnapshot.child("value").getValue().toString();
                temperatureLoadingtxt.setText(str_temperatureLoading + "\u2103");
                float decimal = Float.parseFloat(str_temperatureLoading);
                int num = (int)Math.round(decimal);
                progressbarTemperature.setProgress(num);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        dref= FirebaseDatabase.getInstance().getReference().child("HUMIDITY");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                str_humidityLoading=dataSnapshot.child("value").getValue().toString();
                humidityLoadingtxt.setText(str_humidityLoading + "%");
                progressbarHumidity.setProgress(Integer.parseInt(str_humidityLoading));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        dref= FirebaseDatabase.getInstance().getReference().child("TDS");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                str_tdsLoading=dataSnapshot.child("value").getValue().toString();
                float decimal = Float.parseFloat(str_tdsLoading);
                int num = (int)Math.round(decimal);
                tdsLoadingtxt.setText(num + "ppm");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return root;
    }



}
