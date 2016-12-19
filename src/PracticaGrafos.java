import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

import graphsDSESIUCLM.Edge;
import graphsDSESIUCLM.Graph;
import graphsDSESIUCLM.TreeMapGraph;
import graphsDSESIUCLM.Vertex;

class PracticaGrafos {
	
	/*********************************************************************
	*
	* Class Name: Listas
	* Author/s name: Daniel Doña, Ignacio Rozas, Alberto Vinaroz
	* Release/Creation date: 18/12/16
	* Class version: 0.1
	* Class description: Clase principal que crea el grafo de las estaciones y trayectos y muestra
	* las estaciones intermedias entre dos estaciones dada por el usuario
	* 
	**********************************************************************
	*/
	

	public static void main(String[] args) throws IOException {
		
		
		
		/*********************************************************************
		*
		* Method name: main()
		*
		* Description of the Method: método principal de nuestro programa
		*
		* Calling arguments: 
		* 
		* - argv[]:String, lista de argumentos proporcionados desde la linea de
		* comandos cuando se ejecuta nuestro programa. No usado en nuestro caso
		*
		* Return value: void (nada)
		*
		*********************************************************************/
		
		

		String datos = "data/MetroBikeShare_2016_Q3_trips.csv"; // Datos en CSV
																// de los
																// trayectos y
																// estaciones

		Graph gr = new TreeMapGraph<>(); // Objeto de Grafo

		Queue<DecoratedElement> q_estaciones = new LinkedList<>(); // Cola
																	// para
																	// almacenar
																	// los
																	// vértices
																	// con
																	// los
																	// que
																	// crear
																	// el
																	// grafo
		Queue<Trayecto> q_trayectos = new LinkedList<>(); // Cola para
															// almacenar
															// los
															// trayectos
															// con los
															// que crear
															// el grafo

		Preprocesado prp = new Preprocesado(datos); // Clase que realiza la
													// lectura de fichero y
													// extrae los vértices y
													// aristas
		
		Scanner stdin = new Scanner(System.in);

		// Apartado a)

		System.out.println("\nApartado a)\n");

		q_estaciones = prp.get_estaciones(); // Vértices
		q_trayectos = prp.get_trayectos(); // Aristas

		InicializarEstaciones(gr, q_estaciones);
		InicializarTrayectos(gr, q_trayectos);

		System.out.println("\tNúmero de estaciones (vértices): " + gr.getN());
		System.out.println("\tNúmero de trayectos (aristas): " + gr.getM());

		// Apartado b)

		System.out.println("\n\nApartado b)");

		MostrarEstaciones(gr);

		// Apartado c)

		System.out.println("\n\n\nApartado c)\n");

		while (true) {

			TrayectoMasCorto(gr);

			System.out.print("\n\t¿Calcular otro trayecto? (s/n) ");
			
			if(stdin.nextLine().equals("n")){
				
				System.exit(0);
				
			}
			
			System.out.print("\n");

		}

	}

	// Funciones auxiliares para la creación del grafo

	public static void InicializarEstaciones(Graph grafo, Queue<DecoratedElement> q) {
		
		
		/*********************************************************************
		*
		* Method name: InicializarEstaciones
		*
		* Description of the Method: Introduce las estaciones de la lista q en el grafo de entrada
		*
		* Calling arguments: 
		* 
		* - grafo:Grafo, es el grafo donde se guardara las estaciones
		* 
		* -q:Queue<DecoratedElement>,es la lista de donde se sacaran las estaciones
		* Return value: void (nada)
		*
		*********************************************************************/


		Queue<DecoratedElement> q2 = new LinkedList<>(q); // Se
															// duplica
															// la
															// cola
															// para
															// no
															// modificar
															// la
															// original

		for (; q2.size() > 0;) {

			DecoratedElement v = q2.poll();

			grafo.insertVertex(v);

		}

	}

	public static void InicializarTrayectos(Graph grafo, Queue<Trayecto> q) {
		/*********************************************************************
		*
		* Method name: InicializarTrayectos
		* 
		* Description of the Method: Introduce los trayectos de la lista q en el grafo de entrada
		*
		* Calling arguments: 
		* 
		* - grafo:Grafo, es el grafo donde se guardara las estaciones
		* 
		* -q:Queue<Trayectos>,es la lista de donde se sacaran los trayectos
		* Return value: void (nada)
		*
		*********************************************************************/

		Queue<Trayecto> q2 = new LinkedList<>(q);

		for (; q2.size() > 0;) {

			Trayecto t = q2.poll();

			DecoratedElement v1 = t.getstarte();

			// System.out.println(v1.getID());

			DecoratedElement v2 = t.getende();

			// System.out.println(v2.getID());

			grafo.insertEdge(v1, v2);

		}

	}

