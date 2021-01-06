package ticket_to_utbm.game;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Collection;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Contient les informations sur un joueur
 * @author Grégori MIGNEROT
 */
public class Joueur {
	private String m_nom;                 // Nom du joueur
	private CouleurJoueur m_couleur;      // Couleur des pions du joueur
	private Hashtable<Credit, Integer> m_credits;  // Main du joueur
	private ArrayList<Cursus> m_cursus;   // Cartes cursus prises
	private ArrayList<Chemin> m_chemins;  // Chemins pris
	private int m_ects;                   // Nombre de pions ECTS (aka pions wagons) restants
	
	/**
	 * Initialise le joueur
	 * @param nom Nom du joueur
	 * @param couleur Couleur associée au joueur
	 */
	public Joueur(String nom, CouleurJoueur couleur) {
		m_nom = nom;
		m_couleur = couleur;
		init();
	}
	
	/**
	 * Réinitialise le joueur
	 */
	public void init() {
		m_credits = new Hashtable<Credit, Integer>(9);
		m_credits.put(Credit.Rouge, 0);
		m_credits.put(Credit.Orange, 0);
		m_credits.put(Credit.Jaune, 0);
		m_credits.put(Credit.Vert, 0);
		m_credits.put(Credit.Bleu, 0);
		m_credits.put(Credit.Rose, 0);
		m_credits.put(Credit.Blanc, 0);
		m_credits.put(Credit.Noir, 0);
		m_credits.put(Credit.Humanite, 0);
		m_cursus = new ArrayList<Cursus>(3);
		m_chemins = new ArrayList<Chemin>(12);
		m_ects = 45;
	}
	
	/**
	 * Donne le nom du joueur
	 * @return Nom du joueur
	 */
	public String nom() {
		return m_nom;
	}
	
	/**
	 * Donne la couleur du joueur
	 * @return Couleur du joueur
	 */
	public CouleurJoueur couleur() {
		return m_couleur;
	}
	
	/**
	 * Donne les cartes crédit de la main du joueur.
	 * Retourne une copie de la main, la modifier ne modifiera pas le contenu de Joueur
	 * @return Liste des cartes crédit du joueur
	 */
	public ArrayList<Credit> credits(){
		ArrayList<Credit> cartes = new ArrayList<Credit>();
		for (Credit couleur : m_credits.keySet()) {
			for (int i = 0; i < m_credits.get(couleur); i++) {
				cartes.add(couleur);
			}
		}
		return cartes;
	}
	
	/**
	 * Donne les cartes cursus de la main du joueur.
	 * Retourne une copie de la main, la modifier ne modifiera pas le contenu de Joueur
	 * @return Liste des cartes cursus du joueur
	 */
	public ArrayList<Cursus> cursus(){
		return new ArrayList<Cursus>(m_cursus);
	}
	
	/**
	 * Donne les chemins pris par le joueur.
	 * Retourne une copie de la liste, la modifier ne modifiera pas le contenu de Joueur
	 * @return Liste des chemins appartenant au joueur
	 */
	public ArrayList<Chemin> chemins(){
		return new ArrayList<Chemin>(m_chemins);
	}
	
	/**
	 * Renvoie le nombre de pions ECTS restant au joueur
	 * @return Nombre d'ECTS restants
	 */
	public int ectsRestants() {
		return m_ects;
	}
	
	/**
	 * Ajoute une carte crédit à la main du joueur
	 * @param carte Carte à ajouter
	 */
	public void ajouterCredit(Credit carte) {
		m_credits.put(carte, m_credits.get(carte) + 1);
	}
	
	/**
	 * Ajoute une liste de cartes crédit à la main du joueur
	 * @param cartes Cartes à ajouter
	 */
	public void ajouterCredits(Collection<Credit> cartes) {
		for (Credit carte : cartes) {
			m_credits.put(carte, m_credits.get(carte) + 1);
		}
	}
	
	/**
	 * Ajoute des cartes crédit à la main du joueur
	 * @param cartes Cartes à ajouter
	 */
	public void ajouterCredits(Credit... cartes) {
		for (Credit carte : cartes) {
			m_credits.put(carte, m_credits.get(carte) + 1);
		}
	}
	
