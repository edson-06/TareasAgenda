package com.example.agenda_tareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HorariosActivity extends AppCompatActivity {

    private FloatingActionButton _btnHAñadir ,_btnHexit;
    private ListView listView;
    private ArrayList<String> Horario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios);

        _btnHAñadir=findViewById(R.id.btnHAdd);
        _btnHexit = findViewById(R.id.btnHexit);
        listView = (ListView)findViewById(R.id.lvHorario);

        Horario = new ArrayList<String>();
        Horario.add("Lunes");
        Horario.add("Martes");
        Horario.add("Miercoles");
        Horario.add("Jueves");
        Horario.add("Viernes");

        pintarLista();

        _btnHAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HorariosActivity.this,addHorarioActivity.class);
                startActivity(intent);
                finish();
            }
        });
        _btnHexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void pintarLista(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Horario);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Toast.makeText(HorariosActivity.this, "Has pulsado: "+ Horario.get(position), Toast.LENGTH_LONG).show();
                MostrarDia(Horario.get(position));
            }
        });

    }
    public void MostrarDia(String dia){
        Intent intent = new Intent(HorariosActivity.this,ListaMateriasDiaActivity.class);
        intent.putExtra("dia",dia);
        startActivity(intent);
        finish();
    }


}