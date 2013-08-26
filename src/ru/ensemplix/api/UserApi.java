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

public class UserApi extends Api {
	
	private String userId;

	public void setId(String userId) {
		this.userId = userId;
	}

	public void execute() throws Exception {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost("api.vk.com")
				.setPath("/method/users.get").setParameter("user_ids", userId);
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

	public String getName() {
		JSONObject user = (JSONObject) result().get(0);
		return (String) user.get("first_name");
	}

	public String getLastName() {
		JSONObject user = (JSONObject) result().get(0);
		return (String) user.get("last_name");
	}

}
