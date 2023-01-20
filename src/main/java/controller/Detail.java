package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.data.BoardDAO;
import board.data.BoardDTO;


@WebServlet("/detail")
public class Detail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String id = request.getParameter("list");
		int bid = Integer.parseInt(id);
		
		BoardDAO dao = new BoardDAO();
		List<BoardDTO> detail = dao.detailBoard(bid);
		
		dao.plusHit(bid);
		
		request.setAttribute("detail", detail);
		RequestDispatcher dis = request.getRequestDispatcher("/WEB-INF/boarddetail.jsp");
		dis.forward(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
	}

}
