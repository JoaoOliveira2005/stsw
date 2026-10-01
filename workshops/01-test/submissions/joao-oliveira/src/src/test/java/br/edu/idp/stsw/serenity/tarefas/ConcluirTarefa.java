package br.edu.idp.stsw.serenity.tarefas;

import br.edu.idp.stsw.serenity.telas.TelaDeTarefas;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;

public class ConcluirTarefa {

    public static Performable chamada(String nome) {
        return Task.where("{0} conclui a tarefa \"" + nome + "\"",
                Click.on(TelaDeTarefas.BOTAO_CONCLUIR.of(nome)));
    }
}
