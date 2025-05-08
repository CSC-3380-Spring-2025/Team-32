# Gobbledygook : Team 32
# Members
Project Manager: Joseph Strauss (cuppajoe123)\
Communications Lead: William Morales (WilliamMorales1)\
Git Master: Calvin Gavin (CrawfishChowder)\
Design Lead: Kade Whitman (kmwhitman)\
Quality Assurance Tester: Julian Madrigal (jmadripz)

# About Our Software

Our project is a competitive and fun game between 4 players where they must try and win each round, all centering around one weird word. We play around with the definitions, example sentences and even the spelling of the word. And inbetween some of the rounds, there will be minigames and power-ups to keep the players on their toes. 
## Platforms Tested on
- MacOS
- Linux
- Windows
# Important Links
Kanban Board: [Kanban Link](https://github.com/orgs/CSC-3380-Spring-2025/projects/4)\
Designs: [Design Folder Link](https://github.com/CSC-3380-Spring-2025/Team-32/tree/funny-story-input-checks/designs)\
Styles Guide: [Style Guide Link](https://github.com/CSC-3380-Spring-2025/Team-32/blob/funny-story-input-checks/Code%20Standards%20%26%20Style%20Guide.pdf)

# How to Run Dev and Test Environment

## Dependencies
- Svelte, version 5.28
- TypeScript, version 5.8.2
- Java, version 21.0.1
- Spring Boot, version 3.4.3
- SQLite, version ???
- Maven, version 3.9.9
- npm, verion 10.9.0
- Vite, version 6.2.2
- Node.js, version 22.12.0
### Downloading Dependencies

### Frontend
- [Svelte](https://svelte.dev)
- [TypeScript](https://www.typescriptlang.org/download/)

### Backend
- [Java](https://www.java.com/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Node.js](https://nodejs.org/en/download)

### Database
- [SQLite](https://www.sqlite.org/)

### Build Tools
- [Maven](https://maven.apache.org/)
- [npm](https://www.npmjs.com/)

### IDEs
- [VSCode](https://code.visualstudio.com/download) (not strictly needed, as you can also run the commands straight from the command line)

## Commands

Clone the latest upadated version of the files under the main branch. 
Open the terminal and enter in the following commands.
We were able to run the code both on VSCode and using the command line. 

To install dependencies on Debian:

``` sh
sudo apt install openjdk-17-jre openjdk-17-jdk nodejs npm
```

Open two terminal windows, for running the backend and frontend respectively.

In the first, navigate to the root of the project and run
```sh
./mvnw spring-boot:run
```
In the second, navigate to src/main/svelte and run the following commands:
```sh
npm install #if not already installed
npm run dev
```

This should give you a link to http://localhost:5173/join where you can begin playing!

## Testing

This game is intended to be played by 4 players, locally. To test the game with just one person, simply open 4 incognito tabs with http://localhost:5173/join and enter in inputs for each player. 
