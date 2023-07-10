package com.example.rrssapp.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.rrssapp.DAO.CargoDao;
import com.example.rrssapp.DataBase.RHDataBase;
import com.example.rrssapp.Entities.Cargo;

import java.util.List;

public class CargoRepository {

    private CargoDao dao;
    private LiveData<List<Cargo>> dataset;


    public CargoRepository(Application app){
        RHDataBase db = RHDataBase.getDataBase(app);
        this.dao = db.cargoDao();
        this.dataset = dao.getCargo();

    }

    public LiveData<List<Cargo>> getDataset(){
        return this.dataset;
    }


    public void insert(Cargo cargo){
        RHDataBase.databaseWriteExecutor.execute(() -> {
            dao.insert(cargo);
        });
    }
    public void update(Cargo cargo){
        RHDataBase.databaseWriteExecutor.execute(() -> {
            dao.update(cargo);
        });
    }
    public void delete(Cargo cargo){
        RHDataBase.databaseWriteExecutor.execute(() -> {
            dao.delete(cargo);
        });
    }
    public void deleteAll(){
        RHDataBase.databaseWriteExecutor.execute(() -> {
            dao.deleteAll();
        });
    }

}
