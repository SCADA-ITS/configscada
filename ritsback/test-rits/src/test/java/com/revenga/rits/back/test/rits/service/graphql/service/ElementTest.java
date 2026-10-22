package com.revenga.rits.back.test.rits.service.graphql.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.data.loader.service.CollectionManagerService;
import com.revenga.rits.back.front.core.model.vo.ElementTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.front.core.model.vo.ElementValueVO;
import com.revenga.rits.back.service.graphql.service.ElementService;
import com.revenga.rits.back.service.graphql.service.ElementTypeService;

public class ElementTest {

	public static final String ALIAS_IN_CLONE = ConfigProperty.getInstance().getProperty("application.properties",
			"service.element.clone.alias");

	public static final String DESCRIPTION_IN_CLONE = ConfigProperty.getInstance().getProperty("application.properties",
			"service.element.clone.description");

	private ElementTypeService elementTypeService;
	private ElementService elementService;

	public ElementTest() {

		super();

		CollectionManagerService cms = new CollectionManagerService();

		elementTypeService = new ElementTypeService();
		elementService = new ElementService();

		cms.load();
	}

	@Test
	public void element() {

		// get all elements types
		List<ElementTypeVO> allElementTypes = elementTypeService.getAll();
		assertNotNull(allElementTypes);
		List<ElementVO> elementsVO = new ArrayList<ElementVO>();

		Optional<ElementVO> respElement = null;

		ElementVO elementVO = new ElementVO();

		for (ElementTypeVO elementTypeVO : allElementTypes) {

			elementVO.setElementType(elementTypeVO);
			elementVO.setAlias("Test1" + "_" + elementTypeVO.getAlias());
			elementVO.setDescription("Test1_Test" + "_" + elementTypeVO.getDescription());
			elementVO.setLongitude(12.21);
			elementVO.setLatitude(12.0);
			elementVO.setIcon("icon test1.svg");
			elementVO.setPositionX(12.3);
			elementVO.setPositionY(12.3);
			elementVO.setZoomIn(12);
			elementVO.setZoomOut(14);
			respElement = elementService.create(elementVO);

			assertNotNull(respElement);
			assertNotNull(respElement.get());
			assertEquals(elementTypeVO.getAlias(), respElement.get().getElementType().getAlias());
			assertEquals("Test1" + "_" + elementTypeVO.getAlias(), respElement.get().getAlias());
			assertEquals("Test1_Test" + "_" + elementTypeVO.getDescription(), respElement.get().getDescription());
			assertEquals(12.21, respElement.get().getLongitude(), 0.001);
			assertEquals(12.0, respElement.get().getLatitude(), 0.001);
			assertEquals("icon test1.svg", respElement.get().getIcon());
			assertEquals(12.3, respElement.get().getPositionX(), 0.001);
			assertEquals(12.3, respElement.get().getPositionY(), 0.001);
			assertEquals(12, respElement.get().getZoomIn(), 0.001);
			assertEquals(14, respElement.get().getZoomOut(), 0.001);
			assertEquals(ElementTypeState.STATE_UNKNOWN, respElement.get().getState(), 0);
			// get element
			respElement = elementService.get(respElement.get().getId());
			assertNotNull(respElement);
			elementsVO.add(respElement.get());

		}

		// get all element types
		List<ElementVO> allElement = elementService.getAll();
		assertTrue(allElement.size() >= allElementTypes.size());

		boolean found = false;

		for (ElementVO element : elementsVO) {

			found = false;

			for (ElementVO all : allElement) {

				if (element.getId().equals(all.getId()) && element.getAlias().equals(all.getAlias())
						&& element.getDescription().equals(all.getDescription())) {

					found = true;
					// get delete
					respElement = elementService.delete(all.getId());
					assertNotNull(respElement);

					respElement = elementService.get(element.getId());
					assertNull(respElement);
				}

			}
			assertTrue(found);
		}

		//
		ElementTypeVO elementTypeVo = new ElementTypeVO();

		ElementVO elementWithOutId = new ElementVO();

		elementTypeVo.setId(null);

		elementWithOutId = new ElementVO();
		elementWithOutId.setElementType(elementTypeVo);
		elementWithOutId.setAlias("elementWithOutId");

		respElement = elementService.create(elementWithOutId);
		assertNull(respElement);

		//
		elementWithOutId = new ElementVO();
		elementWithOutId.setElementType(null);
		elementWithOutId.setAlias("elementWithOutId");

		respElement = elementService.create(elementWithOutId);
		assertNull(respElement);

	}

