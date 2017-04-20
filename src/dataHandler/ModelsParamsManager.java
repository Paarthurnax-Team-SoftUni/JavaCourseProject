package dataHandler;


public class ModelsParamsManager {
    private static volatile ModelsParamsManager instance = null;
    private ModelsCollectionData collectablesData;

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

    public void initializeCollectables(ModelsCollectionData collectablesData){
        this.collectablesData =collectablesData;
        this.collectablesData.initializeCollectables();
    }

    public String[] getCollectablesArray() {
        return this.collectablesData.getModelsCollection();
    }
}
