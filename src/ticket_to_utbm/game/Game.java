package ticket_to_utbm.game;

import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author   Marie ASPRO, Raphael Ribeiro
 * 
 * Classe qui gère l'ensemble du jeu.
 *
 * Cette classe va initialiser le nombre de joueurs, les 2 pioches dont chaque joueur aura accès, 
 * mais aussi les crédits de couleurs et le plateau qu'il pourra visualisé par la suite. Elle est 
 * principalement axées sur les actions que va pouvoir faire chaque joueur à chaque tour. 
 */

public class Game {
	private final int m_max_huma_visibles = 2;
	
	// m_listeJoueur : contient la liste de tous les joueurs participants
	private List<Joueur> m_listeJoueur;
	
	// m_piocheCursus : représente la pioche des Cursus (aka les destinations ou objectifs)
	private PiocheCursus m_piocheCursus;
	
	// m_piocheCredit : repésente la pioche des Credits (aka les wagons)
	private PiocheCredit m_piocheCredit;
	
	// m_creditVisible : est un tableau de 5 cases avec les 5 cartes visibles de la pioche 
	private Credit[] m_creditVisible;
	
	// m_plateau : représente le plateau sur lequel les joueurs jouent
	private Plateau m_plateau;
	

	/**
	 * Constructeur de la classe Game
	 * Cette méthode appelle le constructeur de chaque objet utilisé dans ce fichier mais aussi la méthode init.
	 */
	public Game()
	{
		m_listeJoueur = new ArrayList<Joueur>();
		m_piocheCursus = new PiocheCursus();
		m_piocheCredit = new PiocheCredit();
		m_creditVisible = new Credit[5];
		m_plateau = new Plateau();
		init();
	}
	
	
	/**
	* Initialise les joueurs et les cartes visibles
	* Cette méthode permet de choisir le nombre de joueur, chaque nom et chaque couleur.
	* Elle permet aussi de révèler les 5 cartes visibles de la pioche.
	*/
	public void init()
	{		
		m_listeJoueur.clear();
		m_piocheCursus.init();
		m_piocheCredit.init();
		m_plateau.init();
		
		for (int j = 0 ; j < 5 ; j++)
		{
			m_creditVisible[j] = m_piocheCredit.piocher();  
		}
	}
	
	/**
	 * Ajoute un joueur au jeu
	 * @param nom Nom du joueur
	 * @param couleur Couleur attribuée au joueur
	 * @return L'objet joueur créé
	 */
	public Joueur ajouterJoueur(String nom, CouleurJoueur couleur) {
		Joueur joueur = new Joueur(nom, couleur);
		m_listeJoueur.add(joueur);
		return joueur;
	}
	
	/**
	 * Donne une copie de la liste des joueurs, dans l'ordre où ils ont été ajoutés au jeu
	 * @return Copie de la liste des joueurs
	 */
	public ArrayList<Joueur> joueurs(){
		ArrayList<Joueur> copie = new ArrayList<Joueur>(m_listeJoueur);
		return copie;
	}
	
	/**
	 * Donne la carte visible à l'indice i
	 * @param i Numéro de la carte
	 * @return Carte visible à l'indice i
	 */
	public Credit creditVisible(int i){
		return m_creditVisible[i];
	}
	
