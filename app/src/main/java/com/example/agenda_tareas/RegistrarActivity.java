package com.example.agenda_tareas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrarActivity extends AppCompatActivity {
    RequestQueue  requestQueue;
    Button _btnAñadir,_btnExit;
    EditText _txtNombre,_txtUser,_txtPass;
    String nom="", user="", pass="";
    int valor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        _btnAñadir = findViewById(R.id.btnAñadir);
        _btnExit = findViewById(R.id.btnExit);
        _txtNombre = findViewById(R.id.txtNombre);
        _txtUser = findViewById(R.id.txtUser);
        _txtPass = findViewById(R.id.txtPass);

        _btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL ="http://192.168.0.109/Interfaz4/save.php";
                if(validar() == 0){
                    RegistrarNuevo(URL);
                    finish();
                    limpiarRegistro();
                }
            }
        });

        _btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                limpiarRegistro();
            }
        });
    }
    public void limpiarRegistro(){
        _txtNombre.setText("");
        _txtUser.setText("");
        _txtPass.setText("");
    }

    int validar(){
        nom = _txtNombre.getText().toString().trim();
        user = _txtUser.getText().toString().trim();
        pass = _txtPass.getText().toString().trim();
        if(nom.length()<1 || user.length()<1 || pass.length()<1 ){
            Toast.makeText(getApplicationContext(),"Rellene todos los campos",Toast.LENGTH_LONG).show();
            return 1;
        }else{
            return 0;
        }
    }
    public  void RegistrarNuevo(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Operación exitosa", Toast.LENGTH_SHORT).show();
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("nombre",nom);
                parametros.put("usuario",user);
                parametros.put("password",pass);
                //return super.getParams();
                return  parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}