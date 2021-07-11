package com.ecsail.main;

import java.util.ArrayList;
import java.util.List;

public class GenericsInheritance {

	public static void main(String[] args) {
		List<String> names = new ArrayList<>();
		names.add("Name 1");
		names.add("Name 2");
		printList(names);

	}

	private static void printList(List<?> names) {
		names.forEach(System.out::println);
	}

}
