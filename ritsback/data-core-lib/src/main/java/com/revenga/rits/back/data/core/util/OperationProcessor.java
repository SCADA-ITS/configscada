package com.revenga.rits.back.data.core.util;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.data.core.model.ThresholdOperation;

public class OperationProcessor {

	private OperationProcessor() {
		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static long check(Long operation, String thresholdVal1, String thresholdVal2, String value,
			Long dataTypeId) {

		long res = 0L;

		if (operation == null) {

			return 0L;
		}

		if (operation == ThresholdOperation.OPERATION_BETWEEN) {

			res = checkBetween(thresholdVal1, thresholdVal2, value, dataTypeId) ? operation : 0L;

		} else if (operation == ThresholdOperation.OPERATION_EMPTY) {

			res = checkEmpty(value) ? operation : 0L;

		} else if (operation == ThresholdOperation.OPERATION_EQUAL) {

			res = checkEqual(thresholdVal1, value, dataTypeId) ? operation : 0L;
		} else if (operation == ThresholdOperation.OPERATION_GREATER) {

			res = checkGreater(thresholdVal1, value, dataTypeId) ? operation : 0L;
		} else if (operation == ThresholdOperation.OPERATION_GREATER_OR_EQUAL) {

			res = checkGreaterOrEqual(thresholdVal1, value, dataTypeId) ? operation : 0L;
		} else if (operation == ThresholdOperation.OPERATION_LESS) {

			res = checkLess(thresholdVal1, value, dataTypeId) ? operation : 0L;
		} else if (operation == ThresholdOperation.OPERATION_LESS_OR_EQUAL) {

			res = checkLessOrEqual(thresholdVal1, value, dataTypeId) ? operation : 0L;
		} else if (operation == ThresholdOperation.OPERATION_NOT_EMPTY) {

			res = !checkEmpty(value) ? operation : 0L;
		} else if (operation == ThresholdOperation.OPERATION_NOT_EQUAL) {

			res = !checkEqual(thresholdVal1, value, dataTypeId) ? operation : 0L;
		} else if (operation == ThresholdOperation.OPERATION_NOT_BETWEEN) {

			res = !checkBetween(thresholdVal1, thresholdVal2, value, dataTypeId) ? operation : 0L;
		}

		return res;
	}

	private static boolean checkBetween(String thresholdVal1, String thresholdVal2, String valueStr,
			Long dataTypeId) {

		boolean res = false;

		if (ObjectUtils.allNotNull(thresholdVal1, thresholdVal2, valueStr, dataTypeId)) {

			if (NumberUtils.isCreatable(valueStr)) {

				if (dataTypeId == DataType.DATA_TYPE_INTEGER) {

					Integer value = Double.valueOf(valueStr).intValue();
					Integer valLower = Double.valueOf(thresholdVal1).intValue();
					Integer valUpper = Double.valueOf(thresholdVal2).intValue();

					res = (value >= valLower);
					res &= (value <= valUpper);
				} else if (dataTypeId == DataType.DATA_TYPE_REAL) {

					Double value = Double.valueOf(valueStr);
					Double valLower = Double.valueOf(thresholdVal1);
					Double valUpper = Double.valueOf(thresholdVal2);

					res = (value >= valLower);
					res &= (value <= valUpper);
				}
			}
		}

		return res;
	}

	private static boolean checkEmpty(String value) {

		boolean res = false;
		
		if (ObjectUtils.allNotNull(value)) {

			res = StringUtils.isEmpty(value);
		}

		return res;
	}

	private static boolean checkEqual(String thresholdVal1, String valueStr,
			Long dataTypeId) {

		boolean res = false;

		if (ObjectUtils.allNotNull(thresholdVal1, valueStr, dataTypeId)) {

			if (dataTypeId == DataType.DATA_TYPE_INTEGER) {

				if (NumberUtils.isCreatable(valueStr)) {

					Integer value = Double.valueOf(valueStr).intValue();
					Integer valLower = Double.valueOf(thresholdVal1).intValue();

					res = (value.equals(valLower));
				}
			} else if (dataTypeId == DataType.DATA_TYPE_REAL) {

				if (NumberUtils.isCreatable(valueStr)) {

					Double value = Double.valueOf(valueStr);
					Double valLower = Double.valueOf(thresholdVal1);

					res = (value.equals(valLower));
				}
			} else if (dataTypeId == DataType.DATA_TYPE_ALPHANUMERIC) {

				res = (valueStr.equals(thresholdVal1));
			} else if (dataTypeId == DataType.DATA_TYPE_BOOLEAN) {

				Boolean boolValueFromStr = StringUtils.equalsAny(valueStr, "1", "true");
				Boolean boolValueFromThresholdVal1 = StringUtils.equalsAny(thresholdVal1, "1", "true");

				res = (boolValueFromStr.equals(boolValueFromThresholdVal1));
			}
		}
		return res;
	}

	private static boolean checkGreater(String thresholdVal1, String valueStr,
			Long dataTypeId) {

		boolean res = false;

		if (ObjectUtils.allNotNull(thresholdVal1, valueStr, dataTypeId)) {

			if (NumberUtils.isCreatable(valueStr)) {

				if (dataTypeId == DataType.DATA_TYPE_INTEGER) {

					Integer value = Double.valueOf(valueStr).intValue();
					Integer valLower = Double.valueOf(thresholdVal1).intValue();

					res = (value > valLower);
				} else if (dataTypeId == DataType.DATA_TYPE_REAL) {

					Double value = Double.valueOf(valueStr);
					Double valLower = Double.valueOf(thresholdVal1);

					res = (value > valLower);
				}
			}
		}
		return res;
	}

	private static boolean checkGreaterOrEqual(String thresholdVal1, String valueStr,
			Long dataTypeId) {

		boolean res = false;

		if (ObjectUtils.allNotNull(thresholdVal1, valueStr, dataTypeId)) {

			if (NumberUtils.isCreatable(valueStr)) {

				if (dataTypeId == DataType.DATA_TYPE_INTEGER) {

					Integer value = Double.valueOf(valueStr).intValue();
					Integer valLower = Double.valueOf(thresholdVal1).intValue();

					res = (value >= valLower);
				} else if (dataTypeId == DataType.DATA_TYPE_REAL) {

					Double value = Double.valueOf(valueStr);
					Double valLower = Double.valueOf(thresholdVal1);

					res = (value >= valLower);
				}
			}
		}
		return res;
	}

	private static boolean checkLess(String thresholdVal1, String valueStr,
			Long dataTypeId) {

		boolean res = false;

		if (ObjectUtils.allNotNull(thresholdVal1, valueStr, dataTypeId)) {

			if (NumberUtils.isCreatable(valueStr)) {

				if (dataTypeId == DataType.DATA_TYPE_INTEGER) {

					Integer value = Double.valueOf(valueStr).intValue();
					Integer valLower = Double.valueOf(thresholdVal1).intValue();

					res = (value < valLower);
				} else if (dataTypeId == DataType.DATA_TYPE_REAL) {

					Double value = Double.valueOf(valueStr);
					Double valLower = Double.valueOf(thresholdVal1);

					res = (value < valLower);
				}
			}
		}
		return res;
	}

	private static boolean checkLessOrEqual(String thresholdVal1, String valueStr,
			Long dataTypeId) {

		boolean res = false;

		if (ObjectUtils.allNotNull(thresholdVal1, valueStr, dataTypeId)) {

			if (NumberUtils.isCreatable(valueStr)) {

				if (dataTypeId == DataType.DATA_TYPE_INTEGER) {

					Integer value = Double.valueOf(valueStr).intValue();
					Integer valLower = Double.valueOf(thresholdVal1).intValue();

					res = (value <= valLower);
				} else if (dataTypeId == DataType.DATA_TYPE_REAL) {

					Double value = Double.valueOf(valueStr);
					Double valLower = Double.valueOf(thresholdVal1);

					res = (value <= valLower);
				}
			}
		}
		return res;
	}
}
