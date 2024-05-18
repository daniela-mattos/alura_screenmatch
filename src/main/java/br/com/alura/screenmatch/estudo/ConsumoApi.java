package br.com.alura.screenmatch.estudo;

public @interface ConsumoApi {

    //		EXEMPLO DE TESTES CONSUMINDO A API E SELECIONANDO OS DADOS

//		var consumoApi = new ConsumoApi();
//		//pega os dados da api com o endereco abaixo e atribui a um json
//		var json = consumoApi.obterDados(
//				"https://www.omdbapi.com/?t=doctor+who&apikey=fac2f87d");
//		System.out.println(json);

    //usa o conversor da classe integrada com a interface genérica e atribui ao
    //objeto DadosSerie
//		ConverteDados conversor = new ConverteDados();
//		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
//
//		//deve apresentar os dados conforme o filtro feito na classe DadosSerie
//		System.out.println(dados);
//
//		json = consumoApi.obterDados("https://www.omdbapi.com/?t=doctor+who&season=1&episode=2&apikey=fac2f87d");
//		DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
//		System.out.println(dadosEpisodio);

//		List<DadosTemporada> temporadas = new ArrayList<>();
//
//		for (int i = 1; i <= dados.totalTemporadas() ; i++) {
//			json = consumoApi.obterDados("https://www.omdbapi.com/?t=doctor+who&season=" + i + "&apikey=fac2f87d");
//			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
//			temporadas.add(dadosTemporada);
//		}
//
//		temporadas.forEach(System.out::println);

}
