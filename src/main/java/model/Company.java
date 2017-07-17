package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 7/13/2017.
 */
public class Company {

    private String name;
    private Company parent;
    private List<Company> children;
    private int estimatedEarning;

    public Company(String name) {
        this.name = name;
    }

    public Company(String name, int estimatedEarning) {
        this.name = name;
        this.estimatedEarning = estimatedEarning;
    }

    public Company(String name, Company parent) {
        this.name = name;
        this.parent = parent;
    }

    public Company(String name, Company parent, List<Company> children) {
        this.name = name;
        this.parent = parent;
        this.children = children;
    }

    public Company(String name, Company parent, int estimatedEarning) {
        this.name = name;
        this.parent = parent;
        this.estimatedEarning = estimatedEarning;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getParent() {
        return parent;
    }

    public void setParent(Company parent) {
        this.parent = parent;
    }

    public List<Company> getChildren() {
        return children;
    }

    public void addChild(Company child) {
        if (children == null)
            children = new ArrayList<>();
        if (!children.contains(child))
            children.add(child);
    }

    public void setChildren(List<Company> children) {
        this.children = children;
    }

    public int getEstimatedEarning() {
        return estimatedEarning;
    }

    public void setEstimatedEarning(int estimatedEarning) {
        this.estimatedEarning = estimatedEarning;
    }
}
