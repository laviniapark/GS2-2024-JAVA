package br.com.fiap.main;

import java.sql.Connection;

import javax.swing.JOptionPane;

import br.com.com.fiap.bo.ClienteBO;
import br.com.fiap.beans.Cliente;
import br.com.fiap.conexoes.ConexaoFactory;

public class TesteAtualizarCliente {

    public static void main(String[] args) {
        try (Connection connection = new ConexaoFactory().conexao()) {
         
            ClienteBO clienteBO = new ClienteBO(connection);

            int clienteId = Integer.parseInt(
                JOptionPane.showInputDialog("Digite o ID do cliente a ser atualizado:")
            );
            String nome = JOptionPane.showInputDialog("Digite o novo nome do cliente:");
            String cep = JOptionPane.showInputDialog("Digite o novo CEP do cliente (formato: 12345-678):");

            Cliente cliente = new Cliente();
            cliente.setClienteId(clienteId);
            cliente.setNome(nome);
            cliente.setCep(cep);

            String resultado = clienteBO.atualizarCliente(cliente);

            JOptionPane.showMessageDialog(null, resultado);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
