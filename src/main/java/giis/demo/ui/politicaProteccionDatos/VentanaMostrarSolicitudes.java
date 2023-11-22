package giis.demo.ui.politicaProteccionDatos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import giis.demo.componentes.PanelSolicitudes;
import giis.demo.model.politicaDeDatos.PoliticaDeDatos;

public class VentanaMostrarSolicitudes extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private PoliticaDeDatos politicaDeDatos;
	
	private JPanel pnPrincipal;
	private JLabel lbSolicitudes;

	/**
	 * Create the frame.
	 * @param politicaDeDatos 
	 */
	public VentanaMostrarSolicitudes(PoliticaDeDatos p) {
		politicaDeDatos = p;
		setTitle("Club Deportivo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBackground(Color.WHITE);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(1400, 477));
		setBounds(100, 100, 450, 300);
		pnPrincipal = new JPanel();
		pnPrincipal.setBackground(Color.WHITE);
		pnPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(pnPrincipal);
		pnPrincipal.setLayout(new GridLayout(0, 1, 0, 0));
		crearPanelesSolicitudes();
	}
	
	private void crearPanelesSolicitudes() {
		pnPrincipal.removeAll();
		PanelSolicitudes elemento;
		List<Object[]> solicitudes = politicaDeDatos.cargarSolicitudesDeModificacionDeDatos();
		if(solicitudes.isEmpty()) {
			pnPrincipal.add(getLbSolicitudes());
		}
		for (Object[] solicitud : solicitudes) {
			elemento = new PanelSolicitudes(this,solicitud);
			pnPrincipal.add(elemento);
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
}