	@Test
	public void cloneElementVO() {

		List<String> parents = generateParent();
		List<ElementTypeVO> allElementTypes = elementTypeService.getAll();
		// get elementType specific
		Optional<ElementTypeVO> elementTypeVO = elementTypeService.get(allElementTypes.get(0).getId());
		assertNotNull(elementTypeVO);
		Optional<ElementVO> respElement = null;
		Optional<ElementVO> respElementClone = null;

		ElementVO elementVO = new ElementVO();

		elementVO.setElementType(elementTypeVO.get());
		elementVO.setAlias("TestClone" + "_" + elementTypeVO.get().getAlias());
		elementVO.setDescription("TestClone" + "_" + elementTypeVO.get().getDescription());
		elementVO.setLongitude(15.5);
		elementVO.setLatitude(14.4);
		elementVO.setParentIds(parents);
		elementVO.setPositionX(12.1);
		elementVO.setPositionY(11.0);
		elementVO.setZoomIn(2);
		elementVO.setZoomOut(2);
		elementVO.setIcon("Icon.svg");

		respElement = elementService.get(elementVO.getId());
		assertNull(respElement);

		// create element
		respElement = elementService.create(elementVO);
		assertNotNull(respElement);

		assertEquals("TestClone" + "_" + elementTypeVO.get().getAlias(), respElement.get().getAlias());
		assertEquals("TestClone" + "_" + elementTypeVO.get().getDescription(), respElement.get().getDescription());
		assertEquals(15.5, respElement.get().getLongitude(), 0.001);
		assertEquals(14.4, respElement.get().getLatitude(), 0.001);
		assertEquals("Icon.svg", respElement.get().getIcon());
		assertEquals(12.1, respElement.get().getPositionX(), 0.001);
		assertEquals(11.0, respElement.get().getPositionY(), 0.001);
		assertEquals(2, respElement.get().getZoomIn(), 0.001);
		assertEquals(2, respElement.get().getZoomOut(), 0.001);
		assertEquals(ElementTypeState.STATE_UNKNOWN, respElement.get().getState(), 0);

		// clone element
		respElementClone = elementService.clone(respElement.get().getId());
		assertNotNull(respElementClone);
		assertEquals("TestClone" + "_" + elementTypeVO.get().getAlias() + ALIAS_IN_CLONE,
				respElementClone.get().getAlias());
		assertEquals("TestClone" + "_" + elementTypeVO.get().getDescription() + DESCRIPTION_IN_CLONE,
				respElementClone.get().getDescription());

		assertEquals(15.5, respElementClone.get().getLongitude(), 0.001);
		assertEquals(14.4, respElementClone.get().getLatitude(), 0.001);
		assertEquals("Icon.svg", respElementClone.get().getIcon());
		assertEquals(12.1, respElementClone.get().getPositionX(), 0.001);
		assertEquals(11.0, respElementClone.get().getPositionY(), 0.001);
		assertEquals(2, respElementClone.get().getZoomIn(), 0.001);
		assertEquals(2, respElementClone.get().getZoomOut(), 0.001);
		assertEquals(ElementTypeState.STATE_UNKNOWN, respElementClone.get().getState(), 0);

		// delete element and element clone
		respElement = elementService.delete(respElement.get().getId());
		respElementClone = elementService.delete(respElementClone.get().getId());

		respElement = elementService.get(respElement.get().getId());
		assertNull(respElement);

		respElementClone = elementService.get(respElementClone.get().getId());
		assertNull(respElementClone);

		for (String e : parents) {
			respElement = elementService.delete(e);
			assertNotNull(respElement);

			respElement = elementService.get(e);
			assertNull(respElement);
		}

	}

