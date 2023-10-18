package giis.demo.business;

import java.util.List;

import javax.swing.table.TableModel;

import giis.demo.business.entities.SocioEntity;
import giis.demo.util.Database;
import giis.demo.util.SwingUtil;

public abstract class SociosController {
	
	private final static String SQL_CARGAR_TODOS_SOCIOS = "select id, name, surname, email, iban, gender, age from socios ";
	
	public static TableModel setTableModel(Database db, String filter) {
		TableModel model = SwingUtil.getTableModelFromPojos(getSociosForTabla(db, filter), new String[] {"id", "name", "surname", "email", "iban", "gender", "age"});
		return model;
	}
	
	private static List<SocioEntity> getSociosForTabla(Database db, String filter){
		return db.executeQueryPojo(SocioEntity.class, buildQuery(filter));
	}

	private static String buildQuery(String filter) {
		String query;

		if (!filter.isEmpty()) {
			query = SQL_CARGAR_TODOS_SOCIOS + filter;
		} else {
			query = SQL_CARGAR_TODOS_SOCIOS;
		}
		
		return query;
	}
	
}
