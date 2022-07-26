package com.revenga.rits.lib.encrypt.app;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang3.ArrayUtils;

import com.revenga.rits.lib.encrypt.util.AES;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Encrypt {

	private static final String ARG_ENCRYPT = "-e";
	private static final String ARG_DECRYPT = "-d";
	
	private enum Action {
		ENCRYPT, DECRYPT
	}
	
	public static void main(String[] args) {

		if (!ArrayUtils.isEmpty(args) && args.length > 1) {
			
			if (args[0].equals(ARG_ENCRYPT)) {
				
				callAES(Arrays.copyOfRange(args, 1, args.length), Action.ENCRYPT);
			}
			else if (args[0].equals(ARG_DECRYPT)){
				
				callAES(Arrays.copyOfRange(args, 1, args.length), Action.DECRYPT);
			}
			else {
				
				printUsage();
			}
		}
		else {
			
			printUsage();
		}
	}
	
	private static void printUsage() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(System.lineSeparator());
		sb.append("Usage: java -jar encrypt.jar [OPTION] [INPUTS]...");
		sb.append(System.lineSeparator());
		sb.append(System.lineSeparator());
		sb.append("\t-e\t\tEncrypt mode");
		sb.append(System.lineSeparator());
		sb.append("\t-d\t\tDecrypt mode");
		sb.append(System.lineSeparator());
		sb.append("\t[INPUTS]\tlist of strings separated by a space");
		sb.append(System.lineSeparator());
		sb.append(System.lineSeparator());
		sb.append("Example: java -jar encrypt.jar -e string1 string2 string3");
		sb.append(System.lineSeparator());
		
		log.info(sb.toString());
	}
	
	private static void callAES(String[] strs, Action action) {
		
		if (!ArrayUtils.isEmpty(strs) && action != null) {
			
			StringBuilder sb = new StringBuilder();
			
			Arrays.asList(strs).forEach(str -> {

				String output = null;

				try {
					
					if (action.equals(Action.ENCRYPT)) {

						output = AES.encrypt(str);
					} 
					else if (action.equals(Action.DECRYPT)) {

						output = AES.decrypt(str);
					}
					
				} catch (Exception e) {

					output = "ERROR: " + e.getMessage();
				}

				sb.append(String.format("%s -> %s%s", str, output, System.lineSeparator()));
			});
					
			log.info(sb.toString());
		}
	}
}
