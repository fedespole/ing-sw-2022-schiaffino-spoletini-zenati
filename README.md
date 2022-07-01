# Eryantis - Prova Finale di Ingegneria del Software (2021-2022)

Funzionalità Implementate
-----
* Regole Complete
* Socket
* CLI
* GUI
* (FA1) Carte Personaggio (implementazione di 12 carte)
* (FA2) Resilienza alle disconnessioni (con timeout di 45 secondi)

Copertura dei Test
-----
| Package    | Class      | Coverage (method%) |
|:-----------|:-----------|:------------------:|
| model      | basicgame  |    93/96 (96%)     |
| model      | expertgame |    73/77 (94%)     |
| controller | Controller |    29/35 (82%)     |

Setup
-----
Se il sistema operativo è Windows, eseguire il seguente comando nel Prompt per colorare la CLI:

`REG ADD HKCU\CONSOLE /f /v VirtualTerminalLevel /t REG_DWORD /d 1`

Successivamente copiare il progetto:
```bash
git clone https://github.com/fedespole/ing-sw-2022-schiaffino-spoletini-zenati.git
cd ing-sw-2022-schiaffino-spoletini-zenati/deliverables
```

### Esecuzione
#### Windows
`java -jar winExec.jar`
#### Linux
`java -jar linuxExec.jar`

Il numero di porta del Socket del Server è impostata staticamente a 12345, qualora il server lanciasse un'eccezione del tipo "Address already in use", eliminare il processo correntemente in esecuzione su questa porta.
