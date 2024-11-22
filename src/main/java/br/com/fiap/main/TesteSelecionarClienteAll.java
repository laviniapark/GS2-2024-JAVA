package br.com.fiap.main;

import java.sql.Connection;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.fiap.beans.Cliente;
import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.dao.ClienteDAO;

public class TesteSelecionarClienteAll {

	public static void main(String[] args) {

		try (Connection connection = new ConexaoFactory().conexao()) {
			ClienteDAO clienteDAO = new ClienteDAO(connection);

			List<Cliente> listaClientes = clienteDAO.selecionarTodos();

			if (!listaClientes.isEmpty()) {
				StringBuilder mensagem = new StringBuilder("Clientes encontrados:\n");
				for (Cliente cliente : listaClientes) {
					mensagem.append(String.format("ID: %d | Nome: %s | Endereço ID: %d | CEP: %s\n", cliente.getClienteId(),
							cliente.getNome(), cliente.getEnderecoId(), cliente.getCep()));
				}
				JOptionPane.showMessageDialog(null, mensagem.toString());
			} else {
				JOptionPane.showMessageDialog(null, "Nenhum cliente encontrado!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao buscar clientes: " + e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}

	}

}
