/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.centrale;

import bank.bankieren.IBank;
import bank.bankieren.Money;
import fontys.util.NumberDoesntExistException;
import java.rmi.RemoteException;

/**
 *
 * @author gebruiker-pc
 */
public interface IBankTbvCentrale extends IBank
{

	/**
	 * Past het saldo op de rekening met het gegeven nummer aan met het
	 * meegegeven bedrag.
	 *
	 * @param rekeningNummer het rekeningnummer van de rekening om aan te
	 * passen.
	 * @param bedrag Het bedrag om de rekening mee te muteren.
	 * @return een boolean die aangeeft of de mutatie succesvol was.
	 * @throws fontys.util.NumberDoesntExistException Wanneer de bank geen
	 * rekening heeft met het gegeven nummer.
	 * @throws java.rmi.RemoteException Wanneer een onverwachte RMI gerelateerde
	 * fout optreed.
	 */
	boolean muteer(int rekeningNummer, Money bedrag) throws NumberDoesntExistException, RemoteException;
}
