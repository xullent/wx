package org.liufeng.course.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.liufeng.course.pojo.Knowledge;


public final class JdbcUtil { 
    private static String dbUrl = "jdbc:mysql://";
    public static String port = "4050";
    public static String host = "sqld.duapp.com";
    public static String username = "Access Key";
    public static String password = "Secret Key";
    public static String databaseName = "kwarSoPNsvSfNwVHbJgU";
  
    //拒绝new一个实例  
//    private JdbcUtil() {};  
  
    static {//注册驱动  
        try {  
            Class.forName("com.mysql.jdbc.Driver");  
        } catch (ClassNotFoundException e) {  
            throw new ExceptionInInitializerError(e);
        }  
    }  

    public Connection getConnection() throws SQLException {
    	String connName = dbUrl + host + ":" + port + "/" + databaseName;
        return DriverManager.getConnection(connName);
    }  
    /**
	 * 释放JDBC资源
	 * 
	 * @param conn 数据库连接
	 * @param ps
	 * @param rs 记录集
	 */
	private void releaseResources(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if (null != rs)
				rs.close();
			if (null != ps)
				ps.close();
			if (null != conn)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	/**
	 * 获取问答知识表中所有记录
	 * 
	 * @return List<Knowledge>
	 */
	public static List<Knowledge> findAllKnowledge() {
		List<Knowledge> knowledgeList = new ArrayList<Knowledge>();
		String sql = "select * from knowledge";
		JdbcUtil jdbcUtil = new JdbcUtil();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = jdbcUtil.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Knowledge knowledge = new Knowledge();
				knowledge.setId(rs.getInt("id"));
				knowledge.setQuestion(rs.getString("question"));
				knowledge.setAnswer(rs.getString("answer"));
				knowledge.setCategory(rs.getInt("category"));
				knowledgeList.add(knowledge);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			jdbcUtil.releaseResources(conn, ps, rs);
		}
		return knowledgeList;
	}

	/**
	 * 获取上一次的聊天类别
	 * 
	 * @param openId 用户的OpenID
	 * @return chatCategory
	 */
	@SuppressWarnings("static-access")
	public static int getLastCategory(String openId) {
		int chatCategory = -1;
		String sql = "select chat_category from chat_log where open_id=? order by id desc limit 0,1";

		JdbcUtil jdbcUtil = new JdbcUtil();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = jdbcUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, openId);
			rs = ps.executeQuery();
			if (rs.next()) {
				chatCategory = rs.getInt("chat_category");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			jdbcUtil.releaseResources(conn, ps, rs);
		}
		return chatCategory;
	}

	/**
	 * 根据知识id随机获取一个答案
	 * 
	 * @param knowledgeId 问答知识id
	 * @return
	 */
	public static String getKnowledSub(int knowledgeId) {
		String knowledgeAnswer = "";
		String sql = "select answer from knowledge_sub where pid=? order by rand() limit 0,1";

		JdbcUtil jdbcUtil = new JdbcUtil();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = jdbcUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, knowledgeId);
			rs = ps.executeQuery();
			if (rs.next()) {
				knowledgeAnswer = rs.getString("answer");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			jdbcUtil.releaseResources(conn, ps, rs);
		}
		return knowledgeAnswer;
	}

	/**
	 * 随机获取一条笑话
	 * 
	 * @return String
	 */
	public static String getJoke() {
		String jokeContent = "";
		String sql = "select joke_content from joke order by rand() limit 0,1";

		JdbcUtil jdbcUtil = new JdbcUtil();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = jdbcUtil.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				jokeContent = rs.getString("joke_content");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			jdbcUtil.releaseResources(conn, ps, rs);
		}
		return jokeContent;
	}

	/**
	 * 保存聊天记录
	 * 
	 * @param openId 用户的OpenID
	 * @param createTime 消息创建时间
	 * @param reqMsg 用户上行的消息
	 * @param respMsg 公众账号回复的消息
	 * @param chatCategory 聊天类别
	 */
	public static void saveChatLog(String openId, String createTime, String reqMsg, String respMsg, int chatCategory) {
		String sql = "insert into chat_log(open_id, create_time, req_msg, resp_msg, chat_category) values(?, ?, ?, ?, ?)";

		JdbcUtil jdbcUtil = new JdbcUtil();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = jdbcUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, openId);
			ps.setString(2, createTime);
			ps.setString(3, reqMsg);
			ps.setString(4, respMsg);
			ps.setInt(5, chatCategory);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			jdbcUtil.releaseResources(conn, ps, rs);
		}
	}
}