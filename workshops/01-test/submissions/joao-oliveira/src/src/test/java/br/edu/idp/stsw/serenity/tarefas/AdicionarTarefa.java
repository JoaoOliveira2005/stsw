package br.edu.idp.stsw.serenity.tarefas;

import br.edu.idp.stsw.serenity.telas.TelaDeTarefas;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

public class AdicionarTarefa {

    public static Performable chamada(String nome) {
        return Task.where("{0} adiciona a tarefa \"" + nome + "\"",
                Enter.theValue(nome).into(TelaDeTarefas.CAMPO_NOVA_TAREFA),
                Click.on(TelaDeTarefas.BOTAO_ADICIONAR));
    }
}
