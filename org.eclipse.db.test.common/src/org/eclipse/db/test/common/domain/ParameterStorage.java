package org.eclipse.db.test.common.domain;

import java.util.HashMap;
import java.util.Map;

public class ParameterStorage {
	private Map<ParameterContext, Map<String, Object>> storage;
	
	public ParameterStorage() {
		storage = new HashMap<ParameterContext, Map<String, Object>>();
		for(ParameterContext param : ParameterContext.values()) {
			storage.put(param, new HashMap<String, Object>());
		}
	}
	
	public void add(ParameterContext context, String name, Object value) {
		storage.get(context).put(name, value);
	}
	
	public Object get(ParameterContext context, String name) {
		return storage.get(context).containsKey(name) ?  storage.get(context).get(name) : null;
	}
	
	public void set(ParameterContext context, String name, Object value) {
		/*for(Entry<String, Object> item : storage.get(context).entrySet()) {
			if (item.getKey().equals(name)) {
				item.setValue(value);
				return;
			}
		}*/
		add(context, name, value);
	}
	
	public Map<String, Object> getByContext(ParameterContext context) {
		return storage.get(context);
	}
	
	public Boolean contains(ParameterContext context, String name) {
		return storage.get(context).containsKey(name);
	}

}
