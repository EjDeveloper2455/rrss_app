package com.example.rrssapp.ui.planilla;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rrssapp.Entities.Empleado;
import com.example.rrssapp.Entities.Planilla;
import com.example.rrssapp.Repositories.EmpleadoRepository;
import com.example.rrssapp.Repositories.PlanillaRepository;

import java.util.List;

public class PlanillaViewModel extends AndroidViewModel {

    private final LiveData<List<Planilla>> dataset;
    private PlanillaRepository planillaRepository;

    public PlanillaRepository getPlanillaRepository() {
        return planillaRepository;
    }

    public PlanillaViewModel(@NonNull Application app) {
        super(app);
        planillaRepository = new PlanillaRepository(app);
        this.dataset = planillaRepository.getDataset();
    }

    public LiveData<List<Planilla>> getDataset() {
        return dataset;
    }


    public void insert(Planilla planilla){
        planillaRepository.insert(planilla);
    }

    public void update(Planilla planilla){
        planillaRepository.update(planilla);
    }

    public void deleteAll(){
        planillaRepository.deleteAll();
    }
}