package br.com.yaw.sjc.ui;

import br.com.yaw.sjc.model.Mercadoria;

/**
 * Tela para editar o registro da mercadoria.
 * 
 * @author YaW Tecnologia
 */
public class EditarMercadoriaFrame extends IncluirMercadoriaFrame {

	public EditarMercadoriaFrame(ListaMercadoriasFrame framePrincipal) {
		super(framePrincipal);
		setTitle("Editar Mercadoria");
		bExcluir.setVisible(true);
	}
	
	protected Mercadoria loadMercadoriaFromPanel() {
		Mercadoria m = super.loadMercadoriaFromPanel();
		m.setId(getIdMercadoria());
		return m;
	}
	
}
