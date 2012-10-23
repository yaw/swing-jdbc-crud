package br.com.yaw.sjc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.yaw.sjc.exception.PersistenceException;
import br.com.yaw.sjc.model.Mercadoria;

/**
 * Implementa o componente de persistencia da Mercadoria, utilizando codigo JDBC.
 * 
 * @author YaW Tecnologia
 */
public class MercadoriaDAOJDBC implements MercadoriaDAO {

	private final static String CREATE_TABLE = "CREATE TABLE  IF NOT EXISTS mercadoria (id INTEGER IDENTITY PRIMARY KEY NOT NULL, nome VARCHAR(20) NOT NULL, descricao varchar(80) NOT NULL, preco REAL NOT NULL, quantidade INTEGER NOT NULL)";
	private final static String INSERT_MERCADORIA = "INSERT INTO mercadoria (nome,descricao,preco,quantidade) VALUES (?,?,?,?)";
	private final static String UPDATE_MERCADORIA = "UPDATE mercadoria SET nome = ?, descricao = ?, preco = ?, quantidade = ? WHERE id = ?";
	private final static String DELETE_MERCADORIA = "DELETE FROM mercadoria WHERE id = ?";
	private final static String GET_ALL_MERCADORIAS = "SELECT * FROM mercadoria";
	private final static String GET_MERCADORIAS_BY_NOME = "SELECT * FROM mercadoria WHERE nome like ?";
	private final static String GET_MERCADORIA_BY_ID = "SELECT * FROM mercadoria WHERE id = ?";
	
	private static Logger log = Logger.getLogger(MercadoriaDAOJDBC.class);
	
	public void init() throws PersistenceException {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectionManager.getConnection();
			stmt = conn.createStatement();
			int r = stmt.executeUpdate(CREATE_TABLE);
			
			if (r > 0) {
				log.info("Criou a tabela 'mercadoria'");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Nao foi possivivel inicializar o banco de dados: " + CREATE_TABLE, e);
		} finally {
			ConnectionManager.closeAll(conn, stmt);
		}
	}
	
	public void save(Mercadoria mercadoria) throws PersistenceException {
		if (mercadoria == null) {
			throw new PersistenceException("Informe a mercadoria para salvar!");
		}
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = ConnectionManager.getConnection();
			if (mercadoria.getId() == null) {
				stmt = getStatementInsert(conn, mercadoria);
			} else {
				stmt = getStatementUpdate(conn, mercadoria);
			}
			stmt.executeUpdate();
		} catch (SQLException e) {
			String errorMsg = "Erro ao salvar mercadoria!";
			log.error(errorMsg, e);
			throw new PersistenceException(errorMsg, e);
		} finally {
			ConnectionManager.closeAll(conn, stmt);
		}
	}
	
	private PreparedStatement getStatementInsert(Connection conn, Mercadoria m) throws SQLException {
		PreparedStatement stmt = createStatementWithLog(conn, INSERT_MERCADORIA);
		
		stmt.setString(1, m.getNome());
		stmt.setString(2, m.getDescricao());
		stmt.setDouble(3, m.getPreco());
		stmt.setInt(4, m.getQuantidade());
		return stmt;
	}
	
	private PreparedStatement getStatementUpdate(Connection conn, Mercadoria m) throws SQLException {
		PreparedStatement stmt = createStatementWithLog(conn, UPDATE_MERCADORIA);
		stmt.setString(1, m.getNome());
		stmt.setString(2, m.getDescricao());
		stmt.setDouble(3, m.getPreco());
		stmt.setInt(4, m.getQuantidade());
		stmt.setInt(5, m.getId());
		return stmt;
	}

	public void remove(Mercadoria m) throws PersistenceException {
		if (m == null || m.getId() == null) {
			throw new PersistenceException("Informe a mercadoria para exclusao!");
		}
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = ConnectionManager.getConnection();
			stmt = createStatementWithLog(conn, DELETE_MERCADORIA);
			stmt.setInt(1, m.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			String errorMsg = "Erro ao excluir mercadoria!";
			log.error(errorMsg, e);
			throw new PersistenceException(errorMsg, e);
		}finally{
			ConnectionManager.closeAll(conn, stmt);
		}
	}
	
	@Override
	public Mercadoria findById(Integer id) throws PersistenceException {
		if (id == null || id.intValue() <= 0) {
			throw new PersistenceException("Informe o id valido para fazer a busca!");
		}
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Mercadoria m = null;
		
		try {
			conn = ConnectionManager.getConnection();
			stmt = createStatementWithLog(conn, GET_MERCADORIA_BY_ID);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				String nome = rs.getString("nome");
				String descricao = rs.getString("descricao");
				int qtde = rs.getInt("quantidade");
				double preco = rs.getDouble("preco");
				
				m = new Mercadoria(id, nome, descricao, qtde, preco);
			}
			
			return m;
		} catch (SQLException e) {
			String errorMsg = "Erro ao consultar mercadoria por id!";
			log.error(errorMsg, e);
			throw new PersistenceException(errorMsg, e);
		} finally {
			ConnectionManager.closeAll(conn, stmt, rs);
		}
	}
	
	public List<Mercadoria> getAllMercadorias() throws PersistenceException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = ConnectionManager.getConnection();
			stmt = createStatementWithLog(conn, GET_ALL_MERCADORIAS);
			rs = stmt.executeQuery();
			
			return toMercadorias(rs);
		} catch (SQLException e) {
			String errorMsg = "Erro ao consultar todas as mercadorias!";
			log.error(errorMsg, e);
			throw new PersistenceException(errorMsg, e);
		} finally {
			ConnectionManager.closeAll(conn, stmt, rs);
		}
	}
	
	public List<Mercadoria> getMercadoriasByNome(String nome) throws PersistenceException {
		if (nome == null || nome.isEmpty()) {
			return Collections.EMPTY_LIST;
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		
		try {
			conn = ConnectionManager.getConnection();
			stmt = createStatementWithLog(conn, GET_MERCADORIAS_BY_NOME);
			stmt.setString(1, nome + "%");
			rs = stmt.executeQuery();
			
			return toMercadorias(rs);
		} catch (SQLException e) {
			String errorMsg = "Erro ao consultar mercadorias por nome!";
			log.error(errorMsg, e);
			throw new PersistenceException(errorMsg, e);
		} finally {
			ConnectionManager.closeAll(conn, stmt, rs);
		}
	}
	
	private List<Mercadoria> toMercadorias(ResultSet rs) throws SQLException {
		List<Mercadoria> lista = new ArrayList<Mercadoria>();
		while (rs.next()) {
			int id = rs.getInt("id");
			String nome = rs.getString("nome");
			String descricao = rs.getString("descricao");
			int qtde = rs.getInt("quantidade");
			double preco = rs.getDouble("preco");
			
			lista.add(new Mercadoria(id, nome, descricao, qtde, preco));
		}
		return lista;
	}

	private static PreparedStatement createStatementWithLog(Connection conn, String sql) throws SQLException{
		if (conn == null)
			return null;
		
		log.debug("SQL: "+sql);
		return conn.prepareStatement(sql);
	}
	
}
