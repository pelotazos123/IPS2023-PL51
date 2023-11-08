package giis.demo.ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

	private Calendar date;
	private JFrame frame;
	private JPanel contentPanel;
	private JPanel pnEleccionAsambleas;
	private JPanel pnFormularioAsambleaOrd;
	private JPanel pnFormularioAsambleaExt;
	private JButton btnOrdinaria;
	private JButton btnExtraordinaria;
	private JLabel lblFecha;
	private JLabel lblYear;
	private JLabel lblMonth;
	private JLabel lblConvocatoria1;
	private JLabel lblConvocatoria2;
	private JButton btnConvocarOrd;
	private JButton btnConvocarExt;
	private JTextField txtFecha;
	private JComboBox<String> cbYear;
	private JComboBox<String> cbMonth;
	private JTextField txtConv1;
	private JTextField txtConv2;
	private JTextField txtOrdenDiaOrd;
	private JTextField txtOrdenDiaExt;
	private JLabel lblOrdenDelDiaOrd;
	private JLabel lblOrdenDelDiaExt;

	/**
	 * Create the application.
	 */
	public AsambleasView(Calendar date) {
		this.date = date;
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
		contentPanel.add(getPnFormularioAsambleaOrd(),"FormularioAsambleaOrd");
		contentPanel.add(getPnFormularioAsambleaExt(),"FormularioAsambleaExt");
	}
	
	public Calendar getDate() {
		return date;
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
	
	public JPanel getPnFormularioAsambleaOrd() {
		if (pnFormularioAsambleaOrd == null) {
			pnFormularioAsambleaOrd = new JPanel();
			pnFormularioAsambleaOrd.setBounds(0, 0, 0, 0);
			pnFormularioAsambleaOrd.setLayout(null);
			
			pnFormularioAsambleaOrd.add(getLblYear());
			pnFormularioAsambleaOrd.add(getCbYear());
			pnFormularioAsambleaOrd.add(getLblMonth());
			pnFormularioAsambleaOrd.add(getCbMonth());
			pnFormularioAsambleaOrd.add(getTxtOrdenDiaOrd());
			pnFormularioAsambleaOrd.add(getLblOrdenDelDiaOrd());
			pnFormularioAsambleaOrd.add(getBtnConvocarOrd());
		}
		return pnFormularioAsambleaOrd;
	}
	public JPanel getPnFormularioAsambleaExt() {
		if (pnFormularioAsambleaExt == null) {
			pnFormularioAsambleaExt = new JPanel();
			pnFormularioAsambleaExt.setBounds(0, 0, 0, 0);
			pnFormularioAsambleaExt.setLayout(null);

			pnFormularioAsambleaExt.add(getLblFecha());
			pnFormularioAsambleaExt.add(getLblConvocatoria1());
			pnFormularioAsambleaExt.add(getLblConvocatoria2());
			pnFormularioAsambleaExt.add(getTxtFecha());
			pnFormularioAsambleaExt.add(getTxtConv1());
			pnFormularioAsambleaExt.add(getTxtConv2());
			pnFormularioAsambleaExt.add(getTxtOrdenDiaExt());
			pnFormularioAsambleaExt.add(getLblOrdenDelDiaExt());
			pnFormularioAsambleaExt.add(getBtnConvocarExt());
		}
		return pnFormularioAsambleaExt;
	}

	public JFrame getFrame() { 
		return this.frame; 
	}
	
	public JButton getBtnOrdinaria() {
		if (btnOrdinaria == null) {
			btnOrdinaria = new JButton("Ordinaria");
			btnOrdinaria.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					((CardLayout)contentPanel.getLayout()).show(contentPanel,"FormularioAsambleaOrd");
				}
			});
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
					((CardLayout)contentPanel.getLayout()).show(contentPanel,"FormularioAsambleaExt");
				}
			});
			btnExtraordinaria.setBounds(300, 124, 150, 55);
		}
		return btnExtraordinaria;
	}
	
	public JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha:");
			lblFecha.setBounds(149, 37, 69, 24);
		}
		return lblFecha;
	}
	public JLabel getLblMonth() {
		if (lblMonth == null) {
			lblMonth = new JLabel("Mes:");
			lblMonth.setBounds(149, 111, 69, 24);
		}
		return lblMonth;
	}
	public JLabel getLblYear() {
		if (lblYear == null) {
			lblYear = new JLabel("Año:");
			lblYear.setBounds(149, 58, 69, 24);
		}
		return lblYear;
	}
	public JLabel getLblConvocatoria1() {
		if (lblConvocatoria1 == null) {
			lblConvocatoria1 = new JLabel("Hora de 1° convocatoria:");
			lblConvocatoria1.setBounds(149, 83, 153, 39);
		}
		return lblConvocatoria1;
	}
	public JLabel getLblConvocatoria2() {
		if (lblConvocatoria2 == null) {
			lblConvocatoria2 = new JLabel("Hora de 2° convocatoria:");
			lblConvocatoria2.setBounds(149, 144, 153, 39);
		}
		return lblConvocatoria2;
	}
	private JLabel getLblOrdenDelDiaOrd() {
		if (lblOrdenDelDiaOrd == null) {
			lblOrdenDelDiaOrd = new JLabel("Orden del día:");
			lblOrdenDelDiaOrd.setBounds(149, 163, 153, 39);
		}
		return lblOrdenDelDiaOrd;
	}
	private JLabel getLblOrdenDelDiaExt() {
		if (lblOrdenDelDiaExt == null) {
			lblOrdenDelDiaExt = new JLabel("Orden del día:");
			lblOrdenDelDiaExt.setBounds(149, 194, 153, 39);
		}
		return lblOrdenDelDiaExt;
	}
	public JTextField getTxtFecha() {
		if (txtFecha == null) {
			txtFecha = new JTextField();
			txtFecha.setText(new SimpleDateFormat("yyyy-MM-dd").format(getDate().getTime()));
			txtFecha.setBounds(307, 39, 86, 20);
			txtFecha.setColumns(10);
		}
		return txtFecha;
	}
	public JComboBox<String> getCbYear() {
		if (cbYear == null) {
			cbYear = new JComboBox<String>();

			String[] años = new String[5];
			for(int i = 0; i < 5; i++) {
				años[i] = "" + (getDate().get(Calendar.YEAR) + i);
			}
			cbYear.setModel(new DefaultComboBoxModel<String>(años));
			cbYear.setBounds(307, 59, 106, 22);
		}
		return cbYear;
	}
	public JComboBox<String> getCbMonth() {
		if (cbMonth == null) {
			cbMonth = new JComboBox<String>();

			String[] meses = {
		            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
		            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
		    };
			cbMonth.setModel(new DefaultComboBoxModel<String>(meses));
			cbMonth.setBounds(307, 112, 106, 22);
		}
		return cbMonth;
	}
	public JTextField getTxtConv1() {
		if (txtConv1 == null) {
			txtConv1 = new JTextField();
			txtConv1.setColumns(10);
			txtConv1.setBounds(307, 92, 86, 20);
		}
		return txtConv1;
	}
	public JTextField getTxtConv2() {
		if (txtConv2 == null) {
			txtConv2 = new JTextField();
			txtConv2.setColumns(10);
			txtConv2.setBounds(307, 153, 86, 20);
		}
		return txtConv2;
	}
	public JTextField getTxtOrdenDiaOrd() {
		if (txtOrdenDiaOrd == null) {
			txtOrdenDiaOrd = new JTextField();
			txtOrdenDiaOrd.setColumns(10);
			txtOrdenDiaOrd.setBounds(307, 172, 153, 20);
		}
		return txtOrdenDiaOrd;
	}
	public JTextField getTxtOrdenDiaExt() {
		if (txtOrdenDiaExt == null) {
			txtOrdenDiaExt = new JTextField();
			txtOrdenDiaOrd.setColumns(10);
			txtOrdenDiaExt.setBounds(307, 203, 153, 20);
		}
		return txtOrdenDiaExt;
	}
	public JButton getBtnConvocarOrd() {
		if (btnConvocarOrd == null) {
			btnConvocarOrd = new JButton("Convocar");
			btnConvocarOrd.setForeground(new Color(255, 255, 255));
			btnConvocarOrd.setBackground(new Color(0, 128, 0));
			btnConvocarOrd.setBounds(221, 282, 120, 39);
		}
		return btnConvocarOrd;
	}
	public JButton getBtnConvocarExt() {
		if (btnConvocarExt == null) {
			btnConvocarExt = new JButton("Convocar");
			btnConvocarExt.setForeground(new Color(255, 255, 255));
			btnConvocarExt.setBackground(new Color(0, 128, 0));
			btnConvocarExt.setBounds(221, 282, 120, 39);
		}
		return btnConvocarExt;
	}
}
