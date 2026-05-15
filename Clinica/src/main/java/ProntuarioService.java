import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;

public class ProntuarioService {
    private final ProntuarioRepository repository;

    public ProntuarioService(ProntuarioRepository repository) {
        this.repository = repository;
    }

    public String imprimaConta(Prontuario prontuario) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        Internacao internacao = prontuario.getInternacao();
        Set<Procedimento> procedimentos = prontuario.getProcedimentos();
        String nomePaciente = prontuario.getNomePaciente();

        String conta = "----------------------------------------------------------------------------------------------";

        float valorDiarias = 0.0f;
        if (internacao != null) {
            valorDiarias = internacao.valorDiarias(valorDiarias);
        }

        Map<String, Integer> quantidades = new HashMap<>();
        Map<String, Procedimento> exemplares = new HashMap<>();

        float valorTotalProcedimentos = 0.00f;

        for (Procedimento p : procedimentos) {
            valorTotalProcedimentos += p.getValor();

            String tipo = p.getTipoProcedimento();

            quantidades.put(tipo, quantidades.getOrDefault(tipo, 0) + 1);

            exemplares.put(tipo, p);
        }

        List<String> tiposOrdenados = new ArrayList<>(quantidades.keySet());

        tiposOrdenados.sort((t1, t2) -> Float.compare(exemplares.get(t1).getValor(), exemplares.get(t2).getValor()));

        conta += "\nA conta do(a) paciente " + nomePaciente + " tem valor total de __ " +
                formatter.format(valorDiarias + valorTotalProcedimentos) + " __";

        conta += "\n\nConforme os detalhes abaixo:";

        if (internacao != null) {
            conta += "\n\nValor Total Diárias:\t\t\t" + formatter.format(valorDiarias);
            conta += "\n\t\t\t\t\t" + internacao.imprimir();
        }

        if (!procedimentos.isEmpty()) {
            conta += "\n\nValor Total Procedimentos:\t\t" + formatter.format(valorTotalProcedimentos);

            for (String tipo : tiposOrdenados) {
                Procedimento p = exemplares.get(tipo);
                int qtd = quantidades.get(tipo);
                conta += p.getDescricaoRelatorio(qtd);
            }
        }

        conta += "\n\nVolte sempre, a casa é sua!";
        conta += "\n----------------------------------------------------------------------------------------------";

        return conta;
    }

    public Prontuario carregueProntuario(String csv) throws IOException {
        return this.repository.carregueProntuario(csv);
    }

    public String salveProntuario(Prontuario prontuario) throws IOException {
        return this.repository.salveProntuario(prontuario);
    }
}
