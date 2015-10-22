package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Categoria;
import bean.Produto;
import util.ConnectionFactory;

public class ProdutoDAO {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	private CategoriaDAO categoriaDAO;
	
	public ProdutoDAO() throws Exception {
		try {
			this.conn = ConnectionFactory.getConnection();
		} catch (Exception e) {
			throw new Exception("erro: \n" + e.getMessage(), e);
		}
	}

	public void salvar(Produto produto) throws Exception {
		if (produto == null)
			throw new Exception("O valor passado nao pode ser nulo");

		try {
			String SQL = "INSERT INTO produto (nome, descricao, preco, categoriaId) values (?, ?, ?, ?)";
			ps = conn.prepareStatement(SQL);
			ps.setString(1, produto.getNome());
			ps.setString(2, produto.getDescricao());
			ps.setString(3, produto.getPreco());
			ps.setInt(4, produto.getCategoria().getCategoriaId());
			ps.executeUpdate();
		} catch (SQLException sqle) {
			throw new Exception("Erro ao inserir dados " + sqle, sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}
	}
	
	public void atualizar(Produto produto) throws Exception {
		if (produto == null)
			throw new Exception("O valor passado nao pode ser nulo");
		
		try {
			String SQL = "UPDATE produto set nome=?, descricao=?, preco=?, categoriaId=? WHERE produtoId=?";
			ps = conn.prepareStatement(SQL);	
			ps.setString(1, produto.getNome());
			ps.setString(2, produto.getDescricao());
			ps.setString(3, produto.getPreco());
			ps.setInt(4, produto.getCategoria().getCategoriaId());
			ps.setInt(5, produto.getProdutoId());
			ps.executeUpdate();
		} catch (SQLException sqle) {
			throw new Exception("Erro ao alterar dados " + sqle, sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}
	}

	public void excluir(Produto produto) throws Exception {
		if (produto == null)
			throw new Exception("O valor passado nao pode ser nulo");
		
		try {
			String SQL = "DELETE FROM produto WHERE produtoId=?";
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, produto.getProdutoId());
			ps.executeUpdate();
		} catch (SQLException sqle) {
			throw new Exception("Erro ao excluir dados " + sqle, sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}
	}
	
	public Produto procurar(int produtoId) throws Exception {
		try {
			categoriaDAO = new CategoriaDAO();
			Produto produto = new Produto();
			String SQL = "SELECT * FROM produto WHERE produtoId=?";
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, produtoId);
			rs = ps.executeQuery();
			if (rs.next()) {
				produto.setProdutoId(produtoId);
				produto.setNome(rs.getString(2));
				produto.setDescricao(rs.getString(3));
				produto.setPreco(rs.getString(4));
				produto.setCategoria(categoriaDAO.procurar(rs.getInt(5)));
			}
			return produto;
		} catch (SQLException sqle) {
			throw new Exception(sqle);	
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
	}

	public List<Produto> listarProdutos() throws Exception {
		try {
			categoriaDAO = new CategoriaDAO();
			ps = conn.prepareStatement("SELECT * FROM produto");
			rs = ps.executeQuery();
			List<Produto> list = new ArrayList<Produto>();
			while (rs.next()) {
				int produtoId = rs.getInt(1);
				String nome = rs.getString(2);
				String descricao = rs.getString(3);
				String preco = rs.getString(4);
				Categoria categoria = categoriaDAO.procurar(rs.getInt(5));
				list.add(new Produto(produtoId, nome, descricao, preco, categoria));
			}
			return list;
		} catch (SQLException sqle) {
			throw new Exception(sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
	}
	
	public List<Produto> listarProdutosCategoria(int categoriaId) throws Exception {
		try {
			categoriaDAO = new CategoriaDAO();
			ps = conn.prepareStatement("SELECT * FROM produto WHERE categoriaId=?");
			ps.setInt(1, categoriaId);
			rs = ps.executeQuery();
			List<Produto> list = new ArrayList<Produto>();
			while (rs.next()) {
				int produtoId = rs.getInt(1);
				String nome = rs.getString(2);
				String descricao = rs.getString(3);
				String preco = rs.getString(4);
				Categoria categoria = categoriaDAO.procurar(rs.getInt(5));
				list.add(new Produto(produtoId, nome, descricao, preco, categoria));
			}
			return list;
		} catch (SQLException sqle) {
			throw new Exception(sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
	}
}
