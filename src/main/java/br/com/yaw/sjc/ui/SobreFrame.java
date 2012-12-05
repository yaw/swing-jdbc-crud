package br.com.yaw.sjc.ui;

import static br.com.yaw.sjc.app.util.ApplicationProperties.getBuild;
import static br.com.yaw.sjc.app.util.ApplicationProperties.getDesenvolvidoPor;
import static br.com.yaw.sjc.app.util.ApplicationProperties.getSite;
import static br.com.yaw.sjc.app.util.ApplicationProperties.getTitulo;
import static br.com.yaw.sjc.app.util.ApplicationProperties.getVersao;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Tela <i>Sobre</i>. Apresenta detalhes da aplicação.
 * 
 * @see br.com.yaw.sjc.util.ApplicationProperties
 * 
 * @author YaW Tecnologia
 */
public class SobreFrame extends JFrame {

	public SobreFrame(){
		setTitle("Sobre a aplicação");
		setSize(400,200);
		setLocationRelativeTo(null);
		setResizable(false);
		inicializaComponentes();
	}
	
	private void inicializaComponentes() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(montaLabelsSobre(), BorderLayout.CENTER);
		add(panel);
	}
	
	private JPanel montaLabelsSobre() {
		JPanel painelLabels = new JPanel();
		
		GridLayout layout = new GridLayout(5, 2);
		painelLabels.setLayout(layout);

		painelLabels.add(new JLabel("Aplicação:"));
		painelLabels.add(new JLabel(getTitulo()));
		painelLabels.add(new JLabel("Versão:"));
		painelLabels.add(new JLabel(getVersao()));
		painelLabels.add(new JLabel("Build:"));
		painelLabels.add(new JLabel(getBuild()));
		painelLabels.add(new JLabel("Desenv. por:"));
		painelLabels.add(new JLabel(getDesenvolvidoPor()));
		painelLabels.add(new JLabel("Site:"));
		painelLabels.add(new JLabel(getSite()));

		return painelLabels;
	}
	
}
