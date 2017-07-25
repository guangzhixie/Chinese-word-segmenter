package com.w266.cws.enums;

public enum CharacterType {
    NUMBER("1"), DATE("2"), LETTER("3"), OTHER("4");

    private String typeNumber;

    CharacterType(String typeNumber) {
	this.typeNumber = typeNumber;
    }

    public String getTypeNumber() {
	return typeNumber;
    }
}
