/**
 * Project pack:tag >> http://packtag.sf.net
 *
 * This software is published under the terms of the LGPL
 * License version 2.1, a copy of which has been included with this
 * distribution in the 'lgpl.txt' file.
 * 
 * Last author:   $Author: danielgalan $
 * Last modified: $Date: 2008/01/26 12:53:27 $
 * Revision:      $Revision: 1.7 $
 * 
 * $Log: StyleTag.java,v $
 * Revision 1.7  2008/01/26 12:53:27  danielgalan
 * virtual path for combined resources (per resourcetype)
 *
 * Revision 1.6  2007/11/21 20:02:26  danielgalan
 * Don't force a charset, when pack:tag is not enabled
 *
 * Revision 1.5  2007/10/31 09:23:11  danielgalan
 * extends now PackTag
 *
 * Revision 1.4  2007/10/13 20:23:46  danielgalan
 * added charset support, MIME-Type abstraction
 *
 * Revision 1.3  2007/08/27 22:51:11  danielgalan
 * settings into property files instead of web.xml
 *
 * Revision 1.2  2007/05/02 21:29:19  danielgalan
 * last fixes for 2.0, attribute media
 *
 * Revision 1.1  2007/04/22 19:04:24  danielgalan
 * pack.tag moved from subversion to good old CVS
 *
 */
package net.sf.packtag.tag;

import javax.servlet.jsp.JspWriter;

import net.sf.packtag.implementation.IBloomCssPackStrategy;
import net.sf.packtag.strategy.PackStrategy;



/**
 * JSP Tag for compressing Cascading Style Sheet resources.
 * 
 * @author Daniel Galán y Martins
 * @version $Revision: 1.7 $
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
