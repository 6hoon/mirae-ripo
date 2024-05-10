package kh.controller;

import java.util.Scanner;

import kh.model.SubjectVO;

// 학과정보컨트롤러 -------> 서버역할
public class SubjectRegisterManager {

	// 1. 학과 목록 보여주는 기능구현
	public void subjectList() {
		SubjectDAO sdao = new SubjectDAO();
		sdao.getSubjectTotalList();
	}

	// 2. 학과 정보 저장 기능구현
	public void subjectRegister() {
		Scanner input = new Scanner(System.in);
		SubjectDAO sdao = new SubjectDAO();
		// 학과정보 리스트
		System.out.println("------<학과정보리스트>------");
		sdao.getSubjectTotalList();
		// 학과 정보 입력
		System.out.println("-------<학과정보입력>-------");
		System.out.println("학과정보입력");
		System.out.print("학과번호>> ");
		String s_num = input.nextLine();
		System.out.print("학과명>> ");
		String s_name = input.nextLine();
		SubjectVO svo = new SubjectVO(s_num, s_name);

		sdao.setSubjectRegister(svo);

	}

	// 3. 학과 정보 수정 기능구현
	public void subjectUpdate() {
		Scanner input = new Scanner(System.in);
		SubjectDAO sdao = new SubjectDAO();
		// 학과정보 리스트
		System.out.println("------<학과정보리스트>------");
		sdao.getSubjectTotalList();
		// 학과 정보 입력
		System.out.println("수정할 학과 일련번호 입력");
		System.out.print("일련번호>> ");
		int no = 0;
		try {
			no = Integer.parseInt(input.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("숫자로 입력바랍니다");
			System.out.println("메인메뉴로 돌아갑니다");
			return;
		}
		System.out.print("학과번호>> ");
		String s_num = input.nextLine();
		System.out.print("학과명>> ");
		String s_name = input.nextLine();
		SubjectVO svo = new SubjectVO(no, s_num, s_name);

		sdao.setSubjectUpdate(svo);
	}

	// 4. 학과 정보 삭제 기능구현
	public void subjectDelete() {
		Scanner input = new Scanner(System.in);
		SubjectDAO sdao = new SubjectDAO();
		// 학과정보 리스트
		System.out.println("------<학과정보리스트>------");
		sdao.getSubjectTotalList();
		// 학과 정보 입력
		System.out.println("삭제할 학과 일련번호 입력");
		System.out.print("일련번호>> ");
		int no = 0;
		try {
			no = Integer.parseInt(input.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("숫자로 입력바랍니다");
			System.out.println("메인메뉴로 돌아갑니다");
			return;
		}
		sdao.setSubjectDelete(no);
	}

}
