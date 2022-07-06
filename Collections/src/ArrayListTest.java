import java.util.ArrayList;

public class ArrayListTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// 배열은, 생성시에, 갯수를 정해줘야 한다!
		// 배열은 한번 갯수를 정하면, 그갯수 이상으로는 데이터를 추가 불가!
		String[] nameArray = new String[10];
		
		// 따라서, 갯수 정하지 않고, 데이터를 마음대로 
		// 추가할수도 있고, 삭제할수도 있는것이 어레이 리스트 이다.
		
		// 담고 싶은 데이터를 < > 안에 적어줘야 한다.
		// 비어있는 리스를 만든다.
		ArrayList<String> nameList = new ArrayList<String>();
		
		nameList.add("홍길동");
		nameList.add("김나나");
		nameList.add("Mike");
				
		System.out.println( nameList.get(0) );
		System.out.println( nameList.get(1) );
		System.out.println( nameList.get(2) ); 
		
		// 리스트에 있는 데이터를 모두 출력!
		
		// nameList.size() 은 저장되어있는 데이터의 갯수
		 
		for(int i = 0; i < nameList.size() ; i++ ) {
			System.out.println( nameList.get(i) );
		}
		
		// 홍길동, 김나나, Mike 
		// Harry 를 홍길동과 김나나 사이에 추가!! 
		nameList.add(1, "Harry");
		
		for(int i = 0; i < nameList.size() ; i++ ) {
			System.out.println( nameList.get(i) );
		}
		
		System.out.println("---------------");
		
		nameList.remove(0);
		
		for(int i = 0; i < nameList.size() ; i++ ) {
			System.out.println( nameList.get(i) );
		}		
		
		System.out.println("---------------");
		
		nameList.remove("Mike");
		
		for(int i = 0; i < nameList.size() ; i++ ) {
			System.out.println( nameList.get(i) );
		}	
		
		System.out.println("---------------");
		
		// 저장된 데이터를 전부삭제
		nameList.clear();
		
		System.out.println(  nameList.size() );
		
		// 리스트가 비어있는지 확인
//		nameList.isEmpty()
		
		if( nameList.isEmpty() ) {
			System.out.println("비어있다.");
		} else {
			System.out.println("데이터 있음");
		}
		
	}

}
