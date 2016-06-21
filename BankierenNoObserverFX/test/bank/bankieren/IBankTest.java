/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.bankieren;

import bank.centrale.Centrale;
import fontys.util.NumberDoesntExistException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Pim Janissen
 * @author J.H.L.M. Janssen
 */
public class IBankTest
{

	private String bankName;
	private IBank iBank;

	@Before
	public void setUp() throws RemoteException
	{
		this.bankName = "Rabobank";
		this.iBank = new Bank(this.bankName, new Centrale());
	}

	/**
	 * Test of openRekening method, of class IBank.
	 */
	@Test
	public void testOpenRekeningValideInput()
	{
		/**
		 * creatie van een nieuwe bankrekening met een identificerend
		 * rekeningnummer; alleen als de klant, geidentificeerd door naam en
		 * plaats, nog niet bestaat wordt er ook een nieuwe klant aangemaakt
		 *
		 * @param naam van de eigenaar van de nieuwe bankrekening
		 * @param plaats de woonplaats van de eigenaar van de nieuwe
		 * bankrekening
		 * @return -1 zodra naam of plaats een lege string en anders het nummer
		 * van de gecreeerde bankrekening
		 */

		String rekeninghouderNaam = "Erik Janssen";
		String rekeninghouderWoonplaats = "Eindhoven";

		int rekeningNummer = this.iBank.openRekening(rekeninghouderNaam, rekeninghouderWoonplaats);

		Assert.assertFalse(rekeningNummer == -1);
	}

	/**
	 * Test of openRekening method, of class IBank.
	 */
	@Test
	public void testOpenRekeningNaamLegeString()
	{
		/**
		 * creatie van een nieuwe bankrekening met een identificerend
		 * rekeningnummer; alleen als de klant, geidentificeerd door naam en
		 * plaats, nog niet bestaat wordt er ook een nieuwe klant aangemaakt
		 *
		 * @param naam van de eigenaar van de nieuwe bankrekening
		 * @param plaats de woonplaats van de eigenaar van de nieuwe
		 * bankrekening
		 * @return -1 zodra naam of plaats een lege string en anders het nummer
		 * van de gecreeerde bankrekening
		 */

		String rekeninghouderNaam = "";
		String rekeninghouderWoonplaats = "Eindhoven";

		int rekeningNummer = this.iBank.openRekening(rekeninghouderNaam, rekeninghouderWoonplaats);

		Assert.assertTrue(rekeningNummer == -1);
	}

	/**
	 * Test of openRekening method, of class IBank.
	 */
	@Test
	public void testOpenRekeningNaamEnkeleSpatie()
	{
		/**
		 * creatie van een nieuwe bankrekening met een identificerend
		 * rekeningnummer; alleen als de klant, geidentificeerd door naam en
		 * plaats, nog niet bestaat wordt er ook een nieuwe klant aangemaakt
		 *
		 * @param naam van de eigenaar van de nieuwe bankrekening
		 * @param plaats de woonplaats van de eigenaar van de nieuwe
		 * bankrekening
		 * @return -1 zodra naam of plaats een lege string en anders het nummer
		 * van de gecreeerde bankrekening
		 */

		String rekeninghouderNaam = " ";
		String rekeninghouderWoonplaats = "Eindhoven";

		int rekeningNummer = this.iBank.openRekening(rekeninghouderNaam, rekeninghouderWoonplaats);

		Assert.assertFalse(rekeningNummer == -1);
	}

	/**
	 * Test of openRekening method, of class IBank.
	 */
	@Test
	public void testOpenRekeningWoonplaatsLegeString()
	{
		/**
		 * creatie van een nieuwe bankrekening met een identificerend
		 * rekeningnummer; alleen als de klant, geidentificeerd door naam en
		 * plaats, nog niet bestaat wordt er ook een nieuwe klant aangemaakt
		 *
		 * @param naam van de eigenaar van de nieuwe bankrekening
		 * @param plaats de woonplaats van de eigenaar van de nieuwe
		 * bankrekening
		 * @return -1 zodra naam of plaats een lege string en anders het nummer
		 * van de gecreeerde bankrekening
		 */

		String rekeninghouderNaam = "Erik Janssen";
		String rekeninghouderWoonplaats = "";

		int rekeningNummer = this.iBank.openRekening(rekeninghouderNaam, rekeninghouderWoonplaats);

		Assert.assertTrue(rekeningNummer == -1);
	}

