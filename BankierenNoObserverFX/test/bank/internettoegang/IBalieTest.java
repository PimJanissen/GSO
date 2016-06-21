/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.internettoegang;

import bank.bankieren.Bank;
import bank.bankieren.IBank;
import bank.centrale.Centrale;
import java.rmi.RemoteException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Pim Janissen
 */
public class IBalieTest
{

	private IBank iBank;
	private IBalie iBalie;

	@Before
	public void setUp() throws RemoteException
	{
		this.iBank = new Bank("Rabobank", new Centrale());
		this.iBalie = new Balie(this.iBank);
	}

	/**
	 * Test of openRekening method, of class IBalie.
	 * @throws java.rmi.RemoteException
	 */
	@Test
	public void testOpenRekeningValideInput() throws RemoteException
	{
		/**
		 * creatie van een nieuwe bankrekening; het gegenereerde
		 * bankrekeningnummer is identificerend voor de nieuwe bankrekening en
		 * heeft een saldo van 0 euro
		 *
		 * @param naam van de eigenaar van de nieuwe bankrekening
		 * @param plaats de woonplaats van de eigenaar van de nieuwe
		 * bankrekening
		 * @param wachtwoord van het account waarmee er toegang kan worden
		 * verkregen tot de nieuwe bankrekening
		 * @return null zodra naam of plaats een lege string of wachtwoord
		 * minder dan vier of meer dan acht karakters lang is en anders de
		 * gegenereerde accountnaam(8 karakters lang) waarmee er toegang tot de
		 * nieuwe bankrekening kan worden verkregen
		 */

		String naam = "Erik Janssen";
		String plaats = "Eindhoven";
		String wachtwoord = "S3GSO";

		String accountnaam = this.iBalie.openRekening(naam, plaats, wachtwoord);

		Assert.assertTrue(accountnaam.length() == 8);
	}

	/**
	 * Test of openRekening method, of class IBalie.
	 *
	 * @throws java.rmi.RemoteException
	 */
	@Test
	public void testOpenRekeningNaamLegeString() throws RemoteException
	{
		/**
		 * creatie van een nieuwe bankrekening; het gegenereerde
		 * bankrekeningnummer is identificerend voor de nieuwe bankrekening en
		 * heeft een saldo van 0 euro
		 *
		 * @param naam van de eigenaar van de nieuwe bankrekening
		 * @param plaats de woonplaats van de eigenaar van de nieuwe
		 * bankrekening
		 * @param wachtwoord van het account waarmee er toegang kan worden
		 * verkregen tot de nieuwe bankrekening
		 * @return null zodra naam of plaats een lege string of wachtwoord
		 * minder dan vier of meer dan acht karakters lang is en anders de
		 * gegenereerde accountnaam(8 karakters lang) waarmee er toegang tot de
		 * nieuwe bankrekening kan worden verkregen
		 */

		String naam = "";
		String plaats = "Eindhoven";
		String wachtwoord = "S3GSO";

		Assert.assertNull(this.iBalie.openRekening(naam, plaats, wachtwoord));
	}

	/**
	 * Test of openRekening method, of class IBalie.
	 *
	 * @throws java.rmi.RemoteException
	 */
	@Test
	public void testOpenRekeningNaamEnkeleSpatie() throws RemoteException
	{
		/**
		 * creatie van een nieuwe bankrekening; het gegenereerde
		 * bankrekeningnummer is identificerend voor de nieuwe bankrekening en
		 * heeft een saldo van 0 euro
		 *
		 * @param naam van de eigenaar van de nieuwe bankrekening
		 * @param plaats de woonplaats van de eigenaar van de nieuwe
		 * bankrekening
		 * @param wachtwoord van het account waarmee er toegang kan worden
		 * verkregen tot de nieuwe bankrekening
		 * @return null zodra naam of plaats een lege string of wachtwoord
		 * minder dan vier of meer dan acht karakters lang is en anders de
		 * gegenereerde accountnaam(8 karakters lang) waarmee er toegang tot de
		 * nieuwe bankrekening kan worden verkregen
		 */

		String naam = " ";
		String plaats = "Eindhoven";
		String wachtwoord = "S3GSO";

		String accountnaam = this.iBalie.openRekening(naam, plaats, wachtwoord);

		Assert.assertTrue(accountnaam.length() == 8);
	}

