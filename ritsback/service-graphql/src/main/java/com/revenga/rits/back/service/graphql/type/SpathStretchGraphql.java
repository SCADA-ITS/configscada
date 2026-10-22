package com.revenga.rits.back.service.graphql.type;
import com.revenga.rits.back.data.core.model.Spath;
import com.revenga.rits.back.data.core.model.SpathStretch;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.front.core.model.helper.SpathStretchHelper;
import com.revenga.rits.back.front.core.model.vo.SpathStretchVO;
import com.revenga.rits.back.front.core.model.vo.SpathVO;
import com.revenga.rits.back.front.core.model.vo.StretchVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

public class SpathStretchGraphql extends SpathStretchVO{


	/**
	 * 
	 */
	private static final long serialVersionUID = 7285728808146081264L;
	
	private SpathStretch spathStretch; 

	public SpathStretchGraphql(final SpathStretch spathStretch) {
		
		SpathStretchHelper.assign(spathStretch, this);
		this.spathStretch=spathStretch;
	}	

	@Override
	public StretchVO getStretch() {

		if (super.getStretch() == null && spathStretch.getStretchId() != null) {

			Stretch stretch = EntitiesManager.getInstance().getStretchRepository()
					.get(spathStretch.getStretchId(), false);

			if (stretch != null) {

				super.setStretch(new StretchGraphql(stretch));
			}
		}

		return super.getStretch();
	}
	
	@Override
	public SpathVO getSpath() {

		if (super.getSpath() == null && spathStretch.getSpathId() != null) {

			Spath spath = EntitiesManager.getInstance().getSpathRepository()
					.get(spathStretch.getSpathId(), false);

			if (spath != null) {

				super.setSpath(new SpathGraphql(spath, spathStretch.getStretchId()));
			}
		}

		return super.getSpath();
	}
	
	@Override
	public StretchVO getRelStretch() {

		if (super.getRelStretch() == null && spathStretch.getRelStretchId() != null) {

			Stretch stretch = EntitiesManager.getInstance().getStretchRepository()
					.get(spathStretch.getRelStretchId(), false);

			if (stretch != null) {

				super.setRelStretch(new StretchGraphql(stretch));
			}
		}

		return super.getRelStretch();
	}
}
