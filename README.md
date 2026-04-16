[![CI](https://github.com/qatestercea-jpg/restassured-api-tests/actions/workflows/ci.yml/badge.svg)](https://github.com/qatestercea-jpg/restassured-api-tests/actions/workflows/ci.yml)

# API Test Automation Framework (RestAssured + JUnit 5 + Allure)

## Descrição do projeto

Suíte de automação de API em Java que executa validação de endpoints REST com RestAssured e JUnit 5. O projeto produz relatórios Allure e integra execução contínua via GitHub Actions.

Os testes verificam comportamento funcional de endpoints de usuário, incluindo payloads, autenticação e resposta de contrato.

## O que é validado

- criação, listagem e deleção de usuários
- cenários positivos e negativos
- validação de DTOs (serialização e desserialização)
- testes de autenticação

## Comportamento observado

A automação identifica que a API permite deleção de usuários sem autenticação e também aceita token inválido para operação de exclusão.

## Tecnologias utilizadas

- Java 8
- Maven
- RestAssured
- JUnit 5
- Allure Report
- GitHub Actions

## Arquitetura em camadas

- `client`: implementação das chamadas HTTP e configuração de endpoints
- `service`: orquestração dos fluxos de teste e montagem de cenários
- `dto`: modelos de requisição e resposta para serialização e desserialização
- `factory`: geração dinâmica de dados de teste
- `assertions`: validações reutilizáveis de resposta e contrato de API
- `tests`: casos de teste organizados por área funcional

## Características da suíte

- testes independentes: cada caso de teste executa sem dependência de estado de outros testes
- dados dinâmicos: payloads gerados em tempo de execução para reduzir acoplamento
- reutilização: componentes de cliente e assertions compartilhados entre cenários
- cobertura de cenários: inclui casos positivos, negativos, autenticação e validação de DTOs

## Como executar

```bash
git clone https://github.com/qatestercea-jpg/restassured-api-tests.git
cd restassured-api-tests
mvn clean test
```

## Allure

Após a execução dos testes, gere o relatório local com:

```bash
allure serve target/allure-results
```

## CI

O repositório utiliza GitHub Actions para executar `mvn clean test` em cada push. Os resultados do Allure são configurados como artifacts do workflow, permitindo download e análise do conjunto de resultados após a execução.

## Sobre este projeto

Projeto de automação de testes de API focado em verificação técnica de endpoints REST, com validação de comportamento, autenticação e estrutura de dados.
