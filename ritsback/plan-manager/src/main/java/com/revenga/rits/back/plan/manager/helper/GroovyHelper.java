package com.revenga.rits.back.plan.manager.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.Element;

import com.revenga.rits.back.entities.lib.repository.AlarmRepository;
import com.revenga.rits.back.entities.lib.repository.ElementRepository;
import com.revenga.rits.back.data.core.util.ResourcesUtil;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class GroovyHelper {

	private GroovyHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	private static AlarmRepository createAlarmRepository() {

		return new AlarmRepository(false);
	}
	
	private static ElementRepository createElementRepository() {

		return new ElementRepository(false);
	}

	private static List<List<Pair<Alarm, Element>>> getLists(AlarmRepository alarmRepository,ElementRepository elementRepository ){
		List<List<Pair<Alarm, Element>>> total_array = new ArrayList<>();
		List<Pair<Alarm, Element>> casares = new ArrayList<>();
		List<Pair<Alarm, Element>> corominas = new ArrayList<>();
		List<Pair<Alarm, Element>> rioverde = new ArrayList<>();
		List<Pair<Alarm, Element>> montemayor = new ArrayList<>();
		List<Pair<Alarm, Element>> estepona = new ArrayList<>();
		List<Pair<Alarm, Element>> stamariai = new ArrayList<>();
		List<Pair<Alarm, Element>> stamariaii = new ArrayList<>();
		List<Pair<Alarm, Element>> calahonda = new ArrayList<>();
		List<Pair<Alarm, Element>> nagueles = new ArrayList<>();
		List<Pair<Alarm, Element>> laquinta = new ArrayList<>();
		int indexElement = 0;
		int pos = 0;
		int totales = 0;
		String tunel = "";
		List<Alarm> alarms_ = alarmRepository.getAll(false);
		List<Element> elements = elementRepository.getAll(false);

		if (!CollectionUtils.isEmpty(alarms_)) {
			for(Alarm al : alarms_){	
				if(al.getDeactivation()==null && al.getFilteredUntil()==null){
					totales++;
				
					indexElement = elements.indexOf(new Element(al.getSourceElementTypeId(), al.getSourceElementId()));
					pos = elements.get(indexElement).getAlias().indexOf("-"); 		
					tunel = elements.get(indexElement).getAlias().substring(0,pos);
					
					switch(tunel){
						case "RV":
							rioverde.add(new ImmutablePair<>(al, elements.get(indexElement)));
							break; 
						case "MM":
							montemayor.add(new ImmutablePair<>(al, elements.get(indexElement)));
							break; 
						case "LQ":
							laquinta.add(new ImmutablePair<>(al, elements.get(indexElement)));
							break; 
						case "CAS":
							casares.add(new ImmutablePair<>(al, elements.get(indexElement)));
							break; 
						case "COR":
							corominas.add(new ImmutablePair<>(al, elements.get(indexElement)));
							break; 
						case "EST":
							estepona.add(new ImmutablePair<>(al, elements.get(indexElement)));
							break; 
						case "STM2":
							stamariaii.add(new ImmutablePair<>(al, elements.get(indexElement)));
							break; 
						case "STMI":
							stamariai.add(new ImmutablePair<>(al, elements.get(indexElement)));
							break; 
						case "CH":
							calahonda.add(new ImmutablePair<>(al, elements.get(indexElement)));
							break; 
						case "NG":
							nagueles.add(new ImmutablePair<>(al, elements.get(indexElement)));
							break; 
					}
				}
			}
			total_array.add(rioverde);
			total_array.add(montemayor);
			total_array.add(stamariai);
			total_array.add(stamariaii);
			total_array.add(casares);
			total_array.add(corominas);
			total_array.add(laquinta);
			total_array.add(estepona);
			total_array.add(calahonda);
			total_array.add(nagueles);
		}
		return total_array;
	}

	public static List<List<Pair<Alarm, Element>>> initialize(){
		List<List<Pair<Alarm, Element>>> total_array = new ArrayList<>();
		AlarmRepository alarmRepository = createAlarmRepository();
		ElementRepository elementRepository = createElementRepository();
		if (alarmRepository != null && elementRepository != null) {
			total_array = getLists(alarmRepository, elementRepository);
		}
		return total_array;
		
	}

	public static int getTotales(List<List<Pair<Alarm, Element>>> array_total){
		int total = 0;
		for (List<Pair<Alarm, Element>> tunnel : array_total){
			for (Pair<Alarm, Element> pair : tunnel){
				total++;
			}
		}
		return total;
	}


	static class PropertiesReader{
		static List<Pair<String, String>> alarms;
		static List<Pair<String, String>> types;

		private static void init (String option){
			File file;
			if (option.equals("alarms")){
				file = new File(ResourcesUtil.getPath("plan-manager/groovy/i18n.properties"));
				if (alarms == null) {
					alarms = new ArrayList<>();
					try (BufferedReader br = new BufferedReader(new FileReader(file))) {
						String line;
						while ((line = br.readLine()) != null) {
							String[] parts = line.split("=");
							alarms.add(new ImmutablePair<>(parts[0], parts[1]));
						}
					} catch (IOException e) {
						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				}else{
					
					log.debug("Ya existia la lista de alarmas.");
				}

			}else {
				file = new File(ResourcesUtil.getPath("plan-manager/groovy/elementTypes.properties"));			
				if (types == null) {
					types = new ArrayList<>();
					try (BufferedReader br = new BufferedReader(new FileReader(file))) {
						String line;
						while ((line = br.readLine()) != null) {
							String[] parts = line.split("=");
							types.add(new ImmutablePair<>(parts[0], parts[1]));
						}
					} catch (IOException e) {
						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				}else{
					log.debug("Ya existia la lista de tipos.");
				}
			}
		}

		public static void initialize(){
			init("alarms");
			init("types");
		}

		private static String getName(List<Pair<String, String>> alarms, String id){
			String name = new String();
			if (id != null && alarms != null){
				for(Pair<String, String> al : alarms){	
					if(al.getKey().equals(id)){
						name = al.getValue();
					}
				}
			}
			if (name.isEmpty()){
				name = id;
			}
			return name;
		}

		public static String getBody(String tunnel_name ,List<Pair<Alarm, Element>> tunnel){
			String content = "";
			if (!CollectionUtils.isEmpty(tunnel)){
				int contador = 1;
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
				String[] color = {"#FFFFFF", "#ECF0F1"};
				if (!tunnel_name.equals("RIO VERDE")){
					content += "<td style=\"height:50px\"></td>";
				}
				content += "<tr><th colspan=\"4\"><h2>TUNEL DE "+ tunnel_name +" (" + tunnel.size() + ")</h2></th></tr>" +
				"<tr>\n" + 
					"<th style=\"background-color: #ADD8E6; width: 150px\" align=\"left\">Activación</th>\n" + 
					"<th style=\"background-color: #ADD8E6; width: 500px\" align=\"left\">Alarma</th>\n" + 
					"<th style=\"background-color: #ADD8E6; width: 200px\" align=\"left\">Tipo de equipo</th>\n" + 
					"<th style=\"background-color: #ADD8E6; width: 200px\" align=\"left\">Elemento</th>\n" + 
				"</tr>\n";

				for(Pair<Alarm, Element> el : tunnel){
					String id = String.valueOf(el.getLeft().getAlarmConfigId());
					String name =  getName(alarms, id);
					String element_type = String.valueOf(el.getRight().getElementTypeId());
					String element_name =  getName(types, element_type);
					content += "<tr>\n" +
							"<td style=\"background-color: " + color[contador%2] + "\">" + formatter.format(new Date(el.getLeft().getActivation())) + "</td>\n" +	
							"<td style=\"background-color: " + color[contador%2] + "\">" + name + "</td>\n" +	
							"<td style=\"background-color: " + color[contador%2] + "\">" + element_name + "</td>\n" +	
							"<td style=\"background-color: " + color[contador%2] + "\">" + el.getRight().getAlias() + "</td>\n" +	
						"</tr>\n";
					contador++;
				}
			}
			return content;
		}

	}

	static class Mail{
		Properties props;
		String addressTO;
		String addressCC;
		String addressBCC;
		String subject;
		String body;

		//GETTERS
		public Properties getProps(){
			return props;
		}

		public String getAddressTO(){
			return addressTO;
		}

		public String getAddressCC(){
			return addressCC;
		}

		public String getAddressBCC(){
			return addressBCC;
		}

		public String getSubject(){
			return subject;
		}

		public String getBody(){
			return body;
		}

		//SETTER 
		public void setProps(Properties props){
			this.props = props;
		}

		public void setSubject(String subject){
			this.subject = subject;
		}

	}
}