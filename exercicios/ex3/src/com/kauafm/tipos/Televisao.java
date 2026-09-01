package com.kauafm.tipos;

import com.kauafm.interfaces.Ligavel;

public class Televisao implements Ligavel {

    private String marca;
    private boolean ligada;

    public Televisao(String marca, boolean ligada) {
        this.marca = marca;
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

    public String getMarca() {
        return marca;
    }
} 