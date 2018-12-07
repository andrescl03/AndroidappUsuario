package r.andres.proyectousuario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Encuesta extends AppCompatActivity {


    TextView lblEPregunta;
    RadioButton rbtnE1, rbtnE2, rbtnE3, rbtnE4, rbtnE5;
    Button btnESiguiente;
    RadioGroup rbgEncuesta;
    String arrayPreguntas[] = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);

        lblEPregunta = findViewById(R.id.lblEPregunta);
        btnESiguiente = findViewById(R.id.btnESiguiente);
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

    public void obtenerElementosIntent(){
        Bundle datos = this.getIntent().getExtras();
        arrayPreguntas = datos.getStringArray("preguntasEncuesta");

    }



}
