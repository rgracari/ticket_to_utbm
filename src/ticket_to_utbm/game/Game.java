package ticket_to_utbm.game;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @file     Game.java
 * 
 * @author   Marie ASPRO et ...
 * 
 * @brief    Classe qui g�re l'ensemble du jeu.
 *
 * @details  Cette classe va initialiser le nombre de joueur, les 2 pioches dont chaque joueur aura acc�s, 
 *           mais aussi les cr�dits de couleurs et le plateau qu'il pourra visualis� par la suite. Elle est 
 *           principalement ax�es sur les actions que va pouvoir faire chaque joueur � chaque tour. 
 */

import Joueur.java;
import PiocheCursus.java;
import PiocheCredit.java;
import Credit.java;
import Plateau.java;
import Chemin.java;

public class Game {
	
	// m_listeJoueur : contient la liste de tous les joueurs participants
	List<Joueur> m_listeJoueur;
	
	// m_piocheCursus : repr�sente la pioche des Cursus (aka les destinations ou objectifs)
	PiocheCursus m_piocheCursus;
	
	// m_piocheCredit : rep�sente la pioche des Credits (aka les wagons)
	PiocheCredit m_piocheCredit;
	
	// m_creditVisible : est un tableau de 5 cases avec les 5 cartes visibles de la pioche 
	Credit[] m_creditVisible;
	
	// m_plateau : repr�sente le plateau sur lequel les joueurs jouent
	Plateau m_plateau;
	

