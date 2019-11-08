package com.example.arishti;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class fragment_two extends Fragment {
    private static final int ADDMEETING_REQUEST = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two,
                container, false);
        Button button = (Button) view.findViewById(R.id.addmeeting_icon);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), meetingcreate.class);
                startActivityForResult(intent,ADDMEETING_REQUEST );// do something
            }
        });
        return view;
    }
}