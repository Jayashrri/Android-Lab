package com.example.courses;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class CourseSelection extends Fragment {

    String rollNumber;

    TextView showRollNumber;
    LinearLayout courseList;

    public CourseSelection() {
        super(R.layout.fragment_course_selection);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        rollNumber = requireArguments().getString("roll_number");
        courseList = (LinearLayout) getView().findViewById(R.id.course_list);

        showRollNumber = (TextView) getView().findViewById(R.id.showRollNumber);
        showRollNumber.setText("Welcome, " + rollNumber);

        Button submit = (Button) getView().findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });

        Toast.makeText(getActivity(), "Fragment 1 Launched", Toast.LENGTH_LONG).show();
    }

    private void onSubmit() {
        ArrayList<String> selected = new ArrayList<String>();
        for(int i=0; i<courseList.getChildCount(); i++) {
            CheckBox item = (CheckBox) courseList.getChildAt(i);
            if(item.isChecked())
                selected.add(item.getText().toString());
        }

        Bundle bundle = new Bundle();
        bundle.putString("roll_number", rollNumber);
        bundle.putStringArrayList("selected", selected);
        System.out.print(selected);

        getActivity().getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, DisplaySelection.class, bundle)
                .commit();
    }
}