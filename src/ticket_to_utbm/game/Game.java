package ticket_to_utbm.game;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * @file     Game.java
 * 
 * @author   Marie ASPRO et ...
 * 
 * @brief    Classe qui gère l'ensemble du jeu.
 *
 * @details  Cette classe va initialiser le nombre de joueur, les 2 pioches dont chaque joueur aura accès, 
 *           mais aussi les crédits de couleurs et le plateau qu'il pourra visualisé par la suite. Elle est 
 *           principalement axées sur les actions que va pouvoir faire chaque joueur à chaque tour. 
 */

public class Game {
	
	// m_listeJoueur : contient la liste de tous les joueurs participants
	List<Joueur> m_listeJoueur;
	
	// m_piocheCursus : représente la pioche des Cursus (aka les destinations ou objectifs)
	PiocheCursus m_piocheCursus;
	
	// m_piocheCredit : repésente la pioche des Credits (aka les wagons)
	PiocheCredit m_piocheCredit;
	
	// m_creditVisible : est un tableau de 5 cases avec les 5 cartes visibles de la pioche 
	Credit[] m_creditVisible;
	
	// m_plateau : représente le plateau sur lequel les joueurs jouent
	Plateau m_plateau;
	

	/**
	 * @brief      Constructeur de la classe Game
	 * @details    Cette méthode appelle le constructeur de chaque objet utilisé dans ce fichier mais aussi la méthode init.
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
	* @brief      Initialise les joueurs et les cartes visibles
	* @details    Cette méthode permet de choisir le nombre de joueur, chaque nom et chaque couleur.
	* 	      Elle permet aussi de révèler les 5 cartes visibles de la pioche.
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
	* @brief      Méthode qui permet de prendre une ou plusieurs cartes crédits parmis celle qui sont face cachées
	* @details    Au cours d'un tour de jeu, le joueur peut piocher deux cartes face cachées ou une carte face visible 
	*             (hors cartes humanités) et une carte cachée ou faire une autre action sans rapport avec cette méthode.
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
	* @brief      Méthode qui permet de prendre une ou plusieurs cartes crédits parmis celle qui sont face visibles
	* @details    Au cours d'un tour de jeu, le joueur peut piocher deux cartes face visibles (hors cartes humanités, 
	*             aka locomotives), ou une carte face visible (hors cartes humanités) et une carte face cachée ou faire 
	*             une autre action sans rapport avec cette méthode.
	* @param      nombre         Nombre de cartes visibles piochées : compris entre 1 et 2  
	* @param      joueur         Le joueur qui fait l'action
	*/
	public void piocherCreditVisible(int indice, Joueur joueur)
	{
		joueur.ajouterCredit(m_creditVisible[indice]);
		m_creditVisible[indice] = m_piocheCredit.piocher();
	}
	
	
	/**
	* @brief      Méthode qui permet de défausser une carte crédit
	* @details    Lorsque l'on souhaite prendre un chemin, il est nécessaire de défausser des crédits. Pour cela, 
	*             le joueur de choisir de jeter une plusieurs cartes identiques. Quand plusieurs cartes seront
	*             nécessaire, la méthode sera appelée plusieurs fois. 
	* @param      credit         Credit défaussé par un joueur  
	* @param      joueur         Le joueur qui jette un crédit
	*/
	public void defausserCredit(Credit credit, Joueur joueur)
	{
		//point à vérifier : retirerCredits renvoi une liste de cartes Credits 
		joueur.retirerCredits(credit, 1);
		m_piocheCredit.defausser(credit);
	}
	
	
	/**
	* @brief      Méthode qui permet de déposer des ECTS et de prendre un chemin 
	* @details    Au cours d'un tour, un joueur peut décider de prendre un chemin et de déposer des ECTS (aka des wagons)
	*             afin de réussir à terminer ses cartes cursus (aka destinations ou objectifs).
	* @param      chemin     Chemin choisi par le joueur 
	* @param      joueur     Le joueur qui prend le chemin  
	*/
	public void prendreChemin(Chemin chemin, Joueur joueur)
	{
		joueur.ajouterChemin(chemin);
	}
	
	
	/**
	* @brief      Méthode qui permet de piocher une carte Cursus
	* @details    Au début du jeu et au cour de la partie, un joueur doit ou peut piocher des cartes Cursus (aka destinations ou objectifs).
	* @param      nombre     Entier qui représente le nombre de carte que le joueur pioche 
	* @return     Une \e liste \e de \e cursus représentant la série de cartes piochées.
	*/
	public List<Cursus> piocherCursus(int nombre)
	{
		return m_piocheCursus.piocher(nombre);
	}
	
	
	/**
	* @brief      Méthode qui défausse un cursus
	* @details    Lorsqu'un joueur pioche des cartes Cursus (aka destinations ou objectifs), il peut en défausser certaines. 
	*             Cette méthode le lui permet.
	* @param      cursus     Cursus qui n'est pas conservé par le joueur.
	*/
	public void defausserCursus (Cursus cursus)
	{
		m_piocheCursus.defausser(cursus);
	}
	
	
	/**
	* @brief      Permet de trouver quel joueur à la chemin le plus long
	* @details    Utilisation d'un algorithme de recherche en profondeur qui détermine quel joueur a mis le plus d'ECTS
	* 	      bout à bout afin de créer le chemin le plus long.
	* @return     Un \e Joueur qui est celui qui remporte le point bonus.
	*/
	public Joueur cheminLePlusLong()
	{
		int maxi = 0;
		Joueur proprietaire = null;
		for (Joueur joueur : m_listeJoueur) {
			HashSet<Chemin> passe = new HashSet<Chemin>();
			int maxjoueur = cheminLePlusLong(joueur, passe);
			if (maxjoueur > maxi) {
				proprietaire = joueur;
				maxi = maxjoueur;
			}
		}
		return proprietaire;
	}
	
	private int cheminLePlusLong(Joueur joueur, HashSet<Chemin> passe) {
		// TODO ?
	}

}
