package com.revenga.rits.back.io.controller.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.entities.lib.repository.ElementValueRepository;

import be.teletask.onvif.OnvifManager;
import be.teletask.onvif.listeners.OnvifMediaProfilesListener;
import be.teletask.onvif.listeners.OnvifPresetsListener;
import be.teletask.onvif.listeners.OnvifResponseListener;
import be.teletask.onvif.models.OnvifDevice;
import be.teletask.onvif.models.OnvifMediaProfile;
import be.teletask.onvif.models.OnvifPreset;
import be.teletask.onvif.models.OnvifType;
import be.teletask.onvif.requests.OnvifRequest;
import be.teletask.onvif.responses.OnvifResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class OnvifEntitiesManager {
	public static final String FILE_CONSTANTS = "constants.properties";

	public static final long ELEMENT_TYPE_PARAM_ONVIF_HOSTNAME = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.camera.param.onvif_hostname"));

	public static final long ELEMENT_TYPE_PARAM_ONVIF_USERNAME = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.camera.param.onvif_username"));

	public static final long ELEMENT_TYPE_PARAM_ONVIF_PASSWORD = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.camera.param.onvif_password"));

	public class OnvifCamera implements OnvifMediaProfilesListener, OnvifResponseListener, OnvifPresetsListener {

		public class RelativeMovePTZ implements OnvifRequest {

			// Attributes
			private OnvifMediaProfile mediaProfile;
			private double pan = 0;
			private double tilt = 0;
			private double zoom = 0;

			// Constructors
			public RelativeMovePTZ(OnvifMediaProfile mediaProfile, double pan, double tilt, double zoom) {
				this.mediaProfile = mediaProfile;
				this.pan = pan;
				this.tilt = tilt;
				this.zoom = zoom;
			}

			// Properties
			@Override
			public String getXml() {

				return "<RelativeMove xmlns=\"http://www.onvif.org/ver20/ptz/wsdl\">" + "<ProfileToken>"
						+ mediaProfile.getToken() + "</ProfileToken>" + "<Translation > " + "<PanTilt x=\"" + pan
						+ "\" y=\"" + tilt + "\" xmlns=\"http://www.onvif.org/ver10/schema\" />" + "<Zoom x=\"" + zoom
						+ "\" xmlns=\"http://www.onvif.org/ver10/schema\" />" + "</Translation >" + "</RelativeMove>";
			}

			@Override
			public OnvifType getType() {

				return OnvifType.CUSTOM;
			}
		}

		private OnvifManager onvifManager;
		private OnvifMediaProfile mediaProfile;
		private OnvifDevice device;
		private Semaphore mutex;

		public boolean isInit() {

			return (this.device != null && this.onvifManager != null);
		}

		public void init(String hostName, String username, String password) {

			onvifManager = new OnvifManager();
			onvifManager.setOnvifResponseListener(this);
			device = new OnvifDevice(hostName, username, password);
			mutex = new Semaphore(1);

			getMediaProfile();
		}

		public void deInit() {

			onvifManager.destroy();
			this.onvifManager = null;
			this.device = null;
			this.mediaProfile = null;

			if (mutex != null) {

				mutex.release();
				mutex = null;
			}
		}

		@Override
		public void onMediaProfilesReceived(OnvifDevice device, List<OnvifMediaProfile> mediaProfiles) {

			if (!CollectionUtils.isEmpty(mediaProfiles)) {

				log.info(mediaProfiles.get(0) + "\n");
				this.mediaProfile = mediaProfiles.get(0);

			} else {

				log.error("Not found mediaProfile " + device + "\n");
			}

			if (mutex != null) {

				mutex.release();
			}
		}

		public boolean getMediaProfile() {

			boolean resp = false;
			boolean acquire = false;

			if (this.device != null && this.mutex != null) {

				try {

					acquire = this.mutex.tryAcquire(5000, TimeUnit.MILLISECONDS);

					if (acquire) {

						onvifManager.getMediaProfiles(this.device, this);
						resp = true;
					}

				} catch (InterruptedException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
					Thread.currentThread().interrupt();
				}
			}

			return resp;
		}

		public boolean relativeMove(double pan, double tilt, double zoom) {

			boolean resp = false;

			try {

				boolean acquire = false;
				acquire = this.mutex.tryAcquire(5000, TimeUnit.MILLISECONDS);

				if (acquire && this.mediaProfile != null) {

					OnvifRequest request = new RelativeMovePTZ(this.mediaProfile, pan, tilt, zoom);
					onvifManager.sendOnvifRequest(this.device, request);
					resp = true;
				}

			} catch (InterruptedException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
			}

			return resp;
		}

		public boolean absoluteMove(double pan, double tilt, double zoom) {

			boolean resp = false;

			try {
				boolean acquire = false;

				acquire = this.mutex.tryAcquire(5000, TimeUnit.MILLISECONDS);

				if (acquire && this.mediaProfile != null) {

					onvifManager.absoluteMove(this.device, this.mediaProfile, pan, tilt, zoom);
					resp = true;
				}

			} catch (InterruptedException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
			}
			return resp;
		}

		public boolean gotoPreset(String presetId) {

			boolean resp = false;

			if (presetId != null) {
				
				try {
					boolean acquire = false;

					acquire = this.mutex.tryAcquire(5000, TimeUnit.MILLISECONDS);

					if (acquire && this.mediaProfile != null) {

						onvifManager.gotoPreset(this.device, this.mediaProfile, new OnvifPreset(presetId, ""));
						resp = true;
					}

				} catch (InterruptedException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
					Thread.currentThread().interrupt();
				}
			}

			return resp;
		}

		@Override
		public void onResponse(OnvifDevice onvifDevice, OnvifResponse response) {

			log.info(response.getXml() + "\n");

			if (mutex != null) {

				mutex.release();
			}
		}

		@Override
		public void onError(OnvifDevice onvifDevice, int errorCode, String errorMessage) {

			log.error(errorCode + "-> " + errorMessage);
			deInit();
		}

		@Override
		public void onPresetsReceived(OnvifDevice device, OnvifMediaProfile profile, List<OnvifPreset> presets) {

			log.info(presets.toString() + "\n");

			if (mutex != null) {

				mutex.release();
			}
		}

	}

	private static OnvifEntitiesManager instance;
	private Map<Long, OnvifCamera> cameras;

	ElementValueRepository elementValueRepository;

	public static OnvifEntitiesManager getInstance() {

		if (instance == null) {

			instance = new OnvifEntitiesManager();
		}

		return instance;
	}

	private OnvifEntitiesManager() {

		cameras = new HashedMap<>();
		this.elementValueRepository = new ElementValueRepository(false);
	}

	private OnvifCamera initCamera(Long cameraId) {

		OnvifCamera camera = null;

		String onvifHostname = null;
		String onvifUserName = null;
		String onvifPassword = null;

		if (cameraId != null) {

			List<ElementValue> elementValues = this.elementValueRepository.gets(ElementType.ELEMENT_TYPE_CAMERA,
					cameraId, ParamType.CONFIG, false);

			if (!CollectionUtils.isEmpty(elementValues)) {

				ElementValue elemntValue = elementValues.stream()
						.filter(e -> Long.valueOf(ELEMENT_TYPE_PARAM_ONVIF_HOSTNAME).equals(e.getElementTypeParamId()))
						.findFirst().orElse(null);

				if (elemntValue != null && elemntValue.getValue() != null) {

					onvifHostname = elemntValue.getValue();
				}

				elemntValue = elementValues.stream()
						.filter(e -> Long.valueOf(ELEMENT_TYPE_PARAM_ONVIF_USERNAME).equals(e.getElementTypeParamId()))
						.findFirst().orElse(null);

				if (elemntValue != null && elemntValue.getValue() != null) {

					onvifUserName = elemntValue.getValue();
				}

				elemntValue = elementValues.stream()
						.filter(e -> Long.valueOf(ELEMENT_TYPE_PARAM_ONVIF_PASSWORD).equals(e.getElementTypeParamId()))
						.findFirst().orElse(null);

				if (elemntValue != null && elemntValue.getValue() != null) {

					onvifPassword = elemntValue.getValue();
				}
			}

			if (onvifHostname != null && onvifUserName != null && onvifPassword != null) {

				camera = new OnvifCamera();
				camera.init(onvifHostname, onvifUserName, onvifPassword);
			}
		}

		return camera;
	}

	public OnvifCamera getCamera(final Long cameraId) {

		OnvifCamera camera = cameras.get(cameraId);

		if (camera == null) {

			camera = initCamera(cameraId);

			if (camera != null) {

				cameras.put(cameraId, camera);
			}
		}

		if (camera != null && !camera.isInit()) {

			cameras.remove(cameraId);
			camera = initCamera(cameraId);

			if (camera != null) {

				cameras.put(cameraId, camera);
			}
		}

		return camera;
	}

}
