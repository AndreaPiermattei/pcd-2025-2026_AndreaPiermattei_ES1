package pcd.provetask;


public class ProveTaskMain {
    public static void main(String[] argv) {
        System.out.println("inizio prova");

        int poolSize = Runtime.getRuntime().availableProcessors() + 1;
        ProvaService service = new ProvaService(poolSize-1, poolSize);

        service.computeParallel();
    }
}
