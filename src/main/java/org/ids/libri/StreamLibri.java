package org.ids.libri;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.plaf.synth.SynthEditorPaneUI;

import org.ids.libri.Libro.Categoria;

public class StreamLibri {

    // mi appoggio ad un factory method che genera a rotazione
    // la stessa lista di libri
    public List<Libro> generaListaLibri(int n) {
        return Stream.generate(Libreria::gen).limit(n).collect(Collectors.toList());
    }

    public long contaLibriCyberpunk(List<Libro> list) {
        return list.stream().filter(x -> x.getCategoria() == Categoria.CYBERPUNK).peek(System.out::println).count();

    }

    public List<Libro> prezzoCompresoTra12e15(List<Libro> list) {
        return list.stream().filter(s -> s.getPrezzo() >= 12 && s.getPrezzo() <= 15).peek(System.out::println)
                .collect(Collectors.toList());

    }

    public List<String> filtraListaTitoliLibriCyberpunkOppureFantasy(List<Libro> list) {
        return list.stream()
                .filter(x -> x.getCategoria() == Categoria.CYBERPUNK || x.getCategoria() == Categoria.FANTASY)
                .peek(System.out::println).map(Libro::getTitolo).collect(Collectors.toList());

    }

    public List<Libro> generaListaLibriCyberpunk(int n) {
        return Stream.generate(Libreria::gen).filter(x -> x.getCategoria() == Categoria.CYBERPUNK).limit(n)
                .peek(System.out::println).collect(Collectors.toList());

    }

    public boolean checkSePresenteBurningChrome(List<Libro> list) {
        Optional<Libro> gg = list.stream().filter(x -> x.getTitolo().equals("BurningChrome")).findAny();
        if (gg.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public int sommaCosti_reduce(List<Libro> list) {
        return list.stream()
                .map(Libro::getPrezzo)
                .peek(System.out::println)
                .reduce(0,(acc,x)->acc+x);
    }
    //mapToInt = Da uno stream in input di libri restituisce in output uno stream di interi, in base alla funzione passata
    public int sommaCosti_sum(List<Libro> list) {
                return list.stream()
                        .mapToInt(Libro::getPrezzo)
                        .peek(System.out::println)
                        .sum();
    }

    public double sommaCostiInDollari(double EUR_USD, List<Libro> list) {
        
        return 0.0;
    }

    public Optional<Libro> libroMenoCaroDa12InSu(List<Libro> list) {
       Optional<Libro> gg = list.stream()
                            .filter(s->s.getPrezzo()>=12)
                            .min(Comparator.comparing(Libro::getPrezzo));
            if(gg.isPresent()){
                return gg;
            }else{
                return null;
            }
    }

    public List<Libro> libriOrdinatiPerPrezzo(List<Libro> list) {
             return list.stream()
                        .sorted(Comparator.comparing(Libro::getPrezzo))
                        .collect(Collectors.toList());
            }

    // Titolo: "Harry Potter 1" "Harry Potter 2"... "Harry Potter n"
    // categoria: fantasy, prezzo: 15 euro

    //Con intStream creo il numero di libri da 1 fino a n con mapToObj creo una nuova istanza di libro 
    public List<Libro> generaLibriHarryPotterDa15Euro(int n) {
            return null;
    }

    public List<Libro> mescolaLista(List<Libro> list) {
         return Stream.generate(() -> (int) (Math.random() * list.size()))
            .distinct()
            .limit(list.size())
            .map(i -> list.get(i))
            .peek(System.out::println)
            .collect(Collectors.toList());

        
    }

    public Optional<Libro> primoPiuCaroDelPrecedente(List<Libro> list) {
        return null;
    }

}