	private List<String> generateParent() {
		List<String> parents = new ArrayList<String>();
		Optional<ElementVO> respElement = null;
		List<ElementTypeVO> allElementTypes = elementTypeService.getAll();

		assertNotNull(allElementTypes);

		for (ElementTypeVO e : allElementTypes) {

			ElementVO elementParent = new ElementVO();

			elementParent.setElementType(e);
			elementParent.setAlias("Parent" + "_" + e.getAlias());
			elementParent.setDescription("Parent" + "_" + e.getDescription());
			elementParent.setLongitude(12.21);
			elementParent.setLatitude(12.0);
			elementParent.setIcon("parent camara.svg");
			elementParent.setPositionX(12.3);
			elementParent.setPositionY(12.3);
			elementParent.setZoomIn(12);
			elementParent.setZoomOut(14);

			respElement = elementService.create(elementParent);
			assertNotNull(respElement);

			parents.add(respElement.get().getId());
		}

		return parents;
	}

	private List<ElementVO> generateParentForUpdate() {
		List<ElementVO> parents = new ArrayList<ElementVO>();
		Optional<ElementVO> respElement = null;
		List<ElementTypeVO> allElementTypes = elementTypeService.getAll();

		assertNotNull(allElementTypes);

		for (int i = 2; i < allElementTypes.size(); i++) {

			ElementVO elementParent = new ElementVO();

			elementParent.setElementType(allElementTypes.get(i));
			elementParent.setAlias("Parent" + "_" + allElementTypes.get(i).getAlias());
			elementParent.setDescription("Parent" + "_" + allElementTypes.get(i).getDescription());
			elementParent.setLongitude(12.21);
			elementParent.setLatitude(12.0);
			elementParent.setIcon("parent camara.svg");
			elementParent.setPositionX(12.3);
			elementParent.setPositionY(12.3);
			elementParent.setZoomIn(12);
			elementParent.setZoomOut(14);

			respElement = elementService.create(elementParent);
			assertNotNull(respElement);

			parents.add(respElement.get());
		}

		return parents;
	}

	private boolean checkParents(final List<String> parents, final ElementVO child) {

		boolean found = false;

		if (CollectionUtils.isEmpty(parents) && CollectionUtils.isEmpty(child.getParents())) {

			return true;
		} else if (!CollectionUtils.isEmpty(parents) && CollectionUtils.isEmpty(child.getParents())) {

			return false;
		}

		for (String e : parents) {

			found = false;

			for (ElementVO el : child.getParents()) {

				if (e.equals(el.getId())) {

					found = true;
				}
			}

			if (!found) {

				return false;
			}

		}

		return true;
	}

	private boolean checkChildren(final List<String> children, final ElementVO parent) {

 		boolean found = false;

		if (CollectionUtils.isEmpty(children) && CollectionUtils.isEmpty(parent.getChilds())) {

			return true;
		} else if (!CollectionUtils.isEmpty(children) && CollectionUtils.isEmpty(parent.getChilds())) {

			return false;
		}

		for (String e : children) {

			found = false;

			for (ElementVO el : parent.getChilds()) {

				if (e.equals(el.getId())) {

					found = true;
				}
			}

			if (!found) {

				return false;
			}

		}

		return true;
	}

	@Test
	public void hierarchies() {

		List<ElementTypeVO> childsTypes = elementTypeService.getAll();
		List<String> parents = generateParent();
		List<String> children = new ArrayList<String>();
		Optional<ElementVO> respElement = null;

		assertNotNull(childsTypes);

		for (ElementTypeVO elementTypeVO : childsTypes) {

			ElementVO elementChild = new ElementVO();

			elementChild.setParentIds(parents);
			elementChild.setElementType(elementTypeVO);
			elementChild.setAlias("Child" + "_" + elementTypeVO.getAlias());
			elementChild.setDescription("Child" + "_" + elementTypeVO.getDescription());
			elementChild.setLongitude(12.21);
			elementChild.setLatitude(12.0);
			elementChild.setIcon("icon child.svg");
			elementChild.setPositionX(12.3);
			elementChild.setPositionY(12.3);
			elementChild.setZoomIn(12);
			elementChild.setZoomOut(14);

			respElement = elementService.create(elementChild);
			assertNotNull(respElement);

			children.add(respElement.get().getId());
		}

		// Check children
		for (String e : parents) {
			respElement = elementService.get(e);
			assertNotNull(respElement);

			assertTrue(checkChildren(children, respElement.get()));
		}

		// Delete Parent
		for (String e : parents) {
			respElement = elementService.delete(e);
			assertNotNull(respElement);

			respElement = elementService.get(e);
			assertNull(respElement);
		}

		// Check not Parent
		for (String e : children) {
			respElement = elementService.get(e);
			assertNotNull(respElement);

			assertFalse(checkParents(parents, respElement.get()));

			// Delete children
			respElement = elementService.delete(e);
			assertNotNull(respElement);

			respElement = elementService.get(e);
			assertNull(respElement);
		}

	}

