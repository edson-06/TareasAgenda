package com.example.agenda_tareas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Calendar;

public class showPendienteActivity extends AppCompatActivity {
    EditText _txtPUNombre, _txtPUNDescripcion;
    TextView _txtPUFecha;
    String nombre, idMat,descripcion,fecha,estado;

    Spinner _spinnerUp;
    String urlMaterias = "http://192.168.0.109/Interfaz4/subject_fetch_list.php";
    RequestQueue requestQueue;
    private ArrayList<String> materias;
    private ArrayList<String> idMaterias;
    ArrayAdapter<String> adapterMaterias;
    int idMateriaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pendiente);

        //Volley
        requestQueue = Volley.newRequestQueue(this);
        //
        nombre = getIntent().getStringExtra("nombre");
        idMat = getIntent().getStringExtra("idMateria");
        descripcion = getIntent().getStringExtra("desscripción");
        fecha = getIntent().getStringExtra("fecha");
        estado = getIntent().getStringExtra("valor");

        _txtPUNombre = findViewById(R.id.txtPUNombre);
        _txtPUNDescripcion = findViewById(R.id.txtPUNDescripcion);
        _txtPUFecha = findViewById(R.id.txtUpFecha);
        _spinnerUp = findViewById(R.id.spinnerMaterias2);
        obtenerListaMaterias();


        _txtPUNombre.setText(nombre);
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
        _spinnerUp.setAdapter(adapterMaterias);


        _spinnerUp.setSelection(idMaterias.indexOf(idMat));

        _spinnerUp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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