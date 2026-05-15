public abstract class Internacao {

	protected int qtdeDias;

	public Internacao(int qtdeDias) {
		this.qtdeDias = qtdeDias;
	}

	public abstract float valorDiarias(float valorDiarias);
	public abstract String imprimir();
	public abstract String getTipoLeito();

	int getQtdeDias() {
		return this.qtdeDias;
	}

}
