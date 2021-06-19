package com.example.agenda_tareas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class addPendientesActivity extends AppCompatActivity {
    EditText _txtPNombre, _txtPdescripcion;
    TextView _txtPfecha;
    Button _btnPAgregar, _btnPCancelar;
    Spinner _spinner;
    String URLP = "http://192.168.0.109/Interfaz4/savePendientes.php";
    String urlMaterias = "http://192.168.0.109/Interfaz4/subject_fetch_list.php";
    RequestQueue requestQueue;
    private ArrayList<String> materias;
    private ArrayList<String> idMaterias;
    String nombre, id, fecha, descripcion;
    ArrayAdapter<String> adapterMaterias;
    int idMateriaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pendientes);

        //Volley
        requestQueue = Volley.newRequestQueue(this);

        _txtPNombre = findViewById(R.id.txtPNombre);
        _txtPfecha = findViewById(R.id.txtUpFecha);
        _txtPdescripcion = findViewById(R.id.txtPdescripcion);
        _btnPAgregar = findViewById(R.id.btnPAgregrar);
        _btnPCancelar = findViewById(R.id.btnPCancelar);
        _spinner = findViewById(R.id.spinnerMaterias);
        obtenerListaMaterias();

        _btnPAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nombre= _txtPNombre.getText().toString().trim();
                id= idMaterias.get(idMateriaSeleccionada);
                fecha= _txtPfecha.getText().toString().trim();
                descripcion= _txtPdescripcion.getText().toString().trim();

                CrearPendiente(nombre, id, fecha, descripcion);
            }
        });
        _btnPCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarVentana();
            }
        });


    }
    private void CrearPendiente(String nombre, String idMat, String fecha, String descripcion){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(addPendientesActivity.this, "Pendiente añadido", Toast.LENGTH_SHORT).show();
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
                parametros.put("nombre",nombre);
                parametros.put("idMateria",idMat);
                parametros.put("fecha",fecha);
                parametros.put("descripcion",descripcion);
                return  parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        cerrarVentana();

    }
    public void cerrarVentana(){
        Intent intent = new Intent(addPendientesActivity.this,PendientesActivity.class);
        startActivity(intent);
        finish();
    }

    public void abrirCalendario(View view) {
        Calendar calendar = Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(addPendientesActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fecha = year + "-"+month+"-"+dayOfMonth;
                _txtPfecha.setText(fecha);
            }
        },dia,mes,anio);
        dpd.show();
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
    private void pintar(ArrayList<String> names1,ArrayList<String> idPendientes){
        adapterMaterias = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,materias);
        _spinner.setAdapter(adapterMaterias);


        _spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String item = parent.getSelectedItem().toString();
                //Toast.makeText(getApplicationContext(),"Nombre: "+item+" id: "+idMaterias.get(position),Toast.LENGTH_SHORT).show();
                idMateriaSeleccionada = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}