	/**
	 * Test of openRekening method, of class IBalie.
	 *
	 * @throws java.rmi.RemoteException
	 */
	@Test
	public void testOpenRekeningPlaatsLegeString() throws RemoteException
	{
		/**
		 * creatie van een nieuwe bankrekening; het gegenereerde
		 * bankrekeningnummer is identificerend voor de nieuwe bankrekening en
		 * heeft een saldo van 0 euro
		 *
		 * @param naam van de eigenaar van de nieuwe bankrekening
		 * @param plaats de woonplaats van de eigenaar van de nieuwe
		 * bankrekening
		 * @param wachtwoord van het account waarmee er toegang kan worden
		 * verkregen tot de nieuwe bankrekening
		 * @return null zodra naam of plaats een lege string of wachtwoord
		 * minder dan vier of meer dan acht karakters lang is en anders de
		 * gegenereerde accountnaam(8 karakters lang) waarmee er toegang tot de
		 * nieuwe bankrekening kan worden verkregen
		 */

		String naam = "Erik Janssen";
		String plaats = "";
		String wachtwoord = "S3GSO";

		Assert.assertNull(this.iBalie.openRekening(naam, plaats, wachtwoord));
	}

	/**
	 * Test of openRekening method, of class IBalie.
	 *
	 * @throws java.rmi.RemoteException
	 */
	@Test
	public void testOpenRekeningPlaatsEnkeleSpatie() throws RemoteException
	{
		/**
		 * creatie van een nieuwe bankrekening; het gegenereerde
		 * bankrekeningnummer is identificerend voor de nieuwe bankrekening en
		 * heeft een saldo van 0 euro
		 *
		 * @param naam van de eigenaar van de nieuwe bankrekening
		 * @param plaats de woonplaats van de eigenaar van de nieuwe
		 * bankrekening
		 * @param wachtwoord van het account waarmee er toegang kan worden
		 * verkregen tot de nieuwe bankrekening
		 * @return null zodra naam of plaats een lege string of wachtwoord
		 * minder dan vier of meer dan acht karakters lang is en anders de
		 * gegenereerde accountnaam(8 karakters lang) waarmee er toegang tot de
		 * nieuwe bankrekening kan worden verkregen
		 */

		String naam = "Erik Janssen";
		String plaats = " ";
		String wachtwoord = "S3GSO";

		String accountnaam = this.iBalie.openRekening(naam, plaats, wachtwoord);

		Assert.assertTrue(accountnaam.length() == 8);
	}

	/**
	 * Test of openRekening method, of class IBalie.
	 *
	 * @throws java.rmi.RemoteException
	 */
	@Test
	public void testOpenRekeningWachtwoordLegeString() throws RemoteException
	{
		/**
		 * creatie van een nieuwe bankrekening; het gegenereerde
		 * bankrekeningnummer is identificerend voor de nieuwe bankrekening en
		 * heeft een saldo van 0 euro
		 *
		 * @param naam van de eigenaar van de nieuwe bankrekening
		 * @param plaats de woonplaats van de eigenaar van de nieuwe
		 * bankrekening
		 * @param wachtwoord van het account waarmee er toegang kan worden
		 * verkregen tot de nieuwe bankrekening
		 * @return null zodra naam of plaats een lege string of wachtwoord
		 * minder dan vier of meer dan acht karakters lang is en anders de
		 * gegenereerde accountnaam(8 karakters lang) waarmee er toegang tot de
		 * nieuwe bankrekening kan worden verkregen
		 */

		String naam = "Erik Janssen";
		String plaats = "Eindhoven";
		String wachtwoord = "";

		Assert.assertNull(this.iBalie.openRekening(naam, plaats, wachtwoord));
	}

