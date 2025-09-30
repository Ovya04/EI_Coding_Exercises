# Chain of Responsibility Pattern - Support Ticket System

## Overview
The Chain of Responsibility pattern passes requests along a chain of handlers until one handles the request.

## Use Case
This example demonstrates a support ticket system where tickets are escalated through different support levels based on priority.

## Key Components
- **SupportHandler**: Abstract handler class
- **SupportTicket**: Request object with priority levels
- **Level1Support, Level2Support, Level3Support**: Concrete handlers
- **Main**: Demonstrates the chain processing

## Implementation and Output
  <img width="1471" height="755" alt="image" src="https://github.com/user-attachments/assets/0f7418dd-5e7a-4102-847e-1432f8b160b1" />

  <img width="1355" height="511" alt="image" src="https://github.com/user-attachments/assets/0381cda1-a822-4dce-9b57-a08d283137c2" />

  <img width="1486" height="382" alt="image" src="https://github.com/user-attachments/assets/b51960d4-91ff-4a67-b9c2-8678cec2b801" />


## Benefits
- Decouples sender and receiver
- Allows runtime chain modification
- Follows Single Responsibility Principle
- Enables flexible request handling

