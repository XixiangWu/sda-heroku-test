package au.edu.unimelb.cis.swen90007.itsms.database;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * The UnitOfWork class implements the Unit OF Work pattern, providing methods
 * to register Gateway objects to be inserted, updated, or deleted in the
 * connected database, and to either commit or rollback those changes.
 */
public class UnitOfWork {
    private List<IGateway> newObjects = new LinkedList<>();
    private List<IGateway> dirtyObjects = new LinkedList<>();
    private List<IGateway> cleanObjects = new LinkedList<>();
    private List<IGateway> deletedObjects = new LinkedList<>();

    public void registerNew(IGateway newObject) {
        newObjects.add(newObject);
    }

    public void registerDirty(IGateway dirtyObject) {
        if (cleanObjects.contains(dirtyObject)) {
            cleanObjects.remove(dirtyObject);
        }
        dirtyObjects.add(dirtyObject);
    }

    public void registerClean(IGateway cleanObject) {
        cleanObjects.add(cleanObject);
    }

    public void registerDeleted(IGateway deletedObject) {
        if (newObjects.contains(deletedObject)) {
            newObjects.remove(deletedObject);
        } else {
            deletedObjects.add(deletedObject);
        }
    }

    public void commit() {
        for (IGateway object : newObjects) {
            object.insert();
        }
        for (IGateway object : dirtyObjects) {
            object.update();
        }
        for (IGateway object : deletedObjects) {
            object.delete();
        }
        try {
            DBConnection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollback() {
        try {
            DBConnection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
