package com.example.pruebaps;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CallbackServerImpl extends UnicastRemoteObject implements CallbackServerInterface {

    //private Vector<CallbackClientInterface> clientList;
    private Float datoLeido;
    private HashMap<CallbackClientInterface, Integer> clientList;


    public CallbackServerImpl() throws RemoteException {
        super();
        clientList = new HashMap<>();

    }

    public String sayHello() throws java.rmi.RemoteException {
        return ("hello");
    }

    @Override
    public synchronized void registerForCallback(CallbackClientInterface callbackClientObject, Integer tiempo) throws java.rmi.RemoteException {
        // store the callback object into the vector
        // System.out.println("Entre");
        if (!(clientList.containsKey(callbackClientObject))) {
            clientList.put(callbackClientObject, tiempo);
        } // end if
        System.out.println("Registered new client ");
    }

    // This remote method allows an object client to
// cancel its registration for callback
// @param id is an ID for the client; to be used by
// the server to uniquely identify the registered client.
    @Override
    public synchronized void unregisterForCallback(CallbackClientInterface callbackClientObject) throws java.rmi.RemoteException {
        if (clientList.containsKey(callbackClientObject)) {
            clientList.remove(callbackClientObject);
        }
        System.out.println("Unregistered client ");
    }

    public synchronized void doCallbacks() {
        CallbackClientInterface nextClient = null;
        Iterator<Map.Entry<CallbackClientInterface, Integer>> iterator = clientList.entrySet().iterator();
        ArrayList<CallbackClientInterface> clientesADesregistrar = new ArrayList<>();
        try {
            // make callback to each registered client
            for (Map.Entry<CallbackClientInterface, Integer> entrada: clientList.entrySet()){
                System.out.println("**************************************\nCallbacks initiated ---");
                System.out.println("doing " + clientList.keySet() + "-th callback");
                // Obtener el cliente actual y notificarle
                nextClient = entrada.getKey();
                nextClient.notifyMe(this.datoLeido);
                System.out.println(this.datoLeido);
                entrada.setValue(entrada.getValue() - 1);
                if (entrada.getValue() <= 0){
                    clientesADesregistrar.add(entrada.getKey());
                }

                System.out.println("Tiempo de suscripción restante: " + entrada.getValue());

                System.out.println("\n");
                System.out.println("********************************\nServer completed callbacks ---");
            }

            // Eliminamos los clientes que haya que desregistrar
            for(CallbackClientInterface c: clientesADesregistrar){
                unregisterForCallback(c);
            }

        }
        ///// SI HAY UNA EXCEPCION EN LA RECEPCION DEL CALLBACK SE DA DE BAJA AL CLIENTE
        catch (RemoteException e) {
            // Manejar la excepción de recepción del callback aquí
            System.out.println("No se puede enviar el mensaje al cliente");
            try {
                unregisterForCallback(nextClient);
            } catch (RemoteException ex) {
                System.out.println("Ya desregistrado");
            }
        }

    }

    public void setDatoLeido(Float f){
            this.datoLeido = f;
    }
}// end CallbackServerImpl class

