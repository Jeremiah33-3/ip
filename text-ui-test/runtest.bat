@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist .\text-ui-test\ACTUAL.TXT del .\text-ui-test\ACTUAL.TXT

REM delete differences from previous run
if exist .\text-ui-test\DIFFERENCES.TXT del .\text-ui-test\DIFFERENCES.TXT

REM compile the code into the bin folder
javac  -cp .\src\main\java -Xlint:none -d ..\bin .\src\main\java\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin HeyJudy < .\text-ui-test\input.txt > .\text-ui-test\ACTUAL.TXT

REM Convert ACTUAL.TXT and EXPECTED.TXT to Unix format (LF line endings)
powershell -Command "(gc .\text-ui-test\ACTUAL.TXT) -replace '`r', '' -replace '\s+$', '' | sc .\text-ui-test\ACTUAL.TXT"
powershell -Command "(gc .\text-ui-test\EXPECTED.TXT) -replace '`r', '' -replace '\s+$', '' | sc .\text-ui-test\EXPECTED_TEST.TXT"

REM compare the output to the expected output
FC .\text-ui-test\ACTUAL.TXT .\text-ui-test\EXPECTED_TEST.TXT > .\text-ui-test\DIFFERENCES.TXT
IF ERRORLEVEL 1 (
    echo Test FAILED. See differences below:
    type .\text-ui-test\DIFFERENCES.TXT
    exit /b 1
)

echo All tests PASSED!
exit /b 0