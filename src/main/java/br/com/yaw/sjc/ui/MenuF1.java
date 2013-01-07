package br.com.yaw.sjc.ui;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.KeyStroke;

/**
 * <code>JMenu</code> com atalho pré-definido para F1 (hot key).
 * 
 * @author YaW Tecnologia
 */
public class MenuF1 extends JMenu {

	public MenuF1(String title) {
		super(title);
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F1"),"click");
	}
	
	/**
	 * @param action vinculada com a tecla F1.
	 */
	public void addListener(final AbstractAction action) {
		this.getActionMap().put("click", action);
	}

}
