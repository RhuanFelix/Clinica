public class InternacaoEnfermaria extends Internacao{
    public InternacaoEnfermaria(int qtdeDias) {
        super(qtdeDias);
    }

    @Override
    public float valorDiarias(float valorDiarias) {
        if (qtdeDias <= 3) {
            valorDiarias += 40.00f * qtdeDias; // Internação Básica
        } else if (qtdeDias <= 8) {
            valorDiarias += 35.00f * qtdeDias; // Internação Média
        } else {
            valorDiarias += 30.00f * qtdeDias; // Internação Grave
        }
        return valorDiarias;
    }

    @Override
    public String imprimir() {
        return qtdeDias + " diária" + (qtdeDias > 1 ? "s" : "") + " em " + "enfermaria";
    }

    @Override
    public String getTipoLeito() {
        return "ENFERMARIA";
    }


}
