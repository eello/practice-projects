# Simple DI Container

많은 개발자들이 스프링이 시작되면서 컴포넌트 스캔을 통해 `@Component`, `@Controller`, `@Service` 등의 어노테이션을 적용한 클래스에 대해 인스턴스를 생성해 빈으로 관리한다는 것은 알고있습니다.
하지만 정작 대상 클래스들을 어떻게 찾고 어떻게 인스턴스를 생성하는지에 대해서는 관심이 두지 않았습니다.

그러다 이 대상 클래스를 찾고 인스턴스를 생성하는 기술로 `ClassPath Scan`과 `Reflection`이라는 것을 알게되었고 왠지 간단하게라도 스프링에서 DI해주는 컨테이너 비슷하게 만들 수 있을 것같다는 생각에 만든 프로젝트입니다.  

## 스택
- Java 21

## 제한사항
간단하게 구현해볼 목적으로 아래와 같은 가정을 가지고 구현하였습니다.
- 빈으로 등록할 클래스는 `@Component`로만 지정합니다.
- 의존성 주입은 생성자 주입 방식만 지원하며 대상 클래스의 생성자는 하나만 존재합니다.
- 부모 클래스를 상속받은 경우는 없으며 인터페이스만 구현합니다. 즉, 상위 타입은 인터페이스 타입입니다.
- 또한, 상위 타입은 1단계 위까지만 고려합니다. 예를들어, A 인터페이스를 확장한 B 인스턴스를 빈으로 등록될 C 클래스가 구현했더라도 B 타입까지만 고려합니다.  

  
## 흐름
<img width="986" alt="image" src="https://github.com/user-attachments/assets/a5c5559a-4df2-4a88-9558-246453f7ee2a">

1. Application이 실행되면 `BeanInitalizer`의 `initalize()` 메서드가 실행됩니다.
2. `ClassPathBeanDefinitionScanner`로 `@Component`가 적용된 클래스를 찾습니다.
3. 대상 클래스를 적절한 순서로 빈으로 생성해 등록하고 의존성 주입 시 등록된 빈을 사용해 새로운 빈을 생성하고 주입합니다.  



  
## 주요 클래스


  
### ClassPathBeanDefinitionScanner
`@Component`가 적용된 클래스를 찾는 클래스로 `ClassLoader`를 사용해 main 메서드가 실행되는 클래스의 패키지 경로를 기준으로 내부의 모든 클래스를 찾습니다.
찾은 모든 클래스에 대해 Reflection의 기능을 사용해 `@Component` 어노테이션이 적용된 클래스만 뽑아 BeanDefinition으로 가공해 리턴합니다.

---

### BeanDefinition
빈으로 등록될 클래스에 대한 정보를 저장하는 클래스입니다. 멤버 변수는
  - **beanName** : 빈의 이름
  - **beanType** : 빈의 현재 클래스 타입
  - **interfaces** : 빈이 구현하고 있는 인터페이스 목록
  - **dependsOn** : 해당 빈을 생성하기 위해 필요한, 즉 생성자의 파라미터 타입 목록
  - **scope** : 빈의 스코프로 SINGLETON만 지원합니다.

주요 메서드로 `newInstance(Object[] params)`가 있습니다. 이 메서드는 생성자에 필요한 파라미터 배열을 받아 이를 사용해 실제 빈의 인스턴스를 생성하는 메서드 입니다. 인스턴스를 생성하기 위해 `Refelction`의 기능을 사용합니다.

---

### BeanInitializer
`ClassPathBeanDefinitionScanner`를 사용해 빈 후보를 찾고 이를 적절한 순서로 등록합니다. 구현체는 `BeanInitializerUsingTopologicalSort`입니다.
빈을 생성할 때 필요한 파라미터는 이미 생성되어 있는 빈을 사용해야 합니다. 때문에 빈이 등록되는 순서가 중요합니다. 예를들어 B 인스턴스를 생성하기 위해 A 타입의 빈이 필요하다면 A 타입의 빈이 먼저 만들어져야 합니다.

