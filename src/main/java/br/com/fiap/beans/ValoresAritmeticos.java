package br.com.fiap.beans;

public abstract class ValoresAritmeticos {

	private double valor;
	private double resultado;

	public ValoresAritmeticos() {

	}

	public ValoresAritmeticos(double valor) {
		this.valor = valor;
		this.resultado = 0;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getResultado() {
		return resultado;
	}

	public void setResultado(double resultado) {
		this.resultado = resultado;
	}

	public abstract void calcular();

	public double somar(double outroValor) {
		return this.valor + outroValor;
	}

	public double subtrair(double outroValor) {
		return this.valor - outroValor;
	}

	public double multiplicar(double outroValor) {
		return this.valor * outroValor;
	}

	public double dividir(double outroValor) {
		if (outroValor == 0) {
			throw new IllegalArgumentException("Divisão por zero não é permitida.");
		}
		return this.valor / outroValor;
	}

	@Override
	public String toString() {
		return String.format("ValoresAritmeticos [valor=%.2f, resultado=%.6f]", valor, resultado);
	}
}
