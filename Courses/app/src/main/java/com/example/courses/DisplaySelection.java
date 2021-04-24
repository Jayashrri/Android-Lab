package com.example.courses;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.courses.R.color;

import java.util.ArrayList;

public class DisplaySelection extends Fragment {

    String rollNumber;
    ArrayList<String> selected;

    TextView showRollNumber;

    public DisplaySelection() {
        super(R.layout.fragment_display_selection);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        rollNumber = requireArguments().getString("roll_number");
        selected = requireArguments().getStringArrayList("selected");
        System.out.println(selected);

        showRollNumber = (TextView) getView().findViewById(R.id.showRollNumber);
        showRollNumber.setText("Welcome, " + rollNumber);

        Toast.makeText(getActivity(), "Fragment 2 Launched", Toast.LENGTH_LONG).show();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(75, 39, 0, 39);

        LinearLayout linearView = (LinearLayout) getView().findViewById(R.id.display);
        TextView listItem;
        for(String item: selected) {
            listItem = new TextView(getActivity());

            listItem.setText(item);
            listItem.setLayoutParams(params);
            listItem.setTextColor(getResources().getColor(color.black));

            linearView.addView(listItem);
        }
    }
}
