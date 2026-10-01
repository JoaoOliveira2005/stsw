package br.edu.idp.stsw.serenity.telas;

import net.serenitybdd.screenplay.targets.Target;

/**
 * ONDE ficam os elementos da tela de tarefas, que aparece depois do login.
 * Os alvos com {0} recebem o nome da tarefa: TAREFA.of("Estudar").
 */
public class TelaDeTarefas {

    public static final Target MENSAGEM_DE_BOAS_VINDAS = Target.the("mensagem de boas-vindas").locatedBy("#boas-vindas");
    public static final Target CAMPO_NOVA_TAREFA = Target.the("campo Nova tarefa").locatedBy("#nova-tarefa");
    public static final Target BOTAO_ADICIONAR = Target.the("botão Adicionar").locatedBy("#adicionar");
    public static final Target TAREFAS = Target.the("lista de tarefas").locatedBy("#lista-tarefas .texto");

    public static final Target TAREFA = Target.the("tarefa \"{0}\"")
            .locatedBy("//ul[@id='lista-tarefas']/li[span[normalize-space()='{0}']]");
    public static final Target BOTAO_CONCLUIR = Target.the("botão Concluir da tarefa \"{0}\"")
            .locatedBy("//ul[@id='lista-tarefas']/li[span[normalize-space()='{0}']]/button");
}
