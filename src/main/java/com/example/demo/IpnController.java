package com.example.demo;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IpnController {

	/**
	 * log4j.Logger.
	 */
	private static final Logger LOGGER = LogManager.getLogger("Logger");

	@RequestMapping(value = "/ipn", method = RequestMethod.POST)
	public String ipn(HttpServletRequest request) throws IOException {
		LOGGER.info("start - /IPN-GET");
		// LOGGER.info(GlobalUtility.convertClassToJson(request));
		// LOGGER.info("request.getAuthType - " + request.getAuthType());
		// LOGGER.info("request.getCharacterEncoding - " +
		// request.getCharacterEncoding());
		// LOGGER.info("request.getContentLength - " +
		// request.getContentLength());
		// LOGGER.info("request.getContentLengthLong - " +
		// request.getContentLengthLong());
		// LOGGER.info("request.getContentType - " + request.getContentType());
		// LOGGER.info("request.getContextPath - " + request.getContextPath());
		// LOGGER.info("request.isSecure - " + request.isSecure());
		// LOGGER.info("request.getPathInfo - " + request.getPathInfo());
		// LOGGER.info("request.getQueryString- " + request.getQueryString());
		// LOGGER.info("request.getMethod - " + request.getMethod());
		// LOGGER.info("request.getRequestURI - " + request.getRequestURI());
		// LOGGER.info("request.getRequestURL - " + request.getRequestURL());
		// LOGGER.info("request.getProtocol- " + request.getProtocol());
		// LOGGER.info("request.getRemoteAddr- " + request.getRemoteAddr());
		// LOGGER.info("request.getRemoteHost - " + request.getRemoteHost());
		// LOGGER.info("request.getRemotePort - " + request.getRemotePort());
		// LOGGER.info("request.getRemoteUser- " + request.getRemoteUser());
		// LOGGER.info("request.getScheme - " + request.getScheme());
		// LOGGER.info("request.getServerName - " + request.getServerName());
		// LOGGER.info("request.getServerPort - " + request.getServerPort());
		// LOGGER.info("request.getServletPath - " + request.getServletPath());

		LOGGER.info("Start - Read All Header");
		Enumeration<String> attributeNames = request.getHeaderNames();
		while (attributeNames.hasMoreElements()) {
			String headerName = attributeNames.nextElement();
			if ("hmac".equalsIgnoreCase(headerName)) {
				LOGGER.info("headerName - " + headerName);
				Enumeration<String> getAllHeaderValue = request.getHeaders(headerName);
				int count = 0;
				while (getAllHeaderValue.hasMoreElements()) {
					String headerValue = getAllHeaderValue.nextElement();
					LOGGER.info(count + " - headerValue - " + headerValue);
					count++;
				}
			}
		}
		LOGGER.info("End - Read All Header");

		LOGGER.info("Start - Read All Parameter");
		attributeNames = request.getParameterNames();
		while (attributeNames.hasMoreElements()) {
			String paramName = attributeNames.nextElement();
			LOGGER.info("paramName - " + paramName);
			String[] getAllParamValue = request.getParameterValues(paramName);
			int count = 0;
			for (String paramValue : getAllParamValue) {
				LOGGER.info(count + " , paramName - " + paramName + " , paramValue - " + paramValue);
				count++;
			}
		}
		LOGGER.info("End - Read All Parameter");

		/*
		LOGGER.info("Start - Read All Attribute");
		attributeNames = request.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();
			LOGGER.info("attributeName - " + attributeName);
			String value = request.getAttribute(attributeName).toString();
			LOGGER.info("attr value - " + value);
		}
		LOGGER.info("End - Read All Attribute");

		String path = request.getServletContext().getRealPath(File.separator);
		LOGGER.info("path - " + path);
		path = path + File.separator + "newFile" + System.nanoTime() + ".txt";
		LOGGER.info("new path - " + path);
		File file = new File(path);
		if (file.createNewFile()) {
			LOGGER.info("File is created!");
			// FileWriter fw = new FileWriter(file);
			// String data = GlobalUtility.convertClassToJson(ipnMap);
			// LOGGER.info("date - " + data);
			// fw.write(data);
			// fw.flush();
			// fw.close();
		} else {
			LOGGER.info("File already exists.");
		} */
		LOGGER.info("end - /IPN-GET");
		return "ipn-successfully received";
	}
}
