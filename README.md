# Pokemon Game Instructions

## Technologies used
- JDK8
- Tomcat 8 *(apache-tomcat-8.0.53)*
- Eclipse


## How to run the project

> Right-click on project -> Run As -> Run on Server


## How to clean up the database

> Right-click on `DatabaseSetup` *(/PokemonGame/src/setup/DatabaseSetup)* -> Run As -> Java Application


## How to run the Tests

1. Import TestPoke project
1. Open `TestSuite` *(/TestPoke/src/TestSuite)*
1. Change the `URL_Base` to `"http://localhost:8080/PokemonGame/"`

> Right-click on `TestSuite` *(/TestPoke/src/TestSuite)* -> Run As -> JUnit Test


### Assignment 1 JUnit Results
- Runs: `40/40`
- Errors: `0`
- Failures: `3`

*Note: 3 tests will always fail: `testPlayPokemonToBenchFailurePlayingACardThatIsNotAPokemon`, `testPlayPokemonToBenchSuccess`, `testPlayPokemonToBenchFailurePlayingACardNotInHand`*



---
**_Author: Andres Vazquez (#40007182)_**  
*SOEN 387*
