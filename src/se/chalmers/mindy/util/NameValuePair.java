package se.chalmers.mindy.util;

/**
 * 
 * Used to store a name value pair. The name must be a text string, the value type is generic.
 * 
 * @author Viktor Åkerskog
 *
 */
public class NameValuePair<E> {

	String name;
	E value;

	public NameValuePair(final String name, final E value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public E getValue() {
		return value;
	}
}
