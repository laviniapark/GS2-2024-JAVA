package br.com.fiap.beans;

public class KwhHora extends ValoresAritmeticos {

	public KwhHora() {
		super();
	}

	public KwhHora(double valor) {
		super(valor);
	}

	@Override
	public void calcular() {

		double kwh = getValor() / 1000;
		setResultado(kwh);
	}

	@Override
	public String toString() {
		return String.format("KwhHora [valor em Watts=%.2f, resultado em kWh=%.6f]", getValor(), getResultado());
	}
}
