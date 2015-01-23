package org.logogenesis.wording;

/**
 * A word realised by a segment of a character sequence.
 * 
 * @author Daniel Couto-Vale
 */
public class Word {

	private final String chars;
	private final int id;
	private final int start;
	private final int length;
	private final int[] types;

	public Word(String chars, int id, int start, int length, int[] types) {
		this.chars = chars;
		this.id = id;
		this.start = start;
		this.length = length;
		this.types = types;
	}

	public final String getCharacterSequence() {
		return chars;
	}

	public final int getStart() {
		return start;
	}

	public final int getLength() {
		return length;
	}

	public final int getEnd() {
		return getStart() + getLength();
	}

	public final int[] getTypes() {
		return types;
	}

	public final String getForm() {
		return chars.substring(getStart(), getStart() + getLength());
	}

	public final int getId() {
		return id;
	}

}
