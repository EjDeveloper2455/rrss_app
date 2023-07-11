package com.example.rrssapp.ui.planilla;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.example.rrssapp.Entities.Planilla;
import com.example.rrssapp.OnItemClickListener;
import com.example.rrssapp.databinding.FragmentPlanillaBinding;
import com.example.rrssapp.ui.cargo.NewCargoActivity;
import com.example.rrssapp.ui.depto.DepartamentoAdapter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class PlanillaFragment extends Fragment implements OnItemClickListener<Planilla> {

    private FragmentPlanillaBinding binding;
    private PlanillaAdapter planillaAdapter;

    private ActivityResultLauncher<Intent> launcher;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlanillaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        PlanillaViewModel planillaViewModel =
                new ViewModelProvider(this).get(PlanillaViewModel.class);
        planillaAdapter = new PlanillaAdapter(new ArrayList<>(),this);

        setupRecyrcleView();

        planillaViewModel.getDataset().observe(getViewLifecycleOwner(), departamentos -> {
            planillaAdapter.setItems(departamentos);
        });

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Planilla planilla = (Planilla) data.getSerializableExtra("planilla");
                        planillaViewModel.insert(planilla);

                        planillaViewModel.getDataset().observe(getViewLifecycleOwner(), cargos -> {
                            planillaAdapter.setItems(cargos);
                        });
                    } else {
                        Toast.makeText(this.getContext(),"Operación cancelada",Toast.LENGTH_LONG).show();
                    }
                }
        );

        binding.fabPlanilla.setOnClickListener(v -> {
            Intent intent = new Intent (requireContext(), NewPlanillaActivity.class);
            intent.putExtra("action","new");
            launcher.launch(intent);
        });
        return root;
    }
    private void generarReporte(Planilla planilla) {
        try {
            String directorio = "/ReportesRRHHAPP";
            String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath()+directorio;
            File dir = new File(filePath);
            if(!dir.exists()){
                dir.mkdir();
                Toast.makeText(this.getContext(),"Carpeta creada",Toast.LENGTH_LONG).show();
            }

            String pathPdf = "/"+planilla.getConcepto()+".pdf";
            File file = new File(dir,pathPdf);

            FileOutputStream outputStream = new FileOutputStream(file);
            Document documento = new Document();
            PdfWriter.getInstance(documento,outputStream);
            documento.open();



            Paragraph titulo = new Paragraph("Reporte de pago a planilla NO. "+planilla.getId(), FontFactory.getFont("arial",32, Font.BOLD, BaseColor.BLACK));
            titulo.setSpacingAfter(40);
            documento.add(titulo);

            Paragraph fechaP = new Paragraph("Fecha: "+planilla.getFecha(), FontFactory.getFont("arial",25, Font.BOLD, BaseColor.BLACK));
            documento.add(fechaP);
            titulo.setSpacingAfter(20);

            Paragraph conceptoP = new Paragraph("Concepto: "+planilla.getConcepto(), FontFactory.getFont("arial",25, Font.BOLD, BaseColor.BLACK));
            conceptoP.setSpacingAfter(20);
            documento.add(conceptoP);

            Paragraph montoP = new Paragraph("Monto: "+planilla.getMonto(), FontFactory.getFont("arial",25, Font.BOLD, BaseColor.BLACK));
            documento.add(montoP);

            documento.close();

            Toast.makeText(this.getContext(),"Documento creado en documents/ReportesRRHHAPP/"+planilla.getId()+".pdf",Toast.LENGTH_LONG).show();

        }catch (Exception e){
            Toast.makeText(this.getContext(),"Ha ocurrido un error",Toast.LENGTH_LONG).show();
        }

    }
    private void setupRecyrcleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        binding.rvPlanilla.setLayoutManager(linearLayoutManager);
        binding.rvPlanilla.setAdapter(planillaAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(Planilla data) {
        generarReporte(data);
    }
}