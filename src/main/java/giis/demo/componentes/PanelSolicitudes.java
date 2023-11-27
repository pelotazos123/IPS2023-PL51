package giis.demo.componentes;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import giis.demo.ui.politicaProteccionDatos.VentanaMostrarSolicitudes;

public class PanelSolicitudes extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private VentanaMostrarSolicitudes v;
	private Object[] solicitud;
	private JPanel pnDni;
	private JLabel lbDni;
	private JPanel pnNombre;
	private JLabel lbNombre;
	private JPanel pnApellido;
	private JLabel lbApellido;
	private JPanel pnBoton;
	private JButton btCerrarSolicitud;
	private JScrollPane scSolicitud;
	private JTextArea txSolicitud;
	

	/**
	 * Create the panel.
	 * @param solicitud 
	 */
	public PanelSolicitudes(VentanaMostrarSolicitudes v, Object[] solicitud) {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBackground(Color.WHITE);
		this.v = v;
		this.solicitud = solicitud;
		setLayout(new GridLayout(1, 0, 0, 0));
		add(getPnDni());
		add(getPnNombre());
		add(getPnApellido());
		add(getScSolicitud());
		add(getPnBoton());
		cargarDatos();
	}

	private void cargarDatos() {
		getLbDni().setText((String) solicitud[1]);
		getLbNombre().setText((String) solicitud[2]);
		getLbApellido().setText((String) solicitud[3]);
		getTxSolicitud().setText((String) solicitud[4]);
	}

	private JPanel getPnDni() {
		if (pnDni == null) {
			pnDni = new JPanel();
			pnDni.setBackground(Color.WHITE);
			pnDni.setLayout(new GridLayout(0, 1, 0, 0));
			pnDni.add(getLbDni());
		}
		return pnDni;
	}
	private JLabel getLbDni() {
		if (lbDni == null) {
			lbDni = new JLabel("null");
			lbDni.setHorizontalAlignment(SwingConstants.CENTER);
			lbDni.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lbDni;
	}
	private JPanel getPnNombre() {
		if (pnNombre == null) {
			pnNombre = new JPanel();
			pnNombre.setBackground(Color.WHITE);
			pnNombre.setLayout(new GridLayout(0, 1, 0, 0));
			pnNombre.add(getLbNombre());
		}
		return pnNombre;
	}
	private JLabel getLbNombre() {
		if (lbNombre == null) {
			lbNombre = new JLabel("null");
			lbNombre.setHorizontalAlignment(SwingConstants.CENTER);
			lbNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lbNombre;
	}
	private JPanel getPnApellido() {
		if (pnApellido == null) {
			pnApellido = new JPanel();
			pnApellido.setBackground(Color.WHITE);
			pnApellido.setLayout(new GridLayout(0, 1, 0, 0));
			pnApellido.add(getLbApellido());
		}
		return pnApellido;
	}
	private JLabel getLbApellido() {
		if (lbApellido == null) {
			lbApellido = new JLabel("null");
			lbApellido.setHorizontalAlignment(SwingConstants.CENTER);
			lbApellido.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lbApellido;
	}
	private JPanel getPnBoton() {
		if (pnBoton == null) {
			pnBoton = new JPanel();
			pnBoton.setBackground(Color.WHITE);
			pnBoton.setLayout(new GridLayout(0, 1, 0, 0));
			pnBoton.add(getBtCerrarSolicitud());
		}
		return pnBoton;
	}
	private JButton getBtCerrarSolicitud() {
		if (btCerrarSolicitud == null) {
			btCerrarSolicitud = new JButton("Finalizar solicitud");
			btCerrarSolicitud.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					v.finalizarSolicitud((int) solicitud[0]);
				}
			});
			btCerrarSolicitud.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return btCerrarSolicitud;
	}
	private JScrollPane getScSolicitud() {
		if (scSolicitud == null) {
			scSolicitud = new JScrollPane();
			scSolicitud.setViewportView(getTxSolicitud());
		}
		return scSolicitud;
	}
	private JTextArea getTxSolicitud() {
		if (txSolicitud == null) {
			txSolicitud = new JTextArea();
			txSolicitud.setWrapStyleWord(true);
			txSolicitud.setLineWrap(true);
			txSolicitud.setEditable(false);
		}
		return txSolicitud;
	}
}
