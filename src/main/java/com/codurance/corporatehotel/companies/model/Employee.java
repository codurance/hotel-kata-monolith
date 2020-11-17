package com.codurance.corporatehotel.companies.model;

import javax.persistence.*;

@Entity
public class Employee {

  @Id
  @GeneratedValue
  private Integer id;

  @Version
  private Integer version;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private Company company;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }
}
