package giis.demo.ui;

import javax.swing.JDialog;
import javax.swing.JFormattedTextField;

import giis.demo.business.InstalacionController;
import giis.demo.business.ReservationController;
import giis.demo.model.Cursillos;
import giis.demo.model.Instalacion;
import giis.demo.util.Database;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import com.toedter.calendar.JDateChooser;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

public class VentanaCrearCursillos extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final String INTRODUZCA_AQUI = "DNI del entrenador";
	private JPanel pnCreacion;
	private JButton btnCrear;
	private Database db;
	private JPanel pnCentro;
	private JPanel pnNorte;
	private JComboBox<Instalacion> cbInstalaciones;
	private JDateChooser dcInicio;
	private JDateChooser dcFinal;
	private JSpinner spnMaxSocios;
	private JLabel lblMaxSocios;
	private JLabel lblDiaInicio;
	private JLabel lblDiaFinal;
	private JTextField txtHoraFin;
	private JTextField txtHoraInicio;
	private JLabel lblHoraInicio;
	private JLabel lblNewLabel_1;
	private JPanel pnSur;
	private JCheckBox chkLunes;
	private JCheckBox chkMartes;
	private JCheckBox chkMiercoles;
	private JCheckBox chkJueves;
	private JCheckBox chkViernes;
	private JCheckBox chkSabado;
	private JTextField txtCosteCurso;
	private JLabel lblImporteCurso;
	private JPanel pnCentroSur;
	private JPanel pnCentroCentro;
	private JButton btnAddTrainer;
	private JButton btnRemoveTrainer;
	private JPanel pnBotones;
	private JPanel pnTxtEntrenadores;
	private List<JTextField> listaTxtFields;
	private CheckBoxLimiterListener limiter;

	public VentanaCrearCursillos(Database db) {
		this.db = db;
		limiter = new CheckBoxLimiterListener();
		listaTxtFields = new ArrayList<>();
		getContentPane().setBackground(Color.WHITE);
		getContentPane().add(getPnCreacion(), BorderLayout.CENTER);
		getContentPane().add(getBtnCrear(), BorderLayout.SOUTH);
		setTitle("Reservas");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 596, 521);

	}

	private JPanel getPnCreacion() {
		if (pnCreacion == null) {
			pnCreacion = new JPanel();
			pnCreacion.setBackground(Color.WHITE);
			TitledBorder border = new TitledBorder(null, "Creaci\u00F3n de cursillos", TitledBorder.LEADING, TitledBorder.TOP, null, null);
			Font fuente = border.getTitleFont();
			border.setTitleFont(fuente.deriveFont(Font.BOLD, 16));
			pnCreacion.setBorder(border);
			pnCreacion.setLayout(new BorderLayout(0, 0));
			pnCreacion.add(getPnNorte(), BorderLayout.NORTH);
			pnCreacion.add(getPnCentro(), BorderLayout.CENTER);
			pnCreacion.add(getPnSur(), BorderLayout.SOUTH);
		}
		return pnCreacion;
	}
	private JButton getBtnCrear() {
		if (btnCrear == null) {
			btnCrear = new JButton("Crear curso");
			btnCrear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearCurso();
				}
			});
		}
		return btnCrear;
	}
	
	private void crearCurso() {
				
	}
	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.setBackground(Color.WHITE);
			pnCentro.setLayout(new BorderLayout());
			pnCentro.add(getPnCentroCentro(), BorderLayout.CENTER);
			pnCentro.add(getPnCentroSur(), BorderLayout.SOUTH);
		}
		return pnCentro;
	}
	private JPanel getPnNorte() {
		if (pnNorte == null) {
			pnNorte = new JPanel();
			TitledBorder border = new TitledBorder(null, "Instalaci\u00F3n del curso", TitledBorder.LEADING, TitledBorder.TOP, null, null);
			Font fuente = border.getTitleFont();
			border.setTitleFont(fuente.deriveFont(Font.BOLD, 13));
			pnNorte.setBorder(border);
			pnNorte.setBackground(Color.WHITE);
			pnNorte.setLayout(new BorderLayout(0, 0));
			pnNorte.add(getCbInstalaciones(), BorderLayout.CENTER);
		}
		return pnNorte;
	}
	private JComboBox<Instalacion> getCbInstalaciones() {
		if (cbInstalaciones == null) {
			cbInstalaciones = new JComboBox<Instalacion>();
			cbInstalaciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					prepareCursillo();
				}
			});
			cbInstalaciones.setFont(new Font("Tahoma", Font.PLAIN, 15));
			cbInstalaciones.setBackground(Color.WHITE);
			cbInstalaciones.setModel(new DefaultComboBoxModel<Instalacion>(InstalacionController.getInstalaciones(db)));
		}
		return cbInstalaciones;
	}
	
	private void prepareCursillo() {
		spnMaxSocios.setValue(1);
		Instalacion instalacion = ((Instalacion)cbInstalaciones.getSelectedItem());
		spnMaxSocios.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), 
					Integer.valueOf(instalacion.getMaxCurso()), Integer.valueOf(1)));
		spnMaxSocios.setToolTipText("Max: " + instalacion.getMaxCurso() + " - Min: " + instalacion.getMinCurso());
		
	}
	private JDateChooser getDcInicio() {
		if (dcInicio == null) {
			dcInicio = new JDateChooser();
			dcInicio.setFont(new Font("Tahoma", Font.PLAIN, 15));
			dcInicio.setDateFormatString("yyyy-MM-dd");
		}
		return dcInicio;
	}
	private JDateChooser getDcFinal() {
		if (dcFinal == null) {
			dcFinal = new JDateChooser();
			dcFinal.setFont(new Font("Tahoma", Font.PLAIN, 15));
			dcFinal.setDateFormatString("yyyy-MM-dd");
		}
		return dcFinal;
	}
	private JSpinner getSpnMaxSocios() {
		if (spnMaxSocios == null) {
			spnMaxSocios = new JSpinner();
			spnMaxSocios.setFont(new Font("Tahoma", Font.PLAIN, 15));
			cbInstalaciones.setSelectedIndex(0);
		}
		return spnMaxSocios;
	}
	private JLabel getLblMaxSocios() {
		if (lblMaxSocios == null) {
			lblMaxSocios = new JLabel("Nº de plazas: ");
			lblMaxSocios.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return lblMaxSocios;
	}
	private JLabel getLblDiaInicio() {
		if (lblDiaInicio == null) {
			lblDiaInicio = new JLabel("Dia de inicio:");
			lblDiaInicio.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return lblDiaInicio;
	}
	private JLabel getLblDiaFinal() {
		if (lblDiaFinal == null) {
			lblDiaFinal = new JLabel("Dia de finalización:");
			lblDiaFinal.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return lblDiaFinal;
	}
	private JTextField getTxtHoraFin() {
		if (txtHoraFin == null) {
			MaskFormatter maskFormatter = null;
	        
	        try {
	            maskFormatter = new MaskFormatter("##:00");
	            maskFormatter.setPlaceholderCharacter('_');
	        } catch (ParseException e) {
	            System.err.println("Formato inválido");
	        }
	        txtHoraFin = new JFormattedTextField(maskFormatter);
	        txtHoraFin.setFont(new Font("Tahoma", Font.PLAIN, 15));
	        txtHoraFin.setToolTipText("HH:mm");
	        txtHoraFin.setColumns(10);
		}
		return txtHoraFin;
	}
	private JTextField getTxtHoraInicio() {
		if (txtHoraInicio == null) {
			MaskFormatter maskFormatter = null;
	        
	        try {
	            maskFormatter = new MaskFormatter("##:00");
	            maskFormatter.setPlaceholderCharacter('_');
	        } catch (ParseException e) {
	            System.err.println("Formato inválido");
	        }
	        txtHoraInicio = new JFormattedTextField(maskFormatter);
	        txtHoraInicio.setFont(new Font("Tahoma", Font.PLAIN, 15));
	        txtHoraInicio.setToolTipText("HH:mm");
			txtHoraInicio.setColumns(10);
		}
		return txtHoraInicio;
	}
	private JLabel getLblHoraInicio() {
		if (lblHoraInicio == null) {
			lblHoraInicio = new JLabel("Hora de inicio de la clase: ");
			lblHoraInicio.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return lblHoraInicio;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("Hora de finalización de la clase: ");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return lblNewLabel_1;
	}
	private JPanel getPnSur() {
		if (pnSur == null) {
			pnSur = new JPanel();
			pnSur.setToolTipText("Máximo " + Cursillos.MAX_DIAS_SEMANA + " días a la semana.");
			TitledBorder border = new TitledBorder(null, "D\u00EDas de realizaci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null);
			Font fuente = border.getTitleFont();
			border.setTitleFont(fuente.deriveFont(Font.BOLD, 13));
			pnSur.setBorder(border);
			pnSur.setBackground(Color.WHITE);
			pnSur.setLayout(new GridLayout(0, 6, 0, 0));
			pnSur.add(getChkLunes());
			pnSur.add(getChkMartes());
			pnSur.add(getChkMiercoles());
			pnSur.add(getChkJueves());
			pnSur.add(getChkViernes());
			pnSur.add(getChkSabado());
		}
		return pnSur;
	}
	private JCheckBox getChkLunes() {
		if (chkLunes == null) {
			chkLunes = new JCheckBox("Lunes");
			chkLunes.addActionListener(limiter);
			chkLunes.setToolTipText("Máximo " + Cursillos.MAX_DIAS_SEMANA + " días a la semana.");
			chkLunes.setBackground(Color.WHITE);
			chkLunes.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return chkLunes;
	}
	private JCheckBox getChkMartes() {
		if (chkMartes == null) {
			chkMartes = new JCheckBox("Martes");
			chkMartes.addActionListener(limiter);
			chkMartes.setToolTipText("Máximo " + Cursillos.MAX_DIAS_SEMANA + " días a la semana.");
			chkMartes.setBackground(Color.WHITE);
			chkMartes.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return chkMartes;
	}
	private JCheckBox getChkMiercoles() {
		if (chkMiercoles == null) {
			chkMiercoles = new JCheckBox("Miercoles");
			chkMiercoles.addActionListener(limiter);
			chkMiercoles.setToolTipText("Máximo " + Cursillos.MAX_DIAS_SEMANA + " días a la semana.");
			chkMiercoles.setBackground(Color.WHITE);
			chkMiercoles.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return chkMiercoles;
	}
	private JCheckBox getChkJueves() {
		if (chkJueves == null) {
			chkJueves = new JCheckBox("Jueves");
			chkJueves.addActionListener(limiter);
			chkJueves.setToolTipText("Máximo " + Cursillos.MAX_DIAS_SEMANA + " días a la semana.");
			chkJueves.setBackground(Color.WHITE);
			chkJueves.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return chkJueves;
	}
	private JCheckBox getChkViernes() {
		if (chkViernes == null) {
			chkViernes = new JCheckBox("Viernes");
			chkViernes.addActionListener(limiter);
			chkViernes.setToolTipText("Máximo " + Cursillos.MAX_DIAS_SEMANA + " días a la semana.");
			chkViernes.setBackground(Color.WHITE);
			chkViernes.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return chkViernes;
	}
	private JCheckBox getChkSabado() {
		if (chkSabado == null) {
			chkSabado = new JCheckBox("Sábado");
			chkSabado.addActionListener(limiter);
			chkSabado.setToolTipText("Máximo " + Cursillos.MAX_DIAS_SEMANA + " días a la semana.");
			chkSabado.setBackground(Color.WHITE);
			chkSabado.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return chkSabado;
	}
	
	private class CheckBoxLimiterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			limitSelection();			
		}
		
	}
	
	private void limitSelection() {
	        int selectedCount = 0;

	        for (Component component : getPnSur().getComponents()) {
	            if (component instanceof JCheckBox) {
	                JCheckBox checkBox = (JCheckBox) component;
	                if (checkBox.isSelected()) {
	                    selectedCount++;
	                    if (selectedCount > Cursillos.MAX_DIAS_SEMANA) {
	                        checkBox.setSelected(false);
	                    }
	                }
	            }
	        }
	    }
	 
	private JTextField getTxtCosteCurso() {
		if (txtCosteCurso == null) {
			MaskFormatter maskFormatter = null;
	        
	        try {
	            maskFormatter = new MaskFormatter("###,##€");
	            maskFormatter.setPlaceholderCharacter('0');
	        } catch (ParseException e) {
	            System.err.println("Formato inválido");
	        }
			txtCosteCurso = new JFormattedTextField(maskFormatter);
			txtCosteCurso.setToolTipText("Sólo números");
			txtCosteCurso.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtCosteCurso.setColumns(10);
		}
		return txtCosteCurso;
	}
	private JLabel getLblImporteCurso() {
		if (lblImporteCurso == null) {
			lblImporteCurso = new JLabel("Coste del curso: ");
			lblImporteCurso.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return lblImporteCurso;
	}
	private JPanel getPnCentroSur() {
		if (pnCentroSur == null) {
			pnCentroSur = new JPanel();
			pnCentroSur.setBackground(Color.WHITE);
			TitledBorder border = new TitledBorder(null, "Entrenadores del curso", TitledBorder.LEADING, TitledBorder.TOP, null, null);
			Font fuente = border.getTitleFont();
			border.setTitleFont(fuente.deriveFont(Font.BOLD, 13));
			pnCentroSur.setBorder(border);
			pnCentroSur.setBorder(border);
			pnCentroSur.setLayout(new BorderLayout(0, 0));
			pnCentroSur.add(getPnBotones(), BorderLayout.NORTH);
			pnCentroSur.add(getPnTxtEntrenadores(), BorderLayout.SOUTH);
		}
		return pnCentroSur;
	}
	private JPanel getPnCentroCentro() {
		if (pnCentroCentro == null) {
			pnCentroCentro = new JPanel();
			pnCentroCentro.setBackground(Color.WHITE);
			TitledBorder border = new TitledBorder(null, "Parámetros del curso", TitledBorder.LEADING, TitledBorder.TOP, null, null);
			Font fuente = border.getTitleFont();
			border.setTitleFont(fuente.deriveFont(Font.BOLD, 13));
			pnCentroCentro.setBorder(border);
			pnCentroCentro.setLayout(new GridLayout(0, 2, 0, 0));
			pnCentroCentro.add(getLblDiaInicio());
			pnCentroCentro.add(getDcInicio());
			pnCentroCentro.add(getLblDiaFinal());
			pnCentroCentro.add(getDcFinal());
			pnCentroCentro.add(getLblMaxSocios());
			pnCentroCentro.add(getSpnMaxSocios());
			pnCentroCentro.add(getLblHoraInicio());
			pnCentroCentro.add(getTxtHoraInicio());
			pnCentroCentro.add(getLblNewLabel_1());
			pnCentroCentro.add(getTxtHoraFin());
			pnCentroCentro.add(getLblImporteCurso());
			pnCentroCentro.add(getTxtCosteCurso());
		}
		return pnCentroCentro;
	}
	private JButton getBtnAddTrainer() {
		if (btnAddTrainer == null) {
			btnAddTrainer = new JButton("+");
			btnAddTrainer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					añadirEntrenador();
				}
			});
		}
		return btnAddTrainer;
	}
	
	private void añadirEntrenador() {
		if (listaTxtFields.size() < ReservationController.MAX_ENTRENADORES) {
			JTextField nuevo = prepareTxtField();
			listaTxtFields.add(nuevo);
			getPnTxtEntrenadores().add(nuevo);
			getPnTxtEntrenadores().repaint();
			revalidate();
		} else {
			JOptionPane.showMessageDialog(null, "Ya has alcanzado el máximo de entrenadores.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private JTextField prepareTxtField() {
		JTextField nuevo = new JTextField();
		nuevo.setText(INTRODUZCA_AQUI);
		nuevo.setForeground(Color.LIGHT_GRAY);
		nuevo.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				if (nuevo.getText().isEmpty()) {
					nuevo.setForeground(Color.LIGHT_GRAY);
					nuevo.setText(INTRODUZCA_AQUI);
		        }
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if (nuevo.getText().equals(INTRODUZCA_AQUI)) {
					nuevo.setText("");
					nuevo.setForeground(Color.BLACK);
		        }
			}
		});
		nuevo.setEditable(true);
		nuevo.setEnabled(true);
		nuevo.setColumns(10);
		nuevo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		nuevo.setHorizontalAlignment(SwingConstants.CENTER);
		return nuevo;
	}
	
	private JButton getBtnRemoveTrainer() {
		if (btnRemoveTrainer == null) {
			btnRemoveTrainer = new JButton("-");
			btnRemoveTrainer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					eliminaEntrenador();
				}
			});
		}
		return btnRemoveTrainer;
	}
	
	private void eliminaEntrenador() {
		if (listaTxtFields.size() > ReservationController.EMPTY) {
			JTextField txtBorrar = listaTxtFields.remove(listaTxtFields.size()-1);
			getPnTxtEntrenadores().remove(txtBorrar);
			getPnTxtEntrenadores().repaint();
			getPnTxtEntrenadores().revalidate();					
		}
	}
	
	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setBackground(Color.WHITE);
			pnBotones.add(getBtnAddTrainer());
			pnBotones.add(getBtnRemoveTrainer());
		}
		return pnBotones;
	}
	private JPanel getPnTxtEntrenadores() {
		if (pnTxtEntrenadores == null) {
			pnTxtEntrenadores = new JPanel();
			pnTxtEntrenadores.setBackground(Color.WHITE);
			pnTxtEntrenadores.setLayout(new GridLayout(0, 4, 0, 0));
		}
		return pnTxtEntrenadores;
	}
}
