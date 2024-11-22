package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.fiap.beans.KwhHora;

public class KwhHoraDAO {

	private Connection myConnection;

	public KwhHoraDAO(Connection myConnection) {
		super();
		this.myConnection = myConnection;
	}
	
	public String inserir(KwhHora kwH) throws SQLException {
		String sql = "INSERT INTO t_kwh_hora (valor_potencia, resultado) VALUES (?, ?)";
		try (PreparedStatement stmt = myConnection.prepareStatement(sql)) {
			stmt.setDouble(1, kwH.getValor());
			stmt.setDouble(2, kwH.getResultado());
			stmt.executeUpdate();
			return "Conversao de W para Kwh/hora inserida com sucesso!";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Erro ao inserir conversao: " + e.getMessage();
		}
	}
	
	public ArrayList<KwhHora> selecionarTodos() throws SQLException {
        String sql = "SELECT * FROM t_kwh_hora";
        ArrayList<KwhHora> lista = new ArrayList<>();
        try (PreparedStatement stmt = myConnection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                KwhHora kwH = new KwhHora(rs.getDouble("valor_potencia"));
                kwH.setResultado(rs.getDouble("resultado"));
                lista.add(kwH);
            }
        }
        return lista;
    }
	
}
