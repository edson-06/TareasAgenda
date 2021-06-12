package com.example.agenda_tareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

public class addPendientesActivity extends AppCompatActivity {
    EditText _txtPNombre, _txtPidMateria,_txtPfecha, _txtPdescripcion;
    Button _btnPAgregar, _btnPCancelar;
    String URLP = "http://192.168.0.109/Interfaz4/savePendientes.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pendientes);

        _txtPNombre = findViewById(R.id.txtPNombre);
        _txtPidMateria = findViewById(R.id.txtPidMateria);
        _txtPfecha = findViewById(R.id.txtPfecha);
        _txtPdescripcion = findViewById(R.id.txtPdescripcion);

        _btnPAgregar = findViewById(R.id.btnPAgregrar);
        _btnPCancelar = findViewById(R.id.btnPCancelar);

        _btnPAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre= _txtPNombre.getText().toString().trim();
                String id= _txtPidMateria.getText().toString().trim();
                String fecha= _txtPfecha.getText().toString().trim();
                String descripcion= _txtPdescripcion.getText().toString().trim();

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
}