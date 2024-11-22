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

import br.com.com.fiap.bo.EletrodomesticoBO;
import br.com.fiap.beans.Eletrodomestico;
import br.com.fiap.conexoes.ConexaoFactory;

@Path("/eletrodomesticos")
public class EletrodomesticoResource {

	private Connection myConnection;
	private EletrodomesticoBO eletroBo;
	
	public EletrodomesticoResource() throws ClassNotFoundException, SQLException {
		this.myConnection = new ConexaoFactory().conexao();
		this.eletroBo = new EletrodomesticoBO(myConnection);
	}
	
	@POST
	@Path("/cadastrar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cadastrarEletrodomestico(Eletrodomestico eletrodomestico) {
		try {

			String eletroCriado = eletroBo.inserirEletrodomestico(eletrodomestico);
			myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
			return Response.status(Response.Status.CREATED).entity(eletroCriado).build();
		} catch (IllegalArgumentException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Erro de validação: " + e.getMessage()).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao cadastrar eletrodomestico: " + e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarTodosEletrodomesticos() throws SQLException {
		try (Connection connection = new ConexaoFactory().conexao()) {

			EletrodomesticoBO eletroBO = new EletrodomesticoBO(connection);

			ArrayList<Eletrodomestico> eletros = eletroBO.selecionarTodosEletrodomesticos();

			if (!eletros.isEmpty()) {
				myConnection.close();
				System.out.println("Conexao finalizada com sucesso!");
				return Response.ok(eletros).build();
			} else {
				myConnection.close();
				System.out.println("Conexao finalizada com sucesso!");
				return Response.status(Response.Status.NO_CONTENT).entity("Nenhum eletrodomestico encontrado").build();
			}
		} catch (Exception e) {
			myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao listar eletrodomesticos: " + e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarEletrodomestico(@QueryParam("id") int eletronicoId) throws SQLException {
		try {

			Eletrodomestico eletro = eletroBo.selecionarEletrodomestico(eletronicoId);

			if (eletro != null) {
				myConnection.close();
				System.out.println("Conexao fechada com sucesso!");
				return Response.ok(eletro).build();
			} else {
				myConnection.close();
				System.out.println("Conexao fechada com sucesso!");
				return Response.status(Response.Status.NOT_FOUND).entity("Eletrodomestico não encontrado").build();
			}
		} catch (Exception e) {
			myConnection.close();
			System.out.println("Conexao fechada com sucesso!");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao buscar eletrodomestico: " + e.getMessage()).build();
		}
	}
	
	@PUT
	@Path("/atualizar/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizarEletrodomestico(@PathParam("id") int eletronicoId, Eletrodomestico eletroAtualizado) throws SQLException {
		try {
			
			if (eletroAtualizado.getEletronicoId() != eletronicoId) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("ID do eletrodomestico no PathParam não corresponde ao ID no corpo da requisição.").build();
			}

			EletrodomesticoBO eletroBO = new EletrodomesticoBO(myConnection);

			String resultado = eletroBO.atualizarEletrodomestico(eletroAtualizado);

			myConnection.close();
			System.out.println("Conexao fechada com sucesso!");
			return Response.ok(resultado).build();
		} catch (IllegalArgumentException e) {
			myConnection.close();
			System.out.println("Conexao fechada com sucesso!");
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		} catch (Exception e) {
			myConnection.close();
			System.out.println("Conexao fechada com sucesso!");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao atualizar eletrodomestico: " + e.getMessage()).build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletarEletrodomestico(@PathParam("id") int eletronicoId) throws SQLException {
		try {

			String resultado = eletroBo.deletarEletrodomestico(eletronicoId);

			myConnection.close();
			System.out.println("Conexao fechada com sucesso!");
			return Response.ok(resultado).build();
		} catch (SQLException e) {
			myConnection.close();
			System.out.println("Conexao fechada com sucesso!");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao deletar eletrodomestico: " + e.getMessage()).build();
		} catch (Exception e) {
			myConnection.close();
			System.out.println("Conexao fechada com sucesso!");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro inesperado: " + e.getMessage())
					.build();
		}
	}
	
}
