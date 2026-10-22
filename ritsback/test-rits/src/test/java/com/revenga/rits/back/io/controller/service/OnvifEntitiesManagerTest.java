package com.revenga.rits.back.io.controller.service;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

import com.revenga.rits.back.data.loader.service.CollectionManagerService;
import com.revenga.rits.back.io.controller.service.OnvifEntitiesManager.OnvifCamera;

public class OnvifEntitiesManagerTest {

	public OnvifEntitiesManagerTest() {

		CollectionManagerService cms = new CollectionManagerService();
		cms.load();
	}

	@Test
	public void Test1() throws InterruptedException {

		int resp = 0;
		Scanner reader = new Scanner(System.in);

		OnvifCamera onvifCamera = OnvifEntitiesManager.getInstance().getCamera(1L);

		if(onvifCamera != null){
			
			while (resp != -1) {

				System.out.println("1 : Pan\n" + "2: Tilt\n" + "3: Zoom\n" + "4: Exit\n");

				resp = reader.nextInt();

				if (resp != -1) {

					System.out.println("Insert value\n");
					double value = reader.nextDouble();

					switch (resp) {
					case 1:
						onvifCamera.relativeMove(value, 0L, 0L);
						break;

					case 2:
						onvifCamera.relativeMove(0L, value, 0L);
						break;

					case 3:
						onvifCamera.relativeMove(0L, 0L, value);
						break;

					default:
						break;
					}
				}
			}
			
			onvifCamera.deInit();
			System.out.println("End");
			reader.close();
		}
	}
}
