package com.revenga.rits.back.traffic.manager.data.maintenance;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.entities.lib.repository.entity.EntityOnChange;

import jline.internal.Log;

public class ElementOnChange extends EntityOnChange {

	public ElementOnChange() {

		super(Element.class);
	}

	@Override
	protected void create() {

		if (basicElement instanceof Element) {

			Log.debug(basicElement);
		}
	}

	@Override
	protected void update() {

		if (basicElement instanceof Element) {

			Log.debug(basicElement);
		}
	}

	@Override
	protected void delete() {

		if (basicElement instanceof Element) {

			Log.debug(basicElement);
		}
	}
}
