/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.effectenbeurs;

import aexbanner.AEXBanner;
import java.util.TimerTask;

/**
 *
 * @author gebruiker-pc
 */
public class UpdateTimerTask extends TimerTask
{

    private IEffectenbeurs iEffectenbeurs;

    public UpdateTimerTask(IEffectenbeurs effectenbeurs)
    {
        if (effectenbeurs == null)
        {
            throw new IllegalArgumentException("effectenbeurs can not be null.");
        }

        this.iEffectenbeurs = effectenbeurs;
    }

    @Override

    public void run()
    {
        MockEffectenbeurs mockEffectenbeurs = (MockEffectenbeurs) this.iEffectenbeurs;
        mockEffectenbeurs.updateKoersen();
    }
}
