// Único lugar do frontend que conversa com o backend.
// Usa o mesmo endereço que está na barra do navegador (localhost no PC, ou o IP
// do PC quando aberto no celular) e a porta 8080 do backend.
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