	@Test
	public void elementValues() {

		// get all element types
		List<ElementTypeVO> allElementType = elementTypeService.getAll();
		assertNotNull(allElementType);

		ElementVO element = new ElementVO();
		Optional<ElementVO> respElement = null;

		for (ElementTypeVO elementTypeVO : allElementType) {

			if (!CollectionUtils.isEmpty(elementTypeVO.getElementTypeParams())) {

				element.setElementType(elementTypeVO);
				element.setAlias("elementValues" + elementTypeVO.getAlias());

				ElementValueVO value = null;
				List<ElementValueVO> values = new ArrayList<>();

				for (ElementTypeParamVO elementTypeParamVO : elementTypeVO.getElementTypeParams()) {

					value = new ElementValueVO();
					value.setValue("newValue");
					value.setElementTypeParamId(elementTypeParamVO.getId());
					value.setElementTypeParam(elementTypeParamVO);

					values.add(value);
				}

				value = new ElementValueVO();
				value.setValue("valueWithoutParam");
				values.add(value);

				element.setElementValues(values);
				respElement = elementService.create(element);
				assertNotNull(respElement);

				// get element
				element = respElement.get();

				for (int i = 0; i < element.getElementValues().size(); i++) {

					if (element.getElementValues().get(i).getElementTypeParam().getParamType()
							.longValue() == ParamType.CONFIG) {
						element.getElementValues().get(i).setValue("updateValue");

					}

				}

				respElement = elementService.update(element);
				assertNotNull(respElement);

				Optional<ElementVO> respElementClone = null;

				respElementClone = elementService.clone(respElement.get().getId());
				assertNotNull(respElement);

				// Comprobar valores
				if (respElement.get().getElementValues().get(0).getElementTypeParam().getParamType()
						.longValue() == ParamType.CONFIG) {

					assertEquals("updateValue", respElement.get().getElementValues().get(0).getValue());

				} else {
					assertEquals(respElement.get().getElementValues().get(0).getElementTypeParam().getDefaultValue(),
							respElement.get().getElementValues().get(0).getValue());
				}

				if (respElementClone.get().getElementValues().get(0).getElementTypeParam().getParamType()
						.longValue() == ParamType.CONFIG) {

					assertEquals("updateValue", respElementClone.get().getElementValues().get(0).getValue());

				} else {
					assertEquals(
							respElementClone.get().getElementValues().get(0).getElementTypeParam().getDefaultValue(),
							respElementClone.get().getElementValues().get(0).getValue());
				}

				// getElement
				respElement = elementService.delete(respElement.get().getId());
				assertNotNull(respElement);

				respElement = elementService.get(respElement.get().getId());
				assertNull(respElement);

				respElementClone = elementService.delete(respElementClone.get().getId());
				assertNotNull(respElementClone);

				respElementClone = elementService.get(respElementClone.get().getId());
				assertNull(respElementClone);
			}

		}

	}

