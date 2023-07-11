package com.example.rrssapp.ui.planilla;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rrssapp.Entities.Empleado;
import com.example.rrssapp.Entities.Planilla;
import com.example.rrssapp.OnItemClickListener;
import com.example.rrssapp.R;
import com.example.rrssapp.databinding.EmpleadoItemBinding;
import com.example.rrssapp.databinding.PlanillaItemBinding;

import java.util.List;

public class PlanillaAdapter extends RecyclerView.Adapter<PlanillaAdapter.ViewHolder> {
    private List<Planilla> dataset;
    private OnItemClickListener<Planilla> itemClick;

    public PlanillaAdapter(List<Planilla> dataset, OnItemClickListener<Planilla> itemClick) {
        this.dataset = dataset;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PlanillaItemBinding binding = PlanillaItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Planilla planilla = dataset.get(position);

        holder.binding.tvPlanillaFecha.setText(planilla.getFecha().toString());
        holder.binding.tvPlanillaMonto.setText("L. "+planilla.getMonto());

        holder.setOnClickListener(planilla,itemClick);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
    public void setItems(List<Planilla> dataset){
        this.dataset = dataset;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        PlanillaItemBinding binding;
        public ViewHolder(@NonNull PlanillaItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
        public void setOnClickListener(Planilla planilla, OnItemClickListener<Planilla> itemClick){
            binding.imgPlanillaVer.setOnClickListener(v ->{
                itemClick.onItemClick(planilla);
            });


        }
    }
}
