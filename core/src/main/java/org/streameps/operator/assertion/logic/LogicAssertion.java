package org.streameps.operator.assertion.logic;

import io.s4.schema.Schema;
import java.util.List;
import org.streameps.processor.pattern.PatternParameter;

public interface LogicAssertion {

    public boolean assertLogic(List<PatternParameter> map, Schema schema, Object event);
    
    public LogicType getType();
}
