package com.revenga.rits.front.openits.model;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
public class ClientInfoUI implements Serializable {

	@Data
	@AllArgsConstructor
	public class GitInfo implements Serializable {

		public static final int LINE_COMMIT = 1;
		public static final int LINE_AUTHOR = 2;
		public static final int LINE_DATE = 3;
		
		private static final long serialVersionUID = 9219610171768632514L;
	
		String commit;
		String author;
		String date;
		String message;
	}
	
	private static final long serialVersionUID = 1783450175806606694L;
	
	String version;
	GitInfo gitInfo;
	
	long upTime;

	public void createGitInfo(String commit, String author, String date, String message) {
		
		this.gitInfo = new GitInfo(commit, author, date, message);
	}
}

