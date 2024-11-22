package br.com.fiap.conexoes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {

	public Connection conexao() throws ClassNotFoundException, SQLException {

		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl", "RM555679", "011203");
		
		if (connection != null) {
	        System.out.println("Conexão criada com sucesso!");
	    } else {
	        System.err.println("Falha ao criar conexão!");
	    }
		return connection;
	}

}
