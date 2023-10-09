package giis.demo.ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Vista de la pantalla que muestra las carreras activas y permite interactuar con ellas.
 * <br/>Se ha generado con WindowBulder y modificado para ser conforme a MVC teniendo en cuenta:
 * - Se elimina main (es invocada desde CarrerasMain) y se incluye Title en el frame
 * - No se incluye ningun handler de eventos pues estos van en el controlador
 * - Las tablas se encierran en JOptionPane para que se puedan visualizar las cabeceras
 * - Se asinga nombre a las tablas si se van a automatizar la ejecucion de pruebas
 * - Incluye al final los metodos adicionales necesarios para acceder al UI desde el controlador
 */
public class AsambleasView {

	private JFrame frame;
	private JPanel contentPanel;
	private JPanel pnEleccionAsambleas;
	private JPanel pnFormularioAsamblea;
	private JButton btnOrdinaria;
	private JButton btnExtraordinaria;
	private JLabel lblFecha;
	private JLabel lblConvocatoria1;
	private JLabel lblConvocatoria2;
	private JButton btnConvocar;
	private JTextField txtFecha;
	private JTextField txtConv1;
	private JTextField txtConv2;

	/**
	 * Create the application.
	 */
	public AsambleasView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Asambleas");
		frame.setName("Asambleas");
		frame.setBounds(100, 100, 584, 381);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPanel);
		contentPanel.setLayout(new CardLayout(0, 0));
		
		contentPanel.add(getPnEleccionAsambleas(),"EleccionAsambleas");
		contentPanel.add(getPnFormularioAsamblea(),"FormularioAsamblea");
	}
	
	public JPanel getPnEleccionAsambleas() {
		if (pnEleccionAsambleas == null) {
			pnEleccionAsambleas = new JPanel();
			pnEleccionAsambleas.setBounds(0, 0, 0, 0);
			pnEleccionAsambleas.setLayout(null);
			pnEleccionAsambleas.add(getBtnOrdinaria());
			pnEleccionAsambleas.add(getBtnExtraordinaria());
		}
		return pnEleccionAsambleas;
	}
	
	public JPanel getPnFormularioAsamblea() {
		if (pnFormularioAsamblea == null) {
			pnFormularioAsamblea = new JPanel();
			pnFormularioAsamblea.setBounds(0, 0, 0, 0);
			pnFormularioAsamblea.setLayout(null);
			pnFormularioAsamblea.add(getLblFecha());
			pnFormularioAsamblea.add(getLblConvocatoria1());
			pnFormularioAsamblea.add(getLblConvocatoria2());
			pnFormularioAsamblea.add(getBtnConvocar());
			pnFormularioAsamblea.add(getTxtFecha());
			pnFormularioAsamblea.add(getTxtConv1());
			pnFormularioAsamblea.add(getTxtConv2());
		}
		return pnFormularioAsamblea;
	}

	public JFrame getFrame() { 
		return this.frame; 
	}
	
	public JButton getBtnOrdinaria() {
		if (btnOrdinaria == null) {
			btnOrdinaria = new JButton("Ordinaria");
			btnOrdinaria.setBounds(100, 124, 150, 55);
		}
		return btnOrdinaria;
	}
	
	public JButton getBtnExtraordinaria() {
		if (btnExtraordinaria == null) {
			btnExtraordinaria = new JButton("Extraordinaria");
			btnExtraordinaria.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					((CardLayout)contentPanel.getLayout()).show(contentPanel,"FormularioAsamblea");
				}
			});
			btnExtraordinaria.setBounds(300, 124, 150, 55);
		}
		return btnExtraordinaria;
	}
	
	public JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha:");
			lblFecha.setBounds(149, 78, 69, 24);
		}
		return lblFecha;
	}
	public JLabel getLblConvocatoria1() {
		if (lblConvocatoria1 == null) {
			lblConvocatoria1 = new JLabel("Hora de 1° convocatoria:");
			lblConvocatoria1.setBounds(149, 124, 153, 39);
		}
		return lblConvocatoria1;
	}
	public JLabel getLblConvocatoria2() {
		if (lblConvocatoria2 == null) {
			lblConvocatoria2 = new JLabel("Hora de 2° convocatoria:");
			lblConvocatoria2.setBounds(149, 185, 153, 39);
		}
		return lblConvocatoria2;
	}
	public JButton getBtnConvocar() {
		if (btnConvocar == null) {
			btnConvocar = new JButton("Convocar");
			btnConvocar.setBackground(new Color(0, 128, 0));
			btnConvocar.setBounds(221, 282, 120, 39);
		}
		return btnConvocar;
	}
	public JTextField getTxtFecha() {
		if (txtFecha == null) {
			txtFecha = new JTextField();
			txtFecha.setText(new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime()));
			txtFecha.setBounds(307, 80, 86, 20);
			txtFecha.setColumns(10);
		}
		return txtFecha;
	}
	public JTextField getTxtConv1() {
		if (txtConv1 == null) {
			txtConv1 = new JTextField();
			txtConv1.setColumns(10);
			txtConv1.setBounds(307, 133, 86, 20);
		}
		return txtConv1;
	}
	public JTextField getTxtConv2() {
		if (txtConv2 == null) {
			txtConv2 = new JTextField();
			txtConv2.setColumns(10);
			txtConv2.setBounds(307, 194, 86, 20);
		}
		return txtConv2;
	}
	
}
