package giis.demo.model.loggin;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Correo {
	
	private final Properties properties = System.getProperties();
	
	private final static String CORREO = "clubdeportivoips@gmail.com";
	private final static String HOST = "smtp.gmail.com";
	private final static String PORT= "587";
	private final static String CONTRASEÑA = "vguz ubjs oksa ucvc";
	
	private Session sesion;
	
	public Correo() {
		properties.put("mail.smtp.host", HOST);
		properties.put("mail.smtp.port", PORT);
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
		
		properties.put("mail.smtp.user", CORREO);
		properties.put("mail.smtp.clave", CONTRASEÑA);

		
		sesion = Session.getDefaultInstance(properties);
	}
	
	public void enviarCorreo(String correoUsuario, String textoContraseña) throws MessagingException {
		MimeMessage mensaje = new MimeMessage(sesion);
		mensaje.setFrom(new InternetAddress(CORREO));
		mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(correoUsuario));
		mensaje.setSubject("Nueva contraseña");
		mensaje.setText(textoContraseña);
		Transport t = sesion.getTransport("smtp");
		t.connect(properties.getProperty("mail.smtp.host"), CORREO, CONTRASEÑA);
		t.sendMessage(mensaje, mensaje.getAllRecipients());
		t.close();
	}

}
