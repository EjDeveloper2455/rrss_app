package com.example.rrssapp.ui.empleado;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.rrssapp.Entities.Empleado;
import com.example.rrssapp.Repositories.EmpleadoRepository;

import java.util.List;

public class EmpleadoViewModel extends AndroidViewModel {
    private final LiveData<List<Empleado>> dataset;
    private EmpleadoRepository empleadoRepository;

    public EmpleadoRepository getEmpleadoRepository() {
        return empleadoRepository;
    }

    public EmpleadoViewModel(@NonNull Application app) {
        super(app);
        empleadoRepository = new EmpleadoRepository(app);
        this.dataset = empleadoRepository.getDataset();
    }

    public LiveData<List<Empleado>> getDataset() {
        return dataset;
    }
    public LiveData<List<Empleado>> getDatasetEstado(String estado){
        LiveData<List<Empleado>> datasetEstado = empleadoRepository.getDatasetEstado(estado);
        return datasetEstado;
    }

    public LiveData<Double> getMontoTotal(){
        return empleadoRepository.getMontoTotal();
    }
    public void insert(Empleado empleado){
        empleadoRepository.insert(empleado);
    }

    public void update(Empleado empleado){
        empleadoRepository.update(empleado);
    }

    public void delete(Empleado empleado){
        empleadoRepository.delete(empleado);
    }
    public void deleteAll(){
        empleadoRepository.deleteAll();
    }
}