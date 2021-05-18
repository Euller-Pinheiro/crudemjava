package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
	// parâmetros de conexão
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/dbagenda";
	private String user = "root";
	private String password = "";

	/**
	 * Conexão com o banco de dados
	 * @return con
	 */
	public Connection conectar() {
		// con -> objeto
		Connection con = null;
		// tratamento de exceções
		try {
			// uso do driver
			Class.forName(driver);
			// estabelecendo a conexão
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
