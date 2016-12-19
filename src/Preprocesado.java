import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Preprocesado {
	/*********************************************************************
	*
	* Class Name: Preprocesado
	* Author/s name: Daniel Doña, Ignacio Rozas, Alberto Vinaroz
	* Release/Creation date: 18/12/16
	* Class version: 0.1
	* Class description: Es la clase que se encarga de procesar los datos de entrada
	* 
	**********************************************************************
	*/
	

	Queue<DecoratedElement> q_estaciones = new LinkedList<>();
	Queue<Trayecto> q_trayectos = new LinkedList<>();

	private boolean trayecto_unico(Trayecto tr, Queue<Trayecto> q) {

		/*********************************************************************
		*
		* Method name: trayecto_unico
		*
		* Description of the Method:Comprueba que el trayecto es unico
		* 
		* -tr:Trayecto, es el elemento a comprobar
		* -q:Queue<Trayecto>es la cola donde se compbrobara si el elemento es unico
		* 
		* Return value: boolean
		*
		*********************************************************************/

		Queue<Trayecto> q_test = new LinkedList<>(q);

		Trayecto test;

		while ( q_test.size() > 0) {

			test = q_test.poll();

			if (test.equals(tr))
				return false;			

		}

		return true;

	}

	private boolean estacion_unica(DecoratedElement est_d, Queue<DecoratedElement> q) {
		/*********************************************************************
		*
		* Method name: estacion_unica
		*
		* Description of the Method:Comprueba que la estacion es unica
		* 
		* -est_d:DecorateElement, es el elemento el cual se va a comprobar que es unico
		* -q:Queue<DecorateElement>, es la cola donde se buscara la estacion
		* 
		* Return value: boolean
		*
		*********************************************************************/

		Queue<DecoratedElement> q_test = new LinkedList<>(q);

		DecoratedElement test;

		while ( q_test.size() > 0) {

			test = q_test.poll();

			if (test.getID().equals(est_d.getID())) 
				return false;
		}


		return true;

	}

	public Preprocesado(String filename) throws IOException {

		BufferedReader br = null;
		FileReader fr = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(filename));

			br.readLine(); // Nos saltamos la primera línea de cabeceras CSV

			while ((sCurrentLine = br.readLine()) != null) {

				String[] nextLine = sCurrentLine.split(",");

				int duracion = Integer.parseInt(nextLine[1]);

				int inicio = Integer.parseInt(nextLine[4]);

				double lat_s = Double.parseDouble(nextLine[5]);

				double lng_s = Double.parseDouble(nextLine[6]);

				int fin = Integer.parseInt(nextLine[7]);

				double lat_e = Double.parseDouble(nextLine[8]);

				double lng_e = Double.parseDouble(nextLine[9]);

				if (!nextLine[12].equals("Round Trip")) { // Discrimina los
															// viajes que salen
															// y vuelven a la
															// misma estación

					if (duracion <= 300) { // Discrimina los viajes que duran
											// menos de 5 minutos

						Estacion est_s = new Estacion(inicio, lat_s, lng_s); // Objeto
																				// de
																				// Estación

						Estacion est_e = new Estacion(fin, lat_e, lng_e);

						DecoratedElement v_s = new DecoratedElement(Integer.toString(inicio), est_s); // Creamos
																										// un
																										// DecoratedElement
																										// con
																										// la
																										// estación

						DecoratedElement v_e = new DecoratedElement(Integer.toString(fin), est_e);

						Trayecto tr = new Trayecto(inicio, fin, v_s, v_e); // Objeto
																			// de
																			// Trayecto

						if (this.trayecto_unico(tr, this.q_trayectos)) {

							this.q_trayectos.offer(tr); // Insertamos el objeto
														// de Trayecto en
														// nuestra cola de
														// trayectos

							if (this.estacion_unica(v_s, this.q_estaciones)) {

								this.q_estaciones.offer(v_s); // Insertamos este
																// elemento en
																// nuestra cola
																// de estaciones

							}

							if (this.estacion_unica(v_e, this.q_estaciones)) {

								// System.out.println("Insertando"+v_e.getID());

								this.q_estaciones.offer(v_e);

							}

							// System.out.println("Duración: " + nextLine[1] + "
							// Inicio: " + nextLine[4] + " Fin: " +
							// nextLine[7]);

						}

					}

				}

			}

			// System.out.print(n_e+" "+n_t);
			// System.out.print(estaciones[34].getid()+"
			// "+estaciones[34].getlat()+" "+estaciones[34].getlng());

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null) {
					br.close();
				}

				if (fr != null) {
					fr.close();
				}

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}

	public Queue<DecoratedElement> get_estaciones() {

		return this.q_estaciones;

	}

	public Queue<Trayecto> get_trayectos() {

		return this.q_trayectos;

	}

}