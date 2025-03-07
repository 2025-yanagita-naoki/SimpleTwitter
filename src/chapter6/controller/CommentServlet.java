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

import chapter6.beans.Comment;
import chapter6.logging.InitApplication;
import chapter6.service.CommentService;

@WebServlet(urlPatterns = { "/comment" })
public class CommentServlet extends HttpServlet {


    /**
    * ロガーインスタンスの生成
    */
    Logger log = Logger.getLogger("twitter");

    /**
    * デフォルトコンストラクタ
    * アプリケーションの初期化を実施する。
    */
    public CommentServlet() {
        InitApplication application = InitApplication.getInstance();
        application.init();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) // Post呼び出しで実行
            throws IOException, ServletException {

	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

      HttpSession session = request.getSession();
      List<String> errorMessages = new ArrayList<String>();

      int userId = Integer.parseInt(request.getParameter("userId"));
      int id = Integer.parseInt(request.getParameter("id"));
      String text = request.getParameter("text");

      if (!isValid(text, errorMessages)) {
          session.setAttribute("errorMessages", errorMessages);
          response.sendRedirect("./"); // エラーが発生した場合はtop.jspへリダイレクト
          return;
      }

      Comment comment = new Comment();
      comment.setUserId(userId);
      comment.setId(id);
      comment.setText(text);

      new CommentService().insert(comment); // データベースにつぶやきを登録
      response.sendRedirect("./"); // 登録後リダイレクト

    }

    private boolean isValid(String text, List<String> errorMessages) { // バリデーション

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
