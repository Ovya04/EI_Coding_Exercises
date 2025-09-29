# Virtual Classroom Manager v2.0

## 📚 Overview

The Virtual Classroom Manager is a comprehensive, terminal-based application designed to streamline the management of virtual classrooms in educational institutions. Built with enterprise-grade standards, it implements advanced design patterns, SOLID principles, and gold-standard practices for logging, exception handling, and validation.
<img width="972" height="381" alt="image" src="https://github.com/user-attachments/assets/da8d74b5-b2e9-4b84-b01f-5b6ae566bf78" />


## ✨ Features

### 🏫 Classroom Management
- **Add Classroom**: Create new virtual classrooms with validation and capacity limits
- **Remove Classroom**: Safe removal with cascade operations for enrolled students
- **List Classrooms**: Pagination support and filtering capabilities
- **Classroom Details**: Comprehensive view with statistics and analytics

### 👥 Student Management
- **Add Student**: Enhanced enrollment with profile validation and email notifications
- **Remove Student**: Graceful unenrollment with assignment transfer options
- **List Students**: Advanced filtering, sorting, and search capabilities
- **Student Profile**: Detailed view with progress tracking and performance metrics

### 📝 Assignment Management
- **Schedule Assignment**: Rich assignment creation with grading rubrics
- **Submit Assignment**: Status tracking feature
- **List Assignments**: With filtering by status
- **Grade Assignment**: Comprehensive grading system with feedback and letter grades

### 📋 Attendance Management
- **Mark Attendance**: Daily attendance tracking
- **View Attendance**: Detailed attendance records and statistics
- **Attendance Reports**: Comprehensive analytics and trend analysis
- **Automated Notifications**: Absence alerts and notifications

### 🔔 Notification System
- **Assignment Reminders**: Due date notifications for upcoming assignments
- **Grade Notifications**: Automated grade publication alerts
- **Absence Alerts**: Automated notifications for student absences
- **System Announcements**: Important updates and notifications

### 📊 Analytics & Reporting
- **Classroom Analytics**: Performance metrics and engagement statistics
- **Student Progress**: Individual progress tracking and performance dashboards
- **Submission Analytics**: Assignment submission rates and trends
- **Attendance Reports**: Comprehensive attendance analysis

## 🏗️ Architecture & Design

### Design Patterns Implemented
- **Singleton Pattern**: Logger and Configuration management
- **Builder Pattern**: Complex object creation (Student, Assignment)
- **Command Pattern**: CLI command processing
- **Observer Pattern**: Notification system
- **Strategy Pattern**: Different validation and sorting strategies
- **Factory Pattern**: Entity creation and management

### SOLID Principles
- **Single Responsibility**: Each class has one specific purpose
- **Open/Closed**: Extensible architecture for new features
- **Liskov Substitution**: Proper inheritance hierarchies
- **Interface Segregation**: Focused interfaces for different roles
- **Dependency Inversion**: Dependency injection implementation

### Gold Standard Practices
- **Comprehensive Logging**: Multi-level logging with timestamps and performance monitoring
- **Exception Handling**: Custom exception hierarchy with graceful error recovery
- **Input Validation**: Multi-layer validation with sanitization and security checks
- **Defensive Programming**: Extensive null checks and boundary validations
- **Performance Optimization**: Efficient algorithms and memory management

## 💻 Usage Guide
### Basic Commands
<img width="1109" height="761" alt="image" src="https://github.com/user-attachments/assets/fc961f7f-98ab-40ec-940d-a5a6ad573e3a" />
<img width="902" height="430" alt="image" src="https://github.com/user-attachments/assets/bbbaaf60-a298-40d8-a4f5-3db81347d2c3" />





## 📊 Sample Data & Testing




### Validation Rules

#### Student ID Format
- Must follow pattern: `[A-Z]{2}[0-9]{4,6}`
- Examples: `ST1234`, `AB123456`

#### Email Validation
- Standard email format validation
- Must contain `@` and valid domain

#### Classroom Names
- 2-30 characters
- Letters, numbers, spaces, hyphens, and underscores allowed



## 🔒 Error Handling & Recovery

### Validation Errors
The application provides detailed validation error messages with suggestions for correction:
```
❌ Validation Error: Invalid student ID format: st123
   Suggestion: Use format like ST1234 or AB123456
```

### Data Integrity
- Cascade operations for safe data removal
- Confirmation prompts for destructive operations
- Transaction-like operations with rollback capability

### Exception Recovery
- Graceful error recovery without application crash
- Detailed error logging for debugging
- User-friendly error messages with resolution steps

## 📈 Performance Considerations

### Optimization Features
- Efficient data structures (HashMap for O(1) lookups)
- Pagination for large datasets
- Lazy loading of analytics data
- Memory-efficient string operations

### Scalability Notes
- Current implementation uses in-memory storage
- Designed for easy migration to database storage
- Modular architecture supports horizontal scaling

## 🧪 Testing & Quality Assurance

### Manual Testing Scenarios

1. **Classroom Lifecycle**
   - Create, populate, and remove classrooms
   - Test capacity limits and validation

2. **Student Management**
   - Enrollment and unenrollment workflows
   - Cross-classroom enrollment scenarios

3. **Assignment Workflows**
   - Complete assignment lifecycle from creation to grading
   - Test due date validations and overdue scenarios

4. **Attendance Tracking**
   - Daily attendance marking
   - Analytics and reporting accuracy

5. **Error Scenarios**
   - Invalid input handling
   - Edge cases and boundary conditions

## 📝 Development Notes

### Code Quality Standards
- Comprehensive JavaDoc documentation
- Consistent naming conventions
- Proper exception handling hierarchy
- Clean code principles implementation

### Extension Points
- Plugin architecture for custom commands
- Strategy pattern for different grading schemes
- Observer pattern for event notifications
- Factory pattern for entity creation

## 🤝 Contributing

### Development Guidelines
1. Follow existing code style and patterns
2. Add comprehensive JavaDoc documentation
3. Include proper error handling
4. Update README for new features
5. Test all functionality thoroughly

### Git Workflow
```bash
# Create feature branch
git checkout -b feature/new-functionality

# Make changes and commit
git add .
git commit -m "Add new functionality: description"

# Push to repository
git push origin feature/new-functionality
```

## 📄 License

This project is developed as an educational exercise demonstrating enterprise Java development practices, design patterns, and software engineering principles.

## 🆘 Support & Troubleshooting

### Common Issues

1. **Compilation Errors**
   - Ensure Java 17+ is installed
   - Verify classpath is set correctly
   - Check package structure matches directory structure

2. **Runtime Exceptions**
   - Enable debug logging: `debug on`
   - Check input format against validation rules
   - Verify all required parameters are provided

3. **Performance Issues**
   - Use pagination for large datasets
   - Monitor memory usage with large student counts
   - Consider data cleanup for long-running sessions

### Debug Commands
```bash
# Enable detailed logging
debug on

# View current system state
list_classrooms
list_students <classroom>

# Check validation rules
help
```

## 📞 Contact Information

For questions, issues, or contributions related to this Virtual Classroom Manager implementation, please refer to the project repository or contact the development team.

---

**Virtual Classroom Manager v2.0** - Demonstrating Enterprise Java Development Excellence

*Built with ❤️ using Java, Design Patterns, and SOLID Principles* 
