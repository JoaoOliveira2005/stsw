package br.edu.idp.stsw.serenity.passos;

import br.edu.idp.stsw.serenity.tarefas.AbrirOSistema;
import br.edu.idp.stsw.serenity.tarefas.AdicionarTarefa;
import br.edu.idp.stsw.serenity.tarefas.ConcluirTarefa;
import br.edu.idp.stsw.serenity.tarefas.FazerLogin;
import br.edu.idp.stsw.serenity.telas.TelaDeTarefas;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.WaitUntil;

import java.util.List;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Liga cada frase de tarefas.feature ao código Java.
 */
public class PassosDeTarefas {

    @Dado("que {actor} entrou no sistema com usuário {string} e senha {string}")
    public void entrouNoSistema(Actor ator, String usuario, String senha) {
        ator.wasAbleTo(
                AbrirOSistema.naTelaDeLogin(),
                FazerLogin.com(usuario, senha),
                WaitUntil.the(TelaDeTarefas.CAMPO_NOVA_TAREFA, isVisible()).forNoMoreThan(5).seconds());
    }

    @Dado("que {actor} adicionou a tarefa {string}")
    public void adicionouATarefa(Actor ator, String tarefa) {
        ator.wasAbleTo(AdicionarTarefa.chamada(tarefa));
    }

    @Quando("{actor} adiciona a tarefa {string}")
    public void adicionaATarefa(Actor ator, String tarefa) {
        ator.attemptsTo(AdicionarTarefa.chamada(tarefa));
    }

    @Quando("{actor} conclui a tarefa {string}")
    public void concluiATarefa(Actor ator, String tarefa) {
        ator.attemptsTo(ConcluirTarefa.chamada(tarefa));
    }

    @Então("{actor} deve ver as tarefas:")
    public void deveVerAsTarefas(Actor ator, List<String> tarefas) {
        ator.attemptsTo(Ensure.that(TelaDeTarefas.TAREFAS).textValues().containsExactlyElementsFrom(tarefas));
    }

    @Então("{actor} deve ver a tarefa {string} como concluída")
    public void deveVerATarefaComoConcluida(Actor ator, String tarefa) {
        ator.attemptsTo(Ensure.that(TelaDeTarefas.TAREFA.of(tarefa)).hasCssClass("concluida"));
    }

    @Então("{actor} deve ver a lista de tarefas vazia")
    public void deveVerAListaDeTarefasVazia(Actor ator) {
        ator.attemptsTo(Ensure.that(TelaDeTarefas.TAREFAS).textValues().isEmpty());
    }
}
