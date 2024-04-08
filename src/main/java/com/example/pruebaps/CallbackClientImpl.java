package com.example.pruebaps;
import java.io.IOException;
import java.rmi.*;
import java.rmi.server.*;

public class CallbackClientImpl extends UnicastRemoteObject implements CallbackClientInterface {
    public HelloController gui;
    private Integer tiempoSuscrito;
    public CallbackClientImpl(HelloController gui, int time) throws RemoteException {
        super( );
        this.gui = gui;
        this.tiempoSuscrito = time;
    }

    public int getTiempoSuscrito(){
        return tiempoSuscrito;
    }

    public String notifyMe(float RR){
        String returnMessage = "" + RR;
        System.out.println(returnMessage);

        try {
            this.gui.anadirPunto(RR);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return returnMessage;
    }

    @Override
    public void updateRegistrationTime(Integer tiempoARestar) throws RemoteException {
        // Añadimos el tiempo extra al tiempoque el cliente está suscrito

    }

    @Override
    public Integer obtenerTiempoSuscrito(){
        return this.tiempoSuscrito;
    }

    @Override
    public void setTiempoSuscrito(Integer i){
        this.tiempoSuscrito = i;
    }

    @Override
    public HelloController getGui(){
        return this.gui;
    }

}// end CallbackClientImpl class   
