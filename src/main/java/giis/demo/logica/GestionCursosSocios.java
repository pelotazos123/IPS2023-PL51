package giis.demo.logica;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import giis.demo.business.RecibosModel;
import giis.demo.model.CrearLicencias.servicio.TramitarLicencia;
import giis.demo.ui.VentanaGestionCursos;

public class GestionCursosSocios {

	public static final String INSCRITO = "INSCRITO";
	public static final String NO_INSCRITO = "NO INSCRITO";

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
		String ESTA_INSCRITO = "select * from inscritos where id_cursante = ? and id_curso = ? and estado = 'INSCRITO'";
		String CARGAPLAZAS = "select * from inscritos where id_curso = ? and estado = 'INSCRITO' ";
		List<Object[]> cursos = vgcs.getVp().getDb().executeQueryArray(CARGADATOS);
		Object[][] datos = new Object[cursos.size()][cursos.get(0).length + 2];
		for (int i = 0; i < cursos.size(); i++) {
			Object[] columnas = cursos.get(i);
			for (int j = 0; j < columnas.length; j++) {
				if (j == columnas.length - 1) {
					int plazas = vgcs.getVp().getDb().executeQueryArray(CARGAPLAZAS, columnas[0]).size();
					datos[i][j] = (Integer.parseInt(columnas[j].toString()) - plazas) + "/"
							+ Integer.parseInt(columnas[j].toString());
				} else
					datos[i][j] = columnas[j];
			}
			int idCurso = Integer.parseInt(columnas[0].toString());
			String GET_ENTRENADORES_CURSO = "select dni from entrenadores_cursillos where id_curso = ?";
			List<Object[]> entrenadores = vgcs.getVp().getDb().executeQueryArray(GET_ENTRENADORES_CURSO, idCurso);
			datos[i][columnas.length] = entrenadores.size();
			boolean esEntrenador = TestsFisiologicos.esEntrenador(id + "", vgcs.getVp().getDb());
			String GETDNI = "select dni from socios where id = ?";
			String ENTRENACURSO = "select * from entrenadores_cursillos where dni = ? and id_curso = ?";
			if (vgcs.getVp().getDb().executeQueryArray(ESTA_INSCRITO, id, idCurso).isEmpty())
				datos[i][columnas.length + 1] = NO_INSCRITO;
			else
				datos[i][columnas.length + 1] = INSCRITO;
			if (esEntrenador) {
				String dni = vgcs.getVp().getDb().executeQueryArray(GETDNI, id).get(0)[0].toString();
				if (!vgcs.getVp().getDb().executeQueryArray(ENTRENACURSO, dni, idCurso).isEmpty()) {
					datos[i][columnas.length + 1] = "ENTRENADOR";
				}
			}
		}
		return datos;
	}

	public void inscribirse() {
		String INSCRIBIRSE = "insert into inscritos(id_curso, id_cursante, estado, fecha_eliminacion) "
				+ "values (?,?,'INSCRITO', null)";
		int row = vgcs.getTableCursos().getSelectedRow();
		int idCurso = Integer.parseInt(vgcs.getDatos()[row][0].toString());
		String GETPRECIOCURSO = "select price from cursillos where id = ? ";
		double precio = Double
				.parseDouble(vgcs.getVp().getDb().executeQueryArray(GETPRECIOCURSO, idCurso).get(0)[0].toString());
		Date fecha = vgcs.getVp().getDcFecha().getDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		LocalDate date = LocalDate.of(year, month + 1, 1);
		String FECHAFIN = "select fecha_fin from cursillos where id = ?";
		LocalDate fechaFin = LocalDate
				.parse(vgcs.getVp().getDb().executeQueryArray(FECHAFIN, idCurso).get(0)[0].toString());
		if (fechaFin.isBefore(date)) {
			JOptionPane.showMessageDialog(null, "NO se puede inscribir en un curso ya finalizado");
		} else {
			vgcs.getVp().getDb().executeUpdate(INSCRIBIRSE, idCurso, id);

			String GET_ENTRENADORES_CURSO = "select dni from entrenadores_cursillos where id_curso = ?";
			String GET_ID_ENTRENADOR = "select id from socios where dni = ?";
			String ASIGNA_ENTRENADOR = "insert into entrenados(entrenador_id, entrenado_id) values (?,?)";
			List<Object[]> entrenadores = vgcs.getVp().getDb().executeQueryArray(GET_ENTRENADORES_CURSO, idCurso);
			for (Object[] entrenador : entrenadores) {
				List<Object[]> idEntrenador = vgcs.getVp().getDb().executeQueryArray(GET_ID_ENTRENADOR, entrenador[0]);
				vgcs.getVp().getDb().executeUpdate(ASIGNA_ENTRENADOR, idEntrenador.get(0)[0], id);
			}
			creaRecibos(precio, idCurso);
			JOptionPane.showMessageDialog(null, "Inscrito en el curso con id: " + idCurso);
		}
	}

	private void creaRecibos(double precio, int idCurso) {
		Date fecha = vgcs.getVp().getDcFecha().getDate();
		Calendar calendarActual = Calendar.getInstance();
		calendarActual.setTime(fecha);
		int year = calendarActual.get(Calendar.YEAR);
		int month = calendarActual.get(Calendar.MONTH) + 1;
		int day = calendarActual.get(Calendar.DAY_OF_MONTH);
		LocalDate date = LocalDate.of(year, month, day);
		RecibosModel rm = new RecibosModel(vgcs.getVp().getDb());
		TramitarLicencia tl = vgcs.getVp().getTramitarLicencia();
		String iban;
		if (tl.esDirectivo())
			iban = tl.getDirectivo().getNumeroIban();
		else
			iban = tl.getSocio().getNumeroIban();
		int number = rm.getLastNumber();
		String FECHASCURSOS = "select fecha_inicio, fecha_fin from cursillos where id = ?";
		LocalDate fechaInicio = LocalDate
				.parse(vgcs.getVp().getDb().executeQueryArray(FECHASCURSOS, idCurso).get(0)[0].toString());
		LocalDate fechaFin = LocalDate
				.parse(vgcs.getVp().getDb().executeQueryArray(FECHASCURSOS, idCurso).get(0)[1].toString());
		LocalDate v_date;
		LocalDate c_date;
		if (!date.isBefore(fechaInicio)) {
			fechaInicio = date;
		}
		if (fechaInicio.getYear() == fechaFin.getYear()) {
			int months = fechaFin.getMonthValue() - fechaInicio.getMonthValue();
			for (int i = 0; i < months; i++) {
				v_date = generaFechas(fechaInicio, i);
				c_date = v_date.plusDays(14);
				rm.generateRecibo(iban, number++, precio, v_date.toString(), c_date.toString(), "Curso", "Domiciliado",
						"Pendiente");
			}
		} else {
			int months = 12 - fechaInicio.getMonthValue() + fechaFin.getMonthValue();
			for (int i = 0; i < months; i++) {
				v_date = generaFechas(fechaInicio, i);
				c_date = v_date.plusDays(14);
				rm.generateRecibo(iban, number++, precio, v_date.toString(), c_date.toString(), "Curso", "Domiciliado",
						"Pendiente");
			}
		}
		for (int i = 0; i < rm.getListaRecibos().size(); i++)
			System.out.println(rm.getListaRecibos().get(i).toString());
	}

	private LocalDate generaFechas(LocalDate fechaInicio, int i) {
		int month = fechaInicio.getMonthValue() + i;
		int year = fechaInicio.getYear();
		if (month > 12) {
			month -= 12;
			year++;
		}
		LocalDate date = LocalDate.of(year, month, 1);
		return date;
	}

	public void borrar() {
		Date fecha = vgcs.getVp().getDcFecha().getDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		LocalDate date = LocalDate.of(year, month + 1, 1);

		String GET_ENTRENADORES_CURSO = "select dni from entrenadores_cursillos where id_curso = ?";
		String GET_ID_ENTRENADOR = "select id from socios where dni = ?";
		String BORRA_ENTRENADOR = "delete from Entrenados where entrenador_id = ? and entrenado_id = ? ";

		int row = vgcs.getTableCursos().getSelectedRow();
		int idCurso = Integer.parseInt(vgcs.getDatos()[row][0].toString());
		String GETPRECIOCURSO = "select price from cursillos where id = ? ";
		double precio = Double
				.parseDouble(vgcs.getVp().getDb().executeQueryArray(GETPRECIOCURSO, idCurso).get(0)[0].toString());
		String FECHAFIN = "select fecha_fin from cursillos where id = ?";
		LocalDate fechaFin = LocalDate
				.parse(vgcs.getVp().getDb().executeQueryArray(FECHAFIN, idCurso).get(0)[0].toString());
		if (fechaFin.isBefore(date)) {
			JOptionPane.showMessageDialog(null, "NO se puede borrar de un curso ya finalizado");
		} else {
			List<Object[]> entrenadores = vgcs.getVp().getDb().executeQueryArray(GET_ENTRENADORES_CURSO, idCurso);
			for (Object[] entrenador : entrenadores) {
				List<Object[]> idEntrenador = vgcs.getVp().getDb().executeQueryArray(GET_ID_ENTRENADOR, entrenador[0]);
				vgcs.getVp().getDb().executeUpdate(BORRA_ENTRENADOR, idEntrenador.get(0)[0], id);
			}
			RecibosModel rm = new RecibosModel(vgcs.getVp().getDb());
			int months;
			if (fechaFin.getYear() == date.getYear())
				months = fechaFin.getMonthValue() - date.getMonthValue();
			else
				months = 12 - date.getMonthValue() + fechaFin.getMonthValue();
			for (int i = 1; i < months; i++) {
				int number = getNumber(date, i, precio);
				rm.deleteRecibo(number);
			}
			if (day <= 15) {
				String BORRADECURSOANTES = "delete from inscritos where id_cursante = ? and id_curso = ? ";
				vgcs.getVp().getDb().executeUpdate(BORRADECURSOANTES, id, idCurso);
				int number = getNumber(date, 0, precio);
				rm.deleteRecibo(number);
				JOptionPane.showMessageDialog(null, "Usted se ha borrado del curso");
			} else {
				String BORRADECURSODESPUES = "update inscritos set estado = 'BORRADO', "
						+ "fecha_eliminacion = ? where id_cursante = ? and id_curso = ? ";
				vgcs.getVp().getDb().executeUpdate(BORRADECURSODESPUES, date, id, idCurso);
				JOptionPane.showMessageDialog(null, "Usted se ha borrado del curso, el mes siguiente se le "
						+ "cobrará el importe del mismo al haberse borrado después del día 15.");
			}
			for (int i = 0; i < rm.getListaRecibos().size(); i++)
				System.out.println(rm.getListaRecibos().get(i).toString());
			System.out.println(rm.getListaRecibos().size());
		}
	}

	private int getNumber(LocalDate date, int i, double amount) {
		int month = date.getMonthValue() + i;
		int year = date.getYear();
		if (month > 12) {
			month -= 12;
			year++;
		}
		TramitarLicencia tl = vgcs.getVp().getTramitarLicencia();
		String iban;
		if (tl.esDirectivo())
			iban = tl.getDirectivo().getNumeroIban();
		else
			iban = tl.getSocio().getNumeroIban();
		date = LocalDate.of(year, month, 1);
		String GETNUMBER = "select number from recibos where owner_iban = ? and value_date = ? and amount = ?";
		return Integer.parseInt(
				vgcs.getVp().getDb().executeQueryArray(GETNUMBER, iban, date.toString(), amount).get(0)[0].toString());
	}

	public String[] cargaNomColumnas() {
		return new String[] { "ID", "NOMBRE", "INSTALACIÓN", "FECHA INICIO", "FECHA FIN", "PRECIO", "PLAZAS",
				"ENTRENADORES", "ESTADO" };
	}

	public void realizaAccion(String accion) {
		if (accion.equals(INSCRITO)) {
			borrar();
			vgcs.dispose();
		} else if (accion.equals(NO_INSCRITO)) {
			inscribirse();
			vgcs.dispose();
		} else {
			JOptionPane.showMessageDialog(null, "No puede realizar ninguna acción en un curso en el que es entrenador");
		}
	}

}
