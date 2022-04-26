package view.panes.entry_panes;

import model.storeclasses.StoreClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class EntryPane extends ContentRegion{

    private final String name;
    private final StoreClass storeClass;

    public EntryPane(String name, StoreClass storeClass) {
        this.storeClass = storeClass;
        this.name = name;
    }

    public void showContent(){
        try {
            Method getter = storeClass.getClass().getMethod("get"+name);
            String content = getter.invoke(storeClass)+"";
            setContent(content);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public boolean save(){
        try {
            Method setter;
            if(getContent().isEmpty()){
                System.out.println("Not saved");
                return false;
            }
            if(this instanceof ChoiceBoxEntry){
                setter = storeClass.getClass().getMethod("set" + name, int.class);
                setter.invoke(storeClass, Integer.parseInt(getContent()));
            } else {
                setter = storeClass.getClass().getMethod("set" + name, Class.forName("java.lang.String"));
                setter.invoke(storeClass, getContent());
            }
        } catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println("SAVE "+getContent());
        return true;
    }
}
