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


public class Automobiles_fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_automobiles, container, false);

        String[] automobilesMenuItems = {"Cars", "Bicycles", "Scooty or Activa", "Bikes"};
        ListView listView = (ListView) view.findViewById(R.id.automobiles_lv);

        ArrayAdapter<String> automobilesListViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                automobilesMenuItems
        );

        listView.setAdapter(automobilesListViewAdapter);

        return view;
    }

}
