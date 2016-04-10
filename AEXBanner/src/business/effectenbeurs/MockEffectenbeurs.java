/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.effectenbeurs;

import business.effectenbeurs.fonds.Fonds;
import business.effectenbeurs.fonds.IFonds;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import javafx.beans.InvalidationListener;

/**
 *
 * @author gebruiker-pc
 */
public class MockEffectenbeurs extends Observable implements IEffectenbeurs
{

    private final ArrayList<IFonds> koersen;

    @Override
    public List<IFonds> getKoersen()
    {
        return Collections.unmodifiableList(this.koersen);
    }

    public MockEffectenbeurs()
    {
        this.koersen = this.fillKoersen();
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
    
    public void updateKoersen()
    {
        for (IFonds iFonds : this.koersen)
        {
            ((Fonds)iFonds).updateKoers();
        }
        
        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public void addListener(InvalidationListener listener)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeListener(InvalidationListener listener)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
