package main.java.eello.app.service;

import main.java.eello.app.repository.Repository;
import main.java.eello.container.annotation.Component;

//@Primary
@Component
public class BService implements Service {

	private final Repository repository;

	public BService(Repository repository) {
		this.repository = repository;
	}

	@Override
	public void func() {
		System.out.println("BService.func");
		repository.func();
	}

	@Override
	public Repository getRepository() {
		return repository;
	}
}
