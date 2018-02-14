package ututor.edu.csulb.ututor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;

public class Favorites extends Fragment {

    public Button add_fav;
    public Button edit_fav;


    public Favorites() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        ListView listview =(ListView) rootView .findViewById(R.id.listViewFavorite);

        //EDITED Code
        String[] items = new String[] {"Aenah Ramones", "Nishant Saxena", "Lance McVicar", "Henry Tran", "Shahar Janjua", "Chris P. Bacon", "Al Bino", "Bill Board", "Brock Lee", "Crystal Ball", "Ella Vader", "Donald Duck", "Filet Minyon", "Bend Dover", "Elmo's World"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        listview.setAdapter(adapter);

        add_fav = (Button)rootView.findViewById(R.id.addFav);
        add_fav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        edit_fav = (Button) rootView.findViewById(R.id.editFav);
        edit_fav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        return rootView;
    }


}