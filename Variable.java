public class Variable {
	private String type;
	private Object value;

	public Variable(String type) {
		this.type = type;
	}

	public String type() {
		return type;
	}

	/**
	 * Sets this variable's value.
	 * @param key key of value
	 * @param value to store
	 */
	public void put(boolean value) {
		this.value = value;
	}

	/**
	 * Sets this variable's value.
	 * @param key key of value
	 * @param value to store
	 */
	public void put(int value) {
		this.value = value;
	}

	/**
	 * Sets this variable's value.
	 * @param key key of value
	 * @param value to store
	 */
	public void put(float value) {
		this.value = value;
	}

	/**
	 * Sets this variable's value.
	 * @param key key of value
	 * @param value to store
	 */
	public void put(double value) {
		this.value = value;
	}

	/**
	 * Puts an longinto this nonterminals value map.
	 * @param key key of value
	 * @param value to store
	 */
	public void put(long value) {
		this.value = value;
	}

	/**
	 * Sets this variable's value.
	 * @param key key of value
	 * @param value to store
	 */
	public void put(String value) {
		this.value = value;
	}

	/**
	 * Sets this variable's value.
	 * @param key key of value
	 * @param value to store
	 */
	public void put(Object value) {
		this.value = value;
	}

	/**
	 * Sets this variable's value.
	 * @param key key of value
	 * @param value to store
	 */
	public void put(Object[] value) {
		this.value = value;
	}

	/**
	 * Returns the value of this variable
	 * @param key key of value
	 * @return value stored
	 */
	public boolean getAsBoolean() {
		return (boolean)value;
	}

	/**
	 * Returns the value of this variable
	 * @param key key of value
	 * @return value stored
	 */
	public int getAsInt() {
		return (int)value;
	}

	/**
	 * Returns the value of this variable
	 * @param key key of value
	 * @return value stored
	 */
	public float getAsFloat() {
		return (float)value;
	}

	/**
	 * Returns the value of this variable
	 * @param key key of value
	 * @return value stored
	 */
	public double getAsDouble() {
		return (double)value;
	}

	/**
	 * Returns the value of this variable
	 * @param key key of value
	 * @return value stored
	 */
	public long getAsLong() {
		return (long)value;
	}

	/**
	 * Returns the value of this variable
	 * @param key key of value
	 * @return value stored
	 */
	public String getAsString() {
		return (String)value;
	}

	/**
	 * Returns the value of this variable
	 * @param key key of value
	 * @return value stored
	 */
	public Object getAsObject() {
		return value;
	}

	/**
	 * Returns the value of this variable
	 * @param key key of value
	 * @return value stored
	 */
	public Object[] getAsArray() {
		return (Object[])value;
	}

	public String toString() {
		String ret = type + " - ";
		if( type.equals("array")) {
			Object[] objs = getAsArray();
			for(int i = 0; i < objs.length; i++ ) {
				if( i > 0 ) {
					ret += ",";
				}
				ret += objs[i].toString();
			}
		} else {
			ret += value.toString();
		}

		return ret;
	}
}