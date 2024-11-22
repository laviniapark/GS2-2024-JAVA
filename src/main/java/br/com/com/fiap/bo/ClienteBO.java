package br.com.com.fiap.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.fiap.beans.Cliente;
import br.com.fiap.dao.ClienteDAO;
import br.com.fiap.service.ViaCepService;

public class ClienteBO {

	private Connection myConnection;
	private ClienteDAO clienteDao;
	private ViaCepService viaCepService;

	public ClienteBO(Connection connection) throws SQLException, ClassNotFoundException {
		if (connection == null) {
			throw new IllegalArgumentException("A conexão não pode ser nula.");
		}
		this.myConnection = connection;
		this.clienteDao = new ClienteDAO(connection);
		this.viaCepService = new ViaCepService();
	}

	public Cliente inserirCliente(String nome, String cep) throws Exception {
		try {

			validarCliente(nome, cep);

			myConnection.setAutoCommit(false);
			System.out.println("CEP recebido no inserirBo: " + cep);

			int enderecoId = viaCepService.buscarEnderecoId(myConnection, cep);

			if (enderecoId == 0) {
				viaCepService.inserirEnderecoBanco(myConnection, cep);

				enderecoId = viaCepService.buscarEnderecoId(myConnection, cep);

				if (enderecoId == 0) {
					throw new Exception("Erro ao processar o endereço: ID não encontrado mesmo após inserção.");
				}
			}

			Cliente cliente = new Cliente();
			cliente.setNome(nome);
			cliente.setEnderecoId(enderecoId);
			cliente.setCep(cep);

			System.out.println("Cliente a ser inserido: Nome = " + cliente.getNome() + ", EnderecoId = "
					+ cliente.getEnderecoId() + ", CEP = " + cliente.getCep());
			clienteDao.inserir(cliente);

			System.out.println("Preparando para fazer commit...");
			myConnection.commit();
			System.out.println("Commit realizado com sucesso!");
			return cliente;
		} catch (Exception e) {
			if (myConnection != null) {
				try {
					myConnection.rollback();
				} catch (SQLException rollbackEx) {
					System.err.println("Erro ao reverter a transação: " + rollbackEx.getMessage());
				}
			}
			throw new Exception("Erro ao processar cliente: " + e.getMessage(), e);
		}
	}

	private void validarCliente(String nome, String cep) {
		System.out.println("Valores recebidos - Nome: " + nome + ", CEP: " + cep);
		if (nome == null || nome.isEmpty()) {
			throw new IllegalArgumentException("Nome do cliente é obrigatório.");
		}
		if (cep == null || cep.isEmpty() || !cep.matches("\\d{5}-\\d{3}")) {
			throw new IllegalArgumentException("CEP inválido. Deve estar no formato 12345-678.");
		}
	}

	public ArrayList<Cliente> selecionarTodosClientes() throws ClassNotFoundException, SQLException {
		try {
			clienteDao = new ClienteDAO(myConnection);

			return (ArrayList<Cliente>) clienteDao.selecionarTodos();
		} catch (Exception e) {
			System.out.println("Erro ao selecionar todos os clientes: " + e.getMessage());
			throw e;
		}
	}

	public Cliente selecionarCliente(int clienteId) throws SQLException {
		try {
			return clienteDao.selecionar(clienteId);
		} catch (SQLException e) {
			System.out.println("Erro ao selecionar cliente: " + e.getMessage());
			throw e;
		}
	}

	public String atualizarCliente(Cliente cliente) throws Exception {
	    try {
	        validarCliente(cliente.getNome(), cliente.getCep());

	        if (myConnection == null || myConnection.isClosed()) {
	            throw new SQLException("Conexão com o banco está fechada ou inválida.");
	        }

	        myConnection.setAutoCommit(false);

	        ViaCepService viaCepService = new ViaCepService();
	        int enderecoId = viaCepService.inserirOuBuscarEndereco(myConnection, cliente.getCep());

	        cliente.setEnderecoId(enderecoId);

	        String resultado = clienteDao.atualizar(cliente, myConnection);

	        myConnection.commit();
	        return resultado;
	    } catch (Exception e) {
	        if (myConnection != null && !myConnection.isClosed() && !myConnection.getAutoCommit()) {
	            try {
	                myConnection.rollback();
	                System.err.println("Transação revertida com sucesso.");
	            } catch (SQLException rollbackEx) {
	                System.err.println("Erro ao reverter a transação: " + rollbackEx.getMessage());
	            }
	        }
	        throw new Exception("Erro ao atualizar cliente: " + e.getMessage(), e);
	    }
	}

	public String deletarCliente(int clienteId) throws SQLException {
        try {
            return clienteDao.deletar(clienteId);
        } catch (SQLException e) {
            System.out.println("Erro ao deletar cliente: " + e.getMessage());
            throw e;
        }
    }

}
