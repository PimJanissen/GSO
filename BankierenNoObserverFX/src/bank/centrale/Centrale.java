/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.centrale;

import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import bank.bankieren.IRekeningTbvBank;
import bank.bankieren.Money;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gebruiker-pc
 */
public class Centrale extends UnicastRemoteObject implements ICentrale
{

	public static final int PORT = 1099;
	
	private int newRekeningNummer = 1000000;

	private final List<IBank> banks;

	public Centrale() throws RemoteException
	{
		this.startCentrale();
		this.banks = new ArrayList();
	}

	@Override
	public boolean registerBank(IBank bank)
	{
		if (bank == null)
		{
			throw new IllegalArgumentException("Bank can not be null");
		}

		for (IBank registedBank : this.banks)
		{
			try
			{
				if (registedBank.getName().equals(bank.getName()))
				{
					return false;
				}
			}
			catch (RemoteException ex)
			{
				Logger.getLogger(Centrale.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		this.banks.add(bank);
		return false;
	}

	@Override
	public void unregisterBank(IBank bank)
	{
		if (bank == null)
		{
			throw new IllegalArgumentException("Bank can not be null");
		}

		this.banks.remove(bank);
	}

	@Override
	public boolean transfer(IBank bank, IRekeningTbvBank rekening, int tegenrekeningNummer, Money bedrag)
	{
		if (bank == null)
		{
			throw new IllegalArgumentException("Bank can not be null.");
		}
		if (rekening == null)
		{
			throw new IllegalArgumentException("Bank can not be null");
		}
		if (rekening.getNr() == tegenrekeningNummer)
		{

		}
		IRekeningTbvBank tegenRekening = (IRekeningTbvBank) this.findRekening(tegenrekeningNummer, bank);

		Money negative = Money.difference(new Money(0, bedrag.getCurrency()),
				bedrag);
		boolean success = rekening.muteer(negative);
		if (!success)
		{
			return false;
		}

		success = tegenRekening.muteer(bedrag);

		if (!success) // rollback
		{
			rekening.muteer(bedrag);
		}
		return success;
	}

	/**
	 * Finds a rekenig with the given nummer in the list of registered banks.
	 *
	 * @param rekeningNummer The nummer to find.
	 * @param excludedBank The bank to exclude from the search. Can be null.
	 * @return The found rekening.
	 */
	private IRekening findRekening(int rekeningNummer, IBank excludedBank)
	{
		IRekening rekening = null;
		for (IBank bank : this.banks)
		{
			if (bank != excludedBank)
			{
				try
				{
					rekening = bank.getRekening(rekeningNummer);
				}
				catch (RemoteException ex)
				{
					Logger.getLogger(Centrale.class.getName()).log(Level.SEVERE, null, ex);
				}

				if (rekening != null)
				{
					return rekening;
				}
			}
		}

		return null;
	}

	@Override
	public int getUniqueRekeningNummer()
	{
		int returnValue = this.newRekeningNummer;
		this.newRekeningNummer++;

		return returnValue;
	}

	private void startCentrale()
	{
		String bindingName = "Centrale";
		try (FileOutputStream out = new FileOutputStream(bindingName + ".props"))
		{

			String address = java.net.InetAddress.getLocalHost().getHostAddress();
			Properties props = new Properties();
			String rmiCentrale = address + ":" + Centrale.PORT + "/" + bindingName;
			props.setProperty(bindingName, rmiCentrale);

			props.store(out, null);
			
			Registry registry = LocateRegistry.createRegistry(Centrale.PORT);
			registry.bind(bindingName, this);
		}
		catch (AlreadyBoundException | IOException ex)
		{
			Logger.getLogger(Centrale.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
