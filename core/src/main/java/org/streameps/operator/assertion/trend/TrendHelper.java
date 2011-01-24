package org.streameps.operator.assertion.trend;

import io.s4.schema.Schema.Property;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class TrendHelper implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 3991890316133375487L;
    private Map<Integer, Property> trendMap = new HashMap<Integer, Property>();
    private Logger logger = Logger.getLogger(TrendHelper.class);

    public void putTrendValue(Integer count, Property trend) {
	trendMap.put(count, trend);
	logger.info("Trend value count:" + count);
    }

    public Property getTrendValue(int position) {
	Property property = trendMap.get(position);
	if (property != null)
	    return property;
	throw new IllegalArgumentException();
    }

    /**
     * @return the trendMap
     */
    public Map<Integer, Property> getTrendMap() {
	return trendMap;
    }

}
