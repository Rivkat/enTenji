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

public class BanApi extends Api {
	
	private String userId;
	private long banTime;
	private String reason;
	
	public BanApi(String userId, int time, String reason) {
		this.userId = userId;
		this.banTime = time;
		banTime *=60;
		banTime += System.currentTimeMillis() / 1000L;
		this.reason = reason;
	}

	public void execute() throws Exception {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost("api.vk.com")
				.setPath("/method/groups.banUser").setParameter("oid", USER_ID)
				.setParameter("group_id", "30508451")
				.setParameter("user_id", userId)
				.setParameter("comment", reason)
				.setParameter("end_date", String.valueOf(banTime))
				.setParameter("comment_visible", "1")
				.setParameter("access_token", ACCESS_TOKEN);
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

}
