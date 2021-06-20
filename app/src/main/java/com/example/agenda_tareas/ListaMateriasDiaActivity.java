package com.example.agenda_tareas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaMateriasDiaActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    private ArrayList<String> idHoraArray;
    private ArrayList<String> horaArray;
    private ArrayList<String> materiasArray;
    private ArrayList<String> idMateriasArray;
    private ArrayList<String> MateriaHora;
    ArrayAdapter<String> adapterMateriasHora;
    String diaOptenido;

    String urlMaterias = "http://192.168.0.109/Interfaz4/subject_fetch_list.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_materias_dia);

        //Volley
        requestQueue = Volley.newRequestQueue(this);
        //Datos obtenidos desde la actividad anterior
        diaOptenido = getIntent().getStringExtra("dia");
        Toast.makeText(getApplicationContext(),"dia :"+diaOptenido,Toast.LENGTH_SHORT).show();
    }

    private void obtenerListaMaterias() {
        JsonArrayRequest jsor = new JsonArrayRequest(
                Request.Method.GET,
                urlMaterias,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = new JSONObject();
                        materiasArray = new ArrayList<String>();
                        idMateriasArray = new ArrayList<String>();
                        for (int i=0; i<response.length();i++){
                            try {
                                jsonObject = response.getJSONObject(i);
                                materiasArray.add(jsonObject.getString("nombre"));
                                idMateriasArray.add(jsonObject.getString("id"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        BuscarHorasDia(materiasArray,idMateriasArray);
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
    private void BuscarHorasDia(ArrayList<String> namesMateria,ArrayList<String> idMaterias){

    }
    private void pintarLista(ArrayList<String> listaMostrar){
        adapterMateriasHora = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listaMostrar);

    }
}