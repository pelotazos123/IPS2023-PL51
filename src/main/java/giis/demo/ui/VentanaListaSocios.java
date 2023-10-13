package giis.demo.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.ActionListener;
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
	private DefaultListModel<String> modeloListaSocios;

	private final static String MOCK_SOCIO_LISTA = "Pedrito Garcia López - Cuota JOVEN - Hombre";
	
	/**
	 * Create the dialog.
	 */
	public VentanaListaSocios() {
		setBounds(100, 100, 870, 618);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(getPnLista());
		contentPanel.add(getBtnPanel(), BorderLayout.NORTH);
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
		}
		return btnFiltro;
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
	
	// To be changed to a model class "Socios"
	private JList<String> getListSocios() {
		if (listSocios == null) {
			listSocios = new JList<String>();
			modeloListaSocios = new DefaultListModel<String>();
			listSocios.setModel(modeloListaSocios);
			modeloListaSocios.addElement(MOCK_SOCIO_LISTA);
		}
		return listSocios;
	}
}
