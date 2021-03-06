Java 4 Informatica Musicale (j4im)
==================================

[![Build Status](https://travis-ci.org/mapio/j4im.svg)](https://travis-ci.org/mapio/j4im)

Libreria di supporto all'insegnamento di
[Programmazione](http://mereghetti.di.unimi.it/prog/) del corso di studi
in [Informatica
musicale](http://www.ccdinfmi.unimi.it/it/corsiDiStudio/2017/F3Xof2/)
dell'[Università degli Studi di Milano](http://www.unimi.it/).

Come usare la libreria
----------------------

Gli studenti possono scaricare l'ultima versione dalla pagina delle
[releases](https://github.com/mapio/j4im/releases) ed accedere alla
[documentazione delle API](http://mapio.github.io/j4im) della libreria; si
osserva che una copia della documentazione è presente anche nelle release
scaricabili dal precedente link.

Una volta scaricato il `.jar`, ad esempio con il comando

	curl -sLO https://github.com/mapio/j4im/releases/download/0.11-beta/j4im-0.11-beta.jar

è possibile eseguire il codice d'esempio con

	java -cp j4im-0.11-beta.jar it.unimi.di.j4im.esempi.Groove
	java -cp j4im-0.11-beta.jar it.unimi.di.j4im.esempi.FraMartinoCanone

o compilare una propria classe (supponendo si chiami `MiaClasse`) che faccia
uso della libreria con

	javac -cp j4im-0.11-beta.jar MiaClasse.java
	java -cp j4im-0.11-beta.jar:. MiaClasse


Come contribuire
----------------

Chiunque voglia contribuire allo sviluppo può *segnalare eventuali errori* e
*formulare richieste* aprendo una
[issue](https://github.com/mapio/j4im/issues). Gli sviluppatori initeressati
possono fare una [fork](https://github.com/mapio/j4im/fork) di questo
repositoty: le richieste di [pull](https://github.com/mapio/j4im/pulls)
saranno considerate con molto interesse!

![Analytics](https://ga-beacon.appspot.com/UA-377250-20/j4im?pixel)