	/**
	 * @brief      Constructeur de la classe Game
	 * @details    Cette m�thode appelle le constructeur de chaque objet utilis� dans ce fichier mais aussi la m�thode init.
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
	* @details    Cette m�thode permet de choisir le nombre de joueur, chaque nom et chaque couleur.
	* 			  Elle permet aussi de r�veller les 5 cartes visibles de la pioche.
	*/
	public void init()
	{
		// � modifier lors de la creation de l'interface graphique
		// id�e : bouton � cocher : 2 o   3 o   4 o  5 o
		// but : s�curiser le nombre de joueur : 1 < j < 6
		
		try ( Scanner scanner = new Scanner( System.in ) ) 
		{
			System.out.print("Nombre de joueur : ");
            int nb = scanner.nextInt();
		}
		
		for (int i = 0 ; i < nb ; i++)
		{
			try ( Scanner scanner = new Scanner( System.in ) ) 
			{
				System.out.print("Nom du joueur : ");
				String nom = scanner.nextLine();
	            
	            System.out.print("Couleur du joueur : ");
	            String couleur = scanner.nextLine();
			}
			
			Joueur j = new Joueur(nom, couleur);
			m_listeJoueur.add(j);
		}
		
		// /!\ bug /!\� v�rifier : la sortie de piocher est une liste et ici tableau
		for (int j = 0 ; j < 5 ; j++)
		{
			m_creditVisible[j] = m_piocheCredit.piocher(1);  
		}
		
		// cela fonctionne si on modifie le beug du dessus ? :
		//m_creditVisible = m_piocheCredit.piocher(5);	
	}
	
	
	/**
	* @brief      M�thode qui permet de prendre une ou plusieurs cartes cr�dits parmis celle qui sont face cach�es
	* @details    Au cours d'un tour de jeu, le joueur peut piocher deux cartes face cach�es ou une carte face visible 
	*             (hors cartes humanit�s) et une carte cach�e ou faire une autre action sans rapport avec cette m�thode.
	* @param      nombre         Nombre de cartes non visibles pioch�es : compris entre 1 et 2  
	* @param      joueur         Le joueur qui fait l'action
	*/
	public void piocherCreditCache(int nombre, Joueur joueur)
	{
		List<Credit> cartes = new ArrayList<Credit>();
		cartes = m_piocheCredit.piocher(nombre);
		joueur.ajouterCredits(cartes);
		
	}
	
	
	/**
	* @brief      M�thode qui permet de prendre une ou plusieurs cartes cr�dits parmis celle qui sont face visibles
	* @details    Au cours d'un tour de jeu, le joueur peut piocher deux cartes face visibles (hors cartes humanit�s, 
	*             aka locomotives), ou une carte face visible (hors cartes humanit�s) et une carte face cach�e ou faire 
	*             une autre action sans rapport avec cette m�thode.
	* @param      nombre         Nombre de cartes visibles pioch�es : compris entre 1 et 2  
	* @param      joueur         Le joueur qui fait l'action
	*/
	public void piocherCreditVisible(int nombre, Joueur joueur)
	{
		List<Credit> cartes = new ArrayList<Credit>();
		for (int i = 0; i < nombre ; i++)
		{
			//changer la m�thode avec les couleurs et l'interface graphique 
			try ( Scanner scanner = new Scanner( System.in ) ) 
			{
				System.out.print("Numero de la carte face visible : ");
				int num = scanner.nextInt();
			}
			
			// /!\ bug /!\ � v�rifier : les cartes visibles sont un tableau et on l'ajoute dans une liste
			cartes.add(m_creditVisible[num]);
			joueur.ajouterCredits(cartes);
		}
		
	}
	
	
	/**
	* @brief      M�thode qui permet de d�fausser une carte cr�dit
	* @details    Lorsque l'on souhaite prendre un chemin, il est n�cessaire de d�fausser des cr�dits. Pour cela, 
	*             le joueur de choisir de jeter une plusieurs cartes identiques. Quand plusieurs cartes seront
	*             n�cessaire, la m�thode sera appel�e plusieurs fois. 
	* @param      credit         Credit d�fauss� par un joueur  
	* @param      joueur         Le joueur qui jette un cr�dit
	*/
	public void defausserCredit(Credit credit, Joueur joueur)
	{
		//point � r�gler : ici on ne passe qu'un cr�dit 
		//dans l'objet PiocheCredit, la d�fausse prend en param�tre une liste de cr�dit 
		//l'inverse est possible si on appelle l'autre m�thode plusieurs fois mais pas dans ce sens ...
	}
	
	
	/**
	* @brief      M�thode qui permet de d�poser des ECTS et de prendre un chemin 
	* @details    Au cours d'un tour, un joueur peut d�cider de prendre un chemin et de d�poser des ECTS (aka des wagons)
	*             afin de r�ussir � terminer ses cartes cursus (aka destinations ou objectifs).
	* @param      chemin     Chemin choisi par le joueur 
	* @param      joueur     Le joueur qui prend le chemin  
	*/
	public void prendreChemin(Chemin chemin, Joueur joueur)
	{
		joueur.ajouterChemin(chemin);
	}
	
	
	/**
	* @brief      M�thode qui permet de piocher une carte Cursus
	* @details    Au d�but du jeu et au cour de la partie, un joueur doit ou peut piocher des cartes Cursus (aka destinations ou objectifs).
	* @param      nombre     Entier qui repr�sente le nombre de carte que le joueur pioche 
	* @return     Une \e liste \e de \e cursus repr�sentant la s�rie de cartes pioch�es.
	*/
	public List<Cursus> piocherCursus(int nombre)
	{
		return m_piocheCursus.piocher(nombre);
	}
	
	
	/**
	* @brief      M�thode qui d�fausse un cursus
	* @details    Lorsqu'un joueur pioche des cartes Cursus (aka destinations ou objectifs), il peut en d�fausser certaines. 
	*             Cette m�thode le lui permet.
	* @param      cursus     Cursus qui n'est pas conserv� par le joueur.
	*/
	public void defausserCursus (Cursus cursus)
	{
		//point � r�gler : ici on ne passe qu'un cursus 
		//dans l'objet PiocheCursus, la d�fausse prend en param�tre une liste de cursus 
		//l'inverse est possible si on appelle l'autre m�thode plusieurs fois mais pas dans ce sens ...
	}
	
	
	/**
	* @brief      Permet de trouver quel joueur � la chemin le plus long
	* @details    Utilisation d'un algorithme de recherche qui d�termine quel joueur a mis le plus d'ECTS
	* 			  bout � bout afin de cr�er le chemin le plus long.
	* @return     Un \e Joueur qui est celui qui remporte le point bonus.
	*/
	public Joueur cheminLePlusLong()
	{
		//algorithme que je ne m�trise pas ...
	}

}
