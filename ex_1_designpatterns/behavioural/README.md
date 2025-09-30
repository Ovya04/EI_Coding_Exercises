# Behavioural Design Patterns 

 Object interaction and responsibility distribution mechanisms that define communication protocols and workflow management between system components.

## Overview

Behavioural patterns solve complex communication and workflow challenges by defining how objects collaborate, distribute responsibilities, and manage dynamic interactions in loosely coupled, maintainable architectures.

## Implemented Patterns

### 1. Chain of Responsibility Pattern ⛓️
**Location**: `chain_of_responsibility/`  
**Problem Solved**: Request processing through a chain of potential handlers without sender-receiver coupling  
**Business Context**: Customer support ticket escalation system with multiple service levels  
**Technical Implementation**: Linked handler chain with automatic request forwarding and priority-based processing

**Use Case Demonstration**: "A customer support system where service tickets are automatically routed through support levels (Level 1 → Level 2 → Level 3) based on issue priority, with each level handling appropriate requests and escalating others without manual intervention."

**Key Technical Features**:
- Loose coupling between request sender and receiver
- Dynamic chain composition with configurable handler sequence
- Automatic request forwarding based on handler capability
- Single responsibility principle with each handler having specific expertise



### 2. Observer Pattern 👁️
**Location**: `observer/`  
**Problem Solved**: Automatic notification of dependent objects when subject state changes  
**Business Context**: Weather monitoring system with multiple display devices requiring synchronization  
**Technical Implementation**: Subject-observer registration with automatic broadcast notification

**Use Case Demonstration**: "A weather monitoring station that automatically updates all connected displays (mobile phones, web dashboards, TV displays) whenever weather conditions change, ensuring real-time data synchronization across all viewing platforms."

**Key Technical Features**:
- One-to-many dependency management with automatic notifications
- Dynamic observer registration and removal during runtime
- Broadcast communication with subject state change propagation  
- Loose coupling between subject and observer implementations

