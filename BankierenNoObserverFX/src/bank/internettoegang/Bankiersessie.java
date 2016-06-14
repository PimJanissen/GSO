package bank.internettoegang;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import bank.bankieren.Money;

import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;
import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.Publisher;
import java.beans.PropertyChangeEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bankiersessie extends UnicastRemoteObject implements
		IBankiersessie
{

	private static final long serialVersionUID = 1L;
	private long laatsteAanroep;
	private int reknr;
	private IBank bank;
	private Publisher publisher;

	public Bankiersessie(int reknr, IBank bank) throws RemoteException
	{
		laatsteAanroep = System.currentTimeMillis();
		this.reknr = reknr;
		this.bank = bank;
		
		BankiersessiePropertyListener bankierSessiePropertyListener = new BankiersessiePropertyListener();
	}

	public boolean isGeldig()
	{
		return System.currentTimeMillis() - laatsteAanroep < GELDIGHEIDSDUUR;
	}

	@Override
	public boolean maakOver(int bestemming, Money bedrag)
			throws NumberDoesntExistException, InvalidSessionException,
			RemoteException
	{

		if (bedrag == null)
		{
			throw new IllegalArgumentException();
		}

		updateLaatsteAanroep();

		if (reknr == bestemming)
		{
			throw new IllegalArgumentException(
					"source and destination must be different");
		}
		if (!bedrag.isPositive())
		{
			throw new IllegalArgumentException("amount must be positive");
		}

		return bank.maakOver(reknr, bestemming, bedrag);
	}

	private void updateLaatsteAanroep() throws InvalidSessionException
	{
		if (!isGeldig())
		{
			throw new InvalidSessionException("session has been expired");
		}

		laatsteAanroep = System.currentTimeMillis();
	}

	@Override
	public IRekening getRekening() throws InvalidSessionException,
			RemoteException
	{

		updateLaatsteAanroep();

		return bank.getRekening(reknr);
	}

	@Override
	public void logUit() throws RemoteException
	{
		UnicastRemoteObject.unexportObject(this, true);
	}

	private class BankiersessiePropertyListener extends UnicastRemoteObject implements IRemotePropertyListener
	{

		public BankiersessiePropertyListener() throws RemoteException
		{
			Bankiersessie sessie = Bankiersessie.this;

			IRekening rekening = sessie.bank.getRekening(sessie.reknr);
			
			try
			{
				rekening.subscribeRemoteListener(this, "Saldo");
			}
			catch (RemoteException ex)
			{
				Logger.getLogger(Bankiersessie.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) throws RemoteException
		{
			System.out.printf("Saldo geupdate naar %s.\n", evt.getNewValue());
			//Bankiersessie.this.publisher.inform("Saldo", evt.getOldValue(), evt.getNewValue());
		}
	}
}
