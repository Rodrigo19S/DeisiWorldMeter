package pt.ulusofona.aed.deisiworldmeter;

public class EstatisticaFicheiro {
        String nomeFicheiro ;
        int numLinhasValidas = 0;
        int numLinhasInvalidas = 0;
        Integer posicaoPrimeiraLinhaInvalida = -1;

    public EstatisticaFicheiro(String nomeFicheiro, int numLinhasValidas, int numLinhasIUnvalidas, int posicaoPrimeiraLinhaInvalida) {
        this.nomeFicheiro = nomeFicheiro;
        this.numLinhasValidas = numLinhasValidas;
        this.numLinhasInvalidas = numLinhasIUnvalidas;
        this.posicaoPrimeiraLinhaInvalida = posicaoPrimeiraLinhaInvalida;
    }

    public EstatisticaFicheiro(String nomeFicheiro) {
        this.nomeFicheiro = nomeFicheiro;
    }

    @Override
    public String toString() {
        return nomeFicheiro + " | " + numLinhasValidas + " | " + numLinhasInvalidas + " | " + posicaoPrimeiraLinhaInvalida;
    }
}
