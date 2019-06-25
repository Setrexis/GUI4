//%$JGUIToolbox$%//ID fuer Toolboxdateien
/**
 * <h1>Siebensegment8x</h1> mit mehreren Stellen und Dezimalpunkt.
 * 
 * <h2>Methoden</h2> 
 * fuer ganze Zahlen: anzeige(long zahl)  <br/>
 * fuer ganze Zahlen. Der Dezimalpunkt wird dp Stellen von Rechts gesetzt: void anzeige( long lZahl, int dp) <br/>
 * anzeige(double dZahl, int nachkomma) fuer Dezimalzahlen  <br/>
 * nachkomma: Anzahl der Stellen nach dem Komma  <br/>
 * nachkomma = -1 -> kein Dezimalpunkt <br/>
 * 
 * 
 * 
 * @author Hans Witt
 * 
 *         Version: 1.2 ( 19.7.2007 ) Fehler in der Anzeige Exception beim
 *         Ueberlauf bei Dezimalanzeigen Version: 3 (4.8.2008) ergaenzt fuer
 *         Containerklasse fuer GUI-Elemente
 * Version: 3.1 (14.8.2008) Konstruktor auf int neuesX, int neuesY , int
 *           neueBreite, int neueHoehe angepasst
 * @version  3.2 (18.8.2008)
 *        Zustandsvariable auf protected gesetzt 
 */

public class Segment8x implements IComponente{

	private SiebenSegment[] s;
	private Behaelter anzeige;

	protected int hoehe = 100;
	protected int stellen = 6;

	protected int xPos = 0;
	protected int yPos = 0;

	public Segment8x(int stellen, int hoehe) {
		this(Zeichnung.gibZeichenflaeche(), 0,0, stellen, hoehe);
	}

	public Segment8x(int neuesX, int neuesY, int stellen, int hoehe) {
		this(Zeichnung.gibZeichenflaeche(), neuesX, neuesY, stellen, hoehe);
	}

	public Segment8x(IContainer behaelter, int neuesX, int neuesY,
			int stellen, int hoehe) {
		anzeige = new Behaelter(behaelter,0,0,1,1); // moeglichst klein
		s = new SiebenSegment[stellen];
		this.stellen = stellen;
		this.hoehe = hoehe;
		setzePositionInSiebensegmentanzeige();
		setzePosition(neuesX, neuesY);
	}

	/**
	 *  Das Interface IComponente fordert eine Methode die eine BasisComponente zurueckliefert.
	 *  Sie wird benoetigt, um ein Objekt zu einem anderen Behaelter hinzuzufuegen
	 */
	public BasisComponente getBasisComponente() {
		return anzeige.getBasisComponente() ;
	}

	private void setzePositionInSiebensegmentanzeige() {
		for (int i = 0; i < stellen; i++) {
			if (s[i] == null) {
				s[i] = new SiebenSegment(anzeige, hoehe * 6 / 10
						* (stellen - i - 1), 0, hoehe);
			} else {
				s[i].setzePosition(hoehe * 6 / 10 * (stellen - i - 1), 0);
			}
		}
		anzeige.setzeGroesse(hoehe * 6 / 10 * stellen, hoehe);
	}

	public void setzePosition(int x, int y) {
		xPos = x;
		yPos = y;
		anzeige.setzePosition(xPos, yPos);
	}

	
	public void verschieben(int dx , int dy ) {
		setzePosition(xPos + dx, yPos + dy );
	}

	public void setzeGroesse(int hoehe) {
		this.hoehe = hoehe;
		for (int i = 0; i < stellen; i++) {
			s[i].setzeGroesse(hoehe);
		}
		setzePositionInSiebensegmentanzeige();
	}

	/*
	 * gueltige Farben: "rot", "gelb", "blau", "gruen", "lila" , "schwarz" ,
	 * "weiss" , "grau","pink","magenta","orange","cyan","hellgrau"
	 */
	public void setzeFarbe(String neueFarbe) {
		for (int i = 0; i < stellen; i++) {
			s[i].setzeFarbe(neueFarbe);
		}
		setzePosition(xPos, yPos);
	}

	/**
	 * Anzeige einer ganzen Zahl
	 * 
	 * @param zahl
	 */
	public void anzeige(long zahl) {
		int i = 0;
		for (i = 0; i < stellen; i++) {
			s[i].anzeige((int) (zahl % 10));
			zahl = zahl / 10;
		}
		if (zahl != 0) {
			for (i = 0; i < (stellen); i++) {
				s[i].zeigeDP(true);
			}
		}
	}

	/**
	 * Die Anzeige einer Dezimalzahl 
	 * 
	 * @param lZahl ganze Zahl. Anzeige als Pseudo-Dezimalzahl
	 * @param dp Stelle des Dezimalpunkts -1 = ohne 0 = ganz rechts n = neben der n-ten Stelle von rechts
	 */
	public void anzeige( long lZahl, int dp) {
		int i;
		boolean neg = false;
		if (lZahl < 0) {
			lZahl = -lZahl;
			s[stellen - 1].anzeigeMinus();
			neg = true;
		}

		for (i = 0; i < (stellen - (neg ? 1 : 0)); i++) {
			s[i].anzeige((int) (lZahl % 10));
			s[i].zeigeDP(i == (dp));
			lZahl = lZahl / 10;
		}

		if (lZahl != 0) {
			for (i = 0; i <= (stellen - (neg ? 1 : 0)); i++) {
				if (i < stellen) {
					s[i].zeigeDP(true);
				}
			}
		}
	}

	/**
	 * Die Anzeige einer Dezimalzahl 
	 * 
	 * @param dZahl anzuzeigende Dezimalzahl
	 * @param dp Stelle des Dezimalpunkts -1 = ohne 0 = ganz rechts n = neben der n-ten Stelle von rechts
	 */
	public void anzeige(double dZahl, int dp) {
		int i;
		boolean neg = false;
		if (dZahl < 0) {
			dZahl = -dZahl;
			s[stellen - 1].anzeigeMinus();
			neg = true;
		}

		for (i = 0; i < dp; i++) {
			dZahl = dZahl * 10;
		}

		long zahl = Math.round(Math.floor(dZahl));

		for (i = 0; i < (stellen - (neg ? 1 : 0)); i++) {
			s[i].anzeige((int) (zahl % 10));
			s[i].zeigeDP(i == (dp));
			zahl = zahl / 10;
		}

		if (zahl != 0) {
			for (i = 0; i <= (stellen - (neg ? 1 : 0)); i++) {
				if (i < stellen) {
					s[i].zeigeDP(true);
				}
			}
		}
	}


	public static void main(String[] args) {
		Segment8x t = new Segment8x(6, 40);
		t.setzeGroesse(120);
		t.anzeige(15.756, 2);
		StaticTools.warte(500);
		Segment8x t1 = new Segment8x(12, 80);
		t1.setzePosition(0, 200);
		// Zahlen laufen
		for (int i = 1234567890; i < 1234567990; i++) {
			StaticTools.warte(200);
			t1.anzeige(i, 5);
		}
		double d = 2158917.234873;
		t1.anzeige(d, 0);
		StaticTools.warte(2000);
		// Der Dezimalpunkt l&auml;uft.
		for (int i = 0; i < 7; i++) {
			StaticTools.warte(1000);
			t1.anzeige(d, i);
		}
	}

}
