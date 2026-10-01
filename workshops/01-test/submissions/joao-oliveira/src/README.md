# Como rodar o exemplo de Serenity BDD

Este projeto roda **6 cenários de teste escritos em português** sobre uma página web local (login e lista de tarefas). O Serenity abre o Chrome, executa cada passo, tira um print da tela e, no final, gera um relatório HTML.

A execução completa leva cerca de 30 segundos. A primeira vez demora mais, porque o Maven baixa as dependências.

---

## 1. Pré-requisitos

| Ferramenta | Versão | Como conferir |
|---|---|---|
| JDK (Java) | 21 ou mais novo | `java -version` |
| Maven | 3.9 ou mais novo | `mvn -version` |
| Google Chrome | qualquer versão recente | `google-chrome --version` (Linux) |
| Internet | só na primeira execução | para baixar as dependências |

> **Chrome:** se ele não estiver instalado, o Selenium Manager baixa sozinho o *Chrome for Testing* na primeira execução e guarda em `~/.cache/selenium`. Mesmo assim, é mais garantido ter o Chrome instalado.

### Instalação no Ubuntu / WSL

```bash
# Java 21
sudo apt update
sudo apt install -y openjdk-21-jdk

# Maven 3.9 (o apt do Ubuntu 24.04 só tem o 3.8)
cd ~
wget https://archive.apache.org/dist/maven/maven-3/3.9.16/binaries/apache-maven-3.9.16-bin.tar.gz
tar xzf apache-maven-3.9.16-bin.tar.gz
echo 'export PATH="$HOME/apache-maven-3.9.16/bin:$PATH"' >> ~/.bashrc
source ~/.bashrc

# Google Chrome
wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
sudo apt install -y ./google-chrome-stable_current_amd64.deb
```

### Instalação no Windows

