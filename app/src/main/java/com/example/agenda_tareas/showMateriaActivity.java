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

import java.util.HashMap;
import java.util.Map;

public class showMateriaActivity extends AppCompatActivity {
    EditText _txtNombreMat, _txtAreaMat, _txtNumSalonMat,_txtProfesorMat;
    Button _btnEditar, _btnEliminar;
    String URLActualizaMateria = "http://192.168.0.109/Interfaz4/subject_update.php";
    String URLEliminaMateria = "http://192.168.0.109/Interfaz4/subject_Delete.php";
    String nombre,id,area,num_salon,profesor;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_materia);
        //Volley
        requestQueue = Volley.newRequestQueue(this);

        nombre = getIntent().getStringExtra("nombre");
        id = getIntent().getStringExtra("id");
        area = getIntent().getStringExtra("area");
        num_salon = getIntent().getStringExtra("num_salon");
        profesor = getIntent().getStringExtra("professor");

        _txtNombreMat = findViewById(R.id.txtNombreMat);
        _txtAreaMat = findViewById(R.id.txtAreaMat);
        _txtNumSalonMat = findViewById(R.id.txtNumSalonMat);
        _txtProfesorMat = findViewById(R.id.txtProfesorMat);
        _btnEditar = findViewById(R.id.btnMatEditar);
        _btnEliminar= findViewById(R.id.btnMatEliminar);

        _txtNombreMat.setText(nombre);
        _txtAreaMat.setText(area);
        _txtNumSalonMat.setText(num_salon);
        _txtProfesorMat.setText(profesor);

        _btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre=_txtNombreMat.getText().toString().trim();
                area=_txtAreaMat.getText().toString().trim();
                num_salon=_txtNumSalonMat.getText().toString().trim();
                profesor=_txtProfesorMat.getText().toString().trim();

                ActualizarMateria(id,nombre,area,num_salon,profesor);
            }
        });

        _btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EliminarMateria(id);
            }
        });

    }
    public void cerrarVentana(){
        Intent intent = new Intent(showMateriaActivity.this,MateriasActivity.class);
        startActivity(intent);
        finish();
    }
    private void ActualizarMateria(String idMateria,String nombre,String area,String aula,String  profesor){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLActualizaMateria, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(showMateriaActivity.this, "Pendiente actualizado", Toast.LENGTH_SHORT).show();
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
                parametros.put("id",idMateria);
                parametros.put("nombre",nombre);
                parametros.put("area",area);
                parametros.put("aula",aula);
                parametros.put("profesor",profesor);
                return  parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        cerrarVentana();
    }

    private void EliminarMateria(String idMateria){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLEliminaMateria, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(showMateriaActivity.this, "Pendiente eliminado", Toast.LENGTH_SHORT).show();
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
                parametros.put("id",idMateria);
                return  parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        //Mostrar ventana de pendientes
        cerrarVentana();

    }
}