package com.example.rrssapp.ui.depto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rrssapp.Entities.Cargo;
import com.example.rrssapp.Entities.Departamento;
import com.example.rrssapp.R;
import com.example.rrssapp.databinding.ActivityNewCargoBinding;
import com.example.rrssapp.databinding.ActivityNewDepartamentoBinding;

public class NewDepartamentoActivity extends AppCompatActivity {

  private ActivityNewDepartamentoBinding binding;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityNewDepartamentoBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());


    Intent getIntent = getIntent();
   if(getIntent.hasExtra("action")){
      if (getIntent.getStringExtra("action").equals("update")){
          binding.toolbar4.setTitle("Modificar departamento");
        Departamento depto = (Departamento) getIntent.getSerializableExtra("departamento");

        binding.tilNombreDepartamento.getEditText().setText(depto.getNombre());
        binding.tilIdDepto.getEditText().setText(depto.getId()+"");
        binding.tilDescripcionDepto.getEditText().setText(depto.getDescripcion());
      }
    }


    binding.btnGuardarDepto.setOnClickListener(v -> {
        if(!validar())return;
      Intent replyIntent = new Intent();
      Departamento departamento = new Departamento(binding.tilNombreDepartamento.getEditText().getText().toString(),binding.tilDescripcionDepto.getEditText().getText().toString(),"Activo");
        departamento.setNombre(departamento.getNombre().toUpperCase());
      replyIntent.putExtra("departamento", departamento);
      if (binding.toolbar4.getTitle().equals("Modificar departamento")){
          departamento.setId(Integer.parseInt(binding.tilIdDepto.getEditText().getText().toString()));
      }
      replyIntent.putExtra("action",(binding.toolbar4.getTitle().equals("Modificar departamento")?"update":"new"));
      setResult(RESULT_OK, replyIntent);
      finish();
    });
  }
    private boolean validar(){
      if(binding.tilNombreDepartamento.getEditText().getText().toString().isEmpty()){
          binding.tilNombreDepartamento.getEditText().setError(getString(R.string.campo_vacio));
          return false;
      }
        if(binding.tilDescripcionDepto.getEditText().getText().toString().isEmpty()){
            binding.tilDescripcionDepto.getEditText().setError(getString(R.string.campo_vacio));
            return false;
        }
      return true;
    }


}
