package com.android.example.rentalapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Gadgets_fragment extends Fragment {

    Gadgets_fragment(){
        //Empty constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gadgets, container, false);

        String[] gadgetsMenuItems = {"Speakers", "USB devices", "Headphones or Earphones", "Camera", "Mouse", "Keyboard"};
        ListView listView = (ListView) view.findViewById(R.id.gadgets_lv);

        ArrayAdapter<String> gadgetsListViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                gadgetsMenuItems
        );

        listView.setAdapter(gadgetsListViewAdapter);

        return view;
    }


}
