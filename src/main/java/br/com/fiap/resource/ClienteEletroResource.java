package br.com.fiap.resource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.com.fiap.bo.ClienteEletroBO;
import br.com.fiap.beans.ClienteEletro;
import br.com.fiap.conexoes.ConexaoFactory;

@Path("/clientes-eletrodomesticos")
public class ClienteEletroResource {

	private Connection myConnection;
	private ClienteEletroBO clienteEletroBo;

	public ClienteEletroResource() throws ClassNotFoundException, SQLException {
		this.myConnection = new ConexaoFactory().conexao();
		this.clienteEletroBo = new ClienteEletroBO(myConnection);
	}

	@POST
	@Path("/cadastrar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response inserirClienteEletrodomestico(ClienteEletro clienteEletro)
			throws SQLException {
		try {
			
			int clienteId = clienteEletro.getClienteId();
	        int eletronicoId = clienteEletro.getEletronicoId();
	        double horasUsado = clienteEletro.getHorasUsado();
			
			String resultado = clienteEletroBo.associarClienteEletrodomestico(clienteId, eletronicoId, horasUsado);
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
					.entity("Erro ao cadastrar cliente e eletrodoméstico: " + e.getMessage()).build();
		}
	}

	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarTodosClientesEletros() throws SQLException {
		try {

			ArrayList<ClienteEletro> clienteEletros = clienteEletroBo.selecionarTodosClienteEletros();

			if (!clienteEletros.isEmpty()) {
				myConnection.close();
				System.out.println("Conexao finalizada com sucesso!");
				return Response.ok(clienteEletros).build();
			} else {
				myConnection.close();
				System.out.println("Conexao finalizada com sucesso!");
				return Response.status(Response.Status.NO_CONTENT).entity("Nenhuma associaçao encontrada").build();
			}
		} catch (Exception e) {
			myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao listar associaçoes: " + e.getMessage()).build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletarClienteEletro(@PathParam("id") int clienteEletroId) throws SQLException {
		try {

			String resultado = clienteEletroBo.deletarClienteEletro(clienteEletroId);

			myConnection.close();
			System.out.println("Conexao fechada com sucesso!");
			return Response.ok(resultado).build();
		} catch (SQLException e) {
			myConnection.close();
			System.out.println("Conexao fechada com sucesso!");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao deletar associaçao: " + e.getMessage()).build();
		} catch (Exception e) {
			myConnection.close();
			System.out.println("Conexao fechada com sucesso!");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro inesperado: " + e.getMessage())
					.build();
		}
	}
	
}
