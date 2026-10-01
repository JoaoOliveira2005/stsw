package br.edu.idp.stsw.serenity.tarefas;

import br.edu.idp.stsw.serenity.telas.TelaDeLogin;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

/**
 * O QUE o usuário faz para entrar: digita usuário e senha e clica em Entrar.
 */
public class FazerLogin {

    public static Performable com(String usuario, String senha) {
        return Task.where("{0} entra com o usuário \"" + usuario + "\"",
                Enter.theValue(usuario).into(TelaDeLogin.CAMPO_USUARIO),
                Enter.theValue(senha).into(TelaDeLogin.CAMPO_SENHA),
                Click.on(TelaDeLogin.BOTAO_ENTRAR));
    }
}
