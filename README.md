![CI](https://github.com/qatestercea-jpg/restassured-api-tests/actions/workflows/ci.yml/badge.svg)

# API Automation Test Suite

[![CI](https://github.com/qatestercea-jpg/restassured-api-tests/actions/workflows/ci.yml/badge.svg)](https://github.com/qatestercea-jpg/restassured-api-tests/actions/workflows/ci.yml)

## Descrição do projeto

Este repositório contém uma suíte de automação de testes de API em Java, projetada para validar endpoints REST de forma confiável e escalável. A suite usa RestAssured com JUnit 5, gera relatórios Allure e integra execução contínua com GitHub Actions.

A arquitetura do projeto foca em clareza e manutenção, com camadas separadas para:

- `client` — chamadas HTTP e mapeamento de endpoint
- `service` — lógica de negócio de testes e orquestração de fluxos
- `dto` — objetos de transferência de dados para payloads de requisição e resposta
- `factory` — geração de dados de teste dinâmicos e independentes
- `assertions` — validações de API reutilizáveis e padronizadas

## Tecnologias utilizadas

- Java 8
- Maven
- RestAssured
- JUnit 5
- Allure Report
- GitHub Actions

## Arquitetura em camadas

O projeto utiliza a seguinte estrutura de pastas:

```text
src/test/java/com/qa/
├── assertions/      # validações de API reutilizáveis
├── base/            # configuração global de testes RestAssured
├── builder/         # construtores de objetos de teste
├── client/          # implementação das requisições HTTP
├── dto/             # modelos de dados para requisição/resposta
├── factory/         # geração de dados dinâmicos para teste
├── service/         # orquestração de fluxos de API
└── tests/           # classes de teste JUnit 5
    ├── login/
    └── usuario/
```

## Características da suite

- Testes parametrizados via JUnit 5 para cobrir múltiplos cenários com menos código
- Independência entre testes: cada cenário cria seus próprios dados e não depende do estado existente da API
- Validações centralizadas em `ApiAssertions` para facilitar manutenção e legibilidade
- Geração de dados de teste dinâmica com `UsuarioFactory`
- Relatórios técnicos e visuais via Allure
- Pipeline de CI pronta para GitHub Actions

## Como executar os testes

Execute os testes localmente a partir do diretório do projeto:

```bash
mvn clean test
```

## Allure Report

Para gerar e visualizar o relatório Allure:

```bash
mvn allure:serve
```

Isso abre um servidor local com o relatório gerado a partir de `target/allure-results`.

## GitHub Actions / Pipeline CI

A integração contínua é configurada em `.github/workflows/ci.yml` e executa a suíte em eventos de:

- `push` na branch `main`
- `pull_request` direcionado para `main`

A pipeline garante que a validação de API seja executada automaticamente e que o repositório permaneça com qualidade confiável.

## Destaques para recrutadores

- Arquitetura em camadas com separação clara de responsabilidades
- Uso de padrões de teste automatizados e validações reutilizáveis
- Foco em estabilidade e independência de cenário
- Relatórios Allure para comunicação de resultados
- Pipeline de CI pronta para integração contínua

## Observações

- O projeto é ideal para demonstrar habilidades em automação de API, design de testes e integração contínua.
- Caso queira rodar localmente, basta ter Java e Maven instalados.
