package br.com.yaw.sjc.app.util;

import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import br.com.yaw.sjc.ui.SobreFrame;

/**
 * Recupera e disponibiliza informações do <code>MANIFEST.MF</code> para o aplicativo.
 * 
 * @author YaW Tecnologia
 */
public class ApplicationProperties {
	
	private Manifest manifest;
	
	private static ApplicationProperties instance;
	
	private ApplicationProperties(Manifest manifest){
		this.manifest = manifest;
	}
	
	static {
		try {
			Enumeration<URL> resources = SobreFrame.class.getClassLoader().getResources("META-INF/MANIFEST.MF");
			while (resources.hasMoreElements()) {
		    	Manifest manifest = new Manifest(resources.nextElement().openStream());
		    	if (manifest.getMainAttributes().getValue("Built-By") != null 
		    			&& manifest.getMainAttributes().getValue("Built-By").equals("YaW Tecnologia")){ 
		    		instance = new ApplicationProperties(manifest);
		    		break;
		    	}
			}
	    } catch (Exception e) {}
	}
	
	public static String getTitulo(){
		return checkProperty("Implementation-Title");
	}
	
	public static String getVersao(){
		return checkProperty("Implementation-Version");
	}
	
	public static String getBuild(){
		return checkProperty("Implementation-Build");
	}
	
	public static String getDesenvolvidoPor(){
		return checkProperty("Built-By");
	}
	
	public static String getSite(){
		return checkProperty("Implementation-Site");
	}
	
	private static String checkProperty(String p) {
		if (instance == null)
			return "";
		
		return instance.getManifestProperty(p);
	}
	
	private String getManifestProperty(String property) {
		Attributes attributes = manifest.getMainAttributes();
		return attributes.getValue(property);
	}
	
}