	/**
	 * Ajoute une carte cursus à la main du joueur
	 * @param carte Carte à ajouter
	 */
	public void ajouterCursus(Cursus carte) {
		m_cursus.add(carte);
	}
	
	/**
	 * Ajoute une liste de cartes cursus à la main du joueur
	 * @param cartes Cartes à ajouter
	 */
	public void ajouterCursus(Collection<Cursus> cartes) {
		m_cursus.addAll(cartes);
	}
	
	/**
	 * Ajoute des cartes cursus à la main du joueur
	 * @param cartes Cartes à ajouter
	 */
	public void ajouterCursus(Cursus... cartes) {
		for (Cursus carte : cartes) {
			m_cursus.add(carte);
		}
	}
	
	/**
	 * Donne le nombre de cartes de la couleur demandée qu’a le joueur
	 * @param couleur Couleur demandée
	 * @return Nombre de cartes de la couleur demandée dans la main du joueur
	 */
	public int nombreCartes(Credit couleur) {
		return m_credits.get(couleur);
	}
	
	/**
	 * Retire des cartes crédit d'une certaine couleur de la main du joueur,
	 * typiquement quand il les utilise pour prendre un chemin
	 * @param couleur Couleur des cartes crédit à dépenser
	 * @param nombre Nombre de cartes à dépenser
	 * @return Liste des cartes retirées
	 * @throws NoSuchElementException S'il n'y a pas assez de cartes de la couleur demandée dans la main du joueur
	 */
	public ArrayList<Credit> retirerCredits(Credit couleur, int nombre) throws NoSuchElementException {
		int numcartes = m_credits.get(couleur);
		int numhuma = m_credits.get(Credit.Humanite);
		if (numcartes + numhuma < nombre) {
			throw new NoSuchElementException("Pas assez de cartes pour satisfaire la demande");
		}
		int cartesDepensees = Math.min(numcartes, nombre);
		int humaDepensees = nombre - cartesDepensees;
		m_credits.put(couleur, numcartes - cartesDepensees);
		m_credits.put(Credit.Humanite, numhuma - humaDepensees);
		ArrayList<Credit> cartes = new ArrayList<Credit>(nombre);
		for (int i = 0; i < cartesDepensees; i++) {
			cartes.add(couleur);
		}
		for (int i = 0; i < humaDepensees; i++) {
			cartes.add(Credit.Humanite);
		}
		return cartes;
	}
	
	/**
	 * Vérifie si le joueur peut prendre un chemin donné
	 * @param chemin Chemin à vérifier
	 * @return true si le joueur a les crédits nécessaires pour prendre le chemin, false sinon
	 */
	public boolean peutPrendre(Chemin chemin) {
		if (m_ects >= chemin.longueur()) {
			boolean possible;
			if (chemin.couleur() == Credit.Humanite) {
				int maxcartes = 0;
				// On prend la couleur dont le joueur a le plus d’exemplaire pour vérifier si le chemin gris peut être pris
				for (Credit couleur : m_credits.keySet()) {
					if (couleur != Credit.Humanite) {
						maxcartes = Math.max(m_credits.get(couleur), maxcartes);
					}
				}
				possible = maxcartes + m_credits.get(Credit.Humanite) >= chemin.longueur();
			} else {
				int numcartes = m_credits.get(chemin.couleur());
				int numhuma = m_credits.get(Credit.Humanite);
				possible = numcartes + numhuma >= chemin.longueur();
			}
			if (possible) {
				HashSet<Chemin> chemins = new HashSet<Chemin>(m_chemins);
				HashSet<UV> passe = new HashSet<UV>();
				// Évite les boucles (inutile pour le joueur, et pour le chemin le plus long il faut un graphe acyclique)
				if (routeExiste(chemins, passe, chemin.uv1(), chemin.uv2())) {
					return false;
				} else {  // Ferait une boucle
					return true;
				}
			} else {  // Pas les cartes crédits pour prendre le chemin
				return false;
			}
		} else {  // Pas assez de pions ECTS pour prendre le chemin
			return false;
		}
	}
	
