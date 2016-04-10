/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.effectenbeurs.fonds;

import java.io.Serializable;

/**
 *
 * @author gebruiker-pc
 */
public interface IFonds extends Serializable
{
    String getNaam();
    double getKoers();
}
