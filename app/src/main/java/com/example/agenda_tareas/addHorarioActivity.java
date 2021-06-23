package com.example.agenda_tareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class addHorarioActivity extends AppCompatActivity {

    Button _btnHAgregar, _btnHCancelar;
    Spinner _spinnerMaterias, _spinnerDias,_spinnerHoras;
    String urlMaterias = "http://192.168.0.109/Interfaz4/subject_fetch_list.php";
    String URLH="http://192.168.0.109/Interfaz4/horario_Save.php";
    String urValidaaH="http://192.168.0.109/Interfaz4/horario_fetch.php";
    RequestQueue requestQueue;
    private ArrayList<String> HoraArray;
    private ArrayList<String> diaArray;
    private ArrayList<String> materiasArray;
    private ArrayList<String> idMateriasArray;
    ArrayAdapter<String> adapterMaterias;
    ArrayAdapter<String> adapterDias;
    ArrayAdapter<String> adapterHoras;
    int idMateriaSeleccionada;
    int valorHorario=0;
    String dia, materia, hora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_horario);

        //Volley
        requestQueue = Volley.newRequestQueue(this);

        _spinnerMaterias = findViewById(R.id.spinnerHMateria);
        _spinnerDias = findViewById(R.id.spinnerHDia);
        _spinnerHoras = findViewById(R.id.spinnerHHora);
        _btnHAgregar = findViewById(R.id.btnHAgregrar);
        _btnHCancelar = findViewById(R.id.btnHCancelar);

        llenadoArreglos();


    }
    public void llenadoArreglos(){
        HoraArray = new ArrayList<String>();
        HoraArray.add("07:00:00");
        HoraArray.add("08:00:00");
        HoraArray.add("09:00:00");
        HoraArray.add("10:00:00");
        HoraArray.add("11:00:00");
        HoraArray.add("12:00:00");
        HoraArray.add("13:00:00");
        HoraArray.add("14:00:00");
        HoraArray.add("15:00:00");
        HoraArray.add("16:00:00");
        HoraArray.add("17:00:00");
        HoraArray.add("18:00:00");
        HoraArray.add("19:00:00");
        HoraArray.add("20:00:00");

        diaArray = new ArrayList<String>();
        diaArray.add("Lunes");
        diaArray.add("Martes");
        diaArray.add("Miercoles");
        diaArray.add("Jueves");
        diaArray.add("Viernes");
        diaArray.add("Todos");

        obtenerListaDias();
        obtenerListaHoras();
        obtenerListaMaterias();

        _btnHAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidarHorario(urValidaaH);

            }
        });
        _btnHCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarVentana();
            }
        });
    }
    public void cerrarVentana(){
        Intent intent = new Intent(addHorarioActivity.this,HorariosActivity.class);
        startActivity(intent);
        finish();
    }
    public  void ValidarHorario(String u){

        StringRequest stringRequest= new StringRequest(Request.Method.POST, u, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Toast.makeText(addHorarioActivity.this, "Dia y/o hora ocupados", Toast.LENGTH_SHORT).show();
                    Toast.makeText(addHorarioActivity.this, "2"+response, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(addHorarioActivity.this, "1"+response, Toast.LENGTH_SHORT).show();
                    CrearHorario(dia,materia,hora);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(addHorarioActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("dia",dia);
                parametros.put("hora",hora);
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void CrearHorario(String dia, String idMat, String hora){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(addHorarioActivity.this, "Hora añadida", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("dia",dia);
                parametros.put("idMateria",idMat);
                parametros.put("hora",hora);
                return  parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        cerrarVentana();

    }

    public void obtenerListaDias(){
        adapterDias = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,diaArray);
        _spinnerDias.setAdapter(adapterDias);


        _spinnerDias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),"Dia: "+diaArray.get(position),Toast.LENGTH_SHORT).show();
                dia =diaArray.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void obtenerListaHoras(){
        adapterHoras = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,HoraArray);
        _spinnerHoras.setAdapter(adapterHoras);


        _spinnerHoras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),"hora: "+HoraArray.get(position),Toast.LENGTH_SHORT).show();
                hora=HoraArray.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                        pintar(materiasArray,idMateriasArray);
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
    private void pintar(ArrayList<String> names1,ArrayList<String> idPendientes){
        adapterMaterias = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,materiasArray);
        _spinnerMaterias.setAdapter(adapterMaterias);


        _spinnerMaterias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String item = parent.getSelectedItem().toString();
                //Toast.makeText(getApplicationContext(),"Nombre: "+item+" id: "+idMaterias.get(position),Toast.LENGTH_SHORT).show();
                materia = idMateriasArray.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}