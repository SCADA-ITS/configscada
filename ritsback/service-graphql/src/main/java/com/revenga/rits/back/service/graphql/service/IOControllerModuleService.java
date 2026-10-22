package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revenga.rits.back.data.core.model.IOControllerModule;
import com.revenga.rits.back.front.core.model.vo.IOControllerModuleVO;
import com.revenga.rits.back.service.graphql.type.IOControllerModuleGraphql;

@Service
public class IOControllerModuleService implements QueryInterface<IOControllerModuleVO> {

    @Override
    public List<IOControllerModuleVO> getAll() {
    	
        List<IOControllerModuleVO> ioControllerModules = new ArrayList<>();

        for (IOControllerModule ioControllerModule : EntitiesManager.getInstance().getIoControllerModuleRepository().getAll(false)) {
        	
            ioControllerModules.add(new IOControllerModuleGraphql(ioControllerModule));
        }

        return ioControllerModules;
    }

    @Override
    public List<IOControllerModuleVO> gets(List<String> idItems) {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public Optional<IOControllerModuleVO> get(String idItem) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
}
