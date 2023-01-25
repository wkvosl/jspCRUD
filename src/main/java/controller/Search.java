package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.data.BoardDTO;
import board.data.SearchDAO;


@WebServlet("/list")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			
			String searchTitle     = request.getParameter("t")  == ""? "" : request.getParameter("t");
			String searchUserName  = request.getParameter("u")	== ""? "" : request.getParameter("u");
			String searchFirstDate = request.getParameter("fd") == ""? "" : request.getParameter("fd");
			String searchLastDate  = request.getParameter("ld") == ""? "" : request.getParameter("ld");
			
			String basenum = request.getParameter("p");
			if(basenum == null ) basenum = "1";
			int baseNum = Integer.parseInt(basenum);
			
			int viewRow = 10; 
			int min = (baseNum-1) * viewRow ;
				if(min < 0 ) min = 0;
			int max = viewRow;
			
			SearchDAO dao = new SearchDAO();
			List<BoardDTO> searchlist = 
					dao.getSearchList(searchTitle, searchUserName, searchFirstDate, searchLastDate, min, max);
			
			int count = dao.getSearchCount(searchTitle, searchUserName, searchFirstDate, searchLastDate);
			int totalPage = Math.round(count/viewRow);
			
			request.setAttribute("boardlist", searchlist);
			request.setAttribute("viewRow", viewRow);
			request.setAttribute("baseNum", baseNum);
			request.setAttribute("TotalCount", count);
			request.setAttribute("TotalPage", totalPage);
			RequestDispatcher dis = request.getRequestDispatcher("WEB-INF/boardsearch.jsp");
			dis.forward(request, response);
		
			
			
		}

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			

			request.setCharacterEncoding("UTF-8"); //검색으로 들어온 input 키워드.. 한글깨짐현상 때문에
			doGet(request, response);
			

		}

	}