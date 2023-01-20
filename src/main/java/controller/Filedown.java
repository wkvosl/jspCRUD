package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Filedown
 */
@WebServlet("/filedown")
public class Filedown extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		String id = request.getParameter("list");
		int bid = Integer.parseInt(id);
		
		String realfilename = request.getParameter("realfilename");
		String systemfilename = request.getParameter("systemfilename");
		
		String realpath = request.getSession().getServletContext().getRealPath("/img/uploads/");

		File filedown = new File(realpath+systemfilename);
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(realfilename,"UTF-8"));
		
		FileInputStream fi = new FileInputStream(filedown);
		ServletOutputStream os = response.getOutputStream();
		
		int length;
		
		byte[] b = new byte[(int)filedown.length()];
		while((length = fi.read(b)) > 0) {
			os.write(b,0,length);
		}
		
		os.flush();
		
		os.close();
		fi.close();

		request.getRequestDispatcher("detail?list="+bid);
		
	}

}
