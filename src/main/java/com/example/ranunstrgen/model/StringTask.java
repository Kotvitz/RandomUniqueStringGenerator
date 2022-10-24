package com.example.ranunstrgen.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "taskdb", name = "string_task")
public class StringTask {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "minLength")
	private int minLength;
	
	@Column(name = "maxLength")
	private int maxLength;
	
	@Column(name = "numberOfStrings")
	private Long numberOfStrings;
	
	@Column(name = "possibleChars")
	private char[] possibleChars;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public Long getNumberOfStrings() {
		return numberOfStrings;
	}

	public void setNumberOfStrings(Long numberOfStrings) {
		this.numberOfStrings = numberOfStrings;
	}

	public char[] getPossibleChars() {
		return possibleChars;
	}

	public void setPossibleChars(char[] possibleChars) {
		this.possibleChars = possibleChars;
	}
}
