package com.mygdx.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Http.HttpPeticion;
import com.mygdx.game.MainGame;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by Cesar on 28/11/2017.
 */

public class ScreenConfiguracion implements Screen {

    MainGame juego;

    Label lblMsj;

    Label lblId;
    Label lblSonido;
    Label lblIdioma;
    Label lblResolucion;
    Label lblAvatarImg;

    TextField txtId;
    CheckBox checkSonido;
    SelectBox<String> sltIdioma;
    SelectBox<String> sltResolucion;
    TextField txtAvatarImg;

    TextButton btnGuardar;
    TextButton btnAtras;

    Skin skin;

    //Para organizar los elementos que van en este Screen
    Table tabla;
    Stage escenario;

    public ScreenConfiguracion(final MainGame juego) {
        this.juego = juego;

        tabla= new Table();
        escenario = new Stage();
        tabla.setFillParent(true);
        // Cargo el estilo basico de los botones y otros elementos
        skin = new Skin(Gdx.files.internal("E:\\Desarrollos de Software\\LibGDX\\Juego-gdx\\android\\assets\\Skin\\uiskin.json"));

        this.checkSonido=new CheckBox(" Activar sonido",skin);
        this.sltIdioma=new SelectBox<String>(skin);
        this.sltIdioma.setItems("Español","Ingles","Frances");
        this.sltResolucion=new SelectBox<String>(skin);
        this.sltResolucion.setItems("800 * 600","1152 * 648","1280 * 720","1360 * 768");
        this.txtAvatarImg=new TextField("",skin);
        this.btnAtras=new TextButton("ATRAS",skin);
        this.btnGuardar = new TextButton("GUARDAR", skin);

        this.lblMsj=new Label("",skin);
        this.lblId=new Label("IDENTIFICACION",skin);
        this.txtId=new TextField("",skin);
        this.txtId.setFocusTraversal(true);



        // Ahora, pongo todos los elementos en una tabla, lo cual me permitira mejorar las distribuciones de estos
        tabla.defaults().pad(10.0f);
        tabla.add(this.checkSonido).width(MainGame.ANCHO_VIRTUAL/3).height(MainGame.ALTO_VIRTUAL/12).center();
        tabla.row();
        tabla.add(this.sltIdioma).width(MainGame.ANCHO_VIRTUAL/3).height(30).center();
        tabla.row();
        tabla.add(this.sltResolucion).width(MainGame.ANCHO_VIRTUAL/3).height(30).center();
        tabla.row();
        tabla.add(this.sltResolucion).width(MainGame.ANCHO_VIRTUAL/3).height(30).center();
        tabla.row();
        tabla.add(this.txtAvatarImg).width(MainGame.ANCHO_VIRTUAL/3).height(30).center();
        tabla.row();
        tabla.add(this.btnGuardar).width(MainGame.ANCHO_VIRTUAL/3).height(30).center();
        tabla.row();
        tabla.add(this.btnAtras).width(MainGame.ANCHO_VIRTUAL/3).height(30).center();
        tabla.row();
        tabla.add(this.txtId).width(MainGame.ANCHO_VIRTUAL/3).height(30).center();
        tabla.row();
        tabla.add(this.lblMsj).width(MainGame.ANCHO_VIRTUAL/3).height(30).center();
        tabla.center();

        btnGuardar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                lblMsj.setText("");
                System.out.println("Guardando Configuracion");
                try {
                    String sonido=checkSonido.isChecked()?"S":"N";
                    String url="JuegoWebSocket/ConfigServlet?" +
                            "id="+juego.getIdentificacion()+
                            "&sonido="+URLEncoder.encode(sonido,"UTF-8")+
                            "&idioma="+URLEncoder.encode(sltIdioma.getSelected(),"UTF-8")+
                            "&resolucion="+ URLEncoder.encode(sltResolucion.getSelected(), "UTF-8")+
                            "&avatarimg="+URLEncoder.encode(txtAvatarImg.getText(),"UTF-8");

                    System.out.println(url);
                    String msj= HttpPeticion.peticion(url);
                    lblMsj.setText(msj);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    lblMsj.setText(e.getMessage());
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    lblMsj.setText(e.getMessage());
                }

            }
        });

        btnAtras.addListener(new ClickListener(){
            @Override
            public  void clicked (InputEvent event, float x, float y)
            {
                juego.setScreen(new Menu(juego));
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

}
