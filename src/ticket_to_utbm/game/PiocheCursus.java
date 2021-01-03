package ticket_to_utbm.game;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Gère la pioche de cartes cursus (aka destination)
 * @author Grégori MIGNEROT
 */
public class PiocheCursus {
	// m_cartes : contient toutes les cartes Cursus du jeu, pour ne pas les recharger si on relance
	private ArrayList<Cursus> m_cartes;
	// m_pioche : la liste des cartes dans la pioche. Le début de la liste correspond au dessus de la pioche, la fin au dessous.
	private ArrayList<Cursus> m_pioche;
	private final int m_numcartes = 5;  // TODO : Compléter les cartes cursus
	
	/**
	 * Charge les cartes puis initialise la pioche
	 */
	public PiocheCursus() {
		chargerCartes();
		init();
	}
	
	/**
	 * Charge les cartes cursus
	 */
	private void chargerCartes() {  // TODO
		m_cartes = new ArrayList<Cursus>(m_numcartes);
		m_cartes.add(new Cursus(UV.LE03, UV.SP07, 21));
		m_cartes.add(new Cursus(UV.SI40, UV.IT43, 8));
		m_cartes.add(new Cursus(UV.PC40, UV.SY40, 8));
		m_cartes.add(new Cursus(UV.LE03, UV.SY42, 6));
		m_cartes.add(new Cursus(UV.EC02, UV.SY40, 17));
		m_cartes.add(new Cursus(UV.HT01, UV.LE07, 20));
		m_cartes.add(new Cursus(UV.SY40, UV.DR02, 10));
		m_cartes.add(new Cursus(UV.RN40, UV.TX52, 10));
		m_cartes.add(new Cursus(UV.EC02, UV.GE07, 11));
		m_cartes.add(new Cursus(UV.LE03, UV.IT44, 11));
		m_cartes.add(new Cursus(UV.HE09, UV.IA41, 7));
		m_cartes.add(new Cursus(UV.HE09, UV.GE07, 13));
		m_cartes.add(new Cursus(UV.SP07, UV.TX52, 20));
		m_cartes.add(new Cursus(UV.HE05, UV.RE4E, 11));
		m_cartes.add(new Cursus(UV.ST40, UV.SY42, 17));
		m_cartes.add(new Cursus(UV.IT42, UV.IT43, 5));
		m_cartes.add(new Cursus(UV.SP07, UV.WE4A, 16));
		m_cartes.add(new Cursus(UV.IT40, UV.RN41, 11));
		m_cartes.add(new Cursus(UV.WE4A, UV.DR01, 9));
		m_cartes.add(new Cursus(UV.HT01, UV.DR01, 13));
		m_cartes.add(new Cursus(UV.LE05, UV.TX52, 12));
		m_cartes.add(new Cursus(UV.WE4A, UV.RS40, 7));
		m_cartes.add(new Cursus(UV.LE07, UV.SY42, 9));
		m_cartes.add(new Cursus(UV.PH02, UV.LE03, 22));
		m_cartes.add(new Cursus(UV.IT40, UV.DR02, 4));
		m_cartes.add(new Cursus(UV.SP07, UV.AP4A, 8));
		m_cartes.add(new Cursus(UV.HE05, UV.IT43, 12));
		m_cartes.add(new Cursus(UV.LE07, UV.RS40, 13));
		m_cartes.add(new Cursus(UV.PC40, UV.IT41, 9));
		m_cartes.add(new Cursus(UV.PH02, UV.SP07, 9));
	}
	
	/**
	 * Ré-initialise la pioche
	 */
	public void init() {
		m_pioche = new ArrayList<Cursus>(m_numcartes);
		for (Cursus carte: m_cartes) {
			m_pioche.add(carte);
		}
		melanger();
	}
	
	/**
	 * Mélange la pioche
	 */
	public void melanger() {
		Collections.shuffle(m_pioche);
	}
	
	/**
	 * Pioche une carte.
	 * Renvoie la carte du dessus de la pioche, et la retire de la pioche.
	 * @return La carte piochée
	 */
	public Cursus piocher() {
		Cursus carte = m_pioche.get(0);
		m_pioche.remove(0);
		return carte;
	}
	
	/**
	 * Pioche des cartes.
	 * Renvoie les cartes du dessus de la pioche, et les retire de la pioche.
	 * @param num Nombre de cartes à prendre
	 * @return Liste des cartes piochées
	 */
	public ArrayList<Cursus> piocher(int num) {
		ArrayList<Cursus> cartes = new ArrayList<Cursus>(num);
		for (int i = 0; i < num; i++) {
			Cursus carte = m_pioche.get(0);
			m_pioche.remove(0);
			cartes.add(carte);
		}
		return cartes;
	}
	
	/**
	 * Défausse une carte cursus
	 * Place la carte donnée au-dessous de la pioche.
	 * @param carte Carte à défausser
	 */
	public void defausser(Cursus carte) {
		m_pioche.add(carte);
	}
	
	/**
	 * Défausse des cartes cursus
	 * Place les cartes données au-dessous de la pioche
	 * @param cartes Liste de cartes à défausser
	 */
	public void defausser(List<Cursus> cartes) {
		for (Cursus carte: cartes) {
			m_pioche.add(carte);
		}
	}
	
	/**
	 * Defausse des cartes cursus
	 * Place les cartes données au-dessous de la pioche
	 * @param cartes Cartes à défausser
	 */
	public void defausser(Cursus... cartes) {
		for (Cursus carte: cartes) {
			m_pioche.add(carte);
		}
	}
}
