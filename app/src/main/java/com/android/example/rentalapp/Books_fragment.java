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


public class Books_fragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);

        String[] booksMenuItems = {"Academic and Professional", "Literature and Fiction",
                "Entrance Exam", "Biographies and Auto Biographies", "Business and Management",
                "Children and teens"};
        ListView listView = (ListView) view.findViewById(R.id.books_lv);

        ArrayAdapter<String> booksListViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                booksMenuItems
        );

        listView.setAdapter(booksListViewAdapter);

        return view;
    }


}
