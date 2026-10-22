package com.revenga.rits.back.test.rits.service.graphql.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import org.junit.Test;

import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.Pictogram;
import com.revenga.rits.back.data.core.model.PictogramElementTypeParam;
import com.revenga.rits.back.data.core.model.PictogramProtocolType;
import com.revenga.rits.back.data.core.model.PictogramProtocolTypeParam;
import com.revenga.rits.back.data.core.model.PictogramProtocolValue;
import com.revenga.rits.back.data.loader.service.CollectionManagerService;
import com.revenga.rits.back.entities.lib.repository.ElementTypeRepository;
import com.revenga.rits.back.entities.lib.repository.PictogramElementTypeParamRepository;
import com.revenga.rits.back.entities.lib.repository.PictogramProtocolTypeParamRepository;
import com.revenga.rits.back.entities.lib.repository.PictogramProtocolTypeRepository;
import com.revenga.rits.back.entities.lib.repository.PictogramProtocolValueRepository;
import com.revenga.rits.back.entities.lib.repository.PictogramRepository;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PictogramTest {

	private PictogramRepository pictogramRepository;
	private PictogramProtocolValueRepository pictogramProtocolValueRepository;
	private PictogramProtocolTypeRepository pictogramProtocolTypeRepository;
	private PictogramProtocolTypeParamRepository pictogramProtocolTypeParamRepository;
	private PictogramElementTypeParamRepository pictogramElementTypeRepository;
	private ElementTypeRepository elementTypeRepository;

	CollectionManagerService cms;

	static String login = "rits";
	static String password = "rits";
	static String url = "jdbc:postgresql://localhost:5430/rits";

	public PictogramTest() {

		super();

		cms = new CollectionManagerService();

		pictogramRepository = new PictogramRepository();
		pictogramProtocolValueRepository = new PictogramProtocolValueRepository();
		pictogramProtocolTypeRepository = new PictogramProtocolTypeRepository();
		pictogramProtocolTypeParamRepository = new PictogramProtocolTypeParamRepository();
		pictogramElementTypeRepository = new PictogramElementTypeParamRepository();
		elementTypeRepository = new ElementTypeRepository();

	}

	@Test
	public void p1_createData() {

		Connection conn = null;
		Statement stmt = null;

		Calendar cal = Calendar.getInstance();
		java.sql.Timestamp timestamp = new Timestamp(cal.getTimeInMillis());

		// insert pictogram

		String inserta = "insert into master.pictograms (pictogram_id,alias,description,created_at,updated_at)values('"
				+ 5 + "' ,'" + "nuevo elemento" + "' ,'" + "nuevo elemento" + "' ,'" + timestamp + "' ,'" + timestamp
				+ "')";

		try {

			conn = DriverManager.getConnection(url, login, password);
			stmt = conn.createStatement();
			stmt.executeUpdate(inserta);
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// insert pictogram_protocol_types

		inserta = "insert into master.pictogram_protocol_types (pictogram_protocol_type_id,alias,description,created_at,updated_at)values('"
				+ 4 + "' ,'" + "nuevo pictogramProtocolType" + "' ,'" + "nuevo pictogramProtocolType" + "' ,'"
				+ timestamp + "' ,'" + timestamp + "')";

		try {

			conn = DriverManager.getConnection(url, login, password);
			stmt = conn.createStatement();
			stmt.executeUpdate(inserta);
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// insert pictogram_element_types

		inserta = "insert into conf.pictogram_element_types (pictogram_id,pictogram_protocol_type_id,element_type_id,created_at,updated_at)values('"
				+ 5 + "' ,'" + 4 + "' ,'" + 33 + "' ,'" + timestamp + "' ,'" + timestamp + "')";

		try {

			conn = DriverManager.getConnection(url, login, password);
			stmt = conn.createStatement();
			stmt.executeUpdate(inserta);
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// insert pictogram_protocol_type_params
		inserta = "insert into master.pictogram_protocol_type_params (pictogram_protocol_type_id,pictogram_protocol_type_param_id,data_type_id,alias,description,created_at,updated_at)values('"
				+ 4 + "' ,'" + 4 + "' ,'" + 2 + "' ,'" + "nuevo pictogramProtocolTypeParam" + "' ,'"
				+ "nuevo pictogramProtocolTypeParam" + "' ,'" + timestamp + "' ,'" + timestamp + "')";

		try {

			conn = DriverManager.getConnection(url, login, password);
			stmt = conn.createStatement();
			stmt.executeUpdate(inserta);
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// insert pictogram_protocol_values
		inserta = "insert into conf.pictogram_protocol_values (pictogram_id,pictogram_protocol_type_id,pictogram_protocol_type_param_id,element_type_id,value,created_at,updated_at)values('"
				+ 5 + "' ,'" + 4 + "' ,'" + 4 + "' ,'" + 33 + "' ,'" + "nuevo pictogramProtocolType_values" + "' ,'"
				+ timestamp + "' ,'" + timestamp + "')";

		try {

			conn = DriverManager.getConnection(url, login, password);
			stmt = conn.createStatement();
			stmt.executeUpdate(inserta);
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// load cache

		cms.load();

	}

	@Test
	public void p2_pictogramTest() throws ClassNotFoundException {

		Connection conn = null;
		Calendar cal = Calendar.getInstance();
		java.sql.Timestamp timestamp = new Timestamp(cal.getTimeInMillis());

		List<Pictogram> allpictogram = pictogramRepository.getAll(true);
		assertNotNull(allpictogram);
		List<PictogramElementTypeParam> allPictogramElementType = null;
		Pictogram prictogramGet = new Pictogram();

		for (Pictogram pictogram : allpictogram) {

			prictogramGet = pictogramRepository.get(pictogram.getId(), true);
			assertNotNull(prictogramGet);

			if (pictogram.getId().equals(5l)) {

				assertEquals(prictogramGet.getAlias(), "nuevo elemento");
				assertEquals(prictogramGet.getDescription(), "nuevo elemento");
			}

			// getsByPictogram

			allPictogramElementType = pictogramElementTypeRepository.getsByPictogram(prictogramGet.getId(), false);
			assertNotNull(allPictogramElementType);

			for (PictogramElementTypeParam pictogramElementType : allPictogramElementType) {

				assertNotNull(pictogramElementType.getPictogramId());
				assertEquals(pictogramElementType.getPictogramId(), prictogramGet.getId());
			}
		}

		// getsByElementType

		List<ElementType> allElementType = elementTypeRepository.getAll(false);
		assertNotNull(allElementType);

		for (ElementType elementType : allElementType) {

			allPictogramElementType = pictogramElementTypeRepository.getsByElementTypeParam(elementType.getId(), false);

			if (!CollectionUtils.isEmpty(allPictogramElementType)) {
				assertNotNull(allPictogramElementType);

				for (PictogramElementTypeParam pictogramElementType : allPictogramElementType) {

					assertEquals(pictogramElementType.getElementTypeId(), elementType.getId());

				}
			}
		}

		allPictogramElementType = pictogramElementTypeRepository.getAll(false);
		assertNotNull(allPictogramElementType);

		for (PictogramElementTypeParam pictogramElementType : allPictogramElementType) {

			PictogramElementTypeParam pictogramElementTypeGet = pictogramElementTypeRepository
					.get(pictogramElementType.getPictogramId(), pictogramElementType.getElementTypeId(), false);
			assertNotNull(pictogramElementTypeGet);

		}

		// gets protocoltype

		List<PictogramProtocolType> allPictogramProtocolType = pictogramProtocolTypeRepository.getAll(true);
		assertNotNull(allPictogramProtocolType);

		for (PictogramProtocolType pictogramProtocolType : allPictogramProtocolType) {

			PictogramProtocolType pictogramProtocolTypee = pictogramProtocolTypeRepository
					.get(pictogramProtocolType.getId(), false);

			assertNotNull(pictogramProtocolTypee);

			if (pictogramProtocolType.getId().equals(4l)) {

				assertEquals(pictogramProtocolTypee.getAlias(), "nuevo pictogramProtocolType");
				assertEquals(pictogramProtocolTypee.getDescription(), "nuevo pictogramProtocolType");
			}

		}

		List<PictogramProtocolTypeParam> allPictogramProtocolTypeParam = pictogramProtocolTypeParamRepository
				.getAll(true);
		assertNotNull(allPictogramProtocolTypeParam);

		for (PictogramProtocolTypeParam pictogramProtocolTypeParam : allPictogramProtocolTypeParam) {

			PictogramProtocolTypeParam pictogramProtocolTypeParamm = pictogramProtocolTypeParamRepository.get(
					pictogramProtocolTypeParam.getId(), pictogramProtocolTypeParam.getPictogramProtocolTypeId(), false);

			assertNotNull(allPictogramProtocolTypeParam);
			if (pictogramProtocolTypeParam.getId().equals(4l) && (pictogramProtocolTypeParam.getPictogramProtocolTypeId().equals(4l))) {

				assertEquals(pictogramProtocolTypeParam.getId(), pictogramProtocolTypeParamm.getId());
				assertEquals(pictogramProtocolTypeParam.getAlias(), "nuevo pictogramProtocolTypeParam");
				assertEquals(pictogramProtocolTypeParam.getDescription(), "nuevo pictogramProtocolTypeParam");
			}
		}

		for (PictogramProtocolType pictogramProtocolType : allPictogramProtocolType) {

			allPictogramProtocolTypeParam = pictogramProtocolTypeParamRepository
					.getsByProtocolType(pictogramProtocolType.getId(), false);

			assertNotNull(allPictogramProtocolTypeParam);

			if (!CollectionUtils.isEmpty(allPictogramProtocolTypeParam)) {

				for (PictogramProtocolTypeParam pictogramProtocolTypeParamm : allPictogramProtocolTypeParam) {

					assertEquals(pictogramProtocolTypeParamm.getPictogramProtocolTypeId(),
							pictogramProtocolType.getId());

				}
			}

		}

		List<PictogramProtocolValue> allPictogramProtocolValue = pictogramProtocolValueRepository.getAll(true);
		assertNotNull(allPictogramProtocolValue);

		for (PictogramProtocolValue pictogramProtocolValue : allPictogramProtocolValue) {

			PictogramProtocolValue pictogramProtocolValuee = pictogramProtocolValueRepository.get(
					pictogramProtocolValue.getPictogramId(), pictogramProtocolValue.getPictogramProtocolTypeParamId(),
					pictogramProtocolValue.getElementTypeId(), false);

			assertNotNull(pictogramProtocolValuee);

			if (pictogramProtocolValue.getElementTypeId().equals(pictogramProtocolValuee.getElementTypeId())
					&& (pictogramProtocolValue.getPictogramId().equals(pictogramProtocolValuee.getPictogramId()))
					&& pictogramProtocolValue.getPictogramProtocolTypeParamId()
							.equals(pictogramProtocolValuee.getPictogramProtocolTypeParamId())) {

				assertEquals(pictogramProtocolValuee.getElementTypeId(), pictogramProtocolValue.getElementTypeId());
				assertEquals(pictogramProtocolValuee.getPictogramId(), pictogramProtocolValue.getPictogramId());
				assertEquals(pictogramProtocolValuee.getPictogramId(), pictogramProtocolValue.getPictogramId());
				assertEquals(pictogramProtocolValuee.getValue(), pictogramProtocolValue.getValue());
				
				if (pictogramProtocolValue.getElementTypeId().equals(33l)
						&& (pictogramProtocolValue.getPictogramId().equals(5l))
						&& pictogramProtocolValue.getPictogramProtocolTypeParamId()
								.equals(4l) && (pictogramProtocolValue.getPictogramProtocolTypeId().equals(4l))) {
					assertEquals(pictogramProtocolValue.getValue(), "nuevo pictogramProtocolType_values");
					
				}
				
			}
		}

		allPictogramElementType = pictogramElementTypeRepository.getAll(true);
		assertNotNull(allPictogramElementType);

		for (PictogramElementTypeParam pictogramElementType : allPictogramElementType) {

			allPictogramProtocolValue = pictogramProtocolValueRepository.getsByPictogramElementType(
					pictogramElementType.getPictogramId(), pictogramElementType.getElementTypeId(), false);

			assertNotNull(allPictogramProtocolValue);

			for (PictogramProtocolValue pictogramProtocolValue : allPictogramProtocolValue) {

				if ((pictogramElementType.getElementTypeId().equals(pictogramProtocolValue.getElementTypeId()))
						&& (pictogramElementType.getPictogramId().equals(pictogramProtocolValue.getPictogramId()))) {

					assertEquals(pictogramProtocolValue.getElementTypeId(), pictogramElementType.getElementTypeId());

				}

			}

		}

		// update

		String modifica = "update master.pictograms set alias = '" + "modificadoFinal" + "' ,description = '"
				+ "modificadoFinal" + "' ,updated_at = '" + timestamp + "'  where pictogram_id = (?);";

		try {
			conn = DriverManager.getConnection(url, login, password);
			PreparedStatement sentencia = conn.prepareStatement(modifica);
			sentencia.setInt(1, 5);
			sentencia.executeUpdate();

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		cms.load();
		allpictogram = pictogramRepository.getAll(true);
		assertNotNull(allpictogram);

		for (Pictogram pictogram : allpictogram) {

			prictogramGet = pictogramRepository.get(pictogram.getId(), true);
			assertNotNull(prictogramGet);

			if (pictogram.getId().equals(5l)) {

				assertNotEquals(prictogramGet.getAlias(), "nuevo elemento");
				assertNotEquals(prictogramGet.getDescription(), "nuevo elemento");
				assertEquals(prictogramGet.getAlias(), "modificadoFinal");
				assertEquals(prictogramGet.getDescription(), "modificadoFinal");
			}

		}

		// delete values

		try {
			conn = DriverManager.getConnection(url, login, password);
			PreparedStatement st = conn.prepareStatement(
					"DELETE FROM conf.pictogram_protocol_values where pictogram_id = (?) AND pictogram_protocol_type_id = (?) AND pictogram_protocol_type_param_id = (?) and element_type_id = (?);");
			st.setInt(1, 5);
			st.setInt(2, 4);
			st.setInt(3, 4);
			st.setInt(4, 33);
			int rowsDeleted = st.executeUpdate();
			System.out.println(rowsDeleted + " rows deleted");
			st.close();

		} catch (Exception e) {

			System.err.println(e.getClass().getName() + ": " + e.getMessage());

			System.exit(0);

		}

		// delete params

		try {
			conn = DriverManager.getConnection(url, login, password);
			PreparedStatement st = conn.prepareStatement(
					"DELETE FROM master.pictogram_protocol_type_params where pictogram_protocol_type_id = (?) AND pictogram_protocol_type_param_id = (?);");
			st.setInt(1, 4);
			st.setInt(2, 4);
			int rowsDeleted = st.executeUpdate();
			System.out.println(rowsDeleted + " rows deleted");
			st.close();

		} catch (Exception e) {

			System.err.println(e.getClass().getName() + ": " + e.getMessage());

			System.exit(0);

		}

		// delete pictogramsElementTypes

		try {
			conn = DriverManager.getConnection(url, login, password);
			PreparedStatement st = conn.prepareStatement(
					"DELETE FROM conf.pictogram_element_types where pictogram_id = (?) AND pictogram_protocol_type_id = (?) AND element_type_id = (?);");
			st.setInt(1, 5);
			st.setInt(2, 4);
			st.setInt(3, 33);
			int rowsDeleted = st.executeUpdate();
			System.out.println(rowsDeleted + " rows deleted");
			st.close();

		} catch (Exception e) {

			System.err.println(e.getClass().getName() + ": " + e.getMessage());

			System.exit(0);

		}

		// delete pictogramProtocolType

		try {
			conn = DriverManager.getConnection(url, login, password);
			PreparedStatement st = conn.prepareStatement(
					"DELETE FROM master.pictogram_protocol_types WHERE pictogram_protocol_type_id = ?");
			st.setInt(1, 4);
			int rowsDeleted = st.executeUpdate();
			System.out.println(rowsDeleted + " rows deleted");
			st.close();

		} catch (Exception e) {

			System.err.println(e.getClass().getName() + ": " + e.getMessage());

			System.exit(0);

		}

		// delete pictogram

		try {
			conn = DriverManager.getConnection(url, login, password);
			PreparedStatement st = conn.prepareStatement("DELETE FROM master.pictograms WHERE pictogram_id = ?");
			st.setInt(1, 5);
			int rowsDeleted = st.executeUpdate();
			System.out.println(rowsDeleted + " rows deleted");
			st.close();

		} catch (Exception e) {

			System.err.println(e.getClass().getName() + ": " + e.getMessage());

			System.exit(0);

		}

	}

}