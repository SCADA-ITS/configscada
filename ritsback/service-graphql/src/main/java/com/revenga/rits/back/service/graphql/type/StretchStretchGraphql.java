package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.StretchStretch;
import com.revenga.rits.back.data.core.model.StretchStretchStretch;
import com.revenga.rits.back.front.core.model.helper.StretchStretchHelper;
import com.revenga.rits.back.front.core.model.vo.StretchStretchVO;
import com.revenga.rits.back.front.core.model.vo.StretchVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

public class StretchStretchGraphql extends StretchStretchVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2024413709955798391L;
	
	private StretchStretch stretchStretch;

	public StretchStretchGraphql(final StretchStretch stretchStretch) {

		StretchStretchHelper.assign(stretchStretch, this);
		this.stretchStretch = stretchStretch;
	}

	

	@Override
	public List<StretchVO> getStretchs() {
		
		List<StretchVO> stretchsVO = new ArrayList<>();
		
		if (stretchStretch != null) {
			
			List<StretchStretchStretch> stretchStretchsStretchs = EntitiesManager.getInstance().getStretchStretchStretchRepository()
					.getsByStretchStretch(stretchStretch.getId(), false);
			
			for (StretchStretchStretch stretchStretchStretch : stretchStretchsStretchs) {
				
				Stretch stretch = EntitiesManager.getInstance().getStretchRepository()
						.get(stretchStretchStretch.getStretchId(), false);
				
				if (stretch != null) {
					
					stretchsVO.add(new StretchGraphql(stretch));
				}
			}
		}
		
		return stretchsVO;
	}

	
}
