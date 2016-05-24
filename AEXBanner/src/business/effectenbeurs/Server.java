/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.effectenbeurs;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author gebruiker-pc
 */
public class Server extends Application
{

    private static final int PORT = 1099;
    private static final String BINDINGNAME = "MockEffectenbeurs";

    private IEffectenbeurs effectenbeurs;

    public Server()
    {
        try
        {
            this.effectenbeurs = new MockEffectenbeurs();
            Registry registry = LocateRegistry.createRegistry(PORT);
            registry.rebind(BINDINGNAME, this.effectenbeurs);
        }
        catch (RemoteException ex)
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        new Server();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
    }
}
