package com.mygdx.game.comunicacion;

import com.google.gson.Gson;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.datos.Jugador;
import javax.json.Json;
import javax.json.JsonObject;
import java.net.URI;

import javax.websocket.*;

/**
 * Created by Cesar on 28/10/2017.
 */

@ClientEndpoint
public class ClienteSocket {

    Session userSession = null;
    private MessageHandler messageHandler;
    private MyGdxGame myGdxGame;
    private int identificacion;
    private Gson gson;
    private String json;

    //public ClienteSocket(URI endpointURI) {
    public ClienteSocket(MyGdxGame myGdxGame){
        try {
            URI uri=new URI("ws://localhost:8080/JuegoWebSocket/ServerSocket");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, uri);
            System.out.println("Cliente conectado");
            this.myGdxGame=myGdxGame;
            gson=new Gson();
        }
        catch (DeploymentException e) {
            System.out.println("Conectando Cliente Error"+e.getMessage());
        }
        catch (Exception e) {
            System.out.println("por si no se puedo conectar :"+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("opening websocket = "+userSession.getId());
        this.userSession = userSession;
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing websocket = "+userSession.getId());
        this.userSession = null;
    }

    /**
     * Mensajes que llegan desde el servidor
     *
     * @param datos The text message
     */
    @OnMessage
    public void onMessage(String datos) {
        System.out.println(datos);

        Gson g = new Gson();
        Jugador jugador = g.fromJson(datos, Jugador.class);

        if(jugador.getEstado().equals("NuevoJugador")){
            System.out.println("Asignando id");
            this.identificacion=Integer.valueOf(jugador.getId());
            System.out.println("Mi id="+this.identificacion);
            this.myGdxGame.crearJugadorOnline(jugador);
        }
        else if(jugador.getEstado().equals("NuevoJugadorEnLinea")){
            this.myGdxGame.crearJugadorOnline(jugador);
        }
        else if(jugador.getEstado().equals("Jugando")){
            this.myGdxGame.movimientoJugadoresOnline(jugador);
        }

    }

    @OnError
    public void onError(Throwable param){
        System.out.println("Error en la comunicacion :"+param.getMessage());
    }

    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(this.identificacion+";"+message);
    }

    public void enviarDatos(Jugador jugador){
        this.json=this.gson.toJson(jugador,Jugador.class);
        this.userSession.getAsyncRemote().sendObject(this.json);
    }

    public int getIdentificacion() {
        return identificacion;
    }
}