package com.example.agenda_tareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.HashMap;
import java.util.Map;

public class ListaMateriasDiaActivity extends AppCompatActivity {

    RequestQueue requestQueue;

    private ArrayList<String> MateriaHora;
    ArrayList<HorariosMH> datosMH = new ArrayList();
    HorariosMH datosobtenidos;
    HorariosMH datoEspecifico;

    ListView listViewHorasMateria;
    ArrayAdapter<String> adapterMateriasHora;
    String diaOptenido;
    int Posicion;

    String urlListaMaterias= "http://192.168.0.109/Interfaz4/horario_fetch_list.php";
    String urlEliminarH= "http://192.168.0.109/Interfaz4/horario_delete.php";

    private int usuarioSeleccionado = -1;
    private Object mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_materias_dia);

        //Volley
        requestQueue = Volley.newRequestQueue(this);
        //Datos obtenidos desde la actividad anterior
        diaOptenido = getIntent().getStringExtra("dia");

        listViewHorasMateria= findViewById(R.id.lvHorarioMaterias);
        BuscarHorasDia(diaOptenido);
        onClick();
    }

    private void BuscarHorasDia(String dia){
        String url = urlListaMaterias + "?dia="+dia;
        JsonArrayRequest jsor = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = new JSONObject();

                        MateriaHora= new ArrayList<String>();

                        for (int i=0; i<response.length();i++){
                            try {
                                jsonObject = response.getJSONObject(i);

                                String xId=jsonObject.getString("id");
                                String xIdMateria=jsonObject.getString("idMateria");
                                String xNombre=jsonObject.getString("nombre");
                                String xHora=jsonObject.getString("hora");
                                datosobtenidos = new HorariosMH(xId,xIdMateria,xNombre,xHora);
                                datosMH.add(datosobtenidos);
                                MateriaHora.add(jsonObject.getString("nombre") +", Hora: " +jsonObject.getString("hora"));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        pintarLista(MateriaHora);
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
    private void pintarLista(ArrayList<String> listaMostrar){
        adapterMateriasHora = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listaMostrar);
        listViewHorasMateria.setAdapter(adapterMateriasHora);

        listViewHorasMateria.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idBuscar = listaMostrar.get(position);
            }
        });
    }

    public void onClick(){
        listViewHorasMateria.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewHorasMateria.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                usuarioSeleccionado = position;
                mActionMode = ListaMateriasDiaActivity.this.startActionMode(amc);
                view.setSelected(true);
                return true;
            }
        });
    }
    //
    private ActionMode.Callback amc = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.opciones, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if(item.getItemId()==R.id.EliminarItem){
                eliminarHora();
                mode.finish();
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    };
    public void eliminarHora(){
        //Toast.makeText(getApplicationContext(),"Nombre :"+datosMH.get(usuarioSeleccionado).getMateria() +" id: "+datosMH.get(usuarioSeleccionado).getId(),Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlEliminarH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ListaMateriasDiaActivity.this, "Hora eliminada", Toast.LENGTH_SHORT).show();
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
                parametros.put("id",datosMH.get(usuarioSeleccionado).getId());
                return  parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        Intent intent = new Intent(ListaMateriasDiaActivity.this,ListaMateriasDiaActivity.class);
        startActivity(intent);
        finish();

    }


}