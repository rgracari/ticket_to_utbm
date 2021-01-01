package ticket_to_utbm.game;

/**
 * Représente une carte cursus (aka destination)
 * @author Grégori MIGNEROT
 */
public class Cursus {
	private UV m_uv1;
	private UV m_uv2;
	private int m_valeur;
	
	/**
	 * Initialise la carte
	 * @param uv1 Première extrémité du cursus
	 * @param uv2 Deuxième extrémité
	 * @param valeur Valeur en points de la carte
	 */
	public Cursus(UV uv1, UV uv2, int valeur) {
		m_uv1 = uv1;
		m_uv2 = uv2;
		m_valeur = valeur;
	}
	
	/**
	 * Accesseur pour l'extrémité 1
	 * @return Extrémité 1 du parcours
	 */
	public UV uv1() {
		return m_uv1;
	}
	
	/**
	 * Accesseur pour l'extrémité 2
	 * @return Extrémité 2 du parcours
	 */
	public UV uv2() {
		return m_uv2;
	}
	
	/**
	 * Accesseur pour la valeur du cursus
	 * @return Valeur du cursus en points
	 */
	public int valeur() {
		return m_valeur;
	}
}
