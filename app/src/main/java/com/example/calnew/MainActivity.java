package com.example.calnew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etNumero;
    private TextView tvResultado;
    private Button btnSumar, btnRestar, btnMultiplicar, btnDividir, btnIgual, btnC, btnVerResultado;

    private double numero1 = 0;
    private double numero2 = 0;
    private String operacion = "";

    private boolean calculado = false; //En caso de que ya haya un calculo pasa a true
    private boolean nuevaOp = false; //En caso de que ya haya un calculo pasa a true


    private List<Registro> listaRegistro = new ArrayList<>();
    ArrayList<String> listaResultado = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vincular elementos de la UI
        etNumero = findViewById(R.id.etNumero);
        tvResultado = findViewById(R.id.tvResultado);
        btnSumar = findViewById(R.id.btnSumar);
        btnRestar = findViewById(R.id.btnRestar);
        btnMultiplicar = findViewById(R.id.btnMultiplicar);
        btnDividir = findViewById(R.id.btnDividir);
        btnIgual = findViewById(R.id.btnIgual);
        btnC = findViewById(R.id.btnC);
        btnVerResultado = findViewById(R.id.btnVerResultado);

        BorrarLista();

        btnVerResultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirRegistro();
            }
        });
        // Configurar botones de operaciones
        btnSumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarNumeroYOperacion("+");
            }
        });

        btnRestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarNumeroYOperacion("-");
            }
        });

        btnMultiplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarNumeroYOperacion("*");
            }
        });

        btnDividir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarNumeroYOperacion("/");
            }
        });

        // Configurar botón de igual
        btnIgual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularResultado();
                registro();
                nuevaOp = true;
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BorrarLista();
            }
        });

    }

    private void BorrarLista() {
        listaResultado.clear(); //Limpia lista
        listaRegistro.clear(); //Limpia lista
        operacion = ""; //Reinicia operacion
        etNumero.setText(""); //Reinicia numero
        numero1 = 0; //Reinicia primer numero a 0
        numero2 = 0; //Reinicia segundo numero a 0
        calculado = false;// reiniciar calculo a false
        nuevaOp = false; //reiniciar nueva operacion a false
        tvResultado.setText("Resultado: Ingrese algo"); //Reinicia resultado

    }

    private void guardarNumeroYOperacion(String op) {
        String numeroIngresado = etNumero.getText().toString();

        if (!numeroIngresado.isEmpty()) {
            if (nuevaOp) {
                // Solo actualiza numero1 si no se ha calculado nada antes
                numero1 = Double.parseDouble(numeroIngresado);
                realizarOperacion();
                nuevaOp = false;
            } else {
                numero2 = Double.parseDouble(numeroIngresado);
                realizarOperacion();
            }
            operacion = op;
            calculado = false;
            etNumero.setText(""); // Limpia el campo para que el usuario ingrese el siguiente número
        } else {
            Toast.makeText(this, "Por favor ingrese un número", Toast.LENGTH_SHORT).show();
        }
    }

    private void realizarOperacion() {
        switch (operacion) {
            case "+":
                numero1 += numero2;
                break;
            case "-":
                numero1 -= numero2;
                break;
            case "*":
                numero1 *= numero2;
                break;
            case "/":
                if (numero2 != 0) {
                    numero1 /= numero2;
                } else {
                    Toast.makeText(this, "No se puede dividir por cero", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }
    }

    private void calcularResultado() {
        String numeroIngresado = etNumero.getText().toString();
        if (!operacion.isEmpty()) {
            if (!numeroIngresado.isEmpty()) {
                tvResultado.setText("");
                numero2 = Double.parseDouble(numeroIngresado);
                if (operacion.equals("/")&& numero2 == 0){
                    Toast.makeText(this, "No se puede dividir por cero", Toast.LENGTH_SHORT).show();
                    tvResultado.setText("Resultado: No dividir por cero");
                    return;
                }else{
                    realizarOperacion();
                }


                calculado = true;
                tvResultado.setText("Resultado: " + new DecimalFormat("#.##").format(numero1));
                etNumero.setText(""); // Limpiar el EditText para nuevas operaciones
                operacion = "";
                nuevaOp = true;
            } else {
                Toast.makeText(this, "Por favor ingrese un número", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Por favor ingrese un número", Toast.LENGTH_SHORT).show();
        }

    }


    private void registro() {
        String resp = tvResultado.getText().toString();

        if (calculado && !resp.equals("Resultado: Ingrese algo")) {
            Registro registro = new Registro(resp);
            listaRegistro.add(registro);
            Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
            calculado = false; // Reiniciar después de guardar el registro
        } else {
            Toast.makeText(this, "No registrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void abrirRegistro() {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("listaRegistro", new ArrayList<>(listaRegistro));
        startActivity(intent);
    }
}
