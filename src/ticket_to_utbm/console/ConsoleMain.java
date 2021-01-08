package ticket_to_utbm.console;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Hashtable;
import ticket_to_utbm.game.Game;
import ticket_to_utbm.game.Joueur;
import ticket_to_utbm.game.CouleurJoueur;
import ticket_to_utbm.game.Credit;
import ticket_to_utbm.game.Cursus;
import ticket_to_utbm.game.Chemin;

/**
 * Classe qui permet l'affichage du jeu dans le terminal. Elle a été conçu de façon
 * complètement découplée de la logique interne du jeu. On peut considérer cette classe
 * comme une view sur la game logic.
 * 
 * L'utilisateur peut interagir avec les flots standards afin de jouer au jeu.
 */

public class ConsoleMain {
	private Game m_game;
	private static Scanner scan;  // Solution pour éviter d’avoir plusieurs scans sur le même stream en même temps (cause des gros problèmes)
	
	/**
	 * Initialisation des prérequis permettant l'exécution du jeu en console.
	 * Les joueurs sont créés, la logique interne est initialisée et la game loop est lancée.
	 * Le programme s'arrête lorsque la partie est arrivée a son dernier tour.
	 */
	public ConsoleMain() {
		m_game = new Game();
		
		System.out.println("Ticket to UTBM\n=========================");
		int nbjoueurs = -1;
		while (nbjoueurs < 2 || nbjoueurs > 5) {
			System.out.print("Nombre de joueurs : ");
			nbjoueurs = scan.nextInt();
			scan.nextLine();
		}
		for (int i = 0; i < nbjoueurs; i++) {  // Paramétrage des joueurs (nom + couleur)
			System.out.print("Nom du joueur " + (i + 1) + " : ");
			String nom = scan.nextLine();
			int idcouleur = -1;
			while (idcouleur < 1 || idcouleur > 5) {
				System.out.print("1 - Rouge\n2 - Vert\n3 - Bleu\n4 - Jaune\n5 - Noir\nCouleur : ");
				idcouleur = scan.nextInt();
				scan.nextLine();
			}
			CouleurJoueur couleur = null;
			switch (idcouleur) {
				case 1 : couleur = CouleurJoueur.Rouge; break;
				case 2 : couleur = CouleurJoueur.Vert; break;
				case 3 : couleur = CouleurJoueur.Bleu; break;
				case 4 : couleur = CouleurJoueur.Jaune; break;
				case 5 : couleur = CouleurJoueur.Noir; break;
			}
			m_game.ajouterJoueur(nom, couleur);
		}
		
		for (Joueur joueur : m_game.joueurs()) {  // Initialisation de la main des joueurs (premiers cursus + 4 crédits)
			System.out.println("\n\nJoueur " + joueur.nom());
			prendreCursus(joueur);
			m_game.piocherCreditCache(4, joueur);
		}
		
		jeu();
	}
	
