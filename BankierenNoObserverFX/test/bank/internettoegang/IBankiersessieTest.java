/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.internettoegang;

import bank.bankieren.Bank;
import bank.bankieren.IBank;
import bank.bankieren.Money;
import bank.centrale.Centrale;
import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Pim Janissen
 */
public class IBankiersessieTest
{

	private IBank iBank;
	private int rekeningnummer;
	private IBankiersessie iBankiersessie;

	@Before
	public void setUp() throws RemoteException
	{
		this.iBank = new Bank("Rabobank", new Centrale());
		this.rekeningnummer = this.iBank.openRekening("Erik Janssen", "Eindhoven");

		this.iBankiersessie = new Bankiersessie(this.rekeningnummer, this.iBank);
	}

	/**
	 * Test of isGeldig method, of class IBankiersessie.
	 *
	 * @throws java.rmi.RemoteException
	 * @throws fontys.util.InvalidSessionException
	 */
	@Test
	public void testIsGeldigGetRekeningAangeroepen() throws RemoteException, InvalidSessionException
	{
		/**
		 * @returns true als de laatste aanroep van getRekening of maakOver voor
		 * deze sessie minder dan GELDIGHEIDSDUUR geleden is en er geen
		 * communicatiestoornis in de tussentijd is opgetreden, anders false
		 */

		this.iBankiersessie.getRekening();

		Assert.assertTrue(this.iBankiersessie.isGeldig());
	}

	/**
	 * Test of isGeldig method, of class IBankiersessie.
	 *
	 * ZET HANDMATIG VOOR TESTDOELEINDEN DE GELDIGHEIDSDUUR IN IBankiersessie
	 * NAAR bijv. 1000;
	 *
	 * @throws java.rmi.RemoteException
	 * @throws fontys.util.InvalidSessionException
	 */
	@Test
	public void testIsGeldigGetRekeningAangeroepenTijdVerstreken() throws RemoteException, InvalidSessionException
	{
		/**
		 * @returns true als de laatste aanroep van getRekening of maakOver voor
		 * deze sessie minder dan GELDIGHEIDSDUUR geleden is en er geen
		 * communicatiestoornis in de tussentijd is opgetreden, anders false
		 */

		this.iBankiersessie.getRekening();

		try
		{
			Thread.sleep(IBankiersessie.GELDIGHEIDSDUUR + 1l);
		}
		catch (InterruptedException ex)
		{
			Logger.getLogger(IBankiersessieTest.class.getName()).log(Level.SEVERE, null, ex);
		}

		Assert.assertFalse(this.iBankiersessie.isGeldig());
	}

	/**
	 * Test of isGeldig method, of class IBankiersessie.
	 *
	 * @throws java.rmi.RemoteException
	 * @throws fontys.util.InvalidSessionException
	 */
	@Test
	public void testIsGeldigMaakOverAangeroepen() throws RemoteException, InvalidSessionException
	{
		/**
		 * @returns true als de laatste aanroep van getRekening of maakOver voor
		 * deze sessie minder dan GELDIGHEIDSDUUR geleden is en er geen
		 * communicatiestoornis in de tussentijd is opgetreden, anders false
		 */

		try
		{
			this.iBankiersessie.maakOver(this.iBank.openRekening("Pieter Peeters", "Nuenen"), new Money(5020, Money.EURO));
		}
		catch (NumberDoesntExistException ex)
		{
			Logger.getLogger(IBankiersessieTest.class.getName()).log(Level.SEVERE, null, ex);
		}

		Assert.assertTrue(this.iBankiersessie.isGeldig());
	}

	/**
	 * Test of isGeldig method, of class IBankiersessie.
	 *
	 * ZET HANDMATIG VOOR TESTDOELEINDEN DE GELDIGHEIDSDUUR IN IBankiersessie
	 * NAAR bijv. 1000;
	 *
	 * @throws java.rmi.RemoteException
	 * @throws fontys.util.InvalidSessionException
	 */
	@Test
	public void testIsGeldigMaakOverAangeroepenTijdVerstreken() throws RemoteException, InvalidSessionException
	{
		/**
		 * @returns true als de laatste aanroep van getRekening of maakOver voor
		 * deze sessie minder dan GELDIGHEIDSDUUR geleden is en er geen
		 * communicatiestoornis in de tussentijd is opgetreden, anders false
		 */

		try
		{
			this.iBankiersessie.maakOver(this.iBank.openRekening("Pieter Peeters", "Nuenen"), new Money(5020, Money.EURO));
		}
		catch (NumberDoesntExistException ex)
		{
			Logger.getLogger(IBankiersessieTest.class.getName()).log(Level.SEVERE, null, ex);
		}

		try
		{
			Thread.sleep(IBankiersessie.GELDIGHEIDSDUUR + 1l);
		}
		catch (InterruptedException ex)
		{
			Logger.getLogger(IBankiersessieTest.class.getName()).log(Level.SEVERE, null, ex);
		}

		Assert.assertFalse(this.iBankiersessie.isGeldig());
	}

