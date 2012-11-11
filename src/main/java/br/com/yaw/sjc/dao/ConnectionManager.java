package br.com.yaw.sjc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import br.com.yaw.sjc.exception.PersistenceException;

/**
 * Componente responsável por abrir e encerrar a conexão com o banco de dados.
 * 
 * @author YaW Tecnologia
 */
public class ConnectionManager {

	//Informacões para conexão com banco de dados HSQLDB.
	private static final String STR_DRIVER = "org.hsqldb.jdbcDriver";
	private static final String DATABASE = "mercadoria";
	private static final String STR_CON = "jdbc:hsqldb:file:" + DATABASE;
	private static final String USER = "sa";
	private static final String PASSWORD = "";
	
	private static Logger log = Logger.getLogger(ConnectionManager.class);
	

	public static Connection getConnection() throws PersistenceException {
		Connection conn = null;
		try {
			Class.forName(STR_DRIVER);
			conn = DriverManager.getConnection(STR_CON, USER, PASSWORD);
			conn.setAutoCommit(false);
			
			log.debug("Aberta a conexão com banco de dados!");
			return conn;
		} catch (ClassNotFoundException e) {
			String errorMsg = "Driver (JDBC) não encontrado";
			log.error(errorMsg, e);
			throw new PersistenceException(errorMsg, e);
		} catch (SQLException e) {
			String errorMsg = "Erro ao obter a conexão";
			log.error(errorMsg, e);
			throw new PersistenceException(errorMsg, e);
		}
	}

	public static void closeAll(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
				log.debug("Fechada a conexão com banco de dados!");
			}
		} catch (Exception e) {
			log.error("Não foi possivel fechar a conexão com o banco de dados!",e);
		}
	}

	public static void closeAll(Connection conn, Statement stmt) {
		try {
			if (conn != null) {
				closeAll(conn);
			}
			if (stmt != null) {
				stmt.close();
			}
		} catch (Exception e) {
			log.error("Não foi possivel fechar o statement!",e);
		}
	}

	public static void closeAll(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (conn != null || stmt != null) {
				closeAll(conn, stmt);
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			log.error("Não foi possivel fechar o resultSet!",e);
		}
	}

}
