package br.com.fiap.main;

import java.sql.Connection;

import javax.swing.JOptionPane;

import br.com.com.fiap.bo.ClienteBO;
import br.com.fiap.conexoes.ConexaoFactory;

public class TesteDeletarCliente {

    public static void main(String[] args) {
        try (Connection connection = new ConexaoFactory().conexao()) {

            ClienteBO clienteBO = new ClienteBO(connection);

            int clienteId = Integer.parseInt(
                JOptionPane.showInputDialog("Digite o ID do cliente que deseja deletar:")
            );

                String resultado = clienteBO.deletarCliente(clienteId);
                JOptionPane.showMessageDialog(null, resultado);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
