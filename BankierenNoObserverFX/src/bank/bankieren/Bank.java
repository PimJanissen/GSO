package bank.bankieren;

import fontys.util.*;
import java.rmi.RemoteException;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author J.H.L.M. Janssen
 * @author P.Janissen
 */
public class Bank implements IBank {

    /**
     *
     */
    private static final long serialVersionUID = -8728841131739353765L;

    private Map<Integer, IRekeningTbvBank> accounts;
    private Collection<IKlant> clients;
    private int nieuwReknr;
    private String name;
    private Lock rekeningLock;
    private Lock overboekenLock;

    public Bank(String name) {
        accounts = new HashMap<Integer, IRekeningTbvBank>();
        clients = new ArrayList<IKlant>();
        nieuwReknr = 100000000;
        this.name = name;
    }

    public int openRekening(String name, String city) {
        if (name == null || city == null) {
            throw new IllegalArgumentException();
        }

        if (name.equals("") || city.equals("")) {
            return -1;
        }

        rekeningLock.lock();
        IKlant klant = getKlant(name, city);
        IRekeningTbvBank account = null;
        try {
            account = new Rekening(nieuwReknr, klant, Money.EURO);
        } catch (RemoteException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            rekeningLock.unlock();
        }
        accounts.put(nieuwReknr, account);
        nieuwReknr++;
        rekeningLock.unlock();
        return nieuwReknr - 1;
    }

    private IKlant getKlant(String name, String city) {
        for (IKlant k : clients) {
            if (k.getNaam().equals(name) && k.getPlaats().equals(city)) {
                return k;
            }
        }
        IKlant klant = new Klant(name, city);
        clients.add(klant);
        return klant;
    }

    public IRekening getRekening(int nr) {
        return accounts.get(nr);
    }

    public boolean maakOver(int source, int destination, Money money)
            throws NumberDoesntExistException {

        if (money == null) {
            throw new IllegalArgumentException();
        }

        if (source == destination) {
            throw new IllegalArgumentException(
                    "cannot transfer money to your own account");
        }
        if (!money.isPositive()) {
            throw new IllegalArgumentException("money must be positive");
        }

        IRekeningTbvBank source_account = (IRekeningTbvBank) getRekening(source);
        if (source_account == null) {
            throw new NumberDoesntExistException("account " + source
                    + " unknown at " + name);
        }

        overboekenLock.lock();
        Money negative = Money.difference(new Money(0, money.getCurrency()),
                money);
        boolean success = source_account.muteer(negative);
        if (!success) {
            overboekenLock.unlock();
            return false;
        }

        IRekeningTbvBank dest_account = (IRekeningTbvBank) getRekening(destination);
        try {
            if (dest_account == null) {
                throw new NumberDoesntExistException("account " + destination
                        + " unknown at " + name);
            }
        } finally {
            overboekenLock.unlock();
        }
        success = dest_account.muteer(money);

        if (!success) // rollback
        {
            source_account.muteer(money);
        }
        overboekenLock.unlock();
        return success;
    }

    @Override
    public String getName() {
        return name;
    }
}
