/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.effectenbeurs;

import business.effectenbeurs.fonds.Fonds;
import business.effectenbeurs.fonds.IFonds;
import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.Publisher;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gebruiker-pc
 * @author Jeroen Janssen
 */
public class MockEffectenbeurs extends UnicastRemoteObject implements IEffectenbeurs
{

    private final ArrayList<IFonds> koersen;
    private final transient Timer updateTimer;
    private final Publisher publisher;

    public MockEffectenbeurs() throws RemoteException
    {
        this.publisher = new Publisher();
        this.publisher.registerProperty("koersen");
        
        this.koersen = this.fillKoersen();

        this.updateTimer = new Timer();
        this.updateTimer.schedule(new UpdateTimerTask(this), 0, 2000);
    }

    // Stop banner controller
    public void stop()
    {
        this.updateTimer.cancel();
    }

    @Override
    public List<IFonds> getKoersen() throws RemoteException
    {
        return Collections.unmodifiableList(this.koersen);
    }

    private ArrayList<IFonds> fillKoersen()
    {
        ArrayList<IFonds> returnKoersen = new ArrayList<>();

        returnKoersen.add(new Fonds("Allberts", 30.240));
        returnKoersen.add(new Fonds("ABN AMRO", 17.690));
        returnKoersen.add(new Fonds("Aegon", 4.907));
        returnKoersen.add(new Fonds("Ahold", 19.710));
        returnKoersen.add(new Fonds("Akzo Nobel", 59.230));
        returnKoersen.add(new Fonds("Altice", 15.900));
        returnKoersen.add(new Fonds("KPN", 3.616));
        returnKoersen.add(new Fonds("Unibail-Rodamco", 240.700));

        return returnKoersen;
    }

    protected void updateKoersen()
    {        
        for (IFonds iFonds : this.koersen)
        {
            ((Fonds) iFonds).updateKoers();
        }
        
        try
        {
            this.inform("koersen", null, this.getKoersen());
        }
        catch (RemoteException ex)
        {
            Logger.getLogger(MockEffectenbeurs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void registerProperty(String property) throws RemoteException
    {
        publisher.registerProperty(property);
    }

    @Override
    public void unregisterProperty(String property) throws RemoteException
    {
        publisher.unregisterProperty(property);
    }

    @Override
    public void subscribeRemoteListener(IRemotePropertyListener listener, String property) throws RemoteException
    {
        this.publisher.subscribeRemoteListener(listener, property);
    }

    @Override
    public void unsubscribeRemoteListener(IRemotePropertyListener listener, String property) throws RemoteException
    {
        this.publisher.unsubscribeRemoteListener(listener, property);
    }

    @Override
    public void inform(String property, Object oldValue, Object newValue) throws RemoteException
    {
        this.publisher.inform(property, oldValue, newValue);
    }

    @Override
    public List<String> getProperties() throws RemoteException
    {
        return this.publisher.getProperties();
    }
}
