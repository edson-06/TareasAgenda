package com.example.agenda_tareas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class showPendienteActivity extends AppCompatActivity {
    EditText _txtPUNombre, _txtPUidMateria, _txtPUNDescripcion;
    TextView _txtPUFecha;
    String nombre, idMateria,descripcion,fecha,estado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pendiente);
        //
        nombre = getIntent().getStringExtra("nombre");
        idMateria = getIntent().getStringExtra("idMateria");
        descripcion = getIntent().getStringExtra("desscripción");
        fecha = getIntent().getStringExtra("fecha");
        estado = getIntent().getStringExtra("valor");

        _txtPUNombre = findViewById(R.id.txtPUNombre);
        _txtPUidMateria = findViewById(R.id.txtPUidMateria);
        _txtPUNDescripcion = findViewById(R.id.txtPUNDescripcion);
        _txtPUFecha = findViewById(R.id.txtUpFecha);

        _txtPUNombre.setText(nombre);
        _txtPUidMateria.setText(idMateria);
        _txtPUNDescripcion.setText(descripcion);
        _txtPUFecha.setText(fecha);
        

    }

    public void EditarCalendario(View view) {
        Calendar calendar = Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(showPendienteActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fecha = year + "-"+month+"-"+dayOfMonth;
                _txtPUFecha.setText(fecha);
            }
        },dia,mes,anio);
        dpd.show();
    }
}