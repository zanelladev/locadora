package ifsc.tds.com.andre.artur.felipe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ifsc.tds.com.andre.artur.felipe.entity.Locacao;

public class LocacaoDAO implements DAO<Locacao> {

	private ClienteDAO clienteDAO;

	private FilmeDAO filmeDAO;

	public LocacaoDAO() {
		this.clienteDAO = new ClienteDAO();
		this.filmeDAO = new FilmeDAO();
	}

	@Override
	public Object get(Long id) {
		Locacao locacao = null;
		String sql = "select * from locacao where id = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		ResultSet rset = null;

		try {

			conexao = new Conexao().getConnection();
			stm = conexao.prepareStatement(sql);
			stm.setInt(1, id.intValue());
			rset = stm.executeQuery();

			while (rset.next()) { //
				locacao = new Locacao();

				locacao.setId(rset.getLong("id"));
				locacao.setDescricao(rset.getString("descricao"));
				locacao.setDataemprestimo(rset.getDate("dataemprestimo"));

				locacao.setCliente(this.clienteDAO.get(rset.getLong("cliente_id")));
				locacao.setFilme(this.filmeDAO.get(rset.getLong("filme_id")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (stm != null) {
					stm.close();
				}

				if (conexao != null) {
					conexao.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return locacao;
	}

	@Override
	public List<Locacao> getAll() {

		List<Locacao> locacoes = new ArrayList<Locacao>();
		String sql = "select * from locacao";

		Connection conexao = null;

		PreparedStatement stm = null;

		ResultSet rset = null;

		try {

			conexao = new Conexao().getConnection();
			stm = conexao.prepareStatement(sql);
			rset = stm.executeQuery();

			while (rset.next()) {
				Locacao locacao = new Locacao();

				locacao.setId(rset.getLong("id"));
				locacao.setDataemprestimo(rset.getDate("dataemprestimo"));

				locacao.setCliente(this.clienteDAO.get(rset.getLong("cliente_id")));
				locacao.setFilme(this.filmeDAO.get(rset.getLong("filme_id")));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (stm != null) {
					stm.close();
				}

				if (conexao != null) {
					conexao.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return locacoes;
	}

	@Override
	public int save(Locacao locacao) {
		String sql = "insert into locacao (descricao, dataemprestimo, cliente_id, filme_id)" + "values (?,?,?,?)";

		Connection conexao = null;

		PreparedStatement stm = null;
		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setString(1, locacao.getDescricao());
			stm.setDate(2, locacao.getDataemprestimo());
			stm.setLong(3, locacao.getCliente().getId());
			stm.setLong(4, locacao.getFilme().getId());

			stm.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (stm != null) {
					stm.close();
				}

				if (conexao != null) {
					conexao.close();
				}

				return 1;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public boolean update(Locacao locacao, String[] params) {

		String sql = "update locação set descricao = ?, dataemprestimo = ?, cliente_id = ? where id = ?";
		Connection conexao = null;

		PreparedStatement stm = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setString(1, locacao.getDescricao());
			stm.setDate(2, locacao.getDataemprestimo());
			stm.setLong(3, locacao.getCliente().getId());
			stm.setLong(4, locacao.getFilme().getId());

			stm.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (stm != null) {
					stm.close();
				}

				if (conexao != null) {
					conexao.close();
				}

				return true;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean delete(Locacao locacao) {

		String sql = "delete from locacão where id = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {

			conexao = new Conexao().getConnection();
			stm = conexao.prepareStatement(sql);

			stm.setLong(1, locacao.getId());
			stm.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (stm != null) {
					stm.close();
				}

				if (conexao != null) {
					conexao.close();
				}

				return true;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
