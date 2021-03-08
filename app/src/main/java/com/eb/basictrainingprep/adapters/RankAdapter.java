package com.eb.basictrainingprep.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.model.RanksModel;

import java.util.List;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    Context context;
    List<RanksModel> ranksModelList;

    public RankAdapter(Context context, List<RanksModel> ranksModelList) {
        this.context = context;
        this.ranksModelList = ranksModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_rank,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RanksModel model=ranksModelList.get(position);
        String catName=model.getRankCatName();
        holder.txtCatName.setVisibility(View.VISIBLE);
        holder.txtCatName.setText(model.getRankCatName());
        if (position != 0) {
            if (catName.equals(ranksModelList.get(position - 1).getRankCatName())) {
                holder.txtCatName.setVisibility(View.GONE);

            }
        }

        holder.txtRankTitle.setText(model.getRankTitle());
        holder.txtRankAbr.setText(model.getRankAbr());
        holder.txtRankPayGrade.setText(model.getRankPayGrade());
        holder.txtRankRemarks.setText(model.getRankRemarks());
 //       Log.e("GetRanks",model.getRankCatName());
//        Log.e("GetRanks",model.getRankTitle());
//        Log.e("GetRanks",model.getRankAbr());
//        Log.e("GetRanks",model.getRankPayGrade());
//        Log.e("GetRanks",model.getRankRemarks());

    }

    @Override
    public int getItemCount() {
        return ranksModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCatName,txtRankTitle,txtRankAbr,txtRankPayGrade,txtRankRemarks;

        public ViewHolder(@NonNull View view) {
            super(view);
            txtCatName=view.findViewById(R.id.txt_rankCatName);
            txtRankTitle=view.findViewById(R.id.txt_rankTitle);
            txtRankAbr=view.findViewById(R.id.txt_rankAbr);
            txtRankPayGrade=view.findViewById(R.id.txt_rankPayGrad);
            txtRankRemarks=view.findViewById(R.id.txt_rankRemarks);
        }
    }
}
