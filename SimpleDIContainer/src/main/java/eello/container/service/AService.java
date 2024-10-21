package main.java.eello.container.service;

import main.java.eello.container.repository.Repository;
import main.java.eello.core.Component;
import main.java.eello.core.Primary;

@Primary
@Component
public class AService implements Service {

	private final Repository repository;

	public AService(Repository repository) {
		this.repository = repository;
	}

	@Override
	public void func() {
		System.out.println("AService.func");
		repository.func();
	}

	@Override
	public Repository getRepository() {
		return repository;
	}
}
