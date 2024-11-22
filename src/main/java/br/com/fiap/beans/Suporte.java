package br.com.fiap.beans;

public class Suporte {

	private int suporteId;
	private String nome;
	private String email;
	private String mensagem;

	public Suporte() {
		super();
	}

	public Suporte(int suporteId, String nome, String email, String mensagem) {
		super();
		this.suporteId = suporteId;
		this.nome = nome;
		this.email = email;
		this.mensagem = mensagem;
	}

	public int getSuporteId() {
		return suporteId;
	}

	public void setSuporteId(int suporteId) {
		this.suporteId = suporteId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	@Override
	public String toString() {
		return "Suporte [suporteId=" + suporteId + ", nome=" + nome + ", email=" + email + ", mensagem=" + mensagem
				+ "]";
	}

}
