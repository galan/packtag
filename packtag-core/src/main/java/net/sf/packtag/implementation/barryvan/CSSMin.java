package net.sf.packtag.implementation.barryvan;

import java.io.Writer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.PatternSyntaxException;



public class CSSMin {

	public void formatFile(final String input, final Writer writer) throws Exception {
		try {
			StringBuffer sb = new StringBuffer(input.replaceAll("[\t\n\r]", "").replaceAll("  ", " "));
			int k, n;

			//System.out.println("Stripping comments...");
			// Find the start of the comment
			while((n = sb.indexOf("/*")) != -1) {
				k = sb.indexOf("*/", n + 2);
				if (k == -1) {
					throw new Exception("Error: Unterminated comment.");
				}
				sb.delete(n, k + 2);
			}
			//System.out.println("Finished stripping comments.");

			//System.out.println("Extracting & parsing selectors...");
			Vector selectors = new Vector();
			n = 0;
			while((k = sb.indexOf("}", n)) != -1) {
				try {
					selectors.addElement(new Selector(sb.substring(n, k + 1)));
				}
				catch (Exception e) {
					//System.out.println("  Error parsing selector: skipping...");
				}
				n = k + 1;
			}
			//System.out.println("Finished extracting & parsing selectors.");

			//System.out.println("Pretty-printing output...");
			Iterator iterSelectors = selectors.iterator();
			while(iterSelectors.hasNext()) {
				Selector selector = (Selector)iterSelectors.next();
				writer.write(selector.toString());
			}
			writer.write("\r\n");
			writer.flush();
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}
}



class Selector {

	private final Property[] properties;
	private final String selector;


	/**
	 * Creates a new Selector using the supplied strings.
	 * @param selector The selector; for example, "div { border: solid 1px red; color: blue; }"
	 */
	public Selector(final String selector) throws Exception {
		String[] parts = selector.split("\\{"); // We have to escape the { with a \ for the regex, which itself requires escaping for the string. Sigh.
		if (parts.length < 2) {
			throw new Exception("Warning: Incomplete selector: " + selector);
		}
		this.selector = parts[0].trim();
		String contents = parts[1].trim();
		if (contents.length() == 0) {
			throw new Exception("Warning: Empty selector body: " + selector);
		}
		if (contents.charAt(contents.length() - 1) != '}') { // Ensure that we have a leading and trailing brace.
			throw new Exception("Warning: Unterminated selector: " + selector);
		}
		contents = contents.substring(0, contents.length() - 2);
		properties = parseProperties(contents);
		sortProperties(properties);
	}


	/**
	 * Prints out this selector and its contents nicely, with the contents sorted alphabetically.
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(selector).append("{");
		for (int i = 0; i < properties.length; i++) {
			sb.append(properties[i].toString());
		}
		sb.append("}");
		return sb.toString();
	}


	/**
	 * Parses out the properties of a selector's body.
	 * @param contents The body; for example, "border: solid 1px red; color: blue;"
	 */
	private Property[] parseProperties(final String contents) {
		String[] parts = contents.split(";");
		Property[] results = new Property[parts.length];

		for (int i = 0; i < parts.length; i++) {
			try {
				results[i] = new Property(parts[i]);
			}
			catch (Exception e) {
				System.err.println(e.getMessage());
				results[i] = null;
			}
		}

		return results;
	}


	private void sortProperties(final Property[] propertiesToSort) {
		Arrays.sort(propertiesToSort);
	}
}



class Property implements Comparable {

	protected String property;
	protected Value[] values;


	/**
	 * Creates a new Property using the supplied strings. Parses out the values of the property selector.
	 * @param property The property; for example, "border: solid 1px red;" or "-moz-box-shadow: 3px 3px 3px rgba(255, 255, 0, 0.5);".
	 */
	public Property(final String property) throws Exception {
		try {
			// Parse the property.
			String[] parts = property.split(":"); // Split "color: red" to ["color", " red"]
			if (parts.length < 2) {
				throw new Exception("Warning: Incomplete property: " + property);
			}
			this.property = parts[0].trim().toLowerCase();

			values = parseValues(parts[1].trim().replaceAll(", ", ","));

		}
		catch (PatternSyntaxException e) {
			// Invalid regular expression used.
		}
	}


	/**
	 * Prints out this property nicely, with the contents sorted in a standardised order.
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(property).append(":");
		for (int i = 0; i < values.length; i++) {
			Value v = values[i];
			sb.append(v.toString()).append(",");
		}
		sb.deleteCharAt(sb.length() - 1); // Delete the trailing comma.
		sb.append(";");
		return sb.toString();
	}


	public int compareTo(final Object other) {
		return property.compareTo(((Property)other).property);
	}


	private Value[] parseValues(final String contents) {
		String[] parts = contents.split(",");
		Value[] results = new Value[parts.length];

		for (int i = 0; i < parts.length; i++) {
			try {
				results[i] = new Value(parts[i]);
			}
			catch (Exception e) {
				System.err.println(e.getMessage());
				results[i] = null;
			}
		}

		return results;
	}
}



class Value {

	String[] parts;


	public Value(final String value) throws Exception {
		// Parse the value.
		parts = value.split(" "); // Split "solid 1px red" to ["solid","1px","red"] and sort them.
	}


	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < parts.length; i++) {
			sb.append(parts[i]).append(" ");
		}
		return sb.toString().trim();
	}
}