	@Test
	public void getElementByType() {

		List<ElementTypeVO> getAllElementTypes = elementTypeService.getAll();
		Optional<ElementVO> respuestByType = null;

		ElementVO elementNew = new ElementVO();
		elementNew.setElementType(getAllElementTypes.get(0));
		elementNew.setAlias("Element-new");
		elementNew.setDescription("Elemento-new");
		elementNew.setLongitude(12.1);
		elementNew.setIcon("icon.type");

		respuestByType = elementService.create(elementNew);

		List<ElementVO> getByType = elementService.getByType(respuestByType.get().getElementType().getId());

		for (ElementVO elementbyType : getByType) {

			if (respuestByType.get().getId().equals(elementbyType.getId())) {

				elementbyType.setAlias("UpdateElement");
				elementbyType.setDescription("UpdateElement");
				elementbyType.setLongitude(1.1);
				elementbyType.setLatitude(2.2);

				respuestByType = elementService.update(elementbyType);

				respuestByType = elementService.delete(respuestByType.get().getId());
				assertNotNull(respuestByType);

				respuestByType = elementService.get(respuestByType.get().getId());
				assertNull(respuestByType);
				break;
			}

		}

		getByType = elementService.getByType("ElementType:ERROR");
		assertTrue(CollectionUtils.isEmpty(getByType));

	}

	@Test
	public void updateElement() {

		List<ElementTypeVO> elementsTypes = elementTypeService.getAll();

		ElementVO elementVO = new ElementVO();
		elementVO.setElementType(elementsTypes.get(0));
		elementVO.setAlias("Element");
		elementVO.setIcon("icono");

		// create element
		Optional<ElementVO> respElement = null;
		respElement = elementService.create(elementVO);

		assertNotNull(respElement);
		assertNotNull(respElement.get());
		assertEquals("Element", respElement.get().getAlias());

		List<ElementVO> elementsAll = elementService.getAll();
		assertNotNull(respElement);

		for (ElementVO elementUpdate : elementsAll) {

			if (elementUpdate.getId().equals(respElement.get().getId())) {

				// si le pasamos un id null o uno que no existe no modifica el elemento
				elementUpdate.setId(null);
				assertNull(elementService.update(elementUpdate));
			}

		}

		// update element
		ElementVO elementVOUpdate = new ElementVO();
		elementVOUpdate.setId(respElement.get().getId());
		elementVOUpdate.setAlias("updateElement");
		Optional<ElementVO> updateElement = null;
		updateElement = elementService.update(elementVOUpdate);
		assertEquals("updateElement", updateElement.get().getAlias());

		// update with list
		List<ElementVO> allElement = null;
		allElement = elementService.getAll();
		assertNotNull(allElement);

		for (ElementVO elementVO2 : allElement) {

			if (elementVO2.getId().equals(elementVOUpdate.getId())) {

				elementVO2.setAlias("updateElement");
				elementVO2.setLatitude(78.8);
				updateElement = elementService.update(elementVO2);
				assertEquals("updateElement", updateElement.get().getAlias());

				updateElement = elementService.delete(elementVO2.getId());
				assertNotNull(updateElement);
			}
		}

		// delete element
		updateElement = elementService.get(updateElement.get().getId());
		assertNull(updateElement);
	}

	@Test
	public void updateParent() {

		List<String> parent = generateParent();
		Optional<ElementVO> respuestElement = null;
		List<ElementTypeVO> allElements = elementTypeService.getAll();
		assertNotNull(allElements);

		ElementVO element = new ElementVO();

		element.setElementType(allElements.get(0));
		element.setAlias("Element-with-parents");
		element.setDescription("Element-with-parents" + allElements.get(0).getDescription());
		element.setParentIds(parent);

		respuestElement = elementService.create(element);
		assertNotNull(respuestElement);

		ElementVO newElement = respuestElement.get();

		respuestElement = elementService.delete(parent.get(0));
		respuestElement = elementService.delete(parent.get(1));
		respuestElement = elementService.delete(parent.get(2));

		respuestElement = elementService.get(newElement.getId());

		respuestElement = elementService.delete(respuestElement.get().getId());
		assertNotNull(respuestElement.get());

		respuestElement = elementService.get(newElement.getId());
		assertNull(respuestElement);

		for (String elementParentVO : parent) {

			respuestElement = elementService.delete(elementParentVO);

			respuestElement = elementService.get(elementParentVO);

		}

	}

