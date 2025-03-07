<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<!-- EL式の有効無効を設定する falseが有効 -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- JSTLのコアタグライブラリを使用 -->
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
			<c:if test="${ empty loginUser }">
				<!-- login状態ではないときの表示 -->
				<a href="login">ログイン</a>
				<a href="signup">登録する</a>
			</c:if>
			<c:if test="${ not empty loginUser }">
				<!-- login状態の表示 -->
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
		<div class="date-form">
			<form action="./" method="get">
				日付：
				<input name="startDate" type="date" value="<c:out value="${startDate}"/>">
				～
				<input name="endDate"type="date" value="<c:out value="${endDate}"/>">
				<input type="submit" value="絞込">
			</form>

		</div>
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
						<span class="account"> <a
							href="./?user_id=<c:out value="${message.userId}"/> "> <c:out
									value="${message.account}" />
						</a>
						</span> <span class="name"><c:out value="${message.name}" /></span>
					</div>
					<div class="text">
						<pre><c:out value="${message.text}" /></pre>
					</div>
					<div class="date">
						<fmt:formatDate value="${message.createdDate}"
							pattern="yyyy/MM/dd HH:mm:ss" />
					</div>
					<div class=message-submits>
						<c:if test="${loginUser.id == message.userId}">
							<div class=messages_submit>
								<div class="message-submit_edit">
									<form action="edit" method="get">
										<input type="submit" value="編集">
										<input name="editMessageId" type="hidden" value="${message.id}">
									</form>
								</div>
								<div class="message-submit_delete">
									<!-- サーブレット側にて@WebServletのアノテーションで指定している値をURLで紐づけてPostにて送信されている。 -->
									<form action="deleteMessage" method="post">
										<input type="submit" value="削除">
										<!-- hidden属性を使用することで、要素の中身をユーザーに表示しないことを示す -->
										<input name="deleteMassageId" type="hidden"
											value="${message.id}">
									</form>
								</div>
							</div>
						</c:if>
					</div>
					<div class="message-comment">
						<c:if test="${ isShowMessageForm }">
							<c:forEach items="${commentMessages}" var="commentMessage">
								<c:if test="${commentMessage.messageId == message.id}">
									<div class="comment">
										<div class="comment-user">
											<c:out value="${commentMessage.account}" />
											<c:out value="${commentMessage.name}" />
										</div>
										<div class="comment-text"><pre><c:out value="${commentMessage.text}" /></pre></div>
										<fmt:formatDate value="${commentMessage.createdDate}" pattern="yyyy/MM/dd HH:mm:ss" />
									</div>
								</c:if>
							</c:forEach>
							<form action="comment" method="post">
								返信<br />
								<textarea name="text" cols="100" rows="5" class="tweet-box"></textarea>
								<input name="UserId" type="hidden" value="${message.userId}">
								<input name="id" type="hidden" value="${message.id}">
								<br /> <input type="submit" value="返信">
							</form>
						</c:if>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="copyright">Copyright(c)NaokiYanagita</div>
	</div>
</body>
</html>

