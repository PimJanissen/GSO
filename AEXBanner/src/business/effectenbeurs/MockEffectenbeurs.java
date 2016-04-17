/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.effectenbeurs;

import business.effectenbeurs.fonds.Fonds;
import business.effectenbeurs.fonds.IFonds;
import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.RemotePublisher;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

/**
 *
 * @author gebruiker-pc
 * @author Jeroen Janssen
 */
public class MockEffectenbeurs implements IEffectenbeurs, Serializable
{

    private final transient RemotePublisher remotePublisher;
    private final ArrayList<IFonds> koersen;
    private final transient Timer updateTimer;

    @Override
    public List<IFonds> getKoersen()
    {
        return Collections.unmodifiableList(this.koersen);
    }

    public MockEffectenbeurs() throws RemoteException
    {
        this.koersen = this.fillKoersen();

        this.updateTimer = new Timer();
        this.updateTimer.schedule(new UpdateTimerTask(this), 0, 2000);

        this.remotePublisher = new RemotePublisher();
    }

    // Stop banner controller
    public void stop()
    {
        this.updateTimer.cancel();
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
    }

    /**
     * Register property. Register property at this publisher. From now on
     * listeners can subscribe to this property. Nothing changes in case given
     * property was already registered.
     *
     * @param property empty string not allowed
     * @throws java.rmi.RemoteException
     */
    @Override
    public void registerProperty(String property) throws RemoteException
    {
        this.remotePublisher.registerProperty(property);
    }

    /**
     * Unregister property. Unregister property at this publisher. From now on
     * listeners subscribed to this property will not be informed on changes. In
     * case given property is null-String, all properties (except null) will be
     * unregistered.
     *
     * @param property registered property at this publisher
     * @throws java.rmi.RemoteException
     */
    @Override
    public void unregisterProperty(String property) throws RemoteException
    {
        this.remotePublisher.unregisterProperty(property);
    }

    @Override
    public void inform(String property, Object oldValue, Object newValue) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getProperties() throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void subscribeRemoteListener(IRemotePropertyListener listener, String property) throws RemoteException
    {
        this.remotePublisher.subscribeRemoteListener(listener, property);
    }

    @Override
    public void unsubscribeRemoteListener(IRemotePropertyListener listener, String property) throws RemoteException
    {
        this.remotePublisher.subscribeRemoteListener(listener, property);
    }
}
