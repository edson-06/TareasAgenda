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


public class PendientesActivity extends AppCompatActivity {
    FloatingActionButton _btnAñadir ,_btnMexit,_btnBuscar;
    ListView listView;
    RequestQueue requestQueue;
    private ArrayList<String> pendientes;
    private ArrayList<String> idPendientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendientes);

        requestQueue = Volley.newRequestQueue(this);

        _btnAñadir=findViewById(R.id.btnMAdd);
        _btnMexit = findViewById(R.id.btnMexit);
        _btnBuscar=findViewById(R.id.btnMSearch);
        listView = (ListView)findViewById(R.id.lvPenientes);
        //instancia de objeto
        listView = findViewById(R.id.lvPenientes);

        /////
        obtenerListaPendientes();
        ////

        _btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PendientesActivity.this,addPendientesActivity.class);
                startActivity(intent);
                finish();
            }
        });
        _btnBuscar.setOnClickListener(new View.OnClickListener() {
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
                String idBuscar = idPendientes.get(position);
                obtenerPendienteParticular(idBuscar);
            }
        });
    }
    private void lanzarVentana(String id, String nombre, String idMateria, String fecha, String desscripción, String valor){
        Intent intent = new Intent(PendientesActivity.this,showPendienteActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("nombre",nombre);
        intent.putExtra("idMateria",idMateria);
        intent.putExtra("fecha",fecha);
        intent.putExtra("desscripción",desscripción);
        intent.putExtra("valor",valor);
        Toast.makeText(getApplicationContext(),"Datos obtenidos :",Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
    private void obtenerPendienteParticular(String id){
        String url = "http://192.168.0.109/Interfaz4/pending_fetch.php?id="+id;
        JsonObjectRequest jsor = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String id="", nombre="",idMateria="",fecha="",descripcion="",valor ="";
                        try {
                            id = response.getString("id");
                            nombre = response.getString("nombre");
                            idMateria = response.getString("idMateria");
                            fecha = response.getString("fecha");
                            descripcion = response.getString("descripcion");
                            valor = response.getString("estado");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(getApplicationContext(),"Valor nombre :"+nombre,Toast.LENGTH_SHORT).show();
                        lanzarVentana(id, nombre, idMateria, fecha, descripcion,valor);
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
    private void obtenerListaPendientes() {
        String url = "http://192.168.0.109/Interfaz4/pending_fetch_list.php";
        JsonArrayRequest jsor = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = new JSONObject();
                        pendientes = new ArrayList<String>();
                        idPendientes = new ArrayList<String>();
                        for (int i=0; i<response.length();i++){
                            try {
                                jsonObject = response.getJSONObject(i);
                                pendientes.add(jsonObject.getString("nombre"));
                                idPendientes.add(jsonObject.getString("id"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        pintar(pendientes,idPendientes);
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