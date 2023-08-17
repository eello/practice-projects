package eello.oauthback.repository;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Repository;

@Repository
public class OAuthStateRepository {

	private Set<String> cache = new HashSet<>();

	public void save(String state) {
		cache.add(state);
	}

	public boolean isExists(String state) {
		return cache.contains(state);
	}
}
