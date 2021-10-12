@echo off

set CD=%~dp0
SET mainPath=cs\vsu\goncharenko
SET binPath=%CD%bin

cd %binPath%
jar cvfe ../Fox_and_geese.jar cs.vsu.goncharenko.Main %mainPath%\entity\feature\*.class %mainPath%\entity\field\*.class %mainPath%\graph\*.class %mainPath%\gui\*.class %mainPath%\service\*.class %mainPath%\*.class