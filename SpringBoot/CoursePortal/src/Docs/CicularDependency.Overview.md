# Circular Dependency

## Level 1: Direct Circular Dependency

The simplest case.

```text
StudentService -> CourseService
CourseService -> StudentService
```

This usually happens when each service tries to do part of the other's job.

Example:

- StudentService.register() needs CourseService.validateCourse()
- While CourseService.deleteCourse() needs StudentService.removeStudents()

Spring cannot construct either bean because each needs the other first.

---

## Level 2: Indirect Circular Dependency

Much more common.

```text
StudentService -> CourseService -> RegistrationService -> StudentService
```

Nobody directly injects themselves.

Instead.,

```text
A -> B
B -> C
C -> A
```

This is harder to detect because the services may belong to different modules.

---

## Level 3: Large Business Cycle

Imagine an ERP.

```text
StudentService
    ↓
RegistrationService
    ↓
PaymentService
    ↓
NotificationService
    ↓
StudentService
```

This is how circular dependencies usually appear in enterprise systems.

---

# Why Circular Dependency Happens

Almost always because responsibilities overlap.

For example,

- StudentService register() needs CourseService
- CourseService enrollStudent() calls StudentService.updateCredits()

Now both services own each other's business logic.

That violates the Single Responsibility Principle.

---

# Solution 1 — Bridge Service (Best Beginner Solution)

Instead of

```text
StudentService ↔ CourseService
```

Create RegistrationService, Now StudentService calls CourseService via RegistrationService

Neither StudentService nor CourseService know about each other.

This is one of the cleanest solutions.

---

# Solution 2 — Dependency Inversion Principle (DIP)

Suppose,

StudentService calls CourseService

but CourseService only needs checkStudentEligibility()

Instead of depending on StudentService, depend on an interface.now StudentService implements StudentEligibility

Now CourseService calls StudentEligibility Instead of direct StudentService

This reduces coupling.

---

# Solution 3 — Extract Common Logic

Very common.

Instead of

```text
StudentService → CourseService → StudentService
```

Extract the EnrollmentValidator, now

```text
StudentService → EnrollmentValidator ← CourseService
```

Now both services share the validator instead of each other.

---

# Solution 4 — Domain Events (Enterprise Favorite)

Instead of directly calling another service,

publish an event.

```text
Student Registered

StudentService
      ↓
publish event
      ↓
RegistrationListener
      ↓
CourseService
```

Now StudentService doesn't know CourseService

Spring provides this with ApplicationEventPublisher and @EventListener.

This pattern is widely used in larger applications.

---

# Solution 5 — @Lazy

Instead of constructing immediately,

Spring injects a proxy.

```text
StudentService → Lazy Proxy → CourseService
```

The real bean is created only when it's first used.

This fixes the startup problem but does not fix the underlying design.

Use it when the dependency is legitimate but initialization order is the issue.

---

# Solution 6 — Setter Injection

Instead of Constructor Spring creates StudentService, CourseService first,

then injects the dependencies afterward through setters.

This can break some cycles, but constructor injection is generally preferred because it makes dependencies explicit and allows immutable fields.

---

# Solution 7 — Field Injection

Same idea as setter injection.

Spring creates the objects first.

Later, @Autowired injects the fields.

It can work around some circular references (if circular references are allowed), but field injection is generally discouraged because it hides dependencies and makes testing harder.

---

# Solution 8 — Provider / Deferred Lookup

Instead of injecting the object, inject a provider.

ObjectProvider<CourseService> or Provider<CourseService>

The service is retrieved only when needed.

```text
StudentService → ObjectProvider → CourseService
```

This is cleaner than @Lazy when the dependency is only occasionally needed.

---

# Solution 9 — Facade Pattern

Instead of

```text
StudentService → CourseService → PaymentService → NotificationService
```

everything goes through EnrollmentFacade

```text
Controller
     ↓
EnrollmentFacade
     ↓
StudentService
     ↓
CourseService
     ↓
PaymentService
```

Individual services no longer depend on each other.

---

# Solution 10 — Split Read and Write Responsibilities

A common source of cycles is a service handling both queries and commands.

Instead of:

```text
StudentService
```

split it into:

```text
StudentQueryService
StudentCommandService
```

Now CourseService may only need the query service, avoiding a cycle.

---

# When Each Solution Fits

| Situation | Recommended Solution |
|-----------|----------------------|
| Two services each call the other | Bridge/Orchestrator Service |
| Shared business rules | Extract a common service |
| Only one method is needed | Dependency Inversion (interface) |
| Notify another module after work | Domain Events |
| Initialization order problem | @Lazy or ObjectProvider |
| Legacy code | Setter injection (short-term) |
| Large workflow | Facade/Orchestrator |
| Mixed read/write responsibilities | Split services (CQRS-style) |