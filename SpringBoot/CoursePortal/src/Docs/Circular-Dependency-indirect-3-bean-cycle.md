Indirect 3 Bean Circular Dependency
## Goal

Create a indirect circular dependency intentionally among `StudentService`, `CourseService` and `Enrollement Service`.

## Created Cycle

`StudentService` depends on `EnrollmentService`.  
`EnrollmentService` depends on `CourseService`.  
`CourseService` depends on `StudentService`.

## Dependency Graph
```text
CourseController  
      ↓              
CourseService  <-----|
      ↓              |
StudentService       |
      ↓              |
EnrollmentService    |
      ↓              |
CourseService  ------|
```

## Expected Result & Obervation

Spring Boot application should fail during startup. 

## Spring Boot Startup Error

```text
Error starting ApplicationContext. To display the condition evaluation report re-run your application with 'debug' enabled.
2026-06-30T15:20:06.647+05:30 ERROR 1180 --- [CoursePortal] [           main] o.s.b.d.LoggingFailureAnalysisReporter   : 

***************************
APPLICATION FAILED TO START
***************************

Description:

The dependencies of some of the beans in the application context form a cycle:

   courseController defined in file [C:\Users\DS\Documents\projects\SpringBoot\CoursePortal\target\classes\projects\icore\CoursePortal\controller\CourseController.class]
┌─────┐
|  courseService defined in file [~\CoursePortal\target\classes\projects\icore\CoursePortal\services\CourseService.class]
↑     ↓
|  studentService defined in file [~\CoursePortal\target\classes\projects\icore\CoursePortal\services\StudentService.class]
↑     ↓
|  enrollmentService defined in file [~\CoursePortal\target\classes\projects\icore\CoursePortal\services\EnrollmentService.class]
└─────┘


Action:

Relying upon circular references is discouraged and they are prohibited by default. Update your application to remove the dependency cycle between beans. As a last resort, it may be possible to break the cycle automatically by setting spring.main.allow-circular-references to true.


Process finished with exit code 1
```

## Why It Happened

The services were depending on each other for small checks and query operations.

- `StudentService` used `EnrollmentService` to get enrollment count.
- `EnrollmentService` used `CourseService` to check course existence.
- `CourseService` used `StudentService` to check student existence.

Because all three services needed each other, Spring could not decide which bean should be created first.

Because both beans need each other before either one can be fully created, Spring cannot decide which bean should be initialized first.

## Important Observation

The circular dependency was not directly visible at first.

The loop appeared after CourseService entered StudentService, then StudentService entered EnrollmentService, and EnrollmentService again required CourseService. 

## Bad Fix

Adding this property can sometimes force Spring to allow circular references:

```properties
spring.main.allow-circular-references=true
```

But this is not a proper solution. It only hides the design problem.

## Correct Fix
Instead of calling each other user Query Service Principle to Avoid the Service to service chain to avoid the `Circular Dependency`

```text
StudentService
   ↓
StudentEnrollmentQueryService
   ↓
StudentRepo / EnrollementRepo

EnrollmentService
   ↓
StudentEnrollmentQueryService
   ↓
StudentRepo / CourseRepo / EnrollementRepo

CourseService
   ↓
StudentEnrollmentQueryService
   ↓
StudentRepo
```
