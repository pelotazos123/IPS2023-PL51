package giis.demo.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import giis.demo.logica.CheckFichero;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class PayByTransf extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lbTitulo;
	private JLabel lbInstrucciones;
	private JButton btAceptar;
	private JButton btDeclinar;
	private JButton btInsertarFichero;
	private JFileChooser selector;
	private File file;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PayByTransf frame = new PayByTransf();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PayByTransf() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setTitle("Pago por transferencia");
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLbTitulo());
		contentPane.add(getLbInstrucciones());
		contentPane.add(getBtAceptar());
		contentPane.add(getBtDeclinar());
		contentPane.add(getBtInsertarFichero());
	}
	private JLabel getLbTitulo() {
		if (lbTitulo == null) {
			lbTitulo = new JLabel("PAGO POR TRANSFERENCIA");
			lbTitulo.setFont(new Font("Arial", Font.BOLD, 28));
			lbTitulo.setForeground(Color.BLACK);
			lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lbTitulo.setBounds(0, 11, 434, 53);
		}
		return lbTitulo;
	}
	private JLabel getLbInstrucciones() {
		if (lbInstrucciones == null) {
			lbInstrucciones = new JLabel();
			lbInstrucciones.setFont(new Font("Tahoma", Font.PLAIN, 14));
			String mensaje = "Se debe realizar una transferencia a la cuenta\n" +
	                "ESXX-XXXX-XXXX-XXXX-XXXX-XXXX con un importe de XX,XX€\n" +
	                "y con el siguiente concepto: Pago licencia NºXXXXX";
			lbInstrucciones.setText("<html><div style='text-align: center;'>" + mensaje + "</div></html>");
			lbInstrucciones.setHorizontalAlignment(SwingConstants.CENTER);
			lbInstrucciones.setBounds(10, 78, 414, 132);
		}
		return lbInstrucciones;
	}
	private JButton getBtAceptar() {
		if (btAceptar == null) {
			btAceptar = new JButton("Aceptar");
			btAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(file == null)
						JOptionPane.showMessageDialog(null, "No se ha insertado ningún fichero");
					else
						if(CheckFichero.checkFichero(file)) {
							JOptionPane.showMessageDialog(null, "El fichero ha sido validado");
							cierraVentana();
						} else {
							JOptionPane.showMessageDialog(null, "El fichero no tiene el formato correcto");
						}
							
				}
			});
			btAceptar.setBounds(74, 307, 129, 37);
		}
		return btAceptar;
	}
	private JButton getBtDeclinar() {
		if (btDeclinar == null) {
			btDeclinar = new JButton("Declinar");
			btDeclinar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cierraVentana();
				}
			});
			btDeclinar.setBounds(228, 307, 129, 37);
		}
		return btDeclinar;
	}
	
	private void cierraVentana() {
		this.dispose();
	}

	private JButton getBtInsertarFichero() {
		if (btInsertarFichero == null) {
			btInsertarFichero = new JButton("Insertar fichero");
			btInsertarFichero.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cargaFichero();
				}
			});
			btInsertarFichero.setMnemonic('f');
			btInsertarFichero.setBounds(131, 221, 168, 61);
		}
		return btInsertarFichero;
	}

	private void cargaFichero() {
		int resp = getSelector().showOpenDialog(null);
		if (resp == JFileChooser.APPROVE_OPTION) {
			this.file = getSelector().getSelectedFile();
			getBtInsertarFichero().setText(file.getName());
		}
	}
	
	private JFileChooser getSelector() {
		if (selector == null) {
			selector = new JFileChooser();
			selector.setMultiSelectionEnabled(false);
			selector.setFileFilter(new FileNameExtensionFilter("Archivos txt", "txt"));
			String ruta = System.getProperty("user.home") + "/desktop";
			selector.setCurrentDirectory(new File(ruta));
		}
		return selector;
	}
}
