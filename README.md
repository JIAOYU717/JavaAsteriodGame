# Asteroids Game Project - JavaFX

## Project Overview

This project is a JavaFX implementation of the classic arcade game "Asteroids," where players navigate a spaceship through space, destroying asteroids and occasional alien ships. The game's difficulty increases with each level, introducing faster-moving and smaller asteroids that split into two when destroyed. Our version adheres closely to the original game's mechanics, with enhancements for modern gameplay experience.

## Features

- **Three Types of Asteroids**: Includes large, medium, and small asteroids, each with unique behaviors and speeds.
- **Alien Ships**: Occasional alien ships appear, moving across the screen and firing at the player.
- **Player Actions**: The ship can rotate, fire bullets, apply thrust for movement, and execute hyperspace jumps to avoid collisions.
- **Momentum and Movement**: Implements realistic physics for momentum, where the ship continues moving in the direction it's pointed until thrust is applied in the opposite direction.
- **Screen Wrapping**: Objects that move off one edge of the screen reappear on the opposite side, except for bullets, which disappear after a set distance.
- **Increasing Difficulty**: The game progresses through levels with an increasing number of asteroids.
- **On-Screen Information**: Displays the player's current number of lives and score.

## Gameplay

The player starts with a single slow-moving asteroid on the first level, with each subsequent level introducing additional asteroids. The aim is to destroy all asteroids without being hit by them or the shots from alien ships. Destroying asteroids or alien ships increases the player's score.

## Implementation Details

- **Group Size**: 5 students
- **Language**: Java (JavaFX for the graphical user interface)
