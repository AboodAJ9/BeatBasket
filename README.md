FruityBeat

## Voraussetzungen

- Java JDK 11 oder höher
- Maven (>= 3.6)
- (Optional) JavaFX SDK (wenn nicht über Maven eingebunden)

## JARs lokal ins Maven-Repository installieren

Das Projekt verwendet einige externe JAR-Dateien, die nicht aus öffentlichen Maven-Repositories verfügbar sind. 
Um das Projekt lokal starten zu können, müssen diese JARs in dein lokales Maven-Repository installiert werden.

### Schritt 1: JARs in den Ordner `lib/` kopieren

Stelle sicher, dass alle benötigten JAR-Dateien im Verzeichnis `libs/` ab (z.B. `libs/meine-bibliothek.jar`) liegen.

### Schritt 2: JARs lokal installieren

Führe im Projektordner folgende Befehle im Terminal aus (du kannst alle auf einmal kopieren und einfügen):

```bash
mvn install:install-file "-Dfile=libs/eibo_simpleminim_new.jar" "-DgroupId=de.hsrm.mi.eibo" "-DartifactId=simpleminim" "-Dversion=1.0" "-Dpackaging=jar"
mvn install:install-file "-Dfile=libs/jl1.0.1.jar" "-DgroupId=javazoom" "-DartifactId=jl" "-Dversion=1.0.1" "-Dpackaging=jar"
mvn install:install-file "-Dfile=libs/jsminim.jar" "-DgroupId=ddf.minim" "-DartifactId=jsminim" "-Dversion=2.2.2" "-Dpackaging=jar"
mvn install:install-file "-Dfile=libs/jsminim.jar" "-DgroupId=ddf.minim" "-DartifactId=jsminim" "-Dversion=2.2.2" "-Dpackaging=jar"
mvn install:install-file "-Dfile=libs/mp3agic.jar" "-DgroupId=com.mpatric" "-DartifactId=mp3agic" "-Dversion=0.9.1" "-Dpackaging=jar"
mvn install:install-file "-Dfile=libs/mp3playerUtil.jar" "-DgroupId=mp3player" "-DartifactId=mp3player-util" "-Dversion=1.0" "-Dpackaging=jar"
mvn install:install-file "-Dfile=libs/mp3spi1.9.5.jar" "-DgroupId=javazoom" "-DartifactId=mp3spi" "-Dversion=1.9.5" "-Dpackaging=jar"
mvn install:install-file "-Dfile=libs/tritonus_aos.jar" "-DgroupId=org.tritonus" "-DartifactId=tritonus-aos" "-Dversion=0.3.7" "-Dpackaging=jar"
mvn install:install-file "-Dfile=libs/tritonus_share.jar" "-DgroupId=org.tritonus" "-DartifactId=tritonus-share" "-Dversion=0.3.7" "-Dpackaging=jar"
```

### Schritt 2: Projekt starten

Nach erfolgreicher Installation der JARs kannst du das Programm mit folgendem Befehl starten:

mvn javafx:run
