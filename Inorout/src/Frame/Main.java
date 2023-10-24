/*
 * 작성일자 : 2023-10-24
 * 작성자 : 이승현
 * 설명 : 자바 GUI를 활용하여 도형을 그리고 포인트가 도형의 내외부 어디에 존재하는지 확인하는 프로그램
 * 
 * 클래스 이름 : Main
 * 클래스 역할 : 프레임 클래스 생성 및 프로그램 실행
 */
package Frame;
import Frame.myFrame;

public class Main {
	public static void main(String args[]) {
		//메인프레임 생성
		myFrame frame = new myFrame();
		//프레임 보여주기
		frame.setVisible(true);
	}
}
