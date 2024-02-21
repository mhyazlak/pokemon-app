@echo off


REM Run the build command for Vue.js
echo Running npm build for Vue.js...
call npm run build
IF %ERRORLEVEL% NEQ 0 (
   echo Building Vue.js project failed. Check the error messages above.
   exit /b %ERRORLEVEL%
)

REM Move the dist folder to the static directory of the Spring Boot application
echo Moving the dist folder to the Spring Boot static directory...
xcopy /E /I /Y dist ..\backend\src\main\resources\static\vue-dist\
IF %ERRORLEVEL% NEQ 0 (
   echo Moving the dist folder failed. Check the error messages above.
   exit /b %ERRORLEVEL%
)

echo Build and move completed successfully.
pause
