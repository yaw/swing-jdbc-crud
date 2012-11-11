package br.com.yaw.sjc.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
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
 * Tela para incluir o registro da <code>Mercadoria</code>.
 * 
 * @author YaW Tecnologia
 */
public class IncluirMercadoriaFrame extends JFrame {

	private JTextField tfNome;
	private JFormattedTextField tfQuantidade;
	private JTextField tfDescricao;
	private JTextField tfPreco;
	private JFormattedTextField tfId;
	
	private JButton bSalvar;
	private JButton bCancelar;
	
	private ListaMercadoriasFrame framePrincipal;

	public IncluirMercadoriaFrame(ListaMercadoriasFrame framePrincipal) {
		this.framePrincipal = framePrincipal;
		setTitle("Incluir");
		setSize(300,250);
		setLocationRelativeTo(null);
		setResizable(false);
		inicializaComponentes();
		resetForm();
	}
	
	private void inicializaComponentes() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(montaPanelEditarMercadoria(), BorderLayout.CENTER);
		panel.add(montaPanelBotoesEditar(), BorderLayout.SOUTH);
		add(panel);
	}
	
	private JPanel montaPanelBotoesEditar() {
		JPanel panel = new JPanel();

		bSalvar = new JButton("Salvar");
		bSalvar.setMnemonic(KeyEvent.VK_S);
		bSalvar.addActionListener(new SalvarMercadoriaListener());
		panel.add(bSalvar);

		bCancelar = new JButton("Cancelar");
		bCancelar.setMnemonic(KeyEvent.VK_C);
		bCancelar.addActionListener(new CancelarListener());
		panel.add(bCancelar);

		return panel;
	}

	private JPanel montaPanelEditarMercadoria() {
		JPanel painelEditarMercadoria = new JPanel();
		GridLayout layout = new GridLayout(8, 1);
		painelEditarMercadoria.setLayout(layout);

		tfNome = new JTextField();
		tfDescricao = new JTextField();
		tfPreco = new JTextField();
		tfQuantidade = new JFormattedTextField();
		tfId = new JFormattedTextField();
		tfId.setEnabled(false);

		painelEditarMercadoria.add(new JLabel("Nome:"));
		painelEditarMercadoria.add(tfNome);
		painelEditarMercadoria.add(new JLabel("Descrição:"));
		painelEditarMercadoria.add(tfDescricao);
		painelEditarMercadoria.add(new JLabel("Preço:"));
		painelEditarMercadoria.add(tfPreco);
		painelEditarMercadoria.add(new JLabel("Quantidade:"));
		painelEditarMercadoria.add(tfQuantidade);
		painelEditarMercadoria.add(new JLabel("Id:"));
		painelEditarMercadoria.add(tfId);

		return painelEditarMercadoria;
	}
	
	private void resetForm() {
		tfId.setValue(null);
		tfNome.setText("");
		tfDescricao.setText("");
		tfPreco.setText("");
		tfQuantidade.setValue(new Integer(1));
	}
	
	private void populaTextFields(Mercadoria m){
		tfId.setValue(m.getId());
		tfNome.setText(m.getNome());
		tfDescricao.setText(m.getDescricao());
		tfQuantidade.setValue(m.getQuantidade());
		tfPreco.setText(Mercadoria.convertPrecoToString(m.getPreco()));
	}
	
	protected Integer getIdMercadoria(){
		return (Integer) tfId.getValue();
	}
	
	private String validador() {
		StringBuilder sb = new StringBuilder();
		sb.append(tfNome.getText() == null || "".equals(tfNome.getText().trim()) ? "Nome, " : "");
		sb.append(tfPreco.getText() == null || "".equals(tfPreco.getText().trim()) ? "Preço, " : "");
		sb.append(tfQuantidade.getText() == null || "".equals(tfQuantidade.getText().trim()) ? "Quantidade, " : "");
		
		if (!sb.toString().isEmpty()) {
			sb.delete(sb.toString().length()-2, sb.toString().length());
		}
		return sb.toString();
	}
	
	protected Mercadoria loadMercadoriaFromPanel() {
		String msg = validador();
		if (!msg.isEmpty()) {
			throw new RuntimeException("Informe o(s) campo(s): "+msg);
		}
		
		String nome = tfNome.getText();
		String descricao = tfDescricao.getText();
		
		Integer quantidade = null;
		try {
			quantidade = Integer.valueOf(tfQuantidade.getText());
		} catch (NumberFormatException nex) {
			throw new RuntimeException("Campo quantidade com conteúdo inválido!");
		}
		
		Double preco = null;
		try {
			preco = Mercadoria.formatStringToPreco(tfPreco.getText());
		} catch (ParseException nex) {
			throw new RuntimeException("Campo preço com conteúdo inválido!");
		}
		
		return new Mercadoria(null, nome, descricao, quantidade, preco);
	}
	
	public void setMercadoria(Mercadoria m){
		resetForm();
		if (m != null) {
			populaTextFields(m);
		}
	}
	
	private class CancelarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			resetForm();
		}
	}
	
	private class SalvarMercadoriaListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				Mercadoria m = loadMercadoriaFromPanel();
				MercadoriaDAO dao = new MercadoriaDAOJDBC();
				dao.save(m);
				
				setVisible(false);
				resetForm();
				SwingUtilities.invokeLater(framePrincipal.newAtualizaMercadoriasAction());
				
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(IncluirMercadoriaFrame.this, ex.getMessage(), "Erro ao incluir Mercadoria", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
