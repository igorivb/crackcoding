package com.moderate;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.moderate.Task17_10.MyAttr;
import com.moderate.Task17_10.MyElement;

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
		int getPos(); //for error handling
		void put(String token, int pos);
	}
	
	
	private static class TokenInfo {
		final String token;
		final int pos;
		public TokenInfo(String token, int pos) {		
			this.token = token;
			this.pos = pos;
		}				
	}
	
	public static class MyStringTokenizer implements MyStream {
		final String str;
		int pos = 0;
		int prevPos = 0; //changed on next and peek 
		
		//use buffer if it is not empty instead of stream
		Stack<TokenInfo> buffer = new Stack<>();
		
		public MyStringTokenizer(String str) {
			this.str = str;
		} 
		
		@Override
		public String next() {
			return doNext(true);
		}
		
		@Override
		public String peek() {	
			return doNext(false);
		}
		
		@Override
		public int getPos() {
			return this.prevPos;
		}
		
		@Override
		public void put(String token, int pos) {
			buffer.push(new TokenInfo(token, pos));			
		}	
		
		private String doNext(boolean remove) {			
			if (!buffer.isEmpty()) {
				TokenInfo res = remove ? buffer.pop() : buffer.peek();				
				prevPos = res.pos;
				return res.token;
			}
			
			if (pos == str.length()) {
				throw new IllegalStateException("End of stream");
			}
			
			prevPos = pos;
			
			StringBuilder res = new StringBuilder();			
			String c = read();
			if (isSpecial(c)) {
				res.append(c);
				pos ++;
			} else {
				while (pos < str.length() && !isSpecial(c = read())) {				
					res.append(c);
					pos ++;
				}
			}
										
			for (; pos < str.length() && isSpace(c = read()); pos ++); //skip spaces if any			
			
			if (!remove) {
				pos = prevPos;
			}
			
			return res.toString();
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
			String c = stream.next();
			if (!c.equals(OPEN_BRACKET)) {
				error("Expected: " + OPEN_BRACKET, c, stream);
			}
			
			String name = name(stream);
			MyElement elem = new MyElement(name);
			
			if (SLASH.equals(stream.peek())) { //empty tag without body
				stream.next();
				if (!CLOSE_BRACKET.equals((c = stream.next()))) {
					error("Expected: " + CLOSE_BRACKET, c, stream);
				}	
			} else {
				elem.attributes = attributes(stream);
				
				if (!CLOSE_BRACKET.equals(c = stream.next())) {
					error("Expected: " + CLOSE_BRACKET, c, stream);
				}
															
				if (isValue(c = stream.peek())) { //value
					elem.value = value(stream);
				} else if (isNewTag(stream)) { //children
					elem.children = children(stream);
				}
				
				closeTag(name, stream);		
			}						
			
			return elem;
		}				

		private void closeTag(String name, MyStream stream) {
			String c;
			if (!OPEN_BRACKET.equals((c = stream.next()))) {
				error("Expected: " + OPEN_BRACKET, c, stream);										
			}
			if (!SLASH.equals((c = stream.next()))) {
				error("Expected: " + SLASH, c, stream);										
			}
															
			String closedName = name(stream);
			if (!closedName.equals(name)) {
				error(String.format("Open: '%s' and close: '%s' names don't match", name, closedName), closedName, stream);
			}
			
			if (!CLOSE_BRACKET.equals((c = stream.next()))) {
				error("Expected: " + CLOSE_BRACKET, c, stream);
			}			
		}

		boolean isNewTag(MyStream stream) {
			String c = stream.next();
			int pos = stream.getPos();
			boolean newTag = OPEN_BRACKET.equals(c) && isName(stream.peek());
			stream.put(c, pos);
			return newTag;
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
			
			String c;
			while (isName((c = stream.peek()))) {
				String name = name(stream);
				
				if (!EQUAL_SIGN.equals((c = stream.next()))) {
					error("Expected: " + EQUAL_SIGN, c, stream);
				}
				
				String value = value(stream);
				
				MyAttr attr = new MyAttr(name, value);
				attrs.add(attr);
			}
			
			return attrs;
		}

		String name(MyStream stream) {
			return chars(stream);			
		}
		
		String value(MyStream stream) {
			return chars(stream);		
		}
		
		boolean isName(String str) {
			return isChars(str);
		}
		
		//TODO value may contain spaces: need to preserve original formatting
		boolean isValue(String str) {
			return isChars(str);
		}
		
		//letters, digits, underscore
		boolean isChars(String str) {
			//should be alphabetic
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
	}
}
