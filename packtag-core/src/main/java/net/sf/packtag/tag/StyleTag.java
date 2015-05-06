/* Project pack:tag >> https://github.com/galan/packtag */
package net.sf.packtag.tag;

import javax.servlet.jsp.JspWriter;

import net.sf.packtag.implementation.IBloomCssPackStrategy;
import net.sf.packtag.strategy.PackStrategy;



/**
 * JSP Tag for compressing Cascading Style Sheet resources.
 *
 * @author Daniel Galán y Martins
 */
public class StyleTag extends PackTag {

	private static final long serialVersionUID = -6557382191171304567L;

	private final static String STRATEGY_NAME_STYLE = "style";

	private static final String STYLE_START = "<link rel=\"stylesheet\" type=\"text/css\" href=\"";
	private static final String STYLE_MEDIA = "\" media=\"";
	private final static String OUTPUT_CHARSET = " charset=\"utf-8\"";
	private final static String STYLE_ATTRIBUTE_END = "\"";
	private static final String STYLE_END = "/>";
	private static final String ATTRIBUTE_MEDIA = "media";

	private final static String MIME_TYPE_STYLE = "text/css";
	private final static String EXTENSION_STYLE = "css";

	private String media;


	protected String getStrategyName() {
		return STRATEGY_NAME_STYLE;
	}


	protected PackStrategy getResourceDefaultStrategy() {
		return new IBloomCssPackStrategy();
	}


	protected void writeResouce(final JspWriter writer, final String path) throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append(STYLE_START);
		buffer.append(path);
		if ((getMedia() != null) && !getMedia().equals(EMPTY_STRING)) {
			buffer.append(STYLE_MEDIA);
			buffer.append(getMedia());
		}
		buffer.append(STYLE_ATTRIBUTE_END);
		if (isEnabled()) {
			buffer.append(OUTPUT_CHARSET);
		}
		buffer.append(STYLE_END);
		writer.write(buffer.toString());
	}


	protected String getResourceExtension() {
		return EXTENSION_STYLE;
	}


	public String getMedia() {
		return media;
	}


	public void setMedia(final String media) {
		if (isStandardTaglibAvailable()) {
			this.media = (String)evaluate(ATTRIBUTE_MEDIA, media, String.class);
		}
		else {
			this.media = media;
		}
	}


	protected String getMimeType() {
		return MIME_TYPE_STYLE;
	}

}
