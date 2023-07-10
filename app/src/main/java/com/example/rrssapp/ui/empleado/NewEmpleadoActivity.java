package com.example.rrssapp.ui.empleado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.rrssapp.Entities.Empleado;
import com.example.rrssapp.R;

import com.example.rrssapp.databinding.ActivityNewEmpleadoBinding;
import com.example.rrssapp.ui.slideshow.DepartamentoViewModel;

public class NewEmpleadoActivity extends AppCompatActivity {
    private ActivityNewEmpleadoBinding binding;
    private DepartamentoViewModel departamentoViewModel;
    String action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewEmpleadoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        departamentoViewModel = new ViewModelProvider(this).get(DepartamentoViewModel.class);

        departamentoViewModel.getDataset().observe(this,departamentos -> {
        });

        action = null;

        Intent getIntent = getIntent();
        if(getIntent.hasExtra("action")){
            Toast.makeText(this,"Llega aqui",Toast.LENGTH_LONG);
            action = getIntent.getStringExtra("action");
            if (action.equals("update")){
                Empleado emp = (Empleado) getIntent.getSerializableExtra("empleado");
                binding.tilDni.getEditText().setText(emp.getDni());
                binding.tilDni.getEditText().setEnabled(true);
                binding.tilNombreCompleto.getEditText().setText(emp.getNombre());
                binding.spnCargo.setSelection(emp.getDepartamento());
                binding.spnDepartamento.setSelection(emp.getCargo());
                binding.btnSexo.setChecked((emp.getSexo().equals("Femenino")?true:false));
                binding.tilSalario.getEditText().setText(emp.getSalario()+"");
            }
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.departamento_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnDepartamento.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.cargo_list, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnCargo.setAdapter(adapter2);

        binding.btnGuardar.setOnClickListener(v -> {
            int idDepartamento = binding.spnDepartamento.getSelectedItemPosition();
            int idCargo = binding.spnCargo.getSelectedItemPosition();

            double salario = Double.parseDouble(binding.tilSalario.getEditText().getText().toString());

            Intent replyIntent = new Intent();

            Empleado empleado = new Empleado(binding.tilDni.getEditText().getText().toString(),binding.tilNombreCompleto.getEditText().getText().toString(),idDepartamento,idCargo,(binding.btnSexo.isChecked())?"Femenino":"Masculino",salario,"Activo");

            replyIntent.putExtra("empleado",empleado);
            replyIntent.putExtra("action",action);
            setResult(RESULT_OK, replyIntent);
            finish();
        });
    }
}