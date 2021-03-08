package com.eb.basictrainingprep.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.model.ModelImportantArmyDates;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterImportantArmyDates extends RecyclerView.Adapter<AdapterImportantArmyDates.ViewHolderDates> {
    Context context;
    List<ModelImportantArmyDates> modeldates;

    public AdapterImportantArmyDates(Context context, List<ModelImportantArmyDates> modeldates) {
        this.context = context;
        this.modeldates = modeldates;
    }

    @NonNull
    @Override
    public ViewHolderDates onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_important_army_dates,parent,false);

        return new ViewHolderDates(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDates holder, int position) {
        ModelImportantArmyDates armyDates=modeldates.get(position);

        holder.txtDates.setText(armyDates.getDate());
        holder.txtDesc.setText(armyDates.getDesc());

    }

    @Override
    public int getItemCount() {
        return modeldates.size();
    }

    public static class ViewHolderDates extends RecyclerView.ViewHolder {

        TextView txtDates,txtDesc;

        public ViewHolderDates(@NonNull View itemView) {
            super(itemView);

            txtDates=itemView.findViewById(R.id.txt_armyDate);
            txtDesc=itemView.findViewById(R.id.txt_descArmy);
        }
    }
}
