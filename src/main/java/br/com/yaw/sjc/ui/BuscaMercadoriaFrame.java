package br.com.yaw.sjc.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.yaw.sjc.dao.MercadoriaDAO;
import br.com.yaw.sjc.dao.MercadoriaDAOJDBC;
import br.com.yaw.sjc.model.Mercadoria;

/**
 * Tela utilizada para realizar a pesquisa de <code>Mercadoria</code> 
 * com filtro no campo <code>nome</code>.  
 * 
 * @author YaW Tecnologia
 */
public class BuscaMercadoriaFrame extends JFrame {
	
	private JTextField tfNome;
	private JButton bBuscar;
	
	private ListaMercadoriasFrame framePrincipal;
	
	public BuscaMercadoriaFrame(ListaMercadoriasFrame framePrincipal) {
		this.framePrincipal = framePrincipal;
		setTitle("Buscar");
		setSize(250, 250);
		setLocationRelativeTo(null);
		inicializaComponentes();
	}
	
	private void inicializaComponentes() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(montaPanelBuscaMercadoria(), BorderLayout.CENTER);
		panel.add(montaPanelBotoesBusca(), BorderLayout.SOUTH);
		this.add(panel);
		
		GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
	}
	
	private JPanel montaPanelBotoesBusca() {
		JPanel panel = new JPanel();
		
		bBuscar = new JButton("Buscar");
		bBuscar.setMnemonic(KeyEvent.VK_B);
		bBuscar.addActionListener(new BuscarPorNomeListener());
		panel.add(bBuscar);
		
		return panel;
	}
	
	private void resetForm() {
		tfNome.setText("");
	}

	private JPanel montaPanelBuscaMercadoria() {
		JPanel painel = new JPanel();
		GridLayout layout = new GridLayout(8, 1);
		painel.setLayout(layout);

		tfNome = new JTextField();
		painel.add(new JLabel("Nome:"));
		painel.add(tfNome);
		
		return painel;
	}
	
	private class BuscarPorNomeListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String nome = tfNome.getText();
			if (nome.isEmpty()) {
				JOptionPane.showMessageDialog(BuscaMercadoriaFrame.this, 
						"Informe o nome (filtro) para a pesquisa.", "Atenção", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			try {
				MercadoriaDAO dao = new MercadoriaDAOJDBC();
				final List<Mercadoria> mercadorias = dao.getMercadoriasByNome(tfNome.getText());
				resetForm();
				setVisible(false);
				
				framePrincipal.refreshTable(mercadorias);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(BuscaMercadoriaFrame.this,
						ex.getMessage(), "Erro ao buscar Mercadoria(s)", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
}
