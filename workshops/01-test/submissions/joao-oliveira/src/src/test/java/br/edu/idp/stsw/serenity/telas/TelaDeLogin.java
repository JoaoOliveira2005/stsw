package br.edu.idp.stsw.serenity.telas;

import net.serenitybdd.screenplay.targets.Target;

/**
 * ONDE ficam os campos e botões da tela de login.
 * Se a tela mudar, só este arquivo precisa ser corrigido.
 */
public class TelaDeLogin {

    public static final Target CAMPO_USUARIO = Target.the("campo Usuário").locatedBy("#usuario");
    public static final Target CAMPO_SENHA = Target.the("campo Senha").locatedBy("#senha");
    public static final Target BOTAO_ENTRAR = Target.the("botão Entrar").locatedBy("#entrar");
    public static final Target MENSAGEM_DE_ERRO = Target.the("mensagem de erro").locatedBy("#erro");
}
