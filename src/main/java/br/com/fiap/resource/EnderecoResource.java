package br.com.fiap.resource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import br.com.com.fiap.bo.EnderecoBO;
import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.model.Endereco;
import br.com.fiap.service.ViaCepService;

@Path("/enderecos")
public class EnderecoResource {

	private ViaCepService viaCepService;
	private Connection myConnection;
	private EnderecoBO enderecoBO;

	public EnderecoResource() {
		this.viaCepService = new ViaCepService();
		this.enderecoBO = new EnderecoBO(myConnection, viaCepService);
	}

	@POST
	@Path("/cadastrar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cadastrarEndereco(Endereco endereco, @Context UriInfo uriInfo) {
		try (Connection connection = new ConexaoFactory().conexao()) {

			ViaCepService viaCepService = new ViaCepService();
			EnderecoBO enderecoBO = new EnderecoBO(connection, viaCepService);

			int enderecoId = enderecoBO.cadastrarEndereco(endereco);

			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			builder.path(Integer.toString(enderecoId));

			return Response.created(builder.build()).build();
		} catch (IllegalArgumentException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao acessar o banco de dados: " + e.getMessage()).build();
		} catch (IOException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao acessar a API ViaCep: " + e.getMessage()).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro inesperado: " + e.getMessage())
					.build();
		}
	}

	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarTodosEnderecos() {
		try (Connection connection = new ConexaoFactory().conexao()) {

			ViaCepService viaCepService = new ViaCepService();
			EnderecoBO enderecoBO = new EnderecoBO(connection, viaCepService);

			ArrayList<Endereco> enderecos = enderecoBO.selecionarTodosEnderecos();

			if (!enderecos.isEmpty()) {
				return Response.ok(enderecos).build();
			} else {
				return Response.status(Response.Status.NO_CONTENT).entity("Nenhum endereço encontrado").build();
			}
		} catch (Exception e) {

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao listar endereços: " + e.getMessage()).build();
		}
	}

	@GET
	@Path("/buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarEndereco(@QueryParam("cep") String cep) {
		try {
			Endereco endereco = enderecoBO.selecionarEndereco(cep);
			if (endereco != null) {
				return Response.ok(endereco).build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity("Endereço não encontrado").build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao buscar endereço: " + e.getMessage()).build();
		}
	}

}
