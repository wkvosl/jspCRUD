<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="bootstrap_taglib.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="css/all.css">
	<link rel="stylesheet" type="text/css" href="css/list.css">
	<script type="text/javascript" src="js/search.js"></script>
<title>Insert title here</title>
</head>
<body>

	<h1>목록</h1>
	<hr>
	<div id="all_body_div">

<!-- 검색 -->
		<div id="SearchDiv">
			<form action="list" method="post" id="SearchDiv_inForm" name="searchGetForm">&nbsp;

	    		 <span>제목</span> <input type="search" name="t" class="form-control" class="form-control">&nbsp;
	    		 <span>작성자</span> <input type="search" name="u" class="form-control">&nbsp;
	    		 <span>작성일</span> <input type="date" name="fd" class="form-control"> 
	    		 <span>~</span> <input type="date" name="ld" class="form-control">&nbsp;
	    		 
	    		 <input type="submit" value="검색" class="btn btn-primary">&nbsp;
	    		 
			 </form>
		</div>
		
<!-- 전체 게시글 수		 -->
		<p class="listCount_Ptag">Total : ${TotalCount} &nbsp; Page : ${baseNum} / ${TotalPage+1}</p>

<!-- 리스트 테이블 -->
		<div id="table_div">
			<table id="listTable" class="table table-hover">
				<tr id="listTable_Title_tr">
					<th id="th_1">번호</th>
					<th id="th_2">구분</th>
					<th id="th_3">제목</th>
					<th id="th_4">첨부</th>
					<th id="th_5">작성일</th>
					<th id="th_6">작성자</th>
					<th id="th_7">조회수</th>
				</tr>
						<c:forEach var="boardlist" items="${boardlist}">
				<tr>
							<td>${boardlist.rownum }</td>
							<td>${boardlist.boardtype }</td>
							<td><a href="detail?list=${boardlist.bid}">${boardlist.title }</a></td>
							<td>
								<c:if test="${not empty boardlist.systemfilename }">
									💾
								</c:if> 
							</td>
							<td>${boardlist.writedate }</td>
							<td>${boardlist.username }</td>
							<td>${boardlist.hit }</td>
							
						</c:forEach>
			</table>
		</div>
<!-- 등록버튼 -->
		<div id="listToWrite_Btn_div">
			<button onclick="location.href='create'" class="btn btn-primary">등록</button>
		</div>
		
<!-- 페이지 -->
		<div>
			
			<ul class="pagination">
				
					<li class="page-item" >
						<a href="list?p=1&t=${param.t}&u=${param.u}&fd=${param.fd}&=ld${param.ld}" class="page-link" onclick="click_A"> &laquo; </a>
					</li>
				
				<c:forEach var="i" begin="1" end="${TotalPage+1}" varStatus="num">
					<li class="page-item">
						<a href = "list?p=${i}&t=${param.t}&u=${param.u}&fd=${param.fd}&=ld${param.ld}"  class="page-link" onclick="click_A">
							${i}</a> &nbsp;
					</li>
				</c:forEach>
					
					<li class="page-item">
						<a href="list?p=${TotalPage+1 }&t=${param.t}&u=${param.u}&fd=${param.fd}&=ld${param.ld}"  class="page-link" onclick="click_A"> &raquo; </a>	
					</li>
					
			</ul>
			
		</div>
	</div>

</body>
</html>