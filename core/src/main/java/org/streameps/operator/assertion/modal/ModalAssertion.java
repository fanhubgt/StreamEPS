package org.streameps.operator.assertion.modal;

import io.s4.schema.Schema;

import java.util.List;
import org.streameps.processor.pattern.PatternParameter;

public interface ModalAssertion {

    public boolean assertModel(List<PatternParameter> map, Schema schema,
	    Object event);
}
