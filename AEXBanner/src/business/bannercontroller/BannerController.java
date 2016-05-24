/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.bannercontroller;

import aexbanner.AEXBanner;
import business.effectenbeurs.IEffectenbeurs;
import business.effectenbeurs.fonds.Fonds;
import business.effectenbeurs.fonds.IFonds;
import fontyspublisher.IRemotePropertyListener;
import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        this.effectenbeurs.subscribeRemoteListener(new KoersenListener(this), "koersen");
    }

    protected void updateBanner()
    {
        try
        {
            this.banner.setKoersen(this.genereerKoersenTekst(this.effectenbeurs.getKoersen()));
        }
        catch (RemoteException ex)
        {
            Logger.getLogger(BannerController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private class KoersenListener extends UnicastRemoteObject implements IRemotePropertyListener
    {
        private final BannerController bannerController;

        public KoersenListener(BannerController bannerController) throws RemoteException
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
