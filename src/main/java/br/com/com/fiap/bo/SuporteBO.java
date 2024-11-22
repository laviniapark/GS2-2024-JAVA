package br.com.com.fiap.bo;

import java.sql.Connection;

import br.com.fiap.beans.Suporte;
import br.com.fiap.dao.SuporteDAO;

public class SuporteBO {

	private SuporteDAO supDao;

	public SuporteBO(Connection connection) {
		this.supDao = new SuporteDAO(connection);
	}

	public String inserirSuporte(Suporte sup) throws Exception {

		try {
			return supDao.inserir(sup);
		} catch (Exception e) {
			throw new Exception("Erro ao inserir mensagem: " + e.getMessage(), e);
		}
	}
}
