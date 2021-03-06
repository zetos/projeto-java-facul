package controller.adm.produto;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoriaDAO;
import dao.ProdutoDAO;
import util.Imagem;

@WebServlet("/ProdutoListar")
public class ListarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoriaDAO daoCategoria;
	private ProdutoDAO daoProduto;

	public ListarServlet() throws Exception {
		daoCategoria = new CategoriaDAO();
		daoProduto = new ProdutoDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		RequestDispatcher rd = null;

		try {
			Imagem img = new Imagem();
			request.setAttribute("listarCategorias", daoCategoria.listarCategorias());
			request.setAttribute("listarProdutos", daoProduto.listarProdutos());
			request.setAttribute("listarImagens", img.getImagens());
		} catch (Exception e) {
			e.printStackTrace();
		}

		rd = request.getRequestDispatcher("adm/produto/produtos.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
