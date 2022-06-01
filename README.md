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

```bash
git clone https://github.com/fedespole/ing-sw-2022-schiaffino-spoletini-zenati.git
cd ing-sw-2022-schiaffino-spoletini-zenati/deliverables
```

### Esecuzione
#### Server
`java -jar server.jar`

#### Client
`java -jar client.jar`

Dopo aver eseguito il jar del client bisogna inserire da riga di comando IP e porta del server, e successivamente scegliere fra CLI e GUI.