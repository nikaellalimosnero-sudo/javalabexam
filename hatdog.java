import java.util.ArrayList;
import java.util.Scanner;

// Class to represent an Emergency Case
class EmergencyCase {
    String description;
    int priority;

    public EmergencyCase(String description, int priority) {
        this.description = description;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "[Priority: " + priority + " | Case: " + description + "]";
    }
}

// Min-Heap Implementation for Priority Queue
class MinHeapPriorityQueue {
    private ArrayList<EmergencyCase> heap;

    public MinHeapPriorityQueue() {
        heap = new ArrayList<>();
    }

    // Insert a new emergency case
    public void insert(EmergencyCase ec) {
        heap.add(ec);
        heapifyUp(heap.size() - 1);
    }

    // Extract the most urgent case (min priority)
    public EmergencyCase extractMin() {
        if (heap.isEmpty()) return null;

        EmergencyCase min = heap.get(0);
        EmergencyCase last = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }
        return min;
    }

    // Helper: Restore heap upwards
    private void heapifyUp(int index) {
        int parent = (index - 1) / 2;
        while (index > 0 && heap.get(index).priority < heap.get(parent).priority) {
            swap(index, parent);
            index = parent;
            parent = (index - 1) / 2;
        }
    }

    // Helper: Restore heap downwards
    private void heapifyDown(int index) {
        int left, right, smallest;
        while (true) {
            left = 2 * index + 1;
            right = 2 * index + 2;
            smallest = index;

            if (left < heap.size() && heap.get(left).priority < heap.get(smallest).priority)
                smallest = left;

            if (right < heap.size() && heap.get(right).priority < heap.get(smallest).priority)
                smallest = right;

            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    // Swap elements in heap
    private void swap(int i, int j) {
        EmergencyCase temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    // Check if empty
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    // Show all cases
    public void displayAll() {
        if (heap.isEmpty()) {
            System.out.println("No pending emergencies.");
        } else {
            System.out.println("Pending emergencies:");
            for (EmergencyCase ec : heap) {
                System.out.println(ec);
            }
        }
    }
}

// Main class with menu
public class hatdog{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MinHeapPriorityQueue pq = new MinHeapPriorityQueue();

        int choice;
        do {
            System.out.println("\n--- City Emergency Response Center ---");
            System.out.println("1. Add Emergency Case");
            System.out.println("2. Process Next Emergency");
            System.out.println("3. Show All Pending Emergencies");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    System.out.print("Enter case description: ");
                    String desc = sc.nextLine();
                    System.out.print("Enter priority number (smaller = more urgent): ");
                    int prio = sc.nextInt();
                    pq.insert(new EmergencyCase(desc, prio));
                    System.out.println("Emergency case added successfully!");
                    break;

                case 2:
                    EmergencyCase ec = pq.extractMin();
                    if (ec == null) {
                        System.out.println("No emergencies to process!");
                    } else {
                        System.out.println("Processing: " + ec);
                    }
                    break;

                case 3:
                    pq.displayAll();
                    break;

                case 4:
                    System.out.println("Exiting system. Stay safe!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again!");
            }
        } while (choice != 4);

        sc.close();
    }
}
