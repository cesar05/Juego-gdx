package com.mygdx.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.MainGame;
import com.mygdx.game.comunicacion.ClienteSocket;
import com.mygdx.game.datos.Jugador;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cesar on 27/11/2017.
 */

public class IniciarJuego implements Screen {

    MainGame juego;

    SpriteBatch batch;
    private Texture img,imgSoldado;
    private TextureRegion textureRegionSoldado;
    private Sprite soldado;
    private static final int VEL=5;
    private int identificacion;
    private ClienteSocket clienteSocket;

    private List<Sprite> jugadorsOnline;
    private List<Texture> texturesOnline;


    private Jugador jugadorDatos;

    Skin skin;
    TextButton btnSalir;
    //Para organizar los elementos que van en este Screen
    Table tabla;
    Stage escenario;

    public IniciarJuego(final MainGame juego){

        try {
            batch = new SpriteBatch();
            imgSoldado = new Texture("E:\\Desarrollos de Software\\LibGDX\\Juego-gdx\\android\\assets\\M484SpaceSoldier.png");
            soldado = new Sprite(imgSoldado, 10, 10, 49, 49);
            soldado.setPosition(50, 50);
            clienteSocket=new ClienteSocket(this);
            jugadorsOnline=new ArrayList<Sprite>();
            texturesOnline=new ArrayList<Texture>();
            this.jugadorDatos=new Jugador();

            /*
            this.btnSalir=new TextButton("Salir",skin);
            tabla= new Table();
            escenario = new Stage();
            tabla.defaults().pad(20.0f);
            tabla.add(this.btnSalir).width(50).height(20);
            tabla.row();

            escenario.addActor(tabla);*/
        }
        catch(Exception e){
            System.out.println("Error construyendo app "+e.getMessage());
        }
    }


    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //soldado.draw(batch);
        for(Sprite sprite:jugadorsOnline){
            sprite.draw(batch);
        }
        controles();

        batch.end();
        //escenario.act(delta);
        //escenario.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void show()
    {

    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
        imgSoldado.dispose();
    }

    @Override
    public void resume() {
        //super.resume();
    }

    @Override
    public void hide() {

    }

    public void controles(){
        boolean up=Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean down=Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN);
        boolean left=Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean right=Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);


        if(jugadorsOnline.size()>0) {
            float x = jugadorsOnline.get(clienteSocket.getIdentificacion()).getX(),
                    y = jugadorsOnline.get(clienteSocket.getIdentificacion()).getY();
            if (up && !down) {
                y = y + VEL;
            } else if (down && !up) {
                y = y - VEL;
            }
            if (left && !right) {
                x = x - VEL;
            } else if (right && !left) {
                x = x + VEL;
            }
            if (x < 0)
                x = 0;
            if (y < 0)
                y = 0;
            if (x > Gdx.graphics.getWidth() - soldado.getWidth())
                x = Gdx.graphics.getWidth() - soldado.getWidth();
            if (y > Gdx.graphics.getHeight() - soldado.getHeight())
                y = Gdx.graphics.getHeight() - soldado.getHeight();

            //Se envia la informacion de posicion del jugador.
            if (up || down || left || right) {
                jugadorDatos.setEstado("Jugando");
                jugadorDatos.setId(String.valueOf(clienteSocket.getIdentificacion()));
                jugadorDatos.setX(String.valueOf(x));
                jugadorDatos.setY(String.valueOf(y));
                clienteSocket.enviarDatos(jugadorDatos);
            }

            jugadorsOnline.get(clienteSocket.getIdentificacion()).setPosition(x, y);

        }

        if(Gdx.input.isKeyPressed(Input.Keys.K)) {
            clienteSocket.enviarDatos(new Jugador("CREADO","1", "50", "60"));
        }
    }


    public void crearJugadorOnline(Jugador jugador){
        //System.out.println("crearJugadorOnline");
        //jugadorsOnline.add(new Sprite(imgSoldado, 10, 10, 49, 49));
        Sprite sprite=new Sprite(imgSoldado, 10, 10, 49, 49);
        sprite.setPosition(Float.valueOf(jugador.getX()),Float.valueOf(jugador.getY()));
        jugadorsOnline.add(sprite);
    }

    public void movimientoJugadoresOnline(Jugador datosJugador){
        for(Sprite sprite:jugadorsOnline){
            jugadorsOnline.get(Integer.valueOf(datosJugador.getId())).setPosition(Float.valueOf(datosJugador.getX()),Float.valueOf(datosJugador.getY()));
        }
    }
}
