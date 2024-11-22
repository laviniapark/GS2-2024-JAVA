package br.com.com.fiap.bo;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.fiap.beans.User;
import br.com.fiap.dao.ClienteDAO;
import br.com.fiap.dao.UserDAO;

public class UserBO {

	private UserDAO userDao;
	private ClienteDAO clienteDao;

	public UserBO(Connection connection) throws ClassNotFoundException, SQLException {
		this.userDao = new UserDAO(connection);
		this.clienteDao = new ClienteDAO(connection);
	}

	public String criarUsuario(User user) throws Exception {
		validarUsuario(user);

		try {
			if (!clienteDao.existeCliente(user.getClienteId())) {
				throw new IllegalArgumentException("O cliente associado nao existe.");
			}

			if (userDao.existeEmail(user.getEmail())) {
				throw new IllegalArgumentException("O e-mail fornecido já está em uso.");
			}

			return userDao.inserir(user);
		} catch (SQLException e) {
			throw new Exception("Erro ao cadastrar usuário: " + e.getMessage(), e);
		}
	}

	private void validarUsuario(User user) {
		if (user.getEmail() == null || user.getEmail().isEmpty()
				|| !user.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
			throw new IllegalArgumentException("O e-mail fornecido é inválido.");
		}

		if (user.getSenha() == null || user.getSenha().length() < 8) {
			throw new IllegalArgumentException("A senha deve ter pelo menos 8 caracteres.");
		}
	}

	public String atualizarUsuario(User user) throws Exception {
		if (user.getUserId() <= 0 || user.getEmail() == null || user.getEmail().isEmpty()) {
			throw new IllegalArgumentException("Usuario invalido ou email nao fornecido.");
		}

		if (user.getSenha() == null || user.getSenha().isEmpty()) {
			throw new IllegalArgumentException("A senha nao pode ser vazia.");
		}

		try {

			int clienteIdAtual = userDao.selecionarClienteIdPorUsuario(user.getUserId());
			if (clienteIdAtual != user.getClienteId()) {
				throw new IllegalArgumentException("O cliente_id não pode ser alterado.");
			}

			return userDao.atualizar(user);
		} catch (SQLException e) {
			throw new Exception("Erro ao atualizar usuário: " + e.getMessage(), e);
		}
	}

	public String deletarUsuario(int userId) throws Exception {
		if (userId <= 0) {
			throw new IllegalArgumentException("ID do usuario invalido.");
		}

		try {

			if (!userDao.existeUsuario(userId)) {
				throw new IllegalArgumentException("Usuario nao encontrado.");
			}

			return userDao.deletar(userId);
		} catch (SQLException e) {
			throw new Exception("Erro ao deletar usuario: " + e.getMessage(), e);
		}
	}

}
