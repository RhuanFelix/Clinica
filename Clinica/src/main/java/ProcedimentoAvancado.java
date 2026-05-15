public class ProcedimentoAvancado extends Procedimento{

    @Override
    float getValor() {
        return 500;
    }

    @Override
    public String getDescricaoRelatorio(int quantidade) {
        // Se quantidade > 1, usa "procedimentos comuns", senão "procedimento comum"
        String termo = (quantidade > 1) ? "procedimentos avançados" : "procedimento avançado";
        return "\n\t\t\t\t\t" + quantidade + " " + termo;
    }

    @Override
    String getTipoProcedimento() {
        return "AVANCADO";
    }

}
