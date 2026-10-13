package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.model.Spath;
import com.revenga.rits.back.data.core.model.SpathStretch;
import com.revenga.rits.back.front.core.model.helper.SpathHelper;
import com.revenga.rits.back.front.core.model.vo.SpathStretchVO;
import com.revenga.rits.back.front.core.model.vo.SpathVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

public class SpathGraphql extends SpathVO{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5759738224164825070L;

	private Spath spath;
	private Long stretchId;
	
	public SpathGraphql(final Spath spath, Long stretchId) {
		
		SpathHelper.assign(spath, this);
		this.spath = spath;
		this.stretchId = stretchId;
	}
	
	@Override
	public List<SpathStretchVO> getSpathStretchs() {

		List <SpathStretchVO> spathStretchsVO = null;
		
		if (super.getSpathStretchs() == null) {

			spathStretchsVO = new ArrayList<>();
			
			List<SpathStretch> spathStretchs = stretchId != null ? EntitiesManager.getInstance().getSpathStretchRepository()
					.gets(spath.getId(), stretchId, false) : EntitiesManager.getInstance().getSpathStretchRepository()
					.getsBySpath(spath.getId(), false);
			
			for (SpathStretch spathStretch : spathStretchs) {
				
				if (spathStretch != null){
					
					spathStretchsVO.add(new SpathStretchGraphql(spathStretch));
				}	
			}
			super.setSpathStretchs(spathStretchsVO);
		}

		return super.getSpathStretchs();
	}
}
