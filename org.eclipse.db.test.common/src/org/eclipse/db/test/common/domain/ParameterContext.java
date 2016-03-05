package org.eclipse.db.test.common.domain;

public enum ParameterContext {
	/**
	 * Global scope
	 */
	Global,
	/**
	 * Local script/component
	 */
	Local,
	/**
	 * Runtime value. Used to compare with Etalon
	 */
	Runtime,
	/**
	 * Etalon value. Used to compare with Runtime
	 */
	Etalon,
	/**
	 * Constant is a value-type
	 */
	Constant
}
