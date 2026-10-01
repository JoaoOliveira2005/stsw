package br.edu.idp.stsw.serenity.telas;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

/**
 * A aplicação de exemplo é uma página HTML local (src/test/resources/app/index.html),
 * então os testes não dependem de internet nem de servidor.
 */
public class Aplicacao {

    private static final String PAGINA = "/app/index.html";

    public static String url() {
        URL pagina = Aplicacao.class.getResource(PAGINA);
        if (pagina == null) {
            throw new IllegalStateException("Não encontrei " + PAGINA + " no classpath");
        }
        try {
            return Path.of(pagina.toURI()).toUri().toString();
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }
}
