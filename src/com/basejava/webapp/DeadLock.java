package com.basejava.webapp;

import java.util.Random;

public class DeadLock {
    public static void main(String[] args) throws InterruptedException {
        Account account1 = new Account();
        Account account2 = new Account();
        Runner runner = new Runner();
        Thread thread1 = new Thread(() -> runner.thread(account1, account2));
        Thread thread2 = new Thread(() -> runner.thread(account2, account1));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        runner.finished(account1, account2);
    }
}

class Runner {
    public void thread(Account account1, Account account2) {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            synchronized (account1) {
                synchronized (account2) {
                    Account.transfer(account1, account2, random.nextInt(100));
                }
            }
        }
    }

    public void finished(Account account1, Account account2) {
        System.out.println(account1.getBalance());
        System.out.println(account2.getBalance());
        System.out.println("Total balance: " + (account1.getBalance() + account2.getBalance()));
    }
}

class Account {
    private int balance = 10000;

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }

    public static void transfer(Account account1, Account account2, int amount) {
        account1.withdraw(amount);
        account2.deposit(amount);
    }
}
