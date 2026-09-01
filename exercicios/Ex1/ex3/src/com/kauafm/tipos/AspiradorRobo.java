package com.kauafm.tipos;

import com.kauafm.interfaces.*;

public class AspiradorRobo implements Ligavel, Recarregavel {

    private boolean ligado;
    private int nivelBateria;

    public AspiradorRobo(int nivelBateria, boolean ligado) {
        this.nivelBateria = nivelBateria;
        this.ligado = ligado;
    }

    @Override
    public void ligar() {
        ligado = true;
    }

    @Override
    public void desligar() {
        ligado = false;
    }

    @Override
    public boolean estaLigado() {
        return ligado;
    }

    @Override
    public void recarregar(int minutos) {
        nivelBateria += minutos;

        if (nivelBateria > 100) {
            nivelBateria = 100;
        }
    }

    @Override
    public int getNivelBateria() {
        return nivelBateria;
    }
}