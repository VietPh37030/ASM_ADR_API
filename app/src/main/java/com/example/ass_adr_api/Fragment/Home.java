package com.example.ass_adr_api.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.ass_adr_api.R;

public class Home extends Fragment {

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initLocation(rootView); // Pass the inflated view to the method
        return rootView;
    }

    private void initLocation(View rootView) {
        String[] items = {"Hà Nội", "Hồ Chí Minh", "Đà Nẵng"};
        final Spinner locationSpinner = rootView.findViewById(R.id.spinner); // Use rootView.findViewById()
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);
    }
}

