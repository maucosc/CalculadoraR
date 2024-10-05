package com.example.calnew;

import java.io.Serializable;

public class Registro implements Serializable {

    private String resultado;

    public Registro(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

}
