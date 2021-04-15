package com.example.timetable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ViewHolder> {

    private List<Schedule> list;
    private Context ctx;

    public ScheduleListAdapter(List<Schedule> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ScheduleListAdapter.ViewHolder holder, int position) {
        Schedule schedule = list.get(position);
        holder.subjectView.setText(schedule.getSubject());
        holder.timeView.setText(schedule.getTime());

        holder.optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(ctx, holder.optionButton);
                popup.inflate(R.menu.options_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuDelete:
                                ScheduleDbHelper dbHelper = new ScheduleDbHelper(ctx);
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                int deletedRows = db.delete(ScheduleContract.Schedule.TABLE_NAME,
                                        ScheduleContract.Schedule._ID + " LIKE ?",
                                        new String[] {
                                                String.valueOf(schedule.getId())
                                        }
                                );
                                list.remove(position);
                                notifyItemRemoved(position);
                                notifyDataSetChanged();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView subjectView;
        public TextView timeView;
        public TextView optionButton;

        public ViewHolder(View itemView) {
            super(itemView);

            subjectView = (TextView) itemView.findViewById(R.id.subjectName);
            timeView = (TextView) itemView.findViewById(R.id.time);
            optionButton = (TextView) itemView.findViewById(R.id.option);
        }
    }
}
