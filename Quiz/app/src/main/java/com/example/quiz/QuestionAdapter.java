package com.example.quiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private List<Question> list;
    private Context ctx;

    public QuestionAdapter(List<Question> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(QuestionAdapter.ViewHolder holder, int position) {
        Question question = list.get(position);
        holder.question.setText(question.getQuestion());

        for( Option option : question.getOptions()) {
            RadioButton radioButton = new RadioButton(holder.options.getContext());
            radioButton.setText(option.getOption());
            radioButton.setEnabled(false);
            if(option.getIndex() == question.getCorrect())
                radioButton.setChecked(true);
            holder.options.addView(radioButton);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView question;
        public RadioGroup options;

        public ViewHolder(View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.question);
            options = itemView.findViewById(R.id.options);
        }
    }
}
