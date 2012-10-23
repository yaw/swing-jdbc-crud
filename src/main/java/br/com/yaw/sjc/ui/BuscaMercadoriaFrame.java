package br.com.yaw.sjc.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import br.com.yaw.sjc.dao.MercadoriaDAO;
import br.com.yaw.sjc.dao.MercadoriaDAOJDBC;
import br.com.yaw.sjc.model.Mercadoria;

/**
 * Tela utilizada para realizar a pesquisa de mercadoria com filtro no campo nome. 
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
	}
	
	private JPanel montaPanelBotoesBusca() {
		JPanel panel = new JPanel();
		
		bBuscar = new JButton("Buscar");
		bBuscar.setMnemonic(KeyEvent.VK_B);
		bBuscar.addMouseListener(new BuscarPorNomeListener());
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
	
	private class BuscarPorNomeListener extends MouseAdapter {

		public void mouseClicked(MouseEvent event) {
			String nome = tfNome.getText();
			if (nome.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Informe o nome (filtro) para a pesquisa.", "Atencao", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			new Thread(newBuscarMercadoriasAction()).start();
		}
	}
	
	private Runnable newBuscarMercadoriasAction() {
		return new Runnable() {
			@Override
			public void run() {
				try {
					MercadoriaDAO dao = new MercadoriaDAOJDBC();
					final List<Mercadoria> mercadorias = dao.getMercadoriasByNome(tfNome.getText());
					resetForm();
					setVisible(false);
					
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							framePrincipal.refreshTable(mercadorias);
						}
					});
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro ao buscar", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
	}
	
}
