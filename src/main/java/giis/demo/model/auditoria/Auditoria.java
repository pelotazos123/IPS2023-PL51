package giis.demo.model.auditoria;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import giis.demo.business.RecibosModel;
import giis.demo.business.entities.ReciboEntity;
import giis.demo.util.Database;

public class Auditoria {
	
	private String SQL_CARGAR_INSTALACIONES = "select code, name from instalaciones";
	private String SQL_CARGAR_RESERVAS_DE_INSTALACION = "select id, fecha_inicio, fecha_fin from reservas where instalation_code = ?";
	private String SQL_CARGAR_PARTICIPANTES_DE_RESERVA = "select dni from participante_reserva where reserva_id = ?";
	private String SQL_CARGAR_LICENCIAS = "select facturation_date, validation_date from licencias";
	
	private Database db;
	private RecibosModel recibo;
	
	PDDocument documento;
	PDPage pagina;
	PDPageContentStream contenido;
	float tamañoPagina;
	float yPosicion;
	
	private PDType1Font fuenteTimesNewRomman = new PDType1Font(FontName.TIMES_ROMAN);
	
	public Auditoria(Database db) {
		this.db = db;
		recibo = new RecibosModel(db);
	}
	
	
	public void generarPDF(File archivo) throws IOException {
		// Crear un nuevo documento PDF
		documento = new PDDocument();
		pagina = new PDPage();
		
		tamañoPagina = pagina.getMediaBox().getHeight();
		yPosicion = tamañoPagina;
		
		documento.addPage(pagina);

		// Crear contenido para la página
		contenido = new PDPageContentStream(documento, pagina);
		contenido.beginText();
		encabezadoPDF();

		obtenerRecibos();
		obtenerPagosLicencia();
		obtenerUsoInstalaciones();
		
		contenido.endText();
		contenido.close();
		// Guardar el documento
        documento.save(archivo.getAbsolutePath()+".pdf");
        documento.close();
	}

	
	private void encabezadoPDF() throws IOException {
		// Establecer la posición del texto en la página
		contenido.newLineAtOffset(150, 700);
		yPosicion = 700;
		// Configurar el tamaño y la fuente del texto
		contenido.setFont(fuenteTimesNewRomman, 20);
		// Agregar texto al documento
		contenido.showText("Informe de auditoria de Club Deportivo");
	}


