package ru.ensemplix;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class BotLogger {
	
	private static final Logger logger = Logger.getLogger("enTenji");

	public void startLogger() {
		ConsoleFormat consoleFormat = new ConsoleFormat();
		logger.setUseParentHandlers(false);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(consoleFormat);
		logger.addHandler(consoleHandler);

		String currentDir = new File(".").getAbsolutePath();

		try {
			FileHandler var2 = new FileHandler(currentDir + File.separator
					+ "bot.log", true);
			var2.setFormatter(consoleFormat);
			logger.addHandler(var2);
		} catch (Exception var3) {
			logger.log(Level.WARNING, "Failed to log to bot.log", var3);
		}
	}

	public class ConsoleFormat extends Formatter {

		private SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		public String format(LogRecord log) {
			StringBuilder sb = new StringBuilder();
			sb.append(this.dateFormat.format(Long.valueOf(log.getMillis())));

			Level lvl = log.getLevel();

			if (lvl == Level.FINEST) {
				sb.append(" [FINEST] ");
			} else if (lvl == Level.FINER) {
				sb.append(" [FINER] ");
			} else if (lvl == Level.FINE) {
				sb.append(" [FINE] ");
			} else if (lvl == Level.INFO) {
				sb.append(" [INFO] ");
			} else if (lvl == Level.WARNING) {
				sb.append(" [WARNING] ");
			} else if (lvl == Level.SEVERE) {
				sb.append(" [SEVERE] ");
			} else {
				sb.append(" [").append(lvl.getLocalizedName()).append("] ");
			}

			sb.append(log.getMessage());
			sb.append('\n');
			Throwable thr = log.getThrown();

			if (thr != null) {
				StringWriter var5 = new StringWriter();
				thr.printStackTrace(new PrintWriter(var5));
				sb.append(var5.toString());
			}

			return sb.toString();
		}
	}
	
}
