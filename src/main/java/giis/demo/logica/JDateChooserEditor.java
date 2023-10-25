package giis.demo.logica;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class JDateChooserEditor extends DefaultCellEditor {

	private static final long serialVersionUID = 972198783559030305L;
	public JDateChooserEditor(JCheckBox checkBox)
	{
		super(checkBox);
	
	}
	
	JDateChooser date;
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) 
	{
	
		date = new JDateChooser();
		date.setDateFormatString("yyyy-MM-dd");
		return date;
	}
	
	public Object getCellEditorValue() 
	{
		return new String(((JTextField)date.getDateEditor().getUiComponent()).getText());
	}
	
	public boolean stopCellEditing()
	{
		return super.stopCellEditing();
	}
	
	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}
}