public class ProcedimentoBasico extends Procedimento {

    @Override
    float getValor() {
        return 50;
    }

    @Override
    public String getDescricaoRelatorio(int quantidade) {
        String termo = (quantidade > 1) ? "procedimentos básicos" : "procedimento básico";
        return "\n\t\t\t\t\t" + quantidade + " " + termo;
    }

    @Override
    String getTipoProcedimento() {
        return "BASICO";
    }

}
