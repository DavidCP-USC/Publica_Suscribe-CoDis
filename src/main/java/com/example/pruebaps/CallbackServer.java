package com.example.pruebaps;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.io.*;
import java.util.Scanner;

public class CallbackServer  extends Thread {
    private CallbackServerImpl objetoDistribuido;

    public CallbackServer(CallbackServerImpl e) {
        this.objetoDistribuido = e;
    }

    public static void main(String args[]) {
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        String portNum, registryURL;
        try {
            System.out.println("Enter the RMI registry port number:");
            portNum = (br.readLine()).trim();
            int RMIPortNum = Integer.parseInt(portNum);
            startRegistry(RMIPortNum);
            CallbackServerImpl exportedObj = new CallbackServerImpl();
            registryURL = "rmi://localhost:" + portNum + "/callback";
            Naming.rebind(registryURL, exportedObj);
            System.out.println("Callback Server ready.");
            CallbackServer servidor = new CallbackServer(exportedObj);
            servidor.start();
        }// end try
        catch (Exception re) {
            System.out.println("Exception in HelloServer.main: " + re);
        } // end catch

    } // end main

    //This method starts a RMI registry on the local host, if
    //it does not already exists at the specified port number.
    private static void startRegistry(int RMIPortNum) throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(RMIPortNum);
            registry.list();
            // This call will throw an exception
            // if the registry does not already exist
        } catch (RemoteException e) {
            // No valid registry at that port.
            Registry registry = LocateRegistry.createRegistry(RMIPortNum);
        }
    } // end startRegistry

    @Override
    public void run() {
        File archivo = new File("/home/david/OneDrive/3º/Computacion Distribuida/Practica4/publica-suscribeP4/src/main/java/ps/rr1.txt");
        // File archivo = new File("C:\\Users\\eduof\\Desktop\\publica-suscribeP4\\src\\main\\java\\ps\\rr1.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(archivo);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String s;
        while (scanner.hasNextLine()) {
            s = scanner.nextLine();
            this.objetoDistribuido.setDatoLeido(Float.parseFloat(s));
            System.out.println("Dato leido: " + Float.parseFloat(s));
            try {
                this.objetoDistribuido.doCallbacks();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Un cliente ha terminado");
            }
        }
        scanner.close();

    } // end class
}
