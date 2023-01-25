package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import board.data.BoardDAO;
import board.data.BoardDTO;

/**
 * Servlet implementation class reg
 */
@WebServlet("/create")
public class Create extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			RequestDispatcher dis = request.getRequestDispatcher("WEB-INF/boardcreate.jsp");
			dis.forward(request, response);
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//JAVA에서 deprecated되는 이유 : ServletContext에서도 있는걸..deprecated되는 중복으로 가지고 있어서 
		String realpath = request.getSession().getServletContext().getRealPath("/img/uploads/");
		int maxSize = 1024*1024*2; //2MB
		String enctype = "UTF-8";
		MultipartRequest multi = 
				new MultipartRequest(request, realpath, maxSize, enctype, new DefaultFileRenamePolicy());
		
		String referer = request.getHeader("Referer");
		String[] usertypeArr = multi.getParameterValues("usertype");
		
		BoardDTO createBoardArgument = new BoardDTO();
			createBoardArgument.setUsername(multi.getParameter("username"));
			createBoardArgument.setTitle(multi.getParameter("title"));
			createBoardArgument.setBoardtype(multi.getParameter("boardtype"));
			createBoardArgument.setBoardcategory(multi.getParameter("boardcategory"));
			createBoardArgument.setUsertype(String.join(",", usertypeArr));
			createBoardArgument.setContent(multi.getParameter("content"));
			createBoardArgument.setRealfilename(multi.getOriginalFileName("realfilename"));
			createBoardArgument.setSystemfilename(multi.getFilesystemName("realfilename"));
			

			BoardDAO dao = new BoardDAO();
			if(dao.createBoard(createBoardArgument) == 1) {
				response.sendRedirect("list");
				return;
			}else {
				response.sendRedirect(referer);
				return;
			}
	}

}
