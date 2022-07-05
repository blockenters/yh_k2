package main.person;

public class Student extends Person {
	
//	protected int num;
//	protected String name;
//	protected String dept;
//	protected String address;
	
	// Person 의 함수들도 모두 상속 받았다.
	
	private String[] subjects;
	
	public Student() {
		
	}
	
	public Student(int num, String name, String dept, 
			String address, String[] subjects) {
		super(num, name, dept, address);
		this.subjects = subjects;
	}
	
	
	public void print() {
		System.out.println(num);
		System.out.println(name);
		System.out.println(dept);
		System.out.println(address);
		System.out.println( name + " 학생의 수강신청 과목");
		for (int i = 0; i < subjects.length ; i++) {
			System.out.println( subjects[i] );
		}
	}
	
	
	public void printSubj() {
		System.out.println( name + " 학생의 수강신청 과목");
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
