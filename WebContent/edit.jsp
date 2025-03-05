<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="./css/style.css" rel="stylesheet" type="text/css">
		<link href="./css/style.css" rel="stylesheet" type="text/css">
		<title>編集</title>
	</head>
	<body>
		<div class="main-contents">
			<c:if test="${ not empty errorMessages }">
			<!-- ログインしていない場合はテキストエリアが表示されない -->
				<div class="errorMessages">
					<ul>
						<c:forEach items="${errorMessages}" var="errorMessage">
							<li><c:out value="${errorMessage}" />
						</c:forEach>
					</ul>
				</div>
				<c:remove var="errorMessages" scope="session" />
			</c:if>
			<div class="edit-contents">
				<div class="main-title">
					<h2>つぶやき</h2>
				</div>
				<div class="edit-submit">
					<form action="edit" method="post">
						<textarea name="editMessageText" cols="100" rows="5" class="tweet-box"><c:out value="${defaultMessage}" /></textarea>
						<input type="submit" value="更新">
						<input name="editMessageId" type="hidden" value="${editMessageId}">
					</form>
				</div>
				<div class="edit-exit">
					<a href="./">戻る</a>
				</div>
			</div>
			<div class="copyright">
				<small>Copyright(c) YanagitaNaoki</small>
			</div>
		</div>
	</body>
</html>