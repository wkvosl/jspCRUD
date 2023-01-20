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
		let input_file = document.getElementById("input_file");
		const modal = document.getElementById("modal");
		let input_file_span = document.getElementById('span');

//	콘솔에 오류가 떠서 input_file이 null이 아닐때로 바꿈
		if(input_file !== null){ 
			input_file.addEventListener("change", ()=>{
				const maxsize = 2*1024*1024;
				const filesize = input_file.files[0].size;
				
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
