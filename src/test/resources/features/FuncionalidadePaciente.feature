 language: pt
 Funcionalidade: API - Pacientes

 Fundo:
 Dado que eu tenho os dados de um paciente

 Cenário: Cadastrar um novo paciente
 Quando envio requisição para cadastrar o paciente
 Então o paciente deve ser cadastrado com sucesso

 Cenário: Buscar pacientes por cpf
 Quando envio requisição para buscar pacientes por cpf
 Então a resposta deve conter o paciente buscado por cpf

 Cenário: Buscar pacientes por nome
 Quando envio requisição para buscar pacientes por nome
 Então a resposta deve conter os pacientes buscados por nome

 Cenário: Atualizar um paciente
 Quando envio requisição para atualizar o paciente
 Então a resposta deve conter o paciente atualizado

 Cenário: Remover um paciente
 Quando envio requisição para remover o paciente
 Então o paciente deve ser removido