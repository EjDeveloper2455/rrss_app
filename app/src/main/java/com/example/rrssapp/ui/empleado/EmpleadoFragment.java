package com.example.rrssapp.ui.empleado;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rrssapp.Entities.Cargo;
import com.example.rrssapp.Entities.Departamento;
import com.example.rrssapp.Entities.Empleado;
import com.example.rrssapp.OnItemClickListener;
import com.example.rrssapp.R;
import com.example.rrssapp.databinding.FragmentEmpleadoBinding;
import com.example.rrssapp.ui.cargo.CargoViewModel;
import com.example.rrssapp.ui.depto.DepartamentoViewModel;

import java.util.ArrayList;
import java.util.List;

public class EmpleadoFragment extends Fragment implements OnItemClickListener<Empleado> {

    private FragmentEmpleadoBinding binding;
    private EmpleadoAdapter empleadoAdapter;
    private ActivityResultLauncher<Intent> launcher;
    private EmpleadoViewModel empleadoViewModel;

    private List<Departamento> deptoList;
    private List<Cargo> cargoList;

    private DepartamentoViewModel departamentoViewModel;
    private CargoViewModel cargoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        empleadoViewModel =
                new ViewModelProvider(this).get(EmpleadoViewModel.class);
        departamentoViewModel = new ViewModelProvider(this).get(DepartamentoViewModel.class);
        cargoViewModel = new ViewModelProvider(this).get(CargoViewModel.class);

        binding = FragmentEmpleadoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        empleadoAdapter = new EmpleadoAdapter(new ArrayList<>(),this);

        empleadoViewModel.getDataset().observe(getViewLifecycleOwner(), empleados -> {
            empleadoAdapter.setItems(empleados);
        });
        departamentoViewModel.getDataset().observe(getViewLifecycleOwner(), departamentos -> {
            deptoList = departamentos;
        });
        cargoViewModel.getDataset().observe(getViewLifecycleOwner(), cargos -> {
            cargoList = cargos;
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.ver_estados_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnVerEstados.setAdapter(adapter);

        binding.spnVerEstados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setItems(binding.spnVerEstados.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Acciones a realizar cuando no se selecciona ningún elemento del Spinner
            }
        });

        setupRecyrcleView();

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();


                        Empleado empleado = (Empleado) data.getSerializableExtra("empleado");Empleado n = empleado;
                        if(data.hasExtra("action")) {
                            if(data.getStringExtra("action").equals("new"))empleadoViewModel.insert(empleado);
                            else empleadoViewModel.update(empleado);
                        }

