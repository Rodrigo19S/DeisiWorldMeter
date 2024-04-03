package pt.ulusofona.aed.deisiworldmeter;

public class Pais {
    int id;
    String nome;

    String alfa2;

    String alfa3;

    int numPopulacoes = 0;

    public Pais(int id, String alfa2, String alfa3, String nome) {
        this.id = id;
        this.alfa2 = alfa2;
        this.alfa3 = alfa3;
        this.nome = nome;
    }

    public Pais() {
    }

    @Override
    public String toString() {
        if (id > 700) {
            return nome + " | " + id + " | " + alfa2 + " | " + alfa3 + " | " + numPopulacoes;

        }
        return nome + " | " + id + " | " + alfa2 + " | " + alfa3;
    }
}
