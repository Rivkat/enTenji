package ru.ensemplix;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ru.ensemplix.api.BanApi;
import ru.ensemplix.api.CommentDeleteApi;
import ru.ensemplix.api.CommentsApi;
import ru.ensemplix.api.PostsApi;
import ru.ensemplix.api.UserApi;

public class enTenji {

	private static final Logger logger = Logger.getLogger("enTenji");

	private static final String VERSION = "1.2";

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
				
				PostsApi posts = new PostsApi();
				posts.execute();
				JSONArray wallMessages = posts.result();

				if (wallMessages != null) {
					for (int i = 1; i < wallMessages.size(); i++) {
						JSONObject wallPost = (JSONObject) wallMessages.get(i);

						String postId = String.valueOf(wallPost.get("id"));

						CommentsApi comments = new CommentsApi();
						comments.setId(postId);
						comments.execute();

						JSONArray wallComments = comments.result();
						if (wallComments != null) {
							for (int e = 1; e < wallComments.size(); e++) {
								JSONObject wallComment = (JSONObject) wallComments
										.get(e);

								String commentId = String.valueOf(wallComment
										.get("cid"));
								String userId = String.valueOf(wallComment
										.get("uid"));
								String text = String.valueOf(wallComment
										.get("text"));

								Abuse abuse = new Abuse(text);

								if (abuse.containsAbuse()) {

									CommentDeleteApi commentDelete = new CommentDeleteApi();
									commentDelete.setId(commentId);
									commentDelete.execute();

									BanApi ban = new BanApi(userId, 1440,
											"Мат [Tenji]");
									ban.execute();

									UserApi user = new UserApi();
									user.setId(userId);
									user.execute();

									String name = user.getName();
									String lastName = user.getLastName();

									logger.info("Banned " + name + " "
											+ lastName + " for `" + text
											+ "` (abuse)");
								} else if (abuse.containsYoutube()) {

									CommentDeleteApi commentDelete = new CommentDeleteApi();
									commentDelete.setId(commentId);
									commentDelete.execute();

									BanApi ban = new BanApi(userId, 1440,
											"Ссылка на youtube [Tenji]");
									ban.execute();

									UserApi user = new UserApi();
									user.setId(userId);
									user.execute();

									String name = user.getName();
									String lastName = user.getLastName();

									logger.info("Banned " + name + " "
											+ lastName + " for `" + text
											+ "` (youtube link)");
								} else if (abuse.containsIp()) {

									CommentDeleteApi commentDelete = new CommentDeleteApi();
									commentDelete.setId(commentId);
									commentDelete.execute();

									BanApi ban = new BanApi(userId, 90 * 1440,
											"Реклама сервера [Tenji]");
									ban.execute();

									UserApi user = new UserApi();
									user.setId(userId);
									user.execute();

									String name = user.getName();
									String lastName = user.getLastName();

									logger.info("Banned " + name + " "
											+ lastName + " for `" + text
											+ "` (server ads)");
								} else if (abuse.containsVkGroupURL()) {

									CommentDeleteApi commentDelete = new CommentDeleteApi();
									commentDelete.setId(commentId);
									commentDelete.execute();

									BanApi ban = new BanApi(userId, 90 * 1440,
											"Реклама группы [Tenji]");
									ban.execute();

									UserApi user = new UserApi();
									user.setId(userId);
									user.execute();

									String name = user.getName();
									String lastName = user.getLastName();

									logger.info("Banned " + name + " "
											+ lastName + " for `" + text
											+ "` (vk group url)");
								}
							}
							
							try {
								TimeUnit.SECONDS.sleep(2);
							} catch (InterruptedException e) {
								logger.log(Level.WARNING, "InterruptedException", e);
							}
							
						}
					}
				}
			} catch (Exception e) {
				logger.log(Level.WARNING, "Exception", e);
			}
		}
	}

}
