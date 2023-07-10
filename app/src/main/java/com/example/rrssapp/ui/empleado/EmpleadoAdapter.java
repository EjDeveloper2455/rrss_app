package com.example.rrssapp.ui.empleado;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rrssapp.Entities.Empleado;
import com.example.rrssapp.OnItemClickListener;
import com.example.rrssapp.R;
import com.example.rrssapp.Entities.Empleado;
import com.example.rrssapp.OnItemClickListener;
import com.example.rrssapp.R;
import com.example.rrssapp.databinding.EmpleadoItemBinding;

import java.util.List;

public class EmpleadoAdapter extends RecyclerView.Adapter<EmpleadoAdapter.ViewHolder> {
    private List<Empleado> dataset;
    private OnItemClickListener<Empleado> itemClick;

    public EmpleadoAdapter(List<Empleado> dataset, OnItemClickListener<Empleado> itemClick) {
        this.dataset = dataset;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EmpleadoItemBinding binding = EmpleadoItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Empleado empleado = dataset.get(position);
        String nombre = empleado.getNombre();
        String[] nombreArray = nombre.split(" ");
        if(nombreArray.length>2)nombre = nombreArray[0]+" "+nombreArray[1].charAt(0)+". "+nombreArray[2];
        holder.binding.tvNombre.setText(nombre);
        holder.binding.tvDNI.setText(empleado.getDni());
        if(empleado.getSexo().equals("Femenino")){
            holder.binding.iconoEmpleado.setImageResource(R.drawable.empleada);
        }else{
            holder.binding.iconoEmpleado.setImageResource(R.drawable.empleado);
        }
        if (empleado.getEstado().equals("Inactivo")){
            holder.binding.cardLayout.setBackgroundColor(Color.rgb(90,90,90));
        }else{
            holder.binding.cardLayout.setBackgroundColor(Color.rgb(2,114,234));
        }
        holder.setOnClickListener(empleado,itemClick);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
    public void setItems(List<Empleado> dataset){
        this.dataset = dataset;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EmpleadoItemBinding binding;
        public ViewHolder(@NonNull EmpleadoItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
        public void setOnClickListener(Empleado empleado, OnItemClickListener<Empleado> itemClick){
            binding.imgVer.setOnClickListener(v ->{
                itemClick.onItemClick(empleado);
            });


        }
    }
}
