# language: pt
Funcionalidade: Lista de tarefas
  Para não esquecer o que preciso entregar
  Como aluna que já entrou no sistema
  Eu quero anotar e concluir as minhas tarefas

  Contexto:
    Dado que Ana entrou no sistema com usuário "aluno" e senha "idp123"

  Cenário: Adicionar duas tarefas
    Quando ela adiciona a tarefa "Estudar Serenity BDD"
    E ela adiciona a tarefa "Preparar os slides"
    Então ela deve ver as tarefas:
      | Estudar Serenity BDD |
      | Preparar os slides   |

  Cenário: Concluir uma tarefa
    Dado que ela adicionou a tarefa "Entregar o workshop"
    Quando ela conclui a tarefa "Entregar o workshop"
    Então ela deve ver a tarefa "Entregar o workshop" como concluída

  Cenário: Tarefa em branco é ignorada
    Quando ela adiciona a tarefa ""
    Então ela deve ver a lista de tarefas vazia
