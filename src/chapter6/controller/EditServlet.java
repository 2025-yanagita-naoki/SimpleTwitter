package chapter6.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

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

		String editMessageId = request.getParameter("editMessageId");
		List<String> errorMessages = new ArrayList<String>();

		if ((!editMessageId.isEmpty()) && (editMessageId.matches("^[0-9]+$"))) {
            	int selectEditMessageId = Integer.parseInt(editMessageId);

        		Message defaultMessages = new MessageService().select(selectEditMessageId);
        		if(defaultMessages == null) {
        			errorMessages.add("不正なパラメータが入力されました");
        			HttpSession session = request.getSession();
        	    	session.setAttribute("errorMessages", errorMessages);
        	    	response.sendRedirect("./");
        	    	return;
        		}

        		String defaultMessage = defaultMessages.getText();

        		request.setAttribute("defaultMessage", defaultMessage);
        		request.setAttribute("editMessageId", editMessageId);

        		request.getRequestDispatcher("/edit.jsp").forward(request, response);
        } else {
        	errorMessages.add("不正なパラメータが入力されました");
        	HttpSession session = request.getSession();
	    	session.setAttribute("errorMessages", errorMessages);
	    	response.sendRedirect("./"); // 編集後リダイレクト
        }
	}

	@Override
	// POSTで実行
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() +
				" : " + new Object() {
				}.getClass().getEnclosingMethod().getName());

		String editMessageId = request.getParameter("editMessageId");
		String editMessageText = request.getParameter("editMessageText");
		HttpSession session = request.getSession();
		List<String> errorMessages = new ArrayList<String>();

		if (!isValid(editMessageText, errorMessages)) {
            session.setAttribute("errorMessages", errorMessages);
            request.getRequestDispatcher("edit.jsp").forward(request, response);
            return;
        }

		new MessageService().edit(editMessageId, editMessageText); // データベースのつぶやきを編集
		response.sendRedirect("./"); // 編集後リダイレクト
	}

	private boolean isValid(String editMessageText, List<String> errorMessages) { // バリデーション

		  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
	        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

	        if (StringUtils.isBlank(text)) {
	            errorMessages.add("メッセージを入力してください");
	        } else if (140 < text.length()) {
	            errorMessages.add("140文字以下で入力してください");
	        }

	        if (errorMessages.size() != 0) {
	            return false;
	        }
	        return true;
	    }
}
