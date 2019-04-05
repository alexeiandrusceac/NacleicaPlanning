package com.planning.nacleica.authactions;

import com.planning.nacleica.ObjectState;

import androidx.annotation.NonNull;

public class Worker {

    public int workerID;
    public String FirstName;
    public String UserName;
    public String LastName;
    public String Password;
    public String Phone;
    public int IsOnline;
    public ObjectState State;
    public int Title;
    public byte[] Image;
    public String Birthday;

    public Worker() {
    }

    /*
        public int getWorkerID() {
            return this.workerID;
        }

        public String getWorkerName() {
            return this.FirstName;
        }

        public String getWorkerPrename() {
            return this.Prename;
        }

        public String getWorkerPassword() {
            return this.Password;
        }

        public int getWorkerTitle() {
            return this.Title;
        }

        public byte[] getWorkerImage() {
            return this.Image;
        }

        public String getWorkerBirthday() {
            return this.Birthday;
        }
    */
    public String getFullName() {
        return this.FirstName + " " + this.LastName;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getFullName();
    }
}
