package giis.demo.ui;

import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.toedter.calendar.JCalendar;

import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import giis.demo.business.InstalacionController;
import giis.demo.business.ReservationController;
import giis.demo.business.SociosController;
import giis.demo.model.Instalacion;
import giis.demo.model.Reserva;
import giis.demo.util.Database;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.CardLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class VentanaReservas extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JPanel pnPrincipal;
	private JPanel pnSelector;
	private JList<String> list;
	private JCalendar calendar;
	private JComboBox<Instalacion> cbInstalaciones;
	private DefaultListModel<String> modeloListaHoras;
	private JButton btnSiguiente;
	private ReservationController reMan;
	private String chosenDay;
	private Database db;
	private JPanel pnNorth;
	private JLabel lblHorasDisp;
	private JPanel pnSouth;
	private JCheckBox chkAmpliaHora;
	private JPanel pnPrincipalReservas;
	private JPanel pnSecundarioReservas;
	private JPanel pnTxtContainer;
	private JButton btnNuevoSocio;
	private List<JPanel> listaTxtFields;
	private JPanel pnBotones;
	private JButton btnBorrarSocio;
	private JPanel pnTxtFields;
	private JButton btnVolver;
	private JButton btnReservar;
	private JPanel pnBotonesSur;
	private Instalacion selInstalacion;
	
	private static final String INTRODUZCA_AQUI = "Introduzca DNI";
	private JScrollPane scrlTxts;
	private JPanel panel;
	private JLabel lblTxtAñadeSocio;
	private JLabel lblInstalacion;
	
	public VentanaReservas(Database db) {
		this.db = db;
		reMan = new ReservationController(this.db);
		listaTxtFields = new ArrayList<>();
		chosenDay = "";
		setTitle("Reservas");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 911, 618);
		getContentPane().setLayout(new CardLayout(0, 0));
		
		getContentPane().add(getPnPrincipalReservas(), "principal");
		getContentPane().add(getPnSecundarioReservas(), "secundario");
		
	}
	
	private JPanel getPnPrincipalReservas() {
		if (pnPrincipalReservas == null) {
			pnPrincipalReservas = new JPanel();
			pnPrincipalReservas.setLayout(new BorderLayout(0, 0));
			pnPrincipalReservas.add(getPnPrincipal(), BorderLayout.CENTER);
			pnPrincipalReservas.add(getPnSelector(), BorderLayout.EAST);
		}
		return pnPrincipalReservas;
	}
	
	private JPanel getPnPrincipal() {
		if (pnPrincipal == null) {
			pnPrincipal = new JPanel();
			pnPrincipal.setBackground(Color.WHITE);
			pnPrincipal.setLayout(new BorderLayout(0, 0));
			pnPrincipal.add(getCalendar(), BorderLayout.CENTER);
		}
		return pnPrincipal;
	}
	
	private JPanel getPnSelector() {
		if (pnSelector == null) {
			pnSelector = new JPanel();
			pnSelector.setLayout(new BorderLayout(0, 0));
			pnSelector.add(getList(), BorderLayout.CENTER);
			pnSelector.setPreferredSize(new Dimension(200, 100));
			pnSelector.add(getPnNorth(), BorderLayout.NORTH);
			pnSelector.add(getPnSouth(), BorderLayout.SOUTH);
		}
		return pnSelector;
	}
	
	private JCalendar getCalendar() {
		if(calendar == null) {
			calendar = new JCalendar();
			calendar.getYearChooser().setMinimum(Year.now().getValue());
			calendar.getYearChooser().getSpinner().setBackground(Color.WHITE);
			calendar.getMonthChooser().setBackground(Color.WHITE);
			calendar.getDayChooser().setDecorationBackgroundColor(Color.CYAN);
			calendar.getDayChooser().getDayPanel().setBackground(Color.WHITE);
			calendar.getDayChooser().setAlwaysFireDayProperty(false);
			calendar.getDayChooser().setDayBordersVisible(true);
			calendar.setWeekOfYearVisible(false);
			calendar.setSelectableDateRange(java.sql.Date.valueOf(LocalDate.now()), java.sql.Date.valueOf(LocalDate.of(2600, 12, 31)));
			calendar.addPropertyChangeListener("calendar", new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					dayPicker(evt);
				}
			});
			
		}
		return calendar;
	}
	
	private void dayPicker(PropertyChangeEvent evt) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
		chosenDay = formatDate.format(calendar.getDate());
		getPnSelector().setVisible(true);
		generaHoras();
		getBtnSiguiente().setEnabled(false);
	}
	
	private void reservar() {
		if (!completeCheckToSocios()) return;
		
		int selectedVal = getList().getSelectedIndex();
		
		boolean hasExtra = getChkAmpliaHora().isSelected();
		
		String hora = modeloListaHoras.get(selectedVal);
		LocalDate selectedDate = getCalendar().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();;
		LocalTime horaSel = LocalTime.of(Integer.parseInt(hora.split(":")[0]), 00);
		
		LocalDateTime finalDate = selectedDate.atTime(horaSel); 
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String reserva = finalDate.format(dtf);

		selInstalacion = ((Instalacion)getCbInstalaciones().getSelectedItem());
		
		List<String> listaParticipantes = getTxtFromDniFields();
		
		if (!reMan.reservar(finalDate, reserva, selInstalacion, listaParticipantes, hasExtra)) return;
		
		JOptionPane.showMessageDialog(null, "Reserva confirmada de " + selInstalacion.toString() +": " + reserva);
		this.dispose();
	}

	private boolean completeCheckToSocios() {
		List<String> valuesTxt = getTxtFromDniFields(); // Recibe todos los valores escritos en los txt de DNIs
		
		if (!checkEqualSocios(valuesTxt)) // Comprueba que no se repita ningún DNI
			return false;
		
		for (boolean res : checkSocios(valuesTxt)) { // Comprueba que todos los DNIs introducidos son de socios
			if (res)
				continue;
			else {
				JOptionPane.showMessageDialog(null, "Uno de los DNIs no es válido o no es un socio activo.\n"
						+ "Por favor, revise los datos introducidos.", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}

	private List<String> getTxtFromDniFields() {
		List<String> listTxtValues = new ArrayList<>();
		for (JPanel txt: listaTxtFields) {
			listTxtValues.add(((JTextField)txt.getComponent(0)).getText());
		}
		return listTxtValues;
	}

	private boolean[] checkSocios(List<String> values) {
		List<String> dnis = SociosController.getDni(db); // Recibe todos los dnis de todos los socios disponibles
		Iterator<String> itDnis = dnis.iterator();
		List<String> txtValues = values;
		boolean[] results = new boolean[listaTxtFields.size()];
		int counter = 0;		
		
		for (String txt: txtValues) {
			while (itDnis.hasNext()) {
				if (txt.equals(itDnis.next())) {
					results[counter] = true;
					break;
				} else {
					results[counter] = false;
				}
			}
			counter++;
		}
		
		return results;
		
	}
	
	private boolean checkEqualSocios(List<String> values) {
		List<String> listaChecker = new ArrayList<>();
		List<String> txtValues = values;
		
		for (String txt : txtValues) {
			if (listaChecker.isEmpty()) {
				listaChecker.add(txt);				
			} else {
				if (!listaChecker.contains(txt))
					listaChecker.add(txt);
				else {
					JOptionPane.showMessageDialog(null, "No se puede reutilizar el mismo DNI más de una vez.\n"
							+ "Por favor, revise los datos introducidos.", "Error", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
		}
	
		return true;
	}
	
	private JList<String> getList() {
		if (list == null) {
			modeloListaHoras = new DefaultListModel<String>();
			list = new JList<String>();
			list.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					getBtnSiguiente().setEnabled(true); // Solo se permite reservar si hay hora seleccionada
				}
				
//				@Override
//				public void focusLost(FocusEvent e) {
//					list.clearSelection();
//				}
			});
			list.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					checkEnabler(e); // Si la reserva se puede ampliar una hora, se habilita el check
				}
			});
			list.setModel(modeloListaHoras);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	private void checkEnabler(ListSelectionEvent e) {
		JList<String> lista = (JList<String>) e.getSource();
		if (lista.getSelectedValue() == null)
			return;
		String selected = lista.getSelectedValue().split(":")[0];
		getChkAmpliaHora().setEnabled(modeloListaHoras.contains((Integer.parseInt(selected)+1)+":00"));
	}

	private void generaHoras() {
		getBtnSiguiente().setEnabled(false);
		modeloListaHoras.removeAllElements();
		for (int i = 9; i <= 23; i++) {
			modeloListaHoras.addElement(i+":00");
		}		
		
		for (Reserva reserva : reMan.getReservas()) {	
			if (reserva.getFecha().trim().equals(chosenDay)
					&& modeloListaHoras.contains(reserva.getHora().trim())
							&& reserva.getInstalacionId().trim().equals(((Instalacion)getCbInstalaciones().getSelectedItem()).getCode().toString())) {
				System.out.println("Ya hay una reserva");
				modeloListaHoras.removeElement(reserva.getHora().trim());
				int horaSig = Integer.parseInt(reserva.getHora().split(":")[0]);
				if (horaSig + 1 <= 23 && reserva.hasExtra()) // Si la hora fue reservada con una extra, se elimina la sig tmb
					modeloListaHoras.removeElement((horaSig+1)+":00");
			}
		}
	}
	
	private JComboBox<Instalacion> getCbInstalaciones() {
		if (cbInstalaciones == null) {
			cbInstalaciones = new JComboBox<Instalacion>();
			cbInstalaciones.setBackground(Color.WHITE);
			cbInstalaciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					generaHoras();
				}
			});
			cbInstalaciones.setModel(new DefaultComboBoxModel<Instalacion>(InstalacionController.getInstalaciones(db)));
		}
		return cbInstalaciones;
	}
	
	private JButton getBtnSiguiente() {
		if (btnSiguiente == null) {
			btnSiguiente = new JButton("Siguiente");
			btnSiguiente.setEnabled(false);
			btnSiguiente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((CardLayout) getContentPane().getLayout()).next(getContentPane());
					selInstalacion = ((Instalacion)getCbInstalaciones().getSelectedItem());
					getLblInstalacion().setText("Instalacion: " + selInstalacion.getName() + " | Nº max participantes: " + selInstalacion.getMax() +
							" | Nº min participantes: " + selInstalacion.getMin());
				}
			});
		}
		return btnSiguiente;
	}
	private JPanel getPnNorth() {
		if (pnNorth == null) {
			pnNorth = new JPanel();
			pnNorth.setBackground(Color.WHITE);
			pnNorth.setLayout(new BorderLayout(0, 0));
			pnNorth.add(getLblHorasDisp(), BorderLayout.SOUTH);
			pnNorth.add(getCbInstalaciones());
		}
		return pnNorth;
	}
	private JLabel getLblHorasDisp() {
		if (lblHorasDisp == null) {
			lblHorasDisp = new JLabel("Horas disponibles:");
		}
		return lblHorasDisp;
	}
	private JPanel getPnSouth() {
		if (pnSouth == null) {
			pnSouth = new JPanel();
			pnSouth.setBackground(Color.WHITE);
			pnSouth.setLayout(new BorderLayout(0, 0));
			pnSouth.add(getChkAmpliaHora(), BorderLayout.NORTH);
			pnSouth.add(getBtnSiguiente());
		}
		return pnSouth;
	}
	private JCheckBox getChkAmpliaHora() {
		if (chkAmpliaHora == null) {
			chkAmpliaHora = new JCheckBox("Añadir una hora extra");
			chkAmpliaHora.setBackground(Color.WHITE);
		}
		return chkAmpliaHora;
	}

	private JPanel getPnSecundarioReservas() {
		if (pnSecundarioReservas == null) {
			pnSecundarioReservas = new JPanel();
			pnSecundarioReservas.setBackground(Color.WHITE);
			pnSecundarioReservas.setLayout(new BorderLayout(0, 0));
			pnSecundarioReservas.add(getPnTxtContainer(), BorderLayout.CENTER);
			pnSecundarioReservas.add(getPnBotonesSur(), BorderLayout.SOUTH);
			pnSecundarioReservas.add(getPanel(), BorderLayout.NORTH);
		}
		return pnSecundarioReservas;
	}
	private JPanel getPnTxtContainer() {
		if (pnTxtContainer == null) {
			pnTxtContainer = new JPanel();
			pnTxtContainer.setLayout(new BorderLayout(0, 0));
			pnTxtContainer.add(getPnBotones(), BorderLayout.NORTH);
			pnTxtContainer.add(getScrlTxts(), BorderLayout.CENTER);
		}
		return pnTxtContainer;
	}
	private JButton getBtnNuevoSocio() {
		if (btnNuevoSocio == null) {
			btnNuevoSocio = new JButton("+");
			btnNuevoSocio.setBackground(Color.GREEN);
			btnNuevoSocio.setFont(new Font("Tahoma", Font.BOLD, 25));
			btnNuevoSocio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addNewSocio();
				}
			});
		}
		return btnNuevoSocio;
	}

	private void addNewSocio() {
		if (listaTxtFields.size() < selInstalacion.getMax()) {
			JPanel pnlController = new JPanel();
			pnlController.setBackground(Color.WHITE);
			JTextField nuevo = prepareTxtField();
			pnlController.add(nuevo);
			listaTxtFields.add(pnlController);
			getPnTxtFields().add(pnlController);
			getPnTxtFields().repaint();
			getPnTxtFields().revalidate();
		} else {
			JOptionPane.showMessageDialog(null, "Ya has alcanzado el máximo de participantes.", "Error", JOptionPane.ERROR_MESSAGE);
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
		//nuevo.setBounds(30, 96, 427, 35);
		nuevo.setColumns(20);
		//nuevo.setPreferredSize(new Dimension(424, 35));
		nuevo.setFont(new Font("Tahoma", Font.PLAIN, 25));
		nuevo.setHorizontalAlignment(SwingConstants.CENTER);
		return nuevo;
	}
	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setLayout(new GridLayout(0, 2, 0, 0));
			pnBotones.add(getBtnNuevoSocio());
			pnBotones.add(getBtnBorrarSocio());
		}
		return pnBotones;
	}
	private JButton getBtnBorrarSocio() {
		if (btnBorrarSocio == null) {
			btnBorrarSocio = new JButton("-");
			btnBorrarSocio.setBackground(Color.RED);
			btnBorrarSocio.setFont(new Font("Tahoma", Font.BOLD, 25));
			btnBorrarSocio.setPreferredSize(new Dimension());
			btnBorrarSocio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					eliminaSocio();
				}
			});
		}
		return btnBorrarSocio;
	}
	private void eliminaSocio() {
		if (listaTxtFields.size() > 0) {
			JPanel txtBorrar = listaTxtFields.remove(listaTxtFields.size()-1);
			getPnTxtFields().remove(txtBorrar);
			getPnTxtFields().repaint();
			getPnTxtFields().revalidate();					
		}
	}

	private JPanel getPnTxtFields() {
		if (pnTxtFields == null) {
			pnTxtFields = new JPanel();
			pnTxtFields.setBackground(Color.WHITE);
			pnTxtFields.setLayout(new GridLayout(0, 2, 0, 0));
		}
		return pnTxtFields;
	}
	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cancelarOperacion();
				}
			});
		}
		return btnVolver;
	}

	private void cancelarOperacion() {	
		listaTxtFields.removeAll(listaTxtFields);
		getPnTxtFields().removeAll();
		((CardLayout)getContentPane().getLayout()).show(getContentPane(), "principal");
		selInstalacion = null;
	}
	private JButton getBtnReservar() {
		if (btnReservar == null) {
			btnReservar = new JButton("Reservar");
			btnReservar.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btnReservar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (listaTxtFields.size() >= selInstalacion.getMin())
						reservar();
					else
						JOptionPane.showMessageDialog(null, "No llegas al mínimo de participantes.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			});
		}
		return btnReservar;
	}
	
	private JPanel getPnBotonesSur() {
		if (pnBotonesSur == null) {
			pnBotonesSur = new JPanel();
			pnBotonesSur.setLayout(new GridLayout(0, 2, 0, 0));
			pnBotonesSur.add(getBtnVolver());
			pnBotonesSur.add(getBtnReservar());
		}
		return pnBotonesSur;
	}
	
	private JScrollPane getScrlTxts() {
		if (scrlTxts == null) {
			scrlTxts = new JScrollPane();
			scrlTxts.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrlTxts.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrlTxts.setViewportView(getPnTxtFields());
		}
		return scrlTxts;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getLblTxtAñadeSocio_1(), BorderLayout.CENTER);
			panel.add(getLblInstalacion(), BorderLayout.SOUTH);
		}
		return panel;
	}
	private JLabel getLblTxtAñadeSocio_1() {
		if (lblTxtAñadeSocio == null) {
			lblTxtAñadeSocio = new JLabel("Introduzca el DNI de los socios que participarán en la reserva:");
			lblTxtAñadeSocio.setFont(new Font("Tahoma", Font.BOLD, 20));
		}
		return lblTxtAñadeSocio;
	}
	private JLabel getLblInstalacion() {
		if (lblInstalacion == null) {
			lblInstalacion = new JLabel("");
		}
		return lblInstalacion;
	}
}
