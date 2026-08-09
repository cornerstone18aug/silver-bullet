# Silver Bullet
## About
- JavaFX 2 Player Game
- For Java Final Project in CICCC

## Screen shot
### - Top
![Top](/screenshots/Top.png)

### - Main
![Main](/screenshots/Main.png)

## Framework / Language

- JDK 21 (compiles to `--release 12` bytecode)
- JavaFX (OpenJFX 21)
- Maven

## Download & Play
1. Download Executable.zip from below (**Java doesn't need to be installed in your laptop👍**)
   - [For Mac/Linux](https://storage.cloud.google.com/silver-bullet/Executable.zip?hl=en)
   - [For Windows](https://storage.cloud.google.com/silver-bullet/Executable_win.zip?hl=en)

2. Unzip
3. Double click or Run ./bin/silverBullet(Mac, Linux) or ./bin/silverBullet.bat(Windows) from your terminal

## Build & CI

[![Tests](https://github.com/cornerstone18aug/silver-bullet/actions/workflows/tests.yml/badge.svg)](https://github.com/cornerstone18aug/silver-bullet/actions/workflows/tests.yml)
[![Build](https://github.com/cornerstone18aug/silver-bullet/actions/workflows/build.yml/badge.svg)](https://github.com/cornerstone18aug/silver-bullet/actions/workflows/build.yml)
[![codecov](https://codecov.io/gh/cornerstone18aug/silver-bullet/branch/master/graph/badge.svg)](https://codecov.io/gh/cornerstone18aug/silver-bullet)

### Build locally

Requires JDK 17+ (built and tested on JDK 21).

```bash
mvn clean compile                       # compile
mvn test                                # run tests + JaCoCo coverage report
mvn clean compile javafx:jlink          # build the self-contained executable image
```

`mvn test` writes a coverage report to `target/site/jacoco/index.html`. The
`jlink` goal writes a self-contained runtime image to `target/jlinkImage`;
launch it with `./bin/silverBullet` (macOS/Linux) or `bin\silverBullet.bat`
(Windows) — no Java installation required.

### Testing

A JUnit 5 suite covers the core gameplay — turning, movement, shooting, ammo
pickups, the action queue, player removal, and a full plan→execute turn — both
in isolation and end-to-end on a real board.

Coverage is tracked by the Codecov badge above. Run `mvn test` and open
`target/site/jacoco/index.html` for the detailed local report.

### Continuous integration

GitHub Actions runs on every push and pull request:

- **Tests** (`.github/workflows/tests.yml`) — builds and runs the test suite on
  pushes to `master` / `feature/**` and on PRs into `master`. This is a required
  status check for merging, and it uploads the JaCoCo coverage report as an
  artifact.
- **Build** (`.github/workflows/build.yml`) — on pushes to `master`, produces
  native `jlink` executable images for **Linux, Windows and macOS** as
  downloadable artifacts.

Dependency and GitHub Actions updates are automated weekly via Dependabot
(`.github/dependabot.yml`).

## Team
- Juan ([@Floxnu](https://github.com/Floxnu))
- Hao-tse ([@Maxroo](https://github.com/Maxroo))
- Enrique ([@Enrique92](https://github.com/Enrique92))
- Masa ([@nator333](https://github.com/nator333))
