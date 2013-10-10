// Copyright (c) 2003-2013, Jodd Team (jodd.org). All Rights Reserved.

package jodd.typeconverter.impl;

import jodd.typeconverter.TypeConverter;
import jodd.typeconverter.TypeConverterManagerBean;
import jodd.util.CsvUtil;

import java.util.Collection;
import java.util.List;

/**
 * Converts given object to <code>long[]</code>.
 */
public class LongArrayConverter implements TypeConverter<long[]> {

	protected final TypeConverterManagerBean typeConverterManagerBean;

	public LongArrayConverter(TypeConverterManagerBean typeConverterManagerBean) {
		this.typeConverterManagerBean = typeConverterManagerBean;
	}

	public long[] convert(Object value) {
		if (value == null) {
			return null;
		}

		Class valueClass = value.getClass();

		if (valueClass.isArray() == false) {
			// source is not an array
			return convertValueToArray(value);
		}

		// source is an array
		return convertArrayToArray(value);
	}

	/**
	 * Converts type using type converter manager.
	 */
	protected long convertType(Object value) {
		return typeConverterManagerBean.convertType(value, long.class);
	}

	/**
	 * Creates an array with single element.
	 */
	protected long[] convertToSingleElementArray(Object value) {
		return new long[] {convertType(value)};
	}

	/**
	 * Converts non-array value to array. Detects various
	 * collection types and iterates them to make conversion
	 * and to create target array.
 	 */
	protected long[] convertValueToArray(Object value) {
		if (value instanceof List) {
			List list = (List) value;
			long[] target = new long[list.size()];

			for (int i = 0; i < list.size(); i++) {
				Object element = list.get(i);
				target[i] = convertType(element);
			}

			return target;
		}

		if (value instanceof Collection) {
			Collection collection = (Collection) value;
			long[] target = new long[collection.size()];

			int i = 0;
			for (Object element : collection) {
				target[i] = convertType(element);
				i++;
			}

			return target;
		}

		if (value instanceof Iterable) {
			Iterable iterable = (Iterable) value;

            int count = 0;
			for (Object element : iterable) {
				count++;
			}

			long[] target = new long[count];
			int i = 0;
			for (Object element : iterable) {
				target[i] = convertType(element);
            	i++;
            }

			return target;
		}

		if (value instanceof CharSequence) {
			String[] strings = CsvUtil.toStringArray(value.toString());
			return convertArrayToArray(strings);
		}

		// everything else:
		return convertToSingleElementArray(value);
	}

	/**
	 * Converts array value to array.
	 */
	protected long[] convertArrayToArray(Object value) {
		Class valueComponentType = value.getClass().getComponentType();

		if (valueComponentType == long.class) {
			// equal types, no conversion needed
			return (long[]) value;
		}

		long[] result;

		if (valueComponentType.isPrimitive()) {
			// convert primitive array to target array
			result = convertPrimitiveArrayToArray(value, valueComponentType);
		} else {
			// convert object array to target array
			Object[] array = (Object[]) value;
			result = new long[array.length];

			for (int i = 0; i < array.length; i++) {
				result[i] = convertType(array[i]);
			}
		}

		return result;
	}


	/**
	 * Converts primitive array to target array.
	 */
	protected long[] convertPrimitiveArrayToArray(Object value, Class primitiveComponentType) {
		long[] result = null;

		if (primitiveComponentType == long[].class) {
			return (long[]) value;
		}

		if (primitiveComponentType == int.class) {
			int[] array = (int[]) value;
			result = new long[array.length];
			for (int i = 0; i < array.length; i++) {
				result[i] = array[i];
			}
		}
		else if (primitiveComponentType == float.class) {
			float[] array = (float[]) value;
			result = new long[array.length];
			for (int i = 0; i < array.length; i++) {
				result[i] = (long) array[i];
			}
		}
		else if (primitiveComponentType == double.class) {
			double[] array = (double[]) value;
			result = new long[array.length];
			for (int i = 0; i < array.length; i++) {
				result[i] = (long) array[i];
			}
		}
		else if (primitiveComponentType == short.class) {
			short[] array = (short[]) value;
			result = new long[array.length];
			for (int i = 0; i < array.length; i++) {
				result[i] = array[i];
			}
		}
		else if (primitiveComponentType == byte.class) {
			byte[] array = (byte[]) value;
			result = new long[array.length];
			for (int i = 0; i < array.length; i++) {
				result[i] = array[i];
			}
		}
		else if (primitiveComponentType == char.class) {
			char[] array = (char[]) value;
			result = new long[array.length];
			for (int i = 0; i < array.length; i++) {
				result[i] = array[i];
			}
		}
		else if (primitiveComponentType == boolean.class) {
			boolean[] array = (boolean[]) value;
			result = new long[array.length];
			for (int i = 0; i < array.length; i++) {
				result[i] = array[i] ? 1 : 0;
			}
		}
		return result;
	}

}