	@Test
	public void deleteParents() {

		List<String> parent = generateParent();
		Optional<ElementVO> respuestElement = null;
		List<ElementTypeVO> allElements = elementTypeService.getAll();
		assertNotNull(allElements);

		ElementVO element = new ElementVO();

		element.setElementType(allElements.get(0));
		element.setAlias("Element-with-parents");
		element.setDescription("Element-with-parents" + allElements.get(0).getDescription());
		element.setParentIds(parent);

		respuestElement = elementService.create(element);
		assertNotNull(respuestElement);

		List<ElementVO> parentsUpdate = generateParentForUpdate();

		ElementVO newElement = respuestElement.get();
		newElement.setAlias("Element-update-parents");
		newElement.setParents(parentsUpdate);

		respuestElement = elementService.update(newElement);
		assertNotNull(respuestElement);

		assertEquals("Element-with-parents" + allElements.get(0).getDescription(), newElement.getDescription());

		respuestElement = elementService.delete(respuestElement.get().getId());
		assertNotNull(respuestElement.get());

		respuestElement = elementService.get(newElement.getId());
		assertNull(respuestElement);

		respuestElement = elementService.delete(parentsUpdate.get(0).getId());
		respuestElement = elementService.delete(parentsUpdate.get(1).getId());

		for (String elementParentVO : parent) {

			respuestElement = elementService.delete(elementParentVO);

			respuestElement = elementService.get(elementParentVO);

		}

		for (ElementVO elementParentVO : parentsUpdate) {

			respuestElement = elementService.delete(elementParentVO.getId());

			respuestElement = elementService.get(elementParentVO.getId());

		}

	}

	@Test
	public void listParentFree() {

		List<String> parent = generateParent();
		List<String> parentFree = new ArrayList<String>();
		Optional<ElementVO> respuestElement = null;
		List<ElementTypeVO> allElementsTypes = elementTypeService.getAll();
		assertNotNull(allElementsTypes);

		ElementVO element = new ElementVO();

		element.setElementType(allElementsTypes.get(0));
		element.setAlias("ElementWithParent");
		element.setDescription("ElementWithParent" + allElementsTypes.get(0).getDescription());
		element.setParentIds(parent);

		respuestElement = elementService.create(element);
		assertNotNull(respuestElement);

		assertTrue((!CollectionUtils.isEmpty(respuestElement.get().getParents())));

		ElementVO newElement = respuestElement.get();

		newElement.setAlias("ElementUpdateParents");
		newElement.setParentIds(parentFree);

		/*
		 * al modificar este elemento con una lista de parents vacios se borran las
		 * relaciones entre padres e hijos anteriores
		 */

		respuestElement = elementService.update(newElement);
		assertNotNull(respuestElement);

		assertTrue((CollectionUtils.isEmpty(respuestElement.get().getParentIds())));

		// delete element update
		respuestElement = elementService.delete(newElement.getId());
		assertNotNull(respuestElement);

		respuestElement = elementService.get(newElement.getId());
		assertNull(respuestElement);

		// delete parents
		for (String elementoParent : parent) {

			respuestElement = elementService.delete(elementoParent);
			assertNotNull(respuestElement);

			respuestElement = elementService.get(newElement.getId());
			assertNull(respuestElement);

		}

	}

