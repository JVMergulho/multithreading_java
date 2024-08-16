# Java Multithreading

## Criação de threads

main thread: a thread que é criada quando o programa é executado
user thread: o programa finaliza apenas se não tiver nenhuma user thread executando 
daemon thread: o programa pode finalizar mesmo se uma daemon estiver viva -> setDaemon() recebe false por padrão

Há duas formas de criar threads:
- extender a classe Thread e sobrescrever o método run
- implementar a interface Runnable e passar a instância para um objeto Thread

o primeiro método adiciona um restrição de design, pois não há herança múltipla em Java e a classe criada não pode estender outra classe.
o segundo método é mais flexível, pois a classe pode herdar de outra classe.

## Sincronização 

threads em um mesmo programa existem em um espaço de memória comum: 
- podem compartilhar dados (objetos) e código
- são leves comparadas a processos

Há regiões críticas em que queremos que apenas uma thread tenha acesso a certo recurso
