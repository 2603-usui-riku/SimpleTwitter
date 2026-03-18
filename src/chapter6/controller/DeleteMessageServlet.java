package chapter6.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter6.exception.NoRowsUpdatedRuntimeException;
import chapter6.logging.InitApplication;
import chapter6.service.MessageService;

@WebServlet(urlPatterns = { "/deleteMessage" })
public class DeleteMessageServlet extends HttpServlet {
	/**
	* ロガーインスタンスの生成
	*/
	Logger log = Logger.getLogger("twitter");

	/**
	* デフォルトコンストラクタ
	* アプリケーションの初期化を実施する。
	*/
	public DeleteMessageServlet() {
		InitApplication application = InitApplication.getInstance();
		application.init();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.info(new Object() {
		}.getClass().getEnclosingClass().getName() + " : " + new Object() {
		}.getClass().getEnclosingMethod().getName());

		try {
			int msgId = Integer.parseInt(request.getParameter("messageId"));
			new MessageService().delete(msgId);
		} catch (NoRowsUpdatedRuntimeException e) {
			log.warning("他の人によって更新されています。最新のデータを表示しました。データを確認してください。");
		} catch(NumberFormatException e) {
			log.warning("無効なメッセージIDを数値に変換しようとしています。データを確認してください。");
		}

		response.sendRedirect("./");
	}
}
