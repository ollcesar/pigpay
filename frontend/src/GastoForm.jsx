import { useState } from 'react'

const hoje = () => new Date().toLocaleDateString('sv-SE')

export default function GastoForm({ aoSalvar }) {
  const [descricao, setDescricao] = useState('')
  const [valor, setValor] = useState('')
  const [dataGasto, setDataGasto] = useState(hoje())
  const [tipo, setTipo] = useState('DEBITO')
  const [salvando, setSalvando] = useState(false)

  async function enviar(evento) {
    evento.preventDefault()
    setSalvando(true)
    const deuCerto = await aoSalvar({
      descricao: descricao.trim(),
      valor: Number(valor),
      dataGasto,
      tipo,
    })
    setSalvando(false)
    if (deuCerto) {
      setDescricao('')
      setValor('')
      setDataGasto(hoje())
    }
  }

  return (
    <form className="cartao formulario" onSubmit={enviar}>
      <h2>Cadastrar gasto</h2>

      <label>
        Descrição
        <input
          placeholder="Ex.: Mercado"
          value={descricao}
          onChange={(e) => setDescricao(e.target.value)}
          required
        />
      </label>

      <label>
        Valor (R$)
        <input
          type="number"
          inputMode="decimal"
          step="0.01"
          min="0.01"
          placeholder="0,00"
          value={valor}
          onChange={(e) => setValor(e.target.value)}
          required
        />
      </label>

      <label>
        Data do gasto
        <input
          type="date"
          value={dataGasto}
          onChange={(e) => setDataGasto(e.target.value)}
          required
        />
      </label>

      <label>
        Forma de pagamento
        <select value={tipo} onChange={(e) => setTipo(e.target.value)}>
          <option value="DEBITO">Débito / Pix (desconta do saldo)</option>
          <option value="CREDITO">Crédito (vai para a fatura do mês seguinte)</option>
        </select>
      </label>

      <button type="submit" disabled={salvando}>
        {salvando ? 'Salvando...' : 'Salvar gasto'}
      </button>
    </form>
  )
}
