package bstefanov.printinghouse.data.employee;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public abstract class Employee implements Serializable {
    private final UUID id;
    private String name;
    private Date hireDate;
    private BigDecimal salary;

    public Employee(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.hireDate = new Date();
        this.salary = BigDecimal.ONE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        // Probably better to use a custom exception here?
        if (salary.compareTo(BigDecimal.ZERO) > 0)
        {
            this.salary = salary;
        }
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", hireDate=" + getHireDate() +
                ", salary=" + getSalary() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
