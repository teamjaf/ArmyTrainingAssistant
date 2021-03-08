package com.eb.basictrainingprep.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eb.basictrainingprep.R;
import com.eb.basictrainingprep.model.ModelPhoneticAlphabetic;

import java.util.List;

public class AdapterPhoneticAlphabetic extends RecyclerView.Adapter<AdapterPhoneticAlphabetic.ViewHolderPhonetic> {

    Context context;
    List<ModelPhoneticAlphabetic> modelPhoneticAlphabeticList;

    public AdapterPhoneticAlphabetic(Context context, List<ModelPhoneticAlphabetic> modelPhoneticAlphabeticList) {
        this.context = context;
        this.modelPhoneticAlphabeticList = modelPhoneticAlphabeticList;
    }

    @NonNull
    @Override
    public ViewHolderPhonetic onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phonetic_alphbetic,parent,false);
        return new ViewHolderPhonetic(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPhonetic holder, int position) {
        ModelPhoneticAlphabetic modelPhonetic=modelPhoneticAlphabeticList.get(position);

        holder.txtDate.setText(modelPhonetic.getDate());
        holder.txtEvent.setText(modelPhonetic.getEvent());
        holder.txtDesc.setText(modelPhonetic.getDesc());

    }

    @Override
    public int getItemCount() {
        return modelPhoneticAlphabeticList.size();
    }

    public class ViewHolderPhonetic extends RecyclerView.ViewHolder {

        TextView txtDate,txtEvent,txtDesc;

        public ViewHolderPhonetic(@NonNull View itemView) {
            super(itemView);

            txtDate=itemView.findViewById(R.id.txt_date);
            txtEvent=itemView.findViewById(R.id.txt_event);
            txtDesc=itemView.findViewById(R.id.txt_descEvent);
        }
    }
}
