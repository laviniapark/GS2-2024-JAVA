package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.fiap.beans.KwhReais;


public class KwhReaisDAO {

	private Connection myConnection;
	
	public KwhReaisDAO(Connection connection) {
		super();
		this.myConnection = connection;
	}
	
	public String inserir(KwhReais kwhReais) throws SQLException {
		String sql = "INSERT INTO t_kwh_reais (valor_potencia, resultado, tarifa_kwh) VALUES (?, ?, ?)";
		try (PreparedStatement stmt = myConnection.prepareStatement(sql)) {
			stmt.setDouble(1, kwhReais.getValor());
			stmt.setDouble(2, kwhReais.getResultado());
			stmt.setDouble(3, kwhReais.getTarifaKwh());
			stmt.executeUpdate();
			return "Calculo inserido com sucesso!";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Erro ao inserir calculo: " + e.getMessage();
		}
	}
	
	public ArrayList<KwhReais> selecionarTodos() throws SQLException {
        String sql = "SELECT * FROM t_kwh_reais";
        ArrayList<KwhReais> listaKwhReais = new ArrayList<>();
        try (PreparedStatement stmt = myConnection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                KwhReais kwhReais = new KwhReais(rs.getDouble("valor_potencia"), rs.getDouble("tarifa_kwh"));
                kwhReais.setResultado(rs.getDouble("resultado"));
                listaKwhReais.add(kwhReais);
            }
        }
        return listaKwhReais;
    }
	
}
