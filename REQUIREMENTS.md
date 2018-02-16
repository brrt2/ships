# Requirements

Below we track our progress on requirements split into three categories.

## Non-functional

- [x] It must work: if one cannot play, it doesn't work.
- [ ] 60% unit test code coverage (lines).
- [ ] Code quality – non-OO code is tolerated in little amounts.
- [x] Project mantra followed (Git, Maven, test cases, etc.).
- [x] Java FX simple GUI
- [ ] Network transcript, client-server architecture <br/>
_Our comments on this: happy path - done; multiple pairs of transcript can connect in the same time - done - we need to demonstrate; chances are we'll manage to handle some of "glitches"_
- [x] Both transcript are human transcript

## Functional

- [x] One transcript only
- [x] 10x10 board
- [x] Fleet consists of: 4-mast ship, 2 3-mast ships, 3 2-mast ships and 4 1-mast ships.
- [x] Winner has ships remaining while loser has none.
- [ ] Game messages have configurable target: console (System.out, System.err) or logs or external printer.<br/>
_Our comments on this: we need to demonstrate. Socket, file and logger are done, can be configured with reporting.conf. Drawbacks: in general - happy path only is supported; in general - hard-coded English only storied are being logged; file option - if two clients run on the same machine they both log into the same file; file option - files are not cleared out (not sure if it comes handy or not); socket option - the first client who managed to connect to a port is the one who is able to write (unless another clients connects to a different port or if an external server is smart enough to handle multiple clients). Good points: we used a new technology (more interesting than properties files - files have a tree-like structure, and values are type-safe); also we created a customized logger level instead of using default ones; also we have a compiled ready-to-use "dumb" external server class for the demo :P_
- [x] We are bi-lingual: Polish and English are fine. In future we want to add more languages: messages are to be read from a file for chosen language. Choosing the language depends on configuration variable.

## Code Quality & Team Setup

- [x] Holy master - everything on master is holy, this is what is being checked by customer
- [x] CI server - before anything gets pulled into master, it is integrated with master by CI server, it runs tests, checks, etc.
- [x] Reviewers - pull-requests to master that are handled by CI server are then reviewed internally by teammates
- [x] Outside reviewers - once team says yes, outsiders come in (each team chooses external reviewers). Two external reviewers need to say "code is OK" before it can be pulled to master.
- [x] Jenkins (or equivalent) is used as CI server 
  **Client: equivalent**
- [x] Maven is used by Jenkins and team
- [x] Maven has Findbugs, JaCoCo and Checkstyle integration
- [x] Dependency convergence must be 100%, verified with maven-enforcer plugin
- [ ] All dependencies that are NOT newest are recorded (along with reason why) in the dependencies
  **Client: (log4j, lombok)**
_Our comments on this: lombok is the newest version - we can demonstrate_  
- [ ] Sonar is used to keep quality gates (just internally is fine).
- [ ] Acceptance tests are welcome, one per feature is required
- [x] Documentation should be provided, explaining program architecture (diagram is necessary, CRC diagrams are most welcome)

## Features

- [x] Drawing the boards for a player (fleet board has player's fleet and where opponent shot, "seen" board has where player fired and what he has shot).
- [x] Placing the fleet - diagonal placing is disallowed, only horizontal and vertical. Humans can place ships but they can also choose to randomize placement. Ships cannot touch (no adjacent field to a ship can have a ship). **! Dropped** Ships can be partially vertical and partially horizontal, if they have the length. 
- [x] Firing the shot - choose a place, shoot. If you hit, you repeat the shot. You can repeat as many times as you hit.
- [x] Hitting the ship - hit happens when place chosen has enemy ship. Mark this part of ship as hit, ask for another shot. One can repeat the shot into already hit (or even sunken) ship, but this doesn't give the right to another shot. 
- [x] Missing the ship - misses are marked on "seen" board. One can shoot twice in the same place if it is a miss. 
- [ ] Sinking the ship - if all masts of a ship are hit, ship sinks. Once the ship has sunk, mark all adjacent fields as "missed", since none of them can have a ship anyway. <br/>
_Our comments on this: done - we need to demonstrate_
- [x] Sinking last ship, that is, winning.
- [ ] Nuke - thrice per transcript player chooses a 3x3 area and "nukes" it, that is, all ships within take damage as if shot. This is done in addition to normal shot. Only 4-mast ship has nukes, so once they are sunk, nukes cannot be used. <br/>
_Our comments on this: we aren't going to implement it :(_