	@Test
	public void updateElementForParent() {

		List<ElementTypeVO> allElementsTypes = elementTypeService.getAll();
		assertNotNull(allElementsTypes);

		List<String> parents = generateParent();
		List<String> children = new ArrayList<String>();
		Optional<ElementVO> respElement = null;

		ElementVO elementChild = new ElementVO();

		elementChild.setParentIds(parents);
		elementChild.setElementType(allElementsTypes.get(0));
		elementChild.setAlias("Child" + "_" + allElementsTypes.get(0).getAlias());
		elementChild.setDescription("Child" + "_" + allElementsTypes.get(0).getDescription());
		elementChild.setLongitude(12.21);
		elementChild.setLatitude(12.0);
		elementChild.setIcon("icon child.svg");
		elementChild.setPositionX(12.3);
		elementChild.setPositionY(12.3);
		elementChild.setZoomIn(12);
		elementChild.setZoomOut(14);

		respElement = elementService.create(elementChild);
		assertNotNull(respElement);
		children.add(respElement.get().getId());

		// update parent
		ElementVO updateParent = new ElementVO();
		updateParent.setId(parents.get(0));
		updateParent.setAlias("updateParent");
		respElement = elementService.update(updateParent);
		assertNotNull(respElement);

		respElement = elementService.get(updateParent.getId());
		assertNotNull(respElement);

		assertTrue(checkChildren(children, respElement.get()));

		List<ElementVO> allElements = elementService.getAll();

		elementChild.setParents(allElements.get(0).getParents());
		respElement = elementService.create(elementChild);
		children.add(respElement.get().getId());

		// Delete Parent
		for (String e : parents) {
			respElement = elementService.delete(e);
			assertNotNull(respElement);

			respElement = elementService.get(e);
			assertNull(respElement);
		}

		// Check not Parent
		for (String e : children) {

			respElement = elementService.get(e);
			assertNotNull(respElement);

			assertFalse(checkParents(parents, respElement.get()));

			// Delete children
			respElement = elementService.delete(e);
			assertNotNull(respElement);

			respElement = elementService.get(e);
			assertNull(respElement);
		}

	}

	@Test
	public void cloneElement() {

		List<ElementTypeVO> allElementsTypes = elementTypeService.getAll();
		Optional<ElementVO> respElement = null;

		ElementVO element = new ElementVO();

		element.setElementType(allElementsTypes.get(0));
		element.setId(allElementsTypes.get(0).getId());
		element.setAlias("ElementClone" + "_" + allElementsTypes.get(0).getAlias());
		element.setDescription("ElementClone" + "_" + allElementsTypes.get(0).getDescription());
		element.setLongitude(12.21);
		element.setLatitude(12.0);
		element.setIcon("icon ElementClone.svg");
		element.setPositionX(12.3);
		element.setPositionY(12.3);
		element.setZoomIn(12);
		element.setZoomOut(14);

		respElement = elementService.create(element);
		assertNotNull(respElement);

		ElementVO elementClone = new ElementVO();
		elementClone = respElement.get();
		Optional<ElementVO> cloneeElement = null;
		cloneeElement = elementService.clone(elementClone.getId());
		assertNotEquals("ElementClone", cloneeElement.get().getAlias());
		assertEquals(element.getAlias() + "Copy_", cloneeElement.get().getAlias());

		List<ElementVO> elementsAll = elementService.getAll();
		assertNotNull(respElement);

		for (ElementVO elementCloneFreeID : elementsAll) {

			if (elementCloneFreeID.getId().equals(cloneeElement.get().getId())) {

				// si le pasamos un id null o uno que no existe no borra el elemento
				elementCloneFreeID.setId(null);
				assertNull(elementService.clone(elementCloneFreeID.getId()));
			}

		}

		// delete element
		respElement = elementService.delete(respElement.get().getId());
		cloneeElement = elementService.delete(cloneeElement.get().getId());

		respElement = elementService.get(respElement.get().getId());
		assertNull(respElement);

		cloneeElement = elementService.get(cloneeElement.get().getId());
		assertNull(cloneeElement);
	}

	@Test
	public void deleteElementwithoutID() {

		ElementVO element = new ElementVO();

		element.setElementType(elementTypeService.getAll().get(0));
		element.setAlias("deleteElementwithoutID");
		element.setDescription("deleteElementwithoutID");

		// create element
		Optional<ElementVO> respElement = elementService.create(element);

		List<ElementVO> elementsAll = elementService.getAll();
		assertNotNull(respElement);

		for (ElementVO elementDelete : elementsAll) {

			if (elementDelete.getId().equals(respElement.get().getId())
					&& elementDelete.getAlias().contentEquals("deleteElementwithoutID")) {

				elementDelete.setId(null);
				assertNull(elementService.delete(elementDelete.getId()));

				elementDelete.setId(elementTypeService.getAll().get(0).getId() + "Error");
				assertNull(elementService.delete(elementDelete.getId()));
			}

		}

		respElement = elementService.delete(respElement.get().getId());
		assertNotNull(respElement);

		respElement = elementService.get(respElement.get().getId());
		assertNull(respElement);
	}

}
