package giis.demo.ui;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import giis.demo.business.SociosController;
import giis.demo.model.Socio;
import giis.demo.util.Database;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

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
	private JList<String> listSocios;
	private List<Socio> listaSocios;
	private DefaultListModel<String> modeloListaSocios;
	private Database db;

	private static final String NO_SOCIO_FOUND = "Ningún socio encontrado";
	
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
		actualizar();
	}

	private JPanel getPnLista() {
		if (pnLista == null) {
			pnLista = new JPanel();
			pnLista.setBackground(Color.WHITE);
			pnLista.setBorder(new LineBorder(new Color(0, 0, 0), 3));
			pnLista.add(getScrlListaSocios());
			pnLista.add(getListSocios());
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
					actualizar();
				}
			});
			btnActualizar.setBackground(Color.WHITE);
			btnActualizar.setBorder(new LineBorder(new Color(0, 0, 0)));
			btnActualizar.setPreferredSize(new Dimension(30, 30));
		}
		return btnActualizar;
	}
	
	private void actualizar() {
		listaSocios = SociosController.cargarSocios(db, "", "");
		listaSocios.stream().filter(e -> e.getTipoCuota().equals("Cuota Joven"));
		modeloListaSocios.removeAllElements();
		for (int i = 0; i < listaSocios.size(); i++) {
			modeloListaSocios.addElement(listaSocios.get(i).toStringList());
		}
		
	}
	
	protected void actualizar(String filter, String order) {
		List<Socio> result = SociosController.cargarSocios(db, filter, order);
		modeloListaSocios.removeAllElements();
		for (int i = 0; i < result.size(); i++) {
			modeloListaSocios.addElement(result.get(i).toStringList());
		}
		if (result.isEmpty())
			modeloListaSocios.addElement(NO_SOCIO_FOUND);
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
			scrlListaSocios.setViewportView(listSocios);
		}
		return scrlListaSocios;
	}
	
	private JList<String> getListSocios() {
		if (listSocios == null) {
			listSocios = new JList<String>();
			modeloListaSocios = new DefaultListModel<String>();
			listSocios.setModel(modeloListaSocios);
		}
		return listSocios;
	}
}
