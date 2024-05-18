package br.com.alura.screenmatch.estudo;

public @interface Peek {

    //Exemplo de uso do Peek para ver como funciona cada etapa do "Stream"

//    System.out.println("\nTop 10 episódios");
//    dadosEpisodios.stream()
//            .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//            .peek(e -> System.out.println("Primeiro filtro(N/A) " + e))
//            .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//            .peek(e -> System.out.println("Ordenação " + e))
//            .limit(10)
//            .peek(e -> System.out.println("Limite " + e))
//            .map(e -> e.titulo().toUpperCase())
//            .peek(e -> System.out.println("Mapeamento " + e))
//            .forEach(System.out::println);

    //A função”peek” é uma operação intermediária de uma stream. Uma operação intermediária é aquela
    // que processa os dados da stream, mas não a finaliza, ou seja, ela permite que mais operações
    // sejam feitas depois dela. A função peek é usada para "espiar" os elementos da stream sem alterá-los,
    // o que pode ser muito útil para depuração. Essa função vai receber cada elemento da stream e
    // realizar alguma operação de efeito colateral (como imprimir o elemento),
    // mas vai devolver o mesmo elemento sem alterar nada.


}
