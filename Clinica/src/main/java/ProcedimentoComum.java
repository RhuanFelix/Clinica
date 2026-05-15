public class ProcedimentoComum extends Procedimento{

    @Override
    float getValor() {
        return 150;
    }

    @Override
    public String getDescricaoRelatorio(int quantidade) {
        String termo = (quantidade > 1) ? "procedimentos comuns" : "procedimento comum";
        return "\n\t\t\t\t\t" + quantidade + " " + termo;
    }

    @Override
    String getTipoProcedimento() {
        return "COMUM";
    }

}
