package br.com.com.fiap.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.fiap.beans.KwhHora;
import br.com.fiap.dao.KwhHoraDAO;

public class KwhHoraBO {

	private KwhHoraDAO kwHDao;
	
	public KwhHoraBO(Connection connection) {
		this.kwHDao = new KwhHoraDAO(connection);
	}
	
	public void inserirKwhHora(double valor) throws SQLException {

        if (valor <= 0) {
            throw new IllegalArgumentException("O valor de potencia em watts deve ser maior que zero.");
        }

        KwhHora kwH = new KwhHora(valor);
        kwH.calcular();

        kwHDao.inserir(kwH);
    }
	
	public ArrayList<KwhHora> listarTodosKwhHora() throws SQLException {
        return kwHDao.selecionarTodos();
    }
	
}
