package br.com.fiap.resource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.com.fiap.bo.ClienteBO;
import br.com.fiap.beans.Cliente;
import br.com.fiap.conexoes.ConexaoFactory;

@Path("/clientes")
public class ClienteResource {

	private Connection myConnection;
	private ClienteBO clienteBO;

	public ClienteResource() {
		try {
			this.myConnection = new ConexaoFactory().conexao();
			this.clienteBO = new ClienteBO(myConnection);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao inicializar ClienteResource: " + e.getMessage());
		}
	}

	@POST
	@Path("/cadastrar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cadastrarCliente(Cliente cliente) throws SQLException {
		try {
			System.out.println("CEP recebido no Resource: " + cliente.getCep());
			System.out.println("Nome recebido no Resource: " + cliente.getNome());

			Cliente clienteCriado = clienteBO.inserirCliente(cliente.getNome(), cliente.getCep());
			myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
			return Response.status(Response.Status.CREATED).entity(clienteCriado).build();
		} catch (IllegalArgumentException e) {
			myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
			return Response.status(Response.Status.BAD_REQUEST).entity("Erro de validação: " + e.getMessage()).build();
		} catch (Exception e) {
			myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao cadastrar cliente: " + e.getMessage()).build();
		}
	}

	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarTodosClientes() throws SQLException {
		try (Connection connection = new ConexaoFactory().conexao()) {

			ClienteBO clienteBO = new ClienteBO(connection);

			ArrayList<Cliente> clientes = clienteBO.selecionarTodosClientes();

			if (!clientes.isEmpty()) {
				myConnection.close();
				System.out.println("Conexao finalizada com sucesso!");
				return Response.ok(clientes).build();
			} else {
				myConnection.close();
				System.out.println("Conexao finalizada com sucesso!");
				return Response.status(Response.Status.NO_CONTENT).entity("Nenhum cliente encontrado").build();
			}
		} catch (Exception e) {
			myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao listar clientes: " + e.getMessage()).build();
		}
	}

	@GET
	@Path("/buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarCliente(@QueryParam("id") int clienteId) throws SQLException {
		try {

			Cliente cliente = clienteBO.selecionarCliente(clienteId);

			if (cliente != null) {
				myConnection.close();
				System.out.println("Conexao finalizada com sucesso!");
				return Response.ok(cliente).build();
			} else {
				myConnection.close();
				System.out.println("Conexao finalizada com sucesso!");
				return Response.status(Response.Status.NOT_FOUND).entity("Cliente não encontrado").build();
			}
		} catch (Exception e) {
			myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao buscar cliente: " + e.getMessage()).build();
		}
	}

	@PUT
	@Path("/atualizar/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizarCliente(@PathParam("id") int clienteId, Cliente clienteAtualizado) throws SQLException {
		try (Connection connection = new ConexaoFactory().conexao()) {
			System.out.println("Cliente recebido no Resource:");
			System.out.println("ID: " + clienteAtualizado.getClienteId());
			System.out.println("Nome: " + clienteAtualizado.getNome());
			System.out.println("CEP: " + clienteAtualizado.getCep());
			if (clienteAtualizado.getClienteId() != clienteId) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("ID do cliente no PathParam não corresponde ao ID no corpo da requisição.").build();
			}

			ClienteBO clienteBO = new ClienteBO(connection);

			String resultado = clienteBO.atualizarCliente(clienteAtualizado);
			myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
			return Response.ok(resultado).build();
		} catch (IllegalArgumentException e) {
			myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		} catch (Exception e) {
			myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao atualizar cliente: " + e.getMessage()).build();
		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletarCliente(@PathParam("id") int clienteId) {
		try (Connection connection = new ConexaoFactory().conexao()) {

			String resultado = clienteBO.deletarCliente(clienteId);

			return Response.ok(resultado).build();
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao deletar cliente: " + e.getMessage()).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro inesperado: " + e.getMessage())
					.build();
		}
	}

}
