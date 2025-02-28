package chapter6.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter6.logging.InitApplication;

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
	    // POSTで実行
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    		throws IOException, ServletException {

		  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
	        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

	        String editMassageId = request.getParameter("editMassageId");


	        //new MessageService().edit(editMassageId); // データベースのつぶやきを編集
	        request.getRequestDispatcher("edit.jsp").forward(request, response);
	    }
}
