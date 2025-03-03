package chapter6.dao;

import static chapter6.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import chapter6.beans.Message;
import chapter6.exception.NoRowsUpdatedRuntimeException;
import chapter6.exception.SQLRuntimeException;
import chapter6.logging.InitApplication;

public class MessageDao {


    /**
    * ロガーインスタンスの生成
    */
    Logger log = Logger.getLogger("twitter");

    /**
    * デフォルトコンストラクタ
    * アプリケーションの初期化を実施する。
    */
    public MessageDao() {
        InitApplication application = InitApplication.getInstance();
        application.init();

    }

    public void insert(Connection connection, Message message) {

	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

        PreparedStatement ps = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO messages ( ");
            sql.append("    user_id, ");
            sql.append("    text, ");
            sql.append("    created_date, ");
            sql.append("    updated_date ");
            sql.append(") VALUES ( ");
            sql.append("    ?, ");                  // user_id
            sql.append("    ?, ");                  // text
            sql.append("    CURRENT_TIMESTAMP, ");  // created_date
            sql.append("    CURRENT_TIMESTAMP ");   // updated_date
            sql.append(")");

            ps = connection.prepareStatement(sql.toString());

            ps.setInt(1, message.getUserId());
            ps.setString(2, message.getText());

            ps.executeUpdate();
        } catch (SQLException e) {
		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
            throw new SQLRuntimeException(e);
        } finally {
            close(ps);
        }
    }

    public void delete(Connection connection, String deleteMessageId) {

  	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
          " : " + new Object(){}.getClass().getEnclosingMethod().getName());

          PreparedStatement ps = null;
          try {
              String sql = "DELETE FROM messages WHERE id = ?";

//            SQLが実行できる準備を整える
              ps = connection.prepareStatement(sql.toString());

//            バインド変数へ値の代入
              ps.setString(1, deleteMessageId);

//            更新系SQL処理を実行
              ps.executeUpdate();
          } catch (SQLException e) {
  		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
              throw new SQLRuntimeException(e);
          } finally {
              close(ps);
          }
      }

    public void edit(Connection connection, String editMassageId, String editMassageText) {

    	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
            " : " + new Object(){}.getClass().getEnclosingMethod().getName());

            PreparedStatement ps = null;
            try {
            	StringBuilder sql = new StringBuilder();//SQL文を作る
            	sql.append("UPDATE messages SET ");
    			sql.append("    text = ? ");
    			sql.append("    WHERE ");
    			sql.append("    id = ? ");

                ps = connection.prepareStatement(sql.toString());

                ps.setString(1, editMassageText);
                ps.setString(2, editMassageId);

                int count = ps.executeUpdate();
                if (count == 0) {
    	    		log.log(Level.SEVERE,"更新対象のレコードが存在しません", new NoRowsUpdatedRuntimeException());
    	            throw new NoRowsUpdatedRuntimeException();
    	        }
    	    } catch (SQLException e) {
    		  log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
    	        throw new SQLRuntimeException(e);
    	    } finally {
    	        close(ps);
    	    }
        }

    public List<Message> select(Connection connection, int id) {

    	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
            " : " + new Object(){}.getClass().getEnclosingMethod().getName());

            PreparedStatement ps = null;
            try {
                String sql = "SELECT * FROM messages WHERE id = ?";

                ps = connection.prepareStatement(sql);
                ps.setInt(1, id);

                ResultSet rs = ps.executeQuery();

                List<Message> messages = toMessages(rs);
                return messages;
            } catch (SQLException e) {
    		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
                throw new SQLRuntimeException(e);
            } finally {
                close(ps);
            }
        }

    private List<Message> toMessages(ResultSet rs) throws SQLException {


  	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
          " : " + new Object(){}.getClass().getEnclosingMethod().getName());

          List<Message> messages = new ArrayList<Message>();
          try {
              while (rs.next()) {
                  Message message = new Message();
                  message.setId(rs.getInt("id"));
                  message.setText(rs.getString("text"));
                  message.setUserId(rs.getInt("user_id"));
                  message.setCreatedDate(rs.getTimestamp("created_date"));
                  message.setUpdatedDate(rs.getTimestamp("updated_date"));

                  messages.add(message);
              }
              return messages;
          } finally {
              close(rs);
          }
      }
}

