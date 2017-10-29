package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Cesar on 29/10/2017.
 */

public class Jugador {
    private String id;
    private Sprite sprite;
    private int x,y;
    public Jugador() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
