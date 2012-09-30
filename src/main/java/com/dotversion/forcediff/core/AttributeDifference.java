package com.dotversion.forcediff.core;

public class AttributeDifference {
    private final String attributeName;
    private final String attributeValueLeft;
    private final String attributeValueRight;

    public AttributeDifference(String name, String leftValue, String rightValue) {
        assert !leftValue.equals(rightValue);
        this.attributeName = name;
        this.attributeValueLeft = leftValue;
        this.attributeValueRight = rightValue;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getAttributeValueLeft() {
        return attributeValueLeft;
    }

    public String getAttributeValueRight() {
        return attributeValueRight;
    }

    @Override
    public String toString() {
        return String.format("Attribute name - %s, Left value - %s, Right value - %s", attributeName,
                attributeValueLeft, attributeValueRight);
    }

}