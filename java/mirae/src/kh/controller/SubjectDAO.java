package kh.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kh.model.SubjectVO;

public class SubjectDAO {
	// 쿼리문을 적는 곳

	// 1. 학과 목록 리스트 함수
	public void getSubjectTotalList() {
		System.out.println("-------------------------");
		String sql = "select * from subject order by no asc";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.makeConnection(); // DB 와 연결
			pstmt = con.prepareStatement(sql); // DB쪽 sql문 실행
			// select -> excuteQuery // select X -> excuteUpdate
			rs = pstmt.executeQuery(); // sql문 실행결과
			System.out.println("일련번호\t학과번호\t학과");

			while (rs.next()) {
				SubjectVO svo = new SubjectVO();
				svo.setNo(rs.getInt("no"));
				svo.setS_num(rs.getString("s_num"));
				svo.setS_name(rs.getString("s_name"));
				System.out.println(svo.toString());
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
		System.out.println("-------------------------");
	}

	public void setSubjectRegister(SubjectVO svo) {
		System.out.println("-------------------------");
		String sql = "insert into subject values(subject_seq.nextval, ?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.makeConnection(); // DB 와 연결
			pstmt = con.prepareStatement(sql); // DB쪽 sql문 실행
			// select -> excuteQuery // select X -> excuteUpdate
			pstmt.setString(1, svo.getS_num());
			pstmt.setString(2, svo.getS_name());
			int value = pstmt.executeUpdate();
			if (value == 1) {
				System.out.println("\s\s<" + svo.getS_name() + "> 등록 완료");
			} else {
				System.out.println("\s\s<" + svo.getS_name() + "> 등록 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
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
		System.out.println("-------------------------");
	}

	public void setSubjectUpdate(SubjectVO svo) {
		System.out.println("-------------------------");
		String sql = "update subject set s_num = ?, s_name = ? where no = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.makeConnection(); // DB 와 연결
			pstmt = con.prepareStatement(sql); // DB쪽 sql문 실행
			// select -> excuteQuery // select X -> excuteUpdate
			pstmt.setString(1, svo.getS_num());
			pstmt.setString(2, svo.getS_name());
			pstmt.setInt(3, svo.getNo());
			int value = pstmt.executeUpdate();

			if (value == 1) {
				System.out.println("\s\s<" + svo.getS_name() + ">로 수정 완료");
			} else {
				System.out.println("\s\s<" + svo.getS_name() + ">로 수정 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
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
		System.out.println("-------------------------");
	}

	public void setSubjectDelete(int no) {
		System.out.println("-------------------------");
		String sql = "delete from subject where no = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.makeConnection(); // DB 와 연결
			pstmt = con.prepareStatement(sql); // DB쪽 sql문 실행
			// select -> excuteQuery // select X -> excuteUpdate
			pstmt.setInt(1, no);
			int value = pstmt.executeUpdate();
			if (value == 1) {
				System.out.println("\s\s일련번호<" + no + ">학과 삭제 완료");
			} else {
				System.out.println("\s\s일련번호<" + no + ">학과 삭제 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
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
		System.out.println("-------------------------");
	}

	// 2. 학과 정보 입력함수
	// 3. 학과 정보 수정함수
	// 4. 학과 정보 삭제함수

}
