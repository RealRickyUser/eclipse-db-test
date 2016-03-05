package org.eclipse.db.test.common.domain;

/**
 * Класс хранит упрощенную информацию о скрипте для ScriptComponent
 *
 */
public class ScriptItem extends BaseParametredItem<ParameterItem> {
	private String _mappedParams;
	private int _orderId;
	
	public ScriptItem() {
		super();
	}
	
	public ScriptItem(int id, String name) {
		super(id, name);
	}

	public String getMappedParams() {
		return _mappedParams;
	}

	public void setMappedParams(String mappedParams) {
		_mappedParams = mappedParams;
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
		updateParameter(name, ctx, mapped);
	}
	
	private void updateParameter(String name, ParameterContext ctx, String mapped) {
		for (ParameterItem pi : getParameters()) {
			if (!pi.getMapped().equals(mapped))
				continue;
			pi.setName(name);
			pi.setContext(ctx);
			return;
		}
	}
	
	public void saveParamsToMapping() {
		setMappedParams("");
		for (ParameterItem pi : getParameters()) {
			if (pi.getName() == null || pi.getName().isEmpty())
				continue;
			if (pi.getMapped() == null || pi.getMapped().isEmpty())
				continue;
			_mappedParams += String.format("%s=%s;%s|", pi.getMapped(), pi.getContext(), pi.getName());
		}
	}

	public int getOrderId() {
		return _orderId;
	}

	public void setOrderId(int orderId) {
		_orderId = orderId;
	}
}
