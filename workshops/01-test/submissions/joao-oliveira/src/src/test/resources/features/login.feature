# language: pt
Funcionalidade: Login no sistema de tarefas
  Para organizar os meus estudos
  Como aluna do IDP
  Eu quero entrar no sistema com o meu usuário e a minha senha

  Cenário: Login com usuário e senha corretos
    Dado que Ana está na tela de login
    Quando ela entra com usuário "aluno" e senha "idp123"
    Então ela deve ver a mensagem de boas-vindas "Olá, aluno!"

  Esquema do Cenário: Login recusado com usuário ou senha errados
    Dado que Ana está na tela de login
    Quando ela entra com usuário "<usuario>" e senha "<senha>"
    Então ela deve ver a mensagem de erro "Usuário ou senha inválidos."

    Exemplos:
      | usuario | senha  |
      | aluno   | 123456 |
      | maria   | idp123 |