1. Java 21: instale o [Eclipse Temurin 21](https://adoptium.net/temurin/releases/?version=21).
2. Maven 3.9: baixe o `.zip` em [maven.apache.org/download.cgi](https://maven.apache.org/download.cgi), descompacte e coloque a pasta `bin` no `Path` das variáveis de ambiente.
3. Chrome: [google.com/chrome](https://www.google.com/chrome/).
4. Feche e abra o terminal de novo para o `Path` atualizar.

### Instalação no macOS

```bash
brew install openjdk@21 maven
```

O Chrome se instala por [google.com/chrome](https://www.google.com/chrome/).

---

## 2. Rodar os testes

Na raiz do repositório `stsw`:

```bash
cd workshops/01-test/submissions/joao-oliveira/src
mvn clean verify
```

No console, cada cenário aparece com os passos marcados com ✔:

```
Cenário: Login com usuário e senha corretos
  ✔ Dado que Ana está na tela de login
  ✔ Quando ela entra com usuário "aluno" e senha "idp123"
  ✔ Então ela deve ver a mensagem de boas-vindas "Olá, aluno!"
...
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

Se aparecer `Tests run: 6, Failures: 0, Errors: 0` e `BUILD SUCCESS`, está tudo certo.

> O comando é `mvn verify`, e não `mvn test`. Os cenários rodam no plugin *failsafe* (fase `integration-test`), e o relatório é gerado logo depois, na fase `post-integration-test`.

---

## 3. Ver o Chrome abrindo na tela

Por padrão, o Chrome roda escondido (*headless*). Para ver o navegador executando os passos:

```bash
mvn clean verify -Dheadless.mode=false
```

Abre um Chrome novo para cada cenário. **Não feche as janelas durante a execução**, porque isso faz o teste falhar.

> No WSL, a janela só aparece com o WSLg (Windows 11). Sem ele, use o modo padrão e veja os prints no relatório.

---

## 4. Abrir o relatório

O relatório fica em `target/site/serenity/index.html`. Ainda dentro da pasta `src`:

| Sistema | Comando |
|---|---|
| Linux | `xdg-open target/site/serenity/index.html` |
| macOS | `open target/site/serenity/index.html` |
| Windows (cmd) | `start target\site\serenity\index.html` |

**No WSL**, abra direto no Chrome do Windows. O `explorer.exe` nem sempre abre arquivos que estão dentro do Linux:

```bash
"/mnt/c/Program Files/Google/Chrome/Application/chrome.exe" "file:$(wslpath -w target/site/serenity/index.html | tr '\\' '/')"
```

Outra opção é colar na barra do Chrome o endereço `file://wsl.localhost/Ubuntu/<caminho até o projeto>/src/target/site/serenity/index.html`.

> **Dica:** deixe a aba do relatório aberta. Depois de rodar `mvn clean verify` de novo, basta apertar **F5** para ver o relatório atualizado.

**O que olhar no relatório:**

1. Na página inicial, o total de **6 tests** e 100% de sucesso nas duas funcionalidades ("Login no sistema de tarefas" e "Lista de tarefas").
2. Na lista *Automated Scenarios*, clique em **Login com usuário e senha corretos**.
3. Clique em um passo para ver o **print da tela** daquele momento.
4. Abra **Login recusado com usuário ou senha errados**: é o *Esquema do Cenário*, que rodou duas vezes ("2 passing test cases").

---

## 5. (Opcional) Ver um teste falhando

Para ver como o Serenity mostra um erro:

1. Abra `src/test/resources/features/login.feature`.
2. Troque `"Olá, aluno!"` por `"Olá, Ana!"` e salve.
3. Rode `mvn clean verify` de novo. O build falha com `Failures: 1`.
4. No console aparece o erro com o valor esperado e o encontrado:
   ```
   Expected: mensagem de boas-vindas with text value that is equal to: <"Olá, Ana!">
   Actual:   <"Olá, aluno!">
   ```
   O relatório é gerado mesmo com a falha, e o cenário aparece em vermelho com o print da tela.
5. Volte o texto para `"Olá, aluno!"` e salve.

---

## 6. Onde mexer no código

| Quero mudar... | Arquivo |
|---|---|
| O texto dos cenários | `src/test/resources/features/*.feature` |
| O que cada frase faz em Java | `src/test/java/br/edu/idp/stsw/serenity/passos/` |
| As ações do usuário (login, adicionar, concluir) | `src/test/java/br/edu/idp/stsw/serenity/tarefas/` |
| Onde ficam os campos e botões | `src/test/java/br/edu/idp/stsw/serenity/telas/` |
| A página testada | `src/test/resources/app/index.html` |
| Navegador, headless e prints | `src/test/resources/serenity.conf` |
| Versões das bibliotecas | `pom.xml` |

---

## 7. Problemas comuns

| Sintoma | Solução |
|---|---|
| `mvn: command not found` | O Maven não está no `PATH`. Refaça o passo de instalação e abra um terminal novo. |
| `mvn -version` mostra 3.8 ou mais antigo | Coloque a pasta `bin` do Maven 3.9 **antes** das outras no `PATH`. |
| `release version 21 not supported` | O Maven está usando um Java antigo. Confira com `mvn -version` qual Java aparece. |
| `SessionNotCreatedException` ou Chrome não encontrado | Instale o Google Chrome ou rode uma vez com internet para o Selenium baixar o Chrome for Testing. |
| `-Dheadless.mode=false` não abre janela no WSL | Precisa do WSLg (Windows 11). Rode sem a opção e veja os prints no relatório. |
| `there is no POM in this directory` | Entre na pasta `src`, onde está o `pom.xml`, antes de rodar o Maven. |
| `Tests are skipped.` e nenhum cenário roda | Foi usado `mvn test`. Use `mvn clean verify`. |

---

## Resumo dos comandos

| Comando | O que faz |
|---|---|
| `mvn clean verify` | Roda os 6 cenários (Chrome escondido) e gera o relatório |
| `mvn clean verify -Dheadless.mode=false` | Mesma coisa, mostrando o Chrome na tela |
| F5 na aba do relatório | Mostra o relatório da última execução (veja a seção 4 para abrir a primeira vez) |
