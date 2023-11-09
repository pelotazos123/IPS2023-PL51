package giis.demo.model.loggin.servicio;

import java.time.LocalDate;

import javax.swing.JOptionPane;

import giis.demo.model.loggin.Correo;
import giis.demo.model.loggin.Loggin;
import giis.demo.util.Database;

public class GestionarLoggin {
	
	private final static String SQL_OBTENER_CORREO_USUARIO = "select email from socios where dni=?";
	
	private Database db;
	private Correo enviarCorreos = new Correo();
	private Loggin loggin;
	private String usuarioActual;
	private int numIntentosLoggin;
	
	public GestionarLoggin(Database db) {
		this.db = db;
		loggin = new Loggin(db);
		numIntentosLoggin = 0;
	}
	
	public boolean existeUsuario(String dniUsuario) {
		if(!dniUsuario.equals(usuarioActual)) {
			usuarioActual = dniUsuario;
			numIntentosLoggin = 0;
		}
		return loggin.existeUsuario(dniUsuario);
	}
	
	public boolean comprobarContraseñaCorrecta(String dniUsuario, String contraseña) {
		numIntentosLoggin = getNumIntentosLoggin() + 1;
		return loggin.contraseñaCorrecta(dniUsuario, contraseña);
	}
	
	public boolean generarLoggin(String dniUsuario){
		String contraseña = loggin.generarLoggin(dniUsuario);
		String correoUsuario = getCorreoDeUsuario(dniUsuario);
		String textoCorreo = "Contraseña generada: "+contraseña+"\nPara modificarla acceda a su cuenta , pulse cambiar contraseña e introduzca la nueva contraseña";
		return enviarCorreos.enviarCorreo(correoUsuario, textoCorreo);
	}
	
	public boolean restablecerContraseña(String dniUsuario,String correoUsuario) {
		if(!comprobarCorreoCorrecto(dniUsuario,correoUsuario)) {
			return false;
		}
		String contraseña = loggin.restablecerContraseña(dniUsuario);
		String textoCorreo = "Contraseña restablecida: "+contraseña+"\nPara modificarla acceda a su cuenta , pulse cambiar contraseña e introduzca la nueva contraseña";
		return enviarCorreos.enviarCorreo(getCorreoDeUsuario(dniUsuario), textoCorreo);
	}

	public void cambiarContraseña(String dniUsuario, char[] nuevaContraseña) {
		
		loggin.cambiarContraseña(dniUsuario, nuevaContraseña);
	}
	
	public boolean comprobarBloqueado(String dniUsuario, LocalDate fechaAcual) {
		return loggin.comprobarBloqueado(dniUsuario,fechaAcual);
	}
	
	public LocalDate getFechaBloqueo(String dniUsuario) {
		return loggin.getFechaBloqueo(dniUsuario);
	}

	public int getNumIntentosLoggin() {
		return numIntentosLoggin;
	}
	
	public void bloquearUsuario(String dniUsuario) {
		numIntentosLoggin = 0;
		loggin.bloquearUsuario(dniUsuario);
	}
	
	public String getCorreoDeUsuario(String dniUsuario) {
		Object[] result = db.executeQueryArray(SQL_OBTENER_CORREO_USUARIO,dniUsuario).get(0);
		return (String) result[0];
	}
	
	private boolean comprobarCorreoCorrecto(String dniUsuario, String correoUsuario) {
		String correoVerificado = getCorreoDeUsuario(dniUsuario);
		if(correoUsuario.equals(correoVerificado)) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public boolean comprobarNuevaContraseñaValida(char[] nuevaContraseña) {
		int mayus = 0;
		int minus = 0;
		int num = 0;
		if (nuevaContraseña.length == 0) {
			JOptionPane.showMessageDialog(null,"La contraseña no puede estar vacía.",
					"Cambiar contraseña", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		for (int i = 0; i < nuevaContraseña.length; i++) {
			char character = nuevaContraseña[i];
			if(Character.isAlphabetic(character) && Character.isLowerCase(character)) {
				minus++;
			}else if(Character.isAlphabetic(character) && Character.isUpperCase(character)) {
				mayus++;
			}else if(Character.isDigit(character)) {
				num++;
			}else {
				return false;
			}
		}

		if (mayus > 0 && minus > 0 && num > 0) {
			return true;
		} else {
			JOptionPane.showMessageDialog(null,"La contraseña debe contener mayusculas, minusculas y al menos un numero",
					"Cambiar contraseña", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
}
