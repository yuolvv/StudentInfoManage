package com.tsing.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tsing.dao.UserDao;
import com.tsing.model.User;
import com.tsing.util.DbUtil;
import com.tsing.util.StringUtil;

public class LoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil = new DbUtil();
	UserDao userDao = new UserDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		request.setAttribute("userName", userName);
		request.setAttribute("password", password);
		
		
		if(StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)){
			request.setAttribute("error", "用户名或密码为空");
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
		User user = new User(userName,password);
		Connection con = null;
		try {
			con = dbUtil.getCon();
			User currentUser = userDao.login(con, user);
			if(currentUser==null){
				request.setAttribute("error", "用户名或密码输入错误");
				//服务器跳转
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}else {
				
				//获取Session
				HttpSession session = request.getSession();
				session.setAttribute("currentUser", currentUser);
				
				//客户端跳转main.jsp
				response.sendRedirect("main.jsp");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	
}
