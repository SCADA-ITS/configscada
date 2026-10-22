package com.revenga.rits.front.openits.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {
	
	private StringUtil() {
		
		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static List<String> extractVariables(String template, String cadena) {
		
        List<String> variables = new ArrayList<>();

        // Crear una expresión regular basada en la plantilla
        String regex = template.replaceAll("\\{\\w+\\}", "(\\\\w+)");

        // Compilar la expresión regular
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cadena);

        // Buscar coincidencias y extraer las variables
        if (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                variables.add(matcher.group(i));
            }
        }

        return variables;
    }
}
