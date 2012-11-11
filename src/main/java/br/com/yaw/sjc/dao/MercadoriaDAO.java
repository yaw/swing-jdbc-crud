package br.com.yaw.sjc.dao;

import java.util.List;

import br.com.yaw.sjc.model.Mercadoria;

/**
 * Contrato de persistência para a entidade <code>Mercadoria</code>. 
 * 
 * <p>Define as operações basicas de cadastro (CRUD), seguindo o design pattern <code>Data Access Object</code>.</p>
 * 
 * @author YaW Tecnologia
 */
public interface MercadoriaDAO {

	/**
	 * Faz a insercao ou atualizacao da mercadoria na base de dados.
	 * @param mercadoria
	 */
	void save(Mercadoria mercadoria);
	
	/**
	 * Exclui o registro da mercadoria na base de dados 
	 * @param mercadoria
	 */
	void remove(Mercadoria mercadoria);
	
	/**
	 * @return Lista com todas as mercadorias cadastradas no banco de dados.
	 */
	List<Mercadoria> getAll();
	
	/**
	 * @param nome Filtro da pesquisa utilizando like.
	 * @return Lista de mercadorias com filtro em nome.
	 */
	List<Mercadoria> getMercadoriasByNome(String nome);
	
	/**
	 * @param id filtro da pesquisa.
	 * @return Mercadoria com filtro no id, caso nao exista retorna null.  
	 */
	Mercadoria findById(Integer id);

	/**
	 * Inicializa o componente de persistencia.
	 */
	void init();
}
