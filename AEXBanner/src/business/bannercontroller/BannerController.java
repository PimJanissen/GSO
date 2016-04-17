/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.bannercontroller;

import aexbanner.AEXBanner;
import business.effectenbeurs.IEffectenbeurs;
import business.effectenbeurs.MockEffectenbeurs;
import business.effectenbeurs.fonds.Fonds;
import business.effectenbeurs.fonds.IFonds;
import fontyspublisher.IRemotePropertyListener;
import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author gebruiker-pc
 */
public class BannerController
{

    private final AEXBanner banner;
    private final IEffectenbeurs effectenbeurs;

    public BannerController(AEXBanner banner) throws RemoteException
    {
        this.banner = banner;
        this.effectenbeurs = this.banner.iEffectenbeurs;
        this.effectenbeurs.registerProperty("koersen");
        this.effectenbeurs.subscribeRemoteListener(new KoersenListener(this), "koersen");
    }

    protected void updateBanner()
    {
        MockEffectenbeurs mockEffectenbeurs = (MockEffectenbeurs) this.effectenbeurs;
        this.banner.setKoersen(this.genereerKoersenTekst(mockEffectenbeurs.getKoersen()));
    }

    private String genereerKoersenTekst(List<IFonds> fondsen)
    {
        String koersenTekst = "<--";

        for (IFonds iFonds : fondsen)
        {
            Fonds fonds = (Fonds) iFonds;

            String koers = Double.toString((double) Math.round(fonds.getKoers() * 1000) / 1000);
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

    private class KoersenListener implements IRemotePropertyListener
    {

        private final BannerController bannerController;

        public KoersenListener(BannerController bannerController)
        {
            this.bannerController = bannerController;
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            this.bannerController.updateBanner();
        }
    }

}
