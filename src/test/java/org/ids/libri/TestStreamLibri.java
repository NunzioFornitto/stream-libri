package org.ids.libri;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.*; // AssertJ

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.ids.libri.Libro.Categoria;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestStreamLibri {
    StreamLibri sl;

    @Before
    public void setUp() {
        sl = new StreamLibri();
    }

    @After
    public void tearDown() {
        Libreria.reset();
    }
    
    @Test
    public void test_generaLibri() {
        List<Libro> list = sl.generaListaLibri(10);
        assertThat(list)
                .hasSize(10)
                .doesNotContainNull();
    }

    @Test
    public void test_contaLibriCyberpunk() {
        List<Libro> list = sl.generaListaLibri(10);
        long cnt = sl.contaLibriCyberpunk(list);

        assertEquals(3, cnt);
    }

    @Test
    public void test_prezzoCompresoTra12e15() {
        List<Libro> inList = sl.generaListaLibri(5);
        List<Libro> outList = sl.prezzoCompresoTra12e15(inList);

        assertThat(outList)
            .allMatch(x -> x.getPrezzo() >= 12)
            .allMatch(x -> x.getPrezzo() <= 15);
    }

    @Test
    public void test_creaListaTitoliLibriCyberpunkFantasy() {
        List<Libro> inList = sl.generaListaLibri(10);
        List<String> outList = sl.filtraListaTitoliLibriCyberpunkOppureFantasy(inList);
        
        assertThat(outList).containsOnly(
            "Leviathan", "Trono di Spade", 
            "Signore degli Anelli", "Neuromante", 
            "Monnalisa Cyberpunk", "Mirrorshades");
    }

    @Test
    public void test_generaListaLibriCyberpunk() {
        List<Libro> list = sl.generaListaLibriCyberpunk(10);
        
        assertThat(list).hasSize(10);
        assertThat(list).allMatch(x -> x.getCategoria() == Categoria.CYBERPUNK);
    }

    @Test
    public void test_checkSePresenteBurningChrome() {
        List<Libro> list = sl.generaListaLibri(10);

        boolean presente = sl.checkSePresenteBurningChrome(list);
        
        assertThat(presente).isFalse();
    }

    @Test
    public void test_sommaCosti_reduce() {
        List<Libro> list = sl.generaListaLibri(5);

        int sum = sl.sommaCosti_reduce(list);

        assertThat(sum).isEqualTo(112);
    }

    @Test
    public void test_sommaCosti_sum() {
        List<Libro> list = sl.generaListaLibri(5);

        int sum = sl.sommaCosti_sum(list);

        assertThat(sum).isEqualTo(112);
    }

    @Test
    public void test_sommaCostiInDollari() {
        final double EUR_USD = 1.12;
        List<Libro> list = sl.generaListaLibri(5);
        
        double sum = sl.sommaCostiInDollari(EUR_USD, list);

        assertThat(sum).isEqualTo(112 * EUR_USD, offset(0.001));
    }

    @Test
    public void test_libroMenoCaroDa12InSu() {
        List<Libro> list = sl.generaListaLibri(5);

        Optional<Libro> book = sl.libroMenoCaroDa12InSu(list);

        if (book.isPresent())
            assertThat(book.get().getTitolo()).isEqualTo("Monnalisa Cyberpunk");
        else
            fail("should be present");
    }

    @Test
    public void test_libriOrdinatiPerPrezzo() {
        List<Libro> inList = sl.generaListaLibri(10);

        List<Libro> outList = sl.libriOrdinatiPerPrezzo(inList);

        assertThat(outList)
            .isSortedAccordingTo(Comparator.comparing(Libro::getPrezzo));
    }

    @Test
    public void test_generaLibriHarryPotterDa15Euro() {
        List<Libro> list = sl.generaLibriHarryPotterDa15Euro(7);

        // Qualche esempio di asserzione
        assertThat(list).hasSize(7);
        assertThat(list).allMatch(x -> x.getTitolo().matches("Harry Potter [1-7]"));
        assertThat(list).allMatch(x -> x.getCategoria() == Categoria.FANTASY);
        assertThat(list).allMatch(x -> x.getPrezzo() == 15);
        assertThat(list.stream()
            .map(Libro::getTitolo)
            .distinct()
            .collect(Collectors.toList())).hasSize(7);
    }

    @Test
    public void test_mescolaLista() {
        List<Libro> list = sl.generaListaLibri(5);

        list.forEach(System.out::println);
        
        List<Libro> shuffle = sl.mescolaLista(list);

        assertThat(shuffle).containsExactlyInAnyOrderElementsOf(list); 
    }

    @Test
    public void test_primoPiuCaroDelPrecedente() {
        List<Libro> list = sl.generaListaLibri(3);

        list.forEach(System.out::println);

        Optional<Libro> libro = sl.primoPiuCaroDelPrecedente(list);
        
        assertThat(libro).isPresent();
        assertThat(libro.get().getTitolo()).isEqualTo("Trono di Spade");
    }

}
