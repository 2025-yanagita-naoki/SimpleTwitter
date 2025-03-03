package chapter6.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter6.beans.Message;
import chapter6.logging.InitApplication;
import chapter6.service.MessageService;

@WebServlet(urlPatterns = { "/edit" })
public class EditServlet extends HttpServlet {

	/**
	    * ロガーインスタンスの生成
	    */
	Logger log = Logger.getLogger("twitter");

	/**
	* デフォルトコンストラクタ
	* アプリケーションの初期化を実施する。
	*/
	public EditServlet() {
		InitApplication application = InitApplication.getInstance();
		application.init();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		int editMessageId = Integer.parseInt(request.getParameter("editMessageId"));


		List<Message> defaultMessages = new MessageService().select(editMessageId);
		String defaultMessage = defaultMessages.get(0).getText();

		request.setAttribute("defaultMessage", defaultMessage);
		request.setAttribute("editMassageId", editMessageId);
		request.getRequestDispatcher("edit.jsp").forward(request, response);
	}

	@Override
	// POSTで実行
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		String editMassageId = request.getParameter("editMassageId");
		String editMassageText = request.getParameter("editMassageText");

		new MessageService().edit(editMassageId, editMassageText); // データベースのつぶやきを編集
		response.sendRedirect("./"); // 編集後リダイレクト
	}
}
