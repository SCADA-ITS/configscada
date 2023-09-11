package com.revenga.rits.front.openitsdao.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.revenga.rits.front.openitsdao.model.DataSource;
import com.revenga.rits.front.openitsdao.model.DataSourceValue;
import com.revenga.rits.front.openitsdao.repository.DataSourceRepository;
import com.revenga.rits.front.openitsdao.service.DataSourceService;

import graphql.language.AstPrinter;
import graphql.language.Document;
import graphql.parser.Parser;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class DataSourceImpl implements DataSourceService {
	
	private Map<Integer, String> graphqlMap = new HashMap<>();
	@Autowired
	DataSourceRepository dataSourceRepository;
	
	@Override
	public List<DataSource> getAll() {

		List<DataSource> datasources = dataSourceRepository.findAll();
		
		if (!CollectionUtils.isEmpty(datasources)) {
			
			datasources.forEach(this::replaceValue);
		}
		
		return datasources;
	}

	@Override
	public DataSource getById(Integer id) {
		
		DataSource dataSource = dataSourceRepository.findById(id);
		
		replaceValue(dataSource);
		
		return dataSource;
	}
	
	private String getGraphql(DataSource dataSource) {
		
		String graphql = null;
		
		if (dataSource != null && Arrays.asList(DATASOURCE_TYPE_QUERY, DATASOURCE_TYPE_MUTATION)
				.contains(dataSource.getDataSourceType().getId())) {
			
			graphql = graphqlMap.get(dataSource.getId());
			
			if (StringUtils.isEmpty(graphql)) {
				
				StringBuilder sbGraphql = new StringBuilder();
				InputStream is = this.getClass().getClassLoader()
						.getResourceAsStream(DATASOURCE_FOLDER + File.separator + dataSource.getId() + ".gql");
				
				if (is != null) {
					
					try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
				    	
				        String line;
				        
				        while ((line = br.readLine()) != null) {
				        	
				        	sbGraphql.append(line).append("\n");
				        }
				        
				        graphql = sbGraphql.toString();
				        
				        Parser parser = new Parser();
				        Document doc = parser.parseDocument(graphql);
				        graphql = AstPrinter.printAstCompact(doc);
				        
				        graphqlMap.put(dataSource.getId(), graphql);
				        
				    }
				    catch (Exception e) {
			            
				    	log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
			        }
				}
			}
		}
		
		return graphql;
	}
	
	private void replaceValue(DataSource dataSource) {
		
		String graphql = null;
		
		if (dataSource != null && Arrays.asList(DATASOURCE_TYPE_QUERY, DATASOURCE_TYPE_MUTATION)
				.contains(dataSource.getDataSourceType().getId())) {
			
			graphql = getGraphql(dataSource);
			
			if (!StringUtils.isEmpty(graphql)) {
				
				DataSourceValue dataSourceValueContent = null;
				
				List<DataSourceValue> dataSourceValues = dataSource.getDataSourceValues();
				
				if (!CollectionUtils.isEmpty(dataSourceValues)) {
				        
			        if (dataSource.getDataSourceType().getId().equals(DATASOURCE_TYPE_QUERY)) {
						
			        	dataSourceValueContent = dataSourceValues.stream().filter(e -> e.getDataSourceParam().getId().equals(DATASOURCE_TYPE_QUERY_CONTENT)).findFirst().orElse(null);
					}
					else if (dataSource.getDataSourceType().getId().equals(DATASOURCE_TYPE_MUTATION)) {
						
						dataSourceValueContent = dataSourceValues.stream().filter(e -> e.getDataSourceParam().getId().equals(DATASOURCE_TYPE_MUTATION_CONTENT)).findFirst().orElse(null);
					}
			        
			        if (dataSourceValueContent != null) {
			        	
			        	dataSourceValueContent.setValue(graphql);
			        }
				}
			}
		}
	}
}
