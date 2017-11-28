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
 * Created by Cesar on 27/11/2017.
 */

public class ScreenUsuario implements Screen {

    MainGame juego;

    Skin skinUsuario;

    Label lblId;
    Label lblNombre;
    Label lblAvatar;
    Label lblEmail;
    Label lblFechaNacimiento;
    Label lblMsj;

    TextField txtId;
    TextField txtNombre;
    TextField txtAvatar;
    TextField txtEmail;
    TextField txtFechaNacimiento;


    TextButton btnGuardar;
    TextButton btnAtras;



    //Para organizar los elementos que van en este Screen
    Table tabla;
    Stage escenario;

    public ScreenUsuario(final MainGame juego) {
        this.juego=juego;

        tabla= new Table();
        escenario = new Stage();

        tabla.setFillParent(true);
        // Cargo el estilo basico de los botones y otros elementos
        skinUsuario = new Skin(Gdx.files.internal("E:\\Desarrollos de Software\\LibGDX\\Juego-gdx\\android\\assets\\Skin\\uiskin.json"));

        this.lblId=new Label("Identificacion",skinUsuario);
        this.txtId=new TextField("",skinUsuario);
        this.txtId.setFocusTraversal(true);

        this.lblNombre=new Label("Nombre",skinUsuario);
        this.txtNombre=new TextField("",skinUsuario);
        this.txtNombre.setFocusTraversal(true);

        this.lblAvatar=new Label("Avatar",skinUsuario);
        this.txtAvatar=new TextField("",skinUsuario);
        this.txtAvatar.setFocusTraversal(true);

        this.lblEmail=new Label("Email",skinUsuario);
        this.txtEmail=new TextField("",skinUsuario);
        this.txtEmail.setFocusTraversal(true);

        this.lblFechaNacimiento=new Label("Fecha de Nacimiento",skinUsuario);
        this.txtFechaNacimiento=new TextField("",skinUsuario);
        this.txtFechaNacimiento.setFocusTraversal(true);

        this.btnGuardar=new TextButton("Registrar",skinUsuario);
        this.btnAtras=new TextButton("Atras",skinUsuario);

        this.lblMsj=new Label("",skinUsuario);

        // Ahora, pongo todos los elementos en una tabla, lo cual me permitira mejorar las distribuciones de estos
        tabla.defaults().pad(20.0f);
        tabla.add(this.lblId).width(MainGame.ANCHO_VIRTUAL/3).height(MainGame.ALTO_VIRTUAL/12);
        tabla.add(this.txtId).width(MainGame.ANCHO_VIRTUAL/3).height(MainGame.ALTO_VIRTUAL/12);
        tabla.row();
        tabla.add(this.lblNombre).width(MainGame.ANCHO_VIRTUAL/3).height(MainGame.ALTO_VIRTUAL/12);
        tabla.add(this.txtNombre).width(MainGame.ANCHO_VIRTUAL/3).height(MainGame.ALTO_VIRTUAL/12);
        tabla.row();
        tabla.add(this.lblAvatar).width(MainGame.ANCHO_VIRTUAL/3).height(MainGame.ALTO_VIRTUAL/12);
        tabla.add(this.txtAvatar).width(MainGame.ANCHO_VIRTUAL/3).height(MainGame.ALTO_VIRTUAL/12);
        tabla.row();
        tabla.add(this.lblEmail).width(MainGame.ANCHO_VIRTUAL/3).height(MainGame.ALTO_VIRTUAL/12);
        tabla.add(this.txtEmail).width(MainGame.ANCHO_VIRTUAL/3).height(MainGame.ALTO_VIRTUAL/12);
        tabla.row();
        tabla.add(this.lblFechaNacimiento).width(MainGame.ANCHO_VIRTUAL/3).height(MainGame.ALTO_VIRTUAL/12);
        tabla.add(this.txtFechaNacimiento).width(MainGame.ANCHO_VIRTUAL/3).height(MainGame.ALTO_VIRTUAL/12);
        tabla.row();
        tabla.add(this.btnGuardar).width(MainGame.ANCHO_VIRTUAL/3).height(MainGame.ALTO_VIRTUAL/12);
        tabla.add(this.btnAtras).width(MainGame.ANCHO_VIRTUAL/3).height(MainGame.ALTO_VIRTUAL/12);
        tabla.row();
        tabla.add(this.lblMsj).width(MainGame.ANCHO_VIRTUAL/3).height(MainGame.ALTO_VIRTUAL/12);
        tabla.center();

        btnGuardar.addListener(new ClickListener(){
            @Override
            public  void clicked (InputEvent event, float x, float y)
            {
                lblMsj.setText("");
                System.out.println("Probando registro");
                try {
                    if(!validarDatos())return;
                    String url="JuegoWebSocket/UsuarioServlet?" +
                            "id="+txtId.getText()+
                            "&nombre="+txtNombre.getText()+
                            "&avatar="+txtAvatar.getText()+
                            "&email="+txtEmail.getText()+
                            "&fechaNacimiento="+txtFechaNacimiento.getText();
                    System.out.println(url);
                    String msj=HttpPeticion.peticion(url);
                    if(msj.trim().equals("Registro guardado correctamente"))limpiarDatos();
                    lblMsj.setText(msj);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
                //juego.setScreen(new IniciarJuego());
            }
        });

        btnAtras.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new Menu(juego));
           }
       });

        // Ya con todos los elementos organizados procedemos a agregarlos al escenario, que es al fin y al cabo quien dirige la pantalla
        escenario.addActor(tabla);
    }

    @Override
    public void show() {
        // En el momento que la pantalla se muestra al usuario la siguiente instruccion le da al escenario el control de los eventos que haga el usuario (asi podremos detectar cuando toca la pantalla)
        Gdx.input.setInputProcessor(escenario);
        /*
        // Y ponemos en marcha una melodia
        if (!juego.getAdminComponentes().get("Audios/intro.ogg", Music.class).isPlaying())
        {
            juego.getAdminComponentes().get("Audios/intro.ogg", Music.class).play();
            juego.getAdminComponentes().get("Audios/intro.ogg", Music.class).setLooping(true);
        }*/
    }

    @Override
    public void render(float delta) {
        // Limpiamos la pantalla antes de dibujar cualquier cosa
        //Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Ahora, con la siguiente instruccion se procede a decirle al juego en donde debe apuntar la camara segun la vista que se establecio
        //camara.update();
        /*juego.dibujadorPantalla.setProjectionMatrix(camara.combined);*/

        // Finalmente, procedemos a dibujar el menu
       /* juego.dibujadorPantalla.begin();
        juego.dibujadorPantalla.draw(fondoPantallaConf, 0, 0,MainGame.ANCHO_VIRTUAL, MainGame.ALTO_VIRTUAL); // Aqui se dibuja el fondo y se ajusta a las dimensiones dadas
        juego.dibujadorPantalla.end();*/

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
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        escenario.dispose();
        skinUsuario.dispose();
    }

    public boolean validarDatos() {
        try{
            if(txtId.getText().trim().equals("")) throw new Exception("Identificacion es obligatoria");
            if(txtNombre.getText().trim().equals("")) throw new Exception("Nombre es obligatoria");
            if(txtAvatar.getText().trim().equals("")) throw new Exception("Avatar es obligatoria");
            if(txtEmail.getText().trim().equals("")) throw new Exception("Email es obligatoria");
            if(txtFechaNacimiento.getText().trim().equals("")) throw new Exception("Fecha de Nacimiento es obligatoria");
            return true;
        }
        catch(Exception e){
            lblMsj.setText(e.getMessage());
            return false;
        }
    }
    public void limpiarDatos(){
        txtId.setText("");
        txtNombre.setText("");
        txtAvatar.setText("");
        txtEmail.setText("");
        txtFechaNacimiento.setText("");
    }
}
