package com.example.rrssapp.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.rrssapp.DAO.EmpleadoDao;
import com.example.rrssapp.DataBase.RHDataBase;
import com.example.rrssapp.Entities.Empleado;

import java.util.List;

public class EmpleadoRepository {
    private EmpleadoDao dao;
    private LiveData<List<Empleado>> dataset;

    public EmpleadoRepository(Application app){
        RHDataBase db = RHDataBase.getDataBase(app);
        this.dao = db.empleadoDao();
        this.dataset = dao.getEmpleados();
    }

    public LiveData<List<Empleado>> getDataset(){
        return this.dataset;
    }
    public LiveData<List<Empleado>> getDatasetEstado(String estado){
        LiveData<List<Empleado>> datasetEstado = dao.getEmpleadosEstado(estado);
        return datasetEstado;
    }

    public LiveData<Double> getMontoTotal(){
        return dao.getMontoTotal();
    }

    public void insert(Empleado empleado){
        RHDataBase.databaseWriteExecutor.execute(() -> {
            dao.insert(empleado);
        });
    }
    public void update(Empleado empleado){
        RHDataBase.databaseWriteExecutor.execute(() -> {
            dao.update(empleado);
        });
    }
    public void delete(Empleado empleado){
        RHDataBase.databaseWriteExecutor.execute(() -> {
            dao.delete(empleado);
        });
    }
    public void deleteAll(){
        RHDataBase.databaseWriteExecutor.execute(() -> {
            dao.deleteAll();
        });
    }
}
