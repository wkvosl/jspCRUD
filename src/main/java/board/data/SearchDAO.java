package board.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import board.DB.DBconnection;

public class SearchDAO {

	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	String sql="";
	


	//모두 데리고 오기. 
	public List<BoardDTO> getSearchList(
			String search_title, String search_username,
			String searchFirstDate, String searchLastDate,
			int min, int max) {

		List<BoardDTO> searchlist = new ArrayList<>();
		BoardDTO dto = null;
		
		conn = DBconnection.getConnection();
		
		if(search_title != null) {
			search_title = "%" + search_title.trim() + "%"; 
		}else {
			search_title = "%%";
		}
		
		if(search_username != null) {
			search_username = "%" + search_username.trim() + "%";
		}else {
			search_username ="%%";
		}
		
		//기본 검색 일자를 위한 날짜 불러오기
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		cal.add(cal.DATE, 1);
		String oneDayAfter = dateFormat.format(cal.getTime());
		
		if(searchFirstDate == "") {
			searchFirstDate = "2022-01-01";
		}
		if(searchLastDate == "") {
			searchLastDate = oneDayAfter;
		}
		
		sql ="select bbbb.* from (select bbb.*, @rownum:=@rownum+1 rownum from (select bb.* from ( "
				+ " select b.* from board_tbl b, (select @rownum:=0)r order by bid desc ) bb "
				+ " order by writedate desc ) bbb "
				+ " where title like ? and "
				+ " username like ? or "
				+ " writedate between "+searchFirstDate+" and "+searchLastDate 
				+ " )bbbb limit ?, ?"; 
		
		try {
			pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, search_title);
				pstmt.setString(2, search_username);
				pstmt.setInt(3, min);
				pstmt.setInt(4, max);
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
					searchlist.add(dto);
				}
		
				
			rs.close();
			pstmt.close();
			conn.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return searchlist;
	}
	
	
	//검색 칼럼의 수
			public int getSearchCount(String search_title, String search_username,
					String searchFirstDate, String searchLastDate) {
				
				
				conn = DBconnection.getConnection();
				
				if(search_title != null) {
					search_title = "%" + search_title.trim() + "%"; 
				}else {
					search_title = "%%";
				}
				
				if(search_username != null) {
					search_username = "%" + search_username.trim() + "%";
				}else {
					search_username ="%%";
				}
				
				//기본 검색 일자를 위한 날짜 불러오기
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				cal.add(cal.DATE, 1);
				String oneDayAfter = dateFormat.format(cal.getTime());
				
				if(searchFirstDate == "") {
					searchFirstDate = "2022-01-01";
				}
				if(searchLastDate == "") {
					searchLastDate = oneDayAfter;
				}
				
				int total = 0;
				
				sql ="select count(bid) count from (select bb.* from ( "
						+ " select b.* from board_tbl b, (select @rownum:=0)r order by bid desc ) bb "
						+ " order by writedate desc ) bbb "
						+ " where title like ? and "
						+ " username like ? or "
						+ " writedate between "+searchFirstDate+" and "+searchLastDate ;
				
				try {
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, search_title);
					pstmt.setString(2, search_username);
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						total = rs.getInt("count");
					}
					
					rs.close();
					pstmt.close();
					conn.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
				return total;
			}
}
