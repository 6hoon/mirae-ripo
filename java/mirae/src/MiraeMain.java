import java.sql.Connection;
import java.util.Scanner;
import kh.controller.DBUtil;
import kh.controller.StudentRegisterManager;
import kh.controller.SubjectRegisterManager;
import kh.view.MENU_CHOICE;
import kh.view.MenuViewer;
import kh.view.STUDENT_CHOICE;
import kh.view.SUBJECT_CHOICE;

public class MiraeMain {
	public static Scanner choice = new Scanner(System.in);

	public static void main(String[] args) {
		// 메뉴화면 함수
		mainMenu();

		System.out.println("end of main");
	}

	// 메뉴화면 함수
	public static void mainMenu() {
		int choiceNum = 0;
		boolean flagExit = false;

		while (!flagExit) {
			MenuViewer.mainMenuView();
			try {
				choiceNum = Integer.parseInt(choice.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("숫자를 입력해주세요");
			}
			switch (choiceNum) {
			case MENU_CHOICE.SUBJECT:
				subjectMenu();
				break;
			case MENU_CHOICE.STUDENT:
				studentMenu();
				break;
			case MENU_CHOICE.LESSON:
//				MenuViewer.lessonMenuView();
				break;
			case MENU_CHOICE.TRAINEE:
//				MenuViewer.traineeMenuView();
				break;
			case MENU_CHOICE.EXIT:
//				System.out.println("종료합니다");
				flagExit = true;
				break;
			default:
				System.out.println("해당 메뉴 번호만 입력하세요");
				break;
			}
		} // end of while
	}

	public static void studentMenu() {
		int choiceNum = 0;
		StudentRegisterManager sturm = new StudentRegisterManager();
		MenuViewer.studentMenuView();
		try {
			choiceNum = Integer.parseInt(choice.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("숫자로 입력바랍니다");
			System.out.println("메인메뉴로 돌아갑니다");
			return;
		}
		switch (choiceNum) {
		case STUDENT_CHOICE.INSERT :
			sturm.studentRegister();
			break;
		case STUDENT_CHOICE.UPDATE :
			break;
		case STUDENT_CHOICE.LIST :
			sturm.studentTotalList();
			break;
		case STUDENT_CHOICE.MAIN :
			System.out.println("메인메뉴로 돌아갑니다");
			return;
		default:
			System.out.println("지정된 메뉴만 입력바람");
		}
	}

	public static void subjectMenu() {
		int choiceNum = 0;
//		boolean flag = false;
//		while (!flag) {
		// 학과 정보 관리 컨트롤러(작업관리자)
		SubjectRegisterManager srm = new SubjectRegisterManager();
		// 학과 정보 메뉴
		MenuViewer.subjectMenuView();
		try {
			choiceNum = Integer.parseInt(choice.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("숫자로 입력바랍니다");
			System.out.println("메인메뉴로 돌아갑니다");
			return;
		}
		switch (choiceNum) {
		case SUBJECT_CHOICE.LIST:
			srm.subjectList();
			break;
		case SUBJECT_CHOICE.INSERT:
			srm.subjectRegister();
			break;
		case SUBJECT_CHOICE.UPDATE:
			srm.subjectUpdate();
			break;
		case SUBJECT_CHOICE.DELETE:
			srm.subjectDelete();
			break;
		case SUBJECT_CHOICE.MAIN:
//				flag = true;
//			break;
			System.out.println("메인메뉴로 돌아갑니다"); //
			return; //
		default:
			System.out.println("지정된 메뉴만 입력바람");
//			break;
		}
//		}
	}

}
