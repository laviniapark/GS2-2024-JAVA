package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.fiap.beans.User;

public class UserDAO {

	private Connection myConnection;

	public UserDAO(Connection connection) {
		super();
		this.myConnection = connection;
	}
	
	public String inserir(User user) throws SQLException {
		String sql = "INSERT INTO t_users (email, senha, cliente_id) VALUES (?, ?, ?)";
		try (PreparedStatement stmt = myConnection.prepareStatement(sql)) {
			stmt.setString(1, user.getEmail());
			stmt.setString(2, user.getSenha());
			stmt.setInt(3, user.getClienteId());
			stmt.executeUpdate();
			return "Usuario inserido com sucesso!";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Erro ao inserir usuario: " + e.getMessage();
		}
	}
	
	public User selecionar(int userId) throws SQLException {
		String sql = "SELECT * FROM t_users WHERE usuario_id = ?";
		try (PreparedStatement stmt = myConnection.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new User(
							rs.getInt("usuario_id"),
							rs.getString("email"),
							rs.getString("senha"),
							rs.getInt("cliente_id")
							);
				}
			}
		} catch (SQLException e) {
			System.out.println("Erro ao selecionar usuario: " + e.getMessage());
			throw e;
		}
		return null;
	}
	
	public ArrayList<User> selecionarTodos() throws SQLException {
		ArrayList<User> listaUsers = new ArrayList<>();
		String sql = "SELECT * FROM t_users ORDER BY email";

		try (PreparedStatement stmt = myConnection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				User user = new User(
						rs.getInt("usuario_id"),
						rs.getString("email"),
						rs.getString("senha"),
						rs.getInt("cliente_id"));

				listaUsers.add(user);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao selecionar todos os usuarios: " + e.getMessage());
			throw e;
		}

		return listaUsers;
	}
	
	public String atualizar(User user) throws SQLException {
	    String sql = "UPDATE t_users SET email = ?, senha = ? WHERE usuario_id = ?";

	    try (PreparedStatement stmt = myConnection.prepareStatement(sql)) {
	        stmt.setString(1, user.getEmail());
	        stmt.setString(2, user.getSenha());
	        stmt.setInt(3, user.getUserId());

	        int rowsAffected = stmt.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("Usuario atualizado com sucesso no banco!");
	            return "Usuario atualizado com sucesso!";
	        } else {
	            System.err.println("Usuario nao encontrado para atualizaçao. ID: " + user.getUserId());
	            return "Usuario nao encontrado para atualização.";
	        }
	    } catch (SQLException e) {
	        System.err.println("Erro ao atualizar usuario no DAO: " + e.getMessage());
	        throw new SQLException("Erro ao atualizar usuario no banco: " + e.getMessage(), e);
	    }
	}

	public String deletar(int userId) throws SQLException {
		String sql = "DELETE FROM t_users WHERE usuario_id = ?";
		try (PreparedStatement stmt = myConnection.prepareStatement(sql)) {
			stmt.setInt(1, userId);

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				return "Usuario deletado com sucesso!";
			} else {
				return "Usuario nao encontrado.";
			}
		} catch (SQLException e) {
			System.err.println("Erro ao deletar usuario: " + e.getMessage());
			throw e;
		}
	}
	
	public int selecionarClienteIdPorUsuario(int userId) throws SQLException {
	    String sql = "SELECT cliente_id FROM t_users WHERE usuario_id = ?";
	    try (PreparedStatement stmt = myConnection.prepareStatement(sql)) {
	        stmt.setInt(1, userId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("cliente_id");
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Erro ao buscar cliente_id: " + e.getMessage());
	        throw e;
	    }
	    throw new SQLException("Usuario nao encontrado.");
	}
	
	public boolean existeEmail(String email) throws SQLException {
	    String sql = "SELECT 1 FROM t_users WHERE email = ?";
	    try (PreparedStatement stmt = myConnection.prepareStatement(sql)) {
	        stmt.setString(1, email);
	        try (ResultSet rs = stmt.executeQuery()) {
	            return rs.next();
	        }
	    }
	}

	public boolean existeUsuario(int userId) throws SQLException {
	    String sql = "SELECT 1 FROM t_users WHERE usuario_id = ?";
	    try (PreparedStatement stmt = myConnection.prepareStatement(sql)) {
	        stmt.setInt(1, userId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            return rs.next();
	        }
	    } catch (SQLException e) {
	        System.err.println("Erro ao verificar se o usuário existe: " + e.getMessage());
	        throw e;
	    }
	}
  
	
}
