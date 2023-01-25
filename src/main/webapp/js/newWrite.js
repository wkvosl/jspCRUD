/**
 * 
 */

 function checkinput(){
	
	if(document.newWrite_form.boardtype.value.length==0){
		newWrite_form.boardtype.focus();
		return false;
	}
	if(document.newWrite_form.username.value.length==0){
		newWrite_form.username.focus();
		return false;
	}
	if(!radio_1.checked && !radio_2.checked && !radio_3.checked){
		alert('분류를 하나 이상 선택하세요.');
		newWrite_form.radio_1.focus();
		return false;
	}
	if(document.newWrite_form.title.value.length==0){
		newWrite_form.title.focus();
		return false;
	}
	if(document.newWrite_form.content.value.length==0){
		newWrite_form.content.focus();
		return false;
	}
	
	alert('저장 하였습니다.');
	return true;
}


window.onload = function(){
	

		
}


function checkEXT(){
			
	const extOK = ["jpg", "jpeg", "png", "gif"];
	const filenameSplit = input_file.value.split('\\');
	let input_file_span = document.getElementById('span');

	for(var i=0; i<extOK.length; i++){
					let filename = filenameSplit[filenameSplit.length-1];
					let ext = filename.split('.');
					let extEnd = ext[ext.length-1];						
					if(extOK[i]!=extEnd){
						alert('지원하지 않은 확장자');
						input_file.value="";
						input_file_span.innerHTML="가능한 확장자는 "+extOK+" 입니다.";
						return false;
					}else{
						checkSIZE();
						return true;			
					}
				}
			
}


function checkSIZE(){
		let input_file = document.getElementById("input_file");
		const modal = document.getElementById("modal");
		let input_file_span = document.getElementById('span');

//	콘솔에 오류가 떠서 input_file이 null이 아닐때로 바꿈
		if(input_file != null){ 
			
			input_file.addEventListener("change", ()=>{
				
				
				const maxsize = 1*1024;
				let filesize = input_file.files[0].size ? input_file.files[0].size:0;
				
			
				//파일 크기 체크
				if(maxsize < filesize){
					modal.style.display = "flex";
					input_file.value="";
					return false;
				}else{
					let filesize_KB = Math.round(filesize/1024);
					input_file_span.innerHTML =  "&nbsp;("+filesize_KB+"&nbsp;KB)";
				}

			});
			
			
			const modal_btn = document.getElementById('btn');
			modal_btn.addEventListener("click", ()=>{
				modal.style.display = "none";
				input_file.focus();			
			});
		}
}