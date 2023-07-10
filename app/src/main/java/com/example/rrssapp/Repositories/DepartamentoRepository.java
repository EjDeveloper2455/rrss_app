package com.example.rrssapp.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.rrssapp.DAO.DepartamentoDao;
import com.example.rrssapp.DAO.EmpleadoDao;
import com.example.rrssapp.DataBase.RHDataBase;
import com.example.rrssapp.Entities.Departamento;
import com.example.rrssapp.Entities.Empleado;

import java.util.List;

public class DepartamentoRepository {

    private DepartamentoDao dao;

    private LiveData<List<Departamento>> dataset;
    public DepartamentoRepository(Application app){
        RHDataBase db = RHDataBase.getDataBase(app);
        this.dao = db.departamentoDao();
        this.dataset = dao.getDepartamentos();
    }
    public LiveData<List<Departamento>> getDataset() {
        return dataset;
    }

    public void insert(Departamento departamento){
        RHDataBase.databaseWriteExecutor.execute(() -> {
            dao.insert(departamento);
        });
    }
    public void update(Departamento departamento){
        RHDataBase.databaseWriteExecutor.execute(() -> {
            dao.update(departamento);
        });
    }

    public void deleteAll(){
        RHDataBase.databaseWriteExecutor.execute(() -> {
            dao.deleteAll();
        });
    }
}
