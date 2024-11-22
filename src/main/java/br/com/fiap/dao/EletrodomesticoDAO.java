package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.fiap.beans.Eletrodomestico;


public class EletrodomesticoDAO {

	private Connection myConnection;

	public EletrodomesticoDAO(Connection connection) throws ClassNotFoundException, SQLException {
		super();
		this.myConnection = connection;
	}
	
	public String inserir(Eletrodomestico eletrodomestico) throws SQLException {
		String sqlEletro = "INSERT INTO t_eletrodomesticos (categoria, marca, modelo, consumo_watts) VALUES (?, ?, ?, ?)";
		try (PreparedStatement stmtEletro = myConnection.prepareStatement(sqlEletro)) {
			stmtEletro.setString(1, eletrodomestico.getCategoria());
			stmtEletro.setString(2, eletrodomestico.getMarca());
			stmtEletro.setString(3, eletrodomestico.getModelo());
			stmtEletro.setDouble(4, eletrodomestico.getConsumoWatts());
			stmtEletro.executeUpdate();
			return "Eletrodomestico inserido com sucesso!";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Erro ao inserir eletrodomestico: " + e.getMessage();
		}
	}
	
	public Eletrodomestico selecionar(int eletronicoId) throws SQLException {
		String sqlEletro = "SELECT * FROM t_eletrodomesticos WHERE eletronico_id = ?";
		try (PreparedStatement stmt = myConnection.prepareStatement(sqlEletro)) {
			stmt.setInt(1, eletronicoId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Eletrodomestico(
							rs.getInt("eletronico_id"),
							rs.getString("categoria"),
							rs.getString("marca"),
							rs.getString("modelo"),
							rs.getInt("consumo_watts")
							);
				}
			}
		} catch (SQLException e) {
			System.out.println("Erro ao selecionar eletrodomestico: " + e.getMessage());
			throw e;
		}
		return null;
	}
	
	public ArrayList<Eletrodomestico> selecionarTodos() throws SQLException {
		ArrayList<Eletrodomestico> listaEletros = new ArrayList<>();
		String sqlEletro = "SELECT * FROM t_eletrodomesticos";

		try (PreparedStatement stmt = myConnection.prepareStatement(sqlEletro); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Eletrodomestico eletro = new Eletrodomestico(
						rs.getInt("eletronico_id"),
						rs.getString("categoria"),
						rs.getString("marca"),
						rs.getString("modelo"),
						rs.getInt("consumo_watts"));

				listaEletros.add(eletro);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao selecionar todos os eletrodomesticos: " + e.getMessage());
			throw e;
		}

		return listaEletros;
	}
	
	public String atualizar(Eletrodomestico eletro, Connection connection) throws SQLException {
	    String sqlEletro = "UPDATE t_eletrodomesticos SET categoria = ?, marca = ?, modelo = ?, consumo_watts = ? WHERE eletronico_id = ?";

	    try (PreparedStatement stmt = connection.prepareStatement(sqlEletro)) {

	        stmt.setString(1, eletro.getCategoria());
	        stmt.setString(2, eletro.getMarca());
	        stmt.setString(3, eletro.getModelo());
	        stmt.setDouble(4, eletro.getConsumoWatts());
	        stmt.setInt(5, eletro.getEletronicoId());

	        int rowsAffected = stmt.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("Eletrodomestico atualizado no banco com sucesso.");
	            return "Eletrodomestico atualizado com sucesso!";
	        } else {
	            System.err.println("Eletrodomestico não encontrado para atualização. ID: " + eletro.getEletronicoId());
	            return "Eletrodomestico não encontrado para atualização.";
	        }
	    } catch (SQLException e) {
	        System.err.println("Erro ao atualizar eletrodomestico no DAO: " + e.getMessage());
	        throw new SQLException("Erro ao atualizar eletrodomestico no banco: " + e.getMessage(), e);
	    }
	}
	
	public String deletar(int eletronicoId) throws SQLException {
		String sql = "DELETE FROM t_eletrodomesticos WHERE eletronico_id = ?";
		try (PreparedStatement stmt = myConnection.prepareStatement(sql)) {
			stmt.setInt(1, eletronicoId);

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				return "Eletrodomestico deletado com sucesso!";
			} else {
				return "Eletrodomestico não encontrado.";
			}
		} catch (SQLException e) {
			System.err.println("Erro ao deletar eletrodomestico: " + e.getMessage());
			throw e;
		}
	}
	
	public boolean existeEletrodomestico(Eletrodomestico eletrodomestico) throws SQLException {
	    String sql = "SELECT 1 FROM t_eletrodomesticos WHERE categoria = ? AND marca = ? AND modelo = ?";
	    try (PreparedStatement stmt = myConnection.prepareStatement(sql)) {
	        stmt.setString(1, eletrodomestico.getCategoria());
	        stmt.setString(2, eletrodomestico.getMarca());
	        stmt.setString(3, eletrodomestico.getModelo());

	        try (ResultSet rs = stmt.executeQuery()) {
	            return rs.next();
	        }
	    }
	}
	
	public boolean existeEletroId(int eletronicoId) throws SQLException {
	    String sql = "SELECT 1 FROM t_eletrodomesticos WHERE eletronico_id = ?";
	    try (PreparedStatement stmt = myConnection.prepareStatement(sql)) {
	        stmt.setInt(1, eletronicoId);

	        try (ResultSet rs = stmt.executeQuery()) {
	            return rs.next();
	        }
	    } catch (SQLException e) {
	        System.err.println("Erro ao verificar eletrodoméstico: " + e.getMessage());
	        throw e;
	    }
	}

}
