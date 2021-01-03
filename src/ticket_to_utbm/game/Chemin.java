package ticket_to_utbm.game;

/**
 * Représente une route sur le plateau
 * @author Grégori MIGNEROT
 */
public class Chemin {
	private int m_id;
	private UV m_uv1;
	private UV m_uv2;
	private int m_longueur;
	private Credit m_couleur;
	private final int[] m_scores = {0, 1, 2, 4, 7, 10, 15};
	
	/**
	 * Initialise le chemin
	 * @param id Identifiant unique du chemin
	 * @param uv1 Première extrémité
	 * @param uv2 Seconde extrémité
	 * @param longueur Nombre de crédits nécessaires pour couvrir le chemin
	 * @param couleur Couleur des crédits nécessaires pour couvrir le chemin (humanité pour les chemins gris)
	 */
	public Chemin(int id, UV uv1, UV uv2, int longueur, Credit couleur) {
		m_id = id;
		m_uv1 = uv1;
		m_uv2 = uv2;
		m_longueur = longueur;
		m_couleur = couleur;
	}
	
	/**
	 * Accesseur pour l'identifiant unique du chemin
	 * @return Identifiant du chemin
	 */
	public int id() {
		return m_id;
	}
	
	/**
	 * Accesseur pour la première extrémité du chemin
	 * @return Première UV reliée par le chemin
	 */
	public UV uv1() {
		return m_uv1;
	}
	
	/**
	 * Accesseur pour la seconde extrémité du chemin
	 * @return Seconde UV reliée par le chemin
	 */
	public UV uv2() {
		return m_uv2;
	}
	
	/**
	 * Accesseur pour la taille du chemin
	 * @return Longueur du chemin en credits
	 */
	public int longueur() {
		return m_longueur;
	}
	
	/**
	 * Accesseur pour la couleur du chemin
	 * @return Couleur des crédits nécessaires pour prendre le chemin (humanité pour gris)
	 */
	public Credit couleur(){
		return m_couleur;
	}
	
	/**
	 * Donne les points que rapporte le chemin s'il est pris
	 * @return Valeur du chemin en points
	 */
	public int score() {
		return m_scores[m_longueur];
	}
}
