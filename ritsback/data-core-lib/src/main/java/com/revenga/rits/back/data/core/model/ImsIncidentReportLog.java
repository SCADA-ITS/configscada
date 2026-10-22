package com.revenga.rits.back.data.core.model;

import java.util.List;

import com.revenga.rits.back.data.core.annotation.AssociatedKeys;
import com.revenga.rits.back.data.core.annotation.DbConfig;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.annotation.TimeStampField;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeys(fields = { "id" })
@DbConfig(db = "2")
public class ImsIncidentReportLog extends BasicElement implements Comparable<ImsIncidentReportLog> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4628441741092572501L;

	private Long incidentReportId;
	private Long logSubtypeId;
	private Long userId;
	private Integer level;

	@TimeStampField
	private Long time;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@AssociatedKeys(references = {"id=incidentReportLogId"})
	private List<ImsIncidentReportLogValue> incidentReportLogValues;
	
	@Override
	public int compareTo(ImsIncidentReportLog incidentReportLog) {

		return (incidentReportLog == null || incidentReportLog.getTime() == null) ? 1
				: (this.time == null ? -1 : this.time.compareTo(incidentReportLog.getTime()));
	}
}
