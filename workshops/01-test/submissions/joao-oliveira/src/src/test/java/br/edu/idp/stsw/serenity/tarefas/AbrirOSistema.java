package br.edu.idp.stsw.serenity.tarefas;

import br.edu.idp.stsw.serenity.telas.Aplicacao;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

public class AbrirOSistema {

    public static Performable naTelaDeLogin() {
        return Task.where("{0} abre o sistema na tela de login",
                Open.url(Aplicacao.url()));
    }
}
