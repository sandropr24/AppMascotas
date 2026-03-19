package com.example.appmascotas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Actualizar extends AppCompatActivity {

    String tipo, nombre, color;
    double peso;

    int idMascota;

    EditText edtTipoA, edtNombreA, edtColorA, edtPesoA;
    Button btnActualizarMascotas;

    RequestQueue requestQueue;

    private void loadUI(){
        edtTipoA = findViewById(R.id.edtTipoA);
        edtNombreA = findViewById(R.id.edtNombreA);
        edtColorA = findViewById(R.id.edtColorA);
        edtPesoA = findViewById(R.id.edtPesoA);

        btnActualizarMascotas = findViewById(R.id.btnActualizarMascota);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actualizar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadUI();

        Intent intent = getIntent();

        idMascota = intent.getIntExtra("id", -1);
        nombre = intent.getStringExtra("nombre");
        tipo = intent.getStringExtra("tipo");
        color = intent.getStringExtra("color");
        peso = intent.getDoubleExtra("peso", 0.0);

        edtNombreA.setText(nombre);
        edtTipoA.setText(tipo);
        edtColorA.setText(color);
        edtPesoA.setText(String.valueOf(peso));

        btnActualizarMascotas.setOnClickListener(v -> validarRegistroA());
    }

    private void validarRegistroA(){

        if(edtTipoA.getText().toString().isEmpty()){
            edtTipoA.setError("Complete el campo");
            return;
        }

        if (edtNombreA.getText().toString().isEmpty()){
            edtNombreA.setError("Escriba el nombre");
            return;
        }

        if (edtColorA.getText().toString().isEmpty()){
            edtColorA.setError("Campo obligatorio");
            return;
        }

        if (edtPesoA.getText().toString().isEmpty()){
            edtPesoA.setError("Ingrese peso");
            return;
        }

        tipo = edtTipoA.getText().toString().trim();
        nombre = edtNombreA.getText().toString().trim();
        color = edtColorA.getText().toString().trim();
        peso = Double.parseDouble(edtPesoA.getText().toString());

        if (!tipo.equals("Perro") && !tipo.equals("Gato")){
            edtTipoA.setError("Solo: Perro o Gato");
            return;
        }

        if (peso < 0){
            edtPesoA.setError("Peso debe ser positivo");
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mascotas");
        builder.setMessage("¿Seguro de actualizar?");

        builder.setPositiveButton("Si",(a,b) -> actualizarMascota());
        builder.setNegativeButton("No", null);

        builder.create().show();
    }

    private void actualizarMascota(){

        requestQueue = Volley.newRequestQueue(this);

        String URL = "http://192.168.101.17:3000/mascotas/" + idMascota;

        JSONObject jsonObject = new JSONObject();

        try{
            jsonObject.put("tipo", tipo);
            jsonObject.put("nombre", nombre);
            jsonObject.put("color", color);
            jsonObject.put("pesokg", peso);
        }catch (JSONException e){
            Log.e("Error", e.toString());
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                URL,
                jsonObject,
                response -> {
                    Toast.makeText(this, "Actualizado correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> {
                    Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                    Log.e("VolleyError", error.toString());
                }
        );

        requestQueue.add(request);
    }
}