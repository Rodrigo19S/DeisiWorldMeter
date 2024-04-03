package pt.ulusofona.aed.deisiworldmeter;

public class Populacao {
    Pais pais;
    Integer ano;
    int populacaoMasculina;
    int populacaoFeminina;
    double densidade;

    public Populacao(Pais pais, Integer ano, int populacaoMasculina, int populacaoFeminina, double densidade) {
        this.pais = pais;
        this.ano = ano;
        this.populacaoMasculina = populacaoMasculina;
        this.populacaoFeminina = populacaoFeminina;
        this.densidade = densidade;
    }

    public Populacao() {

    }

    @Override
    public String toString() {
        return pais.id + " | " + ano + " | " + populacaoMasculina + " | " + populacaoFeminina + " | " + densidade;
    }
}
