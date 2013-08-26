package ru.ensemplix;

import static org.junit.Assert.*;

import org.junit.Test;

public class AbuseTest {

	@Test
	public void testContainsAbuse() {
		Abuse abuse = new Abuse("Админ какого wtf ?");
		assertTrue(abuse.containsAbuse());
		
		abuse = new Abuse("Всем привет");
		assertFalse(abuse.containsAbuse());
	}

	@Test
	public void testContainsYoutube() {
		Abuse abuse = new Abuse("Клевый канал www.youtube.com");
		assertTrue(abuse.containsYoutube());
		
		abuse = new Abuse("Всем привет");
		assertFalse(abuse.containsYoutube());
	}

	@Test
	public void testContainsIp() {
		Abuse abuse = new Abuse("Все заходите на мой сервер 127.0.0.1");
		assertTrue(abuse.containsIp());

		abuse = new Abuse("Все заходите на мой сервер 5.9.18.143:25735");
		assertTrue(abuse.containsIp());

		abuse = new Abuse("▆ ▅ ▃ ▂ __•★[Epic-World]★_ ▁ ▂ ▃ ▅ ▆ █ [1.5.2]"
				+ "█║•► ★5.9.18.143:25735 █║►•★100 слотов █"
				+ "█║►•★Наша группа http://vk.com/epicworld222█"
				+ " █║►•★ Мощный сервер лагов нет 100% █"
				+ " █║►•★Хостинг не лагает.");
		assertTrue(abuse.containsIp());

		abuse = new Abuse("Всем привет");
		assertFalse(abuse.containsIp());
	}
	
	@Test
	public void testcontainsVkGroupURL() throws Exception {
		Abuse abuse = new Abuse("Все заходите в нашу группу http://vk.com/idiotikshow");
		assertTrue(abuse.containsVkGroupURL());
		
		abuse = new Abuse("Главный администратор http://vk.com/enber");
		assertFalse(abuse.containsVkGroupURL());
		
		abuse = new Abuse("Наша группа http://vk.com/ensemplix");
		assertFalse(abuse.containsVkGroupURL());
	}

}



