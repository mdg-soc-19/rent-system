package com.android.example.rentalapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Accessories_fragment extends Fragment {

    public Accessories_fragment(){
        //Empty constructor required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accessories, container, false);

        String[] accessoriesMenuItems = {"Hats", "Goggles", "Watches", "Makeup", "Jewellery", "others"};
        ListView listView = (ListView) view.findViewById(R.id.accessories_lv);

        ArrayAdapter<String> accessoriesListViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                accessoriesMenuItems
        );

        listView.setAdapter(accessoriesListViewAdapter);

        return view;
    }

}
