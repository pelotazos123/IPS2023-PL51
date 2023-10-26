package giis.demo.logica;

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

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
		date.getDateEditor().getUiComponent().addFocusListener(new FocusAdapter()    {
	        @Override
	        public void focusGained(FocusEvent evt) {
	        	((JTextFieldDateEditor)evt.getSource()).selectAll();
	        }
	    });
		return date;
	}
	
	public Object getCellEditorValue() 
	{
		return new String(((JTextField)date.getDateEditor().getUiComponent()).getText());
	}
	
//	public boolean stopCellEditing()
//	{
//		return super.stopCellEditing();
//	}
//	
//	protected void fireEditingStopped() {
//		super.fireEditingStopped();
//	}
}