<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="${pageContext.request.contextPath }/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">

<title>Mysite</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/bootstrap/js/bootstrap.min.js"></script>
<body>
	<div id="container">
			<!-- header -->
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>

		<!-- navigation -->
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		
		<!-- /header -->

		<!-- /navigation -->

		<div id="content">
			<div id="c_box">
				<div id="guestbook">
					<h2>방명록</h2>

					<div id="insert">
					
						<table>
							<tr>
								<td>이름</td>
								<td><input type="text" name="name"></td>
								<td>비밀번호</td>
								<td><input type="password" name="password"></td>
							</tr>
							<tr>
								<td colspan=4><textarea name="content" cols="75" rows="8"></textarea></td>
							</tr>
							<tr>
								<td colspan=4 align=right><input class ="insert" type="submit" VALUE=" 확인 "></td>
							</tr>
						</table>
					
					</div>
				<div id="gbList">
				
				
				
				
				
				
				
				</div>



				</div>
				<!-- /guestbook -->
			</div>
			<!-- /c_box -->
		</div>
		<!-- /content -->



	
		<!-- footer -->	
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		<!-- /footer -->
	</div>
	<!-- /container -->
	
	
	<!-- 삭제팝업(모달)창 -->
	<div class="modal fade" id="del-pop">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">방명록삭제</h4>
				</div>
				<div class="modal-body">
					<label>비밀번호</label>
					<input type="password" name="modalPassword" value="" id="modalPassword"><br>	
					<input type="text" name="modalNo" value="" id="modalNo"> <br>	
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					<button type="button" class="btn btn-danger" id="btn_del">삭제</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	
	
</body>

<script type="text/javascript">
    var no =0;

	
	$(document).ready(function(){
		$.ajax({
			url : "${pageContext.request.contextPath }/api/gb/ajaxlist",		
			type : "post",
			/*contentType : "application/json",*/
			
			dataType : "json",
			success : function(list){
						/*성공시 처리해야될 코드 작성*/
						console.log(list);	
		    			var no = list[list.length-1].no
						for(var i = 0 ; i<list.length; i++){
							console.log(list.length);
							render(list[i], "down");	
						}
					},
			error : function(XHR, status, error) {
					console.error(status + " : " + error);
					}
		});
		
	});
	
	//스크롤이 화면 제일 아래에 왔을때
	   
	$(window).scroll(function() {
	    if ($(window).scrollTop() == $(document).height() - $(window).height()) {
	    	console.log("down")
	    	$.ajax({
				url : "${pageContext.request.contextPath }/api/gb/pluslist",		
				type : "post",
				/*contentType : "application/json",*/
				data : {no:no},
				dataType : "json",
				success : function(list){
					/*성공시 처리해야될 코드 작성*/
					if(list.length != 0){
					console.log(list);	
					for(var i = 0 ; i<list.length; i++){
						console.log(list.length);
						render(list[i], "down");	
					}
					}
				},
				error : function(XHR, status, error) {
					console.error(status + " : " + error);
				}
				
			});
	    }
	});
	
	$("#gbList").on("click", ".btnDel", function(){
		console.log("delete");
		var $this = $(this);
		var no = $this.data("no");
		console.log(no);
		$("#modalNo").val(no);
		$("#del-pop").modal();
		
		
		
		$("#btn_del").on("click",function(){
			var modalPassword = $("#modalPassword").val();

			console.log(modalPassword);
			$.ajax({
				
				url : "${pageContext.request.contextPath }/api/gb/delete",		
				type : "post",
			
			
				data : {no:no , password:modalPassword},

			
			
				dataType : "json",
				success :function(count){
							console.log(count)
							if(count==1){
								$("#del-pop").modal("hide");
								console.log(no);
								$("#"+no).remove();
							}
					
						$("#modalPassword").val("");
					
				},
				error : function(XHR, status, error) {
					console.error(status + " : " + error);
				}
			
			});
		});

	});
	
	$("#gbList").on("click", "div", function() {
		console.log("click div");
		var $this = $(this);
		console.log($this);
		var str = $this.attr("id");
		console.log(str);
	});
	
	
	// 	기명함수 
	function render(guestbookVo, updown){
		
		
		var str = "";
		str +="	<div id="+guestbookVo.no+">";
		str +="	<table width=510 border=1>";
		str +="		<tr>";
		str +="			<td >"+guestbookVo.no+"</td>";
		str +="			<td>"+guestbookVo.name+"</td>";
		str +="			<td>"+guestbookVo.regDate+"</td>";
		str +="			<td><input class='btnDel' type = 'button' value='삭제' data-no="+guestbookVo.no+" ></td>";
		str +="		</tr>";
		str +="		<tr>";
		str +="			<td colspan=4>"+guestbookVo.content+"</td>";
		str +="		</tr>";
		str +="</table>";
		str +="	</div>";
		str +="<br>";
		
		if(updown == "up"){
			$("#gbList").prepend(str);
		}else if(updown == "down"){
			$("#gbList").append(str);
			no=guestbookVo.no;
			console.log(no);
		}else {
			console.log("updown 오류 ");
		}
	}
		// insert 
		
		$(".insert").on("click", function(){
			var name = $("[name=name]").val();
			console.log(name);
			var password = $("[name=password]").val();
			console.log(password);
			var content = $("[name=content]").val();	
			console.log(content);
			guestbookVo = {
					 name : $("[name='name']").val(),
					 password : $("[name='password']").val(),
					 content : $("[name='content']").val()	
			};
			
		
			$.ajax({
				
				url : "${pageContext.request.contextPath }/api/gb/insert",		
				type : "post",
				contentType : "application/json",								
				data :JSON.stringify(guestbookVo),
				dataType : "json",
				success : function(gVo){
					console.log(gVo)
					if(gVo != null){	
								render( gVo,"up");
							
					}
				},
				error : function(XHR, status, error) {
					console.error(status + " : " + error);
				}
			});
			
			$("[name=name]").val("");
			$("[name=password]").val("");
			$("[name=content]").val("");
		});
		
		
	
</script>
</html>

