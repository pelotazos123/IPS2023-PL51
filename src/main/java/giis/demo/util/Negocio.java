package giis.demo.util;

//import java.io.File;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import ips.huSp1.h1.CheckFichero;

public class Negocio {
	
	public static final int MUJER = 0;
	public static final int HOMBRE = 1;
	
	public static Double getTestCooper(double distance) {
		return (22.351 * distance) + 11.288;
	}
	
	public static Double getTestRockport(int peso, int edad, int sexo, double tiempo, int pulsaciones) {
		return 132.7 - (0.17 * peso) - (0.39 * edad) + (6.31 * sexo) - (3.27 * tiempo) - (0.156 * pulsaciones);
	}
	
	
//	private static final String CHECKLICENCE = "";
//	private static final String URL = "";
//	private static final String USER = "";
//	private static final String PASSWORD = "";
	/*
	 * Comprueba el formato del fichero
	 */
//	public boolean checkComprobante(File file) {
//		return CheckFichero.checkFichero(file);
//	}
//	
//	/*
//	 * Consulta a la base de datos si el numero de licencia pertenece a esa persona
//	 */
//	public boolean checkLicence(int num) {
//		//TODO: Consulta a la base de datos
//		try {
//			Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
//			PreparedStatement pst = c.prepareStatement(CHECKLICENCE);
//			pst.setInt(1, num);
//			ResultSet rs = pst.executeQuery();
//			if(rs.next())
//				return true;
//			return false;
//		} catch (SQLException e) {	
//			e.printStackTrace();
//		}
//		return false;
//	}
	
	
}
