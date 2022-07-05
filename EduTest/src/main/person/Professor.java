package main.person;

public class Professor extends Person {
	
//	protected int num;
//	protected String name;
//	protected String dept;
//	protected String address;
	
	// Person 클래스의 함수들도 상속받았다.
	
	private String[] subjects;
	
	
	public Professor(){
		
	}
	
	public Professor(int num, String name, String dept, 
			String address , String[] subjects) {
		
		super(num, name, dept, address);
		
		this.subjects = subjects;
	}
	
	// 부모로 부터 물려받은 함수를, 내가 수정해서 사용 : 메소드 오버라이딩!
	public void print() {
		System.out.println(num);
		System.out.println(name);
		System.out.println(dept);
		System.out.println(address);
		System.out.println( name + " 교수의 개설 과목");
		for (int i = 0; i < subjects.length ; i++) {
			System.out.println( subjects[i] );
		}		
	}
	
	
	public void printSubj() {
		System.out.println( name + " 교수의 개설 과목");
		for (int i = 0; i < subjects.length ; i++) {
			System.out.println( subjects[i] );
		}
	}

	public String[] getSubjects() {
		return subjects;
	}

	public void setSubjects(String[] subjects) {
		this.subjects = subjects;
	}
	
	

}
