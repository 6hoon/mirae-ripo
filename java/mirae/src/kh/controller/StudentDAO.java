package kh.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kh.model.StudentVO;
import kh.view.MenuViewer;

public class StudentDAO {

	public void getStudentTotalList() {
		MenuViewer.makeLine();
		String sql = "select * from student order by no asc";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBUtil.makeConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			System.out.println("<학생정보 출력>");

			while (rs.next()) {
				StudentVO stuvo = new StudentVO();
				stuvo.setNo(rs.getInt("no"));
				stuvo.setSd_num(rs.getString("sd_num"));
				stuvo.setSd_name(rs.getString("sd_name"));
				stuvo.setSd_id(rs.getString("sd_id"));
				stuvo.setSd_passwd(rs.getString("sd_passwd"));
				stuvo.setS_num(rs.getString("s_num"));
				stuvo.setSd_birthday(rs.getString("sd_birthday"));
				stuvo.setSd_phone(rs.getString("sd_phone"));
				stuvo.setSd_address(rs.getString("sd_address"));
				stuvo.setSd_email(rs.getString("sd_email"));
				stuvo.setSd_date(rs.getDate("sd_date"));
				System.out.println(stuvo.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		MenuViewer.makeLine();
	}
}
