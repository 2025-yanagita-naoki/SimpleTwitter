package chapter6.service;

import static chapter6.utils.CloseableUtil.*;
import static chapter6.utils.DBUtil.*;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import chapter6.beans.Message;
import chapter6.beans.UserMessage;
import chapter6.dao.MessageDao;
import chapter6.dao.UserMessageDao;
import chapter6.logging.InitApplication;


public class MessageService {


    /**
    * ロガーインスタンスの生成
    */
    Logger log = Logger.getLogger("twitter");

    /**
    * デフォルトコンストラクタ
    * アプリケーションの初期化を実施する。
    */
    public MessageService() {
        InitApplication application = InitApplication.getInstance();
        application.init();
    }

    public void insert(Message message) {

	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

        Connection connection = null;
        try {
            connection = getConnection();
            new MessageDao().insert(connection, message);
            commit(connection);
        } catch (RuntimeException e) {
            rollback(connection);
		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
            throw e;
        } catch (Error e) {
            rollback(connection);
		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
            throw e;
        } finally {
            close(connection);
        }
    }

    public void delete(int deleteMessageId) {

  	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
          " : " + new Object(){}.getClass().getEnclosingMethod().getName());

          Connection connection = null;
          try {
              connection = getConnection();
              new MessageDao().delete(connection, deleteMessageId);
              commit(connection);
          } catch (RuntimeException e) {
              rollback(connection);
  		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
              throw e;
          } catch (Error e) {
              rollback(connection);
  		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
              throw e;
          } finally {
              close(connection);
          }
      }

    public void edit(int editMassageId, String editMassageText) {

    	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
            " : " + new Object(){}.getClass().getEnclosingMethod().getName());

            Connection connection = null;
            try {
                connection = getConnection();
                new MessageDao().edit(connection, editMassageId, editMassageText);
                commit(connection);
            } catch (RuntimeException e) {
                rollback(connection);
    		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
                throw e;
            } catch (Error e) {
                rollback(connection);
    		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
                throw e;
            } finally {
                close(connection);
            }
        }

    public List<UserMessage> select(String userId, String start, String end) {

  	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
          " : " + new Object(){}.getClass().getEnclosingMethod().getName());

          final int LIMIT_NUM = 1000;
          String startDate;
          String endDate;

          if(!StringUtils.isEmpty(start)) {
        	  startDate = start + " 00:00:00";
          } else {
        	  startDate = "2020-01-01 00:00:00";
          }

          if(!StringUtils.isEmpty(end)) {
        	  endDate = end + " 23:59:59";
          } else {
        	  Calendar cl = Calendar.getInstance();
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
              endDate = sdf.format(cl.getTime());
          }

          Connection connection = null;
          try {
              connection = getConnection();

              Integer id = null;
              if(!StringUtils.isEmpty(userId)) {
                  id = Integer.parseInt(userId);
              }

              List<UserMessage> messages = new UserMessageDao().select(connection, id, LIMIT_NUM, startDate, endDate);
              commit(connection);

              return messages;
          } catch (RuntimeException e) {
              rollback(connection);
  		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
              throw e;
          } catch (Error e) {
              rollback(connection);
  		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
              throw e;
          } finally {
              close(connection);
          }
      }

    public Message select(int id) {

    	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
            " : " + new Object(){}.getClass().getEnclosingMethod().getName());

            Connection connection = null;
            try {
                connection = getConnection();
                Message messages = new MessageDao().select(connection, id);
                commit(connection);

                return messages;
            } catch (RuntimeException e) {
                rollback(connection);
    		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
                throw e;
            } catch (Error e) {
                rollback(connection);
    		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
                throw e;
            } finally {
                close(connection);
            }
        }
}

