package ru.ensemplix.api;

import java.util.logging.Logger;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public abstract class Api {
	
	private static final Logger logger = Logger.getLogger("enTenji");

	protected static final String ACCESS_TOKEN = "";
	protected static final String USER_ID = "";
	protected static final String GROUP_ID = "-30508451";

	protected static final HttpClient httpclient = new DefaultHttpClient();

	abstract public void execute() throws Exception;

	protected JSONObject jsonResponse;

	public JSONArray result() {
		if (!containsError()) {
			return (JSONArray) jsonResponse.get("response");
		} else {
			logger.warning("Api Error: " + getError());
			return null;
		}
	}

	public boolean containsError() {
		if (jsonResponse.containsKey("error")) {
			return true;
		}
		return false;
	}

	public String getError() {
		return jsonResponse.get("error").toString();
	}

}
