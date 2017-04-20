package dataHandler;


public class ModelsParamsManager {
    private static volatile ModelsParamsManager instance = null;
    private CollectablesData collectablesData;

    private ModelsParamsManager() {
    }

    public static ModelsParamsManager getInstance() {
        if (instance == null) {
            synchronized (ModelsParamsManager.class) {
                if (instance == null) {
                    instance = new ModelsParamsManager();
                }
            }
        }
        return instance;
    }

    public void initializeModelConstants(){
        this.collectablesData =new CollectablesData();
    }

    public String[] getCollectablesArray() {
        return this.collectablesData.getCollectables();
    }
}
