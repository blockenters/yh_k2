import main.person.Person;
import main.person.Professor;
import main.person.Staff;
import main.person.Student;

public class PersonMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Person p = new Person();
		p.setNum(1);
		p.setName("홍길동");
		p.setDept("홍보부");
		p.setAddress("인천시 서구");
		
		p.print();
		
		System.out.println("-----------------");
		
		
		Professor f = new Professor();
		f.setNum(2);
		f.setName("김나나");
		f.setDept("컴공과");
		f.setAddress("서울시 마포구");
		f.setSubjects( new String[] {"자바", "파이썬"}  );
		
		f.print();
		
		
		System.out.println("-----------------");
		
		Student s = new Student();
		s.setNum(3);
		s.setName("김철수");
		s.setDept("경영학과");
		s.setAddress("경기도 성남시");
		s.setSubjects(new String[] {"자바", "마케팅","사회"});
		
		s.print();		
		
		
		System.out.println("-----------------");
		
		Staff sf = new Staff();
		sf.setNum(4);
		sf.setName("이영희");
		sf.setDept("인사팀");
		sf.setAddress("강원도");		
		sf.setJob("교육부");
		
		sf.print();		
		
		System.out.println("-----------------");
		
		Person p2 = new Person(10, "홍길동", "마케팅부", "인천시");
		p2.print();
		
		System.out.println("-----------------");
		
		Professor pf = 
			new Professor(11, "김길동", "경제학과","인천시" , new String[] {"자바", "경제","사회"} );                 
		
		pf.print();
		 
		System.out.println("-----------------");
		
		
		Staff sf2 = new Staff(12, "김나나", "교육부", "인천시", "교육관리");
		sf2.print();
		 
		System.out.println("-----------------");
		
	}

}