	/**
	 * Fonction principale du jeu, appelée une fois que le système de jeu et les joueurs sont initialisés
	 */
	private void jeu() {
		boolean continuer = true;
		int derniertour = -1;  // -1 tant que le jeu tourne, numéro du dernier tour une fois qu’un joueur n’a plus que 2 ECTS ou moins
		int tour = 1;  // Tour actuel
		ArrayList<Joueur> joueurs = m_game.joueurs();
		while (continuer) {
			for (Joueur joueur : joueurs) {
				// Affichage des infos du joueur et des cartes crédits visibles
				System.out.println("\n\n============\n============\nTour " + tour + " : " + joueur.nom());
				System.out.println("\nReste " + joueur.ectsRestants() + " ECTS");
				System.out.println("Score actuel : " + joueur.score());
				System.out.println("\nMain :");
				for (Credit carte : joueur.credits()) {
					afficherCredit(carte);
					System.out.print("  ");
				}
				System.out.println("\n");
				for (Cursus carte : joueur.cursus()) {
					afficherCursus(carte);
					if (joueur.cursusAccompli(carte)) {
						System.out.println(" [OK]");
					} else {
						System.out.println();
					}
				}
				
				System.out.println("\nPlateau");
				for (int i = 0; i < 5; i++) {
					afficherCredit(m_game.creditVisible(i));
					System.out.print("  ");
				}
				System.out.println();
				
				// Sélection de l’action
				boolean fait = false;
				int action;
				while (!fait) {
					action = 0;
					while (action < 1 || action > 3) {
						System.out.print("1 - Piocher\n2 - Prendre un chemin\n3 - Prendre des cartes cursus\nAction : ");
						action = scan.nextInt();
						scan.nextLine();
					}
					
					switch (action) {
						case 1: fait = piocher(joueur); break;
						case 2: fait = prendreChemin(joueur); break;
						case 3: fait = prendreCursus(joueur); break;
					}
				}
				if (joueur.ectsRestants() <= 2 && derniertour < 0) {  // Le joueur a moins de 2 ECTS : dernier tour
					derniertour = tour + 1;
					System.out.println("------------\nLe prochain tour sera le dernier !\n------------");
				}
			}
			if (tour == derniertour) {
				continuer = false;
			}
			tour += 1;
		}
		
		// Calcul des scores finaux
		Hashtable<CouleurJoueur, Integer> scores = new Hashtable<CouleurJoueur, Integer>();
		for (Joueur joueur : joueurs) {
			scores.put(joueur.couleur(), joueur.score(true));
		}
		Joueur pluslong = m_game.cheminLePlusLong();
		scores.put(pluslong.couleur(), scores.get(pluslong.couleur()) + 10);
		
		Joueur gagnant = null;
		int maxscore = -1;
		for (Joueur joueur : joueurs) {
			System.out.print(joueur.nom());
			int score = scores.get(joueur.couleur());
			System.out.println(" : " + score + " points");
			if (score > maxscore) {
				maxscore = score;
				gagnant = joueur;
			}
		}
		System.out.print("\n" + gagnant.nom());
		System.out.print(" a gagné");
	}
	
	/**
	 * Action de piocher pour un joueur
	 * @param joueur Joueur concerné
	 * @return true si l’action est effectuée, false si retour arrière
	 */
	private boolean piocher(Joueur joueur) {
		int action = -1;
		while (action < 0 || action > 5) {
			System.out.print("0 - Face cachée\n1-5 : Face visible\nChoix : ");
			action = scan.nextInt();
		}
		boolean humavisible = false;
		if (action == 0) {
			m_game.piocherCreditCache(1, joueur);
		} else {
			humavisible = m_game.piocherCreditVisible(action - 1, joueur);
		}
		
		// On réaffiche la main
		System.out.println("\nMain :");
		for (Credit carte : joueur.credits()) {
			afficherCredit(carte);
			System.out.print("  ");
		}
		System.out.println();
		
		System.out.println("\nPlateau :");
		for (int i = 0; i < 5; i++) {
			afficherCredit(m_game.creditVisible(i));
			System.out.print("  ");
		}
		System.out.println();
		
		// Carte suivante
		if (!humavisible) {  // Si le joueur a pris une humanité visible il ne peut pas en reprendre une
			humavisible = false;
			do {
				action = -1;
				while (action < 0 || action > 5) {
					System.out.print("0 - Face cachée\n1-5 : Face visible\nChoix : ");
					action = scan.nextInt();
				}
				humavisible = false;
				if (action == 0) {
					m_game.piocherCreditCache(1, joueur);
				} else {
					if (m_game.creditVisible(action - 1) == Credit.Humanite) {  // La deuxième carte piochée ne peut pas être une huma visible
						humavisible = true;
						System.out.println("Vous ne pouvez pas piocher plus d'une humanité visible");
					} else {
						m_game.piocherCreditVisible(action - 1, joueur);
					}
				}
			} while (humavisible);
		}
		
		return true;
	}
	
