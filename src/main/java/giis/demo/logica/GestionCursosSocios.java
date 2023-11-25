package giis.demo.logica;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import giis.demo.ui.VentanaGestionCursos;

public class GestionCursosSocios {
	
	// TODO RELLENAR CON LAS COLUMNAS Y CON EL ID DEL USUARIO
	
	// TODO RELLENAR EL ID DEL USUARIO Y CAMBIAR CURSO POR CURSANTE?
	private static final String BORRADECURSO = "delete from cursos where id = ? "; 
	private static final String ACTUALIZAPRECIOINSCRIBIRSE = "update from cuotas set price = price + ?";
	private static final String ACTUALIZAPRECIOBORRARSE = "update from cuotas set price = price - ?";
	
	public static final String INSCRITO = "INSCRITO";
	public static final String NO_INSCRITO = "NO INSCRITO";
	private static final int COLUMNA_PRECIO = 5;
//	private static final String ASIGNA_ENTRENADOR  = "insert into entrenados(entrenador_id, entrenado_id) values"
//			+ " (?,?)";

	private VentanaGestionCursos vgcs;
	private int id;
	
	public GestionCursosSocios(VentanaGestionCursos ventanaGestionCursos) {
		this.vgcs = ventanaGestionCursos;
	}
	
	public Object[][] cargaFilas() {
		if (vgcs.getVp().getTramitarLicencia().esDirectivo())
			id = vgcs.getVp().getTramitarLicencia().getDirectivo().getId();
		else
			id = vgcs.getVp().getTramitarLicencia().getSocio().getId();
		String CARGADATOS = "SELECT id, nombre, code_instalacion, fecha_inicio, fecha_fin,"
				+ " price, plazas from cursillos c"; 
		String ESTA_INSCRITO = "select * from inscritos where id_cursante = ? and id_curso = ?";
		String CARGAPLAZAS = "select * from inscritos where id_curso = ? ";
		List<Object[]> cursos = vgcs.getVp().getDb().executeQueryArray(CARGADATOS);
		Object[][] datos = new Object[cursos.size()][cursos.get(0).length + 2];
		for (int i = 0; i < cursos.size(); i++) {
			Object[] columnas = cursos.get(i);
			for (int j = 0; j < columnas.length; j++) {
				if(j == columnas.length - 1) {
					int plazas = vgcs.getVp().getDb().executeQueryArray(CARGAPLAZAS, columnas[0]).size();
					System.out.println(columnas[0]);
					System.out.println("Plazas: " + plazas);
					datos[i][j] = (Integer.parseInt(columnas[j].toString()) - plazas) + "/" + Integer.parseInt(columnas[j].toString());
				} else 
					datos[i][j] = columnas[j];
			}
			int idCurso = Integer.parseInt(columnas[0].toString());
			//ASIGNAR A NUMERO ENTRENADORES
			String GET_ENTRENADORES_CURSO = "select dni from entrenadores_cursillos where id_curso = ?";
			List<Object[]> entrenadores = vgcs.getVp().getDb()
										.executeQueryArray(GET_ENTRENADORES_CURSO, idCurso);
			datos[i][columnas.length] = entrenadores.size();
			if(vgcs.getVp().getDb().executeQueryArray(ESTA_INSCRITO, id, idCurso).isEmpty())
				datos[i][columnas.length + 1] = NO_INSCRITO;
			else
				datos[i][columnas.length + 1] = INSCRITO;
		}
		return datos;
	}
	

	public void inscribirse() {
		String INSCRIBIRSE = "insert into inscritos(id_curso, id_cursante) values (?,?)";
		int row = vgcs.getTableCursos().getSelectedRow();
		int idCurso = Integer.parseInt(vgcs.getDatos()[row][0].toString());
		double precio = Double.parseDouble(vgcs.getDatos()[row][COLUMNA_PRECIO].toString());
		vgcs.getVp().getDb().executeUpdate(INSCRIBIRSE, idCurso, id);
//		vgcs.getVp().getDb().executeUpdate(ACTUALIZAPRECIOINSCRIBIRSE, precio);
		
		String GET_ENTRENADORES_CURSO = "select dni from entrenadores_cursillos where id_curso = ?";
		String GET_ID_ENTRENADOR = "select id from socios where dni = ?";
		String ASIGNA_ENTRENADOR  = "insert into entrenados(entrenador_id, entrenado_id) values (?,?)";
		List<Object[]> entrenadores = vgcs.getVp().getDb().executeQueryArray(GET_ENTRENADORES_CURSO, idCurso);
		for(Object[] entrenador: entrenadores) {
			List<Object[]> idEntrenador = vgcs.getVp().getDb().executeQueryArray(GET_ID_ENTRENADOR, entrenador[0]);
			vgcs.getVp().getDb().executeUpdate(ASIGNA_ENTRENADOR, idEntrenador.get(0)[0], id);
		}
		JOptionPane.showMessageDialog(null, "Inscrito");
		
	}

	public void borrar() {
		Date fecha = vgcs.getVp().getDcFecha().getDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		LocalDate date = LocalDate.of(year, month, day);
		if (day <= 15) {
			int[] row = vgcs.getTableCursos().getSelectedRows();
			// TODO CAMBIAR EL 0 POR LA COLUMNA DEL ID DEL CURSO
			for(int i = 0; i < row.length; i++) {
				int idCurso = Integer.parseInt(vgcs.getDatos()[row[i]][0].toString());
//				vp.getDb().executeUpdate(BORRADECURSO, id);
			// TODO COMPROBAR QUE FUE BORRADO
			// TODO CAMBIAR EL 0 POR LA COLUMNA DEL PRECIO DEL CURSO
				int precio = Integer.parseInt(vgcs.getDatos()[row[i]][0].toString());
//				vp.getDb().executeUpdate(ACTUALIZAPRECIO, precio);
			}
		}
		else {
			//TODO COMPROBAR, PERO CREO QUE SÓLO BORRA DEL CURSO
		}
		String BORRA_ENTRENADOR = "delete from Entrenados where entrenador_id = ? and "
				+ "entrenado_id = ? ";
		vgcs.getVp().getDb().executeUpdate(BORRA_ENTRENADOR, id);	//TODO, FALTA BORRAR PARA TODOS LOS ENTRENADORES DEL CURSO
	}
	
	public String[] cargaNomColumnas() {
		return new String[]{"ID","NOMBRE", "INSTALACIÓN","FECHA INICIO", "FECHA FIN", "PRECIO", "PLAZAS", 
				"ENTRENADORES", "ESTADO"};
	}

	public void realizaAccion(String accion) {
		if(accion.equals(INSCRITO))
			borrar();
		else
			inscribirse();
	}
	
}