	/**
	 * Test of maakOver method, of class IBankiersessie.
	 *
	 * @throws java.rmi.RemoteException
	 * @throws fontys.util.NumberDoesntExistException
	 * @throws fontys.util.InvalidSessionException
	 */
	@Test
	public void testMaakOverValideInput() throws RemoteException, NumberDoesntExistException, InvalidSessionException
	{
		/**
		 * er wordt bedrag overgemaakt van de bankrekening met het nummer bron
		 * naar de bankrekening met nummer bestemming
		 *
		 * @param bestemming is ongelijk aan rekeningnummer van deze
		 * bankiersessie
		 * @param bedrag is groter dan 0
		 * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
		 * @throws NumberDoesntExistException als bestemming onbekend is
		 * @throws InvalidSessionException als sessie niet meer geldig is
		 */

		int bestemming = this.iBank.openRekening("Pieter Peeters", "Nuenen");
		Money bedrag = new Money(5020, Money.EURO);

		Assert.assertTrue(this.iBankiersessie.maakOver(bestemming, bedrag));
	}

	/**
	 * Test of maakOver method, of class IBankiersessie.
	 *
	 * @throws java.rmi.RemoteException
	 * @throws fontys.util.NumberDoesntExistException
	 * @throws fontys.util.InvalidSessionException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testMaakOverBestemmingGelijkAanRekeningnummer() throws RemoteException, NumberDoesntExistException, InvalidSessionException
	{
		/**
		 * er wordt bedrag overgemaakt van de bankrekening met het nummer bron
		 * naar de bankrekening met nummer bestemming
		 *
		 * @param bestemming is ongelijk aan rekeningnummer van deze
		 * bankiersessie
		 * @param bedrag is groter dan 0
		 * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
		 * @throws NumberDoesntExistException als bestemming onbekend is
		 * @throws InvalidSessionException als sessie niet meer geldig is
		 */

		int bestemming = this.iBankiersessie.getRekening().getNr();
		Money bedrag = new Money(5020, Money.EURO);

