package main.java.eello.container.contoller;

import main.java.eello.container.service.AService;
import main.java.eello.container.service.Service;
import main.java.eello.core.Component;

@Component
public class AController {

	private final Service service;

	public AController(AService service) {
		this.service = service;
	}

	public void func() {
		System.out.println("AController.func");
		service.func();
	}

	public Service getService() {
		return service;
	}
}