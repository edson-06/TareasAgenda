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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button _btnIniciar,_btnRegistrar;
    EditText _txtLUser, _txtLPass;
    String  user, pass ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _btnIniciar = findViewById(R.id.btnIniciar);
        _btnRegistrar =findViewById(R.id.btnRegistrar);
        _txtLUser = findViewById(R.id.txtLUser);
        _txtLPass = findViewById(R.id.txtLPass);


        _btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = _txtLUser.getText().toString().trim();
                pass = _txtLPass.getText().toString().trim();
                String URL ="http://192.168.0.109/Interfaz4/Validar.php";
                validarLogin(URL);
            }
        });
        _btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegistrarActivity.class));

            }
        });
    }
    private void validarLogin(String u){
       StringRequest stringRequest= new StringRequest(Request.Method.POST, u, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Intent intent = new Intent(getApplicationContext(),MenuActivity2.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Usuario y/o contraseña incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("usuario",_txtLUser.getText().toString().trim());
                parametros.put("password",_txtLPass.getText().toString().trim());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}