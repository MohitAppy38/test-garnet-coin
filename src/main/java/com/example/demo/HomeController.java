package com.example.demo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class HomeController {

	/**
	 * log4j.Logger.
	 */
	private static final Logger LOGGER = LogManager.getLogger("Logger");

	private static final String HMAC_SHA512 = "HmacSHA512";

	@Resource
	private RestTemplate restTemplate;

	@RequestMapping("/ico")
	public String ico() {
		LOGGER.info("/ico endpoint accessed");
		return "ico";
	}

	@RequestMapping(value = "/ipn", method = RequestMethod.POST)
	public String ipn(HttpServletRequest request, @RequestBody final Map<String, String> ipnMap) throws IOException {
		LOGGER.info("/IPN-GET");
		LOGGER.info(GlobalUtility.convertClassToJson(ipnMap));

		Enumeration<String> attributeNames = request.getAttributeNames();
		LOGGER.info("Before - iterate");
		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();
			LOGGER.info("attributeName - " + attributeName);
			String value = (String) request.getAttribute(attributeName);
			LOGGER.info("value - " + value);
		}
		LOGGER.info("After - iterate");

		String path = request.getServletContext().getRealPath(File.separator);
		LOGGER.info("path - " + path);
		path = path + File.separator + "newFile" + System.nanoTime() + ".txt";
		LOGGER.info("new path - " + path);
		File file = new File(path);
		if (file.createNewFile()) {
			LOGGER.info("File is created!");
			FileWriter fw = new FileWriter(file);
			String data = GlobalUtility.convertClassToJson(ipnMap);
			LOGGER.info("date - " + data);
			fw.write(data);
			fw.flush();
			fw.close();
		} else {
			LOGGER.info("File already exists.");
		}
		LOGGER.info("End - ipn");
		return "ipn";
	}

	@RequestMapping(value = "/jstlCheck", method = RequestMethod.POST)
	public String jstlCheck(Model model, HttpServletRequest request) throws IOException {
		LOGGER.info("Start - jstlCheck");
		StringBuffer requestURI = request.getRequestURL();
		String finalIpnUrl = requestURI.substring(0, requestURI.indexOf("/jstlCheck"));
		model.addAttribute("ipn", finalIpnUrl + "/ipn");
		model.addAttribute("amount", "0.00100000");
		model.addAttribute("txn_id", "CPBL5D9SQDPYD5KTTDUAYR0TVF");
		model.addAttribute("address", "39c6edKsjSAr9DWBfygccYigTTRe8VNAcx");
		model.addAttribute("confirms_needed", "2");
		model.addAttribute("status_url",
				"https://www.coinpayments.net/index.php?cmd=status&id=CPBL5D9SQDPYD5KTTDUAYR0TVF&key=e855fef499e9b2d6fd23df5cad8441b4");
		model.addAttribute("qrcode_url",
				"https://www.coinpayments.net/qrgen.php?id=CPBL5D9SQDPYD5KTTDUAYR0TVF&key=e855fef499e9b2d6fd23df5cad8441b4");
		String ipnUrl = finalIpnUrl + "/ipn";
		model.addAttribute("ipn_url", ipnUrl);
		LOGGER.info("End - jstlCheck");
		return "jstlCheck";
	}

	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public String payment(Model model, @RequestParam(value = "amt", required = true) String amt,
			HttpServletRequest request) throws JsonProcessingException, InvalidKeyException, SignatureException,
			NoSuchAlgorithmException, UnsupportedEncodingException {
		LOGGER.info("/payment endpoint accessed");
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("version", "1");
		map.add("key", "6f40ec3464c086703cd6ed2428a78e2724ecbcd39b6b88945d9584fabaef1c56");
		map.add("cmd", "create_transaction");
		map.add("currency1", "USD");
		map.add("currency2", "USD");
		map.add("amount", amt);
		map.add("format", "json");
		StringBuffer requestURI = request.getRequestURL();
		String finalIpnUrl = requestURI.substring(0, requestURI.indexOf("/payment"));
		String ipnUrl = finalIpnUrl.trim() + "/ipn";
		LOGGER.info("32 : ipnUrl - " + ipnUrl);

		String privateKey = "c49083B6fd66b5c6408eF4cd94C23D9bB8F1C06D5c39b74A8bfEd4CDCA733bD6";

		Iterator<String> it = map.keySet().iterator();

		String data = "";
		while (it.hasNext()) {
			String key = it.next();
			String value = map.getFirst(key);
			if (data.isEmpty()) {
				data = key + "=" + value;
			} else {
				data = data + "&" + key + "=" + value;
			}

		}

		LOGGER.info("data - ");
		LOGGER.info(data);
		String hmacString = calculateHMAC(data, privateKey);
		LOGGER.info("hmacString - ");
		LOGGER.info(hmacString);

		HttpHeaders headers = new HttpHeaders();
		headers.add("HMAC", hmacString);
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<MultiValueMap<String, String>> customRequest = new HttpEntity<MultiValueMap<String, String>>(map,
				headers);

		String url = "https://www.coinpayments.net/api.php";

		LOGGER.info("restTemplate - " + restTemplate);
		LOGGER.info("request - " + customRequest);

		ResponseEntity<Response> response = restTemplate.postForEntity(url, customRequest, Response.class);

		LOGGER.info(response);
		LOGGER.info(response.getBody());
		LOGGER.info("###########################");
		LOGGER.info(GlobalUtility.convertClassToJson(response));
		LOGGER.info("###########################");
		LOGGER.info(GlobalUtility.convertClassToJson(response.getBody()));
		LOGGER.info("###########################");
		Response customResponse = response.getBody();
		Result result = customResponse.getResult();
		LOGGER.info("amount - " + result.getAmount());
		model.addAttribute("amount", result.getAmount());
		LOGGER.info("txn_id - " + result.getTxn_id());
		model.addAttribute("txn_id", result.getTxn_id());
		LOGGER.info("address - " + result.getAddress());
		model.addAttribute("address", result.getAddress());
		LOGGER.info("confirms_needed - " + result.getConfirms_needed());
		model.addAttribute("confirms_needed", result.getConfirms_needed());
		LOGGER.info("status_url - " + result.getStatus_url());
		model.addAttribute("status_url", result.getStatus_url());
		LOGGER.info("qrcode_url - " + result.getQrcode_url());
		model.addAttribute("qrcode_url", result.getQrcode_url());
		LOGGER.info("END - /payment endpoint accessed");
		return "payment";
	}

	private static String toHexString(byte[] bytes) {
		Formatter formatter = new Formatter();
		for (byte b : bytes) {
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}

	public static String calculateHMAC(String data, String key)
			throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA512);
		Mac mac = Mac.getInstance(HMAC_SHA512);
		mac.init(secretKeySpec);
		return toHexString(mac.doFinal(data.getBytes()));
	}

}
