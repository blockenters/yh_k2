
public class Child extends Parent {
	
	// 이 클래스는, Parent 클래스가 가지고 있는
	// 속성들을, 상속받을 것이다.
	
	// name
	// age
	// money
		
	String hobby;
	
	Child(){
		System.out.println("Child 생성자 호출됨");
	}	
	
	
	// setMoney()
	// getMoney()
	// print()
	
	// Method Overriding 메소드 오버라이딩
	// 상속하는 클래스에서!! 즉 자식클래스에서
	// 부모 클래스에 들어있는 함수 이름을 그대로 사용하되!!!
	// 내가 원하는대로 내용만 바꿔서 사용하는거!!
	void print() {
		super.print();
		System.out.println("hobby : " + hobby);
	}
	
	// Method Overroading :
	// 하나의 클래스 안에서!!! 똑같은 이름의 함수를 여러개 만드는거!!
	
	
}




