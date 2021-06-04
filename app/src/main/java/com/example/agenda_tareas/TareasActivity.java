package com.example.agenda_tareas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TareasActivity extends AppCompatActivity {
    ListView listView;
    String [] materias ={"Estadistica","Sistemas Distribuidos","Bases de datos", "Paradigmas","Programación estructurada"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);

        listView = (ListView)findViewById(R.id.lvMaterias);
        //adaptador
        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,materias);
        //Pinta lista
        listView.setAdapter(adapter);
    }
}