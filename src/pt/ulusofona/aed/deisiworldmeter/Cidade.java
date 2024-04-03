package pt.ulusofona.aed.deisiworldmeter;

public class Cidade {
    Pais pais;
    String nome;
    String regiao;
    int populacao;
    double latitude;
    double longitude;

    public Cidade(Pais pais, String nome, String regiao, int populacao, double latitude, double longitude) {
        this.pais = pais;
        this.nome = nome;
        this.regiao = regiao;
        this.populacao = populacao;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Cidade() {
    }



    @Override
    public String toString() {
        return nome + " | " + pais.alfa2 + " | " + regiao + " | " + populacao + " | (" + latitude + "," + longitude + ")";
    }
}
