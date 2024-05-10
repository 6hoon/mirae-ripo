package kh.controller;

import java.util.Scanner;

public class StudentRegisterManager {
	public Scanner sc = new Scanner(System.in);
	public void studentRegister() {
		
	}
	public void studentTotalList() {
		StudentDAO studao = new StudentDAO();
		studao.getStudentTotalList();
	}

	

}
