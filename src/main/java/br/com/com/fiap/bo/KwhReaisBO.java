package br.com.com.fiap.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.fiap.beans.KwhReais;
import br.com.fiap.dao.KwhReaisDAO;

public class KwhReaisBO {

	private KwhReaisDAO KRDao;
	
	public KwhReaisBO(Connection connection) {
		this.KRDao = new KwhReaisDAO(connection);
	}
	
	public void inserirKwhReais(double valor, double tarifaKwh) throws SQLException {
        if (valor <= 0) {
            throw new IllegalArgumentException("O consumo (kWh) deve ser maior que zero.");
        }

        if (tarifaKwh < 0) {
            throw new IllegalArgumentException("A tarifa adicional (R$/kWh) não pode ser negativa.");
        }

        KwhReais kwhReais = new KwhReais(valor, tarifaKwh);
        kwhReais.calcular();

        KRDao.inserir(kwhReais);
    }
	
	public ArrayList<KwhReais> listarTodos() throws SQLException {
        return KRDao.selecionarTodos();
    }
	
}
