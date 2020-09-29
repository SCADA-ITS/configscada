package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the module_types database table.
 * 
 */
@Entity
@Table(name="module_types")
@NamedQuery(name="ModuleType.findAll", query="SELECT m FROM ModuleType m")
public class ModuleType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String description;

	private Boolean enabled;

	private String name;

	private Boolean visible;

	//bi-directional many-to-one association to ModuleParam
	@OneToMany(mappedBy="moduleType")
	private List<ModuleParam> moduleParams;

	//bi-directional many-to-one association to WindowParam
	@OneToMany(mappedBy="moduleType")
	private List<WindowParam> windowParams;

	public ModuleType() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getVisible() {
		return this.visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public List<ModuleParam> getModuleParams() {
		return this.moduleParams;
	}

	public void setModuleParams(List<ModuleParam> moduleParams) {
		this.moduleParams = moduleParams;
	}

	public ModuleParam addModuleParam(ModuleParam moduleParam) {
		getModuleParams().add(moduleParam);
		moduleParam.setModuleType(this);

		return moduleParam;
	}

	public ModuleParam removeModuleParam(ModuleParam moduleParam) {
		getModuleParams().remove(moduleParam);
		moduleParam.setModuleType(null);

		return moduleParam;
	}

	public List<WindowParam> getWindowParams() {
		return this.windowParams;
	}

	public void setWindowParams(List<WindowParam> windowParams) {
		this.windowParams = windowParams;
	}

	public WindowParam addWindowParam(WindowParam windowParam) {
		getWindowParams().add(windowParam);
		windowParam.setModuleType(this);

		return windowParam;
	}

	public WindowParam removeWindowParam(WindowParam windowParam) {
		getWindowParams().remove(windowParam);
		windowParam.setModuleType(null);

		return windowParam;
	}

}