import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel.DataTypeEnum;
import com.revenga.rits.back.data.core.model.LogTypeParam;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.Element;
    
def XidPointValueTimeModel getXidPointValueTimeModel(SignallingCommand signallingCommand, int paramId, String xid) {

	XidPointValueTimeModel xidPointValueTimeModel = null;

	if (!CollectionUtils.isEmpty(signallingCommand.getSignallingParams())) {

		SignallingParam signallingParam = null;
		
		for (SignallingParam signallingParamAux : signallingCommand.getSignallingParams()) {
			
			if (signallingParamAux.getId() == paramId) {
				
				signallingParam = signallingParamAux;
				
				break;
			}
		}
		
		if (signallingParam != null) {
		
			xidPointValueTimeModel = new XidPointValueTimeModel();

			xidPointValueTimeModel.setXid(xid);
			xidPointValueTimeModel.setTimestamp(0L);
			xidPointValueTimeModel.setAnnotation("string");

			if (signallingParam.getType() == DataType.DATA_TYPE_INTEGER) {
				
				xidPointValueTimeModel.setDataType(DataTypeEnum.NUMERIC);
				xidPointValueTimeModel.setValue(Integer.parseInt(signallingParam.getValue()));
				
			} else if (signallingParam.getType() == DataType.DATA_TYPE_REAL) {

				xidPointValueTimeModel.setDataType(DataTypeEnum.NUMERIC);
				xidPointValueTimeModel.setValue(Double.parseDouble(signallingParam.getValue()));
				
			} else if (signallingParam.getType() == DataType.DATA_TYPE_ALPHANUMERIC) {

				xidPointValueTimeModel.setDataType(DataTypeEnum.ALPHANUMERIC);
				xidPointValueTimeModel.setValue(signallingParam.getValue());
				
			} else if (signallingParam.getType() == DataType.DATA_TYPE_BOOLEAN) {

				xidPointValueTimeModel.setDataType(DataTypeEnum.BINARY);
				xidPointValueTimeModel.setValue(StringUtils.equalsAny(signallingParam.getValue(), "1", "true"));
			}
		}
	}

	return xidPointValueTimeModel;
}

def XidPointValueTimeModel getXidPointValueTimeModel(SignallingCommand signallingCommand, String xid, Object value) {
	
	XidPointValueTimeModel xidPointValueTimeModel = xidPointValueTimeModel = new XidPointValueTimeModel();

	xidPointValueTimeModel.setXid(xid);
	xidPointValueTimeModel.setTimestamp(0L);
	xidPointValueTimeModel.setAnnotation("string");

	if (value instanceof Long) {
		
		xidPointValueTimeModel.setDataType(DataTypeEnum.NUMERIC);
		
	} else if (value instanceof Double) {

		xidPointValueTimeModel.setDataType(DataTypeEnum.NUMERIC);
		
	} else if (value instanceof String) {

		xidPointValueTimeModel.setDataType(DataTypeEnum.ALPHANUMERIC);
		
	} else if (value instanceof Boolean) {

		xidPointValueTimeModel.setDataType(DataTypeEnum.BINARY);
	}
	
	if (xidPointValueTimeModel.getDataType() != null) {
		
		xidPointValueTimeModel.setValue(value);
	}

	return xidPointValueTimeModel;
}
	  