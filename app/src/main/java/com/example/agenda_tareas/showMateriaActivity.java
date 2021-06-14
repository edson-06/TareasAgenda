package com.example.agenda_tareas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class showMateriaActivity extends AppCompatActivity {
    EditText _txtNombreMat, _txtAreaMat, _txtNumSalonMat,_txtProfesorMat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_materia);

        String nombre = getIntent().getStringExtra("nombre");
        String id = getIntent().getStringExtra("id");
        String area = getIntent().getStringExtra("area");
        String num_salon = getIntent().getStringExtra("num_salon");
        String profesor = getIntent().getStringExtra("professor");

        _txtNombreMat = findViewById(R.id.txtNombreMat);
        _txtAreaMat = findViewById(R.id.txtAreaMat);
        _txtNumSalonMat = findViewById(R.id.txtNumSalonMat);
        _txtProfesorMat = findViewById(R.id.txtProfesorMat);

        _txtNombreMat.setText(nombre);
        _txtAreaMat.setText(area);
        _txtNumSalonMat.setText(num_salon);
        _txtProfesorMat.setText(profesor);

    }
}