<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<%@ include file="bootstrap_taglib.jsp" %>
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="css/all.css">
	<link rel="stylesheet" type="text/css" href="css/newWrite.css">
	<script src="js/newWrite.js"></script>
<title>Insert title here</title>
</head>
<body>

	<h1>조회</h1>
	<hr>
	
	<div id="all_body_div">
		
		<c:forEach items="${detail}" var ="detail">

<form action="filedown" method="post" id="filedownForm">
	<input type="hidden" name="list" value="${detail.bid }">
	<input type="hidden" name="realfilename" value="${detail.realfilename}">
	<input type="hidden" name="systemfilename" value="${detail.systemfilename }">
</form>
		
		<table id="newWriteTable">
			<tr>
				<th id="newWrite_th">구분(필수)</th>
				<td id="newWriteTable_td">
					${detail.boardtype }
				</td>
			</tr>
			<tr>
				<th>작성자(필수)</th>
				<td id="newWriteTable_td">
					${detail.username}
				</td>
			</tr>
			<tr>
				<th>분류(필수)</th>
				<td id="newWriteTable_td">
					${detail.boardcategory}
				</td>
			</tr>
			<tr>
				<th>고객유형</th>
				<td id="newWriteTable_td">
					${detail.usertype}
				</td>
				
			</tr>
			<tr>
				<th>제목(필수)</th>
				<td id="newWriteTable_td">
					${detail.title }
				</td>
			</tr>
			<tr>
				<th>내용(필수)</th>
				<td id="newWriteTable_td" class="textarea_height">
					${detail.content }
				</td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td id="newWriteTable_td">
<!--다운로드 링크-->
					<c:if test="${not empty detail.realfilename }">	
						<button form="filedownForm" class="filedown_but" title="다운로드 받기">
<%-- 							${empty detail.realfilename == true? "": detail.realfilename} --%>
							${detail.realfilename}
						</button>
						<button form="filedownForm" title="다운로드 받기" class="btn btn-secondary btn-sm">다운로드</button>
					</c:if>
				</td>
			</tr>
		</table>
		
		
		
			<div id="newWrite_button_div">
				<input id="btn_size" type="button" value="수정" onclick="location.href='modi.do?list=${detail.bid}'" class="btn btn btn-primary">
				<input id="btn_size" type="button" value="삭제" onclick="confirm('정말 삭제하시겠습니까?');" class="btn btn btn-primary">
				<input id="btn_size" type="button" value="목록" onclick="location.replace('list')" class="btn btn btn-primary">
			</div>
	</c:forEach>
	</div>

</body>
</html>