	/**
	 * Test of openRekening method, of class IBalie.
	 *
	 * @throws java.rmi.RemoteException
	 */
	@Test
	public void testOpenRekeningWachtwoordDrieKarakters() throws RemoteException
	{
		/**
		 * creatie van een nieuwe bankrekening; het gegenereerde
		 * bankrekeningnummer is identificerend voor de nieuwe bankrekening en
		 * heeft een saldo van 0 euro
		 *
		 * @param naam van de eigenaar van de nieuwe bankrekening
		 * @param plaats de woonplaats van de eigenaar van de nieuwe
		 * bankrekening
		 * @param wachtwoord van het account waarmee er toegang kan worden
		 * verkregen tot de nieuwe bankrekening
		 * @return null zodra naam of plaats een lege string of wachtwoord
		 * minder dan vier of meer dan acht karakters lang is en anders de
		 * gegenereerde accountnaam(8 karakters lang) waarmee er toegang tot de
		 * nieuwe bankrekening kan worden verkregen
		 */

		String naam = "Erik Janssen";
		String plaats = "Eindhoven";
		String wachtwoord = "ABC";

		Assert.assertNull(this.iBalie.openRekening(naam, plaats, wachtwoord));
	}

	/**
	 * Test of openRekening method, of class IBalie.
	 *
	 * @throws java.rmi.RemoteException
	 */
	@Test
	public void testOpenRekeningWachtwoordVierKarakters() throws RemoteException
	{
		/**
		 * creatie van een nieuwe bankrekening; het gegenereerde
		 * bankrekeningnummer is identificerend voor de nieuwe bankrekening en
		 * heeft een saldo van 0 euro
		 *
		 * @param naam van de eigenaar van de nieuwe bankrekening
		 * @param plaats de woonplaats van de eigenaar van de nieuwe
		 * bankrekening
		 * @param wachtwoord van het account waarmee er toegang kan worden
		 * verkregen tot de nieuwe bankrekening
		 * @return null zodra naam of plaats een lege string of wachtwoord
		 * minder dan vier of meer dan acht karakters lang is en anders de
		 * gegenereerde accountnaam(8 karakters lang) waarmee er toegang tot de
		 * nieuwe bankrekening kan worden verkregen
		 */

		String naam = "Erik Janssen";
		String plaats = "Eindhoven";
		String wachtwoord = "ABCD";

		String accountnaam = this.iBalie.openRekening(naam, plaats, wachtwoord);

		Assert.assertTrue(accountnaam.length() == 8);
	}

	/**
	 * Test of openRekening method, of class IBalie.
	 *
	 * @throws java.rmi.RemoteException
	 */
	@Test
	public void testOpenRekeningWachtwoordAchtKarakters() throws RemoteException
	{
		/**
		 * creatie van een nieuwe bankrekening; het gegenereerde
		 * bankrekeningnummer is identificerend voor de nieuwe bankrekening en
		 * heeft een saldo van 0 euro
		 *
		 * @param naam van de eigenaar van de nieuwe bankrekening
		 * @param plaats de woonplaats van de eigenaar van de nieuwe
		 * bankrekening
		 * @param wachtwoord van het account waarmee er toegang kan worden
		 * verkregen tot de nieuwe bankrekening
		 * @return null zodra naam of plaats een lege string of wachtwoord
		 * minder dan vier of meer dan acht karakters lang is en anders de
		 * gegenereerde accountnaam(8 karakters lang) waarmee er toegang tot de
		 * nieuwe bankrekening kan worden verkregen
		 */

		String naam = "Erik Janssen";
		String plaats = "Eindhoven";
		String wachtwoord = "ABCDEFGH";

		String accountnaam = this.iBalie.openRekening(naam, plaats, wachtwoord);

		Assert.assertTrue(accountnaam.length() == 8);
	}

