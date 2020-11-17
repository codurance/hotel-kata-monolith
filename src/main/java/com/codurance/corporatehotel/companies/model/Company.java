package com.codurance.corporatehotel.companies.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Company {

  @Id
  private Integer id;

  @Version
  private Integer version;

  @OneToMany(mappedBy = "company")
  private List<Employee> employees;

  public Company() {
  }

  public Company(Integer id) {
    this.id = id;
  }

  public List<Employee> getEmployees() {
    return employees;
  }

  public void setEmployees(List<Employee> employees) {
    this.employees = employees;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
