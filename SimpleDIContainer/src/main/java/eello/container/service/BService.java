package main.java.eello.container.service;

import main.java.eello.container.repository.BRepository;
import main.java.eello.container.repository.Repository;
import main.java.eello.core.Component;

// @Component
public class BService implements Service {

	// private final Service service;
	private final Repository repository;

	public BService(BRepository repository) {
		this.repository = repository;
	}

	// public BService(AService service, Repository repository) {
	// 	this.service = service;
	// 	this.repository = repository;
	// }

	@Override
	public void func() {
		System.out.println("BService.func");
	}

	@Override
	public Repository getRepository() {
		return repository;
	}
}
