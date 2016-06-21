package bank.bankieren;

import bank.centrale.IBankTbvCentrale;
import bank.centrale.ICentrale;
import fontys.util.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author J.H.L.M. Janssen
 * @author P.Janissen
 */
public class Bank extends UnicastRemoteObject implements IBankTbvCentrale, Serializable
{

	/**
	 *
	 */
	private static final long serialVersionUID = -8728841131739353765L;

	private final Map<Integer, IRekeningTbvBank> accounts;
	private final Collection<IKlant> clients;
	private final String name;

	private final ICentrale centrale;

	public Bank(String name, ICentrale centrale) throws RemoteException
	{
		accounts = new HashMap<Integer, IRekeningTbvBank>();
		clients = new ArrayList<IKlant>();
		this.name = name;

		this.centrale = centrale;
	}

	public int openRekening(String name, String city)
	{
		if (name == null || city == null)
		{
			throw new IllegalArgumentException();
		}

		if (name.equals("") || city.equals(""))
		{
			return -1;
		}

		synchronized (this)
		{
			IKlant klant = getKlant(name, city);
			IRekeningTbvBank account = null;
			int nieuwReknr = -1;
			try
			{
				nieuwReknr = this.centrale.getUniqueRekeningNummer();
				account = new Rekening(nieuwReknr, klant, Money.EURO);
			}
			catch (RemoteException ex)
			{
				Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
			}
			accounts.put(nieuwReknr, account);

			return nieuwReknr;
		}
	}

	private IKlant getKlant(String name, String city)
	{
		for (IKlant k : clients)
		{
			if (k.getNaam().equals(name) && k.getPlaats().equals(city))
			{
				return k;
			}
		}
		IKlant klant = new Klant(name, city);
		clients.add(klant);
		return klant;
	}

	public IRekening getRekening(int nr)
	{
		return accounts.get(nr);
	}

	public boolean maakOver(int source, int destination, Money money)
			throws NumberDoesntExistException
	{

		if (money == null)
		{
			throw new IllegalArgumentException();
		}

		if (source == destination)
		{
			throw new IllegalArgumentException(
					"cannot transfer money to your own account");
		}
		if (!money.isPositive())
		{
			throw new IllegalArgumentException("money must be positive");
		}

		IRekeningTbvBank source_account = (IRekeningTbvBank) getRekening(source);
		if (source_account == null)
		{
			throw new NumberDoesntExistException("account " + source
					+ " unknown at " + name);
		}

		IRekening iRekening = this.getRekening(destination);

		if (iRekening == null)
		{
			try
			{
				return this.centrale.transfer(this, source, destination, money);
			}
			catch (RemoteException ex)
			{
				Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		Money negative = Money.difference(new Money(0, money.getCurrency()),
				money);
		boolean success = source_account.muteer(negative);
		if (!success)
		{
			return false;
		}

		IRekeningTbvBank dest_account = (IRekeningTbvBank) iRekening;

		success = dest_account.muteer(money);

		if (!success) // rollback
		{
			source_account.muteer(money);
		}
		return success;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public boolean muteer(int rekeningNummer, Money bedrag) throws NumberDoesntExistException, RemoteException
	{
		IRekeningTbvBank rekening = (IRekeningTbvBank) this.getRekening(rekeningNummer);
		return rekening.muteer(bedrag);
	}
}
