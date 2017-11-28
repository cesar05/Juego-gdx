package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.pantallas.Menu;

/**
 * Created by Cesar on 27/11/2017.
 */

public class MainGame  extends Game {
    // Para empezar manejaremos unas dimensiones unicas para el juego y con ayuda de glViewport() ajustaremos estas dimensiones al tamaño de la pantalla del dispositivo
    public static int ANCHO_VIRTUAL;
    public static int ALTO_VIRTUAL;

    public SpriteBatch dibujadorPantalla; // Luego, este objeto sera el encargado de dibujar todas los elementos del juego en la pantalla.

    private AssetManager adminComponentes; // Por otra parte, este sera el el objeto que cargara y manejara los elementos del juego (Imagenes/Sonido/Musica).

    public static final String path="E:\\Desarrollos de Software\\LibGDX\\Juego-gdx\\android\\assets\\";

    // El siguiente metodo es como el constructor del juego, en donde debemos inicializar lo que necesitemos y abrir la pantalla de carga.
    @Override
    public void create() {
        // En primer lugar, se obtienen las dimensiones del display del dispositivo y que como tal estableceran el tamaño de la pantalla de juego
        ANCHO_VIRTUAL = Gdx.graphics.getWidth();
        ALTO_VIRTUAL = Gdx.graphics.getHeight();

        dibujadorPantalla = new SpriteBatch(); // Se inicializa el dibujador

        adminComponentes = new AssetManager(); // Se inicializa el controlador de elementos
        // Cargamos las imagenes
        adminComponentes.load(path+"Imagenes\\backgroundMenus.png", Texture.class);

        // Ahora llamamos la pantalla de carga
        setScreen(new Menu(this));
    }

    @Override
    public void render() {
        super.render(); // La idea de esta instruccion es delegarle la responsabilidad del dibujo a la pantalla que actualmente se esta mostrando al usuario
    }

    // Metodo para obtener facilmente el administrador de componentes
    public AssetManager getAdminComponentes() {
        return adminComponentes;
    }

}
