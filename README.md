# Java Multithreading

## Criação de threads

main thread: a thread que é criada quando o programa é executado
user thread: o programa finaliza apenas se não tiver nenhuma user thread executando 
daemon thread: o programa pode finalizar mesmo se uma daemon estiver viva -> setDaemon() recebe false por padrão

Há duas formas de criar threads:
- extender a classe Thread e sobrescrever o método run
- implementar a interface Runnable e passar a instância para um objeto Thread

o primeiro método adiciona um restrição de design, pois não há herança múltipla em Java e a classe criada não pode estender outra classe.
o segundo método é mais flexível, pois a classe pode estender outra classe que não seja Thread.

## Sincronização 

threads em um mesmo programa existem em um espaço de memória comum: 
- podem compartilhar dados (objetos) e código
- são leves comparadas a processos

Há regiões críticas em que queremos que apenas uma thread tenha acesso a certo recurso por vez

Ao criar um bloco synchronized é necessário passar um objeto explicitamente como lock
Ao fazer um método synchronized o lock é passado implicitamente: todos os métodos synchronized em uma classe usam o mesmo lock (this) e, portanto, estão ligados. 