	/**
	* Méthode qui permet de prendre une ou plusieurs cartes crédits parmis celle qui sont face cachées
	* Au cours d'un tour de jeu, le joueur peut piocher deux cartes face cachées ou une carte face visible 
	* (hors cartes humanités) et une carte cachée ou faire une autre action sans rapport avec cette méthode.
	* @param      nombre         Nombre de cartes non visibles piochées : compris entre 1 et 2  
	* @param      joueur         Le joueur qui fait l'action
	*/
	public void piocherCreditCache(int nombre, Joueur joueur)
	{
		List<Credit> cartes = new ArrayList<Credit>();
		cartes = m_piocheCredit.piocher(nombre);
		joueur.ajouterCredits(cartes);
	}
	
	
	/**
	* Méthode qui permet de prendre une ou plusieurs cartes crédits parmis celle qui sont face visibles
	* Au cours d'un tour de jeu, le joueur peut piocher deux cartes face visibles (hors cartes humanités, 
	* aka locomotives), ou une carte face visible (hors cartes humanités) et une carte face cachée ou faire 
	* une autre action sans rapport avec cette méthode.
	* @param      indice         la position de la carte parmi les cartes face visible
	* @param      joueur		Le joueur qui fait l'action
	* @return     true si la carte prise est une humanité, false sinon
	*/
	public boolean piocherCreditVisible(int indice, Joueur joueur)
	{
		boolean ishuma = m_creditVisible[indice] == Credit.Humanite;
		joueur.ajouterCredit(m_creditVisible[indice]);
		m_creditVisible[indice] = m_piocheCredit.piocher();
		
		int numhuma = 0;
		for (int i = 0; i < 5; i++) {
			if (m_creditVisible[i] == Credit.Humanite) numhuma += 1;
		}
		
		while (numhuma > m_max_huma_visibles) {
			numhuma = 0;
			for (int i = 0; i < 5; i++) {
				m_piocheCredit.defausser(m_creditVisible[i]);
				m_creditVisible[i] = m_piocheCredit.piocher();
				if (m_creditVisible[i] == Credit.Humanite) {
					numhuma += 1;
				}
			}
		}
		return ishuma;
	}
	
	
	/**
	* Méthode qui permet de défausser une carte crédit
	* Lorsque l'on souhaite prendre un chemin, il est nécessaire de défausser des crédits. Pour cela, 
	* le joueur de choisir de jeter une plusieurs cartes identiques. Quand plusieurs cartes seront
	* nécessaire, la méthode sera appelée plusieurs fois. 
	* @param      credit         Credit défaussé par un joueur  
	* @param      joueur         Le joueur qui jette un crédit
	*/
	public void defausserCredit(Credit credit, Joueur joueur)
	{
		joueur.retirerCredits(credit, 1);
		m_piocheCredit.defausser(credit);
	}
	
	
	/**
	* Méthode qui permet de déposer des ECTS et de prendre un chemin.
	* Au cours d'un tour, un joueur peut décider de prendre un chemin et de déposer des ECTS (aka des wagons)
	* afin de réussir à terminer ses cartes cursus (aka destinations ou objectifs).
	* Les crédits et pions ECTS associés au chemin sont retirés au joueur, mais le chemin doit déjà avoir été retiré du plateau
	* @param      chemin     Chemin choisi par le joueur 
	* @param      joueur     Le joueur qui prend le chemin  
	*/
	public void prendreChemin(Chemin chemin, Joueur joueur) {
		joueur.retirerCredits(chemin.couleur(), chemin.longueur());
		joueur.ajouterChemin(chemin);
	}
	
	/**
	 * Donne un chemin disponible à un joueur, retire le chemin du plateau, et retire les crédits et pions ECTS nécessaires au joueur.
	 * @param id ID du chemin pris
	 * @param joueur Joueur qui a pris le chemin
	 */
	public void prendreChemin(int id, Joueur joueur) {
		Chemin chemin = m_plateau.prendre(id);
		joueur.retirerCredits(chemin.couleur(), chemin.longueur());
		joueur.ajouterChemin(chemin);
	}
	
	/**
	 * Donne un chemin disponible à un joueur, retire le chemin du plateau, et retire les crédits et pions ECTS nécessaires au joueur.
	 * @param id ID du chemin pris
	 * @param joueur Joueur qui a pris le chemin
	 * @param couleur le type de chemin peut être spécifié
	 */
	public void prendreCheminHuma(int id, Joueur joueur, Credit couleur) {
		Chemin chemin = m_plateau.prendre(id);
		joueur.retirerCredits(couleur, chemin.longueur());
		joueur.ajouterChemin(chemin);
	}
	
	
	/**
	* Méthode qui permet de piocher une carte Cursus
	* Au début du jeu et au cour de la partie, un joueur doit ou peut piocher des cartes Cursus (aka destinations ou objectifs).
	* @param      nombre     Entier qui représente le nombre de carte que le joueur pioche 
	* @return     Une \e liste \e de \e cursus représentant la série de cartes piochées.
	*/
	public ArrayList<Cursus> piocherCursus(int nombre)
	{
		return m_piocheCursus.piocher(nombre);
	}
	
	
	/**
	* Méthode qui défausse un cursus
	* Lorsqu'un joueur pioche des cartes Cursus (aka destinations ou objectifs), il peut en défausser certaines. 
	* Cette méthode le lui permet.
	* @param      cursus     Cursus qui n'est pas conservé par le joueur.
	*/
	public void defausserCursus (Cursus cursus)
	{
		m_piocheCursus.defausser(cursus);
	}
	
