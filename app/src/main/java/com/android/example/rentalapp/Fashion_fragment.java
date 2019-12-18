package com.android.example.rentalapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Fashion_fragment extends Fragment {

    public Fashion_fragment(){
        //Empty constructor required
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fashion, container, false);

        String[] fashionMenuItems = {"Men\'s fashion", "Women\'s fashion"};
        ListView listView = (ListView) view.findViewById(R.id.fashio_lv);

        ArrayAdapter<String> fashionListViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                fashionMenuItems
        );

        listView.setAdapter(fashionListViewAdapter);

        return view;
    }
}
