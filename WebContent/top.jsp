<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%> <!-- EL式の有効無効を設定する falseが有効 -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <!-- JSTLのコアタグライブラリを使用 -->
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><!-- JSTLのフォーマットタグライブラリを使用 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./css/style.css" rel="stylesheet" type="text/css">
<title>簡易Twitter</title>
</head>
<body>
	<div class="main-contents">
		<div class="header">
			<c:if test="${ empty loginUser }"> <!-- login状態ではないときの表示 -->
				<a href="login">ログイン</a>
				<a href="signup">登録する</a>
			</c:if>
			<c:if test="${ not empty loginUser }"> <!-- login状態の表示 -->
				<a href="./">ホーム</a>
				<a href="setting">設定</a>
				<a href="logout">ログアウト</a>
			</c:if>
		</div>
		<c:if test="${ not empty loginUser }">
			<div class="profile">
				<div class="name">
					<h2>
						<c:out value="${loginUser.name}" />
						<!-- LoginServletでセットしたセッションから値を取得 -->
					</h2>
				</div>
				<div class="account">
				@
					<c:out value="${loginUser.account}" />
					<!-- LoginServletでセットしたセッションから値を取得 -->
				</div>
				<div class="description">
					<c:out value="${loginUser.description}" />
					<!-- LoginServletでセットしたセッションから値を取得 -->
				</div>
			</div>
		</c:if>
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

		<div class="form-area">
			<c:if test="${ isShowMessageForm }">
				<form action="message" method="post">
					いま、どうしてる？<br />
					<textarea name="text" cols="100" rows="5" class="tweet-box"></textarea>
					<br /> <input type="submit" value="つぶやく">（140文字まで）
				</form>
			</c:if>
		</div>

		<div class="messages">
			<c:forEach items="${messages}" var="message">
				<div class="message">
					<div class="account-name">
						<span class="account">
							<a href="./?user_id=<c:out value="${message.userId}"/> ">
								<c:out value="${message.account}" />
							</a>
						</span>
						<span class="name"><c:out value="${message.name}" /></span>
					</div>
					<div class="text">
						<c:out value="${message.text}" />
					</div>
					<div class="date">
						<fmt:formatDate value="${message.createdDate}"
							pattern="yyyy/MM/dd HH:mm:ss" />
					</div>
					<div class=message-submits>
						<c:if test="${loginUser.id == message.userId}">
							<div class=messages_submit>
								<div class="message-submit_edit">
									<form action="edit" method="post">
										<input type="submit" value="編集">
										<input name="editMassageId" type="hidden" value="${message.id}">
									</form>
								</div>
								<div class="message-submit_delete">
									<form action="deleteMessage" method="post">
										<input type="submit" value="削除">
										<input name="deleteMassageId" type="hidden" value="${message.id}">
									</form>
								</div>
							</div>
						</c:if>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="copyright">Copyright(c)NaokiYanagita</div>
	</div>
</body>
</html>

