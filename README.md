![CI](https://github.com/qatestercea-jpg/restassured-api-tests/actions/workflows/ci.yml/badge.svg)

# API Automation Test Suite

[![CI](https://github.com/qatestercea-jpg/restassured-api-tests/actions/workflows/ci.yml/badge.svg)](https://github.com/qatestercea-jpg/restassured-api-tests/actions/workflows/ci.yml)

## Descrição do projeto

Este projeto é uma suíte de automação de testes de API desenvolvida em Java para validar endpoints REST usando o framework RestAssured. O objetivo é fornecer testes confiáveis de validação funcional e de integração para APIs, com relatórios automatizados e integração contínua no GitHub Actions.

## Tecnologias utilizadas

- Java
- Maven
- RestAssured
- JUnit 5
- Allure Report
- GitHub Actions

## Estrutura de pastas

```text
restassured-project/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/qa/
│   │           ├── App.java
│   │           ├── client/
│   │           └── constants/
│   └── test/
│       └── java/
│           └── com/qa/
│               ├── base/
│               ├── builder/
│               ├── client/
│               ├── constants/
│               ├── dto/
│               ├── service/
│               ├── tests/
│               │   ├── login/
│               │   └── usuario/
│               └── utils/
├── .github/
│   └── workflows/
│       └── ci.yml
└── target/
```

## Como executar os testes

Para rodar os testes localmente, execute o comando abaixo no diretório do projeto:

```bash
mvn clean test
```

## Como gerar relatório Allure

1. Execute os testes com o comando padrão:

```bash
mvn clean test
```

2. Gere o relatório Allure:

```bash
mvn allure:serve
```

3. O comando abrirá um servidor local com o relatório gerado.

> Observação: certifique-se de que o plugin Allure esteja configurado no `pom.xml` para gerar os resultados corretamente.

## Relatório Allure

Abaixo está um exemplo visual do relatório Allure gerado pela suíte de testes:

![Relatório Allure](docs/images/allure-report.png)

> Substitua a imagem pelo screenshot do relatório gerado localmente após executar `mvn allure:serve`.

## CI/CD

A pipeline de integração contínua está configurada em `.github/workflows/ci.yml` e executa os testes automaticamente em eventos de:

- `push` na branch `main`
- Pull request direcionado para a branch `main`

A workflow usa Java 17 e roda o comando:

```bash
mvn clean test
```

## Tipos de testes

O projeto contém os seguintes tipos de testes:

- Testes de API de autenticação e login
- Testes de API de usuário
- Testes de integração de endpoints REST

## Autor

- Desenvolvedor: QA Tester
- Projeto: Automação de testes de API com RestAssured
