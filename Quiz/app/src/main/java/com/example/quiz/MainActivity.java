package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;

    private List<Question> list;
    QuizDbHelper dbHelper;

    public static final int NEW_QUESTION_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(this.getSupportActionBar() != null)
            this.getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new QuizDbHelper(this);
        list = new ArrayList<>();
        loadRecyclerViewItems();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener( view -> {
            Intent intent = new Intent(MainActivity.this, AddQuestionActivity.class);
            startActivityForResult(intent, NEW_QUESTION_REQUEST_CODE);
        });
    }

    private void loadRecyclerViewItems() {
        list = dbHelper.getQuestions();
        recyclerAdapter = new QuestionAdapter(list, this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(this, "Question Saved.", Toast.LENGTH_LONG).show();
        loadRecyclerViewItems();
    }
}