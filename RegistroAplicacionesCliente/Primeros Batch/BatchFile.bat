@echo off
title Analisis del Sistema 
echo Ha comenzado el analisis en este equipo.
echo Procesando ...
wmic product get name,version,installdate,installlocation | find /v "" > files.txt
echo El programa ha finalizado.
exit