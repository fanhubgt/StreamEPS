package org.streameps.operator.assertion;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FunctorRegistry {

    private ConcurrentMap<String, Functor> registry = new ConcurrentHashMap<String, Functor>();

    public void addFunctor(String name, Functor functor) {
	registry.putIfAbsent(name, functor);
    }

    public void removeFunctor(String name) {
	for (String fucnt : registry.keySet()) {
	    if (name.equalsIgnoreCase(fucnt)) {
		registry.remove(fucnt);
		return;
	    }
	}
    }

    public int getCount() {
	return registry.size();
    }
    
    /**
     * @param registry the registry to set
     */
    public void setRegistry(ConcurrentMap<String, Functor> registry) {
	this.registry = registry;
    }
}
