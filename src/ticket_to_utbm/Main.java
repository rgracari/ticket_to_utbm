package ticket_to_utbm;

import ticket_to_utbm.console.ConsoleMain;
import ticket_to_utbm.window.Window;

/**
 * Classe contenant le point d'entrée du programme si l'on souhaite l'exexucter en
 * mode release. Il est possible de configurer l'execution du programme à partir de cette classe.
 */
public class Main {
	
	/**
	 * Point d'entrée du pogramme, vous devez executer ce main si vous voulez executer
	 * le jeu en mode release.
	 * @param args les arguments passés au programme
	 */
	public static void main(String[] args) {
		//ConsoleMain.main(args);
		Window.start();
	}
}