	/**
	 * Test of openRekening method, of class IBank.
	 */
	@Test
	public void testOpenRekeningWoonplaatsEnkeleSpatie()
	{
		/**
		 * creatie van een nieuwe bankrekening met een identificerend
		 * rekeningnummer; alleen als de klant, geidentificeerd door naam en
		 * plaats, nog niet bestaat wordt er ook een nieuwe klant aangemaakt
		 *
		 * @param naam van de eigenaar van de nieuwe bankrekening
		 * @param plaats de woonplaats van de eigenaar van de nieuwe
		 * bankrekening
		 * @return -1 zodra naam of plaats een lege string en anders het nummer
		 * van de gecreeerde bankrekening
		 */

		String rekeninghouderNaam = "Erik Janssen";
		String rekeninghouderWoonplaats = " ";

		int rekeningNummer = this.iBank.openRekening(rekeninghouderNaam, rekeninghouderWoonplaats);

		Assert.assertFalse(rekeningNummer == -1);
	}

	/**
	 * Test of openRekening method, of class IBank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testOpenRekeningNaamNull()
	{
		/**
		 * creatie van een nieuwe bankrekening met een identificerend
		 * rekeningnummer; alleen als de klant, geidentificeerd door naam en
		 * plaats, nog niet bestaat wordt er ook een nieuwe klant aangemaakt
		 *
		 * @param naam van de eigenaar van de nieuwe bankrekening
		 * @param plaats de woonplaats van de eigenaar van de nieuwe
		 * bankrekening
		 * @return -1 zodra naam of plaats een lege string en anders het nummer
		 * van de gecreeerde bankrekening
		 */

		String rekeninghouderNaam = null;
		String rekeninghouderWoonplaats = "Eindhoven";

