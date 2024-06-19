import java.time.LocalDate;

public class Pessoa {
    public String nome;
    public LocalDate dataNascimento;

    Pessoa(String nome, LocalDate dataNascimento){
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }
}
