package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.comunicacion.ClienteSocket;

import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	private Texture img,imgSoldado;
	private TextureRegion textureRegionSoldado;
	private Sprite soldado;
	private static final int VEL=5;
	private int identificacion;
	private ClienteSocket clienteSocket;

	private List<Sprite> jugadorsOnline;
	private List<Texture> texturesOnline;
	private List<Jugador> jugadorList;

	@Override
	public void create () {
		try {
			batch = new SpriteBatch();
			imgSoldado = new Texture("E:\\Desarrollos de Software\\LibGDX\\Juego-gdx\\android\\assets\\M484SpaceSoldier.png");
			soldado = new Sprite(imgSoldado, 10, 10, 49, 49);
			soldado.setPosition(50, 50);
			clienteSocket=new ClienteSocket(this);
			jugadorsOnline=new ArrayList<Sprite>();
			texturesOnline=new ArrayList<Texture>();
			jugadorList=new ArrayList<Jugador>();
		}
		catch(Exception e){
			System.out.println("Error construyendo app "+e.getMessage());
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//soldado.draw(batch);
		for(Sprite sprite:jugadorsOnline){
			sprite.draw(batch);
		}
		controles();
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		imgSoldado.dispose();
	}

	public void controles(){
		boolean up=Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP);
		boolean down=Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN);
		boolean left=Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
		boolean right=Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);

		//float x=soldado.getX(),y=soldado.getY();
		if(jugadorsOnline.size()>0) {
			//float x = jugadorsOnline.get(0).getX(), y = jugadorsOnline.get(0).getY();
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

			//Envio posicion al servidor solo se se oprimio alguna tecla de movimiento
			if (up || down || left || right)
				clienteSocket.sendMessage(x + ";" + y);

			//soldado.setPosition(x,y);

			jugadorsOnline.get(clienteSocket.getIdentificacion()).setPosition(x, y);

		}
	}


	public void crearJugadorOnline(){
		System.out.println("entro 2");
		//texturesOnline.add(new Texture("E:\\Desarrollos de Software\\LibGDX\\Juego-gdx\\android\\assets\\M484SpaceSoldier.png"));
		jugadorsOnline.add(new Sprite(imgSoldado, 10, 10, 49, 49));
		//soldado.setPosition(500,500);
	}

	public void movimientoJugadoresOnline(String datosJugador){
		for(Sprite sprite:jugadorsOnline){
			System.out.println("Desde movimientoJugadoresOnline = "+datosJugador);
			String datos[]=datosJugador.split(";");
			int id=Integer.valueOf(datos[0]);
			jugadorsOnline.get(id).setPosition(Float.valueOf(datos[1]),Float.valueOf(datos[2]));
		}
		/*if(jugadorsOnline.size()>0) {
			System.out.println("Desde movimientoJugadoresOnline="+datosJugador);
			String datos[]=datosJugador.split(";");
			jugadorsOnline.get(0).setPosition(Float.valueOf(datos[1]),Float.valueOf(datos[2]));
		}
		*/
	}

}
