package com.revenga.rits.front.openits.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revenga.rits.front.openitsdao.model.DataSource;
import com.revenga.rits.front.openitsdao.service.DataSourceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping(value = "/api/v1/datasources", produces = { "application/json", "application/xml" })
@Api(value = "DataSources", tags = { "DATA SOURCES" })
public class DataSourceController {

	@Autowired
	DataSourceService dataSourceService;

	@GetMapping
	@ApiOperation(value = "List all data sources", notes = "List all data sources")
	public ResponseEntity<List<?>> getAll() {

		List<DataSource> dataSources = dataSourceService.getAll();
		
		return new ResponseEntity<>(dataSources, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Find data source by id", notes = "Find data source item by id")
	public ResponseEntity<DataSource> getById(
			@ApiParam(name = "id", 
			          type = "Integer", 
			          value = "Data source identifier", 
			          example = "1", 
			          required = true) @PathVariable Integer id) {

		
		return new ResponseEntity<>(dataSourceService.getById(id), HttpStatus.OK);
	}
	
//	@PostMapping(value = "/create")
//	public ResponseEntity<List<DataSource>> createFiles() {
//
//		List<DataSource> dataSources = dataSourceService.getAll();
//		
//		Collections.sort(dataSources, (a, b) -> a.getId().compareTo(b.getId()));
//		
//		for (DataSource dataSource : dataSources) {
//			
//			if (Arrays.asList(DataSourceService.DATASOURCE_TYPE_QUERY, DataSourceService.DATASOURCE_TYPE_MUTATION)
//					.contains(dataSource.getDataSourceType().getId())) {
//			
//				List<DataSourceValue> dataSourceValues = dataSource.getDataSourceValues();
//				
//				if (!CollectionUtils.isEmpty(dataSourceValues)) {
//					
//					for (DataSourceValue dataSourceValue : dataSourceValues) {
//						
//						String graphqlBody = "";
//						String graphqlBodyFormatted = "";
//
//						if (Arrays
//								.asList(DataSourceService.DATASOURCE_TYPE_QUERY_CONTENT,
//										DataSourceService.DATASOURCE_TYPE_MUTATION_CONTENT)
//								.contains(dataSourceValue.getDataSourceParam().getId())) {
//
//							graphqlBody = dataSourceValue.getValue();
//							
//							if (!StringUtils.isEmpty(graphqlBody)) {
//								
//								Parser parser = new Parser();
//								Document doc = parser.parseDocument(graphqlBody);
//								graphqlBodyFormatted = AstPrinter.printAst(doc);
//								
//								try (Writer writer = new BufferedWriter(new OutputStreamWriter(
//										new FileOutputStream(
//												"/home/alvaro/repositorio/rits/ritsfront/openits-dao/src/main/resources/graphql/" + dataSource.getId() + ".gql"),
//										StandardCharsets.UTF_8))) {
//									writer.write(graphqlBodyFormatted);
//								} catch (InvalidSyntaxException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								} catch (IOException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//		
//		return new ResponseEntity<>(dataSources, HttpStatus.OK);
//	}
}