package com.tsing.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.tsing.dao.StudentDao;
import com.tsing.model.Student;
import com.tsing.model.PageBean;
import com.tsing.util.DbUtil;
import com.tsing.util.JsonUtil;
import com.tsing.util.ResponseUtil;

public class StudentListServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil = new DbUtil();
	StudentDao studentDao = new StudentDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//this.doPost(req, res);
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		       //解决中文乱码问题
				request.setCharacterEncoding("utf-8");
				
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		
		
		
		PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Connection con = null;
		try {
			con=dbUtil.getCon();
			JSONObject result =new JSONObject();
			JSONArray jsonArray = JsonUtil.formatRsToJsonArray(studentDao.studentList(con,pageBean));
			int total=studentDao.studentCount(con);
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
	}

	

}
