import { useEffect, useState } from 'react'
import {
  cadastrarGasto,
  cadastrarRenda,
  consultarFatura,
  consultarSaldo,
  listarGastos,
  listarRendas,
} from './api'
import Fatura from './Fatura'
import GastoForm from './GastoForm'
import RendaForm from './RendaForm'

const moeda = new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' })

function formatarData(iso) {
  const [ano, mes, dia] = iso.split('-')
  return `${dia}/${mes}/${ano}`
}

function proximoMes() {
  const hoje = new Date()
  const ano = hoje.getMonth() === 11 ? hoje.getFullYear() + 1 : hoje.getFullYear()
  const mes = ((hoje.getMonth() + 1) % 12) + 1
  return `${ano}-${String(mes).padStart(2, '0')}`
}

export default function App() {
  const [rendas, setRendas] = useState([])
  const [gastos, setGastos] = useState([])
  const [saldo, setSaldo] = useState(0)
  const [mesFatura, setMesFatura] = useState(proximoMes())
  const [fatura, setFatura] = useState(null)
  const [erro, setErro] = useState('')

  async function carregar() {
    try {
      const [novasRendas, novosGastos, novoSaldo] = await Promise.all([
        listarRendas(),
        listarGastos(),
        consultarSaldo(),
      ])
      setRendas(novasRendas)
      setGastos(novosGastos)
      setSaldo(novoSaldo)
      setErro('')
    } catch {
      setErro('Não consegui falar com o backend. Ele está rodando na porta 8080?')
    }
  }

  async function carregarFatura(mes) {
    try {
      setFatura(await consultarFatura(mes))
    } catch {
      setErro('Não consegui carregar a fatura. O backend está rodando na porta 8080?')
    }
  }

  useEffect(() => {
    carregar()
  }, [])

  useEffect(() => {
    carregarFatura(mesFatura)
  }, [mesFatura])

  async function salvarRenda(renda) {
    try {
      await cadastrarRenda(renda)
      await carregar()
      return true
    } catch {
      setErro('Não consegui salvar a renda. Confira os dados e se o backend está rodando.')
      return false
    }
  }

  async function salvarGasto(gasto) {
    try {
      await cadastrarGasto(gasto)
      await carregar()
      await carregarFatura(mesFatura)
      return true
    } catch {
      setErro('Não consegui salvar o gasto. Confira os dados e se o backend está rodando.')
      return false
    }
  }

  const totalRendas = rendas.reduce((soma, renda) => soma + Number(renda.valor), 0)

  return (
    <main>
      <h1>PigPay</h1>

      {erro && <p className="erro">{erro}</p>}

      <section className="cartao saldo">
        <span className="saldo-titulo">Saldo disponível</span>
        <strong className={saldo < 0 ? 'saldo-valor negativo' : 'saldo-valor'}>
          {moeda.format(saldo)}
        </strong>
      </section>

      <RendaForm aoSalvar={salvarRenda} />

      <section className="cartao">
        <h2>Rendas cadastradas</h2>
        <p className="total">
          Total: <strong>{moeda.format(totalRendas)}</strong>
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

      <GastoForm aoSalvar={salvarGasto} />

      <Fatura mes={mesFatura} aoMudarMes={setMesFatura} fatura={fatura} />

      <section className="cartao">
        <h2>Gastos cadastrados</h2>

        {gastos.length === 0 ? (
          <p className="vazio">Nenhum gasto cadastrado ainda.</p>
        ) : (
          <ul className="lista">
            {gastos.map((gasto) => (
              <li key={gasto.id}>
                <div>
                  <span className="descricao">{gasto.descricao}</span>
                  <span className="data">{formatarData(gasto.dataGasto)}</span>
                </div>
                <span className={gasto.tipo === 'CREDITO' ? 'valor credito' : 'valor gasto'}>
                  {gasto.tipo === 'CREDITO' ? 'Crédito ' : '- '}
                  {moeda.format(Number(gasto.valor))}
                </span>
              </li>
            ))}
          </ul>
        )}
      </section>
    </main>
  )
}
