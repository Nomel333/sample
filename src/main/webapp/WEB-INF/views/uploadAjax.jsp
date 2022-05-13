<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.uploadResult {
	width: 100%;
	background-color: gray;
}

.uploadResult ul {
	display: flex;
	flex-flow: row;
	justify-content: center;
	align-items: center;
}

.uploadResult ul li {
	list-style: none;
	padding: 10px;
}

.uploadResult ul li img {
	width: 100px;
}

.bigPictureWrapper {
	position: absolute;
	display: none;
	justify-content: center;
	align-items: center;
	top: 0;
	width: 100%;
	height: 100%;
	background-color: gray;
	z-index: 100;
}

.bigPicture {
	position: relative;
	display: flex;
	justify-content: center;
	align-items: center;
}

.bigPicture img {
	width: 600px;
}

.uploadResult ul li span{
	color: white;
}
</style>
</head>
<body>
	<h1>파일 업로드 ajax로 해보기</h1>
	<div class="uploadDiv">
		<input type="file" name="uploadFile" multiple>
	</div>
	<button id="uploadBtn">파일 업로드</button>
	<div class="uploadResult">
		<ul>

		</ul>
	</div>
	<div class="bigPictureWrapper">
		<div class="bigPicture"></div>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script>
	// 원본 이미지를 보여주는 함수
	// jquery 로드 안된 상황에도 사용할 수 있도록 밖에다가 작성
	function showImage(filePath){
		// alert(filePath);
		$(".bigPictureWrapper").css("display","flex").show();
		
		// html 코드로 img 태그 삽입
		$(".bigPicture").html("<img src='/display?fileName=" + encodeURI(filePath) + "'>").animate({width:'100%',height:'100%'},1000);
		
		
	}

	$(".bigPictureWrapper").on("click", function(e){
		// 이미지 크기만 0으로 줄이기
		$(".bigPicture").stop().animate({width:'0%',height:'0%'},1000);
			
		// 1초 후에 숨기기
		setTimeout(function(e){
			$(".bigPictureWrapper").hide();
		}, 1000);
	})

	$(".uploadResult").on("click", "span", function(e) {
			
		// 삭제 대상 파일
		let targetFile = $(this).data("file");
			
		// 삭제 대상 파일의 타입
		let type = $(this).data("type");
			
		$.ajax({
			url : '/deleteFile',
			type : 'POST',
			data : {fileName : targetFile, type : type},
			dataType : 'text',
			success : function(result){
				alert(result);
			}
		})
			
	})
	
	
	
		$(document).ready(function() {
			// 확장자 제한
			// exe , 압축파일 제한
			// 파일 크기 제한
			// 확장자를 검사하는 제일 간단한 방법 -> string 함수를 이용
			// 파일 이름안에 exe zip alz sh 등이 포함되어 있는지 검사
			// includes
			// 우아한 방법 => 표현식(정규식)
			let regex = new RegExp("(.*?)\.(exs|sh|zip|alz)$");
			let maxSize = 5242880; // v파일 크기 5메가

			var cloneObj = $(".uploadDiv").clone();
			
			// 업로드 결과를 보여줄div 태그 안에 ul 태그 찾아오기
			var uploadResult = $(".uploadResult ul");
			
			// 우리가 찾아온 ul 태그 안에 업로드한 파일들의 정보를 동적으로 생성하여 추가
			function showUploadedFile(uArr){
				
				let uploadHtml = "";
				
				// 업로드 파일 하나-> li 태그 하나
				for(let i = 0; i < uArr.length; i++){
					if(uArr[i].image == false){
						// 이미지 파일이 아닌 경우
						// li 태그 앞에 파일 아이콘 붙이기
						let fileCallPath = encodeURIComponent("/" + uArr[i].uuid + "_" + uArr[i].fileName);
						
						uploadHtml += "<li><a href='/download?fileName=" + fileCallPath +"'><img src ='/resources/img/985381.png'>" + uArr[i].fileName + "</a><span data-file=\'" + fileCallPath + "\' data-type='file'> x </span></li>";
					} else{
						// 이미지 파일인 경우
						// img 태그를 추가하는데 이거를 올린 이미지로
						// uploadHtml += "<li>" + uArr[i].fileName + "</li>";
						// 첨부파일을 이미지로 올리면
						// 원본파일을 올리고, 추가로 섬네일 이미지가 생성됨
						// 섬네일 이미지는 파일 이름이
						// 업로드 경로 + s_ + uuid + 원래 파일 이름
						// 한글 이름은 문제가 생길 수 있어 encodinf 처리를 해준다
						let fileCallPath = encodeURIComponent("/s_" + uArr[i].uuid + "_" + uArr[i].fileName);
						
						// uploadHtml += "<li><img src='/display?fileName=" + fileCallPath + "'></li>"; 
						
						let originPath = uArr[i].uuid + "_" + uArr[i].fileName;
						// 역슬래쉬 두개 있는 문자열을 슬래쉬 하나로 모두 바꿔준다
						originPath = originPath.replace(new RegExp(/\\/g),"/");
						
						uploadHtml += "<li><a href=\"javascript:showImage(\'" + originPath + "\')\"><img src='/display?fileName=" + fileCallPath + "'></a><span data-file=\'" + fileCallPath + "\' data-type='image'> x </span></li>"; 
					}
				}
				uploadResult.append(uploadHtml);
				
			}

			// 파일을 검사하는 함수
			function checkFile(fileName, fileSize) {
				// 파일 크기 검사
				if (fileSize > maxSize) {
					alert("파일 최대 크기 초과");
					return false;
				}
				// 파일 확장자 검사
				if (regex.test(fileName)) {
					alert("해당 종류의 파일은 업로드 불가");
					return false;
				}
				// 두개 모두 통과했다면 return true;
				return true;
			}

			$("#uploadBtn").on("click", function(e) {
				// form 태그 없이  form을 만들어서 보내는 방법
				let formData = new FormData();
				// input 태그 가져오기
				let file = $("input[name='uploadFile']");
				// input 태그에서 file 가져오기
				let files = file[0].files;

				console.log(files);

				// formData에 파일 추가
				for (let i = 0; i < files.length; i++) {

					// 파일 검사 중에 falser가 나오면 파일 업로드를 중단한다
					if (checkFile(files[i].name, files[i].size) == false) {
						return false;
					}

					formData.append("uploadFile", files[i]);
				}
				// input type="file" 초기화 준비

				// ajax 요청 보내기
				// processData, contentType이 fasle인 이유
				// contentType : application.json
				// 파일을 실어서 보내는데 contentType이 file 형식으로 가야된다
				// false로 넣어줘야 contentType이 mulitipart로 설정되서 보내진다
				// processData : ajax로 요청을 보낼 때 data 속서이
				// jquery 내부적ㅇ로 querry string으로 변경을 해버린다 (데이터 처리)
				// 우리는 데이터 처리를 하지 말고 파일 전송의 경우 파일의 데이터를 그대로 보내야 하기 때문에
				// 처리하지 않고 false 값을 준다
				$.ajax({
					url : "/uploadAjaxAction",
					processData : false,
					contentType : false,
					data : formData,
					type : "POST",
					success : function(result) {
						console.log(result);
						
						showUploadedFile(result);
						
						// 요청 보내고 나서 성공하면 input file 초기화
						$(".uploadDiv").html(cloneObj.html());
					}
				})
			})
		})
	</script>
</body>
</html>