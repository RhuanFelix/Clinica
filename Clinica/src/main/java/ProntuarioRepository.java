import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProntuarioRepository {
    private boolean b = false;
    private List<String> l = new ArrayList<>();

    public ProntuarioRepository() {
    }

    public Prontuario carregueProntuario(String arquivoCsv) throws IOException {

        Prontuario prontuario = new Prontuario(null);

        Path path = Paths.get(arquivoCsv);

        Stream<String> linhas = Files.lines(path);

        linhas.forEach((str) -> {
            if (!b) {
                b = true;
            } else {
                System.out.println(str);

                String[] dados = str.split(",");

                String nomePaciente = dados[0].trim();

                String tipoLeito = dados[1] != null && !dados[1].trim().isEmpty() ? dados[1].trim() : null;

                int qtdeDiasInternacao = dados[2] != null && !dados[2].trim().isEmpty() ? Integer.parseInt(dados[2].trim()) : -1;

                String tipoProcedimento = dados[3] != null && !dados[3].trim().isEmpty() ? dados[3].trim() : null;

                int qtdeProcedimentos = dados.length == 5 && dados[4] != null && !dados[4].trim().isEmpty() ? Integer.parseInt(dados[4].trim()) : -1;
                // Contabilizar as diárias
                prontuario.setNomePaciente(nomePaciente);

                if (tipoLeito != null && qtdeDiasInternacao > 0) {
                    if (tipoLeito.equals("APARTAMENTO")) {
                        prontuario.setInternacao(new InternacaoApartamento(qtdeDiasInternacao));
                    } else if (tipoLeito.equals("ENFERMARIA")) {
                        prontuario.setInternacao(new InternacaoEnfermaria(qtdeDiasInternacao));
                    }
                }

                if (tipoProcedimento != null && qtdeProcedimentos > 0) {
                    while (qtdeProcedimentos > 0) {
                        switch (tipoProcedimento) {
                            case "AVANCADO":
                                prontuario.addProcedimento(new ProcedimentoAvancado());
                                qtdeProcedimentos--;
                                break;
                            case "BASICO":
                                prontuario.addProcedimento(new ProcedimentoBasico());
                                qtdeProcedimentos--;
                                break;
                            case "COMUM":
                                prontuario.addProcedimento(new ProcedimentoComum());
                                qtdeProcedimentos--;
                                break;
                        }
                    }
                }
            }
        });

        return prontuario;
    }

    public String salveProntuario(Prontuario prontuario) throws IOException {
        String nomePaciente = prontuario.getNomePaciente();
        Internacao internacao = prontuario.getInternacao();
        Set<Procedimento> procedimentos = prontuario.getProcedimentos();

        l.add("nome_paciente,tipo_leito,qtde_dias_internacao,tipo_procedimento,qtde_procedimentos");

        String l1 = nomePaciente + ",";

        if (prontuario.getInternacao() != null) {
            l1 += internacao.getTipoLeito() + "," + internacao.getQtdeDias() + ",,";
            l.add(l1);
        }

        if (!procedimentos.isEmpty()) {
            Map<String, Long> procedimentosAgrupados = procedimentos.stream().collect(
                    Collectors.groupingBy(Procedimento::getTipoProcedimento, Collectors.counting()));

            List<String> procedimentosOrdenados = new ArrayList<>(procedimentosAgrupados.keySet());
            Collections.sort(procedimentosOrdenados);

            for (String chave : procedimentosOrdenados) {
                String l2 = nomePaciente + ",,," + chave + "," + procedimentosAgrupados.get(chave);
                l.add(l2);
            }
        }

        if (l.size() == 1) {
            l1 += ",,,";
            l.add(l1);
        }

        Path path = Paths.get(nomePaciente.replaceAll(" ", "_").concat(String.valueOf(System.currentTimeMillis())).concat(".csv"));

        Files.write(path, l);

        return path.toString();
    }
}
