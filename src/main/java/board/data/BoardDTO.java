package board.data;

public class BoardDTO {

	
	private int rownum;
	public BoardDTO(int rownum) {
		super();
		this.rownum = rownum;
	}



	public int getRownum() {
		return rownum;
	}



	public void setRownum(int rownum) {
		this.rownum = rownum;
	}



	private int bid;
	private String username;
	private String title;
	private String boardtype;
	private String boardcategory;
	private String usertype;
	private String content;
	private String writedate;
	private String realfilename;
	private String hit;
	private String systemfilename;


	public BoardDTO(int rownum, int bid, String username, String title, String boardtype, String boardcategory,
			String usertype, String content, String writedate, String realfilename, String hit, String systemfilename) {
		super();
		this.rownum = rownum;
		this.bid = bid;
		this.username = username;
		this.title = title;
		this.boardtype = boardtype;
		this.boardcategory = boardcategory;
		this.usertype = usertype;
		this.content = content;
		this.writedate = writedate;
		this.realfilename = realfilename;
		this.hit = hit;
		this.systemfilename = systemfilename;
	}


	public int getBid() {
		return bid;
	}



	public void setBid(int bid) {
		this.bid = bid;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getBoardtype() {
		return boardtype;
	}



	public void setBoardtype(String boardtype) {
		this.boardtype = boardtype;
	}



	public String getBoardcategory() {
		return boardcategory;
	}



	public void setBoardcategory(String boardcategory) {
		this.boardcategory = boardcategory;
	}



	public String getUsertype() {
		return usertype;
	}



	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public String getWritedate() {
		return writedate;
	}



	public void setWritedate(String writedate) {
		this.writedate = writedate;
	}



	public String getRealfilename() {
		return realfilename;
	}



	public void setRealfilename(String realfilename) {
		this.realfilename = realfilename;
	}



	public String getHit() {
		return hit;
	}



	public void setHit(String hit) {
		this.hit = hit;
	}



	public String getSystemfilename() {
		return systemfilename;
	}



	public void setSystemfilename(String systemfilename) {
		this.systemfilename = systemfilename;
	}



	public BoardDTO() {
		// TODO Auto-generated constructor stub
	}



	

	
}
