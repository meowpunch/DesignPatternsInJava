# Design Patterns in Java
- Hands on exercises following belows 
  - [udemy class](https://www.udemy.com/course/design-patterns-java/)
  - [design patterns and principles paper](https://fi.ort.edu.uy/innovaportal/file/2032/1/design_principles.pdf)
  - [design patterns book](https://en.wikipedia.org/wiki/Design_Patterns)

## SOLID

### [Single Responsibility Principle](https://en.wikipedia.org/wiki/Single-responsibility_principle) 
`There should never be more than one reason for a class to change.` i.e. every class should have only one responsibility.

### [Open Closed Principle (OCP)](https://en.wikipedia.org/wiki/Open–closed_principle)
`open for extension, but closed for modification.`
- OCP + [Specification Pattern](https://en.wikipedia.org/wiki/Specification_pattern)
  
### [Liskov Substitution Principle](https://en.wikipedia.org/wiki/Liskov_substitution_principle)
`if S is a subtype of T, then objects of type T in a program may be replaced with objects of type S without altering any of the desirable properties of that program.` i.e. if S is a subtype of T, then objects of type T in a program may be replaced with objects of type S without altering any of the desirable properties of that program.
- Circle / Ellipse Dilemma (can be Square / Rectangle Problem)

### [Interface Segregation Principle](https://en.wikipedia.org/wiki/Interface_segregation_principle)
`Many client-specific interfaces are better than one general-purpose interface.`

### [Dependency Inversion Principle](https://en.wikipedia.org/wiki/Dependency_inversion_principle)
`Depend upon abstractions, [not] concretions.`
- High-level modules should not import anything from low-level modules. Both should depend on abstractions (e.g., interfaces).
- Abstractions should not depend on details. Details (concrete implementations) should depend on abstractions

## Creational Patterns
### [Builder Pattern](https://en.wikipedia.org/wiki/Builder_pattern)
- StringBuilder and HTMLBuilder

## Structural Patterns

## behavioral Patterns

## Concurrency Patterns