이러한 순서를 결정하기 위해 클래스 이름에서도 알 수 있듯 위상정렬 알고리즘을 사용하였고 그 중에서도 익숙한 Kahn 알고리즘을 사용하였습니다.
> 위상정렬을 구현하는 방식으로 indegree를 계산해 0인 것부터 처리하는 Kahn 알고리즘과 DFS를 사용한 방식이 있습니다.

위상정렬을 사용해 자신이 의존하는 클래스가 없거나 이미 생성된 빈 후보 순서로 빈 등록을 시도합니다.

---

### BeanFactory
실제로 빈 인스턴스를 생성하고 등록하는 클래스입니다. 구현체는 `DefaultBeanFactory`입니다. 동일한 이름의 빈이 등록되지 않았다면 등록되려는 빈이 의존하고 있는 타입의 등록되어있는 빈을 사용해 생성에 필요한 파라미터 배열을 만들어 빈을 생성합니다.

이때, `@Primary`를 추가해 파라미터 타입에 등록된 빈이 2개 이상인 경우 `@Primary`가 적용된 클래스를 찾아 주입하게 됩니다.

생성한 빈은 멤버 변수인 `beansByName`과 `beansByType`에 저장합니다.

- **beansByName** : 빈의 이름 - 빈의 인스턴스
- **beansByType** : 빈의 타입 - 타입이 동일한 인스턴스들

beansByType에는 빈의 타입뿐만 아니라 구현하고 있는 인터페이스 타입에도 빈 인스턴스가 동일하게 포함됩니다. 이렇게 등록된 빈들은 구현된 `getBean()` 메서드를 통해 가져올 수 있습니다.


## 결과
테스트의 사용될 클래스들은 간략하게 다음과 같습니다.
```java
@Component
public class AController {

	private final Service service;

	public AController(Service service) {
		this.service = service;
	}

	...
}

@Primary
@Component
public class AService implements Service {

	private final Repository repository;

	public AService(Repository repository) {
		this.repository = repository;
	}

	...
}

@Component
public class BService implements Service {

	private final Repository repository;

	public BService(Repository repository) {
		this.repository = repository;
	}

	...
}

@Component
public class ARepository implements Repository {

	@Override
	public void func() {
		System.out.println("ARepository.func");
	}
}
```

이를 의존관계로 나타내면 다음과 같습니다.

![image](https://github.com/user-attachments/assets/f172d4c9-8d15-4d98-b168-7c09e2bf32cf)

의존하고 있는 ARepository부터 생성되고 이를 주입받는 AService, BService가 생성된 후 마지막으로 AController 순서로 생성이 될 것입니다. 또한, AService에 @Primary가 적용되어 있기 때문에 Service를 의존하는 AController에는 AService가 주입되어야 하며 각각의 클래스에는 등록된 빈이 주입되어야 할 것입니다.

이를 테스트하는 코드입니다.

```java
public static void main(String[] args) throws
  ClassNotFoundException,
  InvocationTargetException,
  InstantiationException,
  IllegalAccessException {

  BeanFactory context = Application.run(SimpleDIContainer.class);

  AController aController = context.getBean("aController", AController.class);
  AService aService = context.getBean("aService", AService.class);
  BService bService = context.getBean("bService", BService.class);
  ARepository aRepository = context.getBean("aRepository", ARepository.class);

  System.out.println("==================================");

  System.out.println("aController.getService() == aService ? " + (aController.getService() == aService));
  System.out.println("aController.getService() != bService ? " + (aController.getService() != bService));
  System.out.println("aService.getRepository() == aRepository ? " + (aService.getRepository() == aRepository));
  System.out.println("bService.getRepository() == aRepository ? " + (bService.getRepository() == aRepository));
}
```

마지막으로 실행한 결과입니다.

<img width="736" alt="image" src="https://github.com/user-attachments/assets/dd1416dd-a73c-42e2-a3b1-c7a92960e466">

