package com.example.appmascotas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListarCustom extends AppCompatActivity implements MascotaAdapter.OnAccionListener {

    RecyclerView recyclerMascota;
    MascotaAdapter adapter;
    ArrayList<Mascota> listaMascotas;
    RequestQueue requestQueue;

    private final String URL = "http://192.168.56.1:3000/mascotas/";

    private void loadUI(){
        recyclerMascota = findViewById(R.id.recyclerMascotas);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar_custom);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            return insets;
        });

        loadUI();

        //preparar la lista y el adaptador antes de utilizar el WS
        listaMascotas = new ArrayList<>();
        adapter = new MascotaAdapter(this,listaMascotas , this);//Implementar la definicion de clase....
        recyclerMascota.setLayoutManager(new LinearLayoutManager(this));
        recyclerMascota.setAdapter(adapter);

        //WS...
        obtenerDatos();
    }

    private  void obtenerDatos(){
        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                jsonArray -> renderizarLista(jsonArray),
                error ->{
                    Log.e("ErrorWS" , error.toString());
                    Toast.makeText(this,"Nose obtuvieron los datos" , Toast.LENGTH_SHORT).show();
                }
        );

        requestQueue.add(jsonArrayRequest);

    }

    private  void renderizarLista(JSONArray jsonMascotas){
        //Con los datos obtenidos , cargaremos la lista<Mascota> ya que esta
        //esta vinculada al MascotaAdapter >RecyclerView

        try{
            listaMascotas.clear();

            for(int i = 0; i < jsonMascotas.length(); i++){
                //Tomaremos un JSON  al avez utilizando su indice
                JSONObject json = jsonMascotas.getJSONObject(i);
                listaMascotas.add(new Mascota(
                        json.getInt("id"),
                        json.getString("tipo"),
                        json.getString("nombre"),
                        json.getString("color"),
                        json.getDouble("pesokg")
                ));
            }//fin for

            adapter.notifyDataSetChanged();

        }catch (Exception e){
            Log.e("ErrorJSON" , e.toString());
        }

    }

    private void eliminarMascota(int id ,int position){
        requestQueue = Volley.newRequestQueue(this);

        String urlEliminar = this.URL + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                urlEliminar,
                null,
                jsonObject -> {
                    try {
                        boolean eliminado = jsonObject.getBoolean("success");
                        String mensaje = jsonObject.getString("message");

                        if(eliminado){
                            adapter.eliminarItem(position);
                            Toast.makeText(this , mensaje , Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        Log.e("ErrorJSON" , e.toString());
                    }
                },
                error -> {
                    Log.e("ErrorWS" , error.toString());
                    Toast.makeText(this,"No se pudo eliminar el registro" , Toast.LENGTH_SHORT).show();
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onEditar(int position, Mascota mascota) {
        Intent intent = new Intent(this, Actualizar.class);

        intent.putExtra("id", mascota.getId());
        intent.putExtra("tipo", mascota.getTipo());
        intent.putExtra("nombre", mascota.getNombre());
        intent.putExtra("peso", mascota.getPesokg());
        intent.putExtra("color", mascota.getColor());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        obtenerDatos();
    }

    @Override
    public void onEliminar(int position, Mascota mascota) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mascotas");
        builder.setMessage("¿Confirma que desea eliminar a " + mascota.getNombre()+"?");

        builder.setPositiveButton("Si" ,(a,b) ->{
            eliminarMascota(mascota.getId(),position);
        });
        builder.setNegativeButton("No" , null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}