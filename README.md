Java 4 Informatica Musicale (j4im)
==================================

![build status](https://travis-ci.org/mapio/j4im.svg?branch=master)

Libreria di supporto all'insegnamento di
[Programmazione](http://boldi.di.unimi.it/Corsi/Mus2014/) del corso di studi
in [Informatica
musicale](http://www.ccdinf.unimi.it/it/corsiDiStudio/2015/F3Xof2/)
dell'[Università degli Studi di Milano](http://www.unimi.it/).

Come usare la libreria
----------------------

Gli studenti possono scaricare l'ultima versione dalla pagina delle
[releases](https://github.com/mapio/j4im/releases) ed accedere alla
[documentazione delle API](http://mapio.github.io/j4im) della libreria; si
osserva che una copia della documentazione è presente anche nelle release
scaricabili dal precedente link.

Una volta scaricato il `.jar`, è possibile eseguire il codice d'esempio con

	java -cp j4im-0.3-beta.jar it.unimi.di.j4im.esempi.Groove
	java -cp j4im-0.3-beta.jar it.unimi.di.j4im.esempi.FraMartinoCanone

o compilare una propria classe (supponendo si chiami `MiaClasse`) che faccia
uso della libreria con

	javac -cp j4im-0.3-beta.jar MiaClasse.java
	java -cp j4im-0.3-beta.jar MiaClasse


Come Contribuire
----------------

Chiunque voglia contribuire allo sviluppo può *segnalare eventuali errori* e
*formulare richieste* aprendo una
[issue](https://github.com/mapio/j4im/issues). Gli sviluppatori initeressati
possono fare una [fork](https://github.com/mapio/j4im/fork) di questo
repositoty: le richieste di [pull](https://github.com/mapio/j4im/pulls)
saranno considerate con molto interesse!
