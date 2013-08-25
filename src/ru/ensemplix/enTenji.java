package ru.ensemplix;

import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class enTenji {

	private static final Logger logger = Logger.getLogger("enTenji");

	private static final String VERSION = "1.1";
	private static final String ACCESS_TOKEN = "";
	private static final String USER_ID = "";

	private HttpClient httpclient = new DefaultHttpClient();

	public static void main(String[] args) {
		BotLogger botLogger = new BotLogger();
		botLogger.startLogger();

		enTenji tenji = new enTenji();
		tenji.start();
	}

	public void start() {
		logger.info("Starting enTenji " + VERSION);

		while (true) {
			try {
				JSONObject wallMessages = getWallMessages();

				if (wallMessages.containsKey("response")) {
					JSONArray wallMessagesArray = (JSONArray) wallMessages
							.get("response");

					for (int i = 1; i < wallMessagesArray.size(); i++) {
						JSONObject wallPost = (JSONObject) wallMessagesArray
								.get(i);

						String postId = String.valueOf(wallPost.get("id"));

						JSONObject wallComments = getWallComments(postId);

						if (wallComments.containsKey("response")) {
							JSONArray wallCommentsArray = (JSONArray) wallComments
									.get("response");

							for (int e = 1; e < wallCommentsArray.size(); e++) {
								JSONObject wallComment = (JSONObject) wallCommentsArray
										.get(e);

								String commentId = String.valueOf(wallComment
										.get("cid"));
								String userId = String.valueOf(wallComment
										.get("uid"));
								String text = String.valueOf(wallComment
										.get("text"));

								if (Abuse.containsAbuse(text)) {
									deleteComment(commentId);
									ban(userId, 1440, "Мат [Tenji]");

									JSONObject userObject = getUser(userId);

									JSONArray userArray = (JSONArray) userObject
											.get("response");

									JSONObject user = (JSONObject) userArray
											.get(0);

									logger.info("Banned "
											+ user.get("first_name") + " "
											+ user.get("last_name") + " for `"
											+ text + "` (abuse)");
								} else if (Abuse.containsYoutube(text)) {
									deleteComment(commentId);
									ban(userId, 1440, "YouTube ссылка [Tenji]");

									JSONObject userObject = getUser(userId);

									JSONArray userArray = (JSONArray) userObject
											.get("response");

									JSONObject user = (JSONObject) userArray
											.get(0);

									logger.info("Banned "
											+ user.get("first_name") + " "
											+ user.get("last_name") + " for `"
											+ text + "` (youtube link)");
								} else if (Abuse.containsIp(text)) {
									deleteComment(commentId);
									ban(userId, 1440,
											"Реклама серверов [Tenji]");

									JSONObject userObject = getUser(userId);

									JSONArray userArray = (JSONArray) userObject
											.get("response");

									JSONObject user = (JSONObject) userArray
											.get(0);

									logger.info("Banned "
											+ user.get("first_name") + " "
											+ user.get("last_name") + " for `"
											+ text + "` (server ads)");
								}
							}
						} else if (wallComments.containsKey("error")) {
							logger.warning("VK API Comments Error: "
									+ wallComments.get("error"));
						}

						try {
							TimeUnit.SECONDS.sleep(3);
						} catch (InterruptedException e) {
							logger.log(Level.WARNING, "InterruptedException", e);
						}
					}
				} else if (wallMessages.containsKey("error")) {
					logger.warning("VK API Messages Error: "
							+ wallMessages.get("error"));
				}
			} catch (Exception e) {
				logger.log(Level.WARNING, "Exception", e);
			}

			try {
				TimeUnit.MINUTES.sleep(3);
			} catch (InterruptedException e) {
				logger.log(Level.WARNING, "InterruptedException", e);
			}
		}
	}
	
	public JSONObject getWallMessages() throws Exception {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost("api.vk.com")
				.setPath("/method/wall.get")
				.setParameter("oid", USER_ID)
				.setParameter("owner_id", "-30508451")
				.setParameter("count", "10")
				.setParameter("access_token", ACCESS_TOKEN);
		URI uri = builder.build();

		HttpGet httpget = new HttpGet(uri);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();

		if (entity != null) {
			InputStream instream = entity.getContent();
			JSONParser parser = new JSONParser();
			JSONObject jsonResponse = (JSONObject) parser.parse(IOUtils
					.toString(instream));
			EntityUtils.consume(entity);
			return jsonResponse;
		}

		return null;
	}
	
	public JSONObject getWallComments(String postId) throws Exception {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost("api.vk.com")
				.setPath("/method/wall.getComments")
				.setParameter("oid", USER_ID)
				.setParameter("post_id", postId)
				.setParameter("owner_id", "-30508451")
				.setParameter("count", "100")
				.setParameter("sort", "desc")
				.setParameter("access_token", ACCESS_TOKEN);
		URI uri = builder.build();

		HttpGet httpget = new HttpGet(uri);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();

		if (entity != null) {
			InputStream instream = entity.getContent();
			JSONParser parser = new JSONParser();
			JSONObject jsonResponse = (JSONObject) parser.parse(IOUtils
					.toString(instream));
			EntityUtils.consume(entity);
			return jsonResponse;
		}

		return null;
	}
	
	public void deleteComment(String commentId) throws Exception {
		
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost("api.vk.com")
				.setPath("/method/wall.deleteComment")
				.setParameter("oid", USER_ID)
				.setParameter("owner_id", "-30508451")
				.setParameter("comment_id", commentId)
				.setParameter("count", "10")
				.setParameter("access_token", ACCESS_TOKEN);
		URI uri = builder.build();

		HttpGet httpget = new HttpGet(uri);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		EntityUtils.consume(entity);
	}
	
	public void ban(String userId, long till, String reason) throws Exception {
		till *= 60;
		till += System.currentTimeMillis() / 1000L;

		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost("api.vk.com")
				.setPath("/method/groups.banUser").setParameter("oid", USER_ID)
				.setParameter("group_id", "30508451")
				.setParameter("user_id", userId)
				.setParameter("comment", reason)
				.setParameter("end_date", String.valueOf(till))
				.setParameter("comment_visible", "1")
				.setParameter("access_token", ACCESS_TOKEN);
		URI uri = builder.build();

		HttpGet httpget = new HttpGet(uri);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		EntityUtils.consume(entity);
	}
	
	public JSONObject getUser(String userId) throws Exception {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost("api.vk.com")
				.setPath("/method/users.get")
				.setParameter("user_ids", userId);
		URI uri = builder.build();

		HttpGet httpget = new HttpGet(uri);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();

		if (entity != null) {
			InputStream instream = entity.getContent();
			JSONParser parser = new JSONParser();
			JSONObject jsonResponse = (JSONObject) parser.parse(IOUtils
					.toString(instream));
			EntityUtils.consume(entity);
			return jsonResponse;
		}

		return null;
	}

}
