[![CI](https://github.com/qatestercea-jpg/restassured-api-tests/actions/workflows/ci.yml/badge.svg)](https://github.com/qatestercea-jpg/restassured-api-tests/actions/workflows/ci.yml)

# API Test Automation Framework (RestAssured + JUnit 5 + Allure)

## Descrição

Suíte de automação de testes de API em Java para validação de endpoints REST.

O projeto utiliza RestAssured para execução das requisições, JUnit 5 para organização dos testes, Allure para geração de relatórios e GitHub Actions para integração contínua.

A estrutura foi organizada em camadas para separar responsabilidades e facilitar manutenção e evolução dos testes.

---

## O que é validado

- Criação de usuários com dados válidos e inválidos
- Listagem de usuários com validação de resposta
- Exclusão de usuários com verificação de consistência
- Testes de autenticação (token inválido e ausência de token)
- Validação de contrato da API com JSON Schema

---

## Arquitetura

```
tests → service → client → API
             ↑
      factory / dto / assertions
```

### Camadas

- **client**: responsável pelas chamadas HTTP
- **service**: encapsula fluxos de negócio dos testes
- **factory**: geração de dados dinâmicos
- **dto**: representação de request e response
- **assertions**: validações reutilizáveis
- **tests**: cenários organizados por domínio

---

## Tecnologias

- Java
- Maven
- RestAssured
- JUnit 5
- Allure Report
- GitHub Actions

---

## Execução dos testes

```bash
mvn clean test
```

---

## Relatório Allure

```bash
allure serve target/allure-results
```

O relatório apresenta:

- status dos testes
- requisições e respostas
- tempo de execução
- detalhes de falhas

---

## Integração Contínua

Pipeline configurada com GitHub Actions:

- execução automática em push e pull request
- execução da suíte de testes
- geração de relatórios Allure como artifacts

---

## Observações

Durante a execução dos testes foi identificado que a API permite exclusão de usuários sem autenticação e com token inválido, comportamento que pode representar falha de segurança.

---

## Objetivo

Demonstrar organização, reutilização de código e aplicação de boas práticas em automação de testes de API com foco em cenários reais.
