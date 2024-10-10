package main.java.eello.container.repository;

import main.java.eello.core.Component;

@Component
public class ARepository implements Repository {

	@Override
	public void func() {
		System.out.println("ARepository.func");
	}
}
