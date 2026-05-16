package pcd.provetask;


public class ProveTaskMain {
    public static void main(String[] argv) {
        System.out.println("inizio prova");

        int poolSize = Runtime.getRuntime().availableProcessors() + 1;
        ProvaService service = new ProvaService(poolSize-1, poolSize);

        final var numberOfProcessors = Runtime.getRuntime().availableProcessors();
        var numberOfBallsOnBoard = 16.0;
        System.out.println(numberOfProcessors);
        Double ballsPerThread = ((numberOfBallsOnBoard*1.0)/numberOfProcessors);

        var diff = ballsPerThread - ballsPerThread.intValue();
        System.out.println(ballsPerThread);
        System.out.println(diff);
        //service.computeParallel();
    }
}
