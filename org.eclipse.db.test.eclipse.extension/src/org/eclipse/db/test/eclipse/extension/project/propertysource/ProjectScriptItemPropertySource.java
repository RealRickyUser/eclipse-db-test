package org.eclipse.db.test.eclipse.extension.project.propertysource;

import org.eclipse.db.test.common.domain.ScriptType;
import org.eclipse.db.test.eclipse.extension.project.ProjectScriptItem;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class ProjectScriptItemPropertySource implements IPropertySource {

	private static final String PROPERTY_NAME = "script.name";
	private static final String PROPERTY_TYPE = "script.type";
	
	private ProjectScriptItem _item;
	private IPropertyDescriptor[] _propertyDescriptors;
	
	private PropertyDescriptor nameProp;
	private ComboBoxPropertyDescriptor _typeProp;
	
	public ProjectScriptItemPropertySource(ProjectScriptItem item) {
		_item = item;
	}
	
	@Override
	public Object getEditableValue() {
		return null;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		if (!_item.isLoaded())
			return new IPropertyDescriptor[]{};
		if (_propertyDescriptors == null)
			loadPropertyDescriptors();
		return _propertyDescriptors;
	}
	
	private void loadPropertyDescriptors() {
		nameProp = new TextPropertyDescriptor(PROPERTY_NAME, "Название");
		_typeProp = new ComboBoxPropertyDescriptor(PROPERTY_TYPE, "Тип", new String[] {"Действие", "Ожидание"});
		
		_propertyDescriptors = new IPropertyDescriptor[] {
				nameProp,
				_typeProp
		};
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals(PROPERTY_NAME)) {
			return _item.getText();
		}
		else if (id.equals(PROPERTY_TYPE) && _item.isLoaded()) {
			return _item.getScript().getType().ordinal();
		}
		return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {

	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if (!_item.isLoaded())
			return;
		if (id.equals(PROPERTY_NAME)) {
			_item.getScript().setName(value.toString());
			_item.raisePropertyChanged();
		}
		if (id.equals(PROPERTY_TYPE)) {
			_item.getScript().setType(ScriptType.values()[(int)value]);
			_item.raisePropertyChanged();
		}

	}

}
