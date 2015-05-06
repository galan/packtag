/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.tag;

import javax.servlet.jsp.JspWriter;

import net.sf.packtag.implementation.JsminPackStrategy;
import net.sf.packtag.strategy.PackStrategy;



/**
 * JSP Tag for compressing JavaScript resources.
 *
 * @author Daniel Galán y Martins
 */
public class ScriptTag extends PackTag {

	private static final long serialVersionUID = -5324074433734258409L;

	private final static String STRATEGY_NAME_JAVASCRIPT = "script";

	private final static String SCRIPT_START = "<script type=\"text/javascript\" src=\"";
	private final static String SCRIPT_END_SOLO = "</script>";
	private final static String SCRIPT_SRC_END = "\"";

	/** Name of the async attribute */
	private final static String ATTRIBUTE_ASYNC = "async";
	/** Name of the defer attribute */
	private final static String ATTRIBUTE_DEFER = "defer";

	/**
	* The charset is needed for IE to process utf-8 (IE isn't a webbrowser .. it's a disease)
	* All Browser (Firefox, Opera, .. evaluate the Content-Type header for each included resource (servlet).
	* But for the IE, the charset has to be specified in the link-reference :-/
	* Well, at least it's usefull for the cache.type "file" ;)
	*/
	private final static String OUTPUT_CHARSET = " charset=\"utf-8\"";
	private final static String OUTPUT_ASYNC_XHTML = " async=\"async\"";
	private final static String OUTPUT_DEFER_XHTML = " defer=\"defer\"";
	private final static String OUTPUT_ASYNC_HTML5 = " async";
	private final static String OUTPUT_DEFER_HTML5 = " defer";
	private final static String SCRIPT_END = ">" + SCRIPT_END_SOLO;

	private final static String MIME_TYPE_JAVASCRIPT = "text/javascript";
	private final static String EXTENSION_JAVASCRIPT = "js";

	/** If async is set to true, the async attribute is added */
	private Boolean async = Boolean.FALSE;
	/** If defer is set to true, the defer attribute is added */
	private Boolean defer = Boolean.FALSE;


	protected void writeResouce(final JspWriter writer, final String path) throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append(SCRIPT_START);
		buffer.append(path);
		buffer.append(SCRIPT_SRC_END);
		if (isEnabled()) {
			buffer.append(OUTPUT_CHARSET);
		}
		if (Boolean.TRUE.equals(isAsync())) {
			if (isScriptAsyncdeferXhtml()) {
				buffer.append(OUTPUT_ASYNC_XHTML);
			}
			else {
				buffer.append(OUTPUT_ASYNC_HTML5);
			}
		}
		if (Boolean.TRUE.equals(isDefer())) {
			if (isScriptAsyncdeferXhtml()) {
				buffer.append(OUTPUT_DEFER_XHTML);
			}
			else {
				buffer.append(OUTPUT_DEFER_HTML5);
			}
		}
		buffer.append(SCRIPT_END);
		writer.write(buffer.toString());
	}


	/** Should the resource be async? */
	public Boolean isAsync() {
		return async;
	}


	/** Sets if the resource should be async */
	public void setAsync(final String async) {
		if (isStandardTaglibAvailable()) {
			this.async = new Boolean((String)evaluate(ATTRIBUTE_ASYNC, async, String.class));
		}
		else {
			this.async = new Boolean(async);
		}
	}


	/** Should the resource be async? */
	public Boolean isDefer() {
		return defer;
	}


	/** Sets if the resource should be async */
	public void setDefer(final String defer) {
		if (isStandardTaglibAvailable()) {
			this.defer = new Boolean((String)evaluate(ATTRIBUTE_DEFER, defer, String.class));
		}
		else {
			this.defer = new Boolean(defer);
		}
	}


	protected String getStrategyName() {
		return STRATEGY_NAME_JAVASCRIPT;
	}


	protected PackStrategy getResourceDefaultStrategy() {
		return new JsminPackStrategy();
	}


	protected String getResourceExtension() {
		return EXTENSION_JAVASCRIPT;
	}


	protected String getMimeType() {
		return MIME_TYPE_JAVASCRIPT;
	}

}