                        setItems(binding.spnVerEstados.getSelectedItemPosition());
                    } else {
                        Toast.makeText(this.getContext(),"Operación cancelada",Toast.LENGTH_LONG).show();
                    }
                }
        );

        binding.fabEmpleado.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), NewEmpleadoActivity.class);
            intent.putExtra("action","new");
            launcher.launch(intent);
        });

        return root;
    }
    private void setupRecyrcleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        binding.rvEmpleados.setLayoutManager(linearLayoutManager);
        binding.rvEmpleados.setAdapter(empleadoAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setItems(int pos){

        if (pos == 0) {
            empleadoViewModel.getDataset().observe(getViewLifecycleOwner(), empleados -> {
                empleadoAdapter.setItems(empleados);
            });
        } else if (pos == 1){
            empleadoViewModel.getDatasetEstado("Activo").observe(getViewLifecycleOwner(), empleados -> {
                empleadoAdapter.setItems(empleados);
            });
        }else{
            empleadoViewModel.getDatasetEstado("Inactivo").observe(getViewLifecycleOwner(), empleados -> {
                empleadoAdapter.setItems(empleados);
            });
        }
    }

    @Override
    public void onItemClick(Empleado data) {
        modalEmpleado(data);
    }

    private void modalEmpleado(Empleado empleado){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modal_empleado,null);
        builder.setView(view);

        AlertDialog dialog = builder.create();


        TextView tvCampos[] =  {view.findViewById(R.id.tvModalDNI),view.findViewById(R.id.tvModalNombre),
                view.findViewById(R.id.tvModalNombre1),view.findViewById(R.id.tvModalDepartamento),
                view.findViewById(R.id.tvModalCargo),view.findViewById(R.id.tvModalSexo),
                view.findViewById(R.id.tvModalSalario),view.findViewById(R.id.tvModalEstado)};
        tvCampos[0].setText(getString(R.string.lbl_modal_dni,empleado.getDni()));
        tvCampos[1].setText(R.string.lbl_nodal_nombre_completo);
        tvCampos[2].setText(empleado.getNombre());
        tvCampos[3].setText(getString(R.string.lbl_modal_departamento,buscarDepto(empleado.getDepartamento(),deptoList).getNombre()));
        tvCampos[4].setText(getString(R.string.lbl_modal_cargo,buscarCargo(empleado.getCargo(),cargoList).getNombreCargo()));
        tvCampos[5].setText(getString(R.string.lbl_modal_sexo,empleado.getSexo()));
        tvCampos[6].setText(getString(R.string.lbl_modal_salario,empleado.getSalario()+""));
        tvCampos[7].setText(getString(R.string.lbl_modal_estado,empleado.getEstado()));
        ConstraintLayout layout = view.findViewById(R.id.layoutPrincipal);

        Button btnCerrar = view.findViewById(R.id.btnCerrar);
        btnCerrar.setOnClickListener(v ->{
            dialog.cancel();
        });

        ImageView iconoEmpleado = view.findViewById(R.id.imgEmpleadoModal);

        if(empleado.getSexo().equals("Femenino")){
            iconoEmpleado.setImageResource(R.drawable.empleada);
        }else{
            iconoEmpleado.setImageResource(R.drawable.empleado);
        }

        ImageView imgEliminar = view.findViewById(R.id.imgEliminar);
        imgEliminar.setOnClickListener(v ->{
            dialog.cancel();
            modalConfimarEliminar(empleado);
        });

        ImageView imgActivar = view.findViewById(R.id.imgHabilitar);
        imgActivar.setOnClickListener(v ->{
            dialog.cancel();

            if(empleado.getEstado().equalsIgnoreCase("Inactivo")){
                empleado.setEstado("Activo");
                layout.setBackgroundColor(ContextCompat.getColor(this.getContext(), R.color.purple_400));

            }else{
                empleado.setEstado("Inactivo");
                layout.setBackgroundColor(ContextCompat.getColor(this.getContext(), R.color.gray0));
            }
            empleadoViewModel.update(empleado);
            setItems(binding.spnVerEstados.getSelectedItemPosition());
        });

        if(empleado.getEstado().equals("Inactivo")){
            imgActivar.setImageResource(R.drawable.habilitar);
        }else{
            imgActivar.setImageResource(R.drawable.deshabilitar);
        }

        ImageView imgModificar = view.findViewById(R.id.imgModificar);
        imgModificar.setOnClickListener(v ->{
            Intent intent = new Intent(requireContext(), NewEmpleadoActivity.class);
            intent.putExtra("action","update");
            intent.putExtra("empleado",empleado);
            launcher.launch(intent);
            dialog.cancel();
        });

        dialog.show();
    }

    public void modalConfimarEliminar(Empleado empleado){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle(empleado.getNombre()+"\n"+empleado.getDni());
        builder.setMessage("¿Está seguro que desea eliminar este empleado?\nSi lo elimina no volverá a recuperar información acerca de este empleado");
        builder.setPositiveButton("Si, deseo eliminar", (dialog, which) -> {
            empleadoViewModel.delete(empleado);
            setItems(binding.spnVerEstados.getSelectedItemPosition());
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> {

        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public Departamento buscarDepto(int id, List<Departamento> list){
        for (Departamento depto: list) {
            if (depto.getId()==id)return depto;
        }
        return null;
    }

    public Cargo buscarCargo(int id, List<Cargo> list){
        for (Cargo cargo: list) {
            if (cargo.getIdCargo()==id)return cargo;
        }
        return null;
    }

}