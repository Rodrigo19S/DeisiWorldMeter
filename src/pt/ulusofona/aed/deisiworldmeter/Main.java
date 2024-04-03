package pt.ulusofona.aed.deisiworldmeter;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        // Caminho da pasta que contém o arquivo cidades.csv
        parseFiles(new File("C:\\Users\\admin\\IdeaProjects\\AED project1-20240401T175634Z-001\\AED project1\\test-files"));


        // Agora que as listas estão preenchidas, você pode obter os objetos
        long start = System.currentTimeMillis();
        ArrayList objetos = getObjects(TipoEntidade.CIDADE);
        int numLinha = 0;
        for (Object objeto : objetos) {
            System.out.println(++numLinha + " -> ! " + objeto);
        }
        System.out.println(estatisticaCidades);
        ArrayList inputsInvalidos = getObjects(TipoEntidade.INPUT_INVALIDO);

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }


    public static ArrayList<Pais> listaPaises = new ArrayList<>();
    public static ArrayList<Cidade> listaCidades = new ArrayList<>();

    public static ArrayList<Populacao> listaPopulacao = new ArrayList<>();

    public static EstatisticaFicheiro estatisticaPaises = new EstatisticaFicheiro("paises.csv");
    public static EstatisticaFicheiro estatisticaCidades = new EstatisticaFicheiro("cidades.csv");
    public static EstatisticaFicheiro estatisticaPopulacao = new EstatisticaFicheiro("populacao.csv");

    public static void limparEstatisticas() {
        estatisticaPaises.posicaoPrimeiraLinhaInvalida = -1;
        estatisticaPaises.numLinhasInvalidas = 0;
        estatisticaPaises.numLinhasValidas = 0;
        estatisticaCidades.posicaoPrimeiraLinhaInvalida = -1;
        estatisticaCidades.numLinhasInvalidas = 0;
        estatisticaCidades.numLinhasValidas = 0;
        estatisticaPopulacao.posicaoPrimeiraLinhaInvalida = -1;
        estatisticaPopulacao.numLinhasInvalidas = 0;
        estatisticaPopulacao.numLinhasValidas = 0;
    }

    public static boolean parseFiles(File pasta) {
        // Criar os objetos File para os três arquivos
        // pasta é pasta no caso test-files onde se encontram os ficheiros csv
        // pasta é um parâmetro da função parseFiles, que já contém a referência para a pasta onde os arquivos estão localizados.
        listaPaises.clear();
        listaCidades.clear();
        listaPopulacao.clear();
        limparEstatisticas();
        File filePaises = new File(pasta + "/paises.csv");
        File fileCidades = new File(pasta + "/cidades.csv");
        File filePopulacao = new File(pasta + "/populacao.csv");

        // Verificar se todos os arquivos existem
        if (!fileCidades.exists() || !filePaises.exists() || !filePopulacao.exists()) {
            return false; // Se algum arquivo não existe, retornar false
        }

        try {
            // Tentar ler os arquivos
            Scanner scannerCidades = new Scanner(fileCidades);
            Scanner scannerPaises = new Scanner(filePaises);
            Scanner scannerPopulacao = new Scanner(filePopulacao);
            scannerPaises.nextLine(); // Ignorar cabeçalho
            scannerCidades.nextLine();
            scannerPopulacao.nextLine();

            // ------------------------------------------------
            // INICIO - Leitura Paises
            // ------------------------------------------------
            int countNumLinha = 1;
            boolean linhaValidaOuNaoPaises = false;
            while (scannerPaises.hasNextLine()) {
                String[] partes = scannerPaises.nextLine().split(",");
                countNumLinha++;
                if (partes.length != 4) {
                    estatisticaPaises.numLinhasInvalidas++;
                    if (estatisticaPaises.posicaoPrimeiraLinhaInvalida == -1) {
                        estatisticaPaises.posicaoPrimeiraLinhaInvalida = countNumLinha;
                    }
                    continue;
                }
                for (String parte : partes) {
                    if (parte == null || parte.isEmpty()) {
                        linhaValidaOuNaoPaises = true;
                        break;
                    }
                }

                if (linhaValidaOuNaoPaises) {
                    if (estatisticaPaises.posicaoPrimeiraLinhaInvalida == -1) {
                        estatisticaPaises.posicaoPrimeiraLinhaInvalida = countNumLinha;
                    }
                    linhaValidaOuNaoPaises = false;

                    estatisticaPaises.numLinhasInvalidas++;
                    continue;
                }
                int id;
                String alfa2;
                String alfa3;
                String nomePais;
                try {
                    id = Integer.parseInt(partes[0]);
                    alfa2 = partes[1].toUpperCase();
                    alfa3 = partes[2].toUpperCase();
                    nomePais = partes[3];
                } catch (NumberFormatException e) {
                    if (estatisticaPaises.posicaoPrimeiraLinhaInvalida == -1) {
                        estatisticaPaises.posicaoPrimeiraLinhaInvalida = countNumLinha;
                    }
                    estatisticaPaises.numLinhasInvalidas++;

                    continue;
                }
                //Para cada pais da lista paises ou seja ele vai percorrer os paises todos que tem atualmente adicionado
                // na listaPaises e verifica se essa linha (esse pais) é igual ao id
                for(Pais pais : listaPaises) {
                    if(id == pais.id) {
                        linhaValidaOuNaoPaises = true;
                        break;
                    }
                }
                // caso encontrar o linhaValidaPaises não adiciona e passa para a próxima linha onde vai ler essa próxima linha
                // isto é para não termos países repetidos no caso
                if(linhaValidaOuNaoPaises){
                    if (estatisticaPaises.posicaoPrimeiraLinhaInvalida == -1) {
                        estatisticaPaises.posicaoPrimeiraLinhaInvalida = countNumLinha;
                    }
                    estatisticaPaises.numLinhasInvalidas++;
                    linhaValidaOuNaoPaises = false;
                    continue;
                }

                estatisticaPaises.numLinhasValidas++;

                listaPaises.add(new Pais(id, alfa2, alfa3, nomePais));
            }
            // ------------------------------------------------
            // FIM - Leitura Paises
            // ------------------------------------------------

            // ------------------------------------------------
            // INICIO - Leitura Cidades
            // ------------------------------------------------
            countNumLinha = 1;
            boolean elementoCidades = false;
            //int countErros = 0;
            while (scannerCidades.hasNextLine()) {
                countNumLinha++;
                String[] partes = scannerCidades.nextLine().split(",");
                if (partes.length != 6) {
                    estatisticaCidades.numLinhasInvalidas++;
                    if (estatisticaCidades.posicaoPrimeiraLinhaInvalida == -1) {
                        estatisticaCidades.posicaoPrimeiraLinhaInvalida = countNumLinha;
                    }

                    continue;
                }
                for (String parte : partes) {
                    if (parte == null || parte.isEmpty()) {
                        elementoCidades = true;
                        break;
                    }
                }
                if (elementoCidades) {
                    if (estatisticaCidades.posicaoPrimeiraLinhaInvalida == -1) {
                        estatisticaCidades.posicaoPrimeiraLinhaInvalida = countNumLinha;
                    }
                    elementoCidades = false;
                    estatisticaCidades.numLinhasInvalidas++;
                    continue;
                }
                String alfa2Cidade = "";
                Pais paisCidade = null;
                String nomeCidade = "";
                String regiao = "";
                int regiaoEmInt = 0;
                double latitude = 0.0;
                double longitude = 0.0;
                int populacaoInt;
                try {//este try vai verificar se uma região é um numero ou nao
                    alfa2Cidade = partes[0].toUpperCase();
                    //Para cada pais na lista de paises ele verifica se o alfa2 de um certo pais da lista de paises (paises.csv) é igual
                    //ao alfa2 do  dessa cidade que estamos a ver e atribui o pais correpondente a essa cidade
                    for (Pais pais : listaPaises) {
                        if (pais.alfa2.equals(alfa2Cidade)) {
                            paisCidade = pais;
                            break;
                        }
                    }
                    nomeCidade = partes[1];
                    regiao = partes[2];
                    regiaoEmInt = Integer.parseInt(regiao);
                    String populacaoString = partes[3];
                    double populacaoDouble = Double.parseDouble(populacaoString);
                    populacaoInt = (int) populacaoDouble;
                    latitude = Double.parseDouble(partes[4]);
                    longitude = Double.parseDouble(partes[5]);
                } catch (NumberFormatException e) {
                    //countErros++;
                    //System.out.println("------------------------------- ERRO INICIO---------------------------------");
                    //System.out.println("NumeroErro : " + countErros);
                    //System.out.println("NumLinha: " + countNumLinhaCidades);
                    //StringBuilder linha = new StringBuilder();
                    //for(String p : partes) {
                    //    linha.append(p).append(" | ");
                    //}
                    //System.out.println(linha);
                    //System.out.println("------------------------------- ERRO FIM---------------------------------");
                    if (estatisticaCidades.posicaoPrimeiraLinhaInvalida == -1) {
                        estatisticaCidades.posicaoPrimeiraLinhaInvalida = countNumLinha;
                    }
                    estatisticaCidades.numLinhasInvalidas++;
                    continue;
                }

                // podem existir linhas do ficheiro da cidades que mencionem paises que na realidade não existem (no ficheiro paises.csv)
                if(paisCidade == null){
                    if (estatisticaCidades.posicaoPrimeiraLinhaInvalida == -1) {
                        estatisticaCidades.posicaoPrimeiraLinhaInvalida = countNumLinha;
                    }
                    estatisticaCidades.numLinhasInvalidas++;
                    continue;
                }

                estatisticaCidades.numLinhasValidas++;

                listaCidades.add(new Cidade(paisCidade, nomeCidade, regiao, populacaoInt, latitude, longitude));
            }
            // ------------------------------------------------
            // FIM - Leitura Cidades
            // ------------------------------------------------

            // ------------------------------------------------
            // INICIO - Leitura População
            // ------------------------------------------------
            countNumLinha = 1;
            boolean elementoPopulacao = false;
            while (scannerPopulacao.hasNextLine()) {
                //linha começa a 1 que supostamente é sempre o cabeçalho e ele vai começar
                // a ler a partir da próxima linha (que é a linha 2 já)  onde divide divide em partes por virgula
                //exemplo
                //id,alfa2,alfa3,nome linha 1
                //4,af,afg,Afeganistão linha 2
                String[] partes = scannerPopulacao.nextLine().split(",");
                countNumLinha++;

                // tratamento de linhas inválidas
                if (partes.length != 5) {
                    if (estatisticaPopulacao.posicaoPrimeiraLinhaInvalida == -1) {
                        estatisticaPopulacao.posicaoPrimeiraLinhaInvalida = countNumLinha;
                    }

                    estatisticaPopulacao.numLinhasInvalidas++;
                    continue;
                }

                for (String parte : partes) {
                    if (parte == null || parte.isEmpty()) {
                        elementoPopulacao = true;
                        break;
                    }
                }
                if (elementoPopulacao) {
                    if (estatisticaPopulacao.posicaoPrimeiraLinhaInvalida == -1) {
                        estatisticaPopulacao.posicaoPrimeiraLinhaInvalida = countNumLinha;
                    }
                    elementoPopulacao = false;

                    estatisticaPopulacao.numLinhasInvalidas++;
                    continue;
                }

                int id = 0;
                Pais paisPopulacao = null;
                int ano = 0;
                int populacaoMasculina = 0;
                int populacaoFeminina = 0;
                double densidade = 0;

                try {//este try vai verificar se uma região é um numero ou nao
                    id = Integer.parseInt(partes[0]);
                    ano = Integer.parseInt(partes[1]);
                    populacaoMasculina = Integer.parseInt(partes[2]);
                    populacaoFeminina = Integer.parseInt(partes[3]);
                    densidade = Double.parseDouble(partes[4]);
                } catch (NumberFormatException e) {
                    if (estatisticaPopulacao.posicaoPrimeiraLinhaInvalida == -1) {
                        estatisticaPopulacao.posicaoPrimeiraLinhaInvalida = countNumLinha;
                    }

                    estatisticaPopulacao.numLinhasInvalidas++;
                    continue;
                }
                //Para cada pais na lista de paises ele verifica se o id pais da lista de paises (paises.csv) é igual
                //ao id do ficheiro da populacao que estamos a ver e incrementa o tanto numero de ficheiros que encontrar
                // esse id dependendo do tamanho do ID (maior que 700) mostra no toString do pais o tanto de numero de linhas
                // de populacao que foram lidas
                for (Pais pais : listaPaises) {
                    if (pais.id == id) {
                        paisPopulacao = pais;
                        pais.numPopulacoes++;
                        break;
                    }
                }

                // podem existir linhas do ficheiro da população que mencionem paises que na realidade não existem (no ficheiro paises.csv)
                if(paisPopulacao == null){
                    if (estatisticaPopulacao.posicaoPrimeiraLinhaInvalida == -1) {
                        estatisticaPopulacao.posicaoPrimeiraLinhaInvalida = countNumLinha;
                    }
                    estatisticaPopulacao.numLinhasInvalidas++;
                    continue;
                }

                listaPopulacao.add(new Populacao(paisPopulacao, ano, populacaoMasculina, populacaoFeminina, densidade));
                estatisticaPopulacao.numLinhasValidas++;

            }
            // ------------------------------------------------
            // FIM - Leitura População
            // ------------------------------------------------

            // Fechar os scanners

            return true; // Retorna true se todos os arquivos foram lidos com sucesso
        } catch (FileNotFoundException e) {
            return false; // Retorna false se houver uma exceção ao tentar ler os arquivos
        }
    }


    public static ArrayList getObjects(TipoEntidade tipo) {
        switch (tipo) {
            case PAIS -> {
                return listaPaises;
            }
            case CIDADE -> {
                return listaCidades;
            }
            case INPUT_INVALIDO -> {
                // Retorna um array de três posições vazias
                ArrayList<Object> inputInvalido = new ArrayList<>();
                inputInvalido.add(estatisticaPaises);
                inputInvalido.add(estatisticaCidades);
                inputInvalido.add(estatisticaPopulacao);
                return inputInvalido;
            }

        }
        return null;
    }
}




