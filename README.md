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
  model/        classes do domínio (Renda, Gasto, TipoPagamento, Fatura)
  repository/   acesso ao banco
  service/      regras de negócio (SaldoService, FaturaService)
  controller/   API REST (/api/rendas, /api/gastos, /api/saldo, /api/faturas)
  config/       configurações (CORS)
frontend/src/
  api.js        conversa com o backend
  App.jsx       tela principal
  RendaForm.jsx formulário de renda
  GastoForm.jsx formulário de gasto (débito/pix ou crédito)
  Fatura.jsx    fatura do cartão de crédito
```

## API

| Método | Rota | O que faz |
| --- | --- | --- |
| POST | /api/rendas | Cadastra uma renda (`descricao`, `valor`, `dataRecebimento`) |
| GET | /api/rendas | Lista as rendas, da mais recente para a mais antiga |
| POST | /api/gastos | Cadastra um gasto (`descricao`, `valor`, `dataGasto`, `tipo`: DEBITO ou CREDITO) |
| GET | /api/gastos | Lista os gastos, do mais recente para o mais antigo |
| GET | /api/saldo | Saldo = rendas - gastos no débito |
| GET | /api/faturas?mes=AAAA-MM | Fatura que vence no mês: compras no crédito do mês anterior (não descontam do saldo) |

## Testes

```powershell
cd backend
.\mvnw test
```
