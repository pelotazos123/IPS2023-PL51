package giis.demo.model.loggin;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import giis.demo.util.Database;

public class Loggin {
	
	private final static String SQL_NUEVO_LOGGIN = "insert into loggin (dni_socio, contrasena) values(?, ?)";
	
	private final static String SQL_OBTENER_CONTRASEÑA = "select contrasena from loggin where dni_socio = ?";
	private final static String SQL_OBTENER_FECHA_BLOQUEO = "select fin_bloqueo from loggin where dni_socio = ?";
	private final static String SQL_OBTENER_USUARIO = "select dni_socio from loggin where dni_socio = ?";
	private final static String SQL_COMPROBAR_BLOQUEADO = "select fin_bloqueo from loggin where dni_socio = ?";
	
	private final static String SQL_CAMBIAR_CONTRASEÑA = "update loggin set contrasena=? where dni_socio=?";
	private final static String SQL_BLOQUEAR_USUARIO = "update loggin set fin_bloqueo=? where dni_socio=?";
	
	private Database db;
	
	public Loggin(Database db) {
		this.db = db;
	}
	
	public boolean existeUsuario(String dni) {
		List<Object[]> result = db.executeQueryArray(SQL_OBTENER_USUARIO,dni);
		return !result.isEmpty();
	}
	
	public boolean contraseñaCorrecta(String dni,String contraseña) {
		Object[] result = db.executeQueryArray(SQL_OBTENER_CONTRASEÑA,dni).get(0);
		String contraseñaUsuario =  (String) result[0];
		return cifrarContraseña(contraseña).equals(contraseñaUsuario);
	}
	
	public String generarLoggin(String dniUsuario) {
		String nuevaContraseña = UUID.randomUUID().toString();
		db.executeUpdate(SQL_NUEVO_LOGGIN, dniUsuario,cifrarContraseña(nuevaContraseña));
		return nuevaContraseña;
	}
	
	public String restablecerContraseña(String dniUsuario) {
		String nuevaContraseña = UUID.randomUUID().toString();
		db.executeUpdate(SQL_CAMBIAR_CONTRASEÑA, cifrarContraseña(nuevaContraseña), dniUsuario);
		return nuevaContraseña;
	}
	
	public void cambiarContraseña(String dniUsuario, String nuevaContraseña) {
		db.executeUpdate(SQL_CAMBIAR_CONTRASEÑA, cifrarContraseña(nuevaContraseña),dniUsuario);
	}
	
	public boolean comprobarBloqueado(String dniUsuario,LocalDate actual) {
		Object[] result = db.executeQueryArray(SQL_COMPROBAR_BLOQUEADO,dniUsuario).get(0);
		String fecha = (String) result[0];
		if(fecha == null) {
			return false;
		}else {
			
			String[] str = fecha.split("-");
			int año = Integer.parseInt(str[0]);
			int mes = Integer.parseInt(str[1]);
			int dia = Integer.parseInt(str[2]);
			LocalDate fechaDesbloqueo = LocalDate.of(año, mes, dia);
			return !actual.isAfter(fechaDesbloqueo);
		}
	}
	
	public void bloquearUsuario(String dniUsuario) {
		LocalDate fechaActual = LocalDate.now();
		String fecha = ""+fechaActual.getYear()+"-"+fechaActual.getMonthValue()+"-"+(fechaActual.getDayOfMonth()+3);
		db.executeUpdate(SQL_BLOQUEAR_USUARIO,fecha, dniUsuario);
	}
	
	public LocalDate getFechaBloqueo(String dniUsuario) {
		Object[] result = db.executeQueryArray(SQL_OBTENER_FECHA_BLOQUEO,dniUsuario).get(0);
		String fecha = (String) result[0];
		String[] str = fecha.split("-");
		int año = Integer.parseInt(str[0]);
		int mes = Integer.parseInt(str[1]);
		int dia = Integer.parseInt(str[2]);
		return LocalDate.of(año, mes, dia);
	}
	
	private String cifrarContraseña(String contraseña) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(contraseña.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

}
