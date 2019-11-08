package com.example.arishti;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class fragment_one extends Fragment {
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one, container, false);

        final ArrayList<contacts> contact = new ArrayList<contacts>();
        for(int i=0;i<3;i++) {
            contact.add(new contacts("Sourabh", R.drawable.ic_person_black_24dp));
            contact.add(new contacts("Pranav", R.drawable.ic_person_black_24dp));
            contact.add(new contacts("kanak", R.drawable.ic_person_black_24dp));
        }
        ContactsAdapter contactAdapter =new ContactsAdapter(getActivity(),contact);


        ListView listView = (ListView) view.findViewById(R.id.contactListView);

        listView.setAdapter(contactAdapter);
        return view;

    }
}
