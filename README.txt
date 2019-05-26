AlgaT � un software per l'autoapprendimento interattivo degli algoritmi Heap e Heapsort. Fornisce la possibilit� di inserire nuove domande tramite i file presenti nella cartella "questions_NOME_LEZIONE.txt".
Per aggiungere nuove domande basta porre domande e risposte seguite dal punto interrogativo "?" e dal punto fermo"." rispettivamente come mostrato di seguito:
<<
Domanda 1? Risposta 1.
Domanda 2? Risposta 2.
>>
Domande e risposte sono considerate indipendentemente da spazi bianchi e "case".

*ATTENZIONE*
Andare a capo alla fine delle domande provocher� un errore di lettura del file del tipo "|Domande| > |Risposte|" dove |n| � la cardinalit� di n;
Dunque questo tipo di scrittura
<<
Domanda 1? Risposta 1.
...
Domanda n? risposta n.
_
>> dove "_" indica "a capo", genera errore.
Per lo stesso ragionamento porre un quesito senza risposta genera errore.

Il file .jar deve trovarsi nella stessa cartella degli altri file per funzionare.

Il codice � stato scritto interamente da Fogu Gabriele e Mylonopoulos Dario, � open source e non � stato realizzato a fini commerciali.

Scritto con JavaFX.