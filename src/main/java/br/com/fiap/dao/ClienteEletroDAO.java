package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.fiap.beans.ClienteEletro;


public class ClienteEletroDAO {

	private Connection myConnection;

	public ClienteEletroDAO(Connection connection) throws ClassNotFoundException, SQLException {
		super();
		this.myConnection = connection;
	}
	
	public String inserir(int clienteId, int eletronicoId, double horasUsado) throws SQLException {
		String sql = "INSERT INTO t_clientes_eletros (cliente_id, eletronico_id, horas_usadas) VALUES (?, ?, ?)";
		try (PreparedStatement stmt = myConnection.prepareStatement(sql)) {
			stmt.setInt(1, clienteId);
			stmt.setInt(2, eletronicoId);
			stmt.setDouble(3, horasUsado);
			stmt.executeUpdate();
			return "Informaçoes inseridas com sucesso!";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Erro ao inserir informaçoes: " + e.getMessage();
		}
	}
	
	public ArrayList<ClienteEletro> selecionarTodos() throws SQLException {
		ArrayList<ClienteEletro> listaCE = new ArrayList<>();
		String sql = "SELECT * FROM t_clientes_eletros";

		try (PreparedStatement stmt = myConnection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				ClienteEletro ce = new ClienteEletro(
						rs.getInt("cliente_eletro_id"),
						rs.getInt("cliente_id"),
						rs.getInt("eletronico_id"),
						rs.getDouble("horas_usadas")
						);

				listaCE.add(ce);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao selecionar todos os clientes e eletrodomesticos: " + e.getMessage());
			throw e;
		}

		return listaCE;
	}
	
	// atualizar nao inserido para que nao haja problemas com a troca do ID
	
	public String deletar(int clienteEletroId) throws SQLException {
		String sql = "DELETE FROM t_clientes_eletros WHERE cliente_eletro_id = ?";
		try (PreparedStatement stmt = myConnection.prepareStatement(sql)) {
			stmt.setInt(1, clienteEletroId);

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				return "Associaçao deletada com sucesso!";
			} else {
				return "Associaçao não encontrada.";
			}
		} catch (SQLException e) {
			System.err.println("Erro ao deletar associaçao: " + e.getMessage());
			throw e;
		}
	}
	
}
