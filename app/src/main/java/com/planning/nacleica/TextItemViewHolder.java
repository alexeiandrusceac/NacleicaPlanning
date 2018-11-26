package com.planning.nacleica;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TextItemViewHolder extends RecyclerView.ViewHolder {
    private TextView taskTitle;
    private TextView taskPeriodFrom;
    private TextView taskPeriodTo;
    public TextItemViewHolder(View itemView) {
        super(itemView);
        taskTitle = (TextView) itemView.findViewById(R.id.task_title);
        taskPeriodFrom =(TextView) itemView.findViewById(R.id.task_periodFrom);
        taskPeriodTo =(TextView) itemView.findViewById(R.id.task_periodTo);
    }

    public void bind(String text) {
        taskTitle.setText(text);
        taskPeriodFrom.setText(text);
        taskPeriodTo.setText(text);
    }

}
