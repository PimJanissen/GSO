/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.bannercontroller;

import aexbanner.AEXBanner;
import business.effectenbeurs.IEffectenbeurs;
import business.effectenbeurs.MockEffectenbeurs;
import java.util.Timer;

/**
 *
 * @author gebruiker-pc
 */
public class BannerController
{

    private AEXBanner banner;
    private IEffectenbeurs effectenbeurs;
    private Timer pollingTimer;

    public BannerController(AEXBanner banner)
    {

        this.banner = banner;
        this.effectenbeurs = new MockEffectenbeurs();

        // Start polling timer: update banner every two seconds
        pollingTimer = new Timer();
        // TODO
    }

    // Stop banner controller
    public void stop()
    {
        pollingTimer.cancel();
        // Stop simulation timer of effectenbeurs
        // TODO
    }

}
