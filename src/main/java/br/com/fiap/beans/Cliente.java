package br.com.fiap.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Cliente {

	private int clienteId;
	private String nome;
	private int enderecoId;
	private String cep;

	public Cliente() {
		super();
	}

	public Cliente(int clienteId, String nome, int enderecoId, String cep) {
		super();
		this.clienteId = clienteId;
		this.nome = nome;
		this.enderecoId = enderecoId;
		this.cep = cep;
	}

	public int getClienteId() {
		return clienteId;
	}

	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getEnderecoId() {
		return enderecoId;
	}

	public void setEnderecoId(int enderecoId) {
		this.enderecoId = enderecoId;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

}
