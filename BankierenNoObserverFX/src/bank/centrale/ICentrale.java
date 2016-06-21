/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.centrale;

import bank.bankieren.IBank;
import bank.bankieren.IRekeningTbvBank;
import bank.bankieren.Money;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface used by a bank to communicate with the central.
 */
public interface ICentrale extends Remote
{

	/**
	 * Registers a bank to this central.
	 *
	 * @param bank The bank to register.
	 * @return false if a bank with the same name already exists, else true.
	 * @throws java.rmi.RemoteException when an unexpected RMI related error
	 * occurs.
	 */
	public boolean registerBank(IBank bank) throws RemoteException;

	/**
	 * Unregisters a given bank from the central. If the bank is not known to
	 * the central, nothing happens.
	 *
	 * @param bank The bank to unregister.
	 * @throws java.rmi.RemoteException when an unexpected RMI related error
	 * occurs.
	 */
	public void unregisterBank(IBank bank) throws RemoteException;

	/**
	 * Transfers the given amount from the given rekening to a rekening with the
	 * given rekeningnummer.
	 *
	 * @param bank The bank which the rekening belongs to.
	 * @param rekening The rekening to withdraw the amount from.
	 * @param tegenrekeningNummer The number of the rekening to transfer the
	 * amount to.
	 * @param bedrag The amount to transfer.
	 * @return false when the transfer failed, due to either the
	 * tegenrekeningNummer not being known with any of the registered banks or
	 * the rekening not having sufficient funds to cover for the transfer.
	 * @throws java.rmi.RemoteException when an unexpected RMI related error
	 * occurs.
	 */
	public boolean transfer(IBank bank, IRekeningTbvBank rekening, int tegenrekeningNummer, Money bedrag) throws RemoteException;

	/**
	 * Gets a new rekeningnummer from this central which is unique to all the
	 * registered banks.
	 *
	 * @return the rekeningnummer.
	 * @throws java.rmi.RemoteException when an unexpected RMI related error
	 * occurs.
	 */
	public int getUniqueRekeningNummer() throws RemoteException;
}
