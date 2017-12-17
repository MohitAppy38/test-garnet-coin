package com.example.demo;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class GlobalUtility.
 *
 * @author Vishal Gajera (vgajera@hibbertgroup.com)
 * @PCR# 161227140838(MCOE Phase 1) JIRA MCOE-290
 */
public final class GlobalUtility {

	/**
	 * Instantiates a new global utility.
	 */
	private GlobalUtility() {
		//
	}

	/**
	 * Convert class to json.
	 *
	 * @param input
	 *            the input
	 * @return the string
	 * @throws JsonProcessingException
	 *             the json processing exception
	 */
	public static String convertClassToJson(final Object input) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		return objectMapper.writeValueAsString(input);
	}

}
