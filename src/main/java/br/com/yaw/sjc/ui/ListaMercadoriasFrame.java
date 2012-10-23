package br.com.yaw.sjc.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import br.com.yaw.sjc.dao.MercadoriaDAO;
import br.com.yaw.sjc.dao.MercadoriaDAOJDBC;
import br.com.yaw.sjc.exception.PersistenceException;
import br.com.yaw.sjc.model.Mercadoria;

/**
 * Tela principal da aplicacao. Apresenta uma lista com as mercadorias cadastradas. 
 * A partir dessa tela eh possivel criar/editar ou pesquisar mercadoria.
 * 
 * @author YaW Tecnologia
 */
public class ListaMercadoriasFrame extends JFrame {
	
	private MercadoriaTable tabela;
	private JScrollPane scrollPane;
	private JButton bNovaMercadoria;
	private JButton bExcluirMercadoria;
	private JButton bBuscarMercadoria;
	private JButton bAtualizaLista;
	
	private IncluirMercadoriaFrame incluirFrame;
	private EditarMercadoriaFrame editarFrame;
	private BuscaMercadoriaFrame buscaFrame;
	
	public ListaMercadoriasFrame() {
		setTitle("Lista de Mercadoria");
		
		inicializaComponentes();
		adicionaComponentes();
		
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void inicializaComponentes() {
		tabela = new MercadoriaTable();
		tabela.addMouseListener(new EditarMercadoriaListener());
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(tabela);

		bNovaMercadoria = new JButton();
		bNovaMercadoria.setText("Nova");
		bNovaMercadoria.setMnemonic(KeyEvent.VK_N);
		bNovaMercadoria.addActionListener(new IncluirMercadoriaListener());

		bExcluirMercadoria = new JButton();
		bExcluirMercadoria.setText("Excluir");
		bExcluirMercadoria.setMnemonic(KeyEvent.VK_E);
		bExcluirMercadoria.addActionListener(new ExcluirMercadoriaListener());

		bBuscarMercadoria = new JButton();
		bBuscarMercadoria.setText("Buscar");
		bBuscarMercadoria.setMnemonic(KeyEvent.VK_B);
		bBuscarMercadoria.addActionListener(new BuscarMercadoriaListener());
		
		bAtualizaLista = new JButton();
		bAtualizaLista.setText("Atualizar");
		bAtualizaLista.setMnemonic(KeyEvent.VK_A);
		bAtualizaLista.addActionListener(new AtualizarListaListener());
		
		incluirFrame = new IncluirMercadoriaFrame(this);
		editarFrame = new EditarMercadoriaFrame(this);
		buscaFrame = new BuscaMercadoriaFrame(this);
		
		inicializaDB();
	}
	
	private void adicionaComponentes(){
		add(scrollPane);
		JPanel panel = new JPanel();
		panel.add(bNovaMercadoria);
		panel.add(bExcluirMercadoria);
		panel.add(bBuscarMercadoria);
		panel.add(bAtualizaLista);
		add(panel, BorderLayout.SOUTH);
	}
	
	private void inicializaDB() {
		try {
			new MercadoriaDAOJDBC().init();
			SwingUtilities.invokeLater(newAtualizaMercadoriasAction());
		} catch (PersistenceException ex) {
			JOptionPane.showMessageDialog(null, "Nao foi possivel utilizar o Banco de dados: "+
					ex.getMessage()+"\nVerifique a dependencia do driver ou configuracoes do banco!", "Erro", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	public Runnable newAtualizaMercadoriasAction() {
		return new Runnable() {
			@Override
			public void run() {
				try {
					MercadoriaDAO dao = new MercadoriaDAOJDBC();
					tabela.reload(dao.getAllMercadorias());
				} catch (PersistenceException ex) {
					JOptionPane.showMessageDialog(null, "Erro ao recarregar tabela: "+ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}				
			}
		};
	}
	
	public void refreshTable(List<Mercadoria> mercadorias) {
		tabela.reload(mercadorias);
	}
	
	private class ExcluirMercadoriaListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Mercadoria m = tabela.getMercadoriaSelected();
			if (m != null) {
				new Thread(newExcluirMercadoriaAction(m)).start();
			}
		}
	}
	
	private Runnable newExcluirMercadoriaAction(final Mercadoria m) {
		return new Runnable() {
			@Override
			public void run() {
				try {
					MercadoriaDAO dao = new MercadoriaDAOJDBC();
					dao.remove(m);
					SwingUtilities.invokeLater(newAtualizaMercadoriasAction());
					
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro ao excluir", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
	}
	
	private class AtualizarListaListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(newAtualizaMercadoriasAction());
		}
	}
	
	private class IncluirMercadoriaListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			incluirFrame.setVisible(true);
		}
	}
	
	private class EditarMercadoriaListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			if (event.getClickCount() == 2) {
				Mercadoria m = tabela.getMercadoriaSelected();
				if (m != null) {
					editarFrame.setMercadoria(m);
					editarFrame.setVisible(true);
				}
			}
		}
	}
	
	private class BuscarMercadoriaListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			buscaFrame.setVisible(true);
		}
	}

}
