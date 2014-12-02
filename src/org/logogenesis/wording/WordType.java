package org.logogenesis.wording;

public class WordType {

	private static int nextId = 1;
	private final int id;
	private final String sort;
	private final String term;
	private final String form;
	private final int length;
	public WordType(String sort, String term, String form, int length) {
		this.id = nextId++;
		this.sort = sort;
		this.term = term;
		this.form = form;
		this.length = length;
	}
	public final String getSort() {
		return sort;
	}
	public final String getTerm() {
		return term;
	}
	public final String getForm() {
		return form;
	}
	public final int getLength() {
		return length;
	}
	public int getId() {
		return id;
	}
}
