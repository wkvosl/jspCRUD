package controller;

import java.io.IOException;
import java.util.List;

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


@WebServlet("/modi.do")
public class Modi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("list");
		int bid = Integer.parseInt(id);
		
		BoardDAO dao = new BoardDAO();
		List<BoardDTO> detail = dao.detailBoard(bid);

		request.setAttribute("detail", detail);
		RequestDispatcher dis = request.getRequestDispatcher("/WEB-INF/boardmodi.jsp");
		dis.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String realpath = request.getSession().getServletContext().getRealPath("/img/uploads/");
		
		int maxSize = 1024*1024*5;
		String enctype = "UTF-8";
		
		MultipartRequest multi = null;
		multi = new MultipartRequest(request, realpath, maxSize, enctype, new DefaultFileRenamePolicy());
		
		String id = multi.getParameter("list");
		int bid = Integer.parseInt(id);
		
		String title = multi.getParameter("title");
		String boardtype = multi.getParameter("boardtype");
		String boardcategory = multi.getParameter("boardcategory");
		String[] usertype = multi.getParameterValues("usertype");
		String content = multi.getParameter("content");
		
		//저장된 파일이 있을경우 : 그냥 수정 => 기존 파일이 존재해야함.
		String realfilename = multi.getOriginalFileName("realfilename"); //받은 파일명
		String systemfilename = multi.getFilesystemName("realfilename"); //서버에 저장될 파일명
		
		//폼에서 부터 가져온, 전의 업로드 파일명
		String systemfilename_copy = multi.getParameter("systemfilename_copy");
		
		//고객유형을 배열로 받아 한 문자열로 저장.
		String usertypeArr = String.join(",", usertype);
		
		String msg="";
		String referer = request.getHeader("Referer");

		BoardDAO dao = new BoardDAO(); 
		if(realfilename == null) {
			if(dao.modiBoardNullFile(bid, boardtype, boardcategory, usertypeArr, title, content)==1) {
				msg = "글 등록 완료";  //현재 js로 대체함.
				request.setAttribute("msg", msg);
				response.sendRedirect("detail?list="+bid);
				return;
			}else {
				msg = "글 등록 실패";
				request.setAttribute("msg", msg); 
				response.sendRedirect(referer);	
			}
		}else{
			if(dao.modiBoard(bid, boardtype, boardcategory, usertypeArr, title, content, realfilename, systemfilename, realpath, systemfilename_copy)==1) {
				msg = "글 등록 완료";  //현재 js로 대체함.
				request.setAttribute("msg", msg);
				response.sendRedirect("detail?list="+bid);
				return;
			}else {
				msg = "글 등록 실패";
				request.setAttribute("msg", msg);
				response.sendRedirect(referer);
			}
		}
		
	}

}
