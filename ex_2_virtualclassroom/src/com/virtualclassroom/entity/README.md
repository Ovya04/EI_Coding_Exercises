# Domain Entities

This folder contains core Java classes that model the data objects of the virtual classroom system.

**Files:**
- `Student.java`: Represents a student enrolled in classrooms. Uses the Builder pattern for flexible instantiation.
- `Assignment.java`: Models assignments with features like state management and grading.
- `Classroom.java`: Aggregates students and assignments, representing a classroom and its contained data.

**Why?**
- Each class here defines real-world concepts as simple, reusable Java objects.
- Used throughout business logic for storing and processing classroom data.
