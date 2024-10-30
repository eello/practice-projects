package main.java.eello.app.service;

import main.java.eello.app.repository.Repository;
import main.java.eello.container.annotation.Component;
import main.java.eello.container.annotation.Primary;

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
