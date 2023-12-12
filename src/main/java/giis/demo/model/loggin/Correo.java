package giis.demo.model.loggin;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Correo implements Runnable{
	
	private final Properties properties = System.getProperties();
	
	private final static String CORREO = "clubdeportivoips@gmail.com";
	private final static String HOST = "smtp.gmail.com";
	private final static String PORT= "587";
	private final static String CONTRASEÑA = "vguz ubjs oksa ucvc";
	
	public final static String TIPO_TXT_MAIL_PLANO = "plano";
	public final static String TIPO_TXT_MAIL_HTML = "html";
	
	private Session sesion;
	private Thread t;
	
	private String correoUsuario;
	private String asunto;
	private String texto;
	private String typeTxt;
	private boolean enviado;
	
	public Correo() {
		properties.put("mail.smtp.host", HOST);
		properties.put("mail.smtp.port", PORT);
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
		
		properties.put("mail.smtp.user", CORREO);
		properties.put("mail.smtp.clave", CONTRASEÑA);
		
		
		sesion = Session.getDefaultInstance(properties);
		t = new Thread(this);
	}
	
	public Correo(String correoUsuario, String asunto, String texto, String typeTxt) {
		this();
		this.correoUsuario = correoUsuario;
		this.asunto = asunto;
		this.texto = texto;
		this.typeTxt = typeTxt;
	}
	
	public boolean enviarCorreo(String correoUsuario, String asunto, String texto) {
		this.correoUsuario = correoUsuario;
		this.asunto = asunto;
		this.texto = texto;
		this.typeTxt = TIPO_TXT_MAIL_PLANO;
		enviado = true;
		t.run();
		return enviado;
		
	}
	
	public void mandarCorreo(String correoUsuario, String asunto, String textoContraseña, String typeText) throws MessagingException {
		MimeMessage mensaje = new MimeMessage(sesion);
		mensaje.setFrom(new InternetAddress(CORREO));
		mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(correoUsuario));
		mensaje.setSubject(asunto, "UTF-8");
		if (typeText.equals(TIPO_TXT_MAIL_PLANO)) // Se manda el correo de con un cuerpo escrito en texto plano
			mensaje.setText(textoContraseña);
		else if (typeText.equals(TIPO_TXT_MAIL_HTML)) // Se manda el correo de con un cuerpo escrito en HTML y en utf-8
			mensaje.setContent(textoContraseña, "text/html; charset=utf-8");
		Transport t = sesion.getTransport("smtp");
		t.connect(properties.getProperty("mail.smtp.host"), CORREO, CONTRASEÑA);
		t.sendMessage(mensaje, mensaje.getAllRecipients());
		t.close();
	}

	@Override
	public void run() {
		try {
			mandarCorreo(correoUsuario, asunto, texto, typeTxt);
		} catch (MessagingException e) {
			System.err.println("Ha ocurrido un error al enviar el correo");
			e.printStackTrace();
			enviado = false;
		}
		
	}

}
