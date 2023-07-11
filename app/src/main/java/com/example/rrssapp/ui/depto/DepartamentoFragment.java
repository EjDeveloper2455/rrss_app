package com.example.rrssapp.ui.depto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rrssapp.Entities.Cargo;
import com.example.rrssapp.Entities.Departamento;
import com.example.rrssapp.OnItemClickListener;
import com.example.rrssapp.databinding.FragmentDepartamentoBinding;
import com.example.rrssapp.ui.cargo.NewCargoActivity;
import com.example.rrssapp.ui.empleado.EmpleadoAdapter;

import java.util.ArrayList;

public class DepartamentoFragment extends Fragment implements OnItemClickListener<Departamento> {

    private FragmentDepartamentoBinding binding;
    DepartamentoViewModel departamentoViewModel;
    private ActivityResultLauncher<Intent> launcher;
    DepartamentoAdapter departamentoAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDepartamentoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        departamentoViewModel = new ViewModelProvider(this).get(DepartamentoViewModel.class);

        departamentoAdapter = new DepartamentoAdapter(new ArrayList<>(),this);
        setupRecyrcleView();

        departamentoViewModel.getDataset().observe(getViewLifecycleOwner(), departamentos -> {
            departamentoAdapter.setItems(departamentos);
        });

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Departamento departamento = (Departamento) data.getSerializableExtra("departamento");
                        if (data.getStringExtra("action").equals("new"))departamentoViewModel.insert(departamento);
                        else {
                            departamentoViewModel.update(departamento);

                        }
                        departamentoViewModel.getDataset().observe(getViewLifecycleOwner(), cargos -> {
                            departamentoAdapter.setItems(cargos);
                        });
                    } else {
                        Toast.makeText(this.getContext(),"Operación cancelada",Toast.LENGTH_LONG).show();
                    }
                }
        );

        binding.fabDepto.setOnClickListener(v -> {
            Intent intent = new Intent (requireContext(), NewDepartamentoActivity.class);
            intent.putExtra("action","new");
            launcher.launch(intent);
        });

        return root;
    }
    private void setupRecyrcleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        binding.rvDepto.setLayoutManager(linearLayoutManager);
        binding.rvDepto.setAdapter(departamentoAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(Departamento data) {
        Intent intent = new Intent (requireContext(), NewDepartamentoActivity.class);
        intent.putExtra("action","update");
        intent.putExtra("departamento",data);
        launcher.launch(intent);
    }
}