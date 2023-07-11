package com.example.rrssapp.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.rrssapp.DAO.DepartamentoDao;
import com.example.rrssapp.DAO.PlanillaDao;
import com.example.rrssapp.DataBase.RHDataBase;
import com.example.rrssapp.Entities.Departamento;
import com.example.rrssapp.Entities.Planilla;

import java.util.List;

public class PlanillaRepository {
    private PlanillaDao dao;

    private LiveData<List<Planilla>> dataset;
    public PlanillaRepository(Application app){
        RHDataBase db = RHDataBase.getDataBase(app);
        this.dao = db.planillaDao();
        this.dataset = dao.getPlanilla();
    }
    public LiveData<List<Planilla>> getDataset() {
        return dataset;
    }

    public void insert(Planilla planilla){
        RHDataBase.databaseWriteExecutor.execute(() -> {
            dao.insert(planilla);
        });
    }
    public void update(Planilla planilla){
        RHDataBase.databaseWriteExecutor.execute(() -> {
            dao.update(planilla);
        });
    }

    public void deleteAll(){
        RHDataBase.databaseWriteExecutor.execute(() -> {
            dao.deleteAll();
        });
    }
}
