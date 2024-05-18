package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=fac2f87d";
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private List<DadosSerie> dadosSeries = new ArrayList<>();
    private SerieRepository repositorio;
    private List<Serie> series = new ArrayList<>();

    private Optional<Serie> serieBusca;

    public Principal(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listas Séries buscadas
                    4 - Buscar Série por título
                    5 - Buscar série por ator
                    6 - Mostrar Top 5 Séries no Banco de Dados
                    7 - Buscar Séries por categoria
                    8 - Buscar Série por número de temporadas
                    9 - Buscar episódio por trecho do nome
                    10 - Mostrar Top 5 Episódios por série
                    11 - Mostrar Episódios a partir de uma data
                                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                    break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    buscarSeriesPorCategoria();
                    break;
                case 8:
                    buscarSeriesPorNumeroTemmporadas();
                    break;
                case 9:
                    buscarEpisodioPorTrecho();
                    break;
                case 10:
                    topEpisodiosPorSerie();
                    break;
                case 11:
                    buscarEpisodiosDepoisData();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSerieWeb() {
            DadosSerie dados = getDadosSerie();
            Serie serie = new Serie(dados);
            //dadosSeries.add(dados);
            repositorio.save(serie);
            System.out.println(dados);
        }

        private DadosSerie getDadosSerie() {
            System.out.println("Digite o nome da série para busca");
            var nomeSerie = leitura.nextLine();
            var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
            DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
            return dados;
        }

        private void buscarEpisodioPorSerie(){
            listarSeriesBuscadas();
            System.out.println("Informe a série: ");
            var nomeSerie = leitura.nextLine();

//            Optional<Serie> serie = series.stream()
//                    .filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
//                    .findFirst();
            //forma mais otimizada de fazer o mesmo que as linhas acima
            Optional<Serie> serie = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

            if (serie.isPresent()) {
                List<DadosTemporada> temporadas = new ArrayList<>();
                var serieEncontrada = serie.get();
                for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                    var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                    DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                    temporadas.add(dadosTemporada);
                }
                temporadas.forEach(System.out::println);

                List<Episodio> episodios = temporadas.stream()
                        .flatMap(d -> d.episodios().stream()
                        .map(e -> new Episodio(d.numero(), e)))
                        .collect(Collectors.toList());
                serieEncontrada.setEpisodios(episodios);
                repositorio.save(serieEncontrada);
            } else {
                System.out.println("Série não encontrada!");
            }
        }

        private void listarSeriesBuscadas() {
        //código que lista séries armazenadas no banco
            series = repositorio.findAll();

        //código que lista séries buscadas armazena numa lista no java
//            List<Serie> series = new ArrayList<>();
//            series = dadosSeries.stream()
//                    .map(d -> new Serie(d))
//                    .collect(Collectors.toList());

            //lista séries por gênero
            series.stream()
                    .sorted(Comparator.comparing(Serie::getGenero))
                    .forEach(System.out::println);
        }

    private void buscarSeriePorTitulo() {
        System.out.println("Escolha a série pelo nome: ");
        var nomeSerie = leitura.nextLine();
        serieBusca = repositorio.findByTituloContainingIgnoreCase(nomeSerie);
        if (serieBusca.isPresent()) {
            System.out.println("Dados da série: " + serieBusca.get());
        } else {
            System.out.println("Série não encontrada!");
        }
    }

    private void buscarSeriePorAtor() {
        System.out.println("Informe o nome do ator: ");
        var nomeAtor = leitura.nextLine();

        System.out.println("Avaliações a partir de qual valor: ");
        var avaliacao = leitura.nextDouble();
        List<Serie> serieEncontradas = repositorio
                .findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);
        System.out.println("Séries em que " + nomeAtor + " trabalhou em: ");
        serieEncontradas.forEach(s-> System.out.println(s.getTitulo() + " avaliação: " + s.getAvaliacao()));
    }

    private void buscarTop5Series() {
        List<Serie> serieTop = repositorio.findTop5ByOrderByAvaliacaoDesc();
        serieTop.forEach(s ->
                System.out.println(s.getTitulo() + " Avaliação: " + s.getAvaliacao()));
    }

    private void buscarSeriesPorCategoria() {
        System.out.println("Informe qual categoria de série buscar: ");
        var nomeGenero = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Séries da categoria: " + nomeGenero);
        seriesPorCategoria.forEach(System.out::println);
    }

    private void buscarSeriesPorNumeroTemmporadas() {
        System.out.println("Informe o número máximo de temporadas: ");
        var numeroTemporadas = leitura.nextInt();

        System.out.println("Avaliações a partir de qual valor: ");
        var avaliacao = leitura.nextDouble();

    //usando método JPQL
        List<Serie> filtroSeries = repositorio.seriesPorTemporadaEAvaliacao(numeroTemporadas, avaliacao);

        //usando método do repositório
//        List<Serie> seriesPorNumeroTemporadas = repositorio
//                .findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(numeroTemporadas, avaliacao);

        System.out.println("Séries com menos de " + numeroTemporadas + " com avaliação superior a " + avaliacao);
        filtroSeries.forEach(s ->
                System.out.println(s.getTitulo() + "  - avaliação: " + s.getAvaliacao()));
    }

    private void buscarEpisodioPorTrecho() {
        System.out.println("Informe o nome do episódio para busca: ");
        var trechoEpisodio = leitura.nextLine();
        List<Episodio> episodiosEncontrados = repositorio.episodiosPorTrecho(trechoEpisodio);
        episodiosEncontrados.forEach(e ->
                System.out.printf("Série: %s | Temporada %s - Episódio %s - %s\n",
                        e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()));
    }

    private void topEpisodiosPorSerie() {
        buscarSeriePorTitulo();
        if (serieBusca.isPresent()) {
            Serie serie = serieBusca.get();
            List<Episodio> topEpisodios = repositorio.topEpisodiosPorSerie(serie);
            topEpisodios.forEach(e ->
                    System.out.printf("Série: %s | Temporada %s - Episódio %s - %s - Avaliação: %s\n",
                            e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao()));
        }
    }


    private void buscarEpisodiosDepoisData() {
        buscarSeriePorTitulo();
        Serie serie = serieBusca.get();
        if (serieBusca.isPresent()) {
            System.out.println("Digite o ano limite de lançamento: ");
            var anoLancamento = leitura.nextInt();
            leitura.nextLine();

            List<Episodio> episodiosAno = repositorio.episodioPorSerieEAno(serie, anoLancamento);
            episodiosAno.forEach(System.out::println);
        }
    }
}
