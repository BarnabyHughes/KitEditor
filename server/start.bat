@echo off

set "jar_url=https://mineacademy.org/api/paper/latest"
set "downloaded_file=paperclip.jar"

curl -o "%downloaded_file%" "%jar_url%"
java -Xms4G -Xmx4G -jar "%downloaded_file%" nogui

pause