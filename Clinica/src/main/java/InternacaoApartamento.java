public class InternacaoApartamento extends Internacao{

    public InternacaoApartamento(int qtdeDias) {
        super(qtdeDias);
    }

    @Override
    public float valorDiarias(float valorDiarias) {
        if (qtdeDias <= 3) {
            valorDiarias += 100.00f * qtdeDias; // Internação Básica
        } else if (qtdeDias <= 8) {
            valorDiarias += 90.00f * qtdeDias;  // Internação Média
        } else {
            valorDiarias += 80.00f * qtdeDias;  // Internação Grave
        }
        return valorDiarias;
    }

    @Override
    public String imprimir() {
        return qtdeDias + " diária" + (qtdeDias > 1 ? "s" : "") + " em " + "apartamento";
    }

    @Override
    public String getTipoLeito() {
        return "APARTAMENTO";
    }
}
