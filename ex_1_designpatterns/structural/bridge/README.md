# Bridge Pattern - Media Player System

## Overview
The Bridge pattern separates an abstraction from its implementation, allowing both to vary independently.

## Use Case
This example demonstrates a media player system where different types of players (Music, Podcast) can work with different audio devices (Speakers, Headphones).

## Implementation and Output

<img width="1497" height="605" alt="image" src="https://github.com/user-attachments/assets/203e747f-050f-48a9-9358-e93d16e7e2b0" />

<img width="1495" height="737" alt="image" src="https://github.com/user-attachments/assets/f03aee41-8df6-41dc-9d13-9e7890315481" />

<img width="1450" height="130" alt="image" src="https://github.com/user-attachments/assets/2be543b1-7d56-4b25-9ba8-12b312a3f925" />

<img width="1454" height="509" alt="image" src="https://github.com/user-attachments/assets/16131d1e-cf61-446f-90b7-d2d42c21b855" />


<img width="1507" height="803" alt="image" src="https://github.com/user-attachments/assets/9b25ba1b-ab10-4cac-8343-0d4ce0632df1" />

<img width="1481" height="134" alt="image" src="https://github.com/user-attachments/assets/247ec0ec-6cc6-4273-bfe7-c8be35ee402d" />





## Key Components
- **MediaPlayer**: Abstraction for media players
- **AudioDevice**: Implementation interface for audio devices
- **MusicPlayer, PodcastPlayer**: Concrete abstractions
- **Speakers, Headphones**: Concrete implementations

## Benefits
- Decouples abstraction from implementation
- Both can be extended independently
- Runtime binding of implementation
- Hides implementation details from clients


