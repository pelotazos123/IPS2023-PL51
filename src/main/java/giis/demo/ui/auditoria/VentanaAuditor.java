package giis.demo.ui.auditoria;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import giis.demo.model.auditoria.Auditoria;

public class VentanaAuditor extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Auditoria auditor;
	
	private JPanel pnPrincipal;
	private JButton btGenerarPDF;
	private JButton btGenerarHojaDeCalculo;
	private JPanel pnBotones;
	private JLabel lbMensaje;

	/**
	 * Create the frame.
	 */
	public VentanaAuditor(Auditoria a) {
		auditor = a;
		setTitle("Club Deportivo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.WHITE);
		setMinimumSize(new Dimension(50, 50));
		setBounds(100, 100, 790, 625);
		setLocationRelativeTo(null);
		pnPrincipal = new JPanel();
		pnPrincipal.setBackground(Color.WHITE);
		pnPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(pnPrincipal);
		pnPrincipal.setLayout(new GridLayout(0, 1, 0, 0));
		pnPrincipal.add(getLbMensaje());
		pnPrincipal.add(getPnBotones());
	}

	private JButton getBtGenerarPDF() {
		if (btGenerarPDF == null) {
			btGenerarPDF = new JButton("Generar Pdf");
			btGenerarPDF.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					generarPDF();
				}
			});
			btGenerarPDF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		}
		return btGenerarPDF;
	}
	
	private void generarPDF() {
		JFileChooser guardar = new JFileChooser();
		int res = guardar.showSaveDialog(null);
		if (res != JFileChooser.APPROVE_OPTION)
			return;
		guardar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		File archivo = guardar.getSelectedFile();
		
		try {
			auditor.generarPDF(archivo);
			
			JOptionPane.showMessageDialog(this,"PDF generado",
					"Auditor", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			System.err.println("Error al crear el PDF: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,"Error al crear el PDF",
					"Auditor", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void generarHojaCalculo() {
		JFileChooser guardar = new JFileChooser();
		int res = guardar.showSaveDialog(null);
		if (res != JFileChooser.APPROVE_OPTION)
			return;
		guardar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		File archivo = guardar.getSelectedFile();
		
		try {
			auditor.generarHojaCalculo(archivo);
			JOptionPane.showMessageDialog(this,"Hoja de calculo generada",
					"Auditor", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			System.err.println("Error al crear la hoja de cálculo: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,"Error al crear la hoja de calculo",
					"Auditor", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private JButton getBtGenerarHojaDeCalculo() {
		if (btGenerarHojaDeCalculo == null) {
			btGenerarHojaDeCalculo = new JButton("Hoja de calculo");
			btGenerarHojaDeCalculo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					generarHojaCalculo();
				}
			});
			btGenerarHojaDeCalculo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		}
		return btGenerarHojaDeCalculo;
	}
	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnBotones.getLayout();
			flowLayout.setHgap(20);
			pnBotones.setBackground(Color.WHITE);
			pnBotones.add(getBtGenerarPDF());
			pnBotones.add(getBtGenerarHojaDeCalculo());
		}
		return pnBotones;
	}
	private JLabel getLbMensaje() {
		if (lbMensaje == null) {
			lbMensaje = new JLabel("Bienvenido Auditor");
			lbMensaje.setHorizontalAlignment(SwingConstants.CENTER);
			lbMensaje.setFont(new Font("Tahoma", Font.PLAIN, 30));
		}
		return lbMensaje;
	}
}
