import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.virtual.equipment.manager.vms.model.TravelTime;
import com.revenga.rits.back.virtual.equipment.manager.vms.model.VmsMessageZone;
import com.revenga.rits.back.virtual.equipment.manager.vms.model.VmsMessageZoneText;

class TravelTime_71_18 {

	org.apache.logging.log4j.Logger log;

	// zone in which we will write the travel times  
	static final Integer ZONE_TRAVEL_TIMES = 2;
	// Max line char length in panel
	static final Integer MAX_LINE_CHARS = 12;
	// Default template for panel (empty travel times template)
	static final String JSON_DATA_TEMPLATE = "[{\"zone\":1,\"graphics\":[{\"id\":1,\"value\":874}]},{\"zone\":2,\"alternance_ms\":5000,\"align\": \"left\",\"texts\":[{\"id\":1,\"value\":\"\"},{\"id\":2,\"value\":\"\"},{\"id\":3,\"value\":\"\"}]}]";
	// Assign value to TIME_UDS to show in text lines. Otherwise, put empty value
	//static final String TIME_UDS = "min";
	static final String TIME_UDS = "";
	// Graphic zone with minutes pictograms 
	static final Integer ZONE_GRAPHIC_MINUTES = 3;
	// Id's array wityh minutes pictograms code
	static final List<Integer> MINUTES_PICTOGRAMS = Arrays.asList(875, 876, 877); 
	

	 TravelTime_71_18(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	/**
	 * 
	 * Method to get travel times message for panel of subtype 22
	 * 
	 * @param vms	Panel element object
	 * @param travelTimes	List of assigned travel times to panel
	 * @param currentMessageZones	Current panel message
	 * @param newMessageZones	New message to get with travel tims 
	 * 
	 * @return	true if success. false if any error
	 */
	
	boolean process(final Element vms, final List<TravelTime> travelTimes,
			final List<VmsMessageZone> currentMessageZones, List<VmsMessageZone> newMessageZones) {

		ObjectMapper mapper = new ObjectMapper();
		int travelTimesOK = 0;
		boolean res = false;

		try {

			List<VmsMessageZone> vmsMessageZones = mapper.readValue(JSON_DATA_TEMPLATE,
					mapper.getTypeFactory().constructCollectionType(List.class, VmsMessageZone.class));

			if (!CollectionUtils.isEmpty(vmsMessageZones)) {

				for (VmsMessageZone vmsMessageZone : vmsMessageZones) {

					if (vmsMessageZone.getZone().equals(ZONE_TRAVEL_TIMES)
							&& !CollectionUtils.isEmpty(vmsMessageZone.getTexts())
							&& !CollectionUtils.isEmpty(travelTimes)) {
						
						log.debug(String.format("Travel times for %s (%s)", vms.getAlias(), (new Key(vms)).getSimpleValue()));
						
						for (int travelTimeIdx = 0; travelTimeIdx < travelTimes.size(); travelTimeIdx++) {

							TravelTime travelTime = travelTimes.get(travelTimeIdx);

							if (travelTime != null) {

								if (travelTime.getTime() != null && travelTime.getTime() > 0) {

									if (travelTimesOK < vmsMessageZone.getTexts().size()) {

										VmsMessageZoneText zoneText = vmsMessageZone.getTexts().get(travelTimesOK++);
										
										if (zoneText != null) {

											String formatLine = "%-"+ (MAX_LINE_CHARS - 3) + "s %2s%s\n\n";
											
											if (!StringUtils.isEmpty(TIME_UDS)) {
												
												formatLine = "%-"+ (MAX_LINE_CHARS - 3 - TIME_UDS.length()) + "s %2s%s\n\n";
											}
										
											log.debug(String.format("NOMBRE #%d: %s", travelTimeIdx + 1, travelTime.getName()))
											String lineTravelTime = String.format(formatLine, travelTime.getName(), travelTime.getTime(), TIME_UDS).trim();
											log.debug(String.format("lineTravelTime #%d: %s", travelTimeIdx + 1, lineTravelTime))
											zoneText.setValue(lineTravelTime + " min");

											log.debug(String.format("line #%d: %s", travelTimeIdx + 1, lineTravelTime));
										}

									} else {

										log.warn(String.format("[%s] %s (#%d) does not fit in the panel",
												(new Key(vms)).getSimpleValue(), travelTime.getName(),
												(travelTimeIdx + 1)));
									}
								} else {

									log.warn(String.format("[%s] %s not available", (new Key(vms)).getSimpleValue(),
											travelTime.getName()));
								}
							}
						}
					}

					if (vmsMessageZone.getZone().equals(ZONE_GRAPHIC_MINUTES)
							&& !CollectionUtils.isEmpty(vmsMessageZone.getGraphics())) {
						
						if (travelTimesOK > 0) {
							
							vmsMessageZone.getGraphics().get(0).setValue(MINUTES_PICTOGRAMS.get(travelTimesOK - 1));
						}
						else {
							
							vmsMessageZone.getGraphics().get(0).setValue(null);
						}
					}
					
					newMessageZones.add(vmsMessageZone);
				}
			}
			
			if (travelTimesOK == 0) {
				
				log.debug(String.format("No travel times for %s (%s)", vms.getAlias(), (new Key(vms)).getSimpleValue()));
			}
			
			res = true;

		} catch (JsonProcessingException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return res;
	}
}

