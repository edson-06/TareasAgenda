package com.example.agenda_tareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PendientesActivity extends AppCompatActivity {
    FloatingActionButton _btnAñadir ,_btnMexit;
    ListView listView;
    String [] pendientes ;
    String [] idPendientes;

    //Coemntario actualizado
    //Doble Comentario

    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendientes);

        requestQueue = Volley.newRequestQueue(this);

        _btnAñadir=findViewById(R.id.btnMAdd);
        _btnMexit = findViewById(R.id.btnMexit);
        _btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PendientesActivity.this,addPendientesActivity.class);
                startActivity(intent);
            }
        });
        _btnMexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       /*
        //instancia de objeto
        listView = (ListView)findViewById(R.id.lvPenientes);
        //adaptador
        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,pendientes);
        //Pinta lista
        listView.setAdapter(adapter);
        //eventos de la lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(PendientesActivity.this, pendientes[position], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PendientesActivity.this,showPendienteActivity.class);
                startActivity(intent);
            }
        });
        */
        obtenerListaPendientes();
    }
    private void obtenerListaPendientes(){
        String url = "http://192.168.1.76/Interfaz4/pending_fetch_list.php";
        Toast.makeText(getApplicationContext(),"010101: ",Toast.LENGTH_SHORT).show();
        JsonObjectRequest jsor = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getApplicationContext(),"Pendientes: ",Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
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
    }
}