	/**
	 * Test of openRekening method, of class IBalie.
	 *
	 * @throws java.rmi.RemoteException
	 */
	@Test
	public void testOpenRekeningWachtwoordNegenKarakters() throws RemoteException
	{
		/**
		 * creatie van een nieuwe bankrekening; het gegenereerde
		 * bankrekeningnummer is identificerend voor de nieuwe bankrekening en
		 * heeft een saldo van 0 euro
		 *
		 * @param naam van de eigenaar van de nieuwe bankrekening
		 * @param plaats de woonplaats van de eigenaar van de nieuwe
		 * bankrekening
		 * @param wachtwoord van het account waarmee er toegang kan worden
		 * verkregen tot de nieuwe bankrekening
		 * @return null zodra naam of plaats een lege string of wachtwoord
		 * minder dan vier of meer dan acht karakters lang is en anders de
		 * gegenereerde accountnaam(8 karakters lang) waarmee er toegang tot de
		 * nieuwe bankrekening kan worden verkregen
		 */

		String naam = "Erik Janssen";
		String plaats = "Eindhoven";
		String wachtwoord = "ABCDEFGHI";

		Assert.assertNull(this.iBalie.openRekening(naam, plaats, wachtwoord));
	}

	/**
	 * Test of openRekening method, of class IBalie.
	 *
	 * @throws java.rmi.RemoteException
	 */
	@Test
	public void testOpenRekeningWachtwoordEnkelSpaties() throws RemoteException
	{
		/**
		 * creatie van een nieuwe bankrekening; het gegenereerde
		 * bankrekeningnummer is identificerend voor de nieuwe bankrekening en
		 * heeft een saldo van 0 euro
		 *
		 * @param naam van de eigenaar van de nieuwe bankrekening
		 * @param plaats de woonplaats van de eigenaar van de nieuwe
		 * bankrekening
		 * @param wachtwoord van het account waarmee er toegang kan worden
		 * verkregen tot de nieuwe bankrekening
		 * @return null zodra naam of plaats een lege string of wachtwoord
		 * minder dan vier of meer dan acht karakters lang is en anders de
		 * gegenereerde accountnaam(8 karakters lang) waarmee er toegang tot de
		 * nieuwe bankrekening kan worden verkregen
		 */

		String naam = "Erik Janssen";
		String plaats = "Eindhoven";
		String wachtwoord = "      ";

		String accountnaam = this.iBalie.openRekening(naam, plaats, wachtwoord);

		Assert.assertTrue(accountnaam.length() == 8);
	}

	/**
	 * Test of openRekening method, of class IBalie.
	 *
	 * @throws java.rmi.RemoteException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testOpenRekeningNaamNull() throws RemoteException
	{
		/**
		 * creatie van een nieuwe bankrekening; het gegenereerde
		 * bankrekeningnummer is identificerend voor de nieuwe bankrekening en
		 * heeft een saldo van 0 euro
		 *
		 * @param naam van de eigenaar van de nieuwe bankrekening
		 * @param plaats de woonplaats van de eigenaar van de nieuwe
		 * bankrekening
		 * @param wachtwoord van het account waarmee er toegang kan worden
		 * verkregen tot de nieuwe bankrekening
		 * @return null zodra naam of plaats een lege string of wachtwoord
		 * minder dan vier of meer dan acht karakters lang is en anders de
		 * gegenereerde accountnaam(8 karakters lang) waarmee er toegang tot de
		 * nieuwe bankrekening kan worden verkregen
		 */

		String naam = null;
		String plaats = "Eindhoven";
		String wachtwoord = "S3GSO";

