package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.com.fiap.model.Endereco;

public class EnderecoDAO {

	private Connection myConnection;

	public EnderecoDAO(Connection connection) {
		this.myConnection = connection;
	}

	public String inserir(Endereco endereco) throws SQLException {
		String sql = "INSERT INTO t_enderecos (cep, logradouro, bairro, localidade, uf) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = myConnection.prepareStatement(sql)) {
			stmt.setString(1, endereco.getCep());
			stmt.setString(2, endereco.getLogradouro());
			stmt.setString(3, endereco.getBairro());
			stmt.setString(4, endereco.getLocalidade());
			stmt.setString(5, endereco.getUf());

			stmt.executeUpdate();

			return "Cadastrado com sucesso!";
		} catch (SQLException e) {
			System.err.println("Erro ao inserir endereco: " + e.getMessage());
			throw e;
		}
	}

	public Endereco selecionar(String cep) throws SQLException {
		String sql = "SELECT * FROM t_enderecos WHERE cep = ?";
		try (PreparedStatement stmt = myConnection.prepareStatement(sql)) {

			stmt.setString(1, cep);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Endereco(rs.getString("cep"), rs.getString("logradouro"), rs.getString("bairro"),
							rs.getString("localidade"), rs.getString("uf"));
				}
			}
		}
		return null;
	}

	public List<Endereco> selecionarTodos() throws SQLException {
		List<Endereco> listaEnderecos = new ArrayList<>();
		String sql = "SELECT * FROM t_enderecos ORDER BY uf ASC";

		try (PreparedStatement stmt = myConnection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Endereco endereco = new Endereco(rs.getString("cep"), rs.getString("logradouro"),
						rs.getString("bairro"), rs.getString("localidade"), rs.getString("uf"));
				listaEnderecos.add(endereco);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao selecionar todos os enderecos: " + e.getMessage());
			throw e;
		}

		return listaEnderecos;
	}
}
