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
	private void chargerCartes() {
		m_cartes = new ArrayList<Cursus>(m_numcartes);
		m_cartes.add(new Cursus(UV.AP4A, UV.IA41, 10));
		m_cartes.add(new Cursus(UV.AP4A, UV.IT45, 16));
		m_cartes.add(new Cursus(UV.IA41, UV.LE03, 12));
		m_cartes.add(new Cursus(UV.IT40, UV.IT42, 6));
		m_cartes.add(new Cursus(UV.IT40, UV.RN40, 7));
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