	// Funciones para mostrar el grafo
	
	public static String MosEst(DecoratedElement e){
		/*********************************************************************
		*
		* Method name: MosEst
		*
		* Description of the Method: Muestra por pantalla los datos de la estacion guardada en e
		*
		* Calling arguments: 
		* 
		* - e:DecortedElemnt,es el elemento decorado que guarda la estacion a mostrar
		* 
		* Return value: String, 
		*
		*********************************************************************/
		
		Estacion est = (Estacion) e.getElement();
		
		String out = est.getid()+" ("+est.getlat()+","+est.getlng()+")";
		
		return out;
		
	}

	public static void MostrarEstaciones(Graph grafo) {
		/*********************************************************************
		*
		* Method name: MostrarEstaciones
		*
		* Description of the Method: Muestra todas las estaciones guardadas en el grafo de entrada
		*
		* Calling arguments: 
		* 
		* - grafo:Graph, es el grafo de donde se sacaran las estaciones
		* 
		* Return value: void (nada)
		*
		*********************************************************************/

		Iterator<Vertex<DecoratedElement>> it1;

		it1 = grafo.getVertices();

		Vertex v, op;

		Edge e;

		for (int i = 0; it1.hasNext(); i++) {

			v = it1.next();

			System.out.print("\n\t[" + i + "] Estación " + MosEst((DecoratedElement) v.getElement()) + " conectada con:");

			Iterator<Edge<DecoratedElement>> it2;

			it2 = grafo.incidentEdges(v);

			while (it2.hasNext()) {

				e = it2.next();
				op = grafo.opposite(v, e);

				System.out.print(" " + op.getID());

			}

		}

	}

	// Funciones para calcular el trayecto más corto entre 2 estaciones

	public static void TrayectoMasCorto(Graph grafo) {
		/*********************************************************************
		*
		* Method name: TrayectoMasCorto
		*
		* Description of the Method: Pide las estaciones de origen y destino al usuario y llama al metodo
		* MasCorto() para econtrar el camino mas corto entre las estaciones dadas y lo muestra
		*
		* Calling arguments: 
		* 
		* - grafo: Graph, es el grafo de donde se sacaran las estaciones y trayectos para hallar el camino mas corto
		* 
		* Return value: void (nada)
		*
		*********************************************************************/
		
		Scanner stdin = new Scanner(System.in);

		boolean valid = false;

		int est1 = 0, est2 = 0;

		do {
			System.out.print("\tIntroduzca la estación (ID) de salida: ");

			if (stdin.hasNextInt()) {

				est1 = stdin.nextInt();

				if (ExisteV(grafo, est1)) {

					valid = true;

				} else {

					System.out.println("\t[ERROR] El ID indicado no existe");

				}

			} else {

				stdin.next();

				System.out.println("\t[ERROR] Debe introducir un número ID de estación");

			}

		} while (valid == false);

		valid = false;

		do {
			System.out.print("\tIntroduzca la estación (ID) de finalización: ");

			if (stdin.hasNextInt()) {

				est2 = stdin.nextInt();

				if (ExisteV(grafo, est2)) {
					
					if(est2 == est1){
						
						System.out.println("\t[ERROR] Introduzca una estación distinta de la de salida");
						
					}else{

						valid = true;
						
					}

				} else {

					System.out.println("\t[ERROR] El ID indicado no existe");

				}

			} else {

				stdin.next();

				System.out.println("\t[ERROR] Debe introducir un número ID de estación");

			}

		} while (valid == false);

		DecoratedElement node;

		Stack<DecoratedElement> sp = new Stack();

		int size;

		node = MasCorto(grafo, IDaV(grafo, est1), IDaV(grafo, est2));

		if (node.getParent() == null) {
			System.out.println("\tNo se ha encontrado un camino");
		} else {
			System.out.print("\tEl trayecto más corto entre " + MosEst((DecoratedElement) IDaV(grafo, est1).getElement()) + " y " + MosEst((DecoratedElement) IDaV(grafo, est2).getElement()) + " es: ");
			while (node.getParent() != null) {
				sp.push(node);
				node = node.getParent();
			}
			sp.push(node);

			size = sp.size();
			for (int i = 0; i < (size - 1); i++) {
				node = sp.pop();
				System.out.print(node.getID() + " -> ");
			}
			node = sp.pop();
			System.out.print(node.getID() + " (" + (size - 2) + " estaciones intermedias)");

		}

	}

