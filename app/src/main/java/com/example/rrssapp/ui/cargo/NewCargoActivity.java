package com.example.rrssapp.ui.cargo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rrssapp.Entities.Cargo;
import com.example.rrssapp.R;
import com.example.rrssapp.databinding.ActivityNewCargoBinding;

public class NewCargoActivity extends AppCompatActivity {

  private ActivityNewCargoBinding binding;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityNewCargoBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());


    Intent getIntent = getIntent();
   if(getIntent.hasExtra("action")){
      if (getIntent.getStringExtra("action").equals("update")){
        Cargo car = (Cargo) getIntent.getSerializableExtra("Cargo");
        binding.tilNombreCargo.getEditText().setText(car.getNombreCargo());
        binding.tilNombreCargo.getEditText().setEnabled(true);
        binding.tilDescripcionCargo.getEditText().setText(car.getDescripcionCargo());
        binding.spnDepartamentoCargo.setSelection(car.getDepartamento());
        binding.tilSalarioCargo.getEditText().setText(car.getSueldoCargo()+"");
      }
    }

    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.departamento_list, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    binding.spnDepartamentoCargo.setAdapter(adapter);



    binding.btnGuardarCargo.setOnClickListener(v -> {
      int idDepartamento = binding.spnDepartamentoCargo.getSelectedItemPosition();


      double salario = Double.parseDouble(binding.tilSalarioCargo.getEditText().getText().toString());

      Intent replyIntent = new Intent();

      Cargo cargo = new Cargo(binding.tilNombreCargo.getEditText().getText().toString(),binding.tilDescripcionCargo.getEditText().getText().toString(),salario,idDepartamento);

      replyIntent.putExtra("cargo", (CharSequence) cargo);
      setResult(RESULT_OK, replyIntent);
      finish();
    });
  }



}
