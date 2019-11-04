package au.edu.unimelb.cis.swen90007.itsms.database;


/**
 * The IGateway interface is used by all Gateway classes for use
 * by the UnitOfWork class and pattern.
 */
public interface IGateway {
    int insert();
    void update();
    void delete();
}