	/**
	 * Action de prendre un chemin
	 * @param joueur Joueur concerné
	 * @return true si l’action est effectuée, false pour retour arrière
	 */
	private boolean prendreChemin(Joueur joueur) {
		// Affichage des chemins appartenant déjà au joueur
		if (joueur.chemins().size() > 0) {
			System.out.println("Chemins déjà pris : ");
			for (Chemin chemin : joueur.chemins()) {
				System.out.print(chemin.uv1() + " <-> " + chemin.uv2() + " : " + chemin.longueur() + " ");
				afficherCredit(chemin.couleur());
				System.out.println();
			}
		}
		
		// Affichage que le joueur peut prendre avec les crédits et les pions ECTS qu’il a
		int action;
		ArrayList<Chemin> prenables = m_game.cheminsPossibles(joueur);
		System.out.println("Chemins prenables :");
		for (Chemin chemin : prenables) {
			System.out.print(chemin.id() + " - " + chemin.uv1() + " <-> " + chemin.uv2() + " : " + chemin.longueur() + " ");
			afficherCredit(chemin.couleur());
			System.out.println();
		}
		System.out.println("0 - Retour");
		
		do {
			System.out.print("Choix : ");
			action = scan.nextInt();
			scan.nextLine();
		} while (action != 0 && !m_game.cheminDisponible(action));
		
		if (action == 0) {
			return false;
		} else if (m_game.chemin(action).couleur() == Credit.Humanite) {
			// Sélection de la couleur à utiliser pour prendre un chemin gris
			int numhuma = joueur.nombreCartes(Credit.Humanite);
			int longueur = m_game.chemin(action).longueur();
			int mincartes = longueur - numhuma;
			System.out.println("Choisissez la couleur à utiliser pour prendre le chemin gris :");
			int numcartes;
			if ((numcartes = joueur.nombreCartes(Credit.Rouge)) >= mincartes) {
				System.out.println("1 - Rouge (" + Math.min(longueur, numcartes) + " + " + Math.max(longueur - numcartes, 0) + " huma)");
			}
			if ((numcartes = joueur.nombreCartes(Credit.Orange)) >= mincartes) {
				System.out.println("2 - Orange (" + Math.min(longueur, numcartes) + " + " + Math.max(longueur - numcartes, 0) + " huma)");
			}
			if ((numcartes = joueur.nombreCartes(Credit.Jaune)) >= mincartes) {
				System.out.println("3 - Jaune (" + Math.min(longueur, numcartes) + " + " + Math.max(longueur - numcartes, 0) + " huma)");
			}
			if ((numcartes = joueur.nombreCartes(Credit.Vert)) >= mincartes) {
				System.out.println("4 - Vert (" + Math.min(longueur, numcartes) + " + " + Math.max(longueur - numcartes, 0) + " huma)");
			}
			if ((numcartes = joueur.nombreCartes(Credit.Bleu)) >= mincartes) {
				System.out.println("5 - Bleu (" + Math.min(longueur, numcartes) + " + " + Math.max(longueur - numcartes, 0) + " huma)");
			}
			if ((numcartes = joueur.nombreCartes(Credit.Rose)) >= mincartes) {
				System.out.println("6 - Rose (" + Math.min(longueur, numcartes) + " + " + Math.max(longueur - numcartes, 0) + " huma)");
			}
			if ((numcartes = joueur.nombreCartes(Credit.Blanc)) >= mincartes) {
				System.out.println("7 - Blanc (" + Math.min(longueur, numcartes) + " + " + Math.max(longueur - numcartes, 0) + " huma)");
			}
			if ((numcartes = joueur.nombreCartes(Credit.Noir)) >= mincartes) {
				System.out.println("8 - Noir (" + Math.min(longueur, numcartes) + " + " + Math.max(longueur - numcartes, 0) + " huma)");
			}
			int indexCouleur = -1;
			while (indexCouleur < 1 || indexCouleur > 8) {
				System.out.print("Couleur : ");
				indexCouleur = scan.nextInt();
				scan.nextLine();
			}
			Credit couleur = null;
			switch (indexCouleur) {
				case 1 : couleur = Credit.Rouge; break;
				case 2 : couleur = Credit.Orange; break;
				case 3 : couleur = Credit.Jaune; break;
				case 4 : couleur = Credit.Vert; break;
				case 5 : couleur = Credit.Bleu; break;
				case 6 : couleur = Credit.Rose; break;
				case 7 : couleur = Credit.Blanc; break;
				case 8 : couleur = Credit.Noir; break;
			}
			m_game.prendreCheminHuma(action, joueur, couleur);
			return true;
		} else {
			m_game.prendreChemin(action, joueur);
			return true;
		}
	}
	
