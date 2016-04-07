/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.bannercontroller;

import aexbanner.AEXBanner;
import business.effectenbeurs.IEffectenbeurs;
import business.effectenbeurs.MockEffectenbeurs;
import business.effectenbeurs.UpdateTimerTask;
import business.effectenbeurs.fonds.Fonds;
import business.effectenbeurs.fonds.IFonds;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;

/**
 *
 * @author gebruiker-pc
 */
public class BannerController implements Observer
{

    private final AEXBanner banner;
    private final IEffectenbeurs effectenbeurs;
    private final Timer pollingTimer;

    public BannerController(AEXBanner banner)
    {

        this.banner = banner;
        this.effectenbeurs = new MockEffectenbeurs();

        // Start polling timer: update banner every two seconds
        pollingTimer = new Timer();
        pollingTimer.schedule(new UpdateTimerTask(this, this.effectenbeurs), 0, 2000);

    }

    // Stop banner controller
    public void stop()
    {
        pollingTimer.cancel();
        // Stop simulation timer of effectenbeurs
        // TODO
    }

    @Override
    public void update(Observable o, Object arg)
    {
        banner.setKoersen(this.genereerKoersenTekst(this.effectenbeurs.getKoersen()));
    }

    private String genereerKoersenTekst(List<IFonds> fondsen)
    {
        String koersenTekst = "<--";

        for (IFonds iFonds : fondsen)
        {
            Fonds fonds = (Fonds) iFonds;

            String koers = Double.toString((double)Math.round(fonds.getKoers() * 1000) / 1000);
            int puntIndex = koers.indexOf(".");
            
            while (koers.substring(puntIndex + 1).length() != 3)
            {
                koers += "0";
            }   
            
            koersenTekst += String.format(" %s %s ", fonds.getNaam(), koers);

            if (fondsen.indexOf(iFonds) != fondsen.size() - 1)
            {
                koersenTekst += "---";
            }
        }

        return koersenTekst + "-->";
    }

}
