# Bridge Pattern - Media Player System

## Overview
The Bridge pattern separates an abstraction from its implementation, allowing both to vary independently.

## Use Case
This example demonstrates a media player system where different types of players (Music, Podcast) can work with different audio devices (Speakers, Headphones).

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


