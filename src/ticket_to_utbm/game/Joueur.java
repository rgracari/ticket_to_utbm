package ticket_to_utbm.game;

import java.util.HashSet;
import java.util.Collection;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Contient les informations sur un joueur
 * @author Grégori MIGNEROT
 */
public class Joueur {
	private String m_name;
	private CouleurJoueur m_couleur;
	private ArrayList<Credit> m_credits;
	private ArrayList<Cursus> m_cursus;
	private ArrayList<Chemin> m_chemins;
	private int m_ects;
	
	/**
	 * Initialise le joueur
	 * @param name Nom du joueur
	 * @param couleur Couleur associée au joueur
	 */
	public Joueur(String name, CouleurJoueur couleur) {
		m_name = name;
		m_couleur = couleur;
		init();
	}
	
	/**
	 * Réinitialise le joueur
	 */
	public void init() {
		m_credits = new ArrayList<Credit>(6);
		m_cursus = new ArrayList<Cursus>(3);
		m_chemins = new ArrayList<Chemin>(12);
		m_ects = 45;
	}
	
	/**
	 * Donne le nom du joueur
	 * @return Nom du joueur
	 */
	public String name() {
		return m_name;
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
		return new ArrayList<Credit>(m_credits);
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
		m_credits.add(carte);
	}
	
	/**
	 * Ajoute une liste de cartes crédit à la main du joueur
	 * @param cartes Cartes à ajouter
	 */
	public void ajouterCredits(Collection<Credit> cartes) {
		m_credits.addAll(cartes);
	}
	
	/**
	 * Ajoute des cartes crédit à la main du joueur
	 * @param cartes Cartes à ajouter
	 */
	public void ajouterCredits(Credit... cartes) {
		for (Credit carte : cartes) {
			m_credits.add(carte);
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
	 * Retire une carte crédit de la main du joueur
	 * @param index Index de la carte dans la main
	 * @return Carte retirée
	 */
	public Credit retirerCredit(int index) {
		Credit carte = m_credits.get(index);
		m_credits.remove(index);
		return carte;
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
		// On doit vérifier s'il y a bien le nombre de cartes avant de commencer à les extraire, donc on récupère leur indice
		ArrayList<Integer> indices = new ArrayList<Integer>(nombre);
		// On itère dans l'ordre inverse pour pouvoir les retirer facilement de la liste plus tard
		for (int i = m_credits.size() - 1; (i >= 0 && indices.size() < nombre); i++) {
			if (m_credits.get(i) == couleur) {
				indices.add(i);
			}
		}
		
		if (indices.size() < nombre) {
			throw new NoSuchElementException("Pas assez de cartes de la bonne couleur dans la main du joueur");
		}
		
		ArrayList<Credit> cartes = new ArrayList<Credit>(nombre);
		// Comme les indices vont de la fin vers le début, 
		// on peut les supprimer dans l'ordre sans crainte de changer les indices suivants
		for (int index : indices) {
			cartes.add(m_credits.get(index));
			m_credits.remove(index);
		}
		return cartes;
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
	 * Vérifie si un cursus donnée a été accompli par le joueur
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
