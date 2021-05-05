package org.ids.libri;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Comparator;

import org.ids.libri.Libro.Categoria;

public class StreamLibri {

    // mi appoggio ad un factory method che genera a rotazione
    // la stessa lista di libri
    public List<Libro> generaListaLibri(int n) {
        return Stream.generate(Libreria::gen)
            .limit(n)
            .collect(Collectors.toList());
    }

    public long contaLibriCyberpunk(List<Libro> list) {
        return list.stream()
            .filter(x -> x.getCategoria() == Categoria.CYBERPUNK)
            .peek(System.out::println)
            .count();
    }

    public List<Libro> prezzoCompresoTra12e15(List<Libro> list) {
        return list.stream()
			.filter(x -> x.getPrezzo() >= 10)
			.filter(x -> x.getPrezzo() <= 15)
            .peek(System.out::println)
            .collect(Collectors.toList());
    }

    public List<String> filtraListaTitoliLibriCyberpunkOppureFantasy(List<Libro> list) {
        return list.stream()
            .filter(x -> x.getCategoria() == Categoria.CYBERPUNK 
                || x.getCategoria() == Categoria.FANTASY)
            .map(Libro::getTitolo)
            .peek(System.out::println)
            .collect(Collectors.toList());
    }

    public List<Libro> generaListaLibriCyberpunk(int n) {
        return Stream.generate(Libreria::gen)
            .filter(x -> x.getCategoria() == Categoria.CYBERPUNK) 
            .limit(n)
            .peek(System.out::println)
            .collect(Collectors.toList());
    }

    public boolean checkSePresenteBurningChrome(List<Libro> list) {
        return list.stream()
            .peek(System.out::println)
            .filter(x -> x.getTitolo().equals("Burning Chrome"))
            .findAny()
            .isPresent();
    }

    public int sommaCosti_reduce(List<Libro> list) {
        return list.stream()
            .peek(System.out::println)
            .map(Libro::getPrezzo)
            .reduce(0, Integer::sum);
    }

    public int sommaCosti_sum(List<Libro> list) {
        return list.stream()
            .peek(System.out::println)
            .mapToInt(Libro::getPrezzo)
            .sum();
    }

    public double sommaCostiInDollari(double EUR_USD, List<Libro> list) {
        return list.stream()
            .map(Libro::getPrezzo)
            .peek(System.out::println)
			.map(x -> x * EUR_USD)
			.peek(System.out::println)
			.reduce(0.0, Double::sum);
    }

    public Optional<Libro> libroMenoCaroDa12InSu(List<Libro> list) {
        return list.stream()
            .filter(x -> x.getPrezzo() >= 12)
            .peek(System.out::println)
            .min(Comparator.comparing(Libro::getPrezzo));
    }

    public List<Libro> libriOrdinatiPerPrezzo(List<Libro> list) {
        return list.stream()
			.sorted(Comparator.comparing(Libro::getPrezzo))
            .peek(System.out::println)
            .collect(Collectors.toList());
    }

    public List<Libro> generaLibriHarryPotterDa15Euro(int n) {
        return IntStream.rangeClosed(1, n)
				.mapToObj(i -> new Libro("Harry Potter " + i, Categoria.FANTASY, 15))
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    public List<Libro> mescolaLista(List<Libro> list) {
        return Stream.generate(() -> (int) (Math.random() * list.size()))
            .distinct()
            .limit(list.size())
            .map(i -> list.get(i))
            .peek(System.out::println)
            .collect(Collectors.toList());
    }

    /**
     * Funzione pura: che non modifica uno stato condiviso (side-effect-free)
     * se parallelizzato con parallel() darà sempre lo stesso risultato
     * (l'ordine dei task paralleli non conta)
     */
    public Optional<Libro> primoPiuCaroDelPrecedente(List<Libro> list) {
        // il range parte da 1 invece che da zero
        return IntStream.range(1, list.size())
            .parallel()
            .filter(i -> list.get(i - 1).getPrezzo() < list.get(i).getPrezzo())
            .mapToObj(i -> list.get(i))
            .peek(System.out::println)
            .findFirst();
    }

}