	/**
	 * Donne la liste des chemins qu'un joueur peut prendre avec sa main actuelle
	 * @param joueur Joueur concerné
	 * @return Liste des chemins que le joueur peut prendre en l'état
	 */
	public ArrayList<Chemin> cheminsPossibles(Joueur joueur){
		ArrayList<Chemin> prenables = new ArrayList<Chemin>();
		for (Chemin chemin : m_plateau.chemins()) {
			if (joueur.peutPrendre(chemin)) {
				prenables.add(chemin);
			}
		}
		return prenables;
	}
	
	/**
	 * Renvoie le chemin avec l’ID donné (sans le retirer)
	 * @param id ID du chemin à récupérer
	 * @return Le chemin demandé
	 */
	public Chemin chemin(int id) {
		return m_plateau.chemin(id);
	}
	
	/**
	 * Donne si le chemin avec l'ID donné est toujours disponible
	 * @param id ID à chercher
	 * @return true si le chemin est disponible, false sinon
	 */
	public boolean cheminDisponible(int id) {
		return m_plateau.disponible(id);
	}
	
	
	/**
	* Permet de trouver quel joueur à la chemin le plus long
	* Utilisation d'un algorithme de recherche en profondeur qui détermine quel joueur a mis le plus d'ECTS
	* bout à bout afin de créer le chemin le plus long.
	* @return     Un \e Joueur qui est celui qui remporte le point bonus.
	*/
	public Joueur cheminLePlusLong()
	{
		int maxi = 0;
		Joueur proprietaire = null;
		for (Joueur joueur : m_listeJoueur) {
			int maxjoueur = cheminLePlusLong(joueur);
			if (maxjoueur > maxi) {
				proprietaire = joueur;
				maxi = maxjoueur;
			}
		}
		return proprietaire;
	}
	
	/**
	 * Trouve la longueur du chemin le plus long appartenant au joueur
	 * @param joueur Joueur concerné
	 * @return Longueur du plus long chemin trouvé
	 */
	private int cheminLePlusLong(Joueur joueur) {
		ArrayList<Chemin> chemins = joueur.chemins();
		// Ensemble des sommets du graphe (avec seulement les extrémités de chemins appartenant au joueur, pas la peine de faire plus)
		HashSet<UV> uvs = new HashSet<UV>();
		for (Chemin chemin : chemins) {
			uvs.add(chemin.uv1());
			uvs.add(chemin.uv2());
		}
		
		int longueurmax = Integer.MIN_VALUE;
		for (UV source : uvs) {
			// Pour chaque UV source, on fait un Dijkstra à l’envers
			// Avec -longueur au lieu de +longueur pour tous les chemins, ça calcule le plus long chemin au lieu du plus court
			// On a assuré dans Joueur.peutPrendre que le graphe sera acyclique, donc c’est bon
			LinkedList<UV> file = new LinkedList<UV>();
			Hashtable<UV, Integer> distances = new Hashtable<UV, Integer>(uvs.size());
			HashSet<UV> passes = new HashSet<UV>(uvs.size());  // Ensemble des sommets déjà passés
			
			file.add(source);
			distances.put(source, 0);
			while (file.size() > 0) {
				UV point = file.remove();
				passes.add(point);
				for (Chemin chemin : chemins) {
					UV voisin;
					if (chemin.uv1() == point) {
						voisin = chemin.uv2();
					} else if (chemin.uv2() == point) {
						voisin = chemin.uv1();
					} else {
						continue;
					}
					if (!passes.contains(voisin)) {
						file.add(voisin);
						int nouvelledist = distances.get(point) - chemin.longueur();
						if (!distances.contains(voisin) || nouvelledist < distances.get(voisin)) {
							distances.put(voisin, nouvelledist);
						}
					}
				}
			}
			int mindist = Integer.MAX_VALUE;
			for (int dist : distances.values()) {
				mindist = Math.min(mindist, dist);
			}
			longueurmax = Math.max(longueurmax, -mindist);
		}
		return longueurmax;
	}
}
