package com.example.rrssapp.ui.planilla;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.rrssapp.Entities.Departamento;
import com.example.rrssapp.Entities.Planilla;
import com.example.rrssapp.R;
import com.example.rrssapp.databinding.ActivityNewDepartamentoBinding;
import com.example.rrssapp.databinding.ActivityNewPlanillaBinding;
import com.example.rrssapp.ui.empleado.EmpleadoViewModel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewPlanillaActivity extends AppCompatActivity {

  private ActivityNewPlanillaBinding binding;
  private EmpleadoViewModel empleadoViewModel;
  double montoPagar;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityNewPlanillaBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    montoPagar = 0.00;

    empleadoViewModel =
            new ViewModelProvider(this).get(EmpleadoViewModel.class);

    empleadoViewModel.getMontoTotal().observe(this, montoTotal -> {
      binding.tilMontoPlanilla.getEditText().setText(montoTotal.toString());
      montoPagar = montoTotal;
    });

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    binding.tilFechaPlanilla.getEditText().setText(sdf.format(new Date()));
    binding.tilFechaPlanilla.getEditText().setFocusable(false);
    binding.tilFechaPlanilla.getEditText().setOnKeyListener(null);

    binding.tilMontoPlanilla.getEditText().setFocusable(false);
    binding.tilMontoPlanilla.getEditText().setOnKeyListener(null);

    binding.btnGuardarDepto.setOnClickListener(v -> {
      if(binding.tilConceptoPlanilla.getEditText().getText().toString().isEmpty()){
        binding.tilConceptoPlanilla.getEditText().setError(getString(R.string.campo_vacio));
        return;
      }
      Intent replyIntent = new Intent();
      Planilla planilla = new Planilla(binding.tilFechaPlanilla.getEditText().getText().toString(),binding.tilConceptoPlanilla.getEditText().getText().toString(),montoPagar);
      if(planilla.getMonto()<=0){
        binding.tilMontoPlanilla.getEditText().setError("Se requiere un monto total para realizar un pago de planilla");
        return;
      }
      replyIntent.putExtra("planilla", planilla);
      setResult(RESULT_OK, replyIntent);
      finish();

    });
  }


}
