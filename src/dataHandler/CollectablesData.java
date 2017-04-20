package dataHandler;

import annotations.Collectable;
import utils.constants.ResourcesConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.constants.CollectiblesAndObstaclesConstants.COLLECTABLES_CLASSES_FOLDER;
import static utils.constants.ResourcesConstants.JAVA_EXTENSION;


public class CollectablesData implements ModelsCollectionData {
    private String[] collectables;

    public CollectablesData() {
    }

    public void initializeCollectables(){
        File collectablesFolder = new File(COLLECTABLES_CLASSES_FOLDER);
        List<String> collectablesList=new ArrayList<>();
        for (File file : collectablesFolder.listFiles()) {
            if (!file.isFile() || !file.getName().endsWith(JAVA_EXTENSION)) {
                continue;
            }
            try {
                String className = file.getName().substring(0, file.getName().lastIndexOf("."));
                Class exeClass = Class.forName(ResourcesConstants.COLLECTIBLES_PACKAGE + className);
                if(exeClass.isAnnotationPresent(Collectable.class)) {
                    collectablesList.add(className);
                }
            } catch (ReflectiveOperationException roe) {
                roe.printStackTrace();
            }
        }

        String[] collectables = new String[collectablesList.size()];
        collectables = collectablesList.toArray(collectables);
        this.collectables= collectables;
    }

    @Override
    public String[] getModelsCollection() {
        return Arrays.copyOf(this.collectables,this.collectables.length);
    }
}
