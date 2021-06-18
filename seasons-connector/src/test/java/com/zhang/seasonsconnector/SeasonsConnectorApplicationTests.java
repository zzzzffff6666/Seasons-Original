package com.zhang.seasonsconnector;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
@Slf4j
class SeasonsConnectorApplicationTests {

	static class Text {
		private String name;
		public Text(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	@Test
	void contextLoads() {
		ArrayList<Text> list1 = new ArrayList<Text>() {{
			add(new Text("123"));
			add(new Text("456"));
			add(new Text("789"));
			add(new Text("000"));
		}};
		for (Text t : list1) {
			System.out.println(t.getName());
		}
		ArrayList<Text> list2 = new ArrayList<>();
		list2.add(list1.get(1));
		list2.add(list1.get(2));
		for (Text t : list2) {
			System.out.println(t.getName());
		}

		System.out.println(list1.get(1) == list2.get(0));
		System.out.println(list1.get(2) == list2.get(1));
	}

}
