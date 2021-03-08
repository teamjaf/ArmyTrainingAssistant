package com.eb.basictrainingprep.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.model.ModelRightAnswers;

import java.util.List;

public class AdapterRightQuestions extends RecyclerView.Adapter<AdapterRightQuestions.ViewHolder> {
    Context context;
    List<ModelRightAnswers> modelRightAnswers;

    public AdapterRightQuestions(Context context, List<ModelRightAnswers> modelRightAnswers) {
        this.context = context;
        this.modelRightAnswers = modelRightAnswers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_right_answers,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ModelRightAnswers myModel=modelRightAnswers.get(position);
        holder.txtRightQuestions.setText(myModel.getQuestion());
        holder.txtRightAnswer.setText("Answer: "+myModel.getAnswer());


    }

    @Override
    public int getItemCount() {
        return modelRightAnswers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtRightQuestions,txtRightAnswer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRightQuestions=itemView.findViewById(R.id.txt_right_ques);
            txtRightAnswer=itemView.findViewById(R.id.txt_right_answer);
        }
    }
}
