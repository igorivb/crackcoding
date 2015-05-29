package com.moderate;

import java.util.ArrayList;
import java.util.List;

import com.moderate.Task17_10.MyAttr;
import com.moderate.Task17_10.MyElement;
/**
 * Grammar:
 * 
element:
	'<'name (elContent | elEmpty) '>'
elContent:
	attribute* '>' (value | children)? </name>
attribute:
	name'='value
children:
	element+
elEmpty:
	'/'
name:
	chars
value:
	chars
chars:
	([letters] [digits] '_')+
WHITESPACE: 
	(' ' | '\t')+
	
Special: '<', '>', '/', '=', WHITESPACE
 *
 */
public class Task17_10_2 {

	public final static String OPEN_BRACKET = "<"; 
	public final static String CLOSE_BRACKET = ">";
	public final static String EQUAL_SIGN = "=";
	public final static String SLASH = "/";	
	private final static String[] SPACES = {" ", "\t" };			
	private final static String[] SPECIAL = { OPEN_BRACKET, CLOSE_BRACKET, SLASH, EQUAL_SIGN };
	
	public static class ParseException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		
		public String token;
		public int pos;
		
		public ParseException(String msg, String token, int pos) {
			super(msg);
			this.token = token;
			this.pos = pos;
		 }		
	}
	
	public interface MyStream {
		String next();
		String peek(); //don't remove read element from stream
		String peek(int lookAhead);	
		int getPos(); //for error handling
		String value();	//get element value
	}
	
	public static class MyStringTokenizer implements MyStream {
		final String str;
		int pos = 0;
		int prevPos = 0; //changed on next and peek 
		
		public MyStringTokenizer(String str) {
			this.str = str;
		} 				
		
		@Override
		public String next() {
			return doNext(true, 1);
		}
		
		@Override
		public String peek() {	
			return doNext(false, 1);
		}
		
		@Override
		public String peek(int lookAhead) {	
			return doNext(false, lookAhead);
		}
		
		@Override
		public int getPos() {
			return this.prevPos;
		}
		
		private String doNext(boolean remove, int lookAhead) {			
			prevPos = pos;
			
			String res = null;
			for (int i = 0; i < lookAhead; i ++) {
				if (pos == str.length()) {
					throw new IllegalStateException("End of stream");
				}
				
				StringBuilder tmp = new StringBuilder();			
				String c = read();
				if (isSpecial(c)) {
					tmp.append(c);
					pos ++;
				} else {
					while (pos < str.length() && !isSpecial(c = read())) {				
						tmp.append(c);
						pos ++;
					}
				}
											
				for (; pos < str.length() && isSpace(c = read()); pos ++); //skip spaces if any
				
				res = tmp.toString();
			}
											
			if (!remove) {
				pos = prevPos;
			}
			
			return res;
		}
		
		@Override
		public String value() {						
			if (pos == str.length()) {
				throw new IllegalStateException("End of stream");
			}
			
			prevPos = pos;
			
			//read till we encounter open bracket
			StringBuilder tmp = new StringBuilder();
			String s;
			for (; pos < str.length() && !OPEN_BRACKET.equals((s = read())); pos ++) {
				tmp.append(s);
			}
			
			return tmp.toString();
		}
		
		private String read() {
			return String.valueOf(str.charAt(pos));
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
	}
	
	public static class MyParser {
		
		public MyElement element(MyStream stream) {		
			nextExpected(stream, OPEN_BRACKET);			
			
			String name = name(stream);
			MyElement elem = new MyElement(name);
			
			if (SLASH.equals(stream.peek())) { //empty tag without body
				stream.next();				
				nextExpected(stream, CLOSE_BRACKET);			
			} else {
				elem.attributes = attributes(stream);
				
				nextExpected(stream, CLOSE_BRACKET);				
																		
				if (!OPEN_BRACKET.equals(stream.peek())) { //value
					elem.value = value(stream);
				} else if (isNewTag(stream)) { //children
					elem.children = children(stream);
				}
				
				closeTag(name, stream);		
			}						
			
			return elem;
		}				

		private void closeTag(String name, MyStream stream) {			
			nextExpected(stream, OPEN_BRACKET);
			nextExpected(stream, SLASH);						
															
			String closedName = name(stream);
			if (!closedName.equals(name)) {
				error(String.format("Open: '%s' and close: '%s' names don't match", name, closedName), closedName, stream);
			}
			
			nextExpected(stream, CLOSE_BRACKET);						
		}

		boolean isNewTag(MyStream stream) {			
			return OPEN_BRACKET.equals(stream.peek(1)) && isName(stream.peek(2));			
		}
		
		private List<MyElement> children(MyStream stream) {
			List<MyElement> children = new ArrayList<>();
			while (isNewTag(stream)) {		
				children.add(element(stream));				
			}					
			return children;
		}

		List<MyAttr> attributes(MyStream stream) {
			List<MyAttr> attrs = new ArrayList<>();
			
			while (isName(stream.peek())) {
				String name = name(stream);
				
				nextExpected(stream, EQUAL_SIGN);				
				
				String value = attrValue(stream);
				
				MyAttr attr = new MyAttr(name, value);
				attrs.add(attr);
			}
			
			return attrs;
		}

		String name(MyStream stream) {
			return chars(stream);			
		}
		
		String value(MyStream stream) {
			return stream.value();		
		}
		
		String attrValue(MyStream stream) {
			return chars(stream);		
		}
				
		boolean isName(String str) {
			return isChars(str);
		}
		
		//letters, digits, underscore
		boolean isChars(String str) {			
			for (int i = 0; i < str.length(); i ++) {
				char c = str.charAt(i);
				if (!Character.isAlphabetic(c) && !Character.isDigit(c) && c != '_') {
					return false;
				}
			}
			return true;
		}

		private String chars(MyStream stream) {
			String str = stream.next();
			if (!isChars(str)) {
				error("Not valid string", str, stream);
			}									
			return str;
		}

		private void error(String msg, String token, MyStream stream) {
			throw new ParseException(
				String.format("%s, token: '%s', pos: %d", msg, token, stream.getPos()), token, stream.getPos());			
		}
		
		void nextExpected(MyStream stream, String expected) {
			String c = stream.next();
			if (!c.equals(expected)) {
				error(String.format("Expected: '%s'", expected), c, stream);
			}
		}
	}
}
