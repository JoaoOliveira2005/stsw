package br.edu.idp.stsw.serenity.passos;

import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

public class ParametrosDosPassos {

    /** Antes de cada cenário: um elenco novo, em que cada ator ganha o seu navegador. */
    @Before
    public void prepararOPalco() {
        OnStage.setTheStage(new OnlineCast());
    }

    /**
     * Transforma o nome escrito no cenário ("Ana") em um ator do Screenplay.
     * Os pronomes "ela" e "ele" (serenity.conf) devolvem o último ator citado.
     */
    @ParameterType("\\p{L}+")
    public Actor actor(String nome) {
        return OnStage.theActorCalled(nome);
    }
}
