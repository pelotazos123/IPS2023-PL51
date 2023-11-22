package giis.demo.ui.politicaProteccionDatos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import giis.demo.model.Socio;
import giis.demo.model.CrearLicencias.servicio.TramitarLicencia;
import giis.demo.model.politicaDeDatos.PoliticaDeDatos;

public class VentanaVerDatos extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private TramitarLicencia tramitarLicencia;
	private PoliticaDeDatos politicaDeDatos;
	
	private JPanel pnPrincipal;
	private JPanel pnBotones;
	private JButton btSolicitarModificarDatos;
	private JButton btDarseBaja;
	private JPanel pnDatos;
	private JPanel pnDatosPrimeraMitad;
	private JPanel pnDni;
	private JLabel lbDni;
	private JTextField txDni;
	private JPanel pnNombre;
	private JLabel lbNombre;
	private JTextField txNombre;
	private JPanel pnApellido;
	private JLabel lbApellido;
	private JTextField txApellido;
	private JPanel pnEmail;
	private JLabel lbEmail;
	private JTextField txEmail;
	private JPanel pnTelefono;
	private JLabel lbTelefono;
	private JTextField txTelefono;
	private JPanel pnTipoCuota;
	private JLabel lbTipoCuota;
	private JTextField txTipoCuota;
	private JPanel pnDatosSegundaMitad;
	private JPanel pnIban;
	private JLabel lbIban;
	private JTextField txIban;
	private JPanel pnAltura;
	private JLabel lbAltura;
	private JTextField txAltura;
	private JPanel pnPeso;
	private JLabel lbPeso;
	private JTextField txPeso;
	private JPanel pnFechaNacimiento;
	private JLabel lbFechaNacimiento;
	private JTextField txFechaNacimiento;
	private JPanel pnGenero;
	private JLabel lbGenero;
	private JTextField txGenero;
	private JPanel pnDirectivo;
	private JLabel lbDirectivo;
	private JTextField txDirectivo;

	/**
	 * Create the frame.
	 * @param politicaDeDatos 
	 */
	public VentanaVerDatos(TramitarLicencia t, PoliticaDeDatos p) {
		tramitarLicencia = t;
		politicaDeDatos = p;
		setMinimumSize(new Dimension(1400, 477));
		setBackground(Color.WHITE);
		setTitle("Club Deportivo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1126, 658);
		setLocationRelativeTo(null);
		pnPrincipal = new JPanel();
		pnPrincipal.setBackground(Color.WHITE);
		pnPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(pnPrincipal);
		pnPrincipal.setLayout(new BorderLayout(0, 0));
		pnPrincipal.add(getPnBotones(), BorderLayout.SOUTH);
		pnPrincipal.add(getPnDatos(), BorderLayout.CENTER);
		cargarDatos();
	}

	private void cargarDatos() {
		Socio socio = tramitarLicencia.getUsuario();
		getTxAltura().setText(""+socio.getAltura());
		getTxApellido().setText(socio.getApellidos());
		getTxDni().setText(socio.getDni());
		getTxEmail().setText(socio.getCorreo());
		getTxIban().setText(socio.getNumeroIban());
		getTxNombre().setText(socio.getNombre());
		getTxPeso().setText(""+socio.getPeso());
		getTxTelefono().setText(""+socio.getTelefono());
		getTxTipoCuota().setText(socio.getTipoCuota().toString());
		getTxFechaNacimiento().setText(socio.getFechaNacimiento().toString());
		getTxGenero().setText(socio.getGenero().toString());
		getTxDirectivo().setText(socio.isEsDirectivo() ? "Si":"No");
	}

	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnBotones.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnBotones.setBackground(Color.WHITE);
			pnBotones.add(getBtSolicitarModificarDatos());
			pnBotones.add(getBtDarseBaja());
		}
		return pnBotones;
	}
	private JButton getBtSolicitarModificarDatos() {
		if (btSolicitarModificarDatos == null) {
			btSolicitarModificarDatos = new JButton("Solicitar modificacion de datos");
			btSolicitarModificarDatos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					politicaDeDatos.enviarSolicitudDeModificacionDeDatos(tramitarLicencia.getUsuario());
				}
			});
			btSolicitarModificarDatos.setBounds(584, 269, 134, 54);
		}
		return btSolicitarModificarDatos;
	}
	private JButton getBtDarseBaja() {
		if (btDarseBaja == null) {
			btDarseBaja = new JButton("Darse de baja");
		}
		return btDarseBaja;
	}
	private JPanel getPnDatos() {
		if (pnDatos == null) {
			pnDatos = new JPanel();
			pnDatos.setLayout(new GridLayout(1, 1, 0, 0));
			pnDatos.add(getPnDatosPrimeraMitad());
			pnDatos.add(getPnDatosSegundaMitad());
		}
		return pnDatos;
	}
	private JPanel getPnDatosPrimeraMitad() {
		if (pnDatosPrimeraMitad == null) {
			pnDatosPrimeraMitad = new JPanel();
			pnDatosPrimeraMitad.setBackground(Color.WHITE);
			pnDatosPrimeraMitad.setLayout(new GridLayout(0, 1, 0, 0));
			pnDatosPrimeraMitad.add(getPnDni());
			pnDatosPrimeraMitad.add(getPnNombre());
			pnDatosPrimeraMitad.add(getPnApellido());
			pnDatosPrimeraMitad.add(getPnEmail());
			pnDatosPrimeraMitad.add(getPnTelefono());
			pnDatosPrimeraMitad.add(getPnTipoCuota());
		}
		return pnDatosPrimeraMitad;
	}
	private JPanel getPnDni() {
		if (pnDni == null) {
			pnDni = new JPanel();
			pnDni.setBackground(Color.WHITE);
			pnDni.add(getLbDni());
			pnDni.add(getTxDni());
		}
		return pnDni;
	}
	private JLabel getLbDni() {
		if (lbDni == null) {
			lbDni = new JLabel("Dni:");
			lbDni.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return lbDni;
	}
	private JTextField getTxDni() {
		if (txDni == null) {
			txDni = new JTextField();
			txDni.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txDni.setEditable(false);
			txDni.setColumns(10);
		}
		return txDni;
	}
	private JPanel getPnNombre() {
		if (pnNombre == null) {
			pnNombre = new JPanel();
			pnNombre.setBackground(Color.WHITE);
			pnNombre.add(getLbNombre());
			pnNombre.add(getTxNombre());
		}
		return pnNombre;
	}
	private JLabel getLbNombre() {
		if (lbNombre == null) {
			lbNombre = new JLabel("Nombre:");
			lbNombre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return lbNombre;
	}
	private JTextField getTxNombre() {
		if (txNombre == null) {
			txNombre = new JTextField();
			txNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txNombre.setEditable(false);
			txNombre.setColumns(10);
		}
		return txNombre;
	}
	private JPanel getPnApellido() {
		if (pnApellido == null) {
			pnApellido = new JPanel();
			pnApellido.setBackground(Color.WHITE);
			pnApellido.add(getLbApellido());
			pnApellido.add(getTxApellido());
		}
		return pnApellido;
	}
	private JLabel getLbApellido() {
		if (lbApellido == null) {
			lbApellido = new JLabel("Apellido:");
			lbApellido.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return lbApellido;
	}
	private JTextField getTxApellido() {
		if (txApellido == null) {
			txApellido = new JTextField();
			txApellido.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txApellido.setEditable(false);
			txApellido.setColumns(10);
		}
		return txApellido;
	}
	private JPanel getPnEmail() {
		if (pnEmail == null) {
			pnEmail = new JPanel();
			pnEmail.setBackground(Color.WHITE);
			pnEmail.add(getLbEmail());
			pnEmail.add(getTxEmail());
		}
		return pnEmail;
	}
	private JLabel getLbEmail() {
		if (lbEmail == null) {
			lbEmail = new JLabel("Correo:");
			lbEmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return lbEmail;
	}
	private JTextField getTxEmail() {
		if (txEmail == null) {
			txEmail = new JTextField();
			txEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txEmail.setEditable(false);
			txEmail.setColumns(10);
		}
		return txEmail;
	}
	private JPanel getPnTelefono() {
		if (pnTelefono == null) {
			pnTelefono = new JPanel();
			pnTelefono.setBackground(Color.WHITE);
			pnTelefono.add(getLbTelefono());
			pnTelefono.add(getTxTelefono());
		}
		return pnTelefono;
	}
	private JLabel getLbTelefono() {
		if (lbTelefono == null) {
			lbTelefono = new JLabel("Telefono:");
			lbTelefono.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return lbTelefono;
	}
	private JTextField getTxTelefono() {
		if (txTelefono == null) {
			txTelefono = new JTextField();
			txTelefono.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txTelefono.setEditable(false);
			txTelefono.setColumns(10);
		}
		return txTelefono;
	}
	private JPanel getPnTipoCuota() {
		if (pnTipoCuota == null) {
			pnTipoCuota = new JPanel();
			pnTipoCuota.setBackground(Color.WHITE);
			pnTipoCuota.add(getLbTipoCuota());
			pnTipoCuota.add(getTxTipoCuota());
		}
		return pnTipoCuota;
	}
	private JLabel getLbTipoCuota() {
		if (lbTipoCuota == null) {
			lbTipoCuota = new JLabel("Tipo de cuota:");
			lbTipoCuota.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return lbTipoCuota;
	}
	private JTextField getTxTipoCuota() {
		if (txTipoCuota == null) {
			txTipoCuota = new JTextField();
			txTipoCuota.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txTipoCuota.setEditable(false);
			txTipoCuota.setColumns(10);
		}
		return txTipoCuota;
	}
	private JPanel getPnDatosSegundaMitad() {
		if (pnDatosSegundaMitad == null) {
			pnDatosSegundaMitad = new JPanel();
			pnDatosSegundaMitad.setBackground(Color.WHITE);
			pnDatosSegundaMitad.setLayout(new GridLayout(0, 1, 0, 0));
			pnDatosSegundaMitad.add(getPnIban());
			pnDatosSegundaMitad.add(getPnAltura());
			pnDatosSegundaMitad.add(getPnPeso());
			pnDatosSegundaMitad.add(getPnFechaNacimiento());
			pnDatosSegundaMitad.add(getPnGenero());
			pnDatosSegundaMitad.add(getPnDirectivo());
		}
		return pnDatosSegundaMitad;
	}
	private JPanel getPnIban() {
		if (pnIban == null) {
			pnIban = new JPanel();
			pnIban.setBackground(Color.WHITE);
			pnIban.add(getLbIban());
			pnIban.add(getTxIban());
		}
		return pnIban;
	}
	private JLabel getLbIban() {
		if (lbIban == null) {
			lbIban = new JLabel("Iban:");
			lbIban.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return lbIban;
	}
	private JTextField getTxIban() {
		if (txIban == null) {
			txIban = new JTextField();
			txIban.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txIban.setEditable(false);
			txIban.setColumns(10);
		}
		return txIban;
	}
	private JPanel getPnAltura() {
		if (pnAltura == null) {
			pnAltura = new JPanel();
			pnAltura.setBackground(Color.WHITE);
			pnAltura.add(getLbAltura());
			pnAltura.add(getTxAltura());
		}
		return pnAltura;
	}
	private JLabel getLbAltura() {
		if (lbAltura == null) {
			lbAltura = new JLabel("Altura:");
			lbAltura.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return lbAltura;
	}
	private JTextField getTxAltura() {
		if (txAltura == null) {
			txAltura = new JTextField();
			txAltura.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txAltura.setEditable(false);
			txAltura.setColumns(10);
		}
		return txAltura;
	}
	private JPanel getPnPeso() {
		if (pnPeso == null) {
			pnPeso = new JPanel();
			pnPeso.setBackground(Color.WHITE);
			pnPeso.add(getLbPeso());
			pnPeso.add(getTxPeso());
		}
		return pnPeso;
	}
	private JLabel getLbPeso() {
		if (lbPeso == null) {
			lbPeso = new JLabel("Peso:");
			lbPeso.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return lbPeso;
	}
	private JTextField getTxPeso() {
		if (txPeso == null) {
			txPeso = new JTextField();
			txPeso.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txPeso.setEditable(false);
			txPeso.setColumns(10);
		}
		return txPeso;
	}
	private JPanel getPnFechaNacimiento() {
		if (pnFechaNacimiento == null) {
			pnFechaNacimiento = new JPanel();
			pnFechaNacimiento.setBackground(Color.WHITE);
			pnFechaNacimiento.add(getLbFechaNacimiento());
			pnFechaNacimiento.add(getTxFechaNacimiento());
		}
		return pnFechaNacimiento;
	}
	private JLabel getLbFechaNacimiento() {
		if (lbFechaNacimiento == null) {
			lbFechaNacimiento = new JLabel("Fecha de nacimiento:");
			lbFechaNacimiento.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return lbFechaNacimiento;
	}
	private JTextField getTxFechaNacimiento() {
		if (txFechaNacimiento == null) {
			txFechaNacimiento = new JTextField();
			txFechaNacimiento.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txFechaNacimiento.setEditable(false);
			txFechaNacimiento.setColumns(10);
		}
		return txFechaNacimiento;
	}
	private JPanel getPnGenero() {
		if (pnGenero == null) {
			pnGenero = new JPanel();
			pnGenero.setBackground(Color.WHITE);
			pnGenero.add(getLbGenero());
			pnGenero.add(getTxGenero());
		}
		return pnGenero;
	}
	private JLabel getLbGenero() {
		if (lbGenero == null) {
			lbGenero = new JLabel("Genero:");
			lbGenero.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return lbGenero;
	}
	private JTextField getTxGenero() {
		if (txGenero == null) {
			txGenero = new JTextField();
			txGenero.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txGenero.setEditable(false);
			txGenero.setColumns(10);
		}
		return txGenero;
	}
	private JPanel getPnDirectivo() {
		if (pnDirectivo == null) {
			pnDirectivo = new JPanel();
			pnDirectivo.setBackground(Color.WHITE);
			pnDirectivo.add(getLbDirectivo());
			pnDirectivo.add(getTxDirectivo());
		}
		return pnDirectivo;
	}
	private JLabel getLbDirectivo() {
		if (lbDirectivo == null) {
			lbDirectivo = new JLabel("Directivo:");
			lbDirectivo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return lbDirectivo;
	}
	private JTextField getTxDirectivo() {
		if (txDirectivo == null) {
			txDirectivo = new JTextField();
			txDirectivo.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txDirectivo.setEditable(false);
			txDirectivo.setColumns(10);
		}
		return txDirectivo;
	}
}
