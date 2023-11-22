package giis.demo.ui;

import javax.swing.JDialog;
import javax.swing.JFormattedTextField;

import giis.demo.business.InstalacionController;
import giis.demo.business.ReservationController;
import giis.demo.business.SociosController;
import giis.demo.model.Cursillos;
import giis.demo.model.Instalacion;
import giis.demo.util.Database;
import giis.demo.util.Util;

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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
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
	private JLabel lblHoraFin;
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
	private ReservationController rC;
	private List <DayOfWeek> dias;
	private JTextField txtNombreCurso;
	private JLabel lblNombreCurso;

	public VentanaCrearCursillos(Database db) {
		this.db = db;
		dias = new ArrayList<DayOfWeek>();
		rC = new ReservationController(db);
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
		String[] horaInicio = getTxtHoraInicio().getText().split(":");
		String[] horaFin = getTxtHoraFin().getText().split(":");
		
		List<String> entrenadores = new ArrayList<String>();
		
		String nombreCurso = getTxtNombreCurso().getText();
		
		if (!checkCurso(nombreCurso))
			return;
		
		LocalDate diaInicio = getDcInicio().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate diaFin = getDcFinal().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		if (!checkTrainers(entrenadores))
			return;
		
		String toParseCost = getTxtCosteCurso().getText().substring(0, getTxtCosteCurso().getText().length() - 1);
		
		if (!rC.creacionCurso(nombreCurso, diaInicio, diaFin, LocalTime.of(Integer.parseInt(horaInicio[0]),Integer.parseInt(horaInicio[1]))
				, LocalTime.of(Integer.parseInt(horaFin[0]),Integer.parseInt(horaFin[1])), entrenadores, dias,
				((Instalacion)getCbInstalaciones().getSelectedItem()), Double.parseDouble(toParseCost), (int)getSpnMaxSocios().getValue())) {
			return;
		}
		
		JOptionPane.showMessageDialog(null, "Curso creado correctamente.", "Exito!", JOptionPane.INFORMATION_MESSAGE);
		this.dispose();
	}

	private boolean checkTrainers(List<String> entrenadores) {
		for (JTextField txtEntrenador : listaTxtFields) {
			String dni = txtEntrenador.getText();
			if (SociosController.isTrainer(dni, db))
				entrenadores.add(dni);
			else {
				JOptionPane.showMessageDialog(null, "El DNI: " + dni + " no es de un entrenador o no existe.", "ERROR", JOptionPane.ERROR_MESSAGE);
				return false;
			}
				
		}
		return true;
	}

	private boolean checkCurso(String nombreCurso) {
		if (nombreCurso.isEmpty()) {
			JOptionPane.showMessageDialog(null, "El nombre del curso no puede estar vacío.", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if (!Util.checkHourInserted(getTxtHoraInicio().getText(), getTxtHoraFin().getText(), ReservationController.HORA_MAXIMA_CURSO, ReservationController.HORA_MINIMA_CURSO)) 
			return false;
		
		if (listaTxtFields.size() == 0) {
			JOptionPane.showMessageDialog(null, "Hay que añadir al menos a un entrenador.", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if (getTxtCosteCurso().getText().equals("000,00€")) {
			JOptionPane.showMessageDialog(null, "El coste del curso no puede estar vacío.", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if (limitSelection(null) == 0) {
			JOptionPane.showMessageDialog(null, "Hay que seleccionar al menos un día de la semana.", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if (getDcInicio().getDate() == null || getDcFinal().getDate() == null) {
			JOptionPane.showMessageDialog(null, "Los dias de inicio y fin de curso no pueden estar vacíos.", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
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
		Instalacion instalacion = ((Instalacion)cbInstalaciones.getSelectedItem());
		spnMaxSocios.setModel(new SpinnerNumberModel(Integer.valueOf(instalacion.getMinCurso()), Integer.valueOf(instalacion.getMinCurso()), 
					Integer.valueOf(instalacion.getMaxCurso()), Integer.valueOf(1)));
		spnMaxSocios.setValue(instalacion.getMinCurso());
		spnMaxSocios.setToolTipText("Max: " + instalacion.getMaxCurso() + " - Min: " + instalacion.getMinCurso());
		
	}
	private JDateChooser getDcInicio() {
		if (dcInicio == null) {
			dcInicio = new JDateChooser();
			dcInicio.setFont(new Font("Tahoma", Font.PLAIN, 15));
			dcInicio.setDateFormatString("yyyy-MM-dd");
			LocalDate minSelectable = LocalDate.now().plusDays(7);
			dcInicio.setMinSelectableDate(java.sql.Date.valueOf(minSelectable));
		}
		return dcInicio;
	}
	private JDateChooser getDcFinal() {
		if (dcFinal == null) {
			dcFinal = new JDateChooser();
			dcFinal.setFont(new Font("Tahoma", Font.PLAIN, 15));
			dcFinal.setDateFormatString("yyyy-MM-dd");
			LocalDate minSelectable = LocalDate.now().plusDays(8);
			dcFinal.setMinSelectableDate(java.sql.Date.valueOf(minSelectable));
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
			lblMaxSocios.setLabelFor(getSpnMaxSocios());
			lblMaxSocios.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return lblMaxSocios;
	}
	private JLabel getLblDiaInicio() {
		if (lblDiaInicio == null) {
			lblDiaInicio = new JLabel("Dia de inicio:");
			lblDiaInicio.setLabelFor(getDcInicio());
			lblDiaInicio.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return lblDiaInicio;
	}
	private JLabel getLblDiaFinal() {
		if (lblDiaFinal == null) {
			lblDiaFinal = new JLabel("Dia de finalización:");
			lblDiaFinal.setLabelFor(getDcFinal());
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
	        txtHoraFin.setHorizontalAlignment(SwingConstants.RIGHT);
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
	        txtHoraInicio.setHorizontalAlignment(SwingConstants.RIGHT);
	        txtHoraInicio.setFont(new Font("Tahoma", Font.PLAIN, 15));
	        txtHoraInicio.setToolTipText("HH:mm");
			txtHoraInicio.setColumns(10);
		}
		return txtHoraInicio;
	}
	private JLabel getLblHoraInicio() {
		if (lblHoraInicio == null) {
			lblHoraInicio = new JLabel("Hora de inicio de la clase: ");
			lblHoraInicio.setLabelFor(getTxtHoraInicio());
			lblHoraInicio.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return lblHoraInicio;
	}
	private JLabel getLblHoraFin() {
		if (lblHoraFin == null) {
			lblHoraFin = new JLabel("Hora de finalización de la clase: ");
			lblHoraFin.setLabelFor(getTxtHoraFin());
			lblHoraFin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return lblHoraFin;
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
			chkLunes = new JCheckBox(DayOfWeek.MONDAY.getDisplayName(TextStyle.FULL, getLocale()));
			chkLunes.addActionListener(limiter);
			chkLunes.setToolTipText("Máximo " + Cursillos.MAX_DIAS_SEMANA + " días a la semana.");
			chkLunes.setBackground(Color.WHITE);
			chkLunes.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return chkLunes;
	}
	private JCheckBox getChkMartes() {
		if (chkMartes == null) {
			chkMartes = new JCheckBox(DayOfWeek.TUESDAY.getDisplayName(TextStyle.FULL, getLocale()));
			chkMartes.addActionListener(limiter);
			chkMartes.setToolTipText("Máximo " + Cursillos.MAX_DIAS_SEMANA + " días a la semana.");
			chkMartes.setBackground(Color.WHITE);
			chkMartes.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return chkMartes;
	}
	private JCheckBox getChkMiercoles() {
		if (chkMiercoles == null) {
			chkMiercoles = new JCheckBox(DayOfWeek.WEDNESDAY.getDisplayName(TextStyle.FULL, getLocale()));
			chkMiercoles.addActionListener(limiter);
			chkMiercoles.setToolTipText("Máximo " + Cursillos.MAX_DIAS_SEMANA + " días a la semana.");
			chkMiercoles.setBackground(Color.WHITE);
			chkMiercoles.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return chkMiercoles;
	}
	private JCheckBox getChkJueves() {
		if (chkJueves == null) {
			chkJueves = new JCheckBox(DayOfWeek.THURSDAY.getDisplayName(TextStyle.FULL, getLocale()));
			chkJueves.addActionListener(limiter);
			chkJueves.setToolTipText("Máximo " + Cursillos.MAX_DIAS_SEMANA + " días a la semana.");
			chkJueves.setBackground(Color.WHITE);
			chkJueves.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return chkJueves;
	}
	private JCheckBox getChkViernes() {
		if (chkViernes == null) {
			chkViernes = new JCheckBox(DayOfWeek.FRIDAY.getDisplayName(TextStyle.FULL, getLocale()));
			chkViernes.addActionListener(limiter);
			chkViernes.setToolTipText("Máximo " + Cursillos.MAX_DIAS_SEMANA + " días a la semana.");
			chkViernes.setBackground(Color.WHITE);
			chkViernes.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return chkViernes;
	}
	private JCheckBox getChkSabado() {
		if (chkSabado == null) {
			chkSabado = new JCheckBox(DayOfWeek.SATURDAY.getDisplayName(TextStyle.FULL, getLocale()));
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
			limitSelection(e);			
		}
		
	}
	
	private int limitSelection(ActionEvent e) {
	        int selectedCount = 0;
	        
	        if (e != null) {
	        	JCheckBox chkBox = (JCheckBox) e.getSource();
	        	System.out.println(DayOfWeek.valueOf(ReservationController.DIAS_SEMANA.get(chkBox.getText()))+" Dia");
	        	if (chkBox.isSelected()) 
	        		dias.add(DayOfWeek.valueOf(ReservationController.DIAS_SEMANA.get(chkBox.getText())));
	        	else
	        		dias.remove(DayOfWeek.valueOf(ReservationController.DIAS_SEMANA.get(chkBox.getText())));	        	
	        } else {
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
	        
	        
	        
	        
	        return selectedCount;
	    }
	 
	private JTextField getTxtCosteCurso() {
		if (txtCosteCurso == null) {
			MaskFormatter maskFormatter = null;
	        
	        try {
	            maskFormatter = new MaskFormatter("###.##€");
	            maskFormatter.setPlaceholderCharacter('0');
	        } catch (ParseException e) {
	            System.err.println("Formato inválido");
	        }
			txtCosteCurso = new JFormattedTextField(maskFormatter);
			txtCosteCurso.setHorizontalAlignment(SwingConstants.RIGHT);
			txtCosteCurso.setToolTipText("Sólo números");
			txtCosteCurso.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtCosteCurso.setColumns(10);
		}
		return txtCosteCurso;
	}
	private JLabel getLblImporteCurso() {
		if (lblImporteCurso == null) {
			lblImporteCurso = new JLabel("Coste del curso: ");
			lblImporteCurso.setLabelFor(getTxtCosteCurso());
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
			pnCentroCentro.add(getLblNombreCurso());
			pnCentroCentro.add(getTxtNombreCurso());
			pnCentroCentro.add(getLblDiaInicio());
			pnCentroCentro.add(getDcInicio());
			pnCentroCentro.add(getLblDiaFinal());
			pnCentroCentro.add(getDcFinal());
			pnCentroCentro.add(getLblMaxSocios());
			pnCentroCentro.add(getSpnMaxSocios());
			pnCentroCentro.add(getLblHoraInicio());
			pnCentroCentro.add(getTxtHoraInicio());
			pnCentroCentro.add(getLblHoraFin());
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
	private JTextField getTxtNombreCurso() {
		if (txtNombreCurso == null) {
			txtNombreCurso = new JTextField();
			txtNombreCurso.setFont(new Font("Tahoma", Font.PLAIN, 13));
			txtNombreCurso.setColumns(10);
		}
		return txtNombreCurso;
	}
	private JLabel getLblNombreCurso() {
		if (lblNombreCurso == null) {
			lblNombreCurso = new JLabel("Nombre del curso: ");
			lblNombreCurso.setLabelFor(getTxtNombreCurso());
			lblNombreCurso.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return lblNombreCurso;
	}
}
