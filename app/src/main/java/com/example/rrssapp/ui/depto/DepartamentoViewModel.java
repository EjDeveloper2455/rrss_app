package com.example.rrssapp.ui.depto;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.rrssapp.Entities.Departamento;
import com.example.rrssapp.Repositories.DepartamentoRepository;

import java.util.List;

public class DepartamentoViewModel extends AndroidViewModel {

    private final LiveData<List<Departamento>> dataset;
    private DepartamentoRepository departamentoRepository;

    public DepartamentoViewModel(@NonNull Application app) {
        super(app);
        departamentoRepository = new DepartamentoRepository(app);
        this.dataset = departamentoRepository.getDataset();
    }

    public LiveData<List<Departamento>> getDataset() {
        return dataset;
    }
    public void insert(Departamento departamento){
        departamentoRepository.insert(departamento);
    }

    public void update(Departamento departamento){
        departamentoRepository.update(departamento);
    }

    public void deleteAll(){
        departamentoRepository.deleteAll();
    }
}