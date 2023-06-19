package com.revenga.rits.front.openits.backoffice;

public abstract class BackOfficeAction {

	protected BackOfficeProcessor backOfficeProcessor;

	protected BackOfficeAction(BackOfficeProcessor backOfficeProcessor) {

		this.backOfficeProcessor = backOfficeProcessor;
	}
}
