package org.streameps.operator.assertion.trend;

public enum TrendType {
    INCREASING("increasing"), 
    DECREASING("decreasing"), 
    STABLE("stable"), 
    NON_INCREASING("non-increasing"), 
    NON_DECREASING("non-decreasing"),
    MIXED("mixed");
    private String name;

    /**
 * 
 */
    private TrendType(String name) {
	this.name = name;
    }
    
    public  TrendType getType (String type)
    {
	for(TrendType t:TrendType.values())
	{
	    if(t.name.equalsIgnoreCase(type))
		return t;
	}
	throw new IllegalArgumentException();
    }
    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return name;
    }
}
