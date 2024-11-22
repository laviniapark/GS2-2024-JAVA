package br.com.fiap.beans;

public class User {

	private int userId;
	private String email;
	private String senha;
	private int clienteId;

	public User() {
		super();
	}

	public User(int userId, String email, String senha, int clienteId) {
		super();
		this.userId = userId;
		this.email = email;
		this.senha = senha;
		this.clienteId = clienteId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public int getClienteId() {
		return clienteId;
	}

	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}

}
