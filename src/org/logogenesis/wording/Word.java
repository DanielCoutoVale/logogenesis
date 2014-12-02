package org.logogenesis.wording;

/**
 * A word as a part of a wording.
 * 
 * @author Daniel Couto Vale <danielvale@uni-bremen.de>
 */
public class Word {

	private final String wording;
	private final int id;
	private final int start;
	private final int length;
	private final int[] types;

	public Word(String wording, int id, int start, int length, int[] types) {
		this.wording = wording;
		this.id = id;
		this.start = start;
		this.length = length;
		this.types = types;
	}

	public final String getWording() {
		return wording;
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

	public final String getPattern() {
		return wording.substring(getStart(), getStart() + getLength());
	}

	public int getId() {
		return id;
	}

}
