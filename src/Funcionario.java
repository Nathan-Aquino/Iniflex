import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Funcionario extends Pessoa implements Comparable<Funcionario> {
    private BigDecimal salario;
    public String funcao;

    Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao){
        super(nome,dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    @Override
    public String toString () {

        DecimalFormat formatoSalario = new DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance(new Locale("pt", "BR")));
        DateTimeFormatter dataNascimentoFormato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return "Nome: " + this.nome + " | " +
                "Data de nascimento: " + this.dataNascimento.format(dataNascimentoFormato) + " | " +
                "Salário: R$" + formatoSalario.format(this.getSalario()) + " | " +
                "Função: " + this.funcao;
    }

    @Override
    public int compareTo(Funcionario funcionario) {
        return this.nome.compareTo(funcionario.nome);
    }
}
