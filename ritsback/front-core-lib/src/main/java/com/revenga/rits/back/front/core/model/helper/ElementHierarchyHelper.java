package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.front.core.model.vo.ElementHierarchyVO;

/**
 * Class that implement an ElementHierachy Helper
 * @author Fj. Olivares
 *
 */
public class ElementHierarchyHelper {

	/**
	 * Default constructor
	 */
	private ElementHierarchyHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	/**
	 * Method that return a new  ElementHierarchyVO object from an ElementHierarchy input parameter  
	 * @param elementHierarchy: ElementHierarchy input parameter
	 * @return: ElementHierarchyVO object
	 */
	public static ElementHierarchyVO get(ElementHierarchy elementHierarchy) {

		ElementHierarchyVO elementHierarchyVO = null;

		if (elementHierarchy != null) {

			elementHierarchyVO = new ElementHierarchyVO();
			
			assign(elementHierarchy, elementHierarchyVO);
		}

		return elementHierarchyVO;
	}
	
	/**
	 * Method that return a new  ElementHierarchy object from an ElementHierarchyVO input parameter
	 * @param elementHierarchyVO: ElementHierarchyVO input parameter
	 * @return: ElementHierarchy object
	 */
	public static ElementHierarchy get(ElementHierarchyVO elementHierarchyVO) {

		ElementHierarchy elementHierarchy = null;

		if (elementHierarchyVO != null) {

			elementHierarchy = new ElementHierarchy();

			if (elementHierarchyVO.getId() != null) {

				Key key = new Key(elementHierarchyVO.getId());

				if (key.getClazz() == ElementHierarchy.class) {

					elementHierarchy.setParentElementTypeId(Long.valueOf(key.getId(1)));
					elementHierarchy.setParentElementId(Long.valueOf(key.getId(2)));
					
					elementHierarchy.setChildElementTypeId(Long.valueOf(key.getId(3)));
					elementHierarchy.setChildElementId(Long.valueOf(key.getId(4)));
				}
			}
		}

		return elementHierarchy;
	}
	
	/**
	 * 
	 * @param elementHierarchy
	 * @param elementHierarchyVO
	 */
	public static void assign(final ElementHierarchy elementHierarchy, ElementHierarchyVO elementHierarchyVO) {

		if (ObjectUtils.allNotNull(elementHierarchy, elementHierarchyVO)) {

			BasicElementHelper.fillBasicProperties(elementHierarchyVO, elementHierarchy);
		}
	}	

}
