import graphsDSESIUCLM.Element;

public class DecoratedElement<T> implements Element {
	
	/*********************************************************************
	*
	* Class Name: DecoratedElement
	* Author/s name: Daniel Doña, Ignacio Rozas, Alberto Vinaroz
	* Release/Creation date: 18/12/16
	* Class version: 0.1
	* Class description: Es el elemento decorado que estara contenido en los vertices de nuestro grafo
	**********************************************************************
	*/
	

	private String id;
	private T element;
	private boolean visited;

	private DecoratedElement<T> parent; 
	private int distance; 

	public DecoratedElement(String key, T element) {
		this.element = element;
		this.id = key;
		this.visited = false;
		this.parent = null;
		this.distance = 0;
	}

	public T getElement() {
		return this.element;
	}

	public boolean getVisited() {
		return this.visited;
	}

	public void setVisited(boolean t) {
		this.visited = t;
	}

	public DecoratedElement<T> getParent() {
		return this.parent;
	}

	public void setParent(DecoratedElement<T> u) {
		this.parent = u;
	}

	public int getDistance() {
		return this.distance;
	}

	public void setDistance(int d) {
		this.distance = d;
	}

	/*
	 * In this case, to check if two Vertices are identical, both the key and
	 * the element must be equal. Notice the cast to convert n (class Object) to
	 * class DecoratedElement<T> IMPORTANT: element needs to override equals()
	 */

	@Override
	public boolean equals(Object n) {
		return (this.id.equals(((DecoratedElement) n).getID())
				&& this.element.equals(((DecoratedElement<T>) n).getElement()));
	}

	@Override
	public String toString() {
		return this.element.toString(); // element needs to override toString()
	}

	@Override
	public String getID() {
		return this.id;
	}
}
