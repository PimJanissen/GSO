/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.effectenbeurs;

import business.effectenbeurs.fonds.IFonds;
import java.util.List;

/**
 *
 * @author gebruiker-pc
 */
public interface IEffectenbeurs
{

    List<IFonds> getKoersen();
}
