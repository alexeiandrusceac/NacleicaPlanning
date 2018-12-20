package com.planning.nacleica;

public class Tasks {
    public int idTask;
    public int idWorker;
    public String TaskName;
    public String TaskCompany;
    public String TaskCompanyPhone;
    public int TaskState;
    public String TaskPeriodFrom;
    public String TaskPeriodTo;
    public byte[] TaskImageBefore;
    public byte[] TaskImageAfter;

    public String getTaskName() {
        return this.TaskName;
    }

    public String getTaskCompany() {
        return this.TaskCompany;
    }

    public String getTaskCompanyPhone() {
        return this.TaskCompanyPhone;
    }

    public String getTaskPeriodFrom() {
        return this.TaskPeriodFrom;
    }

    public String getTaskPeriodTo() {
        return this.TaskPeriodTo;
    }

    public Tasks() {
    }

}
