package com.freedomedition.automata.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.freedomedition.automata.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    DatabaseReference dref;

    String str_temperature;
    String str_humidity;
    String str_tds;

    ImageView temperatureImg;
    ImageView humidityImg;
    ImageView tdsImg;

    TextView temperatureNotice;
    TextView temperatureSuggestion;

    TextView humidityNotice;
    TextView humiditySuggestion;

    TextView tdsNotice;
    TextView tdsSuggestion;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        temperatureImg = root.findViewById(R.id.temperatureImg);
        humidityImg = root.findViewById(R.id.humidityImg);
        tdsImg = root.findViewById(R.id.tdsImg);

        temperatureNotice = root.findViewById(R.id.temperatureNotice);
        temperatureSuggestion = root.findViewById(R.id.temperatureSuggestion);

        humidityNotice = root.findViewById(R.id.humidityNotice);
        humiditySuggestion = root.findViewById(R.id.humiditySuggestion);

        tdsNotice = root.findViewById(R.id.tdsNotice);
        tdsSuggestion = root.findViewById(R.id.tdsSuggestion);



        dref= FirebaseDatabase.getInstance().getReference().child("TEMPERATURE");

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                str_temperature=dataSnapshot.child("value").getValue().toString();
                float decimal = Float.parseFloat(str_temperature);
                int num = (int)Math.round(decimal);

                if(num>30){
                    temperatureImg.setImageResource(R.drawable.temperaturehigh);
                    temperatureNotice.setText(" High");
                    temperatureSuggestion.setText("Turn on Air Conditioner");
                }else if (num<=20){
                    temperatureImg.setImageResource(R.drawable.temperaturelow);
                    temperatureNotice.setText("Low");
                    temperatureSuggestion.setText("Turn off Air Conditioner");
                }else{
                    temperatureImg.setImageResource(R.drawable.temperaturenormal);
                    temperatureNotice.setText("Good");
                    temperatureSuggestion.setText("None");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        dref= FirebaseDatabase.getInstance().getReference().child("HUMIDITY");

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                str_humidity = dataSnapshot.child("value").getValue().toString();
                float decimal = Float.parseFloat(str_humidity);
                int num = (int)Math.round(decimal);

                if(num>85){
                    humidityImg.setImageResource(R.drawable.humidityhigh);
                    humidityNotice.setText("High");
                    humiditySuggestion.setText("Turn off Humidifier");
                }else if (num<65){
                    humidityImg.setImageResource(R.drawable.humiditylow);
                    humidityNotice.setText("Low");
                    humiditySuggestion.setText("Turn on Humidifier");
                }else{
                    humidityImg.setImageResource(R.drawable.humiditynormal);
                    humidityNotice.setText("Good");
                    humiditySuggestion.setText("None");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        dref= FirebaseDatabase.getInstance().getReference().child("TDS");

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                str_tds = dataSnapshot.child("value").getValue().toString();
                float decimal = Float.parseFloat(str_tds);
                int num = (int)Math.round(decimal);

                if(num>1200){
                    tdsImg.setImageResource(R.drawable.warning);
                    tdsNotice.setText("High");
                    tdsSuggestion.setText("Dilute nutrient solution");
                }else if (num<100){
                    tdsImg.setImageResource(R.drawable.warning);
                    tdsNotice.setText("Low");
                    tdsSuggestion.setText("Add nutrient");
                }else{
                    tdsImg.setImageResource(R.drawable.greentick);
                    tdsNotice.setText("Good");
                    tdsSuggestion.setText("None");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        return root;
    }


}
