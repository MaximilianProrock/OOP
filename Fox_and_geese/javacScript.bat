@echo off

set CD=%~dp0
SET javafxHome=%CD%lib\javafx-sdk-17.0.0.1\lib
SET mainPath=%CD%src\cs\vsu\goncharenko
set logPath=%CD%lib\log

javac --module-path "%javafxHome%" --upgrade-module-path "%logPath%" --add-modules ALL-MODULE-PATH -d bin %mainPath%\*.java -d bin %mainPath%\entity\feature\*.java -d bin %mainPath%\entity\field\*.java -d bin %mainPath%\graph\*.java -d bin %mainPath%\gui\*.java -d bin %mainPath%\service\*.java