		this.iBankiersessie.maakOver(bestemming, bedrag);
	}

	/**
	 * Test of maakOver method, of class IBankiersessie.
	 *
	 * @throws java.rmi.RemoteException
	 * @throws fontys.util.NumberDoesntExistException
	 * @throws fontys.util.InvalidSessionException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testMaakOverBedragNul() throws RemoteException, NumberDoesntExistException, InvalidSessionException
	{
		/**
		 * er wordt bedrag overgemaakt van de bankrekening met het nummer bron
		 * naar de bankrekening met nummer bestemming
		 *
		 * @param bestemming is ongelijk aan rekeningnummer van deze
		 * bankiersessie
		 * @param bedrag is groter dan 0
		 * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
		 * @throws NumberDoesntExistException als bestemming onbekend is
		 * @throws InvalidSessionException als sessie niet meer geldig is
		 */

		int bestemming = this.iBank.openRekening("Pieter Peeters", "Nuenen");
		Money bedrag = new Money(0, Money.EURO);

		this.iBankiersessie.maakOver(bestemming, bedrag);
	}

	/**
	 * Test of maakOver method, of class IBankiersessie.
	 *
	 * @throws java.rmi.RemoteException
	 * @throws fontys.util.NumberDoesntExistException
	 * @throws fontys.util.InvalidSessionException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testMaakOverBedragNegatief() throws RemoteException, NumberDoesntExistException, InvalidSessionException
	{
		/**
		 * er wordt bedrag overgemaakt van de bankrekening met het nummer bron
		 * naar de bankrekening met nummer bestemming
		 *
		 * @param bestemming is ongelijk aan rekeningnummer van deze
		 * bankiersessie
		 * @param bedrag is groter dan 0
		 * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
		 * @throws NumberDoesntExistException als bestemming onbekend is
		 * @throws InvalidSessionException als sessie niet meer geldig is
		 */

		int bestemming = this.iBank.openRekening("Pieter Peeters", "Nuenen");
		Money bedrag = new Money(-5020, Money.EURO);

		this.iBankiersessie.maakOver(bestemming, bedrag);
	}

	/**
	 * Test of maakOver method, of class IBankiersessie.
	 *
	 * @throws java.rmi.RemoteException
	 * @throws fontys.util.NumberDoesntExistException
	 * @throws fontys.util.InvalidSessionException
	 */
	@Test(expected = NumberDoesntExistException.class)
	public void testMaakOverRekeningnummerBestaatNiet() throws RemoteException, NumberDoesntExistException, InvalidSessionException
	{
		/**
		 * er wordt bedrag overgemaakt van de bankrekening met het nummer bron
		 * naar de bankrekening met nummer bestemming
		 *
		 * @param bestemming is ongelijk aan rekeningnummer van deze
		 * bankiersessie
		 * @param bedrag is groter dan 0
		 * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
		 * @throws NumberDoesntExistException als bestemming onbekend is
		 * @throws InvalidSessionException als sessie niet meer geldig is
		 */

		int bestemming = 12345678;
		Money bedrag = new Money(5020, Money.EURO);

		this.iBankiersessie.maakOver(bestemming, bedrag);
	}

	/**
	 * Test of maakOver method, of class IBankiersessie.
	 *
	 * @throws java.rmi.RemoteException
	 * @throws fontys.util.NumberDoesntExistException
	 * @throws fontys.util.InvalidSessionException
	 */
	@Test(expected = InvalidSessionException.class)
	public void testMaakOverSessieVerlopen() throws RemoteException, NumberDoesntExistException, InvalidSessionException
	{
		/**
		 * er wordt bedrag overgemaakt van de bankrekening met het nummer bron
		 * naar de bankrekening met nummer bestemming
		 *
		 * @param bestemming is ongelijk aan rekeningnummer van deze
		 * bankiersessie
		 * @param bedrag is groter dan 0
		 * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
		 * @throws NumberDoesntExistException als bestemming onbekend is
		 * @throws InvalidSessionException als sessie niet meer geldig is
		 */

		int bestemming = this.iBank.openRekening("Pieter Peeters", "Nuenen");
		Money bedrag = new Money(5020, Money.EURO);

		try
		{
			Thread.sleep(IBankiersessie.GELDIGHEIDSDUUR + 1l);
		}
		catch (InterruptedException ex)
		{
			Logger.getLogger(IBankiersessieTest.class.getName()).log(Level.SEVERE, null, ex);
		}

		this.iBankiersessie.maakOver(bestemming, bedrag);
	}

	/**
	 * Test of maakOver method, of class IBankiersessie.
	 *
	 * @throws java.rmi.RemoteException
	 * @throws fontys.util.NumberDoesntExistException
	 * @throws fontys.util.InvalidSessionException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testMaakOverBedragNull() throws RemoteException, NumberDoesntExistException, InvalidSessionException
	{
		/**
		 * er wordt bedrag overgemaakt van de bankrekening met het nummer bron
		 * naar de bankrekening met nummer bestemming
		 *
		 * @param bestemming is ongelijk aan rekeningnummer van deze
		 * bankiersessie
		 * @param bedrag is groter dan 0
		 * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
		 * @throws NumberDoesntExistException als bestemming onbekend is
		 * @throws InvalidSessionException als sessie niet meer geldig is
		 */

		int bestemming = 12345678;
		Money bedrag = null;

		this.iBankiersessie.maakOver(bestemming, bedrag);
	}

	/**
	 * Test of getRekening method, of class IBankiersessie.
	 *
	 * @throws java.rmi.RemoteException
	 * @throws fontys.util.InvalidSessionException
	 */
	@Test
	public void testGetRekeningValidSession() throws RemoteException, InvalidSessionException
	{
		/**
		 * @return de rekeninggegevens die horen bij deze sessie
		 * @throws InvalidSessionException als de sessieId niet geldig of
		 * verlopen is
		 * @throws RemoteException
		 */

		Assert.assertNotNull(this.iBankiersessie.getRekening());
	}

	/**
	 * Test of getRekening method, of class IBankiersessie.
	 *
	 * @throws java.rmi.RemoteException
	 * @throws fontys.util.InvalidSessionException
	 */
	@Test(expected = InvalidSessionException.class)
	public void testGetRekeningInvalidSession() throws RemoteException, InvalidSessionException
	{
		/**
		 * @return de rekeninggegevens die horen bij deze sessie
		 * @throws InvalidSessionException als de sessieId niet geldig of
		 * verlopen is
		 * @throws RemoteException
		 */

		try
		{
			Thread.sleep(IBankiersessie.GELDIGHEIDSDUUR + 1l);
		}
		catch (InterruptedException ex)
		{
			Logger.getLogger(IBankiersessieTest.class.getName()).log(Level.SEVERE, null, ex);
		}

		this.iBankiersessie.getRekening();
	}
}
