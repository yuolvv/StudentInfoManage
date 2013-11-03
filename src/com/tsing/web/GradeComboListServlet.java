package com.tsing.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.tsing.dao.GradeDao;
import com.tsing.model.Grade;
import com.tsing.model.PageBean;
import com.tsing.util.DbUtil;
import com.tsing.util.JsonUtil;
import com.tsing.util.ResponseUtil;

public class GradeComboListServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil = new DbUtil();
	GradeDao gradeDao = new GradeDao();
	
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
				
		Connection con = null;
		try {
			con=dbUtil.getCon();
			JSONArray jsonArray =new JSONArray();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("id", "");
			jsonObject.put("gradeName", "请选择...");
			jsonArray.add(jsonObject);
			jsonArray.addAll(JsonUtil.formatRsToJsonArray(gradeDao.gradeList(con,null,null)));
			ResponseUtil.write(response, jsonArray);
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
