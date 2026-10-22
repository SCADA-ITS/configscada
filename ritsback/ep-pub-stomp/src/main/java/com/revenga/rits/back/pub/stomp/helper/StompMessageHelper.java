package com.revenga.rits.back.pub.stomp.helper;

import java.time.Instant;

import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.front.core.model.vo.BasicElementVO;
import com.revenga.rits.back.front.core.model.vo.StompMessage;

public class StompMessageHelper {

	private StompMessageHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static StompMessage get(int action, final String customDestination,
			final BasicElementVO... basicElementVOs) {

		StompMessage stompMessage = new StompMessage();

		switch (action) {

		case Command.ACTION_UPDATE:

			stompMessage.setAction(StompMessage.Action.UPDATE);

			break;

		case Command.ACTION_DELETE:

			stompMessage.setAction(StompMessage.Action.DELETE);

			break;

		case Command.ACTION_CREATE:

			stompMessage.setAction(StompMessage.Action.CREATE);

			break;

		case Command.ACTION_BLOCK:

			stompMessage.setAction(StompMessage.Action.BLOCK);

			break;
			
		default:
			break;
		}

		stompMessage.setTimestamp(Instant.now().getEpochSecond());
		stompMessage.setCustomDestination(customDestination);

		for (BasicElementVO basicElementVO : basicElementVOs) {

			stompMessage.getBasicElementsVO().add(basicElementVO);
		}
		return stompMessage;
	}

	public static StompMessage updateMessage(StompMessage stompMessage, final BasicElementVO... basicElementVOs) {

		stompMessage.setTimestamp(Instant.now().getEpochSecond());

		for (BasicElementVO basicElementVO : basicElementVOs) {

			stompMessage.getBasicElementsVO().add(basicElementVO);
		}
		return stompMessage;
	}

}
