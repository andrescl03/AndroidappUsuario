package r.andres.proyectousuario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import r.andres.proyectousuario.entidades.Pregunta;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  , Response.Listener<String>, Response.ErrorListener{


    Button btnIniciarSesion;
    EditText txtMIdentificador;
    RequestQueue request;
    StringRequest StringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llamadaRecursos();
        btnIniciarSesion.setOnClickListener(this);
        request = Volley.newRequestQueue(this);


    }


    public void llamadaRecursos(){

        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        txtMIdentificador = findViewById(R.id.txtMIdentificador);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnIniciarSesion:
                cargarWebService();

                break;

        }
    }
    public void cargarWebService(){
        String URL = "http://192.168.1.41:8080/Rest_Servicio/rest/restorant/preguntasPorIndentificador?"
                + "identificador=" + txtMIdentificador.getText().toString();

        URL = URL.replace(" ", "%20");
        StringRequest = new StringRequest(Request.Method.GET, URL, this, this);
        request.add(StringRequest);


    }

    @Override
    public void onErrorResponse(VolleyError error) {


        Toast.makeText(this, "ErrorResponse " + error , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(String response) {

        ArrayList<JSONObject> lista = new ArrayList<JSONObject>();
        String arrayPreguntas[] = new String[5];
        int arrayPreguntasID[] = new int[5];

        try {
            JSONArray jsonArray = new JSONArray(response);

            ArrayList<Pregunta> listaEncuesta = new ArrayList<Pregunta>();

                for (int i = 0; i < jsonArray.length(); i++){
                lista.add(jsonArray.getJSONObject(i));
            }



            for (JSONObject obj : lista){
                Pregunta x = new Pregunta();
                x.setIdPregunta(Integer.parseInt(obj.getString("id")));
                x.setDescripcion(obj.getString("des"));
                listaEncuesta.add(x);
            }

            for (int i = 0 ; i <=4 ; i++){
                arrayPreguntas[i] = listaEncuesta.get(i).getDescripcion().toString();
                arrayPreguntasID[i] = listaEncuesta.get(i).getIdPregunta();
            }


            Intent panel = new Intent(this, Encuesta.class);
                panel.putExtra("preguntasID" ,arrayPreguntasID );
             panel.putExtra("preguntasEncuesta", arrayPreguntas);
             startActivity(panel);




        } catch (Exception e) {
        Toast.makeText(this,"error" + e , Toast.LENGTH_SHORT).show();
    }

    }


}
