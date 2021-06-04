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

public class PendientesActivity extends AppCompatActivity {
    FloatingActionButton _btnAñadir ,_btnMexit;
    ListView listView;
    String [] pendientes ={"Tareas","Examen","Reunion de tesis", "Tutoria","Club"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendientes);

        _btnAñadir=findViewById(R.id.btnMAdd);
        _btnMexit = findViewById(R.id.btnMexit);
        _btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PendientesActivity.this,addPendientesActivity.class);
                startActivity(intent);
            }
        });
        _btnMexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       /*
        //instancia de objeto
        listView = (ListView)findViewById(R.id.lvPenientes);
        //adaptador
        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,pendientes);
        //Pinta lista
        listView.setAdapter(adapter);
        //eventos de la lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(PendientesActivity.this, pendientes[position], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PendientesActivity.this,showPendienteActivity.class);
                startActivity(intent);
            }
        });

        */
    }
    private void obtenerPendientes(){
        String URL = "http://localhost/Interfaz4/pending_fetch_list.php";

    }
}