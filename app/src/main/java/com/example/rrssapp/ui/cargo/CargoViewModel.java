package com.example.rrssapp.ui.cargo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.rrssapp.Entities.Cargo;
import com.example.rrssapp.Repositories.CargoRepository;

import java.util.List;

public class CargoViewModel  extends AndroidViewModel {

    private final LiveData<List<Cargo>> dataset;
    private CargoRepository cargoRepository;

    public CargoRepository getCargoRepository() {
        return cargoRepository;
    }



    public CargoViewModel(@NonNull Application application) {
        super(application);
        cargoRepository = new CargoRepository(application);
        this.dataset = cargoRepository.getDataset();
    }


    public LiveData<List<Cargo>> getDataset() {
        return dataset;
    }


    public void insert(Cargo cargo){
        cargoRepository.insert(cargo);
    }

    public void update(Cargo cargo){
        cargoRepository.update(cargo);
    }

    public void delete(Cargo cargo){
        cargoRepository.delete(cargo);
    }
    public void deleteAll(){
        cargoRepository.deleteAll();
    }



}
