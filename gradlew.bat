package com;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.freedomedition.hangar.R;

import androidx.fragment.app.Fragment;

public class tab2 extends Fragment {
    public tab2() {
         }
    ListView listView;
    String[] items = {"Medical consultation","Join one of our wellness program", "Informative feeds", "Interested in a feed content?"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_tab2, container, false);


        listView = view.findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1,items);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //listView clicked item index....start as 0,1,2,3 etc..
                int itemPosition = position;
                //listView clicked value
                String itemValue = (String)listView.getItemAtPosition(position);

                if(itemPosition == 0){
                    Toast.makeText(getContext(), itemValue, Toast.LENGTH_SHORT).show();
                }else if(itemPosition == 1){
                    Toast.makeText(getContext(), itemValue, Toast.LENGTH_SHORT).show();
                }else if (itemPosition == 2){
                    Toast.makeText(getContext(), itemValue, Toast.LENGTH_SHORT).show();
                }

            }
        });




        return view;
    }

}
                                        