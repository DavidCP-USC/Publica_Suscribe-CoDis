/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.pruebaps;
import java.rmi.*;

/**
 *
 * @author david
 */
public interface CallbackServerInterface extends Remote {

    public String sayHello( ) throws java.rmi.RemoteException;

    // This remote method allows an object client to 
    // register for callback
    // @param callbackClientObject is a reference to the
    //        object of the client; to be used by the server
    //        to make its callbacks.

    public void registerForCallback( CallbackClientInterface callbackClientObject, Integer tiempo) throws java.rmi.RemoteException;

    // This remote method allows an object client to 
    // cancel its registration for callback

    public void unregisterForCallback(CallbackClientInterface callbackClientObject) throws java.rmi.RemoteException;

}
