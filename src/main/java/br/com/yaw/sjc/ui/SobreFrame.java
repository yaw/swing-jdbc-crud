package br.com.yaw.sjc.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Tela <i>Sobre</i>. Apresenta detalhes da aplicação.
 * 
 * <p>Le o arquivo <code>META-INF/MANIFEST.MF</code>.</p>
 * 
 * @author YaW Tecnologia
 */
public class SobreFrame extends JFrame {

	private static Manifest manifest;
	
	static {
		try {
			manifest = new Manifest(SobreFrame.class.getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF"));
		} catch (Exception e) {}
	}
	
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
		
		if (manifest != null) {
			GridLayout layout = new GridLayout(5, 2);
			painelLabels.setLayout(layout);

			painelLabels.add(new JLabel("Aplicação:"));
			painelLabels.add(new JLabel(getManifestProperty("Implementation-Title")));
			painelLabels.add(new JLabel("Versão:"));
			painelLabels.add(new JLabel(getManifestProperty("Implementation-Version")));
			painelLabels.add(new JLabel("Build:"));
			painelLabels.add(new JLabel(getManifestProperty("Implementation-Build")));
			painelLabels.add(new JLabel("Desenv. por:"));
			painelLabels.add(new JLabel(getManifestProperty("Built-By")));
			painelLabels.add(new JLabel("Site:"));
			painelLabels.add(new JLabel("http://www.yaw.com.br"));			
		}

		return painelLabels;
	}
	
	private String getManifestProperty(String property) {
		if (manifest != null) {
			Attributes attributes = manifest.getMainAttributes();
			return attributes.getValue(property);
		}
		
		return "";
	}
	
}
