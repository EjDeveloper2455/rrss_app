package com.example.rrssapp.ui.depto;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rrssapp.Entities.Departamento;
import com.example.rrssapp.Entities.Empleado;
import com.example.rrssapp.OnItemClickListener;
import com.example.rrssapp.R;
import com.example.rrssapp.databinding.DepartamentoItemBinding;
import com.example.rrssapp.databinding.EmpleadoItemBinding;

import java.util.List;

public class DepartamentoAdapter extends RecyclerView.Adapter<DepartamentoAdapter.ViewHolder> {
    private List<Departamento> dataset;
    private OnItemClickListener<Departamento> itemClick;

    public DepartamentoAdapter(List<Departamento> dataset, OnItemClickListener<Departamento> itemClick) {
        this.dataset = dataset;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DepartamentoItemBinding binding = DepartamentoItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Departamento departamento = dataset.get(position);
        String nombre = departamento.getNombre();

        holder.binding.tvDeptoNombre.setText(nombre);
        holder.binding.tvIdDepto.setText(departamento.getId()+"");

        holder.setOnClickListener(departamento,itemClick);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
    public void setItems(List<Departamento> dataset){
        this.dataset = dataset;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        DepartamentoItemBinding binding;
        public ViewHolder(@NonNull DepartamentoItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
        public void setOnClickListener(Departamento departamento, OnItemClickListener<Departamento> itemClick){
            binding.imgEditDepto.setOnClickListener(v ->{
                itemClick.onItemClick(departamento);
            });

        }
    }
}
