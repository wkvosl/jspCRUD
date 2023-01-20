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

/**
 * Servlet implementation class reg
 */
@WebServlet("/create")
public class Create extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public Create() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//			if()
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
		
		String username = multi.getParameter("username");
		String title = multi.getParameter("title");
		String boardtype = multi.getParameter("boardtype");
		String boardcategory = multi.getParameter("boardcategory");
		String[] usertype = multi.getParameterValues("usertype");
		//고객유형을 배열로 받아 한 문자열로 저장.
		String usertypeArr = String.join(",", usertype);
		String content = multi.getParameter("content");
		
		//파일 다루기
		String realfilename = multi.getOriginalFileName("realfilename"); //받은 파일명
		String systemfilename = multi.getFilesystemName("realfilename"); //서버에 저장될 파일명
		
		
		//확장자.
//		String[] extArr = realfilename.split("\\.");
//		for(int i=0; i < extArr.length; i++) {
//			String ext = extArr[extArr.length-1];
//		}

			BoardDAO dao = new BoardDAO();
			if(dao.createBoard(username, title, boardtype, boardcategory, usertypeArr, content, realfilename, systemfilename) == 1) {
				response.sendRedirect("list");
				return;
			}else {
				response.sendRedirect(referer);
				return;
			}
	}

}
