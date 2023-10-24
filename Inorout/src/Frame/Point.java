package Frame;
/*
 * 판넬 위에서 작동하는 포인트에 대한 클래스
 */

public class Point {
	//x, y 좌표값
	int x,y;
	
	//생성자
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	//선택된 좌표값으로 재설정
	public void reset(int x, int y) {
		this.x = x;
		this.y = y;		
	}
	
	//해당 포인트가 선택되었는지 확인
	public boolean isSelected(int x, int y) {
		if((this.x < x+10 && this.x > x-10) && ((this.y < y+10  && this.y > y-10))) {
			return true;
		}
		return false;		
	}
	
	//포인트 정보 출력
	public String toString() {
		return "X:"+ x + " | Y:" + y;
		
	}
}
