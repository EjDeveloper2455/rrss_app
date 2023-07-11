package com.example.rrssapp.ui.empleado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.rrssapp.Entities.Cargo;
import com.example.rrssapp.Entities.Departamento;
import com.example.rrssapp.Entities.Empleado;
import com.example.rrssapp.R;

import com.example.rrssapp.databinding.ActivityNewEmpleadoBinding;
import com.example.rrssapp.ui.cargo.CargoViewModel;
import com.example.rrssapp.ui.depto.DepartamentoViewModel;

import java.util.List;

public class NewEmpleadoActivity extends AppCompatActivity {
    private ActivityNewEmpleadoBinding binding;
    private DepartamentoViewModel departamentoViewModel;
    private CargoViewModel cargoViewModel;
    private String action;
    private List<Departamento> deptoList;
    private List<Cargo> cargoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewEmpleadoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        departamentoViewModel = new ViewModelProvider(this).get(DepartamentoViewModel.class);
        cargoViewModel = new ViewModelProvider(this).get(CargoViewModel.class);

        deptoList = null;
        cargoList = null;

        action = "new";

        Intent getIntent = getIntent();

        if(getIntent.hasExtra("action")){
            action = getIntent.getStringExtra("action");
            if (action.equals("update")){
                binding.toolbar2.setTitle("Modificar empleado");
                Empleado emp = (Empleado) getIntent.getSerializableExtra("empleado");
                binding.tilDni.getEditText().setText(emp.getDni());
                binding.tilDni.getEditText().setFocusable(false);
                binding.tilDni.getEditText().setKeyListener(null);
                binding.tilNombreCompleto.getEditText().setText(emp.getNombre());
                binding.spnCargo.setSelection(emp.getDepartamento());
                binding.btnSexo.setChecked((emp.getSexo().equals("Femenino")?true:false));
                binding.tilSalario.getEditText().setText(emp.getSalario()+"");
            }
        }

        cargoViewModel.getDataset().observe(this,cargos -> {
            cargoList = cargos;
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, obtenerCargos(cargoList));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spnCargo.setAdapter(adapter);
            if(getIntent.hasExtra("action")){
                if (getIntent.getStringExtra("action").equals("update")){
                    Empleado emp = (Empleado) getIntent.getSerializableExtra("empleado");
                    binding.spnCargo.setSelection(buscarPosCargo(buscarCargo(emp.getCargo(),cargos).getNombreCargo()));
                }
            }
        });

        departamentoViewModel.getDataset().observe(this,departamentos -> {
            deptoList = departamentos;
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, obtenerDeptos(deptoList));
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spnDepartamento.setAdapter(adapter2);
            if(getIntent.hasExtra("action")){
                if (getIntent.getStringExtra("action").equals("update")){
                    Empleado emp = (Empleado) getIntent.getSerializableExtra("empleado");
                    binding.spnDepartamento.setSelection(buscarPosDepto(buscarDepto(emp.getDepartamento(),departamentos).getNombre()));
                }
            }
        });

        action = null;


        binding.btnGuardar.setOnClickListener(v -> {
            int idDepartamento = buscarDepto(binding.spnDepartamento.getSelectedItem().toString(),deptoList).getId();
            int idCargo = buscarCargo(binding.spnCargo.getSelectedItem().toString(),cargoList).getIdCargo();
            if(!validar())return;
            double salario = Double.parseDouble(binding.tilSalario.getEditText().getText().toString());

            Intent replyIntent = new Intent();

            Empleado newEmpleado = new Empleado(binding.tilDni.getEditText().getText().toString(),binding.tilNombreCompleto.getEditText().getText().toString(),idDepartamento,idCargo,(binding.btnSexo.isChecked())?"Femenino":"Masculino",salario,"Activo");
            newEmpleado.setNombre(newEmpleado.getNombre().toUpperCase());
            replyIntent.putExtra("empleado",newEmpleado);
            replyIntent.putExtra("action",(binding.toolbar2.getTitle().equals("Modificar empleado")?"update":"new"));
            setResult(RESULT_OK, replyIntent);
            finish();
        });
    }

    public boolean validar(){
        if (binding.tilDni.getEditText().getText().toString().length()<13 || binding.tilDni.getEditText().getText().toString().length()>13){
            binding.tilDni.getEditText().setError("El DNI solo puede tener 13 caracteres");
            return false;
        }
        if (binding.tilNombreCompleto.getEditText().getText().toString().isEmpty()){
            binding.tilNombreCompleto.getEditText().setError("El nombre no puede quedar vacio");
            return false;
        }

        try {
            double salario = Double.parseDouble(binding.tilSalario.getEditText().getText().toString());
            if (salario<buscarCargo(binding.spnCargo.getSelectedItem().toString(),cargoList).getSueldoCargo()){

                binding.tilSalario.getEditText().setError("El salario base no puede ser menor que el salario mínimo por este cargo "+buscarCargo(binding.spnCargo.getSelectedItem().toString(),cargoList).getNombreCargo());
                return false;
            }
            if(salario>50000){
                binding.tilSalario.getEditText().setError("El salario base no puede exceder los L. 50,000");
                return false;
            }

        }catch (Exception e){
            binding.tilSalario.getEditText().setError("Los caracteres que ingreso son invalidos");
            return false;
        }


        return true;
    }

    public int buscarCodigoDepto(int depto, List<Departamento> list){
        int cont = 0;
        for (Departamento departamento: list) {
            if (depto == departamento.getId())return cont;
            cont++;
        }
        return -1;
    }
    public Cargo buscarCargo(String nombre, List<Cargo> list){
        int cont = 0;
        for (Cargo cargo: list) {
            if (cargo.getNombreCargo().equals(nombre))return cargo;
            cont++;
        }
        return null;
    }
    public Cargo buscarCargo(int id, List<Cargo> list){
        for (Cargo cargo: list) {
            if (cargo.getIdCargo()==id)return cargo;
        }
        return null;
    }
    public int buscarPosCargo(String nombre){
        for (int i=0; i<binding.spnCargo.getCount(); i++){
            String currentItem = binding.spnCargo.getItemAtPosition(i).toString();
            if (currentItem.equals(nombre))return i;
        }
        return -1;
    }
    public int buscarPosDepto(String nombre){
        for (int i=0; i<binding.spnDepartamento.getCount(); i++){
            String currentItem = binding.spnDepartamento.getItemAtPosition(i).toString();
            if (currentItem.equals(nombre))return i;
        }
        return -1;
    }
    public Departamento buscarDepto(String nombre, List<Departamento> list){
        for (Departamento depto: list) {
            if (depto.getNombre().equals(nombre))return depto;
        }
        return null;
    }
    public Departamento buscarDepto(int id, List<Departamento> list){
        for (Departamento depto: list) {
            if (depto.getId()==id)return depto;
        }
        return null;
    }
    public int buscarCodigoCargo(int cargoID, List<Cargo> list){
        int cont = 0;
        for (Cargo cargo: list) {
            if (cargoID == cargo.getIdCargo())return cont;
            cont++;
        }
        return -1;
    }
    public String[] obtenerDeptos(List<Departamento> list){
        String cadena = "";
        for (Departamento depto: list) {
            cadena += depto.getNombre()+";";
        }
        return cadena.split(";");
    }
    public String[] obtenerCargos(List<Cargo> list){
        String cadena = "";
        for (Cargo cargo: list) {
            cadena += cargo.getNombreCargo()+";";
        }
        return cadena.split(";");
    }
}