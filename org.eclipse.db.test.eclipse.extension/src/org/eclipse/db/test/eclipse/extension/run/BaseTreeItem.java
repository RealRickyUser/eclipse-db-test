package org.eclipse.db.test.eclipse.extension.run;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.db.test.eclipse.extension.Activator;
import org.eclipse.swt.graphics.Image;

public class BaseTreeItem {

	private List<BaseTreeItem> _items;
	private BaseTreeItem _parent;
	
	private Image _image;
	private String _imagePath;
	
	private String _text;
	
	public BaseTreeItem(BaseTreeItem parent) {
		_items = new ArrayList<BaseTreeItem>();
		_parent = parent;
	}
	

	public BaseTreeItem[] getElements(BaseTreeItem inputElement) {
		return null;
	}

	public BaseTreeItem[] getChildren() {
		if (_items.size() == 0)
			loadChilds();
		return _items.toArray(new BaseTreeItem[] {});
	}

	public BaseTreeItem getParent() {
		return _parent;
	}

	public boolean hasChildren() {
		return true;
	}


	public String getText() {
		return _text;
	}


	public void setText(String text) {
		_text = text;
	}
	
	public Image getImage() {
        if (_image == null && _imagePath != null) {
            _image = Activator.getImage(_imagePath);
        }
        return _image;
	}
	
	
	public void addChild(BaseTreeItem item) {
		_items.add(item);
	}
	
	public void insertFirstChild(BaseTreeItem item) {
		_items.add(0, item);
	}
	
	protected void loadChilds() {
	}
	
	public void setImagePath(String path) {
		_imagePath = path;
	}
}
