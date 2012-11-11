package br.com.yaw.sjc.ui;

import java.util.List;

import javax.swing.JTable;

import br.com.yaw.sjc.model.Mercadoria;

/**
 * <code>JTable</code> com operações customizadas para entidade <code>Mercadoria</code>.
 * 
 * @see br.com.yaw.sjc.ui.MercadoriaTableModel
 * 
 * @author YaW Tecnologia
 */
public class MercadoriaTable extends JTable {

	private MercadoriaTableModel modelo;
	
	public MercadoriaTable() {
		modelo = new MercadoriaTableModel();
		setModel(modelo);
	}
	
	public Mercadoria getMercadoriaSelected() {
		int i = getSelectedRow();
		if (i < 0) {
			return null;
		}
		return modelo.getMercadoriaAt(i);
	}
	
	public void reload(List<Mercadoria> mercadorias) {
		modelo.reload(mercadorias);
	}
}
