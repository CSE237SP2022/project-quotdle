#!/bin/bash
javac src/quotdle/*.java src/util/*.java
java -cp src quotdle.Game
#java Game
