package giis.demo.ui;

import java.awt.BorderLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

import giis.demo.business.SociosController;
import giis.demo.logica.JDateChooserEditor;
import giis.demo.util.Database;

import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class VentanaListaSocios extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel pnLista;
	private JPanel btnPanel;
	private JButton btnFiltro;
	private JPanel pnlEsteBtns;
	private JButton btnActualizar;
	private JLabel lblText;
	private JScrollPane scrlListaSocios;
	private Database db;

	private static final String NO_SOCIO_FOUND = "Ningún socio encontrado";
	private JTable tableSocios;
	
	/**
	 * Create the dialog.
	 */
	public VentanaListaSocios(Database db) {
		setTitle("Gestion deportiva: Lista de socios");
		this.db = db;
		setBounds(100, 100, 870, 618);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(getPnLista());
		contentPanel.add(getBtnPanel(), BorderLayout.NORTH);
		actualizar("");
	}

	private JPanel getPnLista() {
		if (pnLista == null) {
			pnLista = new JPanel();
			pnLista.setBackground(Color.WHITE);
			pnLista.setBorder(new LineBorder(new Color(0, 0, 0), 3));
			pnLista.setLayout(new BorderLayout(0, 0));
			pnLista.add(getScrlListaSocios(), BorderLayout.CENTER);
		}
		return pnLista;
	}
	
	private JPanel getBtnPanel() {
		if (btnPanel == null) {
			btnPanel = new JPanel();
			btnPanel.setBackground(Color.WHITE);
			btnPanel.setLayout(new BorderLayout(0, 0));
			btnPanel.add(getBtnFiltro(), BorderLayout.EAST);
			btnPanel.add(getPnlEsteBtns(), BorderLayout.WEST);
		}
		return btnPanel;
	}
	
	private JButton getBtnFiltro() {
		if (btnFiltro == null) {
			btnFiltro = new JButton("Filtrar");
			btnFiltro.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaFiltros();
				}
			});
		}
		return btnFiltro;
	}
	
	private void mostrarVentanaFiltros() {
		VentanaFiltro vF = new VentanaFiltro(this);
		vF.setLocationRelativeTo(this);
		vF.setModal(true);
		vF.setVisible(true);
	}
	
	private JPanel getPnlEsteBtns() {
		if (pnlEsteBtns == null) {
			pnlEsteBtns = new JPanel();
			pnlEsteBtns.setBackground(Color.WHITE);
			pnlEsteBtns.setLayout(new BorderLayout(0, 0));
			pnlEsteBtns.add(getBtnActualizar());
			pnlEsteBtns.add(getLblText(), BorderLayout.WEST);
		}
		return pnlEsteBtns;
	}
	
	private JButton getBtnActualizar() {
		if (btnActualizar == null) {
			btnActualizar = new JButton("↻");
			btnActualizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actualizar("");
				}
			});
			btnActualizar.setBackground(Color.WHITE);
			btnActualizar.setBorder(new LineBorder(new Color(0, 0, 0)));
			btnActualizar.setPreferredSize(new Dimension(30, 30));
		}
		return btnActualizar;
	}
	
	protected void actualizar(String filter) {
		tableSocios.setModel(SociosController.setTableModel(db, filter, tableSocios));
		settingCuotas();
		settingDateChooser();
		settingGenre();
		
		if (tableSocios.getModel().getRowCount() <= 0) {
			JOptionPane.showMessageDialog(null, NO_SOCIO_FOUND, "ERROR", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void settingDateChooser() {
		tableSocios.getColumnModel().getColumn(9).setCellEditor(new JDateChooserEditor(new JCheckBox()));
	}

	private void settingGenre() {
		JComboBox<String> generos = new JComboBox<String>();
		generos.addItem("HOMBRE");
		generos.addItem("MUJER");
		tableSocios.getColumnModel().getColumn(8).setCellEditor(new DefaultCellEditor(generos));
	}

	private void settingCuotas() {
		JComboBox<String> cuotas = new JComboBox<String>();
		cuotas.addItem("SUB18");
		cuotas.addItem("SENIOR");
		cuotas.addItem("VETERANO");
		
		tableSocios.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(cuotas));
	}

	private JLabel getLblText() {
		if (lblText == null) {
			lblText = new JLabel("Lista de socios:           ");
			lblText.setFont(new Font("Sitka Text", Font.PLAIN, 15));
			lblText.setBackground(Color.WHITE);
		}
		return lblText;
	}
	
	private JScrollPane getScrlListaSocios() {
		if (scrlListaSocios == null) {
			scrlListaSocios = new JScrollPane();
			scrlListaSocios.setViewportView(getTableSocios());
		}
		return scrlListaSocios;
	}
	
	@SuppressWarnings("serial")
	private JTable getTableSocios() {
		if (tableSocios == null) {
			tableSocios = new JTable() {
				@Override // Establece que las columnas 1 y 2 no sean editables (ID de socio y DNI)
				public boolean isCellEditable(int row, int column) {
				    return column == 0 || column==1 ? false : true;
				}
				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public Class getColumnClass(int columnIndex) {
					return (getValueAt(0, columnIndex).getClass());
				}
			};
			tableSocios.setBackground(Color.WHITE);
			tableSocios.setColumnSelectionAllowed(true);
			tableSocios.setAutoCreateRowSorter(true);
			tableSocios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableSocios.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			tableSocios.setFillsViewportHeight(true);
			tableSocios.setColumnSelectionAllowed(true);
			tableSocios.setRowSelectionAllowed(true);
		}
		return tableSocios;
	}

}
