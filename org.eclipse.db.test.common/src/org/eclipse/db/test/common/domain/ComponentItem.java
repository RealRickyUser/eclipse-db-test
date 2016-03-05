package org.eclipse.db.test.common.domain;

public class ComponentItem extends BaseParametredItem<ComponentParameterItem> {
	private String _mappedParams;
	private int _orderId;
	
	public ComponentItem() {
		super();
	}
	
	public ComponentItem(int id, String name) {
		super(id, name);
	}
	
	
	public void loadParamsFromMapping() {
		if (_mappedParams == null || _mappedParams.isEmpty())
			return;
		String[] strParams = _mappedParams.split("\\|");
		for(String param : strParams) {
			loadParameterFromString(param);
		}
	}
	
	private void loadParameterFromString(String strParam) {
		if (strParam == null || strParam.isEmpty())
			return;
		String[] prepared = strParam.split("=", 2);
		if (prepared.length != 2)
			return;
		String mapped = prepared[0];
		if (mapped.isEmpty())
			return;
		String[] mappedParams = mapped.split(";");
		if (mappedParams.length != 2)
			return;
		ParameterContext mappedCtx = ParameterContext.valueOf(mappedParams[0]);
		mapped = mappedParams[1];
		String cont = prepared[1];
		if (cont.isEmpty())
			return;
		String[] params = cont.split(";");
		if (params.length != 2)
			return;
		String context = params[0];
		String name = params[1];
		if (context.isEmpty() || name.isEmpty())
			return;
		ParameterContext ctx = ParameterContext.valueOf(context);
		if (ctx== null)
			return;
		updateParameter(name, ctx, mapped, mappedCtx);
	}
	
	private void updateParameter(String name, ParameterContext ctx, String mapped, ParameterContext mappedCtx) {
		for (ComponentParameterItem pi : getParameters()) {
			if (!pi.getMapped().equals(mapped) || pi.getMappedContext() != mappedCtx)
				continue;
			pi.setName(name);
			pi.setContext(ctx);
			pi.setMappedContext(mappedCtx);
			return;
		}
	}
	

	public String getMappedParams() {
		return _mappedParams;
	}

	public void setMappedParams(String mappedParams) {
		_mappedParams = mappedParams;
	}
	
	public void saveParamsToMapping() {
		setMappedParams("");
		for (ComponentParameterItem pi : getParameters()) {
			if (pi.getName() == null || pi.getName().isEmpty())
				continue;
			if (pi.getMapped() == null || pi.getMapped().isEmpty())
				continue;
			_mappedParams += formatParamString(pi);
		}
	}
	
	protected String formatParamString(ComponentParameterItem pi) {
		return String.format("%s;%s=%s;%s|", pi.getMappedContext(), pi.getMapped(), pi.getContext(), pi.getName());
	}

	public int getOrderId() {
		return _orderId;
	}

	public void setOrderId(int orderId) {
		_orderId = orderId;
	}
	
	public ComponentParameterItem findParameter(String mapped, ParameterContext mappedCtx)  {
		for (ComponentParameterItem item : getParameters()) {
			if (item.getMapped().equals(mapped) && item.getMappedContext() == mappedCtx)
				return item;
		}
		return null;
	}

}
