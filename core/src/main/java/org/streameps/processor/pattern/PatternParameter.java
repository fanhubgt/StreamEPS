package org.streameps.processor.pattern;

public class PatternParameter {

    private String propertyName;
    private String relation;
    private Object value;

    /**
     * 
     */
    public PatternParameter(String property, String rel, Object value) {
	this.propertyName = property;
	this.relation = rel;
	this.value = value;
    }

    /**
     * @return the propertyName
     */
    public String getPropertyName() {
	return propertyName;
    }

    /**
     * @return the relation
     */
    public String getRelation() {
	return relation;
    }

    /**
     * @return the value
     */
    public Object getValue() {
	return value;
    }

    /**
     * @param propertyName
     *            the propertyName to set
     */
    public void setPropertyName(String propertyName) {
	this.propertyName = propertyName;
    }

    /**
     * @param relation
     *            the relation to set
     */
    public void setRelation(String relation) {
	this.relation = relation;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(Object value) {
	this.value = value;
    }
}
