package br.com.com.fiap.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.fiap.beans.ClienteEletro;
import br.com.fiap.dao.ClienteDAO;
import br.com.fiap.dao.ClienteEletroDAO;
import br.com.fiap.dao.EletrodomesticoDAO;

public class ClienteEletroBO {

	private Connection myConnection;
	private ClienteEletroDAO clienteEletroDao;
	private ClienteDAO clienteDao;
	private EletrodomesticoDAO eletroDao;
	
	public ClienteEletroBO(Connection connection) throws ClassNotFoundException, SQLException {
		this.myConnection = connection;
		this.clienteEletroDao = new ClienteEletroDAO(connection);
		this.clienteDao = new ClienteDAO(connection);
		this.eletroDao = new EletrodomesticoDAO(connection);
	}
	
	public String associarClienteEletrodomestico(int clienteId, int eletronicoId, double horasUsado) throws Exception {
	    if (clienteId <= 0 || eletronicoId <= 0 || horasUsado <= 0) {
	        throw new IllegalArgumentException("Os IDs e horas usadas devem ser positivos.");
	    }

	    if (!clienteDao.existeCliente(clienteId)) {
	        throw new Exception("Cliente com ID " + clienteId + " não encontrado.");
	    }

	    if (!eletroDao.existeEletroId(eletronicoId)) {
	        throw new Exception("Eletrodoméstico com ID " + eletronicoId + " não encontrado.");
	    }

	    return clienteEletroDao.inserir(clienteId, eletronicoId, horasUsado);
	}

	public ArrayList<ClienteEletro> selecionarTodosClienteEletros() throws ClassNotFoundException, SQLException {
		try {
			clienteEletroDao = new ClienteEletroDAO(myConnection);

			return (ArrayList<ClienteEletro>) clienteEletroDao.selecionarTodos();
		} catch (Exception e) {
			System.out.println("Erro ao selecionar todos as associaçoes: " + e.getMessage());
			throw e;
		}
	}
	
	public String deletarClienteEletro(int clienteEletroId) throws SQLException {
        try {
            return clienteEletroDao.deletar(clienteEletroId);
        } catch (SQLException e) {
            System.out.println("Erro ao deletar associaçao: " + e.getMessage());
            throw e;
        }
    }
	
}
