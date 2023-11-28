package giis.demo.componentes;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import giis.demo.model.TiposCuotas;
import giis.demo.model.competiciones.Competicion;
import giis.demo.model.competiciones.EstadoCompeticion;
import giis.demo.ui.competiciones.VentanaInscripcionCompeticiones;

public class PanelCompeticion extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VentanaInscripcionCompeticiones vC;
	private Competicion compe;
	private JPanel pnNombre;
	private JPanel pnFecha;
	private JPanel pnLugar;
	private JPanel pnBoton;
	private JLabel lbNombre;
	private JLabel lbFecha;
	private JLabel lbLugar;
	private JTextArea txCategorias;
	private JButton btInscribirse;
	private JButton btSacarListado;
	
	public PanelCompeticion(VentanaInscripcionCompeticiones vC, Competicion c, boolean inscrito) {
		setBackground(Color.WHITE);
		setBorder(new LineBorder(Color.BLACK));
		this.vC = vC;
		setLayout(new GridLayout(1, 0, 0, 0));
		add(getPnNombre());
		add(getPnFecha());
		add(getPnLugar());
		add(getTxCategorias());
		add(getPnBoton());
		compe = c;
		cargarDatos(inscrito);
		if (compe.getEstado().equals(EstadoCompeticion.CANCELADA)) {
			setBackground(Color.GRAY);
		}
	}

	private void cargarDatos(boolean inscrito) {
		getLbNombre().setText(compe.getNombre());
		getLbFecha().setText(compe.getFecha().toString());
		getLbLugar().setText(compe.getLugar());
		List<TiposCuotas> categorias = compe.getCategorias();
		String texto = "";
		for(TiposCuotas categoria : categorias) {
			texto+= categoria+"\n";
		}
		getTxCategorias().setText(texto);	
		System.out.println(compe.getEstado());
		if (compe.getEstado().equals(EstadoCompeticion.ABIERTA)) {
			if(vC.getTramitarLicencia().esDirectivo()) {
				getPnBoton().add(getBtSacarListado());
			}else {
				getPnBoton().add(getBtInscribirse());
				if(inscrito) {
					getBtInscribirse().setEnabled(false);
				}
			}
		} else {
			getPnBoton().add(getBtSacarListado());
			getBtSacarListado().setEnabled(false);
			getBtSacarListado().setText("CANCELADA");
			getTxCategorias().setBackground(Color.GRAY);
			getPnBoton().setBackground(Color.GRAY);
			getPnFecha().setBackground(Color.GRAY);
			getPnLugar().setBackground(Color.GRAY);
			getPnNombre().setBackground(Color.GRAY);
			
		}
		
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
	private JPanel getPnFecha() {
		if (pnFecha == null) {
			pnFecha = new JPanel();
			pnFecha.setBackground(Color.WHITE);
			pnFecha.setLayout(new GridLayout(0, 1, 0, 0));
			pnFecha.add(getLbFecha());
		}
		return pnFecha;
	}
	private JPanel getPnLugar() {
		if (pnLugar == null) {
			pnLugar = new JPanel();
			pnLugar.setBackground(Color.WHITE);
			pnLugar.setLayout(new GridLayout(1, 0, 0, 0));
			pnLugar.add(getLbLugar());
		}
		return pnLugar;
	}
	private JPanel getPnBoton() {
		if (pnBoton == null) {
			pnBoton = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnBoton.getLayout();
			flowLayout.setAlignOnBaseline(true);
			pnBoton.setBackground(Color.WHITE);
		}
		return pnBoton;
	}
	private JLabel getLbNombre() {
		if (lbNombre == null) {
			lbNombre = new JLabel("nombre");
			lbNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lbNombre.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lbNombre;
	}
	private JLabel getLbFecha() {
		if (lbFecha == null) {
			lbFecha = new JLabel("fecha");
			lbFecha.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lbFecha.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lbFecha;
	}
	private JLabel getLbLugar() {
		if (lbLugar == null) {
			lbLugar = new JLabel("lugar");
			lbLugar.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lbLugar.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lbLugar;
	}
	private JTextArea getTxCategorias() {
		if (txCategorias == null) {
			txCategorias = new JTextArea();
			txCategorias.setLineWrap(true);
			txCategorias.setWrapStyleWord(true);
			txCategorias.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txCategorias.setEditable(false);
		}
		return txCategorias;
	}
	private JButton getBtInscribirse() {
		if (btInscribirse == null) {
			btInscribirse = new JButton("Inscribirse");
			btInscribirse.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btInscribirse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					inscribirse();
				}
			});
		}
		return btInscribirse;
	}
	
	private void inscribirse() {
		vC.inscribirseEnCompeticion(compe);
		getBtInscribirse().setEnabled(false);
	}
	
	private JButton getBtSacarListado() {
		if (btSacarListado == null) {
			btSacarListado = new JButton("Sacar listado");
			btSacarListado.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btSacarListado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					vC.sacarListadoCompeticion(compe);
				}
			});
		}
		return btSacarListado;
	}
}
