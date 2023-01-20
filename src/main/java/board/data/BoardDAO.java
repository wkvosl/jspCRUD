package board.data;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import board.DB.DBconnection;

public class BoardDAO {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	private String sql="";
	
	//모두 데리고 오기.
	public List<BoardDTO> getBoardList(int min, int max) {

		conn = DBconnection.getConnection();

		sql ="select bbb.* from (select bb.*, @rownum:=@rownum+1 rownum from ( "
				+ "				 select b.* from board_tbl b, (select @rownum:=0)r order by bid desc ) bb "
				+ "				 order by writedate desc) bbb "
				+ "              limit ?, ? ";
		
		
		
		List<BoardDTO> boardlist = new ArrayList<>();
		BoardDTO dto = null;
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, min);
			pstmt.setInt(2, max);
			pstmt.executeQuery();
			rs = pstmt.executeQuery();
			
				while(rs.next()) {
					dto = new BoardDTO();
						dto.setRownum(rs.getInt("rownum"));
						dto.setBid(rs.getInt("bid"));
						dto.setUsername(rs.getString("username"));
						dto.setTitle(rs.getString("title"));
						dto.setBoardtype(rs.getString("boardtype"));
						dto.setBoardcategory(rs.getString("boardcategory"));
						dto.setUsertype(rs.getString("usertype"));
						dto.setContent(rs.getString("content"));
						dto.setWritedate(rs.getString("writedate"));
						dto.setRealfilename("realfilename");
						dto.setHit(rs.getString("hit"));
						dto.setSystemfilename(rs.getString("systemfilename"));
					boardlist.add(dto);

				}
			rs.close();
			pstmt.close();
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return boardlist;
	}
	
	//게시판 생성.
	public int createBoard(String username, String title, String boardtype,
			String boardcategory, String usertype, String content, 
			String realfilename, String systemfilename) {
		
		conn = DBconnection.getConnection();
		
		sql ="insert into board_tbl "
				+ " (username, title, boardtype, boardcategory, usertype, content, "
				+ " writedate, realfilename, systemfilename) "
				+ " value (?, ?, ? ,? , ?, ?, now(), ?, ?) ";
		
		try {
		pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, title);
			pstmt.setString(3, boardtype);
			pstmt.setString(4, boardcategory);
			pstmt.setString(5, usertype);
			pstmt.setString(6, content);
			pstmt.setString(7, realfilename);
			pstmt.setString(8, systemfilename);
			pstmt.executeUpdate();
		
			pstmt.close();
			conn.close();
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	//게시판 상세보기
	public List<BoardDTO> detailBoard(int bid) {
		
		BoardDTO dto = null;
		List<BoardDTO> detail = new ArrayList<>();

		conn = DBconnection.getConnection();
		
		sql ="select * from board_tbl where bid = ?";
		
		try {
		pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bid);
			rs = pstmt.executeQuery();
		
			while(rs.next()) {
				dto = new BoardDTO();
				dto.setBid(rs.getInt("bid"));
				dto.setBoardtype(rs.getString("boardtype"));
				dto.setUsername(rs.getString("username"));
				dto.setBoardcategory(rs.getString("boardcategory"));
				dto.setUsertype(rs.getString("usertype"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setRealfilename(rs.getString("realfilename"));
				dto.setSystemfilename(rs.getString("systemfilename"));
				detail.add(dto);
			}
			
			pstmt.close();
			conn.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return detail;
	}
	
	//게시판 수정, 파일도 새로 업로드 될때
	public int modiBoard(int bid, String boardtype, String boardcategory, String usertypeArr, 
			String title, String content, String realfilename, String systemfilename, String realpath, String systemfilename_copy) {

		
		conn = DBconnection.getConnection();
		
		//새로 업로드 되기 전의 업로드 파일명과 경로를 가지고 삭제함.
		String path = realpath;
		File file = new File(path+systemfilename_copy);
		if(file.exists()) file.delete();
		
		sql ="update board_tbl set boardtype = ?, boardcategory = ?, usertype = ?, title = ?, content = ?, realfilename = ?, systemfilename = ? where bid = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardtype);
			pstmt.setString(2, boardcategory);
			pstmt.setString(3, usertypeArr);
			pstmt.setString(4, title);
			pstmt.setString(5, content);
			pstmt.setString(6, realfilename);
			pstmt.setString(7, systemfilename);
			pstmt.setInt(8, bid);
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
			
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	//게시판 수정, 파일은 그대로 일때,
	public int modiBoardNullFile(int bid, String boardtype, String boardcategory, String usertypeArr, 
			String title, String content) {
		
		conn = DBconnection.getConnection();
		
		sql ="update board_tbl set boardtype = ?, boardcategory = ?, usertype = ?, title = ?, content = ? where bid = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardtype);
			pstmt.setString(2, boardcategory);
			pstmt.setString(3, usertypeArr);
			pstmt.setString(4, title);
			pstmt.setString(5, content);
			pstmt.setInt(6, bid);
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	//수정에서 파일명 옆에 있는 삭제버튼 누를때
	public boolean modiFileDel_but(int bid) {
		
		conn = DBconnection.getConnection();
		
		sql="update board_tbl set realfilename = null, systemfilename = null where bid = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bid);
			pstmt.executeUpdate();

			
			pstmt.close();
			conn.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void boardDel(int bid) {
		
		conn = DBconnection.getConnection();
		
		sql = "delete from board_tbl where bid = ?";
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bid);
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//조회 +1
	public void plusHit(int bid) {
		
		conn = DBconnection.getConnection();
		
		sql = "update board_tbl set hit = hit+1 where bid = ?";
		
		try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, bid);
		pstmt.executeUpdate();
		
		rs.close();
		pstmt.close();
		conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//전체 칼럼의 수
		public int getTotalCount() {
			
			conn = DBconnection.getConnection();
			
			int total = 0;
			
			sql = "select count(bid) count from board_tbl";
			
			try {
				
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				total = rs.getInt("count");
			}
			
			rs.close();
			stmt.close();
			conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			return total;
		}

		
	
}
