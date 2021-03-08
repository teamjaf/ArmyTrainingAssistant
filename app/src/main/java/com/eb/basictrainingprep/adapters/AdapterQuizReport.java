package com.eb.basictrainingprep.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.model.QuizReportModel;

import java.util.List;

public class AdapterQuizReport extends RecyclerView.Adapter<AdapterQuizReport.QuizReportViewHolder> {
    Context context;
    List<QuizReportModel> quizReportModels;

    public AdapterQuizReport(Context context, List<QuizReportModel> quizReportModels) {
        this.context = context;
        this.quizReportModels = quizReportModels;
    }

    @NonNull
    @Override
    public QuizReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_quiz_report, parent, false);

        return new QuizReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizReportViewHolder holder, int position) {
        QuizReportModel quizReport = quizReportModels.get(position);
        holder.txtQuizDate.setText(quizReport.getQuizDate());
        holder.txtQuizStatus.setText(quizReport.getQuizStatus());
        holder.txtQuizType.setText(quizReport.getQuizType());
        holder.txtQuizScore.setText(quizReport.getQuizScore() + "  out of  100%");
    }

    @Override
    public int getItemCount() {
        return quizReportModels.size();
    }

    public class QuizReportViewHolder extends RecyclerView.ViewHolder {

        TextView txtQuizStatus, txtQuizType, txtQuizScore, txtQuizDate;

        public QuizReportViewHolder(@NonNull View itemView) {
            super(itemView);

            txtQuizType = itemView.findViewById(R.id.txt_quizType);
            txtQuizDate = itemView.findViewById(R.id.txt_quiz_date);
            txtQuizStatus = itemView.findViewById(R.id.txt_quiz_status);
            txtQuizScore = itemView.findViewById(R.id.txt_quiz_score);
        }
    }
}
