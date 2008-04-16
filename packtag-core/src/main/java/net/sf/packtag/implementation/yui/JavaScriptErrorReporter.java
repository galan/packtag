package net.sf.packtag.implementation.yui;

/*
 * YUI Compressor
 * Author: Julien Lecomte <jlecomte@yahoo-inc.com>
 * Copyright (c) 2007, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 *     http://developer.yahoo.net/yui/license.txt
 */

import net.sf.packtag.util.SafeLogger;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;



public class JavaScriptErrorReporter implements ErrorReporter {

	public JavaScriptErrorReporter() {
	}


	public void warning(final String message, final String sourceName, final int line, final String lineSource, final int lineOffset) {
		reportMessage(message, sourceName, line, lineSource, lineOffset);
	}


	public EvaluatorException runtimeError(final String message, final String sourceName, final int line, final String lineSource, final int lineOffset) {
		error(message, sourceName, line, lineSource, lineOffset);
		return new EvaluatorException(message);
	}


	public void error(final String message, final String sourceName, final int line, final String lineSource, final int lineOffset) {
		reportMessage(message, sourceName, line, lineSource, lineOffset);
	}


	private void reportMessage(final String message, final String sourceName, final int line, final String lineSource, final int lineOffset) {
		if (line < 0) {
			if (message.length() == 0) {
				SafeLogger.error(JavaScriptErrorReporter.class, "An unknown error occurred while checking:" + sourceName);
			}
			else {
				SafeLogger.error(JavaScriptErrorReporter.class, message);
			}
		}
		else {
			SafeLogger.error(JavaScriptErrorReporter.class, line + ":" + lineOffset + ":" + message);
		}
	}

}