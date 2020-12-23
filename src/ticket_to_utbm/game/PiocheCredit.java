package ticket_to_utbm.game;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Gère la pioche de cartes crédits (aka cartes wagon)
 * @author Grégori MIGNEROT
 */
public class PiocheCredit {
	// Le dessus est au début, le dessous à la fin de la liste
	private ArrayList<Credit> m_pioche;
	private ArrayList<Credit> m_defausse;
	private final int m_num_credits_types = 8;
	private final int m_num_credits_color = 12;
	private final int m_num_credits_huma = 14;
	
	/**
	 * Initialise la pioche
	 */
	public PiocheCredit() {
		init();
	}
	
	/**
	 * Réinitialise la pioche et la défausse
	 */
	public void init() {
		m_defausse = new ArrayList<Credit>();
		m_pioche = new ArrayList<Credit>(m_num_credits_types * m_num_credits_color + m_num_credits_huma);
		for (int i = 0; i < m_num_credits_color; i++) {
			m_pioche.add(Credit.Rouge);
			m_pioche.add(Credit.Rose);
			m_pioche.add(Credit.Bleu);
			m_pioche.add(Credit.Vert);
			m_pioche.add(Credit.Jaune);
			m_pioche.add(Credit.Orange);
			m_pioche.add(Credit.Noir);
			m_pioche.add(Credit.Blanc);
		}
		for (int i = 0; i < m_num_credits_huma; i++) {
			m_pioche.add(Credit.Humanite);
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
	 * Pioche une carte
	 * Retourne la carte du dessus de la pioche et la retire de la pioche
	 * @return Carte piochée
	 */
	public Credit piocher() {
		if (m_pioche.size() == 0) {
			recupererDefausse();
		}
		Credit carte = m_pioche.get(0);
		m_pioche.remove(0);
		return carte;
	}
	
	/**
	 * Pioche des cartes.
	 * Retourne les cartes du dessus de la pioche et les retire de la pioche
	 * @param num Nombre de cartes à piocher
	 * @return Liste des cartes piochées
	 */
	public ArrayList<Credit> piocher(int num){
		ArrayList<Credit> cartes = new ArrayList<Credit>(num);
		if (num > m_pioche.size()) {
			recupererDefausse();
		}
		for (int i = 0; i < num; i++) {
			Credit carte = m_pioche.get(0);
			m_pioche.remove(0);
			cartes.add(carte);
		}
		return cartes;
	}
	
	/**
	 * Récupère la défausse. 
	 * Remet les cartes sur la pioche et mélange.
	 */
	private void recupererDefausse() {
		m_pioche = new ArrayList<Credit>(m_defausse);
		m_defausse.clear();
		melanger();
	}
	
	/**
	 * Défausse une carte crédit
	 * Place la carte donnée au-dessous de la pioche.
	 * @param carte Carte à défausser
	 */
	public void defausser(Credit carte) {
		m_defausse.add(carte);
	}
	
	/**
	 * Défausse des cartes crédit
	 * Place les cartes données au-dessous de la pioche
	 * @param cartes Liste de cartes à défausser
	 */
	public void defausser(List<Credit> cartes) {
		for (Credit carte: cartes) {
			m_defausse.add(carte);
		}
	}
	
	/**
	 * Defausse des cartes crédit
	 * Place les cartes données au-dessous de la pioche
	 * @param cartes Cartes à défausser
	 */
	public void defausser(Credit... cartes) {
		for (Credit carte: cartes) {
			m_defausse.add(carte);
		}
	}
}
