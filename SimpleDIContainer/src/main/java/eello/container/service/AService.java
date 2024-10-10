package main.java.eello.container.service;

import main.java.eello.container.repository.ARepository;
import main.java.eello.container.repository.Repository;
import main.java.eello.core.Component;

@Component
public class AService implements Service {

	private final Service service;
	private final Repository repository;

	// public AService(ARepository repository) {
	// 	this.repository = repository;
	// }

	public AService(BService service, Repository repository) {
		this.service = service;
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
