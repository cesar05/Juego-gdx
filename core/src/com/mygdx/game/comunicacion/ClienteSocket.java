package com.mygdx.game.comunicacion;

import com.mygdx.game.Jugador;
import com.mygdx.game.MyGdxGame;

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
    //public ClienteSocket(URI endpointURI) {
    public ClienteSocket(MyGdxGame myGdxGame){
        try {
            URI uri=new URI("ws://localhost:8080/JuegoWebSocket/ServerSocket");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, uri);
            System.out.println("Cliente conectado");
            this.myGdxGame=myGdxGame;
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
        System.out.println("opening websocket");
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
        System.out.println("closing websocket");
        this.userSession = null;
    }

    /**
     * Mensajes que llegan desde el servidor
     *
     * @param message The text message
     */
    @OnMessage
    public void onMessage(String message) {
        System.out.println("Mensaje desde servidor "+message);

        if(message.equals("NUEVOJUGADOR")){
            System.out.println("entro 1");
            this.myGdxGame.crearJugadorOnline();
        }
        else if(message.indexOf("IDENTIFICACION")>=0){
            System.out.println("Asignando id");
            String identificacion[]=message.split(";");
            this.identificacion=Integer.valueOf(identificacion[1]);
            System.out.println("Mi id="+this.identificacion);
            this.myGdxGame.crearJugadorOnline();
        }
        else{
            System.out.println("holassad");
            this.myGdxGame.movimientoJugadoresOnline(message);
        }
    }

    @OnError
    public void onError(Throwable param){
        System.out.println("Error en la comunicacion :"+param.getMessage());
    }
    /**
     * register message handler
     *
     * @param msgHandler
     */
    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    /**
     * Send a message.
     *
     * @param message
     */
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(this.identificacion+";"+message);
    }

    /**
     * Message handler.
     *
     *
     */
    public static interface MessageHandler {

        public void handleMessage(String message);
    }

    public int getIdentificacion() {
        return identificacion;
    }
}