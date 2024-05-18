package br.com.alura.screenmatch.estudo;


import br.com.alura.screenmatch.model.DadosEpisodio;

import javax.crypto.spec.PSource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public @interface Stream {
//    List<String> nomes = Arrays.asList("Mitsy", "morango", "colecao", "chapeu", "paraguai");
//
    //FAZ DIFERENÇA SE AS PALAVRAS TIVEREM LETRA MAÍSCULA, MAIÚSCULA VEM PRIMEIRO

//    List<String> nomes = Arrays.asList("Mitsy", "Morango", "Colecao", "Chapeu", "Paraguai");
//
//		nomes.stream()
//              .sorted() //vai ordenar
//				.limit(3)   //limita aos três primeiros
//              .filter(n -> n.startsWith("N")) //separa pela inicial
//              .map(n -> n.toUpperCase()) //transforma letra maiuscula
//				.forEach(System.out::println); //imprime os selecionados
//              (forEach é uma "operação final")

    // FILRA NUMEROS PARES

//    List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//
//    List<Integer> numerosPares = numeros.stream()
//            .filter(n -> n % 2 == 0)
//            .collect(Collectors.toList());
//             (collect é outra "operação final" salva o que foi filtrado para uma nova lista)
//    System.out.println(numerosPares); // Output: [2, 4, 6, 8, 10]

//    Se usar apenas "toList()" ele cria uma lista imutável.
//    "Collectors.toList" cria uma lista que pode ser alterada depois.

    // OUTRO EXEMPLO DE COMO USAR
//    List<DadosEpisodio> episodios = temporadas.stream()
//            .flatMap(t -> t.episodios().stream()) //filtra episódios das temporadas
//            .collect(Collectors.toList());

}
