package com.kauafm.tipos;

import com.kauafm.interfaces.Ligavel;

public class Lampada implements Ligavel {

    private String comodo;
    private boolean ligada;

    public Lampada(String comodo, boolean ligada) {
        this.comodo = comodo;
        this.ligada = ligada;
    }

    @Override
    public void ligar() {
        ligada = true;
    }

    @Override
    public void desligar() {
        ligada = false;
    }

    @Override
    public boolean estaLigado() {
        return ligada;
    }

    public String getComodo() {
        return comodo;
    }
}