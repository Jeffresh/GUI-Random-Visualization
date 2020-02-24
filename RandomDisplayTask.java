   /**
     * ClassNV.java
     * Purpose: generic Class that you can modify and adapt easily for any application
     * that need data visualization.
     * @author: Jeffrey Pallarés Núñez.
     * @version: 1.0 23/07/19
     */

public class RandomDisplayTask
{
    private static int[][] matrix;
    private static Canvas canvasTemplateRef;
    private static EngineGenerator handler = new EngineGenerator();
    private static RandomGenerator rg ;
    private static String random_engine;
    private static int seq_len;
    private static int seed;

    public int[][] getData() { return matrix; }

    public void plug(Canvas ref) { canvasTemplateRef = ref; }

    public void initializer(int seed, int sequence_length, String random_engine) {
        matrix = new int[1000][1000];

        for (int i = 0; i < 1000 ; i++)
            for (int j = 0; j < 1000 ; j++)
                matrix[i][j] = 0;

        handler.createEngines();
        rg = new RandomGenerator(seed);
        RandomDisplayTask.random_engine = random_engine;
        seq_len = sequence_length;
        RandomDisplayTask.seed = seed;
    }
    public static Boolean abort = false;

    public static void stop() {
        abort = true;
    }

    public void computeClassNV(int color) {

        abort = false;
        int point_value = 0;
        int random_generated;
        rg.reset();

        for(int i = 1; i < seq_len; i++){
            if(abort)
                break;
            random_generated = rg.getIthRandomNumber(handler.engines.get(random_engine), seed,i).intValue()%1000;
            System.out.println(random_generated);
            if(i%2==1)
                point_value= random_generated%1000;
            else{
                matrix[point_value][random_generated]=color;
                canvasTemplateRef.paintImmediately(0,0,1000,1000);
            }
        }
    }
    
}