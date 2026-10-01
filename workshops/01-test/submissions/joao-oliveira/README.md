# Serenity BDD 5.3.11: testes que viram documentação

- **Aluno:** João Oliveira
- **Disciplina:** Segurança e Teste de Software (IDP), 2026.2
- **Slides:** [joao-oliveira.pdf](joao-oliveira.pdf)
- **Código de exemplo:** [src/](src/)

---

## Introdução

O [Serenity BDD](https://serenity-bdd.github.io) é uma biblioteca Java, gratuita e de código aberto, para escrever **testes de aceitação automatizados** que também servem de **documentação viva** do sistema. Ele não substitui o Cucumber nem o Selenium; fica por cima deles:

- o **Cucumber** lê os cenários escritos em linguagem natural (Gherkin, inclusive em português);
- o **Selenium WebDriver** (ou o Playwright) controla o navegador;
- o **Serenity** liga as duas coisas, organiza o código no padrão **Screenplay** e gera um **relatório HTML** com cada cenário, cada passo e um print da tela.

Foi criado por John Ferguson Smart, autor do livro *BDD in Action*. Começou com o nome *Thucydides* e passou a se chamar Serenity em 2014. A versão atual é a 5.x.

### Onde fica na pirâmide de testes

```
                 /\
                /  \        ACEITAÇÃO / INTERFACE  ← Serenity BDD + Cucumber + Selenium
               /    \       (simula o usuário na tela; o nível mais lento)
              /------\
             /        \     SERVIÇO / API          ← Serenity + REST Assured
            /          \    (testa a API direto, sem navegador)
           /------------\
          /              \  UNIDADE                ← JUnit, Mockito (não é o foco do Serenity)
         /________________\ (testa cada classe isolada; a base, com mais testes)
```

O Serenity atua no **topo da pirâmide**, em testes de aceitação de ponta a ponta pela interface. Com o módulo `serenity-rest-assured` ele também cobre a camada de **serviço/API**. Na **base** (testes de unidade) o natural é usar JUnit e Mockito direto. Como os testes de interface são os mais lentos e caros, a recomendação é usar o Serenity nos **fluxos mais importantes** do negócio, e não para testar cada regra isoladamente.

---

## Principais Funcionalidades

**Recursos suportados**

| Recurso | O que faz |
|---|---|
| Documentação viva | Relatório HTML com todos os cenários, cada passo, tempo de execução e print da tela. |
| Cenários em português | Arquivos `.feature` com `# language: pt` (`Funcionalidade`, `Cenário`, `Dado`, `Quando`, `Então`). |
| Padrão Screenplay | O teste é contado como uma história: um **ator** (a Ana) executa **tarefas** em **telas**. O código fica reaproveitável. |
| Asserções legíveis | `Ensure.that(...)` vira uma frase no relatório (por exemplo, *ela should see mensagem de boas-vindas with text value that is equal to: "Olá, aluno!"*). |
| Prints automáticos | `serenity.take.screenshots` define quando tirar print: a cada passo, só em falhas etc. |
| Esperas explícitas | `WaitUntil.the(alvo, isVisible())` espera a tela responder, sem `Thread.sleep`. |
| Web, API e mobile | Selenium ou Playwright (web), REST Assured (API) e Appium (mobile) no mesmo projeto. |

**Tipos de teste**

- **Caixa-preta:** o Serenity testa o sistema pelo que o usuário vê e faz, sem olhar o código da aplicação.
- **Aceitação (BDD):** cada cenário confere se o sistema faz o que o cliente pediu, escrito numa linguagem que o cliente consegue ler.
- Não é uma ferramenta de caixa-branca: cobertura de código e testes de unidade ficam com JUnit, Mockito, JaCoCo etc.

**Integrações**

- **Executores:** Cucumber 7, JUnit Platform 6 (JUnit 6) e JUnit 4.
- **Navegadores e automação:** Selenium WebDriver (Chrome, Firefox, Edge, Safari), Playwright, Appium.
- **Nuvens de navegadores:** BrowserStack, Sauce Labs, LambdaTest, Selenium Grid.
- **API:** REST Assured.
- **Build e CI/CD:** Maven, Gradle, Jenkins, GitHub Actions, GitLab CI (o relatório é só uma pasta HTML).
- **Gestão:** Jira (ligação de cenários a histórias).

---

## Demonstração

O exemplo testa uma aplicação web pequena que vem junto com o código ([src/src/test/resources/app/index.html](src/src/test/resources/app/index.html)), então **não depende de internet nem de servidor**. Ela tem:

- uma **tela de login** (usuário `aluno`, senha `idp123`) que **demora 800 ms para responder**, como um servidor de verdade;
- uma **lista de tarefas**, em que dá para adicionar e concluir tarefas.

São **6 cenários** escritos em português:

| Arquivo | Cenário |
|---|---|
| `login.feature` | Login com usuário e senha corretos |
| `login.feature` | Login recusado com usuário ou senha errados (Esquema do Cenário com 2 exemplos) |
| `tarefas.feature` | Adicionar duas tarefas |
| `tarefas.feature` | Concluir uma tarefa |
| `tarefas.feature` | Tarefa em branco é ignorada |

O cenário principal, em [login.feature](src/src/test/resources/features/login.feature):

```gherkin
Cenário: Login com usuário e senha corretos
  Dado que Ana está na tela de login
  Quando ela entra com usuário "aluno" e senha "idp123"
  Então ela deve ver a mensagem de boas-vindas "Olá, aluno!"
```

Cada frase é ligada a um método Java em [PassosDeLogin.java](src/src/test/java/br/edu/idp/stsw/serenity/passos/PassosDeLogin.java):

```java
@Quando("{actor} entra com usuário {string} e senha {string}")
public void entraCom(Actor ator, String usuario, String senha) {
    ator.attemptsTo(FazerLogin.com(usuario, senha));
}
```

E a tarefa [FazerLogin](src/src/test/java/br/edu/idp/stsw/serenity/tarefas/FazerLogin.java) diz **o que** a Ana faz, usando os elementos da [TelaDeLogin](src/src/test/java/br/edu/idp/stsw/serenity/telas/TelaDeLogin.java):

```java
public static Performable com(String usuario, String senha) {
    return Task.where("{0} entra com o usuário \"" + usuario + "\"",
            Enter.theValue(usuario).into(TelaDeLogin.CAMPO_USUARIO),
            Enter.theValue(senha).into(TelaDeLogin.CAMPO_SENHA),
            Click.on(TelaDeLogin.BOTAO_ENTRAR));
}
```

Pontos que o exemplo mostra:

- **Pronomes:** `screenplay.pronouns = "ela,ele"` no `serenity.conf` faz o "ela" do cenário apontar para a Ana.
- **Espera pelos 800 ms:** o passo `Então` usa `WaitUntil.the(..., isVisible())` em vez de `Thread.sleep`.
- **Esquema do Cenário:** o mesmo cenário roda com duas combinações de usuário e senha errados.
- **Tabela de dados:** `Então ela deve ver as tarefas:` recebe a lista de tarefas como `List<String>`.
- **Relatório:** um print da tela a cada passo (`take.screenshots = AFTER_EACH_STEP`).

### Estrutura do código (padrão Screenplay)

```
src/
├── README.md                           # como rodar (passo a passo)
├── pom.xml
└── src/test/
    ├── java/br/edu/idp/stsw/serenity/
    │   ├── CucumberTestSuite.java      # roda os .feature e envia o resultado ao Serenity
    │   ├── telas/                      # ONDE ficam os elementos da página
    │   │   ├── Aplicacao.java
    │   │   ├── TelaDeLogin.java
    │   │   └── TelaDeTarefas.java
    │   ├── tarefas/                    # O QUE o usuário faz
    │   │   ├── AbrirOSistema.java
    │   │   ├── FazerLogin.java
    │   │   ├── AdicionarTarefa.java
    │   │   └── ConcluirTarefa.java
    │   └── passos/                     # liga cada frase do .feature ao Java
    │       ├── ParametrosDosPassos.java
    │       ├── PassosDeLogin.java
    │       └── PassosDeTarefas.java
    └── resources/
        ├── app/index.html              # aplicação testada
        ├── features/login.feature
        ├── features/tarefas.feature
        ├── serenity.conf               # Chrome, headless, prints, pronomes
        ├── logback-test.xml            # deixa o console limpo
        └── logging.properties
```

Se um botão mudar de lugar, basta corrigir o arquivo em `telas/`. Tarefas, passos e cenários continuam iguais.

---

## Lista de Frameworks Similares

| Ferramenta | Camada | Linguagens | Diferença em relação ao Serenity |
|---|---|---|---|
| Cucumber (sozinho) | Aceitação | Java, JavaScript, Ruby e outras | Só lê os cenários; o relatório é simples e a organização do código fica por sua conta. |
| JBehave | Aceitação | Java | O framework BDD mais antigo em Java; o Serenity também se integra a ele. |
| Robot Framework | Aceitação | Python | Testes por palavras-chave, fácil para quem não programa; tem relatório próprio (`log.html`). |
| Gauge | Aceitação | Java, JavaScript, Python, C# | Especificações escritas em Markdown. |
| Karate | Serviço/API | DSL própria (roda na JVM) | Focado em API, sem precisar escrever Java. |
| Serenity/JS | Aceitação + API | JavaScript/TypeScript | Mesma ideia (Screenplay + relatório) para projetos em JavaScript. |
| Allure Report | Só relatórios | Várias | Gera relatórios bonitos, mas não organiza nem executa os testes. |

---

## Vantagens e Desvantagens

| Vantagens | Desvantagens |
|---|---|
| Relatório vivo, com prints de cada passo, que serve como evidência de teste. | Curva de aprendizado: Cucumber, Screenplay, Targets e configuração do Serenity de uma vez. |
| Cenários em português que cliente, analista e professor conseguem ler. | Mais configuração que usar o Selenium sozinho (BOM, plugins do Maven, `serenity.conf`). |
| Screenplay deixa o código reaproveitável e fácil de manter. | Tirar print de cada passo deixa a execução mais lenta (dá para usar `FOR_FAILURES`). |
| Web, API e mobile no mesmo projeto e no mesmo relatório. | Comunidade menor que a do Selenium ou do Cucumber; menos respostas prontas na internet. |
| Maduro: mais de 10 anos de desenvolvimento, versão 5 atual, documentação oficial. | Mudanças entre versões grandes: o plugin `SerenityReporterParallel`, por exemplo, mudou de pacote da versão 4 para a 5. |
| Gratuito e de código aberto. | Só Java; para JavaScript é outro projeto (Serenity/JS). |

**Desempenho medido neste exemplo:** os 6 cenários rodam em cerca de 20 segundos (um Chrome novo por cenário e print a cada passo). O `mvn clean verify` completo leva cerca de 35 segundos, depois que as dependências já foram baixadas.

---

## Casos de Sucesso

Não existe uma lista oficial de empresas que usam o Serenity, mas há exemplos públicos:

- **Livro *BDD in Action*, 2ª edição (Manning, 2023):** escrito por John Ferguson Smart e Jan Molak, os criadores do Serenity BDD e do Serenity/JS. O livro usa o Serenity nos exemplos e está na bibliografia da disciplina.
- **Xebia:** a consultoria publicou no seu blog uma avaliação prática do framework ([*Trying out the Serenity BDD framework; a report*](https://xebia.com/blog/trying-out-the-serenity-bdd-framework-a-report/)). A conclusão sobre o relatório foi: *"The reporting is indeed the most beautiful I had seen, personally."*
- **Nesta disciplina:** a aula [z03-todomvc-cucumber-serenitybdd](../../../../lectures/z03-todomvc-cucumber-serenitybdd/) usa Serenity, Cucumber e Screenplay para testar o TodoMVC.

---

## Conclusão

O Serenity BDD resolve três problemas comuns dos testes automatizados de interface: **testes ilegíveis** (os cenários ficam em português), **falta de evidência** (o relatório guarda cada passo com print) e **código repetido** (o Screenplay separa telas, tarefas e passos). O preço é uma curva de aprendizado maior e mais configuração do que usar o Selenium direto.

**Adote quando:**
- o projeto já usa BDD (`Dado`, `Quando`, `Então`);
- é preciso mostrar evidência dos testes (auditoria, cliente, homologação);
- a suíte de aceitação em Java é grande e precisa ser fácil de manter;
- pessoas que não programam leem os testes.

**Evite quando:**
- o projeto é pequeno ou é um protótipo;
- o time não conhece Java;
- só são necessários testes de unidade;
- o time trabalha com JavaScript (nesse caso, veja o Serenity/JS).

Em resumo: o Serenity vale a pena quando o relatório e os cenários em linguagem natural trazem valor para o time, e não só para quem programa.

---

## Instruções para execução do exemplo

O passo a passo completo, com instalação no Linux/WSL, Windows e macOS, o que olhar no relatório e problemas comuns, está em **[src/README.md](src/README.md)**.

### Pré-requisitos

| Ferramenta | Versão | Como conferir |
|---|---|---|
| JDK | 21 ou mais novo | `java -version` |
| Maven | 3.9 ou mais novo | `mvn -version` |
| Google Chrome | qualquer versão recente | `google-chrome --version` |

Se o Chrome não estiver instalado, o **Selenium Manager** baixa sozinho o *Chrome for Testing* na primeira execução (precisa de internet) e guarda em `~/.cache/selenium`. A primeira execução também baixa as dependências do Maven.

Versões usadas no projeto (definidas no [pom.xml](src/pom.xml)):

| Biblioteca | Versão |
|---|---|
| Serenity BDD (`serenity-bom`, `serenity-maven-plugin`) | 5.3.11 |
| Cucumber | 7.34.2 |
| JUnit Platform / Jupiter | 6.0.3 |
| Selenium (trazido pelo Serenity) | 4.46.0 |
| Java | 21 |

### Rodar os testes

```bash
cd workshops/01-test/submissions/joao-oliveira/src
mvn clean verify
```

Resultado esperado no final:

```
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

Para **ver o Chrome abrindo na tela** (útil na apresentação):

```bash
mvn clean verify -Dheadless.mode=false
```

### Abrir o relatório

O relatório é gerado em `target/site/serenity/index.html`:

```bash
xdg-open target/site/serenity/index.html                          # Linux
open target/site/serenity/index.html                              # macOS
start target\site\serenity\index.html                             # Windows (cmd)

# WSL: abre no Chrome do Windows
"/mnt/c/Program Files/Google/Chrome/Application/chrome.exe" "file:$(wslpath -w target/site/serenity/index.html | tr '\\' '/')"
```

No relatório, clique em um cenário e depois em um passo para ver o print da tela daquele momento.

### Como funciona por dentro

- O `maven-surefire-plugin` está desligado. Os cenários rodam no `maven-failsafe-plugin` (fase `integration-test`), que executa a classe `CucumberTestSuite`.
- Na fase `post-integration-test`, o `serenity-maven-plugin` (goal `aggregate`) junta os resultados e gera o relatório.
- Por isso o comando é `mvn verify`, e não `mvn test`.

### Problemas comuns

| Sintoma | Solução |
|---|---|
| `mvn -version` mostra 3.8 ou mais antigo | Instale o Maven 3.9+ e coloque a pasta `bin` dele antes das outras no `PATH`. |
| `SessionNotCreatedException` / Chrome não encontrado | Instale o Google Chrome ou rode uma vez com internet para o Selenium Manager baixar o Chrome for Testing. |
| `-Dheadless.mode=false` no WSL não abre janela | Precisa do WSLg (Windows 11). Sem ele, rode no modo padrão (headless) e veja os prints no relatório. |
