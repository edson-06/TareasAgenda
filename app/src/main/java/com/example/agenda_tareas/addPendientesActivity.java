package com.example.agenda_tareas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class addPendientesActivity extends AppCompatActivity {
    EditText _txtPNombre, _txtPidMateria, _txtPdescripcion;
    TextView _txtPfecha;
    Button _btnPAgregar, _btnPCancelar;
    String URLP = "http://192.168.0.109/Interfaz4/savePendientes.php";
    String nombre, id, fecha, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pendientes);

        _txtPNombre = findViewById(R.id.txtPNombre);
        _txtPidMateria = findViewById(R.id.txtPidMateria);
        _txtPfecha = findViewById(R.id.txtUpFecha);
        _txtPdescripcion = findViewById(R.id.txtPdescripcion);

        _btnPAgregar = findViewById(R.id.btnPAgregrar);
        _btnPCancelar = findViewById(R.id.btnPCancelar);

        _btnPAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nombre= _txtPNombre.getText().toString().trim();
                id= _txtPidMateria.getText().toString().trim();
                fecha= _txtPfecha.getText().toString().trim();
                descripcion= _txtPdescripcion.getText().toString().trim();

                CrearPendiente(nombre, id, fecha, descripcion);
                Toast.makeText(addPendientesActivity.this,"Añadiendo",Toast.LENGTH_SHORT).show();
            }
        });
        _btnPCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
}