	/**
	 * Action de prendre de nouvelle cartes cursus
	 * @param joueur Joueur concernée
	 * @return true si l’action est effectuée, false pour retour arrière
	 */
	private boolean prendreCursus(Joueur joueur) {
		// Affichage des chemins appartenant déjà au joueur
		if (joueur.chemins().size() > 0) {
			System.out.println("Chemins déjà pris : ");
			for (Chemin chemin : joueur.chemins()) {
				System.out.print(chemin.uv1() + " <-> " + chemin.uv2() + " : " + chemin.longueur() + " ");
				afficherCredit(chemin.couleur());
				System.out.println();
			}
		}
		
		ArrayList<Cursus> cartes = m_game.piocherCursus(3);
		for (int i = 0; i < 3; i++) {
			System.out.print((i + 1) + " - ");
			afficherCursus(cartes.get(i));
			System.out.println();
		}
		
		// Sélection des cartes à défausser
		String nums;
		boolean invalide = true;
		ArrayList<Integer> poubelle = new ArrayList<Integer>();
		while (invalide) {
			poubelle.clear();
			System.out.println("Choisissez les cartes à défausser, maximum 2 cartes (indiquez les numéros des cartes à défausser, comme 12 ou 2, ou rien si vous voulez toutes les garder)");
			System.out.print(": ");
			nums = scan.nextLine();
			if (nums.length() <= 2) {
				boolean indexvalides = true;
				for (int i = 0; i < nums.length(); i++) {
					int index = Integer.parseInt(nums.substring(i, i + 1)) - 1;
					poubelle.add(index);
					if (index > 2 || index < 0) {
						indexvalides = false;
					}
				}
				if (indexvalides) {
					invalide = false;
				}
			}
		}
		for (int i = 0; i < 3; i++) {
			if (poubelle.contains(i)) {
				m_game.defausserCursus(cartes.get(i));  // Les cartes sélectionnées sont défaussées
			} else {
				joueur.ajouterCursus(cartes.get(i));  // Ajoutées à la main du joueur sinon
			}
		}
		return true;
	}
	
	/**
	 * Affiche la couleur d’un crédit sous forme d’un texte
	 * @param credit Couleur à écrire
	 */
	private void afficherCredit(Credit credit) {
		switch (credit) {
			case Humanite: System.out.print("Huma  "); break;
			case Rouge: System.out.print("Rouge "); break;
			case Bleu: System.out.print("Bleu  "); break;
			case Vert: System.out.print("Vert  "); break;
			case Jaune: System.out.print("Jaune "); break;
			case Orange: System.out.print("Orange"); break;
			case Rose: System.out.print("Rose  "); break;
			case Blanc: System.out.print("Blanc "); break;
			case Noir: System.out.print("Noir  "); break;
		}
	}
	
	/**
	 * Afficher une carte cursus sous forme d’un texte
	 * @param carte Carte crédit à écrire
	 */
	private void afficherCursus(Cursus carte) {
		System.out.print(carte.uv1());
		System.out.print(" <-> ");
		System.out.print(carte.uv2());
		System.out.print("  : (" + carte.valeur() + ")");
	}
	
	/**
	 * Point d’entrée de la version console
	 * @param args Arguments en ligne de commande (ignorés)
	 */
	public static void main(String[] args) {
		scan = new Scanner(System.in);
		new ConsoleMain();
		scan.close();
	}

}
