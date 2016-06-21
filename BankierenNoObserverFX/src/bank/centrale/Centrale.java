/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.centrale;

import bank.bankieren.IBank;
import bank.bankieren.Money;
import fontys.util.NumberDoesntExistException;
import java.io.FileOutputStream;
import java.io.IOException;
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
	public boolean transfer(IBank bank, int rekeningNummer, int tegenrekeningNummer, Money bedrag) throws RemoteException
	{
		if (bank == null)
		{
			throw new IllegalArgumentException("Bank can not be null.");
		}

		IBankTbvCentrale rekeningBank = (IBankTbvCentrale) bank;

		IBankTbvCentrale tegenRekeningBank = (IBankTbvCentrale) this.findBank(tegenrekeningNummer);

		Money negative = Money.difference(new Money(0, bedrag.getCurrency()),
				bedrag);
		boolean success = false;
		try
		{
			success = rekeningBank.muteer(rekeningNummer, negative);
		}
		catch (NumberDoesntExistException ex)
		{
			Logger.getLogger(Centrale.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		if (!success)
		{
			return false;
		}

		try
		{
			success = tegenRekeningBank.muteer(tegenrekeningNummer, bedrag);
		}
		catch (NumberDoesntExistException ex)
		{
			Logger.getLogger(Centrale.class.getName()).log(Level.SEVERE, null, ex);
		}

		if (!success) // rollback
		{
			try
			{
				rekeningBank.muteer(rekeningNummer, bedrag);
			}
			catch (NumberDoesntExistException ex)
			{
				Logger.getLogger(Centrale.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return success;
	}

	/**
	 * Finds the bank which has a rekening with the given nummer in the list of
	 * registered banks.
	 *
	 * @param rekeningNummer The nummer to find.
	 */
	private IBank findBank(int rekeningNummer) throws RemoteException
	{
		for (IBank bank : this.banks)
		{
			if (bank.getRekening(rekeningNummer) != null)
			{
				return bank;
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
