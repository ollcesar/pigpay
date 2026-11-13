# PigPay

App de controle de gastos pessoais, para pessoas comuns que querem organizar o salário e os gastos.
Projeto da disciplina de Processo de Desenvolvimento de Software (Scrum com Jira e GitHub).

## Tecnologias

- **Backend:** Java 21, Spring Boot, Spring Data JPA, banco H2 (arquivo local)
- **Frontend:** React com Vite, pensado para tela de celular

## Como rodar

Use dois terminais ao mesmo tempo.

**Backend (porta 8080):**

```powershell
cd backend
.\mvnw spring-boot:run
```

**Frontend (porta 5173):**

```powershell
cd frontend
npm install
npm run dev
```

Abra o endereço que o frontend mostrar (normalmente http://localhost:5173).

## Estrutura

```
backend/src/main/java/com/pigpay/backend/
  model/        classes do domínio (Renda)
  repository/   acesso ao banco
  controller/   API REST (/api/rendas)
  config/       configurações (CORS)
frontend/src/
  api.js        conversa com o backend
  App.jsx       tela principal
  RendaForm.jsx formulário de renda
```

## API

| Método | Rota | O que faz |
| --- | --- | --- |
| POST | /api/rendas | Cadastra uma renda (`descricao`, `valor`, `dataRecebimento`) |
| GET | /api/rendas | Lista as rendas, da mais recente para a mais antiga |

## Testes

```powershell
cd backend
.\mvnw test
```
