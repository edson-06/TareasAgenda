package com.example.agenda_tareas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class showPendienteActivity extends AppCompatActivity {
    EditText _txtPUNombre, _txtPUidMateria, _txtPUNDescripcion,_txtPUNFecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pendiente);
        //
        String nombre = getIntent().getStringExtra("nombre");
        String idMateria = getIntent().getStringExtra("idMateria");
        String descripcion = getIntent().getStringExtra("desscripción");
        String fecha = getIntent().getStringExtra("fecha");
        String estado = getIntent().getStringExtra("valor");

        _txtPUNombre = findViewById(R.id.txtPUNombre);
        _txtPUidMateria = findViewById(R.id.txtPUidMateria);
        _txtPUNDescripcion = findViewById(R.id.txtPUNDescripcion);
        _txtPUNFecha = findViewById(R.id.txtPUNFecha);

        _txtPUNombre.setText(nombre);
        _txtPUidMateria.setText(idMateria);
        _txtPUNDescripcion.setText(descripcion);
        _txtPUNFecha.setText(fecha);




    }
}