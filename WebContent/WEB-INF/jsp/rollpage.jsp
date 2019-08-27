<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function jumpto(){
		var regexp=/^[1-9]\d*$/;
		var pageIndex = document.getElementById("inputPage").value;
		var totalPageCount = document.getElementById("totalPageCount").value;
		if(!regexp.test(pageIndex)){
			alert("请输入大于0的正整数！");
			return false;
		}else if((pageIndex-totalPageCount) > 0){
			alert("请输入小于总页数的页码");
			return false;
		}else{
			links(pageIndex);
		}
	}
	function links(pageIndex){
		var queryname = "<%=request.getParameter("queryname")%>";
		var queryUserRole = "<%=request.getParameter("queryUserRole")%>";
		if(queryname == "" && queryUserRole == ""){
			window.location.href="http://localhost:8080/SupermarketOrder/toUserlist?pageIndex=" + pageIndex;
		}
		else{
			window.location.href="http://localhost:8080/SupermarketOrder/selectUserByCondition?method=query&queryname=" + queryname + "&queryUserRole="
			+ queryUserRole + "&pageIndex=" + pageIndex;
		}
	}
</script>
</head>
<body>
 		<div class="page-bar">
			<ul class="page-num-ul clearfix">
				<li>共${param.totalCount }条记录&nbsp;&nbsp; ${param.pageIndex }/${param.totalPageCount }页</li>
				<c:if test="${param.pageIndex > 1}">
					<a href="javascript:links(1);">首页</a>
					<a href="javascript:links(${param.pageIndex-1});">上一页</a>
				</c:if>
				<c:if test="${param.pageIndex < param.totalPageCount }">
					<a href="javascript:links(${param.pageIndex+1});">下一页</a>
					<a href="javascript:links(${param.totalPageCount });">最后一页</a>
				</c:if>
				&nbsp;&nbsp;
			</ul>
		 <span class="page-go-form"><label>跳转至</label>
	     <input type="text" name="inputPage" id="inputPage" class="page-key" />页
	     <button type="button" class="page-btn" onClick="jumpto()">GO</button>
		</span>
		</div> 
</body>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/rollpage.js"></script>
</html>