	private void obtenerRecibos() throws IOException {
		contenido.newLineAtOffset(-100, -100);
		yPosicion -= 100;
		contenido.setFont(fuenteTimesNewRomman, 15);

		contenido.showText("Recibos del club: ");
		contenido.newLineAtOffset(10, -20);
		yPosicion -= 20;
		contenido.setFont(fuenteTimesNewRomman, 12);

		List<ReciboEntity> recibos = recibo.getListaRecibos();
		if(recibos.isEmpty()) {
			comprobarFinPagina();
			contenido.showText("No hay recibos");
			contenido.newLineAtOffset(0, -20);
			yPosicion -= 20;
		}
		for (ReciboEntity recibo : recibos) {
			String str = "Cuenta: "+recibo.getOwner_iban()+"     Fecha de cargo: "+recibo.getCharge_date()
			+ "     Cantidad: "+recibo.getAmount()+"€";
			
			comprobarFinPagina();
			contenido.showText(str);
			contenido.newLineAtOffset(0, -20);
			yPosicion -= 20;
		}
	}
	
	
	private void obtenerPagosLicencia() throws IOException {
		contenido.newLineAtOffset(-10, -50);
		yPosicion -= 50;
		comprobarFinPagina();
		contenido.setFont(fuenteTimesNewRomman, 15);

		contenido.showText("Pago de licencias: ");
		contenido.newLineAtOffset(10, -20);
		yPosicion -= 20;
		contenido.setFont(fuenteTimesNewRomman, 12);
		
		List<Object[]> licencias = db.executeQueryArray(SQL_CARGAR_LICENCIAS);
		if(licencias.isEmpty()) {
			comprobarFinPagina();
			contenido.showText("No hay pagos de licencias");
			contenido.newLineAtOffset(0, -20);
			yPosicion -= 20;
		}
		for (Object[] licencia : licencias) {
			
			String str = "Fecha realizacion pago: ";
			
			if(licencia[0] == null) {
				str += " pago no realizado";
			}else {
				str += licencia[0];
			}
			
			
			str += "     Fecha validacion pago: ";
			if(licencia[1]== null) {
				str += " pago no validado";
			}else {
				str += licencia[1];
			}
			
			comprobarFinPagina();
			contenido.showText(str);
			contenido.newLineAtOffset(0, -20);
			yPosicion -= 20;
		}
	}
	
	
	private void obtenerUsoInstalaciones() throws IOException {
		contenido.newLineAtOffset(-10, -50);
		yPosicion -= 50;
		comprobarFinPagina();
		contenido.setFont(fuenteTimesNewRomman, 15);

		contenido.showText("Uso de instalaciones: ");
		contenido.newLineAtOffset(10, -20);
		yPosicion -= 20;
		contenido.setFont(fuenteTimesNewRomman, 12);
		
		List<Object[]> instalaciones = db.executeQueryArray(SQL_CARGAR_INSTALACIONES);
		for (Object[] instalacion : instalaciones) {
			String idInstalacion = (String) instalacion[0];
			String nombre = (String) instalacion[1];
			
			comprobarFinPagina();
			contenido.showText(nombre);
			contenido.newLineAtOffset(0, -20);
			yPosicion -= 20;
			
			List<Object[]> reservas = db.executeQueryArray(SQL_CARGAR_RESERVAS_DE_INSTALACION,idInstalacion);
			if(reservas.isEmpty()) {
				comprobarFinPagina();
				contenido.showText("No ha sido utilizada");
				contenido.newLineAtOffset(0, -20);
				yPosicion -= 20;
			}
			for (Object[] reserva : reservas) {
				int idReserva = (int) reserva[0];
				String fechaInicio = (String) reserva[1];
				String[] fecha = fechaInicio.split(" ");
				
				List<Object[]> participantes = db.executeQueryArray(SQL_CARGAR_PARTICIPANTES_DE_RESERVA,idReserva);
				if(!participantes.isEmpty()) {
					
					comprobarFinPagina();
					contenido.showText("Utilizada por:");
					contenido.newLineAtOffset(10, -20);
					yPosicion -= 20;
					
					for (Object[] participante : participantes) {
						String dni = (String) participante[0];
						comprobarFinPagina();
						contenido.showText(dni);
						contenido.newLineAtOffset(0, -20);
						yPosicion -= 20;
					}
					
					contenido.newLineAtOffset(-10, 0);
					comprobarFinPagina();
					contenido.showText("El "+fecha[0]+" a las "+fecha[1]);
					contenido.newLineAtOffset(0, -30);
					yPosicion -= 30;
				}
			}
			contenido.newLineAtOffset(0, -20);
			yPosicion -= 20;
		}
		
		
	}
	
	
	private void comprobarFinPagina() throws IOException {
		if(yPosicion <= 50) {
			contenido.endText();
            contenido.close();
            
            pagina = new PDPage();
            tamañoPagina = pagina.getMediaBox().getHeight();
    		yPosicion = tamañoPagina;
    		documento.addPage(pagina);
    		
    		contenido = new PDPageContentStream(documento, pagina);
    		contenido.beginText();
    		
    		contenido.newLineAtOffset(50, 700);
    		yPosicion = 700;
    		contenido.setFont(fuenteTimesNewRomman, 12);            
		}
	}
	
	
	public void generarHojaCalculo(File ruta) throws IOException {
		// Crear un nuevo libro de Excel en formato xlsx
		Workbook libro = new XSSFWorkbook();
		Sheet hoja = libro.createSheet("MiHoja");

		int numFilas = escribirRecibos(hoja) +3;
		numFilas = escribirPagosLicencia(hoja, numFilas) + 3;
		escribirUsoInstalaciones(hoja, numFilas);
        
        
     // Guardar el libro en un archivo
        FileOutputStream archivo = new FileOutputStream(ruta.getAbsolutePath()+".xlsx");
        libro.write(archivo);
        archivo.close();
	}

	
	private int escribirRecibos(Sheet hoja) {
		Row fila = hoja.createRow(0);
        Cell celda = fila.createCell(0);
        celda.setCellValue("Recibos del club");
		
        int numFila= 1;
        int numColumna = 0;
        
        List<ReciboEntity> recibos = recibo.getListaRecibos();
        if(recibos.isEmpty()) {
			
			fila = hoja.createRow(numFila);
			numFila+=2;
			celda = fila.createCell(0);
	        celda.setCellValue("No hay recibos");
		}
		for (ReciboEntity recibo : recibos) {
			
			numColumna = 0;
			fila = hoja.createRow(numFila);
			
			String str = recibo.getOwner_iban()+";"+recibo.getCharge_date()+";"+recibo.getAmount();
			String[] datos = str.split(";");
			for (String dato : datos) {
				celda = fila.createCell(numColumna);
				celda.setCellValue(dato);
				numColumna++;
			}
			numFila++;
		}
		return numFila;
	}
	
	
	private int escribirPagosLicencia(Sheet hoja, int numFilas) {
		Row fila = hoja.createRow(numFilas);
        Cell celda = fila.createCell(0);
        celda.setCellValue("Pago de licencias");
        
        numFilas++;
        int numColumna = 0;
        
        List<Object[]> licencias = db.executeQueryArray(SQL_CARGAR_LICENCIAS);
		if(licencias.isEmpty()) {
			fila = hoja.createRow(numFilas);
			numFilas+=2;
			celda = fila.createCell(0);
	        celda.setCellValue("No hay pagos de licencias");
		}
		for (Object[] licencia : licencias) {
			
			numColumna = 0;
			fila = hoja.createRow(numFilas);
        	numFilas++;
			
			String str = "Fecha realizacion pago: ";
			
			if(licencia[0] == null) {
				str += " pago no realizado";
			}else {
				str += licencia[0];
			}
			
			celda = fila.createCell(numColumna);
        	celda.setCellValue(str);
        	numColumna++;
			
			
			str = "Fecha validacion pago: ";
			if(licencia[1]== null) {
				str += " pago no validado";
			}else {
				str += licencia[1];
			}
			
			celda = fila.createCell(numColumna);
        	celda.setCellValue(str);
        	numColumna++;
		}
        return numFilas;
	}
	
	
	private void escribirUsoInstalaciones(Sheet hoja, int numFilas) {
		Row fila = hoja.createRow(numFilas);
        Cell celda = fila.createCell(0);
        celda.setCellValue("Uso de instalaciones");
        
        numFilas++;
        int numColumna = 0;
        
        
        List<Object[]> instalaciones = db.executeQueryArray(SQL_CARGAR_INSTALACIONES);
		for (Object[] instalacion : instalaciones) {
			String idInstalacion = (String) instalacion[0];
			String nombre = (String) instalacion[1];
			
			numColumna = 0;
			fila = hoja.createRow(numFilas);
			numFilas++;
			celda = fila.createCell(0);
	        celda.setCellValue(nombre);
			
			List<Object[]> reservas = db.executeQueryArray(SQL_CARGAR_RESERVAS_DE_INSTALACION,idInstalacion);
			if(reservas.isEmpty()) {
				
				fila = hoja.createRow(numFilas);
				numFilas+=2;
				celda = fila.createCell(0);
		        celda.setCellValue("No ha sido utilizada");
			}
			for (Object[] reserva : reservas) {
				int idReserva = (int) reserva[0];
				String fechaInicio = (String) reserva[1];
				String[] fecha = fechaInicio.split(" ");
				
				List<Object[]> participantes = db.executeQueryArray(SQL_CARGAR_PARTICIPANTES_DE_RESERVA,idReserva);
				if(!participantes.isEmpty()) {
					
					fila = hoja.createRow(numFilas);
					numFilas++;
					numColumna = 0;
					celda = fila.createCell(numColumna);
					numColumna++;
			        celda.setCellValue("Utilizada por:");
					
					for (Object[] participante : participantes) {
						String dni = (String) participante[0];
						
						celda = fila.createCell(numColumna);
						numColumna++;
				        celda.setCellValue(dni);
					}
					
					fila = hoja.createRow(numFilas);
					numFilas+= 2;
					celda = fila.createCell(0);
			        celda.setCellValue("El "+fecha[0]+" a las "+fecha[1]);
				}
			}
			numFilas ++;
		}
	}

}
