<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="${pageContext.request.contextPath }/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/gallery.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/bootstrap/js/bootstrap.min.js"></script>
<title>Mysite</title>
</head>
<style>
.max-small {
	width: auto;
	height: auto;
	max-width: 150px;
	max-height: 150px;
}
</style>
<body>
	<div id="container">
		<!-- header -->
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>

		<!-- navigation -->
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>

		<div id="content">
			<div id="c_box">
				<div id="gallery">
					<h2>갤러리</h2>
					<c:if test="${sessionScope.authUser.no != null }">
						<input id="btnImgPop" class="btn btn-primary" type="button" value="이미지등록">
					</c:if>
					<ul id="imgPicture">
					</ul>
				</div>
				<!-- /gallery -->
			</div>
			<!-- /c_box -->
		</div>
		<!-- /content -->

		<!-- footer -->
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>

	</div>
	<!-- /container -->



	<!-- 이미지등록 팝업(모달)창 -->
	<div class="modal fade" id="delPop">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">이미지등록</h4>
				</div>
					<div class="modal-body">
						<div class="formgroup">
							<label>코멘트작성</label><br> 
							 <input type="hidden" name="user_no" id="user_no" value="${sessionScope.authUser.no}">
							 <input type="text" name="comments" id="comments"><br>
						</div>
						<div class="formgroup">
							<label>이미지선택</label><br> 
							<input type="file" name="file" value="" id="file"> <br>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
						<button type="submit" class="btn btn-primary" id="btnImgAdd">등록</button>
					</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->



	<!-- 이미지보기 팝업(모달)창 -->
	<div class="modal fade" id="viewPop">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">이미지보기</h4>
				</div>
				<div id="view" class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
					<c:if test="${sessionScope.authUser.no != null }">
					<button type="button" class="btn btn-danger" id="btnDel">삭제</button>
					</c:if>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->


</body>

<script type="text/javascript">
	
	//list 출력 
	$(document).ready(function() {
		$.ajax({
			url : "${pageContext.request.contextPath }/file/ajax_list",
			type : "post",
			/*contentType : "application/json",*/

			dataType : "json",
			success : function(list) {
				/*성공시 처리해야될 코드 작성*/
				console.log(list);
				var no = list[list.length - 1].no
				for (var i = 0; i < list.length; i++) {
					console.log(list.length);
					render(list[i], "down");
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});

	/* 이미지등록 팝업(모달)창 띄우기*/
	$("#btnImgPop").on("click", function() {
		$("#delPop").modal();

		
		$("#btnImgAdd").on("click", function() {
			var file = $("[name=file]")[0].files[0];
			console.log(file);
			var comments = $("[name=comments]").val();
			console.log(comments);
			var user_no = $("[name=user_no]").val();
			console.log(user_no);
			
			var formData = new FormData();
			formData.append('file', $("[name=file]")[0].files[0]);
			formData.append('comments', $("[name=comments]").val());
			formData.append('user_no', $("[name=user_no]").val());
			
			
			$.ajax({
				url : "${pageContext.request.contextPath }/file/upload",
				type : "post",
				data : formData,
				dataType : "json",
				processData: false, 
				contentType: false,
				success : function(fileVo) {
					if(fileVo != null){
						render(fileVo,"down");
						$("#delPop").modal("hide");
						alert("업로드 성공!");
					}else alert("업로드 실패 ");
				},
				error : function(XHR, status, error) {
					console.error(status + " : " + error);
				}
			});
			
		})
	});

	// 클릭시 사진이 화면에 나오게 하는  
	$("#imgPicture").on("click", "li", function() {
		var $this = $(this);
		console.log($this);
		var no = $this.data("no");
		console.log(no);
		$.ajax({
			url : "${pageContext.request.contextPath }/file/ajax_one",
			type : "post",
			/*contentType : "application/json",*/
			data : {no : no},
			dataType : "json",
			success : function(fVo) {
				/*성공시 처리해야될 코드 작성*/
				if (fVo != null) {
					console.log(fVo);
					eider(fVo);
					$("#viewPop").modal();
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});
	
	//삭제할때 
	$("#btnDel").on("click", function() {
		console.log("aaab");
		var no = $("#no_delete").val();
		console.log(no);
		var user_no = "${sessionScope.authUser.no}";
		console.log(user_no);
		$.ajax({
			url : "${pageContext.request.contextPath }/file/delete",
			type : "post",
			/*contentType : "application/json",*/
			data : {
				no : no,
				user_no : user_no
			},
			dataType : "json",
			success : function(list) {
				/*성공시 처리해야될 코드 작성*/
				
				if(list == true){
				$("#viewPop").modal("hide");			
				$("#no"+no).remove();
				
				}else{ console.log("false")
				alert("다른 사용자의 게시글입니다.")
				};
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});

	function render(fileVo, updown) {
		var str = "";
		str += "		<li id= no"+fileVo.no+" data-no="+fileVo.no+">";
		str += "			<div>";
		str += "				<img src =${pageContext.request.contextPath }/upload/"+fileVo.saveName+">";
		str += "			</div>";
		str += "		</li>";

		if (updown == "up") {
			$("#imgPicture").prepend(str);
		} else if (updown == "down") {
			$("#imgPicture").append(str);
		} else {
			console.log("updown 오류 ");
		}
	}

	function eider(fileVo) {
		
		var str = ""
		str += "<div id='viewImg' class='formgroup'>"
		str += "<img class='max-small' src =${pageContext.request.contextPath }/upload/"+fileVo.saveName+">"
		str += "</div>"
		str += "<div id='viewComments' class='formgroup'>"
		str += "<label>코멘트</label><br>"
		str += "<input type='hidden' name='no' id='no_delete' value="+fileVo.no+">"
		str += fileVo.comments
		str += "</div>"

		$("#view").html(str);
	}
	
</script>
</html>