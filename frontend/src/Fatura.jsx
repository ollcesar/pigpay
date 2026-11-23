const moeda = new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' })

const NOMES_DOS_MESES = [
  'janeiro', 'fevereiro', 'março', 'abril', 'maio', 'junho',
  'julho', 'agosto', 'setembro', 'outubro', 'novembro', 'dezembro',
]

function nomeDoMes(aaaaMm) {
  const [ano, mes] = aaaaMm.split('-')
  return `${NOMES_DOS_MESES[Number(mes) - 1]}/${ano}`
}

function formatarData(iso) {
  const [ano, mes, dia] = iso.split('-')
  return `${dia}/${mes}/${ano}`
}

export default function Fatura({ mes, aoMudarMes, fatura }) {
  return (
    <section className="cartao">
      <h2>Fatura do cartão</h2>

      <label>
        Mês da fatura
        <input
          type="month"
          value={mes}
          onChange={(e) => e.target.value && aoMudarMes(e.target.value)}
        />
      </label>

      {fatura && (
        <>
          <p className="total">
            Fatura de {nomeDoMes(fatura.mes)}:{' '}
            <strong className="valor-fatura">{moeda.format(Number(fatura.total))}</strong>
          </p>
          <p className="dica">Compras no crédito do mês anterior entram aqui. O saldo não é descontado.</p>

          {fatura.gastos.length === 0 ? (
            <p className="vazio">Nenhum gasto nesta fatura.</p>
          ) : (
            <ul className="lista">
              {fatura.gastos.map((gasto) => (
                <li key={gasto.id}>
                  <div>
                    <span className="descricao">{gasto.descricao}</span>
                    <span className="data">{formatarData(gasto.dataGasto)}</span>
                  </div>
                  <span className="valor credito">{moeda.format(Number(gasto.valor))}</span>
                </li>
              ))}
            </ul>
          )}
        </>
      )}
    </section>
  )
}
