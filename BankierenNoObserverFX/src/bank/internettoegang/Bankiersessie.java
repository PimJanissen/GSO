package bank.internettoegang;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import bank.bankieren.Money;

import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;
import fontyspublisher.Publisher;
import java.util.Observer;
import bank.bankieren.Rekening;
import fontyspublisher.IRemotePropertyListener;
import java.util.Observable;
import util.PropertyNames;

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
		
		this.publisher = new Publisher();
		this.publisher.registerProperty(PropertyNames.SALDO);
		
		new BankiersessiePropertyListener();
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
	
	@Override
	public void subscribeRemoteListener(IRemotePropertyListener listener, String property) throws RemoteException
	{
		this.publisher.subscribeRemoteListener(listener, property);
	}
	
	@Override
	public void unsubscribeRemoteListener(IRemotePropertyListener listener, String property) throws RemoteException
	{
		this.publisher.unsubscribeRemoteListener(listener, property);
	}
	
	private class BankiersessiePropertyListener implements Observer
	{
		
		public BankiersessiePropertyListener() throws RemoteException
		{
			Bankiersessie sessie = Bankiersessie.this;
			
			IRekening rekening = sessie.bank.getRekening(sessie.reknr);
			((Rekening) rekening).addObserver(this);
		}
		
		@Override
		public void update(Observable o, Object arg)
		{
			Bankiersessie.this.publisher.inform(PropertyNames.SALDO, null, arg);
		}
	}
}
