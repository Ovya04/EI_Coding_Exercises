# Observer Pattern - Weather Station

## Overview
The Observer pattern defines a one-to-many dependency between objects so that when one object changes state, all dependents are notified automatically.

## Use Case
This example demonstrates a weather station that notifies multiple display devices when weather data changes.

## Key Components
- **Subject**: Interface for objects that notify observers
- **Observer**: Interface for objects that get notified
- **WeatherStation**: Concrete subject that holds weather data
- **PhoneDisplay, WebDisplay, TVDisplay**: Concrete observers

## Implementation and Output

<img width="1507" height="686" alt="image" src="https://github.com/user-attachments/assets/0defefa6-1f70-43ff-b5fd-4067d224d029" />



<img width="1259" height="500" alt="image" src="https://github.com/user-attachments/assets/557f1616-0822-4e49-9bfa-1f61641c9ea8" />




<img width="1446" height="464" alt="image" src="https://github.com/user-attachments/assets/eac5f6fe-d408-4d16-85e1-dec0abe1799e" />

<img width="1492" height="455" alt="image" src="https://github.com/user-attachments/assets/89f7069c-230e-4445-ad22-1bdfc1376c48" />




<img width="1457" height="784" alt="image" src="https://github.com/user-attachments/assets/a249c156-8623-4a2b-957b-d694659d70e3" />




<img width="1504" height="471" alt="image" src="https://github.com/user-attachments/assets/267b15e2-c0fd-4bef-a7d4-48c28e2653d6" />




<img width="1483" height="202" alt="image" src="https://github.com/user-attachments/assets/92b91177-44fa-449f-a20b-e0a72cf33eb1" />







## Benefits
- Defines loose coupling between subject and observers
- Supports broadcast communication
- Dynamic registration/removal of observers
- Follows Open/Closed Principle

