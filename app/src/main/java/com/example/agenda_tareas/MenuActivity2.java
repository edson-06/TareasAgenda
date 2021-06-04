package com.example.agenda_tareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity2 extends AppCompatActivity {
    Button _btnTareas, _btnHorarios,_btnPendientes,_btnSalir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);

        _btnPendientes = findViewById(R.id.btnPendientes);
        _btnTareas = findViewById(R.id.btnTareas);
        _btnHorarios = findViewById(R.id.btnHorarios);
        _btnSalir = findViewById(R.id.btnSalir);

        _btnPendientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PendientesActivity.class));
            }
        });
        _btnTareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),TareasActivity.class));
            }
        });
        _btnHorarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HorariosActivity.class));
            }
        });
        _btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}