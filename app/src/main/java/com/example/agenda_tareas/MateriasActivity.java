package com.example.agenda_tareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MateriasActivity extends AppCompatActivity {
    FloatingActionButton _btnMAñadir ,_btnMexit,_btnMBuscar;
    ListView listView;
    RequestQueue requestQueue;
    private ArrayList<String> materias;
    private ArrayList<String> idMaterias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);

        requestQueue = Volley.newRequestQueue(this);

        _btnMAñadir=findViewById(R.id.btnMatAdd);
        _btnMexit = findViewById(R.id.btnMatexit);
        _btnMBuscar=findViewById(R.id.btnMatSearch);
        listView = (ListView)findViewById(R.id.lvMaterias);

        /////
        obtenerListaMaterias();
        ////
        _btnMAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MateriasActivity.this,addMateriasActivity.class);
                startActivity(intent);
            }
        });
        _btnMBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        _btnMexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void pintar(ArrayList<String> names1,ArrayList<String> idPendientes){
        //adaptador
        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,names1);
        //Pinta lista
        listView.setAdapter(adapter);
        //eventos de la lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idBuscar = idMaterias.get(position);
                obtenerPendienteParticular(idBuscar);
            }
        });
    }
    private void lanzarVentana(String id, String nombre, String area, String num_salon, String professor){
        Intent intent = new Intent(MateriasActivity.this,showMateriaActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("nombre",nombre);
        intent.putExtra("area",area);
        intent.putExtra("num_salon",num_salon);
        intent.putExtra("professor",professor);
        startActivity(intent);
    }
    private void obtenerPendienteParticular(String id){
        String url = "http://192.168.0.109/Interfaz4/subject_fetch.php?id="+id;
        JsonObjectRequest jsor = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String id="", nombre="",area="",num_salon="",profesor="";
                        try {
                            id = response.getString("id");
                            nombre = response.getString("nombre");
                            area = response.getString("area");
                            num_salon = response.getString("num_salon");
                            profesor = response.getString("profesor");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(getApplicationContext(),"Valor nombre :"+nombre,Toast.LENGTH_SHORT).show();
                        lanzarVentana(id, nombre, area, num_salon, profesor);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error :"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsor);
    }
    private void obtenerListaMaterias() {
        String url = "http://192.168.0.109/Interfaz4/subject_fetch_list.php";
        JsonArrayRequest jsor = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = new JSONObject();
                        materias = new ArrayList<String>();
                        idMaterias = new ArrayList<String>();
                        for (int i=0; i<response.length();i++){
                            try {
                                jsonObject = response.getJSONObject(i);
                                materias.add(jsonObject.getString("nombre"));
                                idMaterias.add(jsonObject.getString("id"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        pintar(materias,idMaterias);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error :"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsor);
    }
}