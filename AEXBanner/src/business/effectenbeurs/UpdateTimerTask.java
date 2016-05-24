/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.effectenbeurs;

import java.util.TimerTask;

/**
 *
 * @author gebruiker-pc
 */
public class UpdateTimerTask extends TimerTask
{

    private MockEffectenbeurs mockEffectenbeurs;

    public UpdateTimerTask(MockEffectenbeurs mockEffectenbeurs)
    {
        if (mockEffectenbeurs == null)
        {
            throw new IllegalArgumentException("effectenbeurs can not be null.");
        }

        this.mockEffectenbeurs = mockEffectenbeurs;
    }

    @Override

    public void run()
    {
        this.mockEffectenbeurs.updateKoersen();
    }
}
