
package com.example.pruebaps;

import java.rmi.RemoteException;

public interface CallbackClientInterface extends java.rmi.Remote {
    public String notifyMe(float RR) throws RemoteException;
    // Este método permite al cliente actualizar su tiempo de registro en el servidor.
    void updateRegistrationTime(Integer tiempoARestar) throws RemoteException;

    Integer obtenerTiempoSuscrito()throws RemoteException;

    void setTiempoSuscrito(Integer i)throws RemoteException;

    HelloController getGui() throws RemoteException;

    //void onDataReceived(Float data) throws RemoteException;

}
