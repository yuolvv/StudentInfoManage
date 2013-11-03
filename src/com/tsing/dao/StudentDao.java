package com.tsing.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tsing.model.PageBean;


public class StudentDao {

	public ResultSet studentList(Connection con,PageBean pageBean)throws Exception{
		StringBuffer sb = new StringBuffer("select * from t_student s,t_grade g where s.gradeId=g.id");
		
		if(pageBean!=null){
			//特别注意limit前后的空格!!!
			sb.append(" limit " + pageBean.getStart() + "," + pageBean.getRows());
		}
		
		PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		return pstmt.executeQuery();

	}
	
	public int studentCount(Connection con)throws Exception {
		StringBuffer sb=new StringBuffer("select count(*) as total from t_student s,t_grade g where s.gradeId=g.id");
		PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else {
			return 0;
		}
	}
}
