//%$JGUIToolboxEX$%//ID fuer Toolboxdateien
public class FarbeInidatei {

	Rechteck anzeige1;
	Rechteck anzeige2;
	Rechteck anzeige3;
	INIDatei farben ;

	public FarbeInidatei() {
		
	    farben = new INIDatei("VieleFarben.ini");
	    StaticTools.setzeFarben(farben.leseSketion("Farben"));

	    anzeige1 = new Rechteck(10, 10, 300, 80);
		anzeige2 = new Rechteck(10, 110, 300, 80);
		anzeige3 = new Rechteck(10, 210, 300, 80);
		anzeige1.setzeFarbe("purple4");
		anzeige2.setzeFarbe("red1");
		anzeige3.setzeFarbe("chocolate3");
		
		farben.setzeSektion("Farben", StaticTools.leseFarben());
		farben.schreibeINIDatei("VieleFarben.ini",true);
	}

	/**
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		new FarbeInidatei();
	}

}
