package com;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Allows to repeat test case several times.
 */
public class RepeatRule implements TestRule {

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD})
	public @interface Repeat {
		int times();
	}

	private static class RepeatStatement extends Statement {
		int times;
		Statement base;
		
		public RepeatStatement(int times, Statement base) {
			this.times = times;
			this.base = base;
		}

		@Override
		public void evaluate() throws Throwable {
			for (int i = 0; i < times; i ++) {
				base.evaluate();
			}
		}
	}
	
	@Override
	public Statement apply(Statement base, Description description) {
		Repeat repeat = description.getAnnotation(Repeat.class);
		if (repeat != null) {
			int times = repeat.times();
			return new RepeatStatement(times, base);
		} 
		return base;
	}
}
