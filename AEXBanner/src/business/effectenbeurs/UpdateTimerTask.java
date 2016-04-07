/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.effectenbeurs;

import business.bannercontroller.BannerController;
import java.util.TimerTask;

/**
 *
 * @author gebruiker-pc
 */
public class UpdateTimerTask extends TimerTask
{

    private BannerController bannerController;
    private IEffectenbeurs iEffectenbeurs;

    public UpdateTimerTask(BannerController bannerController, IEffectenbeurs effectenbeurs)
    {
        if (bannerController == null)
        {
            throw new IllegalArgumentException("bannerController can not be null.");
        }
        if (effectenbeurs == null)
        {
            throw new IllegalArgumentException("effectenbeurs can not be null.");
        }

        this.bannerController = bannerController;
        this.iEffectenbeurs = effectenbeurs;
    }

    @Override

    public void run()
    {
        MockEffectenbeurs mockEffectenBeurs = (MockEffectenbeurs)this.iEffectenbeurs;

        mockEffectenBeurs.addObserver(this.bannerController);
        
        mockEffectenBeurs.updateKoersen();
    }
}
