package giis.demo.ui.politicaProteccionDatos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import giis.demo.componentes.PanelSolicitudes;
import giis.demo.model.politicaDeDatos.PoliticaDeDatos;

public class VentanaMostrarSolicitudes extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private PoliticaDeDatos politicaDeDatos;
	
	private JPanel pnPrincipal;
	private JLabel lbSolicitudes;
	private JPanel pnLellenda;
	private JPanel pnNombre;
	private JLabel lblDni;
	private JPanel pnFecha;
	private JLabel lblNombre;
	private JPanel pnLugar;
	private JLabel lblApellidos;
	private JPanel pnCategorias;
	private JLabel lblCorreo;
	private JPanel pnBoton;
	private JScrollPane scSolicitudes;
	private JPanel pnSolicitudes;

	/**
	 * Create the frame.
	 * @param politicaDeDatos 
	 */
	public VentanaMostrarSolicitudes(PoliticaDeDatos p) {
		politicaDeDatos = p;
		setTitle("Club Deportivo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBackground(Color.WHITE);
		setMinimumSize(new Dimension(800, 517));
		setBounds(100, 100, 450, 300);
		pnPrincipal = new JPanel();
		pnPrincipal.setBackground(Color.WHITE);
		pnPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(pnPrincipal);
		pnPrincipal.setLayout(new BorderLayout(0, 0));
		pnPrincipal.add(getPnLellenda(), BorderLayout.NORTH);
		pnPrincipal.add(getScSolicitudes(), BorderLayout.CENTER);
		setLocationRelativeTo(null);
		crearPanelesSolicitudes();
	}
	
	private void crearPanelesSolicitudes() {
		PanelSolicitudes elemento;
		getPnSolicitudes().removeAll();
		List<Object[]> solicitudes = politicaDeDatos.cargarSolicitudesDeModificacionDeDatos();
		if(solicitudes.isEmpty()) {
			getPnSolicitudes().add(getLbSolicitudes());
		}
		for (Object[] solicitud : solicitudes) {
			elemento = new PanelSolicitudes(this,solicitud);
			getPnSolicitudes().add(elemento);
		}
		validate();
	}
	
	public void finalizarSolicitud(int solicitudId) {
		politicaDeDatos.finalizarSolicitud(solicitudId);
		crearPanelesSolicitudes();
	}

	private JLabel getLbSolicitudes() {
		if (lbSolicitudes == null) {
			lbSolicitudes = new JLabel("No hay solicitudes pendientes");
			lbSolicitudes.setFont(new Font("Tahoma", Font.PLAIN, 30));
			lbSolicitudes.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lbSolicitudes;
	}
	private JPanel getPnLellenda() {
		if (pnLellenda == null) {
			pnLellenda = new JPanel();
			pnLellenda.setBorder(new LineBorder(new Color(0, 0, 0)));
			pnLellenda.setBackground(Color.WHITE);
			pnLellenda.setLayout(new GridLayout(1, 1, 0, 0));
			pnLellenda.add(getPnNombre());
			pnLellenda.add(getPnFecha());
			pnLellenda.add(getPnLugar());
			pnLellenda.add(getPnCategorias());
			pnLellenda.add(getPnBoton());
		}
		return pnLellenda;
	}
	private JPanel getPnNombre() {
		if (pnNombre == null) {
			pnNombre = new JPanel();
			pnNombre.setBackground(Color.WHITE);
			pnNombre.add(getLblDni());
		}
		return pnNombre;
	}
	private JLabel getLblDni() {
		if (lblDni == null) {
			lblDni = new JLabel("Dni");
			lblDni.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblDni;
	}
	private JPanel getPnFecha() {
		if (pnFecha == null) {
			pnFecha = new JPanel();
			pnFecha.setBackground(Color.WHITE);
			pnFecha.add(getLblNombre());
		}
		return pnFecha;
	}
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre");
			lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblNombre;
	}
	private JPanel getPnLugar() {
		if (pnLugar == null) {
			pnLugar = new JPanel();
			pnLugar.setBackground(Color.WHITE);
			pnLugar.add(getLblApellidos());
		}
		return pnLugar;
	}
	private JLabel getLblApellidos() {
		if (lblApellidos == null) {
			lblApellidos = new JLabel("Apellidos");
			lblApellidos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblApellidos;
	}
	private JPanel getPnCategorias() {
		if (pnCategorias == null) {
			pnCategorias = new JPanel();
			pnCategorias.setBackground(Color.WHITE);
			pnCategorias.add(getLblCorreo());
		}
		return pnCategorias;
	}
	private JLabel getLblCorreo() {
		if (lblCorreo == null) {
			lblCorreo = new JLabel("Contenido solicitud");
			lblCorreo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblCorreo;
	}
	private JPanel getPnBoton() {
		if (pnBoton == null) {
			pnBoton = new JPanel();
			pnBoton.setBackground(Color.WHITE);
		}
		return pnBoton;
	}
	private JScrollPane getScSolicitudes() {
		if (scSolicitudes == null) {
			scSolicitudes = new JScrollPane();
			scSolicitudes.setViewportView(getPnSolicitudes());
		}
		return scSolicitudes;
	}
	private JPanel getPnSolicitudes() {
		if (pnSolicitudes == null) {
			pnSolicitudes = new JPanel();
			pnSolicitudes.setBackground(Color.WHITE);
			pnSolicitudes.setLayout(new GridLayout(0, 1, 0, 0));
		}
		return pnSolicitudes;
	}
}
