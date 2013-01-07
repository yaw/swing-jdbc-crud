package br.com.yaw.sjc.model;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Classe de modelo que representa uma mercadoria.
 * 
 * <p>A <code>mercadoria</code> é um objeto persistido no banco de dados, por isso a classificamos como <strong>Entidade</strong>.</p>
 * 
 * <p>As funcionalidades desse sistema demonstração são concentradas no cadastro (CRUD) de mercadorias.</p>
 * 
 * @author YaW Tecnologia
 */
public class Mercadoria {

	private Integer id;
	private String nome;
	private String descricao;
	private Integer quantidade;
	private Double preco;
	
	private static final NumberFormat numberFmt = NumberFormat.getNumberInstance(new Locale("pt","BR"));
	
	public Mercadoria(){
	}
	
	public Mercadoria(Integer id, String nome, String descricao, Integer quantidade, Double preco) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.quantidade = quantidade;
		this.preco = preco;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Integer getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	public static String convertPrecoToString(double preco) {
		return numberFmt.format(preco);
	}
	
	public static double formatStringToPreco(String strPreco) throws ParseException {
		 return numberFmt.parse(strPreco).doubleValue();
	}
	
	public String toString() {
		return "[ " + nome + " - " + descricao + " - " + quantidade + " - " + preco + " ]";
	}
	
}
