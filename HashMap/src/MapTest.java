import java.util.HashMap;
import java.util.Iterator;

public class MapTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// map은 파이썬의 딕셔너리와 비슷한 데이터 스트럭쳐
		// key, value 쌍을 저장!!! 
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("model", "iPhone 12");
		map.put("year", "2021");
		map.put("color", "red");
		
		System.out.println( map.get("color") );
		
		// key 만 가져올때
		Object[] keys = map.keySet().toArray();
		
		for(int i = 0; i < keys.length; i++) {
			System.out.println(  (String)keys[i]  );
		}
		
		// value 만 가져올때
		Object[] values = map.values().toArray();
		
		for(int i = 0; i < values.length; i++) {
			System.out.println(  (String)values[i]  );
		}
		
		// 해쉬맵에 저장된 데이터를 삭제.
		map.remove("year");
		
		System.out.println("------------------");
		
		// Iterator 를 이용해서 전체 데이터를 가져오는 방법
		
		// 키 를 가져오는 방법
		Iterator<String> keyIter = map.keySet().iterator();
		while( keyIter.hasNext() ) {
			String key = keyIter.next();
			String value = map.get(key);
			System.out.println("key : " + key + " , value : "+value);
		}
		
		System.out.println( map.get("model") );
		
		map.clear();
		
		System.out.println( map.size() );

	}

}