	public static DecoratedElement MasCorto(Graph grafo, Vertex<DecoratedElement> v1, Vertex<DecoratedElement> v2) {
		
		/*********************************************************************
		*
		* Method name: MasCorto
		*
		* Description of the Method: Ecuentra el camino mas corto entre dos estaciones
		*
		* Calling arguments: 
		* 
		* -grafo:Graph, es el grafo en donde se buscara el camino mas corto
		* 
		*  
		* -v1: Vertex<DecoratedElement>,es la estacion de origen
		* -v1: Vertex<DecoratedElement>,es la estacion de destino
		* 
		* Return value: DecoratedElement, es el elemento decorado que guarda la siguiente estacion de la ruta
		*
		*********************************************************************/

		LimpiarDist(grafo);

		// System.out.println("V1: " + v1.getID());
		// System.out.println("V2: " + v2.getID());

		Queue<Vertex<DecoratedElement>> q = new LinkedList();
		boolean noEnd = true;
		Vertex<DecoratedElement> u, v = null;
		Edge e;
		Iterator<Edge> it;

		v1.getElement().setVisited(true);
		q.offer(v1);
		while (!q.isEmpty() && noEnd) {
			u = q.poll();
			it = grafo.incidentEdges(u);
			while (it.hasNext() && noEnd) {
				e = it.next();
				v = grafo.opposite(u, e);
				if (!(v.getElement()).getVisited()) {
					(v.getElement()).setVisited(true);
					(v.getElement()).setParent(u.getElement());
					(v.getElement()).setDistance(((u.getElement()).getDistance()) + 1);
					q.offer(v);
					noEnd = !(v.getElement().equals(v2.getElement()));
				}
			}
		}

		return v.getElement();

	}

	public static Vertex IDaV(Graph grafo, int est) {
		
		/*********************************************************************
		*
		* Method name: IDaV
		*
		* Description of the Method: devuelve el vertice correspondiente al id est en el grafo
		*
		* Calling arguments: 
		* 
		* - grafo:Graph,El grafo donde se buscara la estacion
		* - est:int,es el id de la estacion a buscar
		* Return value: Vertex, es el vertice que contiene la estacion que hemos buscado
		*
		*********************************************************************/

		Iterator<Vertex<DecoratedElement>> it1;

		it1 = grafo.getVertices();

		Vertex v;

		while (it1.hasNext()) {

			v = it1.next();

			if (Integer.parseInt(v.getID()) == est) {

				return v;
			}

		}

		return null;

	}

	public static void LimpiarDist(Graph grafo) {
		
		/*********************************************************************
		*
		* Method name: LimpiarDist
		*
		* Description of the Method: pone todos los vertices en no visitados sin padres y con distancia 0
		*
		* Calling arguments: 
		* 
		* - grafo:Graph,el grafo que se va a limpiar
		* 
		* Return value: void (nada)
		*
		*********************************************************************/

		Iterator<Vertex<DecoratedElement>> it1;

		it1 = grafo.getVertices();

		Vertex<DecoratedElement> v;

		while (it1.hasNext()) {

			v = it1.next();

			v.getElement().setDistance(0);
			v.getElement().setVisited(false);
			v.getElement().setParent(null);

		}

	}

	public static boolean ExisteV(Graph grafo, int est) {
		
		/*********************************************************************
		*
		* Method name: ExisteV
		*
		* Description of the Method: devuelve true si la estacion est esta dentro del grafo
		*
		* Calling arguments: 
		* 
		* - grafo:Graph,el grafo donde se buscara la estacion
		* - est:ing, es el id del la estacion a buscar
		* Return value: boolean
		*
		*********************************************************************/

		// Hay un método de la clase Graph que es exists(Vertex<V> v), pero
		// necesita un vértice para comparar

		Iterator<Vertex<DecoratedElement>> it1;

		it1 = grafo.getVertices();

		Vertex v;

		while (it1.hasNext()) {

			v = it1.next();

			if (Integer.parseInt(v.getID()) == est) {

				return true;
			}

		}

		return false;

	}

}
