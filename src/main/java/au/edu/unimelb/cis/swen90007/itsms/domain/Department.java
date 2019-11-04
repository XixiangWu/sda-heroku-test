package au.edu.unimelb.cis.swen90007.itsms.domain;

import au.edu.unimelb.cis.swen90007.itsms.database.DepartmentFinder;
import au.edu.unimelb.cis.swen90007.itsms.database.DepartmentGateway;

/**
 * The Department class defines the Domain layer interface
 * for Department data,
 */
public class Department {

    private int id;

    private String departmentName;

    private String location;

    public Department(int id, String name, String location) {
        this.id = id;
        this.departmentName = name;
        this.location = location;
    }

    public Department(DepartmentGateway departmentGateway) {
        this.id = departmentGateway.getId();
        this.departmentName = departmentGateway.getDepartmentName();
        this.location = departmentGateway.getLocation();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static Department getDepartment(int departmentId) {
        DepartmentFinder finder = new DepartmentFinder();
        DepartmentGateway department = finder.find(departmentId);
        if (department == null)
            return null;
        return new Department(department);
    }
}
