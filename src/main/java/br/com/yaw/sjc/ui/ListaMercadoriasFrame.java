package br.com.yaw.sjc.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import br.com.yaw.sjc.dao.MercadoriaDAO;
import br.com.yaw.sjc.dao.MercadoriaDAOJDBC;
import br.com.yaw.sjc.exception.PersistenceException;
import br.com.yaw.sjc.model.Mercadoria;

/**
 * Tela principal da aplicação. Apresenta uma lista com as mercadorias cadastradas. 
 * 
 * <p>A partir dessa tela eh possivel criar/editar ou pesquisar mercadoria.</p>
 * 
 * @author YaW Tecnologia
 */
public class ListaMercadoriasFrame extends JFrame {
	
	private MercadoriaTable tabela;
	private JScrollPane scrollPane;
	private JButton bNovaMercadoria;
	private JButton bBuscarMercadoria;
	private JButton bAtualizaLista;
	private JMenuBar menubar;
	
	private IncluirMercadoriaFrame incluirFrame;
	private EditarMercadoriaFrame editarFrame;
	private BuscaMercadoriaFrame buscaFrame;
	private SobreFrame sobreFrame;
	
	public ListaMercadoriasFrame() {
		setTitle("Lista de Mercadoria");
		
		inicializaComponentes();
		adicionaComponentes();
		
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
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

		bBuscarMercadoria = new JButton();
		bBuscarMercadoria.setText("Buscar");
		bBuscarMercadoria.setMnemonic(KeyEvent.VK_B);
		bBuscarMercadoria.addActionListener(new BuscarMercadoriaListener());
		
		bAtualizaLista = new JButton();
		bAtualizaLista.setText("Atualizar");
		bAtualizaLista.setMnemonic(KeyEvent.VK_A);
		bAtualizaLista.addActionListener(new AtualizarListaListener());
		
		menubar = new JMenuBar();
		JMenu mAjuda = new JMenu("Ajuda");
		mAjuda.setMnemonic(KeyEvent.VK_J);
        JMenuItem miSobre = new JMenuItem("Sobre    - F1");
        miSobre.addActionListener(new SobreMenuListener());
        mAjuda.add(miSobre);
        menubar.add(mAjuda);
        setJMenuBar(menubar);
		
		incluirFrame = new IncluirMercadoriaFrame(this);
		editarFrame = new EditarMercadoriaFrame(this);
		buscaFrame = new BuscaMercadoriaFrame(this);
		sobreFrame = new SobreFrame();
		
		mAjuda.getInputMap().put(KeyStroke.getKeyStroke("F1"),"pressed");
		mAjuda.getActionMap().put("pressed", new SobreMenuListener());
		
		inicializaDB();
	}
	
	private void adicionaComponentes(){
		add(scrollPane);
		JPanel panel = new JPanel();
		panel.add(bNovaMercadoria);
		panel.add(bBuscarMercadoria);
		panel.add(bAtualizaLista);
		add(panel, BorderLayout.SOUTH);
	}
	
	private void inicializaDB() {
		try {
			new MercadoriaDAOJDBC().init();
			SwingUtilities.invokeLater(newAtualizaMercadoriasAction());
		} catch (PersistenceException ex) {
			JOptionPane.showMessageDialog(this, "Não foi possível inicializar o Banco de dados: "+
					ex.getMessage()+"\nVerifique a dependência do driver ou configurações do banco!", "Erro", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	public Runnable newAtualizaMercadoriasAction() {
		return new Runnable() {
			public void run() {
				try {
					MercadoriaDAO dao = new MercadoriaDAOJDBC();
					tabela.reload(dao.getAll());
				} catch (PersistenceException ex) {
					JOptionPane.showMessageDialog(ListaMercadoriasFrame.this,
							ex.getMessage(), "Erro ao consultar Mercadoria(s)", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
	}
	
	public void refreshTable(List<Mercadoria> mercadorias) {
		tabela.reload(mercadorias);
	}
	
	private class AtualizarListaListener implements ActionListener {
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
		public void actionPerformed(ActionEvent event) {
			buscaFrame.setVisible(true);
		}
	}
	
	private class SobreMenuListener extends AbstractAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			sobreFrame.setVisible(true);
		}
	}

}
