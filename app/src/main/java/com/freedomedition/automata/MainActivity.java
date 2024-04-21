package com.freedomedition.automata;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    DatabaseReference dref;

    SharedPreferences shared_str_temperatureLoading;
    SharedPreferences shared_str_humidityLoading;
    SharedPreferences shared_str_PH;
    SharedPreferences shared_str_moisture;

    NotificationChannel channel;
    NotificationManager manager;

    NotificationChannel channel1;
    NotificationManager manager1;

    NotificationChannel channel2;
    NotificationManager manager2;

    NotificationChannel channel3;
    NotificationManager manager3;

    String str_temperatureLoading;
    String str_humidityLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_new)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        shared_str_temperatureLoading = getSharedPreferences("shared_str_temperatureLoading", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared_str_temperatureLoading.edit();
        editor.putString("key","first");
        editor.commit();

        shared_str_humidityLoading = getSharedPreferences("shared_str_humidityLoading", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = shared_str_humidityLoading.edit();
        editor1.putString("key1", "first");
        editor1.commit();

        shared_str_PH = getSharedPreferences("shared_str_PH", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = shared_str_PH.edit();
        editor2.putString("key2", "first");
        editor2.commit();

        shared_str_moisture = getSharedPreferences("shared_str_moisture", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = shared_str_moisture.edit();
        editor3.putString("key3", "first");
        editor3.commit();



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

            channel1 = new NotificationChannel("My Notification 1", "My Notification 1", NotificationManager.IMPORTANCE_DEFAULT);
            manager1 = getSystemService(NotificationManager.class);
            manager1.createNotificationChannel(channel1);

            channel2 = new NotificationChannel("My Notification 2", "My Notification 2", NotificationManager.IMPORTANCE_DEFAULT);
            manager2 = getSystemService(NotificationManager.class);
            manager2.createNotificationChannel(channel2);

            channel3 = new NotificationChannel("My Notification 3", "My Notification 3", NotificationManager.IMPORTANCE_DEFAULT);
            manager3 = getSystemService(NotificationManager.class);
            manager3.createNotificationChannel(channel3);
        }



        dref= FirebaseDatabase.getInstance().getReference().child("TEMPERATURE");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                str_temperatureLoading=dataSnapshot.child("value").getValue().toString();

                if(shared_str_temperatureLoading.getString("key", "def").equals("first")){

                    shared_str_temperatureLoading = getSharedPreferences("shared_str_temperatureLoading", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared_str_temperatureLoading.edit();
                    editor.putString("key","more");
                    editor.commit();
                }else if(Float.parseFloat(str_temperatureLoading) > 30){
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"My Notification");
                    builder.setContentTitle("Alert !!");
                    builder.setContentText("Temperature is above 30 degree celcius.");
                    builder.setSmallIcon(R.drawable.automatalogo);
                    builder.setAutoCancel(true);

                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
                    managerCompat.notify(1,builder.build());
                }else if(Float.parseFloat(str_temperatureLoading) < 20){
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"My Notification");
                    builder.setContentTitle("Alert !!");
                    builder.setContentText("Temperature is below 20 degree celcius.");
                    builder.setSmallIcon(R.drawable.automatalogo);
                    builder.setAutoCancel(true);
                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
                    managerCompat.notify(1,builder.build());
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

                str_humidityLoading=dataSnapshot.child("value").getValue().toString();

                if(shared_str_humidityLoading.getString("key1", "def").equals("first")){

                    shared_str_humidityLoading = getSharedPreferences("shared_str_humidityLoading", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = shared_str_humidityLoading.edit();
                    editor1.putString("key1", "more");
                    editor1.commit();

                }else if(Float.parseFloat(str_humidityLoading) > 85){
                    NotificationCompat.Builder builder1 = new NotificationCompat.Builder(getApplicationContext(),"My Notification 1");
                    builder1.setContentTitle("Alert !!");
                    builder1.setContentText("Humidity is above 85%.");
                    builder1.setSmallIcon(R.drawable.automatalogo);
                    builder1.setAutoCancel(true);
                    NotificationManagerCompat managerCompat1 = NotificationManagerCompat.from(getApplicationContext());
                    managerCompat1.notify(2,builder1.build());

                }else if(Float.parseFloat(str_humidityLoading) < 65){
                    NotificationCompat.Builder builder1 = new NotificationCompat.Builder(getApplicationContext(),"My Notification 1");
                    builder1.setContentTitle("Alert !!");
                    builder1.setContentText("Humidity is below 65%.");
                    builder1.setSmallIcon(R.drawable.automatalogo);
                    builder1.setAutoCancel(true);
                    NotificationManagerCompat managerCompat1 = NotificationManagerCompat.from(getApplicationContext());
                    managerCompat1.notify(2,builder1.build());
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

}
