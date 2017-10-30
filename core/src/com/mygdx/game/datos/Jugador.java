package com.mygdx.game.datos;

import java.io.Serializable;

/**
 * Created by Cesar on 29/10/2017.
 */

public class Jugador implements Serializable{

    private String estado;
    private String id;
    private String x, y;

    public Jugador() {
    }

    public Jugador(String estado, String id, String x, String y) {
        this.estado = estado;
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
