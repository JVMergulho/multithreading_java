/*
 * Implemente a classe Semáforo em Java com dois métodos que realizam as seguintes operações:
 * up: incrementa o contador do semáforo e acorda threads que estejam bloqueadas.
 * down: decrementa o contador do semáforo ou, caso seja zero, suspende a thread.
 */

package practice.EE2_2017_1.question4;

class Semaforo extends Object {
	private int contador;

	public Semaforo (int inicial) {
    	contador = inicial;
	}

	public void down() {
    	synchronized (this) {
            while(contador == 0){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            contador--;
        }
	}

	public void up() {
    	synchronized (this) {
            contador++;

            this.notifyAll();
        }
	}
}
