package giis.demo.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import giis.demo.business.SociosController;
import giis.demo.util.Database;

import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

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
	private JLabel lblNoSocios;
	
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
			pnLista.add(getLblNoSocios(), BorderLayout.NORTH);
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
		tableSocios.setModel(SociosController.setTableModel(db, filter));
		if (tableSocios.getModel().getRowCount() <= 0) {
			tableSocios.setVisible(false);
			lblNoSocios.setVisible(true);
		} else {
			lblNoSocios.setVisible(false);
		}
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
	
	private JTable getTableSocios() {
		if (tableSocios == null) {
			tableSocios = new JTable();
			tableSocios.setColumnSelectionAllowed(true);
			tableSocios.setAutoCreateRowSorter(true);
		}
		return tableSocios;
	}
	private JLabel getLblNoSocios() {
		if (lblNoSocios == null) {
			lblNoSocios = new JLabel("");
			lblNoSocios.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblNoSocios.setVisible(false);
			lblNoSocios.setText(NO_SOCIO_FOUND);
		}
		return lblNoSocios;
	}
}
