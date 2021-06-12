package com.example.agenda_tareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;


public class MenuActivity2 extends AppCompatActivity {
    Button _btnTareas, _btnHorarios,_btnPendientes,_btnSalir;
    RequestQueue requestQueue;
    String[] pendientes = new String[20];
    String[] idPendientes= new String[20];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
        _btnPendientes = findViewById(R.id.btnPendientes);
        _btnTareas = findViewById(R.id.btnTareas);
        _btnHorarios = findViewById(R.id.btnHorarios);
        _btnSalir = findViewById(R.id.btnSalir);

        requestQueue = Volley.newRequestQueue(this);

        _btnPendientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pendientes = obtenerListaPendientes();
                Intent i = new Intent(getApplicationContext(),PendientesActivity.class);
                startActivity(i);
            }
        });
        _btnTareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),TareasActivity.class));
            }
        });
        _btnHorarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HorariosActivity.class));
            }
        });
        _btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private String[] obtenerListaPendientes(){
        String url = "http://192.168.0.109/Interfaz4/pending_fetch_list.php";
        Toast.makeText(getApplicationContext(),"010101: ",Toast.LENGTH_SHORT).show();
        JsonArrayRequest jsor = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject ;
                        Toast.makeText(getApplicationContext(),"tamaño: "+response.length(),Toast.LENGTH_SHORT).show();
                        for (int i=0; i<response.length();i++){
                            try {
                                jsonObject = response.getJSONObject(i);
                                pendientes[i]=""+jsonObject.getString("nombre");
                                idPendientes[i]=""+jsonObject.getString("id");
                                Toast.makeText(getApplicationContext(),"tamaño: "+idPendientes[i],Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error :"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsor);
        return pendientes;
    }

}