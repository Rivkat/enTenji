package ru.ensemplix;

import static org.junit.Assert.*;

import org.junit.Test;

public class AbuseTest {

	@Test
	public void testContainsAbuse() {
		assertTrue(Abuse.containsAbuse("Админ какого wtf ?"));
		assertFalse(Abuse.containsAbuse("Всем привет"));
	}

	@Test
	public void testContainsYoutube() {
		assertTrue(Abuse.containsYoutube("Клевый канал www.youtube.com"));
		assertFalse(Abuse.containsAbuse("Всем привет"));
	}

	@Test
	public void testContainsIp() {
		assertTrue(Abuse.containsIp("Все заходите на мой сервер 127.0.0.1"));
		assertTrue(Abuse.containsIp("Все заходите на мой сервер 5.9.18.143:25735"));
		assertTrue(Abuse.containsIp("▆ ▅ ▃ ▂ __•★[Epic-World]★_ ▁ ▂ ▃ ▅ ▆ █ [1.5.2]"
				+ "█║•► ★5.9.18.143:25735 █║►•★100 слотов █"
						+ "█║►•★Наша группа http://vk.com/epicworld222█"
						+ " █║►•★ Мощный сервер лагов нет 100% █"
						+ " █║►•★Хостинг не лагает."));
		assertFalse(Abuse.containsAbuse("Всем привет"));

	}

}
