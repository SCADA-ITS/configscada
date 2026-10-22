package com.revenga.rits.back.test.rits.data.cache;

import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import be.teletask.onvif.OnvifManager;
import be.teletask.onvif.listeners.OnvifMediaProfilesListener;
import be.teletask.onvif.listeners.OnvifResponseListener;
import be.teletask.onvif.models.OnvifDevice;
import be.teletask.onvif.models.OnvifMediaProfile;
import be.teletask.onvif.models.OnvifType;
import be.teletask.onvif.requests.OnvifRequest;
import be.teletask.onvif.responses.OnvifResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Onviftest {

	private class MyOnvifManager implements OnvifMediaProfilesListener, OnvifResponseListener {

		public class RelativeMovePTZ implements OnvifRequest {

			// Attributes
			private OnvifMediaProfile mediaProfile;
			private double pan = 0, tilt = 0, zoom = 0;

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

		public void init(String hostName, String username, String password) {

			onvifManager = new OnvifManager();
			onvifManager.setOnvifResponseListener(this);
			device = new OnvifDevice(hostName, username, password);
			onvifManager.getMediaProfiles(device, this);
		}

		public void deinit() {

			onvifManager.destroy();
		}

		@Override
		public void onMediaProfilesReceived(OnvifDevice device, List<OnvifMediaProfile> mediaProfiles) {

			this.mediaProfile = mediaProfiles.get(0);
		}

		@Override
		public void onResponse(OnvifDevice onvifDevice, OnvifResponse response) {

			log.info(response.getXml() + "\n");
		}

		@Override
		public void onError(OnvifDevice onvifDevice, int errorCode, String errorMessage) {

			log.error(errorCode + "-> " + errorMessage);
		}

		public void relativeMove(OnvifDevice device, OnvifMediaProfile profile, double pan, double tilt, double zoom) {
			OnvifRequest request = new RelativeMovePTZ(profile, pan, tilt, zoom);
			onvifManager.sendOnvifRequest(device, request);
		}

		public boolean pan(double pan) {

			boolean resp = false;

			if (this.mediaProfile != null) {

				relativeMove(device, mediaProfile, pan, 0.0f, 0.0f);
				resp = true;
			}
			return resp;
		}

		public boolean tilt(double tilt) {

			boolean resp = false;

			if (this.mediaProfile != null) {

				relativeMove(device, mediaProfile, 0.0f, tilt, 0.0f);
				resp = true;
			}
			return resp;
		}

		public boolean zoom(double zoom) {

			boolean resp = false;

			if (this.mediaProfile != null) {

				relativeMove(device, mediaProfile, 0.0f, 0.0f, zoom);
				// onvifManager.absoluteMove(device, mediaProfile, 0.0f, 0.0f, zoom);
				resp = true;
			}
			return resp;
		}
	}

	@Test
	public void Test1() throws InterruptedException {

		int resp = 0;
		Scanner reader = new Scanner(System.in);

		MyOnvifManager myOnvifManager = new MyOnvifManager();
		myOnvifManager.init("192.168.88.251", "admin", "(R4D7)Revenga");

		while (resp != -1) {

			System.out.println("1 : Pan\n" + "2: Tilt\n" + "3: Zoom\n" + "4: Exit\n");

			resp = reader.nextInt();

			if (resp != -1) {

				System.out.println("Insert value\n");
				double value = reader.nextDouble();

				switch (resp) {
				case 1:
					myOnvifManager.pan(value);
					break;

				case 2:
					myOnvifManager.tilt(value);
					break;

				case 3:
					myOnvifManager.zoom(value);
					break;

				default:
					break;
				}
			}
		}

		System.out.println("End");
		reader.close();
		myOnvifManager.deinit();
	}
}
