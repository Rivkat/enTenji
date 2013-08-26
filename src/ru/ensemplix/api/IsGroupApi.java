package ru.ensemplix.api;

import java.io.InputStream;
import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class IsGroupApi extends Api {

	private String id;

	public void setId(String id) {
		this.id = id;
	}

	public void execute() throws Exception {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost("api.vk.com")
				.setPath("/method/utils.resolveScreenName")
				.setParameter("screen_name", id);
		URI uri = builder.build();

		HttpGet httpget = new HttpGet(uri);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();

		if (entity != null) {
			InputStream instream = entity.getContent();
			JSONParser parser = new JSONParser();
			jsonResponse = (JSONObject) parser
					.parse(IOUtils.toString(instream));
			EntityUtils.consume(entity);
		}
	}

	public boolean isGroup() {
		if (jsonResponse.get("response") instanceof JSONObject) {
			JSONObject object = (JSONObject) jsonResponse.get("response");
			if (String.valueOf(object.get("type")).equals("group")) {
				return true;
			}
		}
		return false;
	}

}
