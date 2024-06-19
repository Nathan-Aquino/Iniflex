import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Iniflex {

    public List<Funcionario> listaFuncionarios = new ArrayList<>();
    public Map<String,List<Funcionario>> mapFuncoes = new HashMap<>();

    public static void main(String[] args) {
        Iniflex iniflex = new Iniflex();
        iniflex.populaLista();

        Scanner scanner = new Scanner(System.in);

        boolean flag = true;
        boolean flagAgrupado = false;
        while (flag) {
            System.out.println("Bem-vindo ao Iniflex!");
            System.out.println("1 - Remover o funcionário “João” da lista.");
            System.out.println("2 - Imprimir todos os funcionários com todas as suas informações.");
            System.out.println("3 - Os funcionários receberam 10% de aumento de salário.");
            System.out.println("4 - Agrupar os funcionários por função.");
            System.out.println("5 - Imprimir os funcionários, agrupados por função.");
            System.out.println("6 - Imprimir os funcionários que fazem aniversário no mês 10 e 12.");
            System.out.println("7 - Imprimir o funcionário com a maior idade.");
            System.out.println("8 - Imprimir a lista de funcionários por ordem alfabética.");
            System.out.println("9 - Imprimir o total dos salários dos funcionários.");
            System.out.println("10 - Imprimir quantos salários mínimos ganha cada funcionário, considerando que o salário mínimo é R$1212.00.");
            System.out.println("11 - Sair.");

            System.out.print("Digite o número da opção escolhida: ");

            try {
                Integer escolha = scanner.nextInt();

                switch (escolha) {
                    case 1:
                        iniflex.removaJoao();
                        System.out.println("João Retirado da Lista!");
                        System.out.println();
                        break;
                    case 2:
                        iniflex.printaFuncionarios();
                        System.out.println();
                        break;
                    case 3:
                        iniflex.aumentaSalario();
                        System.out.println("Salários aumentados!");
                        System.out.println();
                        break;
                    case 4:
                        iniflex.agrupaFuncionarioPorFuncao();
                        flagAgrupado = true;
                        System.out.println("Funcionários agrupados!");
                        System.out.println();
                        break;
                    case 5:
                        if (flagAgrupado) {
                            iniflex.printaFuncionariosAgrupadosPorFuncao();
                            System.out.println();
                        } else {
                            System.out.println("Necessário Agrupar por função antes (opção 4)!");
                            System.out.println();
                        }
                        break;
                    case 6:
                        iniflex.imprimaFuncionariosNascimentoMes10e12();
                        System.out.println();
                        break;
                    case 7:
                        iniflex.imprimaFuncionarioMaisVelho();
                        System.out.println();
                        break;
                    case 8:
                        iniflex.imprimaFuncionariosPorOrdemAlfabetica();
                        System.out.println();
                        break;
                    case 9:
                        iniflex.totalSalarios();
                        System.out.println();
                        break;
                    case 10:
                        iniflex.quantidadeSalarioMinPorFuncionario();
                        System.out.println();
                        break;
                    case 11:
                        System.out.println("Até Logo!");
                        flag = false;
                        break;
                    default:
                        System.out.println("Opção inválida!");
                        System.out.println();
                        break;
                }
            } catch (InputMismatchException excecao) {
                System.out.println("Opção inválida!");
                scanner.nextLine();
                continue;
            }
        }
    }

    private void populaLista(){
        String caminhoCsv = "src/dados.csv";
        String linha = "";
        String separadorCsv = ",";
        try{
            BufferedReader br = new BufferedReader(new FileReader(caminhoCsv));
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(separadorCsv);
                //System.out.println(Arrays.toString(dados));
                Funcionario funcionario = new Funcionario(dados[0],
                        LocalDate.parse(dados[1], DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        new BigDecimal(dados[2]),
                        dados[3]
                        );
                this.listaFuncionarios.add(funcionario);
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removaJoao () {
        Funcionario funcinarioParaRemover = null;

        for (Funcionario funcionario : listaFuncionarios) {
            if (funcionario.nome.equals("João")) {
                funcinarioParaRemover = funcionario;
            }
        }

        listaFuncionarios.remove(funcinarioParaRemover);
    }

    public void printaFuncionarios () {
        for (Funcionario funcionario : listaFuncionarios) {
            System.out.println(funcionario.toString());
        }
    }

    public void aumentaSalario () {
        for (Funcionario funcionario : listaFuncionarios) {
            BigDecimal quantiaDeAumento = funcionario.getSalario().multiply(new BigDecimal("0.10"));
            BigDecimal salarioAumentado = funcionario.getSalario().add(quantiaDeAumento);
            funcionario.setSalario(salarioAumentado);
        }
    }

    public void agrupaFuncionarioPorFuncao () {
        for (Funcionario funcionario : listaFuncionarios) {
            if (!mapFuncoes.containsKey(funcionario.funcao)) {
                mapFuncoes.put(funcionario.funcao, new ArrayList<>());
            }

            mapFuncoes.get(funcionario.funcao).add(funcionario);
        }
    }

    public void printaFuncionariosAgrupadosPorFuncao () {
        mapFuncoes.forEach((funcao, funcionarios) -> {
            System.out.println("Função: " + funcao);
            for (Funcionario funcionario : funcionarios) {
                System.out.println(funcionario.nome);
            }
        });
    }

    public void imprimaFuncionariosNascimentoMes10e12 () {
        System.out.println("Funcionários que fazem aniversário no mês 10 e 12:");
        for (Funcionario funcionario : listaFuncionarios) {
            Integer mes = funcionario.dataNascimento.getMonthValue();
            if (mes.equals(10) || mes.equals(12)) {
                System.out.println(funcionario.nome);
            }
        }
    }

    public void imprimaFuncionarioMaisVelho () {
        Funcionario funcionarioMaisVelho = listaFuncionarios.getFirst();
        for (Funcionario funcionario : listaFuncionarios) {
            if(funcionario.dataNascimento.isBefore(funcionarioMaisVelho.dataNascimento)){
                funcionarioMaisVelho = funcionario;
            }
        }

        LocalDate dataDeHoje = LocalDate.now();
        Integer idade = dataDeHoje.getYear() - funcionarioMaisVelho.dataNascimento.getYear();

        if (dataDeHoje.getMonthValue() < funcionarioMaisVelho.dataNascimento.getMonthValue()
                || (dataDeHoje.getMonthValue() == funcionarioMaisVelho.dataNascimento.getMonthValue()
                && dataDeHoje.getDayOfMonth() < funcionarioMaisVelho.dataNascimento.getDayOfMonth())) {
            idade--;
        }

        System.out.println("Nome: " + funcionarioMaisVelho.nome + " | Idade: " + idade);
    }

    public void imprimaFuncionariosPorOrdemAlfabetica () {
        List<Funcionario> listaFuncionarioOrdenada = new ArrayList<>(listaFuncionarios);

        Collections.sort(listaFuncionarioOrdenada);

        for (Funcionario funcionario : listaFuncionarioOrdenada) {
            System.out.println(funcionario.toString());
        }
    }

    public void totalSalarios () {
        BigDecimal somatorio = new BigDecimal("0.0");

        for (Funcionario funcionario : listaFuncionarios) {
            somatorio = somatorio.add(funcionario.getSalario());
        }

        DecimalFormat formatoTotal = new DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance(new Locale("pt", "BR")));

        System.out.println("Total: " + "R$" + formatoTotal.format(somatorio));
    }

    public void quantidadeSalarioMinPorFuncionario () {
        BigDecimal salarioMinimo = new BigDecimal("1212.00");

        for (Funcionario funcionario : listaFuncionarios) {
            BigDecimal quantidadeSalarioMin = funcionario.getSalario().divideToIntegralValue(salarioMinimo);
            System.out.println("Nome: " + funcionario.nome + " | qtd.Salario Min: " + quantidadeSalarioMin);
        }
    }
}
