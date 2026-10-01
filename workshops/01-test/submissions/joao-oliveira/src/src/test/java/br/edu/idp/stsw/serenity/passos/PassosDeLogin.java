package br.edu.idp.stsw.serenity.passos;

import br.edu.idp.stsw.serenity.tarefas.AbrirOSistema;
import br.edu.idp.stsw.serenity.tarefas.FazerLogin;
import br.edu.idp.stsw.serenity.telas.TelaDeLogin;
import br.edu.idp.stsw.serenity.telas.TelaDeTarefas;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Liga cada frase de login.feature ao código Java.
 */
public class PassosDeLogin {

    @Dado("que {actor} está na tela de login")
    public void estaNaTelaDeLogin(Actor ator) {
        ator.wasAbleTo(AbrirOSistema.naTelaDeLogin());
    }

    @Quando("{actor} entra com usuário {string} e senha {string}")
    public void entraCom(Actor ator, String usuario, String senha) {
        ator.attemptsTo(FazerLogin.com(usuario, senha));
    }

    // O sistema demora 800 ms para responder: o WaitUntil espera a mensagem aparecer.
    @Então("{actor} deve ver a mensagem de boas-vindas {string}")
    public void deveVerAMensagemDeBoasVindas(Actor ator, String mensagem) {
        ator.attemptsTo(
                WaitUntil.the(TelaDeTarefas.MENSAGEM_DE_BOAS_VINDAS, isVisible()).forNoMoreThan(5).seconds(),
                Ensure.that(TelaDeTarefas.MENSAGEM_DE_BOAS_VINDAS).hasText(mensagem));
    }

    @Então("{actor} deve ver a mensagem de erro {string}")
    public void deveVerAMensagemDeErro(Actor ator, String mensagem) {
        ator.attemptsTo(
                WaitUntil.the(TelaDeLogin.MENSAGEM_DE_ERRO, isVisible()).forNoMoreThan(5).seconds(),
                Ensure.that(TelaDeLogin.MENSAGEM_DE_ERRO).hasText(mensagem));
    }
}
