package com.example.pruebaps;
import java.io.*;
import java.rmi.*;

public class CalbackClient extends Thread{
    private HelloController gui;
    public CallbackServerInterface CallbackServer;
    public CallbackClientInterface CallbackObj;
    public CalbackClient(HelloController gui){
        this.gui = gui;
    }
    @Override
    public void run() {
        try {
            int RMIPort;
            int time;
            String IP;
            InputStreamReader is =new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is);
            System.out.println("IP del servidor:");
            IP = br.readLine();
            System.out.println("Enter the RMIregistry port number:");
            String portNum = br.readLine();
            RMIPort = Integer.parseInt(portNum); 
            System.out.println("Enter how many seconds to stay registered:");
            String timeDuration = br.readLine();
            time = Integer.parseInt(timeDuration);
            System.out.println(time);
            String registryURL = "rmi://" + IP + ":" + RMIPort + "/callback";  
            // find the remote object and cast it to an interface object
            this.CallbackServer =(CallbackServerInterface)Naming.lookup(registryURL);
            System.out.println("Lookup completed " );
            System.out.println("Server said " + this.CallbackServer.sayHello());
            this.CallbackObj = new CallbackClientImpl(this.gui, time);
            // register for callback
            System.out.println("Trying to register for callbacks");
            Integer tiempo = new Integer(time);
            this.CallbackServer.registerForCallback(this.CallbackObj, tiempo);
            while (true){
                while(this.CallbackObj.obtenerTiempoSuscrito() > 0){
                    Thread.sleep(1000);
                    if(this.gui.tiempoExtra != 0 || this.gui.renovar){

                        // ESTO YA SE PODRÍA PONER EN EL DISPARADOR DEL EVENTO
                        this.CallbackServer.unregisterForCallback(this.CallbackObj);
                        this.CallbackServer.registerForCallback(this.CallbackObj, this.gui.tiempoExtra);
                        this.CallbackObj.setTiempoSuscrito(this.gui.tiempoExtra-1);
                        // Reiniciamos ese tiempo extra
                        this.gui.tiempoExtra = 0;
                        this.gui.renovar = false;
                    }
                    else{
                        this.CallbackObj.setTiempoSuscrito(this.CallbackObj.obtenerTiempoSuscrito()-1);
                        // System.out.println("Time3: " + this.CallbackObj.obtenerTiempoSuscrito());
                    }
                    System.out.println("TiempoTotal: " + this.CallbackObj.obtenerTiempoSuscrito());
                }
                // wait for invocations from clients
                this.CallbackServer.unregisterForCallback(this.CallbackObj);
                System.out.println("Unregistered for callback.");
                System.out.println("Quieres volver a registrarte? Renueva en la ventana gráfica");
                this.gui.renovar = false;
                while(!this.gui.renovar){
                    Thread.sleep(1);
                }
                System.out.println("Renovado");
                this.gui.puntos.getData().clear();
                this.CallbackObj.setTiempoSuscrito(this.gui.tiempoExtra);
                System.out.println("Time: " + this.CallbackObj.obtenerTiempoSuscrito());
                this.CallbackServer.registerForCallback(this.CallbackObj, this.CallbackObj.obtenerTiempoSuscrito());
                //this.gui.tiempoExtra = 0;
            }
        } // end try
        catch (Exception e) {
            System.out.println("Exception in CallbackClient: " + e);
        } // end catch
    } //end main
}
