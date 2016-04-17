/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.effectenbeurs.fonds;

import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author gebruiker-pc
 */
public class Fonds implements IFonds, Serializable
{

    private final String naam;
    private double koers;
    private final Random random;

    public Fonds(String naam, double startKoers)
    {
        this.random = new Random();
        
        this.naam = naam;
        this.koers = startKoers;
    }

    @Override
    public String getNaam()
    {
        return this.naam;
    }

    @Override
    public double getKoers()
    {
        return this.koers;
    }
    
    public void updateKoers()
    {
        boolean changed = this.random.nextBoolean();
        
        if (changed)
        {
            boolean drop = this.random.nextBoolean();
            
            int change = this.random.nextInt(4) + 1;
            
            if (drop)
            {
                change *= -1;
            }
            
            double multiplier = 1 + ((float)change / 100);
            
            this.koers *= multiplier;
        }
    }

}
