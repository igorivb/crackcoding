package com.moderate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.moderate.Task17_10_2.MyParser;
import com.moderate.Task17_10_2.MyStream;
import com.moderate.Task17_10_2.MyStringTokenizer;

/**
 * Since XML is very verbose, you are given a way of encoding it where each tag gets
 * mapped to a pre-defined integer value
 */
public class Task17_10 {

	static class MyElement {
		String name;
		String value;
		List<MyAttr> attributes = new ArrayList<>();
		List<MyElement> children = new ArrayList<>();
		public MyElement(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			StringBuilder str = new StringBuilder();
			str.append("<" + this.name);
					
			for (MyAttr attr : attributes) {
				str.append(" " + attr.name + "=" + attr.value);
			}			
			str.append(">");
			
			if (!children.isEmpty()) {
				for (MyElement el : children) {
					str.append("\n");
					str.append(el);
				}
				str.append("\n");
			} else if (this.value != null) {
				str.append(value);
			}
					
			str.append("</" + this.name + ">");
			return str.toString();
		}
	}
	
	static class MyAttr {
		String name;
		String value;
		public MyAttr(String name, String value) {
			super();
			this.name = name;
			this.value = value;
		}	
		
		@Override
		public String toString() {			
			return name + "=" + value;
		}
	}
	
	final static int END = 0; 
	
	static void add(StringBuilder res, Object val) {
		if (res.length() > 0) {
			res.append(" ");
		}
		res.append(val);
	}
	
	static void encode(MyElement el, Map<String, Integer> mappings, StringBuilder res) {
		add(res, mappings.get(el.name));
		
		if (!el.attributes.isEmpty()) { //attributes
			for (MyAttr attr : el.attributes) {
				add(res, mappings.get(attr.name));
				add(res, attr.value);
			}
			add(res, END);
		}
		
		if (!el.children.isEmpty()) { //children
			for (MyElement child : el.children) {
				encode(child, mappings, res);
			}			
		} else if (el.value != null) { //value
			add(res, el.value);
		}
		
		add(res, END);
	}		
	
	public static String encode(MyElement el, Map<String, Integer> mappings) {
		StringBuilder res = new StringBuilder();
		encode(el, mappings, res);
		return res.toString();
	}	
	
	static void addMapping(Map<String, Integer> mappings, String name, int val) {
		if (!mappings.containsKey(name)) {
			mappings.put(name, val);
		}		
	}
	
	public static void main(String[] args) {
		String[] inputs = {
			"<family/>", 
			"<a>aaa</a>",
			"<a f=ff	d=dd>aaa</a>",
			"<c></c>",
			"<a f=f1 	   	dd=dd><c_c>some</c_c></a>",
			"<note><to>Tove</to><from>Jani</from><heading>Reminder</heading><body>Don't forget me this weekend!</body></note>"
		};
		
		for (String input : inputs) {			
			System.out.println("----- Input: " + input);
			
			MyStream stream = new MyStringTokenizer(input);
			MyElement root = new MyParser().element(stream); //parse		
			
			System.out.println("\nParsed element:\n" + root);
			
			//fill mappings
			Map<String, Integer> mappings = new HashMap<>();
			Queue<MyElement> q = new LinkedList<>();		
			q.add(root);
			int c = 1;
			while (!q.isEmpty()) {
				MyElement el = q.remove();
				addMapping(mappings, el.name, c ++);
				
				for (MyAttr attr : el.attributes) {
					addMapping(mappings, attr.name, c ++);
					addMapping(mappings, attr.value, c ++);
				}
				
				if (el.value != null) {
					addMapping(mappings, el.value, c ++);				
				} else {
					for (MyElement child : el.children) {
						q.add(child);
					}
				}
			}
			
			System.out.println("\nMappings:");
			for (Map.Entry<String, Integer> entry : mappings.entrySet()) {
				System.out.printf("\t%s=%d%n", entry.getKey(), entry.getValue());
			}
			
			System.out.println();
			String res = encode(root, mappings);
			System.out.println("Encoded: " + res);
			
			System.out.println();
		}				
	}

}
