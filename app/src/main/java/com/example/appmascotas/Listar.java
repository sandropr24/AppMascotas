package com.example.appmascotas;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Listar extends AppCompatActivity {

    ListView lstMascotas;

    RequestQueue requestQueue;

    private final String URL = "http://192.168.56.1:3000/mascotas/";

    private void loadUI(){
        lstMascotas = findViewById(R.id.lstMascotas);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadUI();

        obtenerDatos();
    }

    //Importar objetos JSON desde ws
    private void obtenerDatos(){
        requestQueue = Volley.newRequestQueue(this);//comunicación
        //¿Que nos devuelve el WS con el metodo GET?

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        renderizarListView(jsonArray);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("ErrorWS" , volleyError.toString());
                        Toast.makeText(getApplicationContext(), "Nose obtubo los datos" , Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);

    }//obtnerDatos

    //Ponlar (llenar) ListView con los JSON
    private void renderizarListView(JSONArray jsonMascotas){
        try{
            ArrayAdapter adapter;
            ArrayList<String> listaMascotas = new ArrayList<>();

            for(int i = 0; i < jsonMascotas.length(); i++){
                JSONObject jsonObject = jsonMascotas.getJSONObject(i);
                listaMascotas.add(jsonObject.getString("tipo") + " " + jsonObject.getString("nombre"));
            }

            adapter = new ArrayAdapter(this , android.R.layout.simple_list_item_1, listaMascotas);
            lstMascotas.setAdapter(adapter);

        }
        catch(Exception e){
            Log.e("ErrorJSON" , e.toString());
        }

    }
}