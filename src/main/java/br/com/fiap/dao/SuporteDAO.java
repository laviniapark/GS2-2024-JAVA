package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.fiap.beans.Suporte;


public class SuporteDAO {

	private Connection myConnection;

	public SuporteDAO(Connection myConnection) {
		super();
		this.myConnection = myConnection;
	}
	
	public String inserir(Suporte sup) throws SQLException {
		String sql = "INSERT INTO t_suporte (nome, email, mensagem) VALUES (?, ?, ?)";
		try (PreparedStatement stmt = myConnection.prepareStatement(sql)) {
			stmt.setString(1, sup.getNome());
			stmt.setString(2, sup.getEmail());
			stmt.setString(3, sup.getMensagem());
			stmt.executeUpdate();
			return "Mensagem enviada com sucesso!";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Erro ao enviar mensagem: " + e.getMessage();
		}
	}
	
	public ArrayList<Suporte> selecionarTodos() throws SQLException {
		ArrayList<Suporte> listaSup = new ArrayList<>();
		String sql = "SELECT * FROM t_suporte ORDER BY suporte_id ASC";

		try (PreparedStatement stmt = myConnection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Suporte sup = new Suporte(
						rs.getInt("suporte_id"), 
						rs.getString("nome"), 
						rs.getString("email"),
						rs.getString("mensagem")
						);

				listaSup.add(sup);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao selecionar todas as mensagens: " + e.getMessage());
			throw e;
		}

		return listaSup;
	}
	
	public String deletar(int suporteId) throws SQLException {
		String sql = "DELETE FROM t_suporte WHERE suporte_id = ?";
		try (PreparedStatement stmt = myConnection.prepareStatement(sql)) {
			stmt.setInt(1, suporteId);

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				return "Mensagem deletada com sucesso!";
			} else {
				return "Mensagem não encontrada.";
			}
		} catch (SQLException e) {
			System.err.println("Erro ao deletar mensagem: " + e.getMessage());
			throw e;
		}
	}
	
}
