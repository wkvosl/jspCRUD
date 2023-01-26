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

@WebServlet("/create")
public class Create extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher dis = request.getRequestDispatcher("WEB-INF/boardcreate.jsp");
		dis.forward(request, response);
	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String realpath = request.getSession().getServletContext().getRealPath("/img/uploads/");
		int maxSize = 1024*1024*2; //2MB
		String enctype = "UTF-8";
		
		MultipartRequest multi = new MultipartRequest(request, realpath, maxSize, enctype, new DefaultFileRenamePolicy());
		
		String referer = request.getHeader("Referer");
		
		String username = multi.getParameter("username");
		String title = multi.getParameter("title");
		String boardtype = multi.getParameter("boardtype");
		String boardcategory = multi.getParameter("boardcategory");
		String[] usertypeArr = multi.getParameterValues("usertype");
		String usertype = String.join(",", usertypeArr);
		String content = multi.getParameter("content");
		String realfilename = multi.getOriginalFileName("realfilename"); 
		String systemfilename = multi.getFilesystemName("realfilename"); 
		
		BoardDTO createBoardArgument = new BoardDTO();
			createBoardArgument.setUsername(username);
			createBoardArgument.setTitle(title);
			createBoardArgument.setBoardtype(boardtype);
			createBoardArgument.setBoardcategory(boardcategory);
			createBoardArgument.setUsertype(usertype);
			createBoardArgument.setContent(content);
			createBoardArgument.setRealfilename(realfilename);
			createBoardArgument.setSystemfilename(systemfilename);
			
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
