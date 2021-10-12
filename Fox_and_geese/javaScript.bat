@echo off

set CD=%~dp0
SET javafxHome=%CD%lib\javafx-sdk-17.0.0.1\lib
SET mainPath=%CD%src\cs\vsu\goncharenko
set logPath=%CD%lib\log


java --module-path "%javafxHome%" --upgrade-module-path "%logPath%" --add-modules ALL-MODULE-PATH -jar %CD%Fox_and_geesse.jar