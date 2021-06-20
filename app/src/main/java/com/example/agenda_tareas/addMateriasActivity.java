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

public class addMateriasActivity extends AppCompatActivity {
    EditText _txtNombre, _txtArea,_txtNumSalon, _txtProfesor;
    Button _btnPAgregar, _btnPCancelar;
    String URLP = "http://192.168.0.109/Interfaz4/subject_save.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_materias);

        _txtNombre = findViewById(R.id.txtMatNombre);
        _txtArea = findViewById(R.id.txtMatArea);
        _txtNumSalon = findViewById(R.id.txtMatNumSalon);
        _txtProfesor = findViewById(R.id.txtMatProfesor);

        _btnPAgregar = findViewById(R.id.btnMatAgregrar);
        _btnPCancelar = findViewById(R.id.btnMatCancelar);

        _btnPAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre= _txtNombre.getText().toString().trim();
                String area= _txtArea.getText().toString().trim();
                String numSalon= _txtNumSalon.getText().toString().trim();
                String Profesor= _txtProfesor.getText().toString().trim();

                CrearMateria(nombre, area, numSalon, Profesor);
            }
        });
        _btnPCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void CrearMateria(String nombre, String area, String aula, String Profesor){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(addMateriasActivity.this, "Materia añadida", Toast.LENGTH_SHORT).show();
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
                parametros.put("area",area);
                parametros.put("aula",aula);
                parametros.put("profesor",Profesor);
                return  parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        cerrarVentana();
    }

    public void cerrarVentana(){
        Intent intent = new Intent(addMateriasActivity.this,MateriasActivity.class);
        startActivity(intent);
        finish();
    }

}