		this.iBalie.openRekening(naam, plaats, wachtwoord);
	}

	/**
	 * Test of openRekening method, of class IBalie.
	 *
	 * @throws java.rmi.RemoteException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testOpenRekeningWoonplaatsNull() throws RemoteException
	{
		/**
		 * creatie van een nieuwe bankrekening; het gegenereerde
		 * bankrekeningnummer is identificerend voor de nieuwe bankrekening en
		 * heeft een saldo van 0 euro
		 *
		 * @param naam van de eigenaar van de nieuwe bankrekening
		 * @param plaats de woonplaats van de eigenaar van de nieuwe
		 * bankrekening
		 * @param wachtwoord van het account waarmee er toegang kan worden
		 * verkregen tot de nieuwe bankrekening
		 * @return null zodra naam of plaats een lege string of wachtwoord
		 * minder dan vier of meer dan acht karakters lang is en anders de
		 * gegenereerde accountnaam(8 karakters lang) waarmee er toegang tot de
		 * nieuwe bankrekening kan worden verkregen
		 */

		String naam = "Erik Janssen";
		String plaats = null;
		String wachtwoord = "S3GSO";

		this.iBalie.openRekening(naam, plaats, wachtwoord);
	}

	/**
	 * Test of openRekening method, of class IBalie.
	 *
	 * @throws java.rmi.RemoteException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testOpenRekeningWachtwoordNull() throws RemoteException
	{
		/**
		 * creatie van een nieuwe bankrekening; het gegenereerde
		 * bankrekeningnummer is identificerend voor de nieuwe bankrekening en
		 * heeft een saldo van 0 euro
		 *
		 * @param naam van de eigenaar van de nieuwe bankrekening
		 * @param plaats de woonplaats van de eigenaar van de nieuwe
		 * bankrekening
		 * @param wachtwoord van het account waarmee er toegang kan worden
		 * verkregen tot de nieuwe bankrekening
		 * @return null zodra naam of plaats een lege string of wachtwoord
		 * minder dan vier of meer dan acht karakters lang is en anders de
		 * gegenereerde accountnaam(8 karakters lang) waarmee er toegang tot de
		 * nieuwe bankrekening kan worden verkregen
		 */

		String naam = "Erik Janssen";
		String plaats = "Eindhoven";
		String wachtwoord = null;

		this.iBalie.openRekening(naam, plaats, wachtwoord);
	}

	/**
	 * Test of logIn method, of class IBalie.
	 *
	 * @throws java.rmi.RemoteException
	 */
	@Test
	public void testLogInValideInput() throws RemoteException
	{
		/**
		 * er wordt een sessie opgestart voor het login-account met de naam
		 * accountnaam mits het wachtwoord correct is
		 *
		 * @param accountnaam
		 * @param wachtwoord
		 * @return de gegenereerde sessie waarbinnen de gebruiker toegang krijgt
		 * tot de bankrekening die hoort bij het betreffende login- account mits
		 * accountnaam en wachtwoord matchen, anders null
		 */

		String naam = "Erik Janssen";
		String plaats = "Eindhoven";
		String wachtwoord = "S3GSO";

		String accountnaam = this.iBalie.openRekening(naam, plaats, wachtwoord);

		Assert.assertNotNull(this.iBalie.logIn(accountnaam, wachtwoord));
	}

	/**
	 * Test of logIn method, of class IBalie.
	 *
	 * @throws java.rmi.RemoteException
	 */
	@Test
	public void testLogInOngeldigWachtwoord() throws RemoteException
	{
		/**
		 * er wordt een sessie opgestart voor het login-account met de naam
		 * accountnaam mits het wachtwoord correct is
		 *
		 * @param accountnaam
		 * @param wachtwoord
		 * @return de gegenereerde sessie waarbinnen de gebruiker toegang krijgt
		 * tot de bankrekening die hoort bij het betreffende login- account mits
		 * accountnaam en wachtwoord matchen, anders null
		 */

		String naam = "Erik Janssen";
		String plaats = "Eindhoven";
		String wachtwoord = "S3GSO";

		String accountnaam = this.iBalie.openRekening(naam, plaats, wachtwoord);

		Assert.assertNull(this.iBalie.logIn(accountnaam, "S3JSF"));
	}

	/**
	 * Test of logIn method, of class IBalie.
	 *
	 * @throws java.rmi.RemoteException
	 */
	@Test
	public void testLogInOngeldigeAccountnaam() throws RemoteException
	{
		/**
		 * er wordt een sessie opgestart voor het login-account met de naam
		 * accountnaam mits het wachtwoord correct is
		 *
		 * @param accountnaam
		 * @param wachtwoord
		 * @return de gegenereerde sessie waarbinnen de gebruiker toegang krijgt
		 * tot de bankrekening die hoort bij het betreffende login- account mits
		 * accountnaam en wachtwoord matchen, anders null
		 */

		String naam = "Erik Janssen";
		String plaats = "Eindhoven";
		String wachtwoord = "S3GSO";

		this.iBalie.openRekening(naam, plaats, wachtwoord);

		Assert.assertNull(this.iBalie.logIn("Erik Janssen", wachtwoord));
	}

	/**
	 * Test of logIn method, of class IBalie.
	 *
	 * @throws java.rmi.RemoteException
	 */
	@Test
	public void testLogInGeldigWachtwoordAndereCaps() throws RemoteException
	{
		/**
		 * er wordt een sessie opgestart voor het login-account met de naam
		 * accountnaam mits het wachtwoord correct is
		 *
		 * @param accountnaam
		 * @param wachtwoord
		 * @return de gegenereerde sessie waarbinnen de gebruiker toegang krijgt
		 * tot de bankrekening die hoort bij het betreffende login- account mits
		 * accountnaam en wachtwoord matchen, anders null
		 */

		String naam = "Erik Janssen";
		String plaats = "Eindhoven";
		String wachtwoord = "S3GSO";

		String accountnaam = this.iBalie.openRekening(naam, plaats, wachtwoord);

		Assert.assertNull(this.iBalie.logIn(accountnaam, wachtwoord.toLowerCase()));
	}

	/**
	 * Test of logIn method, of class IBalie.
	 *
	 * @throws java.rmi.RemoteException
	 */
	@Test
	public void testLogInGeldigeAccountnaamAndereCaps() throws RemoteException
	{
		/**
		 * er wordt een sessie opgestart voor het login-account met de naam
		 * accountnaam mits het wachtwoord correct is
		 *
		 * @param accountnaam
		 * @param wachtwoord
		 * @return de gegenereerde sessie waarbinnen de gebruiker toegang krijgt
		 * tot de bankrekening die hoort bij het betreffende login- account mits
		 * accountnaam en wachtwoord matchen, anders null
		 */

		String naam = "Erik Janssen";
		String plaats = "Eindhoven";
		String wachtwoord = "S3GSO";

		String accountnaam = this.iBalie.openRekening(naam, plaats, wachtwoord);

		Assert.assertNull(this.iBalie.logIn(accountnaam.toUpperCase(), wachtwoord));
	}

	/**
	 * Test of logIn method, of class IBalie.
	 *
	 * @throws java.rmi.RemoteException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testLogInAccountnaamNull() throws RemoteException
	{
		/**
		 * er wordt een sessie opgestart voor het login-account met de naam
		 * accountnaam mits het wachtwoord correct is
		 *
		 * @param accountnaam
		 * @param wachtwoord
		 * @return de gegenereerde sessie waarbinnen de gebruiker toegang krijgt
		 * tot de bankrekening die hoort bij het betreffende login- account mits
		 * accountnaam en wachtwoord matchen, anders null
		 */

		String naam = "Erik Janssen";
		String plaats = "Eindhoven";
		String wachtwoord = "S3GSO";

		this.iBalie.openRekening(naam, plaats, wachtwoord);

		this.iBalie.logIn(null, wachtwoord);
	}

	/**
	 * Test of logIn method, of class IBalie.
	 *
	 * @throws java.rmi.RemoteException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testLogInWachtwoordNull() throws RemoteException
	{
		/**
		 * er wordt een sessie opgestart voor het login-account met de naam
		 * accountnaam mits het wachtwoord correct is
		 *
		 * @param accountnaam
		 * @param wachtwoord
		 * @return de gegenereerde sessie waarbinnen de gebruiker toegang krijgt
		 * tot de bankrekening die hoort bij het betreffende login- account mits
		 * accountnaam en wachtwoord matchen, anders null
		 */

		String naam = "Erik Janssen";
		String plaats = "Eindhoven";
		String wachtwoord = "S3GSO";

		String accountnaam = this.iBalie.openRekening(naam, plaats, wachtwoord);

		this.iBalie.logIn(accountnaam, null);
	}
}
