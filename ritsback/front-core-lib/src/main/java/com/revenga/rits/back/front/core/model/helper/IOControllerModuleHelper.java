package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.IOControllerModule;
import com.revenga.rits.back.front.core.model.vo.IOControllerModuleVO;

public class IOControllerModuleHelper {

	private IOControllerModuleHelper() {
		
		super();
	}

	public static IOControllerModuleVO get(IOControllerModule ioControllerModule) {
		
		IOControllerModuleVO ioControllerModuleVO = null;

		if (ioControllerModule != null) {
			
			ioControllerModuleVO = new IOControllerModuleVO();
			assign(ioControllerModule, ioControllerModuleVO);
		}

		return ioControllerModuleVO;
	}

	public static IOControllerModule get(IOControllerModuleVO ioControllerModuleVO) {
		
		IOControllerModule ioControllerModule = null;

		if (ioControllerModuleVO != null) {
			
			ioControllerModule = new IOControllerModule();

			if (ioControllerModuleVO.getId() != null) {
				
				Key key = new Key(ioControllerModuleVO.getId());

				if (key.getClazz() == IOControllerModule.class) {
					
					ioControllerModule.setModuleTypeId(Long.valueOf(key.getId(1)));
					ioControllerModule.setId(Long.valueOf(key.getId(2)));
				}
			}

			ioControllerModule.setAlias(ioControllerModuleVO.getAlias());
			ioControllerModule.setDescription(ioControllerModuleVO.getDescription());
		}

		return ioControllerModule;
	}

	public static void assign(final IOControllerModule ioControllerModule, IOControllerModuleVO ioControllerModuleVO) {
		
		if (ioControllerModule != null && ioControllerModuleVO != null) {
			
			BasicElementHelper.fillBasicProperties(ioControllerModuleVO, ioControllerModule);
		}
	}
}
