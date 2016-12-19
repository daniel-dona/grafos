public class Trayecto {
	
	/*********************************************************************
	*
	* Class Name: Trayecto
	* Author/s name: Daniel Doña, Ignacio Rozas, Alberto Vinaroz
	* Release/Creation date: 18/12/16
	* Class version: 0.1
	* Class description: Es la clase que define los elementos del trayecto
	* 
	**********************************************************************
	*/
	

	private int start;
	private int end;
	DecoratedElement v_start;
	DecoratedElement v_end;

	public Trayecto(int start_i, int end_i, DecoratedElement v_s, DecoratedElement v_e) {

		this.start = start_i;
		this.end = end_i;
		this.v_start = v_s;
		this.v_end = v_e;

	}

	public int getstart() {

		return this.start;

	}

	public int getend() {

		return this.end;

	}

	public DecoratedElement getstarte() {

		return this.v_start;

	}

	public DecoratedElement getende() {

		return this.v_end;

	}

	public boolean equals(Trayecto tr) {

		int tr_start = tr.getstart();
		int tr_end = tr.getend();

		if (((tr_start == this.start) && (tr_end == this.end)) || ((tr_start == this.end) && (tr_end == this.start))) {

			// Tratándose de un grafo no dirigido, consideramos (a,b) y (b,a)
			// como aristas equivalentes

			return true;

		} else {

			return false;

		}

	}

}