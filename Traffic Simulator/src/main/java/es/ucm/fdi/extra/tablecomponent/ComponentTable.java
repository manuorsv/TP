package es.ucm.fdi.extra.tablecomponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;

import es.ucm.fdi.model.Describable;

public class ComponentTable extends JPanel {

	private ListOfMapsTableModel elementsTable;
	private String[] columns;
	private List<? extends Describable> elements;
	private JTable table;

	String name;

	public ComponentTable(String[] c, List<? extends Describable> d, String n) {
		super(new BorderLayout());
		columns = c;
		elements = d;
		name = n;
		initGUI();
	}

	public void setElementsList(List<? extends Describable> l) {
		elements = l;
	}

	private class ListOfMapsTableModel extends AbstractTableModel {

		@Override
		public String getColumnName(int columnIndex) {
			return columns[columnIndex];
		}
		@Override
		public int getRowCount() {
			return elements.size();
		}
		@Override
		public int getColumnCount() {
			return columns.length;
		}
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Map<String, String> out = new HashMap<>();
			out.put("#", "" + rowIndex);
			elements.get(rowIndex).describe(out);
			return out.get((columns[columnIndex]));
		}

		public void updateTable() {
			fireTableDataChanged();
		}
	}

	public void initGUI() {
		this.setPreferredSize(new Dimension(300, 200));

		elementsTable = new ListOfMapsTableModel();
		table = new JTable(elementsTable);

		JScrollPane scroll = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		Border b = BorderFactory.createLineBorder(Color.black, 2);
		scroll.setBorder(BorderFactory.createTitledBorder(b, name));

		this.add(scroll);
	}

	public void pop() {
		elements.remove(0);
		elementsTable.updateTable();
	}

	public void updateTable() {
		elementsTable.updateTable();
	}
}