	/**
	 * Donne un chemin au joueur
	 * @param chemin Chemin à ajouter
	 */
	public void ajouterChemin(Chemin chemin) {
		m_chemins.add(chemin);
		m_ects -= chemin.longueur();
	}
	
	/**
	 * Renvoie le score total du joueur, sans soustraire les cursus non accomplis
	 * @return Score du joueur
	 */
	public int score() {
		return scoreChemins() + scoreCursus();
	}
	
	/**
	 * Renvoie le score total du joueur
	 * @param fin Si true, soustrait les points des cursus non accomplis (typiquement au décompte final des points, pas avant)
	 * @return Score du joueur
	 */
	public int score(boolean fin) {
		return scoreChemins() + scoreCursus(fin);
	}
	
	/**
	 * Renvoie le nombre de points du joueur venant de ses chemins capturés
	 * @return Score des chemins
	 */
	public int scoreChemins() {
		int points = 0;
		for (Chemin chemin : m_chemins) {
			points += chemin.score();
		}
		return points;
	}
	
	/**
	 * Renvoie le nombre de points du joueur venant de ses cartes cursus
	 * @param fin Si true, soustrait la valeur des cursus non accomplis (typiquement au décompte final des points, pas avant)
	 * @return Score des cursus
	 */
	public int scoreCursus(boolean fin) {
		int points = 0;
		for (Cursus carte : m_cursus) {
			if (cursusAccompli(carte)) {
				points += carte.valeur();
			} else if (fin) {
				points -= carte.valeur();
			}
		}
		return points;
	}
	
	/**
	 * Renvoie le nombre de points du joueur venant de ses cartes cursus, sans soustraire les cursus non accomplis
	 * @return Score des cursus
	 */
	public int scoreCursus() {
		return scoreCursus(false);
	}
	
	/**
	 * Vérifie si un cursus donné a été accompli par le joueur
	 * @param carte Cursus à vérifier
	 * @return Si le cursus a été accompli
	 */
	public boolean cursusAccompli(Cursus carte) {
		HashSet<Chemin> chemins = new HashSet<Chemin>(m_chemins);
		HashSet<UV> passe = new HashSet<UV>();
		return routeExiste(chemins, passe, carte.uv1(), carte.uv2());
	}
	
	/**
	 * Cherche s'il existe une route de depart à arrivee par les chemins donnés
	 * @param chemins Chemins empruntables par la route
	 * @param passe UV déjà passées par la route
	 * @param depart Départ de la route
	 * @param arrivee But à atteindre par la route depuis le départ
	 * @return Si la route existe
	 */
	private boolean routeExiste(HashSet<Chemin> chemins, HashSet<UV> passe, UV depart, UV arrivee) {
		// Recherche relativement standard dans un arbre
		for (Chemin chemin: chemins) {
			UV suivant = null;
			if ((chemin.uv1() == arrivee && chemin.uv2() == depart) || (chemin.uv1() == depart && chemin.uv2() == arrivee)) {
				return true;  // Le chemin va directement du départ à l'arrivée, donc la route existe
			} else if (chemin.uv1() == depart && !passe.contains(chemin.uv2())) {
				suivant = chemin.uv2();
			} else if (chemin.uv2() == depart && !passe.contains(chemin.uv1())) {
				suivant = chemin.uv1();
			}
			
			// Le chemin part du départ et ne reboucle pas sur une UV déjà passée (pas grave mais ça diminue les chemins à tester),
			// donc on peut potentiellement continuer la route de celui-ci
			if (suivant != null) {
				// On continue la recherche sans ce chemin (inutile de pouvoir repasser deux fois par le même, et ça ferait des boucles infinies)
				HashSet<Chemin> subchemins = new HashSet<Chemin>(chemins);
				subchemins.remove(chemin);
				HashSet<UV> subpasse = new HashSet<UV>(passe);
				subpasse.add(depart);
				
				// On continue la recherche récursivement, en donnant le bout du chemin (supposément plus près) et l'arrivée souhaitée.
				if (routeExiste(subchemins, subpasse, suivant, arrivee)) {
					return true;
				}
			}
		}
		return false;
	}
}
