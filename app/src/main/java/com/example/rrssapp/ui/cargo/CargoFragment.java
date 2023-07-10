package com.example.rrssapp.ui.cargo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rrssapp.Entities.Cargo;
import com.example.rrssapp.OnItemClickListener;
import com.example.rrssapp.databinding.FragmentRvCargosBinding;

import java.util.ArrayList;

public class CargoFragment extends Fragment implements OnItemClickListener<Cargo> {

    private FragmentRvCargosBinding binding;
    private CargoAdapter cargoAdapter;
    private ActivityResultLauncher<Intent> launcher;
    private CargoViewModel cargoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cargoViewModel =
                new ViewModelProvider(this).get(CargoViewModel.class);

        binding = FragmentRvCargosBinding.inflate(inflater,container, false);
        View root = binding.getRoot();
        cargoAdapter = new CargoAdapter(new ArrayList<>(),this);


        cargoViewModel.getDataset().observe(getViewLifecycleOwner(), cargos -> {
            cargoAdapter.setItems(cargos);
        });

        setupRecyrcleView();
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Cargo cargo = (Cargo) data.getSerializableExtra("cargo");
                        cargoViewModel.insert(cargo);
                    } else {
                        Toast.makeText(this.getContext(),"Operación cancelada",Toast.LENGTH_LONG).show();
                    }
                }
        );

        binding.fabCargo.setOnClickListener(v -> {
            Intent intent = new Intent (requireContext(), NewCargoActivity.class);
            intent.putExtra("action","new");
            launcher.launch(intent);
        });



        return root;
    }

    private void setupRecyrcleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        binding.rvCargos.setLayoutManager(linearLayoutManager);
        binding.rvCargos.setAdapter(cargoAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(Cargo data) {

    }
}
