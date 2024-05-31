package bstefanov.printinghouse.data.employee;

public class Operator extends Employee {
    private Manager manager;

    public Operator(String name, Manager manager) {
        super(name);
        this.manager = manager;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
