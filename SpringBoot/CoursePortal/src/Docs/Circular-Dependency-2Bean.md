# Circular Dependency Lab 01 - Basic 2 Bean Cycle

## Goal

Create a direct circular dependency intentionally between StudentService and CourseService.

## Created Cycle

`StudentService` depends on `CourseService`.  
`CourseService` depends on `StudentService`.

## Dependency Graph
```text
CourseController  <--|
      ↓              |
CourseService        |
      ↓              |
StudentService       |
      ↓              |
CourseService  ------|
```

## Expected Result

Spring Boot application should fail during startup.

## Spring Boot Startup Error

```text
Error starting ApplicationContext. To display the condition evaluation report re-run your application with 'debug' enabled.
2026-06-30T12:54:30.110+05:30 ERROR 2732 --- [CoursePortal] [           main] o.s.b.d.LoggingFailureAnalysisReporter   : 

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
└─────┘


Action:

Relying upon circular references is discouraged and they are prohibited by default. Update your application to remove the dependency cycle between beans. As a last resort, it may be possible to break the cycle automatically by setting spring.main.allow-circular-references to true.


```

## Why It Happened

CourseService` required `StudentService` during bean creation.

At the same time, `StudentService` required `CourseService` during bean creation.

Because both beans need each other before either one can be fully created, Spring cannot decide which bean should be initialized first.

## Important Observation

The error starts from `CourseController` because `CourseController` depends on `CourseService`.

But the actual cycle is not in the controller its in service.

## Bad Fix

Adding this property can sometimes force Spring to allow circular references:

```properties
spring.main.allow-circular-references=true
```

But this is not a proper solution. It only hides the design problem.

## Correct Fix

Do not make `StudentService` and `CourseService` call each other directly.

If both services need shared data, Move that combined logic to a separate service like PortalStatsService or EnrollmentService..
