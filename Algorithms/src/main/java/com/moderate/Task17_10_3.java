package com.moderate;

import static com.moderate.Task17_10_2.*;

import java.util.ArrayList;
import java.util.List;

import com.moderate.ParserTask17_10.MyAttr;
import com.moderate.ParserTask17_10.MyElement;
import com.moderate.Task17_10_2.ParseException;

public class Task17_10_3 {

	final String input;	
	int pos = 0;
	int prevPos = 0;
	
	public Task17_10_3(String input) {
		this.input = input;
	}
	
	public MyElement element() {
		nextExpected(OPEN_BRACKET);
		
		String name = name();
		MyElement element = new MyElement(name);
		
		if (SLASH.equals(peek())) { //elEmpty
			next();
			nextExpected(CLOSE_BRACKET);
		} else { //elContent
			element.attributes = attributes();
			nextExpected(CLOSE_BRACKET);
		
			if (isElementStart()) { //children
				element.children = children();				
			} else if (!OPEN_BRACKET.equals(peek())) {
				element.value = value();
			}
			
			endTag(name);
		}							
		return element;
	}		

	private void endTag(String name) {
		nextExpected(OPEN_BRACKET);
		nextExpected(SLASH);
		
		String closedName = name();
		if (!name.equals(closedName)) {
			error(String.format("Open: '%s' and close: '%s' names don't match", name, closedName), closedName);
		}
		
		nextExpected(CLOSE_BRACKET);
	}

	private List<MyElement> children() {
		List<MyElement> children = new ArrayList<>();
		while (isElementStart()) {
			children.add(element());
		}
		return children;
	}
	
	private List<MyAttr> attributes() {
		List<MyAttr> attrs = new ArrayList<>();
		while (isName(peek())) {
			String name = name();
			nextExpected(EQUAL_SIGN);
			String value = attrValue();
			attrs.add(new MyAttr(name, value));
		}		
		return attrs;
	}
	
	private String attrValue() {		
		return next();
	}

	private boolean isElementStart() {
		return OPEN_BRACKET.equals(peek(1)) && !SLASH.equals(peek(2));
	}	

	private String peek(int num) {
		return doNext(num, false);
	}

	private String doNext(int num, boolean remove) {
		if (pos == input.length()) {
			throw new IllegalStateException("End of stream");
		}		
		prevPos = pos;
		
		String str = null;
		for (int i = 0; i < num; i ++) { //look ahead
			StringBuilder res = new StringBuilder();
			String c;
			res.append(c = read());
			pos ++;
			if (!isSpecial(c)) {
				for (; pos < input.length() - 1 && !isSpecial(c = read()); pos ++) {
					res.append(c);
				}
			}
						
			for (; pos < input.length() - 1 && isSpace(read()); pos ++); //skip spaces
			
			str = res.toString();
		}
		
		if (!remove) {
			pos = prevPos;
		}		
		return str;
	}
	
	private boolean isSpace(String c) {
		for (String sp : SPACES) {
			if (sp.equals(c)) {
				return true;
			} 	
		}
		return false;
	}

	private boolean isSpecial(String c) {
		for (String sp : SPECIAL) {
			if (sp.equals(c) || isSpace(c)) {
				return true;
			} 					
		}
		return false;
	}			

	private String read() {		
		return String.valueOf(input.charAt(pos));
	}

	int getPos() {
		return prevPos;
	}

	private String value() {
		if (pos == input.length()) {
			throw new IllegalStateException("End of stream");
		}		
		prevPos = pos;
		
		StringBuilder res = new StringBuilder();
		String c;
		for (; pos < input.length() - 1 && !OPEN_BRACKET.equals((c = read())); pos ++) {
			res.append(c);
		}
		return res.toString();				
	}
	
	//letters, digits, underscore
	private boolean isName(String str) {
		for (int i = 0; i < str.length(); i ++) {
			char c = str.charAt(i);
			if (!Character.isAlphabetic(c) && !Character.isDigit(c) && c != '_') {
				return false;
			}
		}
		return true;
	}
	
	private String name() {
		String name = next();
		if (!isName(name)) {
			error("Not valid string", name);
		}
		return name;
	}

	void error(String msg, String token) {
		throw new ParseException(
			String.format("%s, token: '%s', pos: %d", msg, token, getPos()), token, getPos());			
	}
	
	void nextExpected(String expected) {
		String c = next();
		if (!c.equals(expected)) {
			error(String.format("Expected: '%s', was: '%s'", expected), expected);
		}
	}

	private String next() {
		return doNext(1, true);
	}
	
	private String peek() {
		return peek(1);
	}
}
