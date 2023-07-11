package com.example.rrssapp.ui.cargo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.rrssapp.Entities.Cargo;
import com.example.rrssapp.Entities.Departamento;
import com.example.rrssapp.Entities.Empleado;
import com.example.rrssapp.R;
import com.example.rrssapp.databinding.ActivityNewCargoBinding;
import com.example.rrssapp.ui.depto.DepartamentoViewModel;

import java.util.List;

public class NewCargoActivity extends AppCompatActivity {

  private ActivityNewCargoBinding binding;
    private DepartamentoViewModel departamentoViewModel;
    private List<Departamento> deptoList;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityNewCargoBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    deptoList = null;


    Intent getIntent = getIntent();
   if(getIntent.hasExtra("action")){
      if (getIntent.getStringExtra("action").equals("update")){
          binding.toolbar3.setTitle("Modificar cargo");
        Cargo car = (Cargo) getIntent.getSerializableExtra("cargo");

        binding.tilNombreCargo.getEditText().setText(car.getNombreCargo());
        binding.tilIdCargo.getEditText().setText(car.getIdCargo()+"");
        binding.tilDescripcionCargo.getEditText().setText(car.getDescripcionCargo());
        binding.spnDepartamentoCargo.setSelection(car.getDepartamento());
        binding.tilSalarioCargo.getEditText().setText(car.getSueldoCargo()+"");
      }
    }

      departamentoViewModel = new ViewModelProvider(this).get(DepartamentoViewModel.class);

      departamentoViewModel.getDataset().observe(this,departamentos -> {
          deptoList = departamentos;
          ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, obtenerDeptos(deptoList));
          adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          binding.spnDepartamentoCargo.setAdapter(adapter2);
          if(getIntent.hasExtra("action")){
              if (getIntent.getStringExtra("action").equals("update")){
                  Cargo emp = (Cargo) getIntent.getSerializableExtra("cargo");
                  binding.spnDepartamentoCargo.setSelection(buscarPosDepto(buscarDepto(emp.getDepartamento(),departamentos).getNombre()));
              }
          }
      });

    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.departamento_list, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    binding.spnDepartamentoCargo.setAdapter(adapter);

    binding.btnGuardarCargo.setOnClickListener(v -> {
      int idDepartamento = buscarDepto(binding.spnDepartamentoCargo.getSelectedItem().toString(),deptoList).getId();

      double salario = Double.parseDouble(binding.tilSalarioCargo.getEditText().getText().toString());
      Intent replyIntent = new Intent();
      Cargo cargo = new Cargo(binding.tilNombreCargo.getEditText().getText().toString(),binding.tilDescripcionCargo.getEditText().getText().toString(),salario,idDepartamento);
      cargo.setNombreCargo(cargo.getNombreCargo().toUpperCase());
      replyIntent.putExtra("cargo", cargo);
      if(binding.toolbar3.getTitle().equals("Modificar cargo")){
          cargo.setIdCargo(Integer.parseInt(binding.tilIdCargo.getEditText().getText().toString()));
      }
      replyIntent.putExtra("action",(binding.toolbar3.getTitle().equals("Modificar cargo")?"update":"new"));
      setResult(RESULT_OK, replyIntent);
      finish();
    });
  }
    public String[] obtenerDeptos(List<Departamento> list){
        String cadena = "";
        for (Departamento depto: list) {
            cadena += depto.getNombre()+";";
        }
        return cadena.split(";");
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
    public int buscarPosDepto(String nombre){
        for (int i=0; i<binding.spnDepartamentoCargo.getCount(); i++){
            String currentItem = binding.spnDepartamentoCargo.getItemAtPosition(i).toString();
            if (currentItem.equals(nombre))return i;
        }
        return -1;
    }

}
