public class Estacion {
	/*********************************************************************
	*
	* Class Name: Estacion
	* Author/s name: Daniel Doña, Ignacio Rozas, Alberto Vinaroz
	* Release/Creation date: 18/12/16
	* Class version: 0.1
	* Class description: Es la clase que almacena los datos de las estaciones
	* 
	**********************************************************************
	*/
	

	private int id;
	private double lat;
	private double lng;

	public Estacion(int id_i, double lat_i, double lng_i) {

		this.id = id_i;
		this.lat = lat_i;
		this.lng = lng_i;

	}

	int getid() {

		return this.id;

	}

	double getlat() {

		return this.lat;

	}

	double getlng() {

		return this.lng;

	}

}