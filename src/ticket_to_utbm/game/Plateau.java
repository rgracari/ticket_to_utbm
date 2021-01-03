package ticket_to_utbm.game;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Gère le plateau, donc la liste des chemins
 * @author Grégori MIGNEROT
 */
public class Plateau {
	private Hashtable<Integer, Chemin> m_chemins;
	private final int m_nb_chemins = 100;
	
	/**
	 * Initialise le plateau
	 */
	public Plateau() {
		init();
	}
	
	/**
	 * Réinitialise le plateau en reconstruisant la liste des chemins
	 */
	public void init() {
		m_chemins = new Hashtable<Integer, Chemin>(m_nb_chemins);
		m_chemins.put(0, new Chemin(0, UV.HT01, UV.PH02, 1, Credit.Humanite));
		m_chemins.put(1, new Chemin(1, UV.HT01, UV.PH02, 1, Credit.Humanite));
		m_chemins.put(2, new Chemin(2, UV.PH02, UV.EC02, 1, Credit.Humanite));
		m_chemins.put(3, new Chemin(3, UV.PH02, UV.EC02, 1, Credit.Humanite));
		m_chemins.put(4, new Chemin(4, UV.HT01, UV.HE09, 3, Credit.Humanite));
		m_chemins.put(5, new Chemin(5, UV.PH02, UV.HE09, 4, Credit.Humanite));
		m_chemins.put(6, new Chemin(6, UV.EC02, UV.ST40, 5, Credit.Vert));
		m_chemins.put(7, new Chemin(7, UV.EC02, UV.ST40, 5, Credit.Rose));
		m_chemins.put(8, new Chemin(8, UV.ST40, UV.SP07, 3, Credit.Jaune));
		m_chemins.put(9, new Chemin(9, UV.ST40, UV.SP07, 3, Credit.Rose));
		m_chemins.put(10, new Chemin(10, UV.SP07, UV.SP03, 2, Credit.Humanite));
		m_chemins.put(11, new Chemin(11, UV.ST40, UV.IA41, 5, Credit.Blanc));
		m_chemins.put(12, new Chemin(12, UV.ST40, UV.IA41, 5, Credit.Orange));
		m_chemins.put(13, new Chemin(13, UV.SP03, UV.IA41, 3, Credit.Orange));
		m_chemins.put(14, new Chemin(14, UV.EC02, UV.IA41, 6, Credit.Bleu));
		m_chemins.put(15, new Chemin(15, UV.PH02, UV.AP4A, 6, Credit.Jaune));
		m_chemins.put(16, new Chemin(16, UV.HE09, UV.AP4A, 4, Credit.Humanite));
		m_chemins.put(17, new Chemin(17, UV.IA41, UV.AP4A, 3, Credit.Rose));
		m_chemins.put(18, new Chemin(18, UV.IA41, UV.IT40, 3, Credit.Rouge));
		m_chemins.put(19, new Chemin(19, UV.IA41, UV.IT40, 3, Credit.Jaune));
		m_chemins.put(20, new Chemin(20, UV.AP4A, UV.IT40, 4, Credit.Vert));
		m_chemins.put(21, new Chemin(21, UV.SP07, UV.GE07, 3, Credit.Humanite));
		m_chemins.put(22, new Chemin(22, UV.SP07, UV.DR02, 6, Credit.Noir));
		m_chemins.put(23, new Chemin(23, UV.GE07, UV.DR02, 3, Credit.Humanite));
		m_chemins.put(24, new Chemin(24, UV.GE07, UV.DR01, 3, Credit.Humanite));
		m_chemins.put(25, new Chemin(25, UV.DR02, UV.DR01, 2, Credit.Humanite));
		m_chemins.put(26, new Chemin(26, UV.GE07, UV.IT40, 5, Credit.Blanc));
		m_chemins.put(27, new Chemin(27, UV.DR01, UV.IT40, 2, Credit.Humanite));
		m_chemins.put(28, new Chemin(28, UV.HE09, UV.HE05, 6, Credit.Blanc));
		m_chemins.put(29, new Chemin(29, UV.AP4A, UV.HE05, 4, Credit.Bleu));
		m_chemins.put(30, new Chemin(30, UV.HE05, UV.SI40, 4, Credit.Noir));
		m_chemins.put(31, new Chemin(31, UV.AP4A, UV.SI40, 6, Credit.Orange));
		m_chemins.put(32, new Chemin(32, UV.AP4A, UV.IT45, 5, Credit.Rouge));
		m_chemins.put(33, new Chemin(33, UV.IT40, UV.IT45, 4, Credit.Rose));
		m_chemins.put(34, new Chemin(34, UV.IT45, UV.SI40, 2, Credit.Humanite));
		m_chemins.put(35, new Chemin(35, UV.IT45, UV.SI40, 2, Credit.Humanite));
		m_chemins.put(36, new Chemin(36, UV.IT40, UV.IT42, 4, Credit.Noir));
		m_chemins.put(37, new Chemin(37, UV.IT40, UV.IT42, 4, Credit.Orange));
		m_chemins.put(38, new Chemin(38, UV.IT45, UV.IT42, 1, Credit.Humanite));
		m_chemins.put(39, new Chemin(39, UV.IT45, UV.IT42, 1, Credit.Humanite));
		m_chemins.put(40, new Chemin(40, UV.IT40, UV.IT41, 4, Credit.Rouge));
		m_chemins.put(41, new Chemin(41, UV.DR01, UV.IT41, 3, Credit.Bleu));
		m_chemins.put(42, new Chemin(42, UV.IT41, UV.IT42, 2, Credit.Humanite));
		m_chemins.put(43, new Chemin(43, UV.IT41, UV.IT42, 2, Credit.Humanite));
		m_chemins.put(44, new Chemin(44, UV.IT41, UV.IT44, 2, Credit.Humanite));
		m_chemins.put(45, new Chemin(45, UV.IT41, UV.IT44, 2, Credit.Humanite));
		m_chemins.put(46, new Chemin(46, UV.IT44, UV.IT43, 1, Credit.Humanite));
		m_chemins.put(47, new Chemin(47, UV.IT44, UV.IT43, 1, Credit.Humanite));
		m_chemins.put(48, new Chemin(48, UV.DR02, UV.IT41, 5, Credit.Jaune));
		m_chemins.put(49, new Chemin(49, UV.DR02, UV.IT44, 4, Credit.Rouge));
		m_chemins.put(50, new Chemin(50, UV.DR02, UV.IT43, 6, Credit.Vert));
		m_chemins.put(51, new Chemin(51, UV.HE05, UV.PC40, 6, Credit.Humanite));
		m_chemins.put(52, new Chemin(52, UV.SI40, UV.PC40, 3, Credit.Humanite));
		m_chemins.put(53, new Chemin(53, UV.SI40, UV.RN40, 6, Credit.Rose));
		m_chemins.put(54, new Chemin(54, UV.PC40, UV.RN40, 2, Credit.Humanite));
		m_chemins.put(55, new Chemin(55, UV.PC40, UV.LE07, 5, Credit.Noir));
		m_chemins.put(56, new Chemin(56, UV.RN40, UV.LE07, 3, Credit.Humanite));
		m_chemins.put(57, new Chemin(57, UV.LE05, UV.LE07, 2, Credit.Humanite));
		m_chemins.put(58, new Chemin(58, UV.LE05, UV.LE07, 2, Credit.Humanite));
		m_chemins.put(59, new Chemin(59, UV.LE05, UV.LE03, 2, Credit.Jaune));
		m_chemins.put(60, new Chemin(60, UV.LE05, UV.LE03, 2, Credit.Rouge));
		m_chemins.put(61, new Chemin(61, UV.LE03, UV.LE07, 3, Credit.Bleu));
		m_chemins.put(62, new Chemin(62, UV.RN40, UV.RN41, 2, Credit.Humanite));
		m_chemins.put(63, new Chemin(63, UV.LE03, UV.RN41, 2, Credit.Blanc));
		m_chemins.put(64, new Chemin(64, UV.LE03, UV.RN41, 2, Credit.Vert));
		m_chemins.put(65, new Chemin(65, UV.WE4A, UV.SI40, 3, Credit.Rouge));
		m_chemins.put(66, new Chemin(66, UV.WE4A, UV.IT45, 4, Credit.Bleu));
		m_chemins.put(67, new Chemin(67, UV.WE4A, UV.SY41, 2, Credit.Vert));
		m_chemins.put(68, new Chemin(68, UV.WE4A, UV.SY41, 2, Credit.Blanc));
		m_chemins.put(69, new Chemin(69, UV.WE4A, UV.RN41, 3, Credit.Orange));
		m_chemins.put(70, new Chemin(70, UV.WE4A, UV.RN41, 3, Credit.Noir));
		m_chemins.put(71, new Chemin(71, UV.WE4A, UV.RN40, 4, Credit.Blanc));
		m_chemins.put(72, new Chemin(72, UV.LE03, UV.LE02, 2, Credit.Orange));
		m_chemins.put(73, new Chemin(73, UV.LE03, UV.LE02, 2, Credit.Noir));
		m_chemins.put(74, new Chemin(74, UV.LE01, UV.LE02, 2, Credit.Humanite));
		m_chemins.put(75, new Chemin(75, UV.LE01, UV.LE02, 2, Credit.Humanite));
		m_chemins.put(76, new Chemin(76, UV.RN41, UV.LE02, 2, Credit.Humanite));
		m_chemins.put(77, new Chemin(77, UV.RN41, UV.LE01, 2, Credit.Humanite));
		m_chemins.put(78, new Chemin(78, UV.RN41, UV.SY41, 5, Credit.Vert));
		m_chemins.put(79, new Chemin(79, UV.RN41, UV.SY40, 4, Credit.Jaune));
		m_chemins.put(80, new Chemin(80, UV.LE01, UV.SY40, 3, Credit.Noir));
		m_chemins.put(81, new Chemin(81, UV.SY41, UV.SY40, 2, Credit.Humanite));
		m_chemins.put(82, new Chemin(82, UV.SY42, UV.SY40, 1, Credit.Humanite));
		m_chemins.put(83, new Chemin(83, UV.RE4E, UV.SY40, 3, Credit.Blanc));
		m_chemins.put(84, new Chemin(84, UV.LE01, UV.SY42, 2, Credit.Humanite));
		m_chemins.put(85, new Chemin(85, UV.LE01, UV.SY42, 2, Credit.Humanite));
		m_chemins.put(86, new Chemin(86, UV.IT42, UV.SY41, 2, Credit.Bleu));
		m_chemins.put(87, new Chemin(87, UV.IT42, UV.SY41, 2, Credit.Rose));
		m_chemins.put(88, new Chemin(88, UV.RE4E, UV.SY41, 2, Credit.Humanite));
		m_chemins.put(89, new Chemin(89, UV.RE4E, UV.IT41, 2, Credit.Humanite));
		m_chemins.put(90, new Chemin(90, UV.RE4E, UV.IT44, 2, Credit.Humanite));
		m_chemins.put(91, new Chemin(91, UV.RE4E, UV.RS40, 3, Credit.Vert));
		m_chemins.put(92, new Chemin(92, UV.LE01, UV.HM40, 2, Credit.Humanite));
		m_chemins.put(93, new Chemin(93, UV.SY42, UV.HM40, 2, Credit.Humanite));
		m_chemins.put(94, new Chemin(94, UV.SY42, UV.RS40, 4, Credit.Jaune));
		m_chemins.put(95, new Chemin(95, UV.SY42, UV.RS40, 4, Credit.Orange));
		m_chemins.put(96, new Chemin(96, UV.IT43, UV.RS40, 2, Credit.Humanite));
		m_chemins.put(97, new Chemin(97, UV.TX52, UV.RS40, 6, Credit.Rouge));
		m_chemins.put(98, new Chemin(98, UV.TX52, UV.HM40, 4, Credit.Rose));
		m_chemins.put(99, new Chemin(99, UV.TX52, UV.SY42, 5, Credit.Bleu));
	}
	
	/**
	 * Retourne un chemin à partir de son id, et le retire de la liste pour signifier qu'il a été pris
	 * @param id Identifiant du chemin
	 * @return Chemin pris
	 * @throws NoSuchElementException Si le chemin avec l'identifiant donné n'est pas dans la liste, donc s'il est déjà pris ou s'il n'existe pas
	 */
	public Chemin prendre(int id) throws NoSuchElementException {
		Chemin prise = m_chemins.get(id);
		if (prise == null) {
			throw new NoSuchElementException("Le chemin est déjà pris ou n'existe pas");
		} else {
			m_chemins.remove(id);
			return prise;
		}
	}
	
	/**
	 * Donne la liste des chemins disponibles
	 * @return Liste des chemins disponibles
	 */
	public ArrayList<Chemin> chemins(){
		ArrayList<Chemin> copie = new ArrayList<Chemin>(m_chemins.values());
		return copie;
	}
	
	/**
	 * Donne si le chemin avec l'id donné est disponible
	 * @param id ID du chemin à chercher
	 * @return true si le chemin est disponible, false sinon
	 */
	public boolean disponible(int id) {
		return m_chemins.containsKey(id);
	}
	
}
