package org.eclipse.db.test.common.dal;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class CustomCallableStatement implements CallableStatement {

	@Override
	public void addBatch() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearParameters() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean execute() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ResultSet executeQuery() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int executeUpdate() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParameterMetaData getParameterMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setArray(int arg0, Array arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAsciiStream(int arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAsciiStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAsciiStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(int arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(int arg0, Blob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(int arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBoolean(int arg0, boolean arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setByte(int arg0, byte arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBytes(int arg0, byte[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(int arg0, Reader arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(int arg0, Clob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(int arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDate(int arg0, Date arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDate(int arg0, Date arg1, Calendar arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDouble(int arg0, double arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFloat(int arg0, float arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInt(int arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLong(int arg0, long arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(int arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(int arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNString(int arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNull(int arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNull(int arg0, int arg1, String arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setObject(int arg0, Object arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setObject(int arg0, Object arg1, int arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setObject(int arg0, Object arg1, int arg2, int arg3)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRef(int arg0, Ref arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRowId(int arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSQLXML(int arg0, SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setShort(int arg0, short arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setString(int arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTime(int arg0, Time arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTime(int arg0, Time arg1, Calendar arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTimestamp(int arg0, Timestamp arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTimestamp(int arg0, Timestamp arg1, Calendar arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setURL(int arg0, URL arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUnicodeStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addBatch(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancel() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearBatch() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean execute(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean execute(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean execute(String arg0, int[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean execute(String arg0, String[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int[] executeBatch() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet executeQuery(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int executeUpdate(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int executeUpdate(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int executeUpdate(String arg0, int[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int executeUpdate(String arg0, String[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFetchDirection() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFetchSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxRows() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getMoreResults(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getResultSetType() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getUpdateCount() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPoolable() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCursorName(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEscapeProcessing(boolean arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFetchDirection(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFetchSize(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMaxFieldSize(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMaxRows(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPoolable(boolean arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setQueryTimeout(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Array getArray(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Array getArray(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(int arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob getBlob(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob getBlob(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getBoolean(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getBoolean(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte getByte(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte getByte(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] getBytes(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getBytes(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getCharacterStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getCharacterStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clob getClob(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clob getClob(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(int arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(String arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getDouble(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDouble(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getFloat(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getFloat(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInt(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInt(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLong(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLong(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Reader getNCharacterStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getNCharacterStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(int arg0, Map<String, Class<?>> arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(String arg0, Map<String, Class<?>> arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getObject(int arg0, Class<T> arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getObject(String arg0, Class<T> arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ref getRef(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ref getRef(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RowId getRowId(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RowId getRowId(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public short getShort(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short getShort(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getString(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getString(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(int arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(String arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(int arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(String arg0, Calendar arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getURL(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getURL(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerOutParameter(int arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerOutParameter(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerOutParameter(int arg0, int arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerOutParameter(int arg0, int arg1, String arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerOutParameter(String arg0, int arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerOutParameter(String arg0, int arg1, String arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAsciiStream(String arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAsciiStream(String arg0, InputStream arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAsciiStream(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBigDecimal(String arg0, BigDecimal arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(String arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(String arg0, InputStream arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(String arg0, Blob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(String arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBoolean(String arg0, boolean arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setByte(String arg0, byte arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBytes(String arg0, byte[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(String arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(String arg0, Reader arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(String arg0, Clob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDate(String arg0, Date arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDate(String arg0, Date arg1, Calendar arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDouble(String arg0, double arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFloat(String arg0, float arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInt(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLong(String arg0, long arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(String arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(String arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNString(String arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNull(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNull(String arg0, int arg1, String arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setObject(String arg0, Object arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setObject(String arg0, Object arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setObject(String arg0, Object arg1, int arg2, int arg3)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRowId(String arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSQLXML(String arg0, SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setShort(String arg0, short arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setString(String arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTime(String arg0, Time arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTime(String arg0, Time arg1, Calendar arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTimestamp(String arg0, Timestamp arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTimestamp(String arg0, Timestamp arg1, Calendar arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setURL(String arg0, URL arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean wasNull() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
