@echo off
rem /**
rem  * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
rem  *
rem  * Author: ThinkGem
rem  */
echo [INFO] 设置Maven版本库路径
echo.

cd %~dp0

set MAVEN_HOME=%cd%\maven3
set path=%MAVEN_HOME%\bin;%windir%\system32;%path%

echo [INFO] 正在设置...
echo.

setlocal enabledelayedexpansion
set txt=%cd%\repository

echo [INFO] 版本库路径设置为 %txt%
echo.

set txt=!txt:\=/!
call :replace %MAVEN_HOME%\conf\settings.xml.default @REPO_DIR@ %txt% >%MAVEN_HOME%\conf\settings.xml

echo [INFO] 《《《设置完成，请务必按照以下说明进行操作》》》
echo.

echo [INFO] 1）配置系统环境变量, PATH前面添加：
echo [INFO]       %MAVEN_HOME%\bin;
echo.
echo [INFO] 2）配置 Eclipse 如下
echo.
echo [INFO]    a）有 Maven 插件：Window --^> Preferences --^> Maven 
echo [INFO]       --^> User Settings --^> 点击 Browse... 按钮，选择你的 
echo [INFO]       %MAVEN_HOME%\conf\settings.xml 文件。
echo.
echo [INFO]    b）没有 Maven 插件：Window --^> Preferences --^> Java 
echo [INFO]       --^> Bulid Path --^> Classpath Variables --^> 点击 new... 按钮
echo [INFO]       Name：M2_REPO
echo [INFO]       Path：%cd%\repository
echo [INFO]       点击 OK 按钮
echo.

pause

goto :eof
:replace
for /f "tokens=1* delims=:" %%i in ('findstr /n ".*" %1') do (
	set txt=%%j
	if "!txt!" == "" (
		echo.
	) else (
		echo !txt:%2=%3!
	)
)
goto :eof