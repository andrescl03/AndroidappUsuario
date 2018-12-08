package r.andres.proyectousuario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class Encuesta extends AppCompatActivity implements  View.OnClickListener  , Response.Listener<String>, Response.ErrorListener{


    TextView lblEPregunta;
    RadioButton rbtnE1, rbtnE2, rbtnE3, rbtnE4, rbtnE5;
    Button btnESiguiente;
    RadioGroup rbgEncuesta;

    String arrayPreguntas[] = new String[5];

    List<String> arrayPreguntasID = new ArrayList<>();
    List<String> arrayRespuesta = new ArrayList<>();
    StringRequest StringRequest;
    RequestQueue request;

    int contador = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);

        lblEPregunta = findViewById(R.id.lblEPregunta);
        btnESiguiente = findViewById(R.id.btnESiguiente);
        btnESiguiente.setOnClickListener(this);
        request = Volley.newRequestQueue(this);

        rbtnE1 = findViewById(R.id.rbtnE1);
        rbtnE2 = findViewById(R.id.rbtnE2);
        rbtnE3 = findViewById(R.id.rbtnE3);
        rbtnE4 = findViewById(R.id.rbtnE4);
        rbtnE5 = findViewById(R.id.rbtnE5);
        rbgEncuesta = findViewById(R.id.rbgEncuesta);
        obtenerElementosIntent();

        rbtnE1.setText(" Muy buena");
        rbtnE2.setText(" Buena");
        rbtnE3.setText(" Regular");
        rbtnE4.setText(" Mala");
        rbtnE5.setText(" Muy mala");

        lblEPregunta.setText(arrayPreguntas[0]);

    }

    public void cargarPregunta(){


        int index = rbgEncuesta.indexOfChild(findViewById(rbgEncuesta.getCheckedRadioButtonId())) + 1;
        arrayRespuesta.add(String.valueOf(index));

        lblEPregunta.setText(arrayPreguntas[contador]);
        Log.d("TAG", "cargarPregunta: " +arrayPreguntas[contador]);
        Log.d("TAG2", "RESPUESTA: " +arrayRespuesta.get(contador));

        if(contador==4){
            cargarWebService();
            Toast.makeText(this,"llega ", Toast.LENGTH_SHORT).show();
        }
        else{
        contador++;
        }
    }

    public void obtenerElementosIntent(){
        Bundle datos = this.getIntent().getExtras();

        String arrayPreguntasobtenidas[] = new String[5];
        int arrayPreguntasIDObtenidas[] = new int[5];

        arrayPreguntas = datos.getStringArray("preguntasEncuesta");
        arrayPreguntasIDObtenidas = datos.getIntArray("preguntasID");


        for (int i= 0 ; i<arrayPreguntasIDObtenidas.length ; i++){
        arrayPreguntasID.add(arrayPreguntasIDObtenidas.toString());
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnESiguiente:
                cargarPregunta();
                break;
        }
    }

    public void guardarArray(){
    }
    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {

    }
    public void cargarWebService(){
        String URL = "http://localhost:8080/Rest_Servicio/rest/restorant/query2?"
                + "pre=" + arrayPreguntasID + "&res=" + arrayRespuesta;

        URL = URL.replace(" ", "%20");
        StringRequest = new StringRequest(Request.Method.GET, URL, this, this);
        request.add(StringRequest);
    }
}
