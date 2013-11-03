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
import com.tsing.util.StringUtil;

public class GradeSaveServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil = new DbUtil();
	GradeDao gradeDao = new GradeDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//解决中文乱码问题
		request.setCharacterEncoding("utf-8");
		
		String gradeName = request.getParameter("gradeName");
		String gradeDesc = request.getParameter("gradeDesc");
		String id=request.getParameter("id");
		Grade grade=new Grade(gradeName,gradeDesc);
		if(StringUtil.isNotEmpty(id)){
			grade.setId(Integer.parseInt(id));
		}
		Connection con = null;
		try {  
			con=dbUtil.getCon();
			int saveNums=0;
			JSONObject result =new JSONObject();
			if(StringUtil.isNotEmpty(id)){
				saveNums=gradeDao.gradeModify(con, grade);
			}else{
				saveNums=gradeDao.gradeAdd(con, grade);
			}
			
			if(saveNums>0){
				result.put("success", "true");
			}else{
				//javascript里面的要求
				result.put("success", "true");
				
				result.put("errorMsg", "删除失败");
				
			}
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
