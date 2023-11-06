package giis.demo.ui.competiciones;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import giis.demo.componentes.PanelCompeticion;
import giis.demo.model.CrearLicencias.servicio.TramitarLicencia;
import giis.demo.model.competiciones.Competicion;
import giis.demo.model.competiciones.servicio.GestionarCompeticiones;

public class VentanaInscripcionCompeticiones extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private GestionarCompeticiones gestorCompeticiones;
	private TramitarLicencia tramitarLicencia;
	private JPanel pnPrincipal;
	private JPanel pnLellenda;
	private JPanel pnCompeticiones;
	private JScrollPane srcCompeticiones;
	private JPanel pnNombre;
	private JPanel pnFecha;
	private JPanel pnLugar;
	private JPanel pnCategorias;
	private JPanel pnBoton;
	private JLabel lbNombre;
	private JLabel lbFecha;
	private JLabel lbLugar;
	private JLabel lbCategorias;
	private JButton btnFiltro;

	/**
	 * Create the frame.
	 */
	public VentanaInscripcionCompeticiones(GestionarCompeticiones gestorCompeticiones, TramitarLicencia t) {
		this.gestorCompeticiones = gestorCompeticiones;
		tramitarLicencia = t;
		setMinimumSize(new Dimension(1050, 477));
		setBackground(Color.WHITE);
		setTitle("Club Deportivo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 870, 618);
		pnPrincipal = new JPanel();
		pnPrincipal.setBorder(new TitledBorder(null, "Competiciones", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnPrincipal.setBackground(Color.WHITE);
		setLocationRelativeTo(null);

		setContentPane(pnPrincipal);
		pnPrincipal.setLayout(new BorderLayout(0, 0));
		pnPrincipal.add(getPnLellenda(), BorderLayout.NORTH);
		pnPrincipal.add(getSrcCompeticiones(), BorderLayout.CENTER);
		crearPanelesCompeticiones();
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
	private JScrollPane getSrcCompeticiones() {
		if (srcCompeticiones == null) {
			srcCompeticiones = new JScrollPane();
			srcCompeticiones.setBackground(Color.WHITE);
			srcCompeticiones.setViewportView(getPnCompeticiones());
		}
		return srcCompeticiones;
	}
	
	private JPanel getPnCompeticiones() {
		if (pnCompeticiones == null) {
			pnCompeticiones = new JPanel();
			pnCompeticiones.setBackground(Color.WHITE);
			pnCompeticiones.setLayout(new GridLayout(0, 1, 0, 0));
		}
		return pnCompeticiones;
	}
	
	private void crearPanelesCompeticiones() {
		pnCompeticiones.removeAll();
		PanelCompeticion elemento;
		List<Competicion> competicionesDisponibles = null;
		if(tramitarLicencia.esDirectivo()) {
			competicionesDisponibles = gestorCompeticiones.getTodasLasCompeticiones();
			getPnBoton().add(getBtnFiltro());
		}else {
			competicionesDisponibles = gestorCompeticiones.getCompeticionesDisponibles(tramitarLicencia.getSocio().getTipoCuota());
		}
		for(int i = 0; i < competicionesDisponibles.size(); i++) {
			elemento = new PanelCompeticion(this,competicionesDisponibles.get(i));
			getPnCompeticiones().add(elemento);
		}
	}

	public TramitarLicencia getTramitarLicencia() {
		return tramitarLicencia;
	}
	
	public void inscribirseEnCompeticion(Competicion compe) {
		int competicionId = compe.getId();
		int socioId = tramitarLicencia.getSocio().getId();
		
		if(gestorCompeticiones.comprobarSocioYaInscrito(competicionId, socioId)) {
			JOptionPane.showMessageDialog(this,"Ya estas inscrito",
					"Competiciones", JOptionPane.INFORMATION_MESSAGE);
		}else if(gestorCompeticiones.comprobarSiSePuedeInscribir(competicionId, socioId)) {
			gestorCompeticiones.inscribirSocio(competicionId, socioId);
			JOptionPane.showMessageDialog(this,"Ha sido inscrito en "+compe.getNombre(),
					"Competiciones", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(this,"No se puede inscribir a dos competiciones en un mismo dia",
					"Competiciones", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void sacarListadoCompeticion(Competicion compe) {
		JFileChooser guardar = new JFileChooser();
		guardar.showSaveDialog(null);
		guardar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		File archivo = guardar.getSelectedFile();
		gestorCompeticiones.generarListaSocios(compe.getId(), archivo);
		
	}
	private JPanel getPnNombre() {
		if (pnNombre == null) {
			pnNombre = new JPanel();
			pnNombre.setBackground(Color.WHITE);
			pnNombre.add(getLbNombre());
		}
		return pnNombre;
	}
	private JPanel getPnFecha() {
		if (pnFecha == null) {
			pnFecha = new JPanel();
			pnFecha.setBackground(Color.WHITE);
			pnFecha.add(getLbFecha());
		}
		return pnFecha;
	}
	private JPanel getPnLugar() {
		if (pnLugar == null) {
			pnLugar = new JPanel();
			pnLugar.setBackground(Color.WHITE);
			pnLugar.add(getLbLugar());
		}
		return pnLugar;
	}
	private JPanel getPnCategorias() {
		if (pnCategorias == null) {
			pnCategorias = new JPanel();
			pnCategorias.setBackground(Color.WHITE);
			pnCategorias.add(getLbCategorias());
		}
		return pnCategorias;
	}
	private JPanel getPnBoton() {
		if (pnBoton == null) {
			pnBoton = new JPanel();
			pnBoton.setBackground(Color.WHITE);
		}
		return pnBoton;
	}
	private JLabel getLbNombre() {
		if (lbNombre == null) {
			lbNombre = new JLabel("Nombre");
			lbNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lbNombre;
	}
	private JLabel getLbFecha() {
		if (lbFecha == null) {
			lbFecha = new JLabel("Fecha");
			lbFecha.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lbFecha;
	}
	private JLabel getLbLugar() {
		if (lbLugar == null) {
			lbLugar = new JLabel("Lugar");
			lbLugar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lbLugar;
	}
	private JLabel getLbCategorias() {
		if (lbCategorias == null) {
			lbCategorias = new JLabel("Categorias");
			lbCategorias.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lbCategorias;
	}
	private JButton getBtnFiltro() {
		if (btnFiltro == null) {
			btnFiltro = new JButton("Filtrar");
			btnFiltro.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnFiltro.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaFiltros();
				}
			});
		}
		return btnFiltro;
	}
	
	private void mostrarVentanaFiltros() {
		VentanaFiltrarCompeticiones vF = new VentanaFiltrarCompeticiones(this,gestorCompeticiones);
		vF.setLocationRelativeTo(this);
		vF.setModal(true);
		vF.setVisible(true);
		
	}
	
	public void filtrarCompeticiones(List<Competicion> competiciones) {
		pnCompeticiones.removeAll();
		PanelCompeticion elemento;
		List<Competicion> competicionesDisponibles = competiciones;
		for(int i = 0; i < competicionesDisponibles.size(); i++) {
			elemento = new PanelCompeticion(this,competicionesDisponibles.get(i));
			getPnCompeticiones().add(elemento);
		}
		validate();
	}
}
