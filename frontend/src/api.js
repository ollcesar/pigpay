const API = `http://${window.location.hostname}:8080/api`

export async function listarRendas() {
  const resposta = await fetch(`${API}/rendas`)
  if (!resposta.ok) {
    throw new Error('Não foi possível carregar as rendas.')
  }
  return resposta.json()
}

export async function cadastrarRenda(renda) {
  const resposta = await fetch(`${API}/rendas`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(renda),
  })
  if (!resposta.ok) {
    throw new Error('Não foi possível salvar a renda.')
  }
  return resposta.json()
}

export async function listarGastos() {
  const resposta = await fetch(`${API}/gastos`)
  if (!resposta.ok) {
    throw new Error('Não foi possível carregar os gastos.')
  }
  return resposta.json()
}

export async function cadastrarGasto(gasto) {
  const resposta = await fetch(`${API}/gastos`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(gasto),
  })
  if (!resposta.ok) {
    throw new Error('Não foi possível salvar o gasto.')
  }
  return resposta.json()
}

export async function consultarSaldo() {
  const resposta = await fetch(`${API}/saldo`)
  if (!resposta.ok) {
    throw new Error('Não foi possível carregar o saldo.')
  }
  const dados = await resposta.json()
  return Number(dados.saldo)
}

export async function consultarFatura(mes) {
  const resposta = await fetch(`${API}/faturas?mes=${mes}`)
  if (!resposta.ok) {
    throw new Error('Não foi possível carregar a fatura.')
  }
  return resposta.json()
}
