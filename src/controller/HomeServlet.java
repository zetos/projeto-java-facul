package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoriaDAO;
import dao.ProdutoDAO;

@WebServlet("/Home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProdutoDAO daoProduto;
	private CategoriaDAO daoCategoria;

	public HomeServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		RequestDispatcher rd = null;
		
		String categoriaId = request.getParameter("categoriaId");

		try {
			daoCategoria = new CategoriaDAO();
			daoProduto = new ProdutoDAO();
			request.setAttribute("listarCategorias", daoCategoria.listarCategorias());
			if (categoriaId == null) {
				request.setAttribute("listarProdutos", daoProduto.listarProdutos());
			} else {
				request.setAttribute("listarProdutos", daoProduto.listarProdutos(Integer.parseInt(categoriaId)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		rd = request.getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
