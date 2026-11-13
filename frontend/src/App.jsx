import { useEffect, useState } from 'react'
import { cadastrarRenda, listarRendas } from './api'
import RendaForm from './RendaForm'

const moeda = new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' })

// "2026-11-05" -> "05/11/2026" (sem usar Date, para não sofrer com fuso horário)
function formatarData(iso) {
  const [ano, mes, dia] = iso.split('-')
  return `${dia}/${mes}/${ano}`
}

export default function App() {
  const [rendas, setRendas] = useState([])
  const [erro, setErro] = useState('')

  async function carregar() {
    try {
      setRendas(await listarRendas())
      setErro('')
    } catch {
      setErro('Não consegui falar com o backend. Ele está rodando na porta 8080?')
    }
  }

  // Roda uma vez, quando a tela abre.
  useEffect(() => {
    carregar()
  }, [])

  // Devolve true se salvou, para o formulário saber se pode limpar os campos.
  async function salvar(renda) {
    try {
      await cadastrarRenda(renda)
      await carregar()
      return true
    } catch {
      setErro('Não consegui salvar a renda. Confira os dados e se o backend está rodando.')
      return false
    }
  }

  const total = rendas.reduce((soma, renda) => soma + Number(renda.valor), 0)

  return (
    <main>
      <h1>PigPay</h1>

      {erro && <p className="erro">{erro}</p>}

      <RendaForm aoSalvar={salvar} />

      <section className="cartao">
        <h2>Rendas cadastradas</h2>
        <p className="total">
          Total: <strong>{moeda.format(total)}</strong>
        </p>

        {rendas.length === 0 ? (
          <p className="vazio">Nenhuma renda cadastrada ainda.</p>
        ) : (
          <ul className="lista">
            {rendas.map((renda) => (
              <li key={renda.id}>
                <div>
                  <span className="descricao">{renda.descricao}</span>
                  <span className="data">{formatarData(renda.dataRecebimento)}</span>
                </div>
                <span className="valor">{moeda.format(Number(renda.valor))}</span>
              </li>
            ))}
          </ul>
        )}
      </section>
    </main>
  )
}
