//%$JGUIToolboxEX$%//ID fuer Toolboxdateien
/**
 * Bewegter Ball
 */
public class Ball_Witt implements ITuWas {
	
	private int			xMittelpunkt;
	private int			yMittelpunkt;
	private int			dx	= 3;
	
	private Kreis		ball;
	private Taktgeber	takt;
	
	/**
	 * Konstruktor
	 */
	public Ball_Witt() {
		
		xMittelpunkt = 50;
		yMittelpunkt = 300;
		
		ball = new Kreis(50);
		ball.setzeMittelpunkt(xMittelpunkt, yMittelpunkt);
		ball.setzeFarbe("rot");
		
		takt = new Taktgeber(this, 99);
		takt.setzteZeitZwischenAktionen(50);
		takt.mehrfach(100);
		
	} // Ende Konstruktor
	
	/**
	 * horizontales Bewegen des Balls um dx
	 */
	public void bewege() {
		xMittelpunkt = xMittelpunkt + dx;
		ball.setzeMittelpunkt(xMittelpunkt, yMittelpunkt);
	} // Ende bewege
	
	public void tuWas(int ID) {
		bewege();
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		new Ball_Witt();
	}
	
} // Ende Klasse
