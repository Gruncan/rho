package testgame;

import net.rho.core.GameObject;


public class SnakeQueue {

    //Tail being the head of the snake but tail of queue
    private final GameObject[] array;
    private final int n;
    private int head;
    private int tail;


    public SnakeQueue(int n) {
        this.array = new GameObject[n];
        this.head = 0;
        this.tail = 0;
        this.n = n;
    }


    /**
     * Inserts element at head of snake
     */
    public void insert(GameObject gameObject) {
        if (this.size() == this.n - 1) {
            System.out.println("FULL");
            return;
        }

        this.array[this.tail] = gameObject;
        this.tail = (this.tail + 1) % this.n;
    }


    /**
     * Removes element from back
     */
    public GameObject remove() {
        if (this.isEmpty()) {
            System.out.println("UNDERFLOW");
            return null;
        }
        GameObject x = this.array[this.head];
        this.head = (this.head + 1) % this.n;
        return x;
    }


    /**
     * Inserts element at tail of snake
     */
    public void add(GameObject gameObject) {
        if (this.size() == this.n - 1) {
            System.out.println("FULL");
            return;
        }

        this.head = this.head == 0 ? this.n - 1 : this.head - 1;
        this.array[this.head] = gameObject;

    }


    public GameObject peak() {
        int index = this.tail == 0 ? this.n - 1 : this.tail - 1;
        return this.array[index];
    }


    public boolean isEmpty() {
        return this.head == this.tail;
    }

    public int size() {
        return (this.n - this.head + this.tail) % this.n;
    }


    public GameObject[] getArray() {
        GameObject[] gameObjects = new GameObject[this.size()];
        int i = this.head;
        int j = 0;
        while (i != this.tail) {
            gameObjects[j] = this.array[i];
            i = (i + 1) % this.n;
            j++;
        }
        return gameObjects;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.n; i++) {
            GameObject go = this.array[i];
            sb.append("(").append(i).append(") ");
            if (go == null)
                sb.append("[ * ]");
            else
                sb.append("[ ").append(go).append(" ]");


            if (i == this.head)
                sb.append("(Head)");
            else if (i == this.tail)
                sb.append("(Tail)");


            if (i != n - 1)
                sb.append(", ");

        }
        return sb.toString();
    }
}
