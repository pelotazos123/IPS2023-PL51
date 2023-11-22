package giis.demo.model.competiciones;

import java.time.LocalDate;
import java.util.List;

import giis.demo.model.TiposCuotas;
import giis.demo.model.TiposDeportes;

public class Competicion {
	
	private int id;
	private String nombre;
	private LocalDate fecha;
	private String lugar;
	private List<TiposCuotas> categorias;
	private TiposDeportes deporte;
	
	public Competicion(int id, String nombre, LocalDate fecha, String lugar, List<TiposCuotas> categorias, TiposDeportes deporte) {
		this.id = id;
		this.nombre = nombre;
		this.fecha = fecha;
		this.lugar = lugar;
		this.categorias = categorias;
		this.deporte = deporte;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public String getLugar() {
		return lugar;
	}

	public List<TiposCuotas> getCategorias() {
		return categorias;
	}

	public TiposDeportes getDeporte() {
		return deporte;
	}

}
