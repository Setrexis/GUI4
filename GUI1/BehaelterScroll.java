//%$JGUIToolbox$%//ID fuer Toolboxdateien

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class BehaelterScroll extends BasisComponente implements IComponente,
		IContainer {

	public Zeichenflaeche panel;
	public JScrollPane scrollPane = null;

	/**
	 * Konstuktor fuer Hauptfenster
	 */
	public BehaelterScroll() {
		this(Zeichnung.gibZeichenflaeche());
	}

	/**
	 * Konstruktor fuer Hauptfenster
	 * 
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public BehaelterScroll(int neueBreite, int neueHoehe) {
		this(Zeichnung.gibZeichenflaeche(), 0, 0, neueBreite, neueHoehe);
	}

	/**
	 * Konstruktor fuer Hauptfenster
	 * 
	 * @param neuesX
	 * @param neuesY
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public BehaelterScroll(int neuesX, int neuesY, int neueBreite, int neueHoehe) {
		this(Zeichnung.gibZeichenflaeche(), neuesX, neuesY, neueBreite,
				neueHoehe);
	}

	/**
	 * Konstruktor
	 * 
	 * @param behaelter
	 */
	public BehaelterScroll(IContainer behaelter) {
		this(behaelter, 0, 0, 100, 50);
	}

	/**
	 * allgemeiner Konstuktor
	 * 
	 * @param behaelter
	 * @param neueBreite
	 * @param neueHoehe
	 */
	public BehaelterScroll(IContainer behaelter, int neuesX, int neuesY,
			int neueBreite, int neueHoehe) {

		setLayout(new BorderLayout());

		panel = new Zeichenflaeche();
		panel.setLayout(null);

		scrollPane = new JScrollPane();

		add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(panel);

		behaelter.add(this, 0);
		setzeDimensionen(neuesX, neuesY, neueBreite, neueHoehe);

		behaelter.validate();
	}

	@Override
	public BasisComponente getBasisComponente() {
		return this;
	}

	@Override
	public void paintComponentSpezial(Graphics g) {
	}

    /**
     * Fuer Interface IContainer
     */
	@Override
	public Component add(Component comp, int index){
		return panel.add(comp,index);
	}

	
     public void setzeKomponentenKoordinaten(JComponent obj, int x, int y,
        int width, int height) {
    	 panel.setzeKomponentenKoordinaten(obj, x, y, width, height);
    }

    public void setzeKomponentenGroesse(JComponent obj, int width, int height) {
    	panel.setzeKomponentenGroesse(obj, width, height);
    }

    @Override
	public void setzeKomponentenPosition(JComponent obj, int x, int y) {
    	panel.setzeKomponentenPosition(obj, x, y);
    }
	@Override
	public double getBehaelterZoom() {
		return 1;
	}

}
