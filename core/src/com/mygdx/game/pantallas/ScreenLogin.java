package com.mygdx.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Http.HttpPeticion;
import com.mygdx.game.MainGame;

import java.io.IOException;

/**
 * Created by Cesar on 28/11/2017.
 */

public class ScreenLogin implements Screen {

    MainGame juego;

    Label lblMsj;
    Label lblId;
    TextField txtId;
    TextButton btnLogin;
    TextButton botonSalir;
    TextButton botonRegistrarse;

    Skin skin;

    //Para organizar los elementos que van en este Screen
    Table tabla;
    Stage escenario;

    public ScreenLogin(final MainGame juego) {
        this.juego = juego;

        tabla= new Table();
        escenario = new Stage();
        tabla.setFillParent(true);
        // Cargo el estilo basico de los botones y otros elementos
        skin = new Skin(Gdx.files.internal("E:\\Desarrollos de Software\\LibGDX\\Juego-gdx\\android\\assets\\Skin\\uiskin.json"));

        this.btnLogin=new TextButton("ENTRAR",skin);
        this.lblMsj=new Label("",skin);
        this.lblId=new Label("IDENTIFICACION",skin);
        this.txtId=new TextField("",skin);
        this.txtId.setFocusTraversal(true);

        botonRegistrarse = new TextButton("REGISTRARSE", skin);
        botonSalir = new TextButton("SALIR", skin);

        // Ahora, pongo todos los elementos en una tabla, lo cual me permitira mejorar las distribuciones de estos
        tabla.defaults().pad(20.0f);
        tabla.add(this.lblId).width(MainGame.ANCHO_VIRTUAL/3).height(MainGame.ALTO_VIRTUAL/12).center();
        tabla.row();
        tabla.add(this.txtId).width(MainGame.ANCHO_VIRTUAL/3).height(MainGame.ALTO_VIRTUAL/12).center();
        tabla.row();
        tabla.add(this.btnLogin).width(MainGame.ANCHO_VIRTUAL/3).height(MainGame.ALTO_VIRTUAL/12).center();
        tabla.row();
        tabla.add(this.botonRegistrarse).width(MainGame.ANCHO_VIRTUAL/3).height(MainGame.ALTO_VIRTUAL/12).center();
        tabla.row();
        tabla.add(this.botonSalir).width(MainGame.ANCHO_VIRTUAL/3).height(MainGame.ALTO_VIRTUAL/12).center();
        tabla.row();
        tabla.add(this.lblMsj).width(MainGame.ANCHO_VIRTUAL/3).height(MainGame.ALTO_VIRTUAL/12).center();
        tabla.row();
        tabla.center();

        btnLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                lblMsj.setText("");
                System.out.println("Probando registro");
                try {
                    if(!validarDatos())return;
                    String url="JuegoWebSocket/LoginServlet?" +
                            "id="+txtId.getText();
                    System.out.println(url);
                    String msj= HttpPeticion.peticion(url);
                    if(msj.trim().equals("AUTENTICADO")){
                        juego.setScreen(new Menu(juego));
                        limpiarDatos();
                    }
                    lblMsj.setText(msj);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    lblMsj.setText(e.getMessage());
                    e.printStackTrace();
                }

            }
        });

        botonRegistrarse.addListener(new ClickListener(){
            @Override
            public  void clicked (InputEvent event, float x, float y)
            {
                System.out.println("Boton Registrarse");
                juego.setScreen(new ScreenUsuario(juego));
            }
        });

        botonSalir.addListener(new ClickListener(){
            @Override
            public  void clicked (InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }
        });

        escenario.addActor(tabla);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(escenario);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // A continuacion se actualiza el escenario del menu configuraciones
        escenario.act(delta);
        escenario.draw();
    }

    @Override
    public void resize(int width, int height) {
        escenario.getViewport().update(width, height, true); // Con esta instruccion se ajusta el tamaño del escenario
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        escenario.dispose();
        skin.dispose();
    }
    public boolean validarDatos() {
        try{
            if(txtId.getText().trim().equals("")) throw new Exception("Identificacion es obligatoria");
            return true;
        }
        catch(Exception e){
            lblMsj.setText(e.getMessage());
            return false;
        }
    }
    public void limpiarDatos(){
        txtId.setText("");
    }
}
