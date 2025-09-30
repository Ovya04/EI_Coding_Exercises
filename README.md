#  Exercises

## Excercise 1:

This project demonstrates a comprehensive understanding of fundamental design patterns through six carefully selected implementations spanning all three core categories.
Design patterns are reusable solutions to commonly occurring problems in software design, providing a structured approach to building robust, maintainable, and flexible applications


The project uses six core design patterns. Here is a concise explanation of each:

-  Builder Pattern  
Separates the construction of a complex object from its representation, enabling step-by-step fluent creation of immutable objects without resorting to telescoping constructors.  

-  Factory Pattern  
Defines an interface for creating objects but lets subclasses decide which class to instantiate, encapsulating object creation logic and promoting loose coupling.  

-  Bridge Pattern  
Decouples an abstraction from its implementation so that the two can vary independently, enabling clients to combine any abstraction with any implementation at runtime.  

-  Decorator Pattern  
Dynamically attaches additional responsibilities to an object by wrapping it in decorator classes, allowing behavior to be added without modifying the original class.  

-  Chain of Responsibility Pattern  
Passes a request along a chain of handlers, where each handler can either process the request or forward it to the next, enabling dynamic selection of the appropriate handler and decoupling sender from receiver.  

-  Observer Pattern  
Defines a one-to-many dependency between objects so that when one object’s state changes, all registered observers are automatically notified and updated, supporting event-driven, publish-subscribe architectures.

Each pattern is implemented with professional-grade error handling, comprehensive input validation, and interactive command-line interfaces that provide hands-on learning experiences while demonstrating enterprise-level architectural thinking.


## Excercise 2: 

### Mini Project 8: Virtual Classroom Manager
The Virtual Classroom Manager addresses a very real challenge: how to organize and oversee teaching activities without a complex learning management system (LMS). In basic terms, it lets an instructor spin up “classrooms,” enroll students, and track assignments—all from the command line.

With this tool, you can:

Create and list classrooms to organize different classes.

Register students and view their enrollment in each classroom.

Schedule and submit assignments, track their status, and record grades.

Handle all operations without a database—data is managed in memory for easy testing and extension.

Designed with clear separation of concerns, this project uses domain entities (Student, Assignment, Classroom), a central controller for command processing, and utility classes for logging and input validation. It provides a foundation you can extend with features like persistence, notifications, or a graphical interface.
