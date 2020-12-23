package ticket_to_utbm.game;

public class Cursus {
	private UV m_uv1;
	private UV m_uv2;
	private int m_valeur;
	
	public Cursus(UV uv1, UV uv2, int valeur) {
		m_uv1 = uv1;
		m_uv2 = uv2;
		m_valeur = valeur;
	}
	
	public UV uv1() {
		return m_uv1;
	}
	
	public UV uv2() {
		return m_uv2;
	}
	
	public int valeur() {
		return m_valeur;
	}
}