		this.iBank.openRekening(rekeninghouderNaam, rekeninghouderWoonplaats);
	}

	/**
	 * Test of openRekening method, of class IBank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testOpenRekeningWoonplaatsNull()
	{
		/**
		 * creatie van een nieuwe bankrekening met een identificerend
		 * rekeningnummer; alleen als de klant, geidentificeerd door naam en
		 * plaats, nog niet bestaat wordt er ook een nieuwe klant aangemaakt
		 *
		 * @param naam van de eigenaar van de nieuwe bankrekening
		 * @param plaats de woonplaats van de eigenaar van de nieuwe
		 * bankrekening
		 * @return -1 zodra naam of plaats een lege string en anders het nummer
		 * van de gecreeerde bankrekening
		 */

		String rekeninghouderNaam = "Erik Janssen";
		String rekeninghouderWoonplaats = null;

		this.iBank.openRekening(rekeninghouderNaam, rekeninghouderWoonplaats);
	}

	/**
	 * Test of maakOver method, of class IBank.
	 *
	 * @throws fontys.util.NumberDoesntExistException
	 */
	@Test
	public void testMaakOverValideInput() throws NumberDoesntExistException
	{
		/**
		 * er wordt bedrag overgemaakt van de bankrekening met nummer bron naar
		 * de bankrekening met nummer bestemming, mits het afschrijven van het
		 * bedrag van de rekening met nr bron niet lager wordt dan de
		 * kredietlimiet van deze rekening
		 *
		 * @param bron
		 * @param bestemming ongelijk aan bron
		 * @param bedrag is groter dan 0
		 * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
		 * @throws NumberDoesntExistException als een van de twee
		 * bankrekeningnummers onbekend is
		 */

		int bron = this.iBank.openRekening("Erik Janssen", "Eindhoven");
		int bestemming = this.iBank.openRekening("Pieter Peeters", "Nuenen");
		Money bedrag = new Money(5020, Money.EURO);

		Assert.assertTrue(this.iBank.maakOver(bron, bestemming, bedrag));
	}

	/**
	 * Test of maakOver method, of class IBank.
	 *
	 * @throws fontys.util.NumberDoesntExistException
	 */
	@Test(expected = NumberDoesntExistException.class)
	public void testMaakOverBronBestaatNiet() throws NumberDoesntExistException
	{
		/**
		 * er wordt bedrag overgemaakt van de bankrekening met nummer bron naar
		 * de bankrekening met nummer bestemming, mits het afschrijven van het
		 * bedrag van de rekening met nr bron niet lager wordt dan de
		 * kredietlimiet van deze rekening
		 *
		 * @param bron
		 * @param bestemming ongelijk aan bron
		 * @param bedrag is groter dan 0
		 * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
		 * @throws NumberDoesntExistException als een van de twee
		 * bankrekeningnummers onbekend is
		 */

		int bron = 12345678;
		int bestemming = this.iBank.openRekening("Pieter Peeters", "Nuenen");
		Money bedrag = new Money(5020, Money.EURO);

		this.iBank.maakOver(bron, bestemming, bedrag);
	}

	/**
	 * Test of maakOver method, of class IBank.
	 *
	 * @throws fontys.util.NumberDoesntExistException
	 */
	@Test(expected = NumberDoesntExistException.class)
	public void testMaakOverBestemmingBestaatNiet() throws NumberDoesntExistException
	{
		/**
		 * er wordt bedrag overgemaakt van de bankrekening met nummer bron naar
		 * de bankrekening met nummer bestemming, mits het afschrijven van het
		 * bedrag van de rekening met nr bron niet lager wordt dan de
		 * kredietlimiet van deze rekening
		 *
		 * @param bron
		 * @param bestemming ongelijk aan bron
		 * @param bedrag is groter dan 0
		 * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
		 * @throws NumberDoesntExistException als een van de twee
		 * bankrekeningnummers onbekend is
		 */

		int bron = this.iBank.openRekening("Erik Janssen", "Eindhoven");
		int bestemming = 12345678;
		Money bedrag = new Money(5020, Money.EURO);

		this.iBank.maakOver(bron, bestemming, bedrag);
	}

	/**
	 * Test of maakOver method, of class IBank.
	 *
	 * @throws fontys.util.NumberDoesntExistException
	 */
	@Test(expected = NumberDoesntExistException.class)
	public void testMaakOverBronEnBestemmingBestaanNiet() throws NumberDoesntExistException
	{
		/**
		 * er wordt bedrag overgemaakt van de bankrekening met nummer bron naar
		 * de bankrekening met nummer bestemming, mits het afschrijven van het
		 * bedrag van de rekening met nr bron niet lager wordt dan de
		 * kredietlimiet van deze rekening
		 *
		 * @param bron
		 * @param bestemming ongelijk aan bron
		 * @param bedrag is groter dan 0
		 * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
		 * @throws NumberDoesntExistException als een van de twee
		 * bankrekeningnummers onbekend is
		 */

		int bron = 12345678;
		int bestemming = 11223344;
		Money bedrag = new Money(5020, Money.EURO);

		this.iBank.maakOver(bron, bestemming, bedrag);
	}

	/**
	 * Test of maakOver method, of class IBank.
	 *
	 * @throws fontys.util.NumberDoesntExistException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testMaakOverBedragNul() throws NumberDoesntExistException
	{
		/**
		 * er wordt bedrag overgemaakt van de bankrekening met nummer bron naar
		 * de bankrekening met nummer bestemming, mits het afschrijven van het
		 * bedrag van de rekening met nr bron niet lager wordt dan de
		 * kredietlimiet van deze rekening
		 *
		 * @param bron
		 * @param bestemming ongelijk aan bron
		 * @param bedrag is groter dan 0
		 * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
		 * @throws NumberDoesntExistException als een van de twee
		 * bankrekeningnummers onbekend is
		 */

		int bron = this.iBank.openRekening("Erik Janssen", "Eindhoven");
		int bestemming = this.iBank.openRekening("Pieter Peeters", "Nuenen");
		Money bedrag = new Money(0, Money.EURO);

		this.iBank.maakOver(bron, bestemming, bedrag);
	}

	/**
	 * Test of maakOver method, of class IBank.
	 *
	 * @throws fontys.util.NumberDoesntExistException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testMaakOverBedragNegatief() throws NumberDoesntExistException
	{
		/**
		 * er wordt bedrag overgemaakt van de bankrekening met nummer bron naar
		 * de bankrekening met nummer bestemming, mits het afschrijven van het
		 * bedrag van de rekening met nr bron niet lager wordt dan de
		 * kredietlimiet van deze rekening
		 *
		 * @param bron
		 * @param bestemming ongelijk aan bron
		 * @param bedrag is groter dan 0
		 * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
		 * @throws NumberDoesntExistException als een van de twee
		 * bankrekeningnummers onbekend is
		 */

		int bron = this.iBank.openRekening("Erik Janssen", "Eindhoven");
		int bestemming = this.iBank.openRekening("Pieter Peeters", "Nuenen");
		Money bedrag = new Money(-5020, Money.EURO);

		this.iBank.maakOver(bron, bestemming, bedrag);
	}

	/**
	 * Test of maakOver method, of class IBank.
	 *
	 * @throws fontys.util.NumberDoesntExistException
	 */
	@Test
	public void testMaakOverOnvoldoendeSaldo() throws NumberDoesntExistException
	{
		/**
		 * er wordt bedrag overgemaakt van de bankrekening met nummer bron naar
		 * de bankrekening met nummer bestemming, mits het afschrijven van het
		 * bedrag van de rekening met nr bron niet lager wordt dan de
		 * kredietlimiet van deze rekening
		 *
		 * @param bron
		 * @param bestemming ongelijk aan bron
		 * @param bedrag is groter dan 0
		 * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
		 * @throws NumberDoesntExistException als een van de twee
		 * bankrekeningnummers onbekend is
		 */

		int bron = this.iBank.openRekening("Erik Janssen", "Eindhoven");
		int bestemming = this.iBank.openRekening("Pieter Peeters", "Nuenen");
		Money bedrag = new Money(500000, Money.EURO);

		Assert.assertFalse(this.iBank.maakOver(bron, bestemming, bedrag));
	}

	/**
	 * Test of maakOver method, of class IBank.
	 *
	 * @throws fontys.util.NumberDoesntExistException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testMaakOverBronGelijkAanBestemming() throws NumberDoesntExistException
	{
		/**
		 * er wordt bedrag overgemaakt van de bankrekening met nummer bron naar
		 * de bankrekening met nummer bestemming, mits het afschrijven van het
		 * bedrag van de rekening met nr bron niet lager wordt dan de
		 * kredietlimiet van deze rekening
		 *
		 * @param bron
		 * @param bestemming ongelijk aan bron
		 * @param bedrag is groter dan 0
		 * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
		 * @throws NumberDoesntExistException als een van de twee
		 * bankrekeningnummers onbekend is
		 */

		int bron = this.iBank.openRekening("Erik Janssen", "Eindhoven");
		int bestemming = bron;
		Money bedrag = new Money(5020, Money.EURO);

		this.iBank.maakOver(bron, bestemming, bedrag);
	}

	/**
	 * Test of maakOver method, of class IBank.
	 *
	 * @throws fontys.util.NumberDoesntExistException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testMaakOverBedragNull() throws NumberDoesntExistException
	{
		/**
		 * er wordt bedrag overgemaakt van de bankrekening met nummer bron naar
		 * de bankrekening met nummer bestemming, mits het afschrijven van het
		 * bedrag van de rekening met nr bron niet lager wordt dan de
		 * kredietlimiet van deze rekening
		 *
		 * @param bron
		 * @param bestemming ongelijk aan bron
		 * @param bedrag is groter dan 0
		 * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
		 * @throws NumberDoesntExistException als een van de twee
		 * bankrekeningnummers onbekend is
		 */

		int bron = this.iBank.openRekening("Erik Janssen", "Eindhoven");
		int bestemming = this.iBank.openRekening("Pieter Peeters", "Nuenen");
		Money bedrag = null;

		Assert.assertFalse(this.iBank.maakOver(bron, bestemming, bedrag));
	}

	/**
	 * Test of getRekening method, of class IBank.
	 */
	@Test
	public void testGetRekeningValideInput()
	{
		/**
		 * @param nr
		 * @return de bankrekening met nummer nr mits bij deze bank bekend,
		 * anders null
		 */

		int rekeningnummer = this.iBank.openRekening("Erik Janssen", "Eindhoven");

		Assert.assertNotNull(this.iBank.getRekening(rekeningnummer));
	}

	/**
	 * Test of getRekening method, of class IBank.
	 */
	@Test
	public void testGetRekeningRekeningnummerOnbekend()
	{
		/**
		 * @param nr
		 * @return de bankrekening met nummer nr mits bij deze bank bekend,
		 * anders null
		 */

		int rekeningnummer = 12345678;

		Assert.assertNull(this.iBank.getRekening(rekeningnummer));
	}

	/**
	 * Test of getName method, of class IBank.
	 */
	@Test
	public void testGetName()
	{
		Assert.assertEquals(this.bankName, this.iBank.getName());
	}
}
