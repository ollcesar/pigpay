import { useState } from 'react'

// Data de hoje no formato AAAA-MM-DD, no fuso do usuário (o formato que o campo de data usa).
const hoje = () => new Date().toLocaleDateString('sv-SE')

export default function RendaForm({ aoSalvar }) {
  const [descricao, setDescricao] = useState('')
  const [valor, setValor] = useState('')
  const [dataRecebimento, setDataRecebimento] = useState(hoje())
  const [salvando, setSalvando] = useState(false)

  async function enviar(evento) {
    evento.preventDefault()
    setSalvando(true)
    const deuCerto = await aoSalvar({
      descricao: descricao.trim(),
      valor: Number(valor),
      dataRecebimento,
    })
    setSalvando(false)
    if (deuCerto) {
      setDescricao('')
      setValor('')
      setDataRecebimento(hoje())
    }
  }

  return (
    <form className="cartao formulario" onSubmit={enviar}>
      <h2>Cadastrar renda</h2>

      <label>
        Descrição
        <input
          placeholder="Ex.: Salário"
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
        Data de recebimento
        <input
          type="date"
          value={dataRecebimento}
          onChange={(e) => setDataRecebimento(e.target.value)}
          required
        />
      </label>

      <button type="submit" disabled={salvando}>
        {salvando ? 'Salvando...' : 'Salvar renda'}
      </button>
    </form>
  )
}
