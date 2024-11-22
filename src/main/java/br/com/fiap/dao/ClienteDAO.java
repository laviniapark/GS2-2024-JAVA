package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import br.com.fiap.beans.Cliente;

public class ClienteDAO {

	private Connection myConnection;

	public ClienteDAO(Connection connection) throws ClassNotFoundException, SQLException {
		super();
		this.myConnection = connection;
	}

	public String inserir(Cliente cliente) throws SQLException {
		String sqlCliente = "INSERT INTO t_clientes (nome, endereco_id, cep) VALUES (?, ?, ?)";
		try (PreparedStatement stmtCliente = myConnection.prepareStatement(sqlCliente)) {
			stmtCliente.setString(1, cliente.getNome());
			stmtCliente.setInt(2, cliente.getEnderecoId());
			stmtCliente.setString(3, cliente.getCep());
			stmtCliente.executeUpdate();
			return "Cliente inserido com sucesso!";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Erro ao inserir cliente: " + e.getMessage();
		}
	}

	public Cliente selecionar(int clienteId) throws SQLException {
		String sql = "SELECT * FROM t_clientes WHERE cliente_id = ?";
		try (PreparedStatement stmt = myConnection.prepareStatement(sql)) {
			stmt.setInt(1, clienteId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Cliente(rs.getInt("cliente_id"), rs.getString("nome"), rs.getInt("endereco_id"),
							rs.getString("cep"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Erro ao selecionar cliente: " + e.getMessage());
			throw e;
		}
		return null;
	}

	public ArrayList<Cliente> selecionarTodos() throws SQLException {
		ArrayList<Cliente> listaClientes = new ArrayList<>();
		String sql = "SELECT * FROM t_clientes ORDER BY nome";

		try (PreparedStatement stmt = myConnection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Cliente cliente = new Cliente(rs.getInt("cliente_id"), rs.getString("nome"), rs.getInt("endereco_id"),
						rs.getString("cep"));

				listaClientes.add(cliente);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao selecionar todos os clientes: " + e.getMessage());
			throw e;
		}

		return listaClientes;
	}

	public String atualizar(Cliente cliente, Connection connection) throws SQLException {
	    String sql = "UPDATE t_clientes SET nome = ?, endereco_id = ?, cep = ? WHERE cliente_id = ?";

	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {

	        stmt.setString(1, cliente.getNome());
	        stmt.setInt(2, cliente.getEnderecoId());
	        stmt.setString(3, cliente.getCep());
	        stmt.setInt(4, cliente.getClienteId());

	        int rowsAffected = stmt.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("Cliente atualizado no banco com sucesso.");
	            return "Cliente atualizado com sucesso!";
	        } else {
	            System.err.println("Cliente não encontrado para atualização. ID: " + cliente.getClienteId());
	            return "Cliente não encontrado para atualização.";
	        }
	    } catch (SQLException e) {
	        System.err.println("Erro ao atualizar cliente no DAO: " + e.getMessage());
	        throw new SQLException("Erro ao atualizar cliente no banco: " + e.getMessage(), e);
	    }
	}



	public String deletar(int clienteId) throws SQLException {
		String sql = "DELETE FROM t_clientes WHERE cliente_id = ?";
		try (PreparedStatement stmt = myConnection.prepareStatement(sql)) {
			stmt.setInt(1, clienteId);

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				return "Cliente deletado com sucesso!";
			} else {
				return "Cliente não encontrado.";
			}
		} catch (SQLException e) {
			System.err.println("Erro ao deletar cliente: " + e.getMessage());
			throw e;
		}
	}
	
	public boolean existeCliente(int clienteId) throws SQLException {
	    String sql = "SELECT 1 FROM t_clientes WHERE cliente_id = ?";
	    try (PreparedStatement stmt = myConnection.prepareStatement(sql)) {
	        stmt.setInt(1, clienteId);

	        try (ResultSet rs = stmt.executeQuery()) {
	            return rs.next();
	        }
	    } catch (SQLException e) {
	        System.err.println("Erro ao verificar cliente: " + e.getMessage());
	        throw e;
	    }
	}

}
