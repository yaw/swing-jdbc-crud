package br.com.yaw.sjc.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.yaw.sjc.model.Mercadoria;

/**
 * Define um TableModel para entidade <code>Mercadoria</code>, considerando as colunas:
 * <ul>
 *   <li>Nome;</li>
 *   <li>Descrição;</li>
 *   <li>Preço;</li>
 *   <li>Quantidade;</li>
 * </ul> 
 * 
 * @author YaW Tecnologia
 */
public class MercadoriaTableModel extends AbstractTableModel {

	private List<Mercadoria> mercadorias;
	
	private String[] colNomes = { "Nome", "Descricao", "Preco", "Quantidade" };
	private Class<?>[] colTipos = { String.class, String.class, String.class, Integer.class };
	
	public MercadoriaTableModel(){
	}
	
	public void reload(List<Mercadoria> mercadorias) {
		this.mercadorias = mercadorias;
		//atualiza o componente na tela
		fireTableDataChanged();
	}

	@Override
	public Class<?> getColumnClass(int coluna) {
		return colTipos[coluna];
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public String getColumnName(int coluna) {
		return colNomes[coluna];
	}

	@Override
	public int getRowCount() {
		if (mercadorias == null){
			return 0;
		}
		return mercadorias.size();
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		Mercadoria m = mercadorias.get(linha);
		switch (coluna) {
		case 0:
			return m.getNome();
		case 1:
			return m.getDescricao();
		case 2:
			return Mercadoria.convertPrecoToString(m.getPreco());
		case 3:
			return m.getQuantidade();
		default:
			return null;
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	public Mercadoria getMercadoriaAt(int index) {
		return mercadorias.get(index);
	}
}
